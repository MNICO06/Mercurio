package com.mercurio.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Pokedex {
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private MenuLabel chiamanteM;

    private Image background;
    private Image backgroundPokedex;
    private Image tastoApriImage;
    private Image tastoXImage;
    private Image avantiImage;
    private Image indietroImage;
    private Label label;

    private Array<Actor> pokedexActor;
    Array<Image> animationImages = new Array<>();

    private int pagina = 1;
    private int yPoke = 540;
    private int cont = 1;
    private int pokePerPag = 30;
    private int numeroPokemon = 73;

    public Pokedex(Stage stage, MenuLabel chiamanteM) {
        this.stage = stage;
        this.chiamanteM = chiamanteM;
        this.pokedexActor = new Array<>();
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));
        Gdx.input.setInputProcessor(stage);
        renderizzaPokedex();
        
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    private void renderizzaPokedex() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // renderizzo il background iniziale
        Texture backgroundTexture = new Texture("sfondo/sfondoPokedex.png");
        background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
        pokedexActor.add(background);

        // renderizzo il background dove ci vanno i pokemon
        Texture backgroundPokedexTexture = new Texture("sfondo/sfondoPokedexAperto.png");
        backgroundPokedex = new Image(backgroundPokedexTexture);
        backgroundPokedex.setSize(256*4.4f, 180*4.4f);
        backgroundPokedex.setPosition(-50, -60);
        backgroundPokedex.setColor(1,1,1,0.95f);
        stage.addActor(backgroundPokedex);
        backgroundPokedex.setVisible(false);
        pokedexActor.add(backgroundPokedex);


        Texture tastoApriTexture = new Texture("sfondo/tastoAperturaPokedex.png");
        tastoApriImage = new Image(tastoApriTexture);
        tastoApriImage.setSize(66*4, 27*4);
        tastoApriImage.setPosition((screenWidth/2) - 130, (screenHeight/2) + 100);
        stage.addActor(tastoApriImage);
        pokedexActor.add(tastoApriImage);

        tastoApriImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //vado ad aprire il pokedex regionale

                tastoApriImage.setVisible(false);
                backgroundPokedex.setVisible(true);
                indietroImage.setVisible(true);
                avantiImage.setVisible(true);
                label.setVisible(true);
                renderizzaPoke();
            }
        });

        Texture tastoX = new Texture("sfondo/X.png");
        tastoXImage = new Image(tastoX);
        tastoXImage.setPosition(screenWidth - 86, 10);
        stage.addActor(tastoXImage);
        pokedexActor.add(tastoXImage);
        
        tastoXImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pulsisciInventario();
                pulisciPagina();
                chiamanteM.closePokedex();
                
            }
        });

        // Aggiungi le immagini "avanti" e "indietro" in alto
        Texture avantiTexture = new Texture("assets/sfondo/avanti.png");
        Texture indietroTexture = new Texture("assets/sfondo/indietro.png");

        avantiImage = new Image(avantiTexture);
        indietroImage = new Image(indietroTexture);

        // Carica il font personalizzato
        font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));
        // Crea uno stile per la Label con il font personalizzato
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        // Imposta la scala del font
        labelStyle.font.getData().setScale(5f); // Ingrandisce il font
        indietroImage.setSize(30, 50);
        avantiImage.setSize(30, 50);
        indietroImage.setPosition(300, 60);
        avantiImage.setPosition(700, 60);
        indietroImage.setVisible(false);
        avantiImage.setVisible(false);

        //listener per la freccia avanti
        indietroImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //se non la pagina successiva non esiste non fa nulla
                if ((pagina -1) <= 0) {

                }else {
                    pagina -= 1;
                    renderizzaPoke();
                    aggiornaVisibilitaFreccie();
                    aggiornaVisibilitaFreccie();
                    label.setText(getPaginaLabel(pagina));
                }
            }
        });

        //listener per la freccia avanti
        avantiImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //se non la pagina successiva non esiste non fa nulla
                if ((pagina + 1) > 3) {

                }else {
                    pagina += 1;
                    renderizzaPoke();
                    aggiornaVisibilitaFreccie();
                    label.setText(getPaginaLabel(pagina));
                }
            }
        });

        stage.addActor(indietroImage);
        stage.addActor(avantiImage);

        // Crea la Label con il testo desiderato
        label = new Label("PAGINA 1", labelStyle);
        label.setPosition(410, 60);
        label.setVisible(false);

        stage.addActor(label);

    }


    private void renderizzaPoke() {
        pulisciPagina();

        FileHandle file = Gdx.files.local("assets/ashJson/pokemonScoperti.json");
        JsonValue json = new JsonReader().parse(file.readString());

        cont = 1;

        for (int i = (pokePerPag * (pagina - 1)) +1; i < (pokePerPag * (pagina)) +1; i ++) {
            JsonValue pokeJson = json.get(String.valueOf(i));

            if (pokeJson != null) {

                String nomePoke = pokeJson.getString("nome");

                Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());

                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth() + 40, animationTexture.getHeight()*2 + 40);
                animationImage.setPosition(50 + (cont * 120),  yPoke);
                stage.addActor(animationImage);

                //TODO: aggiungere il listener e il renderlo nero se non presente
                

                animationImages.add(animationImage);

                cont ++;
                if (cont == 7) {
                    cont = 1;
                    yPoke = yPoke - 100;

                }
            }
        }

    }

    private void pulisciPagina() {
        for (Image img : animationImages) {
            img.remove();
        }

        animationImages.clear();
        cont = 0;
        yPoke = 540;
    }

    private void pulsisciInventario() {
        for (Actor actor : pokedexActor) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
    }

    private void aggiornaVisibilitaFreccie() {
        // Nasconde la freccia indietro se sei sulla prima pagina
        indietroImage.setVisible(pagina > 1);
        // Nasconde la freccia avanti se sei sull'ultima pagina
        avantiImage.setVisible(pagina < 3);
    }
    public String getPaginaLabel(int number) {
        return "PAGINA " + number;
    }


}
