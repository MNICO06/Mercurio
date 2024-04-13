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
    private Label openMenuLabel;
    private Stage stage;
    private boolean menuOpened;
    private boolean xKeyPressed;

    public MenuLabel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createOpenMenuLabel();

        menuOpened = false;
        xKeyPressed = true; // Permette l'apertura iniziale del menu con la label
    }

    
    private void createOpenMenuLabel() {
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

        openMenuLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!menuOpened) {
                    // Se il menu non è aperto, apri il menu
                    apriMenu();
                } else {
                    // Se il menu è aperto, chiudi il menu
                    chiudiMenu();
                }
            }
        });

        stage.addActor(openMenuLabel);
    }
    
    
    private void apriMenu() {
        if (!menuOpened && xKeyPressed) {
        	

        	
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

            menuOpened = true;
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
        if (menuOpened) {
            Array<Actor> actors = stage.getActors();
            Array<Actor> actorsToRemove = new Array<>();

            // Trova gli attori aggiunti dopo l'apertura del menu
            for (Actor actor : actors) {
                if (!actor.equals(openMenuLabel)) {
                    actorsToRemove.add(actor);
                }
            }
            xKeyPressed=true;

            // Rimuovi gli attori in modo ritardato per simulare una chiusura più graduale
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    for (Actor actor : actorsToRemove) {
                        actor.remove();
                    }
                    menuOpened = false;
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
        // Controlla se il tasto X è premuto per aprire o chiudere il menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (!menuOpened && xKeyPressed) {
                // Se il menu non è aperto e il tasto X è abilitato, apri il menu
                apriMenu();
            } else if (menuOpened) {
                // Se il menu è aperto, chiudi il menu
                chiudiMenu();
            }
        }

        // Rendering dello stage
        batch.begin();
        stage.draw();
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
