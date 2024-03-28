package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ash {

    SpriteBatch batch;
    Texture characterSheet;
    TextureRegion[] indietro;
    TextureRegion[] sinistra;
    TextureRegion[] destra;
    TextureRegion[] avanti;
    Animation<TextureRegion> characterAnimation;
    float stateTime;
    Vector2 characterPosition;

    Texture textureIndietro;
    Texture textureAvanti;
    Texture textureDestra;
    Texture textureSinistra;

    TextureRegion currentFrame;

    boolean movingLeft = false;
    boolean movingRight = false;
    boolean movingUp = false;
    boolean movingDown = false;

    private int player_width;
    private int player_height;

    private int speed_Camminata_orizontale = 50;
    private int speed_Camminata_verticale = 40;

    public Ash() {
        //dichiarazione generale delle animazione personaggi
        indietro = new TextureRegion[3];
        avanti = new TextureRegion[3];
        destra = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        textureIndietro = new Texture(Gdx.files.internal("player/personaggioIndietro.png"));
        textureAvanti = new Texture(Gdx.files.internal("player/personaggioAvanti.png"));
        textureDestra = new Texture(Gdx.files.internal("player/personaggioDestra.png"));
        textureSinistra = new Texture(Gdx.files.internal("player/personaggioSinistra.png"));


        int regionWidthInd = textureIndietro.getWidth() / 3;
        int regionHeightInd = textureIndietro.getHeight();
        int regionWidthAv = textureIndietro.getWidth() / 3;
        int regionHeightAv = textureIndietro.getHeight();
        int regionWidthDx = textureDestra.getWidth() / 3;
        int regionHeightDx = textureDestra.getHeight();
        int regionWidthSx = textureSinistra.getWidth() / 3;
        int regionHeightSx = textureSinistra.getHeight();

        for (int i = 0; i < 3; i++) {
            indietro[i] = new TextureRegion(textureIndietro, i * regionWidthInd, 0, regionWidthInd, regionHeightInd);
            avanti[i] = new TextureRegion(textureAvanti, i * regionWidthAv, 0, regionWidthAv, regionHeightAv);
            destra[i] = new TextureRegion(textureDestra, i * regionWidthDx, 0, regionWidthDx, regionHeightDx);
            sinistra[i] = new TextureRegion(textureSinistra, i * regionWidthSx, 0, regionWidthSx, regionHeightSx);
        }

        //calcolo centro personaggio
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();

        float windowCenterX = windowWidth / 4f;
        float windowCenterY = windowHeight / 3.2f;

        characterPosition= new Vector2(windowCenterX, windowCenterY);

        player_width = 18; // Larghezza del personaggio
        player_height = 24; // Altezza del personaggio

        characterAnimation = new Animation<>(0.14f, indietro[0]);
        stateTime = 0f;
    }



    //rendering del personaggio
    public void render(SpriteBatch batch, MapLayer collisioniLayer, OrthographicCamera camera) {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = characterAnimation.getKeyFrame(stateTime, true);
        
        camera.position.set(getPlayerPosition().x + player_width / 2, getPlayerPosition().y + player_height / 2, 0);
        camera.update();
        
        controlloTasti(collisioniLayer);
        batch.draw(currentFrame, characterPosition.x, characterPosition.y, player_width, player_height);

    }


    //controllo del tasti e quindi del movimento del personaggio
    public void controlloTasti(MapLayer collisioniLayer) {

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterAnimation = new Animation<>(0.14f, destra);
            characterPosition.x += speed_Camminata_orizontale * Gdx.graphics.getDeltaTime(); // Aumenta la coordinata x
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterAnimation = new Animation<>(0.14f, sinistra);
            characterPosition.x -= speed_Camminata_orizontale * Gdx.graphics.getDeltaTime(); // Diminuisci la coordinata x
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterAnimation = new Animation<>(0.14f, indietro);
            characterPosition.y -= speed_Camminata_verticale * Gdx.graphics.getDeltaTime(); // Diminuisci la coordinata y
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            characterAnimation = new Animation<>(0.14f, avanti);
            characterPosition.y += speed_Camminata_verticale * Gdx.graphics.getDeltaTime(); // Aumenta la coordinata y
        }

        //controllo per non far muovere il personaggio nel caso in cui nessuno stia premendo il tasto
        if (!Gdx.input.isKeyPressed(Input.Keys.A) && movingLeft) {
            currentFrame = sinistra[0];
        } else if (!Gdx.input.isKeyPressed(Input.Keys.D) && movingRight) {
            currentFrame = destra[0];
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && movingUp) {
            currentFrame = avanti[0];
        } else if (!Gdx.input.isKeyPressed(Input.Keys.S) && movingDown) {
            currentFrame = indietro[0];
        }
        

    }

    public Vector2 getPlayerPosition() {
        return characterPosition;
    }

    public int getPlayerWidth() {
        return player_width;
    }

    public int getPlayerHeight() {
        return player_height;
    }

}
