package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Bot {
    protected float player_width;
    protected float player_height;
    protected TextureRegion[] indietro;
    protected TextureRegion[] sinistra;
    protected TextureRegion[] destra;
    protected TextureRegion[] avanti;
    protected TextureRegion[][] tmp;
    protected Animation<TextureRegion> camminaSinistra;
    protected Animation<TextureRegion> camminaDestra;
    protected Animation<TextureRegion> camminaAvanti;
    protected Animation<TextureRegion> camminaIndietro;
    protected Animation<TextureRegion> fermoSinistra;
    protected Animation<TextureRegion> fermoDestra;
    protected Animation<TextureRegion> fermoAvanti;
    protected Animation<TextureRegion> fermoIndietro;
    protected Animation<TextureRegion> characterAnimation;
    protected TextureRegion currentAnimation;
    protected Rectangle boxPlayer;
    protected Rectangle boxBlocca;
    protected Vector2 characterPosition;
    protected float camminataFrame_speed;
    protected float stateTime;
    protected float xPunto;
    protected float yPunto;

    /* -y = il personaggio si trova sotto
     * y = il personaggio si trova sopra
     * 
     * -x = il personaggio si trova a sinstra
     * x = il personaggio si trova a destra
    */
    protected String direzione;

    public Bot(float width, float height, String texturePath, float xPunto, float yPunto) {
        this.xPunto = xPunto;
        this.yPunto = yPunto;
        player_width = width;
        player_height = height;

        Texture texture = new Texture(Gdx.files.internal(texturePath));
        tmp = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 4);
        indietro = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        destra = new TextureRegion[3];
        avanti = new TextureRegion[3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    if (j == 0) {
                        avanti[0] = tmp[i][j];
                    } else if (j == 1) {
                        destra[0] = tmp[i][j];
                    } else if (j == 2) {
                        avanti[1] = tmp[i][j];
                    }
                } else if (i == 1) {
                    if (j == 0) {
                        sinistra[0] = tmp[i][j];
                    } else if (j == 1) {
                        destra[1] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[0] = tmp[i][j];
                    }
                } else if (i == 2) {
                    if (j == 0) {
                        sinistra[1] = tmp[i][j];
                    } else if (j == 1) {
                        destra[2] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[1] = tmp[i][j];
                    }
                } else if (i == 3) {
                    if (j == 0) {
                        sinistra[2] = tmp[i][j];
                    } else if (j == 1) {
                        avanti[2] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[2] = tmp[i][j];
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
        
        stateTime = 0f;
        currentAnimation = fermoIndietro.getKeyFrame(0);
        characterPosition = new Vector2();
    }

    public float getHeight() {
        return player_height;
    }

    public float getWidth() {
        return player_width;
    }

    public TextureRegion getCurrentAnimation() {
        return currentAnimation;
    }

    public Vector2 getPosition() {
        return characterPosition;
    }

    public Rectangle getBoxPlayer() {
        return boxPlayer;
    }

    public void setPosition(float x, float y) {
        characterPosition.set(x, y);
        boxPlayer = new Rectangle(x + player_width / 4 - 2, y - 2, player_width / 2 + 2, player_height / 6 + 6);
    }

    public void setBoxBlocca(Rectangle boxBlocca) {
        this.boxBlocca = boxBlocca;
    }

    public Rectangle getBoxBlocca() {
        return boxBlocca;
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

    public void setDirezione(String direzione) {
        this.direzione = direzione;
    }

    public String getDirezione() {
        return direzione;
    }

    public float getPuntoX() {
        return xPunto;
    }

    public float getPuntoY() {
        return yPunto;
    }

    public void setX(float x) {
        characterPosition.x = x;
    }

    public void setY(float y) {
        characterPosition.y = y;
    }
}
