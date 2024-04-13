package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class MenuLabel {

    private SpriteBatch batch;
    private BitmapFont font;
    private Label label;
    private Stage stage;
    private boolean menuOpened;
    private boolean menuAperto = true;
    private Label openMenuLabel;
    private boolean xKeyPressed = true;
    private NinePatch backgroundPatch;
    
    public MenuLabel() {
    	batch = new SpriteBatch();
        stage = new Stage();
        menuAperto = false;
        
        // Carica il font personalizzato da file .fnt
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        Skin skin = new Skin();
        skin.add("custom-font", font);

        Texture backgroundTexture = new Texture("sfondo/x.png");

        // Crea un NinePatch dalle texture (specificando le dimensioni dei bordi)
        int left = 10;
        int right = 10;
        int top = 10;
        int bottom = 10;
        NinePatch backgroundPatch = new NinePatch(backgroundTexture, left, right, top, bottom);

        // Crea uno sfondo NinePatchDrawable per il menù
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(backgroundPatch);

        // Imposta lo stile per le label
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");
        style.font.getData().setScale(2.5f);
        style.background = backgroundDrawable;

        // Creazione della label per aprire il menù
        openMenuLabel = new Label("", style);
        openMenuLabel.setPosition(938, 20); // Imposta la posizione della label sulla mappa

        openMenuLabel.setPosition(938, 10); // Imposta la posizione della label sulla mappa
        openMenuLabel.setWidth(75); // Imposta la larghezza desiderata della label
        openMenuLabel.setHeight(75); // Imposta l'altezza desiderata della label
        openMenuLabel.setWrap(true);
        
        
        // Aggiungi un listener per aprire il menù quando la label viene cliccata
        openMenuLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!menuOpened) {
                    apriMenu();
                    menuOpened = true;
                }
            }
        });
        
        
        // Aggiungi gli attori allo stage
        stage.addActor(openMenuLabel);

        // Imposta l'input processor per gestire il click del mouse
        Gdx.input.setInputProcessor(stage);

    }
    
    
    private void apriMenu() {
        if (!menuAperto) {
            // Aggiungi lo sfondo del menù
            Texture backgroundTexture = new Texture("sfondo/sfondo.png");
            Image menuBackground = new Image(backgroundTexture);
            menuBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(menuBackground);

            // Aggiungi le label del menù
            float labelY = Gdx.graphics.getHeight() - 100; // Posizione iniziale delle label
            addMenuItem("Borsa", labelY);
            addMenuItem("Pokédex", labelY - 50);
            addMenuItem("Pokémon", labelY - 100);
            addMenuItem("Salva", labelY - 150);

            menuAperto = true;
            xKeyPressed = false; // Disabilita temporaneamente il tasto X dopo l'apertura del menu
            
            addCloseLabel();
            
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    xKeyPressed = true; // Riabilita il tasto X dopo un breve ritardo
                }
            }, 0.1f);
        }
    }
    
    
    private void addCloseLabel() {
        // Utilizza lo stesso stile della label per aprire il menù
        Label.LabelStyle style = openMenuLabel.getStyle();

        // Crea una nuova label "Chiudi" con lo stesso stile, testo vuoto e sfondo X.png
        Label closeLabel = new Label("", style);
        closeLabel.setPosition(openMenuLabel.getX(), openMenuLabel.getY());
        closeLabel.setWidth(openMenuLabel.getWidth());
        closeLabel.setHeight(openMenuLabel.getHeight());
        closeLabel.setWrap(openMenuLabel.getWrap());

        // Imposta lo sfondo della label "Chiudi" con lo stesso sfondo della label per aprire il menù
        Texture backgroundTexture = new Texture("sfondo/x.png");
        int left = 10;
        int right = 10;
        int top = 10;
        int bottom = 10;
        NinePatch backgroundPatch = new NinePatch(backgroundTexture, left, right, top, bottom);
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(backgroundPatch);
        closeLabel.getStyle().background = backgroundDrawable;

        // Aggiungi un listener per chiudere il menù quando la label "Chiudi" viene cliccata
        closeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chiudiMenu();
                menuOpened = false;
            }
        });

        // Aggiungi la label "Chiudi" allo stage
        stage.addActor(closeLabel);
    }
    

    private void chiudiMenu() {
        if (menuAperto) {
            Array<Actor> actors = stage.getActors();
            Array<Actor> actorsToRemove = new Array<>();

            // Trova gli attori aggiunti dopo l'apertura del menu
            for (Actor actor : actors) {
                if (!actor.equals(openMenuLabel)) {
                    actorsToRemove.add(actor);
                }
            }

            // Rimuovi gli attori in modo ritardato per simulare una chiusura più graduale
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    for (Actor actor : actorsToRemove) {
                        actor.remove();
                    }
                    menuAperto = false;
                }
            }, 0.1f); // Ritardo di 0.3 secondi prima di rimuovere gli attori
        }
    }

    private void addMenuItem(String text, float posY) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.font.getData().setScale(2.0f);

        Label menuItemLabel = new Label(text, style);
        menuItemLabel.setPosition(100, posY);
        stage.addActor(menuItemLabel);
    }

    public void render() {
    	if (Gdx.input.isKeyPressed(Input.Keys.X) && xKeyPressed) {
            if (!menuOpened) {
                // Se il menu non è aperto, apri il menu
                apriMenu();
                menuOpened = true;
                xKeyPressed = false; // Disabilita temporaneamente il tasto X
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        xKeyPressed = true; // Riabilita il tasto X dopo un breve ritardo
                    }
                }, 0.3f);
            } else {
                // Se il menu è aperto, chiudi il menu
                chiudiMenu();
                menuOpened = false;
            }
        }
    	
        batch.begin();
        stage.draw(); // Disegna gli attori dello stage
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
