package com.mercurio.game.Screen;

import org.json.JSONArray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.pokemon.Battle;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;

public class Box extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    Array<Image> animationImages = new Array<>();
    Array<Texture> animationTextures = new Array<>();
    private String nomePoke;
    private TextureRegion[] sfondi;
    private MercurioMain game;
    private Image avantiImage;
    private Image indietroImage;

    public Box(MercurioMain game){
        this.game=game;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);

        show();
        caricaBorsa();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            dispose();
        }
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        game.closeBox();
    }

    public void leggiPoke(int numero) {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get(numero);
        nomePoke = pokeJson.getString("nomePokemon");
    }

    public void disegnaPoke(int param){
        Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
        animationTextures.add(animationTexture);
        Image background = new Image(sfondi[0]);
        TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
        // Crea un'immagine utilizzando solo la prima metà dell'immagine
        Image animationImage = new Image(animationRegion);
        animationImage.setSize(animationTexture.getWidth() +20, animationTexture.getHeight()*2 +20);
        animationImage.setPosition(250 + param*100,  (Gdx.graphics.getHeight() + background.getHeight()) / 2);
        stage.addActor(animationImage);
    }

    @Override
    public void show() {

        Texture textureBack = new Texture("sfondo/sfondoBox.png");

        int regionWidth = textureBack.getWidth() / 4;
        int regionHeight = textureBack.getHeight() / 6;

        sfondi = new TextureRegion[24];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                sfondi[i+j] = new TextureRegion(textureBack, i * regionWidth, j* regionHeight, regionWidth, regionHeight);
            }
        }

        // Add background
        Image background = new Image(sfondi[0]);
        // Ritaglia l'immagine per adattarla alla dimensione dello schermo
        background.setSize(700, 600);
        background.setPosition((Gdx.graphics.getWidth() - background.getWidth()) / 2, (Gdx.graphics.getHeight() - background.getHeight()) / 2);
        stage.addActor(background);

        // Aggiungi le immagini "avanti" e "indietro" in alto
        Texture avantiTexture = new Texture("assets/sfondo/avanti.png");
        Texture indietroTexture = new Texture("assets/sfondo/indietro.png");

        avantiImage = new Image(avantiTexture);
        indietroImage = new Image(indietroTexture);

        // Posiziona le immagini in alto a sinistra e destra
        float marginTop = 70; // distanza dal bordo superiore dello schermo
        // Calcola la posizione centrale per le immagini
        float centerX = Gdx.graphics.getWidth() / 2;
        float yPos = (Gdx.graphics.getHeight() + background.getHeight()) / 2 - avantiImage.getHeight() - marginTop;
        float separator = 240;

        indietroImage.setPosition(centerX - indietroImage.getWidth() - separator, yPos);
        indietroImage.setSize(30, 50);

        avantiImage.setPosition(centerX + separator, yPos);
        avantiImage.setSize(30, 50);

        stage.addActor(avantiImage);
        stage.addActor(indietroImage);

        // Carica il font personalizzato
        font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));

        // Crea uno stile per la Label con il font personalizzato
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        // Imposta la scala del font
        labelStyle.font.getData().setScale(5f); // Ingrandisce il font

        // Crea la Label con il testo desiderato
        Label label = new Label("BOX 1", labelStyle);

        // Posiziona la label centrata tra le frecce
        label.setPosition(centerX - label.getWidth() / 2, yPos);
        stage.addActor(label);
    }

    public void caricaBorsa() {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        // Crea un'etichetta per ogni oggetto nel file JSON
        for (int i = 0; i < json.size; i++) {
            leggiPoke(i);
            disegnaPoke(i);
        }
    }

}