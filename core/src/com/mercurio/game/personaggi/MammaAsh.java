package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MammaAsh {
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;

    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;

    private Animation<TextureRegion> characterAnimation;
    private TextureRegion currentAnimation;


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

    private Rectangle boxPlayer;
    private Rectangle boxInteraction;


    public MammaAsh() {

        texture = new Texture (Gdx.files.internal("player/mammaAsh.png"));
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
        
        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

        characterPosition = new Vector2(188, 110);

        player_width = 24; // Larghezza del personaggio
        player_height = 22; // Altezza del personaggio

        //per collisione
        boxPlayer = new Rectangle(characterPosition.x, characterPosition.y, player_width, player_height);

        //per interaction
        boxInteraction = new Rectangle(characterPosition.x, characterPosition.y, player_width, player_height);

        currentAnimation = fermoAvanti.getKeyFrame(0);
        stateTime = 0f;
        
    }

    public TextureRegion getTexture() {
        return currentAnimation;
    }

    public Vector2 getPosition() {
        return characterPosition;
    }

    public float getWidth() {
        return player_width;
    }

    public float getHeight() {
        return player_height;
    }

    public Rectangle getBoxPlayer() {
        return boxPlayer;
    }
    

    
}
