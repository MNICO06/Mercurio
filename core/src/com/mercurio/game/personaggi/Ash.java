package com.mercurio.game.personaggi;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.Screen.MercurioMain;

public class Ash {
    private boolean canMove = true;

    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;
    private Animation<TextureRegion> characterAnimation;

    private TextureRegion currentAnimation;

    private float stateTime;
    private Vector2 characterPosition;

    private Texture textureIndietro;
    private Texture textureAvanti;
    private Texture textureDestra;
    private Texture textureSinistra;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private int player_width;
    private int player_height;

    private float speed_Camminata_orizontale = 50;
    private float speed_Camminata_verticale = 40;
    private float muovi_X = 0;
    private float muovi_Y = 0;


    private MercurioMain game;

    private float camminataFrame_speed = 0.14f;

    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> camminaAvanti;
    private Animation<TextureRegion> camminaIndietro;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;
    

    private Rectangle boxPlayer;

    public Ash(MercurioMain game) {
        this.game = game;

        //vado a dichiarare e a prendere tutte le texture di immagini
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

        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        camminaAvanti = new Animation<>(camminataFrame_speed, avanti);
        camminaIndietro = new Animation<>(camminataFrame_speed, indietro);


        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

        //segnere la posizione del personaggio (poi mettere quella salvata)
        characterPosition= new Vector2(170, 90);

        player_width = 18; // Larghezza del personaggio
        player_height = 24; // Altezza del personaggio

        //box player con i piedi per le collisioni
        boxPlayer = new Rectangle(characterPosition.x+player_width/4, characterPosition.y+2, player_width/2, player_height/6);

        //animazione attuale che viene renderizzata(da cambiare per cambiarre l'animazione del personaggio)
        currentAnimation = fermoIndietro.getKeyFrame(0);
        stateTime = 0f;
    }

    //metodo del movimento che chiama anche il controllo collisione
    public void move(MapLayer collisionLayer, ArrayList<Rectangle> rectList) {
        if (canMove) {

            boolean keyPressed = false; // Controlla se un tasto è premuto

            stateTime += Gdx.graphics.getDeltaTime();

            movingLeft = false;
            movingRight = false;
            movingUp = false;
            movingDown = false;
        
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
                muovi_X = speed_Camminata_orizontale;
                //characterPosition.x += speed_Camminata_orizontale * Gdx.graphics.getDeltaTime();
                keyPressed = true;

                movingRight = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
                muovi_X = speed_Camminata_orizontale * -1;
                //characterPosition.x -= speed_Camminata_orizontale * Gdx.graphics.getDeltaTime();
                keyPressed = true;

                movingLeft = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
                muovi_Y = speed_Camminata_verticale * -1;
                //characterPosition.y -= speed_Camminata_verticale * Gdx.graphics.getDeltaTime();
                keyPressed = true;

                movingDown = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
                muovi_Y = speed_Camminata_verticale;
                //characterPosition.y += speed_Camminata_verticale * Gdx.graphics.getDeltaTime();
                keyPressed = true;

                movingUp = true;
            }

        
            // Se nessun tasto è premuto, imposta l'animazione fermo solo se l'animazione corrente è in uno stato fermo
            if (!keyPressed) {
                if (currentAnimation == camminaSinistra.getKeyFrame(stateTime, true)) {
                    currentAnimation = fermoSinistra.getKeyFrame(0); // Imposta il frame fermo a 0
                } else if (currentAnimation == camminaDestra.getKeyFrame(stateTime, true)) {
                    currentAnimation = fermoDestra.getKeyFrame(0);
                } else if (currentAnimation == camminaAvanti.getKeyFrame(stateTime, true)) {
                    currentAnimation = fermoAvanti.getKeyFrame(0);
                } else if (currentAnimation == camminaIndietro.getKeyFrame(stateTime, true)) {
                    currentAnimation = fermoIndietro.getKeyFrame(0);
                }
            }

            //salvo le vecchie posizioni
            float old_x = characterPosition.x;
            float old_y = characterPosition.y;

            //metodi controllo collisione in altezza (sia oggetti che npc)
            if (muovi_X != 0) {
                characterPosition.x += muovi_X * Gdx.graphics.getDeltaTime();
                boxPlayer.setPosition(characterPosition.x+player_width/4, characterPosition.y+2);
                if (checkCollisions(collisionLayer)) {
                    characterPosition.x = old_x;
                }
                if (checkCollisionsPlayer(rectList)) {
                    characterPosition.x = old_x;
                }
            }



            //metodi controllo collisione in orizzontale (sia oggetti che npc)
            if (muovi_Y != 0) {
                characterPosition.y += muovi_Y * Gdx.graphics.getDeltaTime();
                boxPlayer.setPosition(characterPosition.x+player_width/4, characterPosition.y+2);
                if (checkCollisions(collisionLayer)) {
                    characterPosition.y = old_y;
                }
                if (checkCollisionsPlayer(rectList)) {
                    characterPosition.y = old_y;
                }
            }


            muovi_X = 0;
            muovi_Y = 0;

        }
    }

    //metodo controllo collisione (prende il layer collisione poi prendo rettangolo per rettangolo e controllo)
    private boolean checkCollisions(MapLayer collisionLayer) {
        // Itera sulle celle del livello di collisione
        for (MapObject object : collisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // Controlla la collisione con il rettangolo "rect"
                if (boxPlayer.overlaps(rect)) {
                    // Collisione rilevata
                    return true;
                }
            }
        }
        // Nessuna collisione rilevata
        return false;
    }
    
    //stessa cosa di quello sopra solo che ho già il rettangolo
    private boolean checkCollisionsPlayer(ArrayList<Rectangle> rectList) {
        if (rectList != null) {
            for (Rectangle rect : rectList) {
                if (boxPlayer.overlaps(rect)) {
                    //collisione rilavata
                    return true;
                }
            }
        }
        //nessuna collisione rilevata
        return false;
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

    public TextureRegion getAnimazione() {
        return currentAnimation;
    }
    
    public Rectangle getBoxPlayer() {
        return boxPlayer;
    }

    public void setMovement(boolean canMove) {
        this.canMove = canMove;
    }

    public void dispose() {
        textureAvanti.dispose();
        textureDestra.dispose();
        textureIndietro.dispose();
        textureSinistra.dispose();
    }

}
