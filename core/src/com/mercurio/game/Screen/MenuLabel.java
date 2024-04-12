package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MenuLabel {

    private SpriteBatch batch;
    private BitmapFont font;
    private Label label;

    public MenuLabel() {
        batch = new SpriteBatch();

        // Carica il font personalizzato da file .fnt
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        
        Skin skin = new Skin();
        skin.add("custom-font", font);

        Texture backgroundTexture = new Texture("sfondo/q.jpg");

     // Crea un NinePatch dalle texture (specificando le dimensioni dei bordi)
        int left = 10;
        int right = 10;
        int top = 10;
        int bottom = 10;
        NinePatch backgroundPatch = new NinePatch(backgroundTexture, left, right, top, bottom);

        // Crea uno sfondo NinePatchDrawable per la label
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(backgroundPatch);
        

        
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");

        style.font.getData().setScale(2.5f);
        
        style.background = backgroundDrawable;
        
        label = new Label("Menu", style);
        label.setPosition(938, 10); // Imposta la posizione della label sulla mappa
        label.setWidth(75); // Imposta la larghezza desiderata della label
        label.setHeight(75); // Imposta l'altezza desiderata della label
        label.setWrap(true);
    }

    public void render() {
        batch.begin();
        label.draw(batch, 1); // Disegna la label nello SpriteBatch
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        label.getStyle().background = null; // Libera la risorsa dello sfondo
        label = null;
    }
}
