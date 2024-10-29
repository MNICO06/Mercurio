package com.mercurio.game.Screen;

import org.json.JSONArray;

import com.badlogic.gdx.Gdx;
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

    public Box(){
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
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
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

    public void disegnaPoke(){
        Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
        animationTextures.add(animationTexture);
        TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
        // Crea un'immagine utilizzando solo la prima metà dell'immagine
        Image animationImage = new Image(animationRegion);
        animationImage.setSize(animationTexture.getWidth(), animationTexture.getHeight()*2);
        animationImage.setPosition(3,  45 * 3 - 55);
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
        background.setSize(400, 400);
        stage.addActor(background);

        //background.setDrawable(new TextureRegionDrawable(sfondi[quello_che_vuoi]));
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
            disegnaPoke();
        }
    }
    
}