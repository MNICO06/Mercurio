package com.mercurio.game.Screen;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

import org.json.JSONTokener;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Shop extends ScreenAdapter{
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font1;

    Array<Image> animationImages = new Array<>();
    Array<Actor> animationTextures = new Array<>();

    private int denaro;

    private int inizioRiga = 0; //aumento e diminuisco in base a se premo la freccia in giù o in su

    private Label labelDescrizioneCopia;
    private Image imageOggettoCopia;

    private MercurioMain game;
    Image background;

    private Label labelDenaro;
    private int yPosIniziale = 560;
    private int yPosInizialeLabel = 590;
    Label labelQuantita;

    public Shop(MercurioMain game) {
        this.game=game;
        batch = new SpriteBatch();
        stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        this.font1 = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));
        Gdx.input.setInputProcessor(stage);
        show();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch

    }


    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        game.closeBox();
        for (Actor actor : animationTextures) {
            actor.remove();
        }
        for (Image img : animationImages) {
            img.remove();
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        
        Texture textureBack = new Texture("sfondo/sfondoPokemarket.png");
        // Add background
        background = new Image(textureBack);
        background.setSize(1024, 720);
        stage.addActor(background);

        //apertura json per leggere quantitativo denaro da renderizzare
        FileHandle file = Gdx.files.local("assets/ashJson/datiGenerali.json");
        JsonValue json = new JsonReader().parse(file.readString());
        denaro = json.getInt("denaro");
        labelDenaro = new Label(String.valueOf(denaro), new Label.LabelStyle(font1, null));
        labelDenaro.setFontScale(5f);
        int pos = 180;

        if (denaro > 9 && denaro < 100) {
            pos = pos - 20;
        }else if (denaro > 99 && denaro < 1000) {
            pos = pos - 40;
        }else if (denaro > 999 && denaro < 10000) {
            pos = pos - 60;
        }else if (denaro > 9999 && denaro < 100000) {
            pos = pos - 90;
        }else if (denaro > 99999 && denaro < 1000000) {
            pos = pos - 120;
        }else if (denaro > 999999 && denaro < 10000000) {
            pos = pos - 150;
        }else if (denaro > 9999999 && denaro < 100000000) {
            pos = pos - 170;
        }else if (denaro > 99999999 && denaro < 1000000000) {
            pos = pos - 175;
        }

        labelDenaro.setPosition(pos, 610);
        stage.addActor(labelDenaro);

        renderizzaLabel();
    }

    //240
    private void renderizzaLabel() {
        FileHandle file = Gdx.files.local("assets/jsonGenerali/oggettiShop.json");
        JsonValue oggettiShop = new JsonReader().parse(file.readString());

        for (int i = inizioRiga; i < inizioRiga + 5; i++) {

            JsonValue oggettoJson = oggettiShop.get(i);

            if (oggettoJson != null) {

                //TODO: inserire ancora scelta quantità, cambio pagina, riduzione dei propri soldi solo se si può comprare
                
                final String nome = oggettoJson.name;

                
                //posizionare le varie linee e poi le label al loro interno
                Texture texture1 = new Texture("sfondo/lineaOggetto.png");
                Image imageOggetto1 = new Image(texture1);
                imageOggetto1.setSize(280*2, 35*2);
                imageOggetto1.setPosition(430, yPosIniziale);
                animationImages.add(imageOggetto1);
                stage.addActor(imageOggetto1);

                Label labelSinistra = new Label(nome, new Label.LabelStyle(font1, null));
                labelSinistra.setFontScale(3f);
                labelSinistra.setPosition(460, yPosInizialeLabel);
                animationTextures.add(labelSinistra);
                stage.addActor(labelSinistra);

                Label labelDestra = new Label(String.valueOf(oggettoJson.getInt("costo")), new Label.LabelStyle(font1, null));
                labelDestra.setFontScale(3f);
                labelDestra.setPosition(900, yPosInizialeLabel);
                animationTextures.add(labelDestra);
                stage.addActor(labelDestra);

                //settaggio dell'immagine dell'oggetto
                Texture texture = new Texture(oggettoJson.getString("path"));
                Image imageOggetto = new Image(texture);
                imageOggetto.setPosition(26, 26);
                imageOggetto.setSize(75, 75);
                imageOggetto.setVisible(false);
                animationImages.add(imageOggetto);
                stage.addActor(imageOggetto);

                //codice per preparare la descrizione in modo da dividerla in righe
                String[] parole = oggettoJson.getString("descrizione").split(" ");
                StringBuilder rigaCorrente = new StringBuilder(parole[0]);

                String testoDescrizione = "";

                for (int j = 1; j < parole.length; j++) {
                    if (rigaCorrente.length() + 1 + parole[j].length() <= 55) {
                        rigaCorrente.append(" ").append(parole[j]);
                    } else {
                        rigaCorrente.append("\n");
                        testoDescrizione += rigaCorrente.toString();
                        rigaCorrente = new StringBuilder(parole[j]);
                    }
                }

                if (rigaCorrente.length() > 0) {
                    rigaCorrente.append("\n");
                    testoDescrizione += rigaCorrente.toString();
                }

                Label labelDescrizione = new Label(testoDescrizione, new Label.LabelStyle(font1, null));
                labelDescrizione.setFontScale(3f);
                labelDescrizione.setPosition(170, 70, Align.topLeft);
                animationTextures.add(labelDescrizione);
                labelDescrizione.setVisible(false);
                stage.addActor(labelDescrizione);

                labelQuantita = new Label("", new Label.LabelStyle(font1, null));

                final int index = i;
                imageOggetto1.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        //rimuovo se c'enerano di precedenti
                        if (imageOggettoCopia != null) {
                            imageOggettoCopia.setVisible(false);
                            imageOggettoCopia = null;
                        }
                        if (labelDescrizioneCopia != null) {
                            labelDescrizioneCopia.setVisible(false);
                            labelDescrizioneCopia = null;
                        }

                        //vado ad aprire il pokedex regionale
                        labelDescrizione.setVisible(true);
                        imageOggetto.setVisible(true);

                        labelDescrizioneCopia = labelDescrizione;
                        imageOggettoCopia = imageOggetto;

                        //TODO: aggiungere il controllo per inserire il numero in borsa
                        FileHandle borsa = Gdx.files.local("assets/ashJson/borsa.json");
                        JsonValue oggettoBorsa = new JsonReader().parse(borsa.readString());

                        int qta = 0;

                        for (int gianni = 0; gianni < oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).size; gianni++) {
                            if (oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).get(gianni).getString("name").equals(nome)) {
                                qta = oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).get(gianni).getInt("quantity");
                            }
                        }

                        labelQuantita.setText(String.valueOf(qta));
                        labelQuantita.setFontScale(5f);
                        labelQuantita.setPosition(220, 185);
                        animationTextures.add(labelQuantita);
                        stage.addActor(labelQuantita);

                    }
                });

                yPosIniziale -= 80;
                yPosInizialeLabel -= 80;
            }
        }
    }

    private void svuotaTutto() {
        for (Actor actor : animationTextures) {
            actor.remove();
        }
        for (Image img : animationImages) {
            img.remove();
        }
    }

}
