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

    private Array<Actor> pokedexActor;

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
                chiamanteM.closePokedex();
                
            }
        });

    }


    private void renderizzaPoke() {
        FileHandle file = Gdx.files.local("assets/ashJson/pokemonScoperti.json");
        JsonValue json = new JsonReader().parse(file.readString());

        int cont = 1;
        int yPoke = 540;

        for (int i = 1; i < json.size + 1; i ++) {
            JsonValue pokeJson = json.get(String.valueOf(i));
            String nomePoke = pokeJson.getString("nome");

            Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
            TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());

            Image animationImage = new Image(animationRegion);
            animationImage.setSize(animationTexture.getWidth() + 40, animationTexture.getHeight()*2 + 40);
            animationImage.setPosition(60 + (cont * 80),  yPoke);
            stage.addActor(animationImage);

            cont ++;
            if (cont == 10) {
                cont = 1;
                yPoke = yPoke - 100;

            }

        }

    }

    private void pulsisciInventario() {
        for (Actor actor : pokedexActor) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
    }

}
