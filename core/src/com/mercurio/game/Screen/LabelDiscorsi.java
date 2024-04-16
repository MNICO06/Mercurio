package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LabelDiscorsi {
    // Dichiarazioni delle texture
    private Label labelMamma;
    private BitmapFont font;
    private SpriteBatch batch;

    // Variabili per il timer
    private float timer;
    private float intervalloLettera = 0.5f; // Intervallo di tempo tra le lettere
    private int indiceDiscorso;
    private boolean continuaDiscorso;
    private String discorsoMamma = "ciao figliuolo come stai, come mai stai uscendo dove stai andando?";
    private String pezzoDiscorso;
    private ArrayList<String> discorsoDivisoMamma;
    
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
        standardGrigioBordoTexture = new Texture ("sfondo/primoBoxText.png");
        standardArancioneTexture = new Texture ("sfondo/boxTextArancio.png");

        indiceDiscorso = 0;
        continuaDiscorso = true;
        
        
        int left = 10;
        int right = 10;
        int top = 10;
        int bottom = 10;
        NinePatch backgroundPatch = new NinePatch(standardArancioneTexture, left, right, top, bottom);

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(backgroundPatch);
        
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");

        style.font.getData().setScale(2.5f);
        
        style.background = backgroundDrawable;

        
        labelMamma = new Label(discorsoMamma, style);
        labelMamma.setPosition(280, 10); // Imposta la posizione della label sulla mappa
        labelMamma.setWidth(400); // Imposta la larghezza desiderata della label
        labelMamma.setHeight(75); // Imposta l'altezza desiderata della label
        labelMamma.setWrap(true);

    }

    public void renderDiscMamma() {
        batch.begin();
        labelMamma.draw(batch, 1); // Disegna la label nello SpriteBatch
        batch.end();
    }
}
