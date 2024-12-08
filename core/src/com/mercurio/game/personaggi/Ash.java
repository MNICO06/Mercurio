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
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetsAsh;
import com.mercurio.game.Screen.MercurioMain;

public class Ash {
    private boolean canMove = true;

    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;
    private TextureRegion[] indietroSurf;
    private TextureRegion[] sinistraSurf;
    private TextureRegion[] destraSurf;
    private TextureRegion[] avantiSurf;

    private TextureRegion currentAnimation;

    private float stateTime;
    private Vector2 characterPosition;

    private Texture textureIndietro;
    private Texture textureAvanti;
    private Texture textureDestra;
    private Texture textureSinistra;

    private Texture textureIndietroSurf;
    private Texture textureAvantiSurf;
    private Texture textureDestraSurf;
    private Texture textureSinistraSurf;

    private int player_width;
    private int player_height;
    private int player_width_surf;
    private int player_height_surf;
    private int player_width_current;
    private int player_height_current;

    private float speed_Camminata_orizontale = 300;
    private float speed_Camminata_verticale = 300;
    private float speed_Camminata_orizontale_surf = 100;
    private float speed_Camminata_verticale_surf = 100;
    private float muovi_X = 0;
    private float muovi_Y = 0;

    private boolean inAcqua = false;

    private MercurioMain game;

    private float camminataFrame_speed = 0.14f;

    // camminata
    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> camminaAvanti;
    private Animation<TextureRegion> camminaIndietro;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;

    // surf normale
    private Animation<TextureRegion> surfSinistra;
    private Animation<TextureRegion> surfDestra;
    private Animation<TextureRegion> surfAvanti;
    private Animation<TextureRegion> surfIndietro;
    private Animation<TextureRegion> surfSinistraFermo;
    private Animation<TextureRegion> surfDestraFermo;
    private Animation<TextureRegion> surfAvantiFermo;
    private Animation<TextureRegion> surfIndietroFermo;

    private Rectangle boxPlayer;
    private Rectangle boxPlayer_cammina;
    private Rectangle boxPlayer_surf;

    private GameAsset asset;

    public Ash(MercurioMain game) {
        this.game = game;
        this.asset = game.getGameAsset();

        // vado a dichiarare e a prendere tutte le texture di immagini
        indietro = new TextureRegion[3];
        avanti = new TextureRegion[3];
        destra = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        textureIndietro = asset.getAsh(AssetsAsh.P_INDIETRO);
        textureAvanti = asset.getAsh(AssetsAsh.P_AVANTI);
        textureDestra = asset.getAsh(AssetsAsh.P_DESTRA);
        textureSinistra = asset.getAsh(AssetsAsh.P_SINISTRA);

        int regionWidthInd = textureIndietro.getWidth() / 3;
        int regionHeightInd = textureIndietro.getHeight();
        int regionWidthAv = textureIndietro.getWidth() / 3;
        int regionHeightAv = textureIndietro.getHeight();
        int regionWidthDx = textureDestra.getWidth() / 3;
        int regionHeightDx = textureDestra.getHeight();
        int regionWidthSx = textureSinistra.getWidth() / 3;
        int regionHeightSx = textureSinistra.getHeight();

        try {

            for (int i = 0; i < 3; i++) {
                indietro[i] = new TextureRegion(textureIndietro, i * regionWidthInd, 0, regionWidthInd,
                        regionHeightInd);
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

        } catch (Exception e) {
            System.out.println("Errore caricamento sprite ash, " + e);
        }

        // -------------------TEXTURE
        // SURF-------------------------------------------------------
        sinistraSurf = new TextureRegion[4];
        destraSurf = new TextureRegion[4];
        avantiSurf = new TextureRegion[4];
        indietroSurf = new TextureRegion[4];
        textureSinistraSurf = asset.getAsh(AssetsAsh.SURF_P_SINISTRA);
        textureDestraSurf = asset.getAsh(AssetsAsh.SURF_P_DESTRA);
        textureAvantiSurf = asset.getAsh(AssetsAsh.SURF_P_AVANTI);
        textureIndietroSurf = asset.getAsh(AssetsAsh.SURF_P_INDIETRO);

        regionWidthInd = textureIndietroSurf.getWidth() / 4;
        regionHeightInd = textureIndietroSurf.getHeight();
        regionWidthAv = textureAvantiSurf.getWidth() / 4;
        regionHeightAv = textureAvantiSurf.getHeight();
        regionWidthDx = textureDestraSurf.getWidth() / 4;
        regionHeightDx = textureDestraSurf.getHeight();
        regionWidthSx = textureSinistraSurf.getWidth() / 4;
        regionHeightSx = textureSinistraSurf.getHeight();

        try {
            for (int i = 0; i < 4; i++) {
                sinistraSurf[i] = new TextureRegion(textureSinistraSurf, i * regionWidthInd, 0, regionWidthInd,
                        regionHeightInd);
                destraSurf[i] = new TextureRegion(textureDestraSurf, i * regionWidthDx, 0, regionWidthDx,
                        regionHeightDx);
                avantiSurf[i] = new TextureRegion(textureAvantiSurf, i * regionWidthSx, 0, regionWidthSx,
                        regionHeightSx);
                indietroSurf[i] = new TextureRegion(textureIndietroSurf, i * regionWidthAv, 0, regionWidthAv,
                        regionHeightAv);
            }

            surfSinistra = new Animation<>(camminataFrame_speed, sinistraSurf);
            surfDestra = new Animation<>(camminataFrame_speed, destraSurf);
            surfAvanti = new Animation<>(camminataFrame_speed, avantiSurf);
            surfIndietro = new Animation<>(camminataFrame_speed, indietroSurf);
            surfSinistraFermo = new Animation<>(camminataFrame_speed, sinistraSurf[0]);
            surfDestraFermo = new Animation<>(camminataFrame_speed, destraSurf[0]);
            surfAvantiFermo = new Animation<>(camminataFrame_speed, avantiSurf[0]);
            surfIndietroFermo = new Animation<>(camminataFrame_speed, indietroSurf[0]);

        } catch (Exception e) {
            System.out.println("Errore caricamento sprite ash surf, " + e);
        }

        // segnere la posizione del personaggio (poi mettere quella salvata)
        characterPosition = new Vector2(170, 90);

        player_width = 18;
        player_height = 24;
        player_width_surf = 45;
        player_height_surf = 60;

        player_width_current = 18;
        player_height_current = 24;

        // box player con i piedi per le collisioni
        boxPlayer = new Rectangle(characterPosition.x + player_width / 4, characterPosition.y + 2, player_width / 2,
                player_height / 6);
        boxPlayer_cammina = new Rectangle(characterPosition.x + player_width / 4, characterPosition.y + 2,
                player_width / 2, player_height / 6);
        boxPlayer_surf = new Rectangle(characterPosition.x + player_width_surf / 4, characterPosition.y + 2,
                player_width_surf / 2, player_height_surf / 6);

        // animazione attuale che viene renderizzata(da cambiare per cambiarre
        // l'animazione del personaggio)
        currentAnimation = fermoIndietro.getKeyFrame(0);
        stateTime = 0f;
    }

    // metodo del movimento che chiama anche il controllo collisione
    public void move(MapLayer oggettiStoria, MapLayer collisionLayer, ArrayList<Rectangle> rectList) {
        try {
            if (canMove) {

                boolean keyPressed = false; // Controlla se un tasto è premuto

                stateTime += Gdx.graphics.getDeltaTime();

                

                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    if (inAcqua == false) {
                        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
                        muovi_X = speed_Camminata_orizontale;
                    } else {
                        currentAnimation = surfDestra.getKeyFrame(stateTime, true);
                        muovi_X = speed_Camminata_orizontale_surf;
                    }

                    keyPressed = true;

                   
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    if (inAcqua == false) {
                        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
                        muovi_X = speed_Camminata_orizontale * -1;
                    } else {
                        currentAnimation = surfSinistra.getKeyFrame(stateTime, true);
                        muovi_X = speed_Camminata_orizontale_surf * -1;
                    }

                    keyPressed = true;


                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    if (inAcqua == false) {
                        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
                        muovi_Y = speed_Camminata_verticale * -1;
                    } else {
                        currentAnimation = surfIndietro.getKeyFrame(stateTime, true);
                        muovi_Y = speed_Camminata_verticale_surf * -1;
                    }

                    keyPressed = true;


                }
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    if (inAcqua == false) {
                        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
                        muovi_Y = speed_Camminata_verticale;
                    } else {
                        currentAnimation = surfAvanti.getKeyFrame(stateTime, true);
                        muovi_Y = speed_Camminata_verticale_surf;
                    }

                    keyPressed = true;


                }

                // Se nessun tasto è premuto, imposta l'animazione fermo solo se l'animazione
                // corrente è in uno stato fermo
                if (!keyPressed) {
                    if (inAcqua == false) {
                        if (currentAnimation == camminaSinistra.getKeyFrame(stateTime, true)) {
                            currentAnimation = fermoSinistra.getKeyFrame(0); // Imposta il frame fermo a 0
                        } else if (currentAnimation == camminaDestra.getKeyFrame(stateTime, true)) {
                            currentAnimation = fermoDestra.getKeyFrame(0);
                        } else if (currentAnimation == camminaAvanti.getKeyFrame(stateTime, true)) {
                            currentAnimation = fermoAvanti.getKeyFrame(0);
                        } else if (currentAnimation == camminaIndietro.getKeyFrame(stateTime, true)) {
                            currentAnimation = fermoIndietro.getKeyFrame(0);
                        }
                    } else {
                        if (currentAnimation == surfSinistra.getKeyFrame(stateTime, true)) {
                            currentAnimation = surfSinistraFermo.getKeyFrame(0); // Imposta il frame fermo a 0
                        } else if (currentAnimation == surfDestra.getKeyFrame(stateTime, true)) {
                            currentAnimation = surfDestraFermo.getKeyFrame(0);
                        } else if (currentAnimation == surfAvanti.getKeyFrame(stateTime, true)) {
                            currentAnimation = surfAvantiFermo.getKeyFrame(0);
                        } else if (currentAnimation == surfIndietro.getKeyFrame(stateTime, true)) {
                            currentAnimation = surfIndietroFermo.getKeyFrame(0);
                        }
                    }

                    game.setisInMovement(false);
                } else {
                    game.setisInMovement(true);
                }

                // salvo le vecchie posizioni
                float old_x = characterPosition.x;
                float old_y = characterPosition.y;

                // metodi controllo collisione in orizzontale (sia oggetti che npc)
                if (muovi_X != 0) {
                    characterPosition.x += muovi_X * Gdx.graphics.getDeltaTime();
                    boxPlayer.setPosition(characterPosition.x + player_width / 4, characterPosition.y + 2);
                    if (checkCollisions(collisionLayer)) {
                        characterPosition.x = old_x;
                    }
                    if (checkCollisionsPlayer(rectList)) {
                        characterPosition.x = old_x;
                    }
                    if (checkCollisionsStory(oggettiStoria)) {
                        characterPosition.x = old_x;
                    }
                }

                // metodi controllo collisione in verticale (sia oggetti che npc)
                if (muovi_Y != 0) {
                    characterPosition.y += muovi_Y * Gdx.graphics.getDeltaTime();
                    boxPlayer.setPosition(characterPosition.x + player_width / 4, characterPosition.y + 2);
                    if (checkCollisions(collisionLayer)) {
                        characterPosition.y = old_y;
                    }
                    if (checkCollisionsPlayer(rectList)) {
                        characterPosition.y = old_y;
                    }
                    if (checkCollisionsStory(oggettiStoria)) {
                        characterPosition.y = old_y;
                    }
                }
                muovi_X = 0;
                muovi_Y = 0;
            }

        } catch (Exception e) {
            System.out.println("Errore movimento Ash, " + e);
        }

    }

    // metodo controllo collisione (prende il layer collisione poi prendo rettangolo
    // per rettangolo e controllo)
    private boolean checkCollisions(MapLayer collisionLayer) {
        try {
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

        } catch (Exception e) {
            System.out.println("Errore checkCollision ash, " + e);
            return false;
        }
    }

    // stessa cosa di quello sopra solo che ho già il rettangolo
    private boolean checkCollisionsPlayer(ArrayList<Rectangle> rectList) {
        try {
            if (rectList != null) {
                for (Rectangle rect : rectList) {
                    if (boxPlayer.overlaps(rect)) {
                        // collisione rilavata
                        return true;
                    }
                }
            }
            // nessuna collisione rilevata
            return false;
        } catch (Exception e) {
            System.out.println("Errore checkCollisionPlayer ash, " + e);
            return false;
        }

    }

    // Metodo per controllare le collisioni solo sugli oggetti con proprietà
    // "considerare" = true
    private boolean checkCollisionsStory(MapLayer oggettiStoria) {
        try {
            // Itera sugli oggetti del livello di collisione
            for (MapObject object : oggettiStoria.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    // Verifica se l'oggetto ha la proprietà "considerare" impostata su true
                    Boolean considerare = (Boolean) object.getProperties().get("considerare");
                    if (considerare != null && considerare) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();

                        // Controlla la collisione con il rettangolo "rect"
                        if (boxPlayer.overlaps(rect)) {
                            // Collisione rilevata
                            return true;
                        }
                    }
                }
            }
            // Nessuna collisione rilevata
            return false;

        } catch (Exception e) {
            System.out.println("Errore checkCollisionsStory, " + e);
            return false;
        }

    }

    public Vector2 getPlayerPosition() {
        return characterPosition;
    }

    public void setPosition(float x, float y) {
        characterPosition.set(x, y);
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

    // funzioni per far muovere il bot
    public void muoviBotBasso() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
        characterPosition.y -= 20f * Gdx.graphics.getDeltaTime();
    }

    public void muoviBotAlto() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
        characterPosition.y += 20f * Gdx.graphics.getDeltaTime();
    }

    public void muoviBotDestra() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
        characterPosition.x += 20f * Gdx.graphics.getDeltaTime();
    }

    public void muoviBotSinistra() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
        characterPosition.x -= 20f * Gdx.graphics.getDeltaTime();
    }

    public void setFermoSinistra() {
        currentAnimation = fermoSinistra.getKeyFrame(stateTime);
    }

    public void setFermoDestra() {
        currentAnimation = fermoDestra.getKeyFrame(stateTime);
    }

    public void setFermoAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(stateTime);
    }

    public void setFermoIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(stateTime);
    }

    public void setCamminaSinistra() {
        currentAnimation = camminaSinistra.getKeyFrame(stateTime);
    }

    public void setCamminaDestra() {
        currentAnimation = camminaDestra.getKeyFrame(stateTime);
    }

    public void setCamminaAvanti() {
        currentAnimation = camminaAvanti.getKeyFrame(stateTime);
    }

    public void setCamminaIndietro() {
        currentAnimation = camminaIndietro.getKeyFrame(stateTime);
    }

    public void setSurfSinistra() {
        currentAnimation = surfSinistra.getKeyFrame(stateTime);
    }

    public void setSurfDestra() {
        currentAnimation = surfDestra.getKeyFrame(stateTime);
    }

    public void setSurfAvanti() {
        currentAnimation = surfAvanti.getKeyFrame(stateTime);
    }

    public void setSurfIndietro() {
        currentAnimation = surfIndietro.getKeyFrame(stateTime);
    }

    public void setSurfSinistraFermo() {
        currentAnimation = surfSinistraFermo.getKeyFrame(stateTime);
    }

    public void setSurfDestraFermo() {
        currentAnimation = surfDestraFermo.getKeyFrame(stateTime);
    }

    public void setSurfAvantiFermo() {
        currentAnimation = surfAvantiFermo.getKeyFrame(stateTime);
    }

    public void setSurfIndietroFermo() {
        currentAnimation = surfIndietroFermo.getKeyFrame(stateTime);
    }

    public void setInAcqua(boolean inAcqua) {
        this.inAcqua = inAcqua;
    }

    public void setDimensionSurf() {
        player_height_current = player_height_surf;
        player_width_current = player_width_surf;
        boxPlayer = boxPlayer_surf;
        inAcqua = true;
    }

    public void setDimensionCammina() {
        player_height_current = player_height;
        player_width_current = player_width;
        boxPlayer = boxPlayer_cammina;
        inAcqua = false;
    }

    public float getCurrentWidht() {
        return player_width_current;
    }

    public float getCurrentHeght() {
        return player_height_current;
    }

    public void dispose() {
        textureAvanti.dispose();
        textureDestra.dispose();
        textureIndietro.dispose();
        textureSinistra.dispose();
    }

}
