package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BotNormali {
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;

    //_TeenagerM
    private Animation<TextureRegion> camminaSinistra_TeenagerM;
    private Animation<TextureRegion> camminaDestra_TeenagerM;
    private Animation<TextureRegion> camminaAvanti_TeenagerM;
    private Animation<TextureRegion> camminaIndietro_TeenagerM;
    private Animation<TextureRegion> fermoSinistra_TeenagerM;
    private Animation<TextureRegion> fermoDestra_TeenagerM;
    private Animation<TextureRegion> fermoAvanti_TeenagerM;
    private Animation<TextureRegion> fermoIndietro_TeenagerM;
    private Animation<TextureRegion> characterAnimation_TeenagerM;
    private TextureRegion currentAnimation_TeenagerM;


    private float stateTime;
    private Vector2 characterPosition;

    private Texture texture;

    private boolean movingLeft = false;
    private boolean movingRight = false;

    private int player_width;
    private int player_height;

    private float speed_Camminata_orizontale = 50;
    private float speed_Camminata_verticale = 40;

    private float camminataFrame_speed = 0.14f;

    public BotNormali() {

        //_TeenagerM
        texture = new Texture (Gdx.files.internal("player/Teenager M.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
        indietro = new TextureRegion[4];
        sinistra = new TextureRegion[4];
        destra = new TextureRegion[4];
        avanti = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j< 4; j++) {
                if (i == 0) {
                    indietro[j] = tmp [i][j];
                }
                else if (i == 1) {
                    sinistra[j] = tmp [i][j];
                }
                else if (i == 2) {
                    destra[j] = tmp [i][j];
                }
                else if (i == 3) {
                    avanti[j] = tmp [i][j];
                }
            }
        }
        fermoSinistra_TeenagerM = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra_TeenagerM = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti_TeenagerM = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro_TeenagerM = new Animation<>(camminataFrame_speed, indietro[0]);
        camminaSinistra_TeenagerM = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra_TeenagerM = new Animation<>(camminataFrame_speed, destra);
        camminaAvanti_TeenagerM = new Animation<>(camminataFrame_speed, avanti);
        camminaIndietro_TeenagerM = new Animation<>(camminataFrame_speed, indietro);


        
    }

}
