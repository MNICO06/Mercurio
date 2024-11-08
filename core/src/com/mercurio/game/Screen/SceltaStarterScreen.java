package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

public class SceltaStarterScreen extends ScreenAdapter {

    private MercurioMain game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    private String nomePoke;
    Array<Image> animationImages = new Array<>();
    Array<Texture> animationTextures = new Array<>();

    public SceltaStarterScreen(MercurioMain game){
        this.game=game;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
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
    }


    @Override
    public void show() {

        int regionWidth = 1024;
        int regionHeight = 720;

        Texture litten = new Texture("assets/pokemon/litten.png");
        Texture rowlet = new Texture("assets/pokemon/rowlet.png");
        Texture popplio = new Texture("assets/pokemon/popplio.png");

        int cols = 4;
        int rows = 1;

        // Crea array per contenere i frame
        TextureRegion[] littenFrames = new TextureRegion[cols];
        TextureRegion[] rowletFrames = new TextureRegion[cols];
        TextureRegion[] popplioFrames = new TextureRegion[cols];


        // Suddividi le textures in frame
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Suddividi ogni texture in frame usando TextureRegion.split
                littenFrames[j] = new TextureRegion(litten, j * (litten.getWidth() / cols), i * (litten.getHeight() / rows), litten.getWidth() / cols, litten.getHeight() / rows);
                rowletFrames[j] = new TextureRegion(rowlet, j * (rowlet.getWidth() / cols), i * (rowlet.getHeight() / rows), rowlet.getWidth() / cols, rowlet.getHeight() / rows);
                popplioFrames[j] = new TextureRegion(popplio, j * (popplio.getWidth() / cols), i * (popplio.getHeight() / rows), popplio.getWidth() / cols, popplio.getHeight() / rows);
            }
        }

        // Crea immagini per il primo frame (indice 0) di ciascun Pokémon
        Image littenImage = new Image(littenFrames[0]);
        Image rowletImage = new Image(rowletFrames[0]);
        Image popplioImage = new Image(popplioFrames[0]);

        littenImage.setSize(200, 200);
        rowletImage.setSize(200, 200);
        popplioImage.setSize(200, 200);


        // Posiziona le immagini sullo stage
        littenImage.setPosition(150, 300); // Posizione di litten
        rowletImage.setPosition(400, 300); // Posizione di rowlet
        popplioImage.setPosition(650, 300); // Posizione di popplio


        // Aggiungi le immagini allo stage
        stage.addActor(littenImage);
        stage.addActor(rowletImage);
        stage.addActor(popplioImage);

    }


}
