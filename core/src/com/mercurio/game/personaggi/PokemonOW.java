package com.mercurio.game.personaggi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.Screen.MercurioMain;

public class PokemonOW {
    private TextureRegion[] indietro = new TextureRegion[2];
    private TextureRegion[] sinistra = new TextureRegion[2];
    private TextureRegion[] destra  = new TextureRegion[2];
    private TextureRegion[] avanti = new TextureRegion[2];

    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;

    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> camminaAvanti;
    private Animation<TextureRegion> camminaIndietro;

    private TextureRegion currentAnimation;

    private float stateTime;
    private Vector2 characterPosition;

    private int poke_width;
    private int poke_height;

    private float camminataFrame_speed = 0.14f;

    private Rectangle boxPoke;

    private MercurioMain game;
    private GameAsset asset;

    public PokemonOW(MercurioMain game) {
        this.game = game;

        poke_width = 18;
        poke_height = 24;
    }

    public void creaPoke(String pokemon) {
        try{
            Texture texture = new Texture ("pokemonOverworld/" + pokemon + "OW.png");
            TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 4);

            //salvataggio delle immagini
            indietro[0] = tmp[2][0];
            indietro[1] = tmp[3][0];

            sinistra[0] = tmp[0][1];
            sinistra[1] = tmp[1][1];

            destra[0] = tmp[2][1];
            destra[1] = tmp[3][1];

            avanti[0] = tmp[0][0];
            avanti[1] = tmp[1][0];

            fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
            fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
            fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
            fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

            camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
            camminaDestra = new Animation<>(camminataFrame_speed, destra);
            camminaAvanti = new Animation<>(camminataFrame_speed, avanti);
            camminaIndietro = new Animation<>(camminataFrame_speed, indietro);


        } catch (Exception e) {
            System.out.println("Errore creaPokemon pokemonOW, "+ e);
        }
    }

    public void setPosition(float x, float y) {
        characterPosition.set(x, y);
    }
    public void setX(float x) {
        characterPosition.x = x;
    }
    public void setY(float y) {
        characterPosition.y = y;
    }

    //fuzioni per settare le animazioni
    public void setFermoAvanti(float stateTime) {
        currentAnimation = fermoAvanti.getKeyFrame(stateTime, true);
    }
    public void setFermoSinistra(float stateTime) {
        currentAnimation = fermoSinistra.getKeyFrame(stateTime, true);
    }
    public void setFermoDestra(float stateTime) {
        currentAnimation = fermoDestra.getKeyFrame(stateTime, true);
    }
    public void setFermoIndietro(float stateTime) {
        currentAnimation = fermoIndietro.getKeyFrame(stateTime, true);
    }
    public void setCamminaSinistra(float stateTime) {
        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
    }
    public void setCamminaDestra(float stateTime) {
        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
    }
    public void setCamminaAvanti(float stateTime) {
        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
    }
    public void setCamminaIndietro(float stateTime) {
        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
    }
    public TextureRegion getCurrentAnimation() {
        return currentAnimation;
    }
    public float getWidth() {
        return poke_width;
    }
    public float getHeight() {
        return poke_height;
    }
    public Vector2 getCharacterPosition() {
        return characterPosition;
    }
}
