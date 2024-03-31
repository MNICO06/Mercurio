package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.Screen.MercurioMain;

public class Ash {

    private Texture characterSheet;
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

    private TextureRegion currentFrame;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private int player_width;
    private int player_height;

    private int speed_Camminata_orizontale = 50;
    private int speed_Camminata_verticale = 40;

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

    private Polygon characterPolygon;

    public Ash(MercurioMain game) {
        this.game = game;

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

        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        camminaAvanti = new Animation<>(camminataFrame_speed,avanti);
        camminaIndietro = new Animation<>(camminataFrame_speed, indietro);
        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

        //calcolo centro per posizionare personaggio
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();

        float windowCenterX = windowWidth / 4f;
        float windowCenterY = windowHeight / 3.2f;

        characterPosition= new Vector2(windowCenterX, windowCenterY);

        player_width = 18; // Larghezza del personaggio
        player_height = 24; // Altezza del personaggio

        // Definizione dei vertici del poligono per un personaggio rettangolare
        float[] vertices = {
            characterPosition.x, characterPosition.y,                         // Vertice in alto a sinistra
            characterPosition.x + player_width, characterPosition.y,          // Vertice in alto a destra
            characterPosition.x + player_width, characterPosition.y + player_height, // Vertice in basso a destra
            characterPosition.x, characterPosition.y + player_height          // Vertice in basso a sinistra
        };

        // Creazione del poligono con i vertici definiti
        characterPolygon = new Polygon(vertices);


        characterAnimation = new Animation<>(0.14f, indietro[0]);
        stateTime = 0f;
    }

    public void move(MapLayer collisionLayer) {
        boolean keyPressed = false; // Controlla se un tasto è premuto

        stateTime += Gdx.graphics.getDeltaTime();

        movingLeft = false;
        movingRight = false;
        movingUp = false;
        movingDown = false;
    
    
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
            characterPosition.x += speed_Camminata_orizontale * Gdx.graphics.getDeltaTime();
            keyPressed = true;

            movingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
            characterPosition.x -= speed_Camminata_orizontale * Gdx.graphics.getDeltaTime();
            keyPressed = true;

            movingLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
            characterPosition.y -= speed_Camminata_verticale * Gdx.graphics.getDeltaTime();
            keyPressed = true;

            movingDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
            characterPosition.y += speed_Camminata_verticale * Gdx.graphics.getDeltaTime();
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

        
        float old_x = characterPosition.x;
        float old_y = characterPosition.y;

        
        characterPolygon.setPosition(characterPosition.x, characterPosition.y);
    

        if (checkCollisions(collisionLayer, characterPolygon)) {
            System.out.println("no");
        }

        

    }

    private boolean checkCollisions(MapLayer collisionLayer, Polygon characterPolygon) {
        // Scorri gli oggetti del layer collision
        for(MapObject object : collisionLayer.getObjects()) {
            if(object instanceof PolygonMapObject) {
                // Ottieni il poligono dell'oggetto sulla mappa
                PolygonMapObject polygonObject = (PolygonMapObject) object;
                Polygon objectPolygon = polygonObject.getPolygon();
                
                // Verifica la collisione tra il poligono del personaggio e il poligono dell'oggetto
                if(Intersector.overlapConvexPolygons(characterPolygon, objectPolygon)) {
                    return true; // Collisione rilevata
                }
            }
        }
        return false; // Nessuna collisione rilevata
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

    public void dispose() {
        textureAvanti.dispose();
        textureDestra.dispose();
        textureIndietro.dispose();
        textureSinistra.dispose();
    }

}
