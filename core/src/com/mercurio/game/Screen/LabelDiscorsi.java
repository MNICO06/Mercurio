package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LabelDiscorsi {
    // Dichiarazioni delle texture
    private Label label;
    private BitmapFont font;
    private SpriteBatch batch;
    
    private Texture standardGrigioBordoTexture;
    private Texture standardGrigioTexture;
    private Texture standardRossoTexture;
    private Texture standardBluTexture;
    private Texture standardVerdeTexture;
    private Texture standardArancioneTexture;
    private Texture standardViolaTexture;
    private Texture nuvolaTexture;
    private Texture stranoGialloTexture;
    private Texture nuvolaRosaTexture;
    private Texture gialloFigoTexture;
    private Texture grigioFigoTexture;
    private Texture grigioScuroTexture;
    private Texture cianoTexture;
    private Texture rosaTexture;
    private Texture siepeTexture;
    private Texture pergamenaTexture;
    private Texture muroTexture;
    private Texture tappetoTexture;
    private Texture erbettaTexture;

    public LabelDiscorsi() {
        batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        Skin skin = new Skin();
        skin.add("custom-font", font);

        // Carica la texture originale
        Texture texture = new Texture("sfondo/q.jpg");
        standardGrigioBordoTexture = new Texture ("sfondo/primoBoxText.png");
        
        int left = 10;
        int right = 10;
        int top = 10;
        int bottom = 10;
        NinePatch backgroundPatch = new NinePatch(standardGrigioBordoTexture, left, right, top, bottom);

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(backgroundPatch);
        
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");

        style.font.getData().setScale(2.5f);
        
        style.background = backgroundDrawable;
        
        label = new Label("funziona considerate che ora è fisso ed è una prova", style);
        label.setPosition(280, 10); // Imposta la posizione della label sulla mappa
        label.setWidth(400); // Imposta la larghezza desiderata della label
        label.setHeight(75); // Imposta l'altezza desiderata della label
        label.setWrap(true);
    
    }

    public void render() {
        batch.begin();
        label.draw(batch, 1); // Disegna la label nello SpriteBatch
        batch.end();
    }
}
