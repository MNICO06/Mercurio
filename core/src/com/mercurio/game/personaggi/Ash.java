package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

    //variabili per il posizionamento corretto del personaggio
    int windowWidth;
    int windowHeight;
    float windowCenterX;
    float windowCenterY;

    float speed = 0.7f; // Velocità di movimento del personaggio
    
    private Rectangle collision_box;

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
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();

        windowCenterX = windowWidth / 2.3f;
        windowCenterY = windowHeight / 2.3f;



        characterAnimation = new Animation<>(0.14f, indietro[0]);
        stateTime = 0f;
    }

    public void gestioneCollisioni(MapLayer collisioniLayer) {

    }

    private boolean checkCollision(MapLayer collisioniLayer) {
        for (MapObject object : collisioniLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // Controlla la collisione con il rettangolo "rect"
                if (collision_box.overlaps(rect)) {
                    // Collisione rilevata
                    return true;
                }
            }
        }
        return false;
    }

    //rendering del personaggio
    public void render(SpriteBatch batch) {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = characterAnimation.getKeyFrame(stateTime, true);
        controlloTasti();
        batch.draw(currentFrame,windowCenterX,windowCenterY,100,130);



    }
    

    //controllo del tasti e quindi del movimento del personaggio
    public void controlloTasti() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterAnimation = new Animation<>(0.14f, destra);
            movingLeft = false;
            movingRight = true;
            movingUp = false;
            movingDown = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterAnimation = new Animation<>(0.14f, sinistra);
            movingLeft = true;
            movingRight = false;
            movingUp = false;
            movingDown = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterAnimation = new Animation<>(0.14f, indietro);
            movingLeft = false;
            movingRight = false;
            movingUp = false;
            movingDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            characterAnimation = new Animation<>(0.14f, avanti);
            movingLeft = false;
            movingRight = false;
            movingUp = true;
            movingDown = false;
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
