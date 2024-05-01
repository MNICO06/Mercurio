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
    private Rectangle boxInteractionVerticale;
    private Rectangle boxInteractionOrizzontaleDestro;
    private Rectangle boxInteractionOrizzontaleSinistro;


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
        
        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

        characterPosition = new Vector2(188, 110);

        player_width = 24; // Larghezza del personaggio
        player_height = 22; // Altezza del personaggio

        //per collisione
        boxPlayer = new Rectangle(characterPosition.x+player_width/4 - 2, characterPosition.y-2, player_width/2 + 2, player_height/6 + 6);
        //per interaction
        boxInteractionVerticale = new Rectangle(characterPosition.x+player_width/4 - 2, characterPosition.y - 20, player_width/2 + 2, player_height - 8);
        boxInteractionOrizzontaleDestro = new Rectangle(characterPosition.x+player_width/4 + 12, characterPosition.y -2, player_width/2 + 2, player_height - 8);
        boxInteractionOrizzontaleSinistro = new Rectangle(characterPosition.x+player_width/4 - 15, characterPosition.y -2, player_width/2 + 2, player_height - 8);

        currentAnimation = fermoAvanti.getKeyFrame(0);
        stateTime = 0f;
        
    }


    public void setSinstra() {
        currentAnimation = fermoSinistra.getKeyFrame(0);
    }
    public void setDestra() {
        currentAnimation = fermoDestra.getKeyFrame(0);
    }
    public void setIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(0);
    }
    public void setAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(0);
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
    
    //metodi che mi servono per far spostare la madre
    public Rectangle getInterBoxVert() {
        return boxInteractionVerticale;
    }
    public Rectangle getInterBoxOrizSx() {
        return boxInteractionOrizzontaleSinistro;
    }
    public Rectangle getInterBoxOrizDx() {
        return boxInteractionOrizzontaleDestro;
    }

    public void dispose() {
        texture.dispose();
    }
    
}
