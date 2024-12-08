package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetsBot;
import com.mercurio.game.Screen.MercurioMain;

public class Rivale {
    private float player_width;
    private float player_height;
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;
    private TextureRegion[][] tmp;
    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> camminaAvanti;
    private Animation<TextureRegion> camminaIndietro;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;
    private Animation<TextureRegion> characterAnimation;
    private TextureRegion currentAnimation;
    private Rectangle boxPlayer;
    private Vector3 characterPosition;
    private float camminataFrame_speed = 0.14f;
    private float stateTime;

    private float xBase;
    private float yBase;

    private GameAsset asset;

    public Rivale(MercurioMain game) {
        this.asset = game.getGameAsset();

        Texture texture = asset.getBot(AssetsBot.RIVALE);
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);

        indietro = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        destra = new TextureRegion[3];
        avanti = new TextureRegion[3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0) {
                    if (j == 0) {
                        indietro[0] = tmp[i][j];
                    } else if (j == 1) {
                        indietro[1] = tmp[i][j];
                    } else if (j == 3) {
                        indietro[2] = tmp[i][j];
                    }
                } else if (i == 1) {
                    if (j == 0) {
                        avanti[0] = tmp[i][j];
                    } else if (j == 1) {
                        avanti[1] = tmp[i][j];
                    } else if (j == 3) {
                        avanti[2] = tmp[i][j];
                    }
                } else if (i == 2) {
                    if (j == 0) {
                        sinistra[0] = tmp[i][j];
                    } else if (j == 1) {
                        sinistra[1] = tmp[i][j];
                    } else if (j == 3) {
                        sinistra[2] = tmp[i][j];
                    }
                } else if (i == 3) {
                    if (j == 0) {
                        destra[0] = tmp[i][j];
                    } else if (j == 1) {
                        destra[1] = tmp[i][j];
                    } else if (j == 3) {
                        destra[2] = tmp[i][j];
                    }
                }
            }
        }

        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[2]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);
        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        camminaAvanti = new Animation<>(camminataFrame_speed, avanti);
        camminaIndietro = new Animation<>(camminataFrame_speed, indietro);

        player_width = 24; // Larghezza del personaggio
        player_height = 24; // Altezza del personaggio

        stateTime = 0f;
        currentAnimation = camminaIndietro.getKeyFrame(0);
        characterPosition = new Vector3();

    }

    //funzioni per far muovere il bot
    public void muoviBotBasso() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
        characterPosition.y -= 60f * Gdx.graphics.getDeltaTime();
    }
    public void muoviBotAlto() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
        characterPosition.y += 60f * Gdx.graphics.getDeltaTime();
    }
    public void muoviBotDestra() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
        characterPosition.x += 60f * Gdx.graphics.getDeltaTime();
    }
    public void muoviBotSinistra() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
        characterPosition.x -= 60f * Gdx.graphics.getDeltaTime();
    }

    //funzioni per settare animazione bot
    public void setFermoSinistra() {
        currentAnimation = fermoSinistra.getKeyFrame(stateTime, true);
    }
    public void setFermoDestra() {
        currentAnimation = fermoDestra.getKeyFrame(stateTime, true);
    }
    public void setFermoAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(stateTime, true);
    }
    public void setFermoIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(stateTime, true);
    }
    public void setCamminaSinistra() {
        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
    }
    public void setCamminaDestra() {
        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
    }
    public void setCamminaAvanti() {
        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
    }
    public void setCamminaIndietro() {
        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
    }


    public void setPosition(float x, float y) {
        characterPosition.set(x, y, 0);
        boxPlayer = new Rectangle(x + player_width / 4 - 2, y - 2, player_width / 2 + 2, player_height / 6 + 6);
    }

    public Rectangle getBox() {
        return boxPlayer;
    }
    public TextureRegion getTexture() {
        return currentAnimation;
    }
    public Vector3 getPosition() {
        return characterPosition;
    }
    public float getWidth() {
        return player_width;
    }
    public float getHeight() {
        return player_height;
    }
    public void updateStateTime() {
        stateTime += Gdx.graphics.getDeltaTime();
    }
    public float getStateTime() {
        return stateTime;
    }
}
