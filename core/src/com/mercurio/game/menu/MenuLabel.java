package com.mercurio.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.Screen.MercurioMain;

public class MenuLabel{

    private Squadra squadra;
	private SpriteBatch batch;
    private BitmapFont font;
    public static Label openMenuLabel;
    private static Stage stage;
    private boolean menuOpened;
    private boolean xKeyPressed;
    private Borsa borsa;
    private MercurioMain game;
    
    public MenuLabel(MercurioMain game) {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        this.game=game;
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
            float Xlabel = 240;
            float YLabel = 156;
            // Aggiungi lo sfondo del menù
            Texture backgroundTexture = new Texture("sfondo/sfondo.png");
            Image menuBackground = new Image(backgroundTexture);
            menuBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(menuBackground);

            // Aggiungi le label del menù con sfondo personalizzato e testo
            float labelY = Gdx.graphics.getHeight() - 205; // Posizione iniziale delle label
            float labelX = Gdx.graphics.getHeight() - 500; // Posizione iniziale delle label
            addMenuItem(labelY, labelX, "sfondo/pokedex.png", "Pokédex", Xlabel, YLabel, () -> {
                apriPokedex();
            }); 
            addMenuItem(labelY - 231, labelX, "sfondo/pokemon.png", "Pokémon", Xlabel, YLabel, () -> {
            	apriPokemon();
            }); 
            addMenuItem(labelY - 462, labelX, "sfondo/borsa.png", "Borsa", Xlabel, YLabel, () -> {
            	apriBorsa();
            }); 
            addMenuItem(labelY, labelX + 400, "sfondo/medaglie.png", "Medaglie", Xlabel, YLabel, () -> {
            	apriMedaglie();
            }); 
            addMenuItem(labelY - 231, labelX + 400, "sfondo/salva.png", "Salva", Xlabel, YLabel, () -> {
                salvataggio();
            }); 
            addMenuItem(labelY - 462, labelX + 400, "sfondo/spegni.png", "Spegni", Xlabel, YLabel, () -> {
                spegnimento();
            }); 

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

    private void apriPokedex() {
    	System.out.println("Pokedex aperto");
    }
    
	private void apriPokemon() {
        squadra = new Squadra(getStage(),false,null,this, false);
	}
	    
	private void apriBorsa() {
	    borsa = new Borsa(getStage(),false,null);
	}
	
	private void apriMedaglie() {
		System.out.println("Medaglie aperte");
	}
	
	private void salvataggio() {
        salva();
		System.out.println("Salvato");
	}
	
	private void spegnimento() {
        System.out.println("Spegnimento in corso...");
    
        // Termina tutti i thread attivi
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        Thread[] threads = new Thread[rootGroup.activeCount()];
        rootGroup.enumerate(threads);
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }
    
        // Chiudi il batch
        batch.dispose();
        // Rimuovi tutti gli attori dallo stage
        stage.clear();
        // Chiudi l'applicazione
        Gdx.app.exit();
        // Chiudi l'applicazione
        System.exit(0);
    }
    
    private void salva(){
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/datiPosizione.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
    

        json.remove("x");
        json.addChild("x", new JsonValue((game.getPlayer().getPlayerPosition().x)));

        json.remove("y");
        json.addChild("y", new JsonValue((game.getPlayer().getPlayerPosition().y)));

        json.remove("luogo");
        json.addChild("luogo", new JsonValue(game.getLuogo()));

        json.remove("screen");
        json.addChild("screen", new JsonValue(game.getScreenString()));
        // Scrivi il JSON aggiornato nel file mantenendo la formattazione
        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

        game.copiaJson("ashJson/borsa.json","assets/jsonSalvati/borsaSalvato.json");
        game.copiaJson("ashJson/squadra.json","assets/jsonSalvati/squadraSalvato.json");
        game.copiaJson("bots/bots.json","assets/jsonSalvati/botsSalvato.json");
        game.copiaJson("ashJson/datiGenerali.json", "assets/jsonSalvati/datiGeneraliSalvato.json");
        game.copiaJson("ashJson/box.json", "assets/jsonSalvati/boxSalvato.json");
    }

	public static Stage getStage() {
	    return stage;
	}
    
    // Metodo per aggiungere una label con uno sfondo personalizzato, testo e dimensioni specifiche
    private void addMenuItem(float y, float x, String backgroundImagePath, String labelText, float width, float height, final Runnable action) {
        // Aggiungi sfondo come immagine
        Texture backgroundTexture = new Texture(backgroundImagePath);
        Image menuBackground = new Image(backgroundTexture);
        menuBackground.setSize(width, height);
        menuBackground.setPosition(x, y);
        stage.addActor(menuBackground);
        
     // Aggiungi gestore di eventi di input per eseguire l'azione quando l'immagine viene premuta
        menuBackground.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Esegui l'azione associata a questa label quando viene premuta
                action.run();
            }
        });
        
        // Aggiungi testo sotto l'immagine come label
        Label.LabelStyle labelStyle = new Label.LabelStyle(); // Stile predefinito
        labelStyle.font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        Label label = new Label(labelText, labelStyle);
        labelStyle.font.getData().setScale(5f);
        label.setPosition(x + (width - label.getPrefWidth()) / 2, y - label.getPrefHeight() + 20); // Posizione testo sotto l'immagine
        stage.addActor(label);
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
            }, 0.1f); // Ritardo di 0.1 secondi prima di rimuovere gli attori
        }
    }

 

    public void render() {
        // Controlla se il tasto X è premuto per aprire o chiudere il menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && !game.getIsInMovement()) {
            if (!menuOpened && xKeyPressed) {
                // Se il menu non è aperto e il tasto X è abilitato, apri il menu
                apriMenu();
            } else if (menuOpened) {
                // Se il menu è aperto, chiudi il menu
                chiudiMenu();
            }
        }

        // Rendering dello stage
        if (borsa != null) {
            borsa.render();
        }

        // Rendering dello stage
        if (squadra != null) {
            squadra.render();
        }


        batch.begin();
        stage.draw();
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }


    public void closeSquadra() {
        Gdx.input.setInputProcessor(stage);
        squadra = null;
    }
}