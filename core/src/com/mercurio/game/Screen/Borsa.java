package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Borsa {

    private Array<Actor> inventoryItemActors = new Array<>(); // Array per tracciare gli attori degli oggetti dell'inventario
    private int currentPageIndex = 0;
    private TextureRegion frame;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Array<Actor> borsaActors; // Array per tracciare gli attori della borsa
    private TextureRegion[] cure;
    private TextureRegion[] ball;
    private TextureRegion[] key; 
    private TextureRegion[] mt;
    
    private Texture textureCure;
    private Texture textureBall;
    private Texture textureKey;
    private Texture textureMT;
    
    private float cambioFrame_speed = 0.14f;
    
    private Animation<TextureRegion> cambiaCure;
    private Animation<TextureRegion> cambiaBall;
    private Animation<TextureRegion> cambiaKey;
    private Animation<TextureRegion> cambiaMT;
    private Animation<TextureRegion> nsCure; /* ns sta per not selected*/
    private Animation<TextureRegion> nsBall;
    private Animation<TextureRegion> nsKey;
    private Animation<TextureRegion> nsMT;

    private Image labelBall;
    private Image labelCure;
    private Image labelMT;
    private Image labelKey;

    private float stateTime;
    
    public Borsa(Stage stage) {
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        this.stage = stage;
        this.borsaActors = new Array<>(); // Inizializza l'array degli attori della borsa

        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    private void createUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Add background and close button
        Texture backgroundTexture = new Texture("sfondo/sfondo.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
    
        // Load textures and create TextureRegions
        textureCure = new Texture(Gdx.files.internal("sfondo/cureBag.png"));
        textureBall = new Texture(Gdx.files.internal("sfondo/ballBag.png"));
        textureKey = new Texture(Gdx.files.internal("sfondo/keyBag.png"));
        textureMT = new Texture(Gdx.files.internal("sfondo/mtBag.png"));
    
        cure = new TextureRegion[3];
        ball = new TextureRegion[3];
        key = new TextureRegion[3];
        mt = new TextureRegion[3];
    
        int regionHeight = textureCure.getHeight() / 3; // Assuming each texture has 3 rows
    
        for (int i = 0; i < 3; i++) {
            cure[i] = new TextureRegion(textureCure, 0, i * regionHeight, textureCure.getWidth(), regionHeight);
            ball[i] = new TextureRegion(textureBall, 0, i * regionHeight, textureBall.getWidth(), regionHeight);
            key[i] = new TextureRegion(textureKey, 0, i * regionHeight, textureKey.getWidth(), regionHeight);
            mt[i] = new TextureRegion(textureMT, 0, i * regionHeight, textureMT.getWidth(), regionHeight);
        }
        
        cambiaCure = new Animation<>(cambioFrame_speed, cure);
        cambiaBall = new Animation<>(cambioFrame_speed, ball);
        cambiaKey = new Animation<>(cambioFrame_speed, key);
        cambiaMT = new Animation<>(cambioFrame_speed, mt);

        nsCure = new Animation<>(cambioFrame_speed, cure[0]);
        nsBall = new Animation<>(cambioFrame_speed, ball[0]);
        nsKey = new Animation<>(cambioFrame_speed, key[0]);
        nsMT = new Animation<>(cambioFrame_speed, mt[0]);
    
        // Create Image actors using TextureRegions
         labelCure = new Image(cure[0]);
        labelCure.setSize(100, 100);
        labelCure.setPosition(70, screenHeight - 150);
        stage.addActor(labelCure);
    
         labelBall = new Image(ball[0]);
        labelBall.setSize(100, 100);
        labelBall.setPosition(200, screenHeight - 150);
        stage.addActor(labelBall);
    
         labelKey = new Image(key[0]);
        labelKey.setSize(100, 100);
        labelKey.setPosition(330, screenHeight - 150);
        stage.addActor(labelKey);
    
         labelMT = new Image(mt[0]);
        labelMT.setSize(100, 100);
        labelMT.setPosition(460, screenHeight - 150);
        stage.addActor(labelMT);
    
        // Handle click events on labels
        labelCure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelCure, cambiaCure);
                deactivateAnimations(labelBall, labelKey, labelMT);
                showInventoryItems();
            }
        });
    
        labelBall.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelBall, cambiaBall);
                deactivateAnimations(labelCure, labelKey, labelMT);
                clearInventoryItems(); //solo per prova, questo comando va poi tolto
            }
        });
    
        labelKey.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelKey, cambiaKey);
                deactivateAnimations(labelCure, labelBall, labelMT);
                clearInventoryItems(); //solo per prova, questo comando va poi tolto
            }
        });
    
        labelMT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelMT, cambiaMT);
                deactivateAnimations(labelCure, labelBall, labelKey);
                clearInventoryItems(); //solo per prova, questo comando va poi tolto
            }
        });
    
        
    
        Texture closeButtonTexture = new Texture("sfondo/x.png");
        NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
        NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);
    
        Label closeLabel = new Label("", new Label.LabelStyle(font, null));
        closeLabel.setWidth(75);
        closeLabel.setHeight(75);
        closeLabel.setPosition(screenWidth - 100, screenHeight - 100);
        closeLabel.getStyle().background = closeButtonDrawable;
        closeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        stage.addActor(closeLabel);
    
        // Add actors to the borsaActors array
        borsaActors.addAll(labelCure, labelBall, labelKey, labelMT, background, closeLabel);

    }
    

    private void close() {
        clearInventoryItems();
        // Rimuovi gli attori della borsa in modo ritardato per simulare una chiusura graduale
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Actor actor : borsaActors) {
                    actor.remove();
                }
                borsaActors.clear(); // Pulisci l'array degli attori della borsa dopo la rimozione
            }
        }, 0.1f); // Ritardo di 0.1 secondi prima di rimuovere gli attori
    }
    
    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        stateTime = 1f;
        frame = animation.getKeyFrame(stateTime, true);
        image.setDrawable(new TextureRegionDrawable(frame));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stateTime = 2f;
                frame = animation.getKeyFrame(stateTime, true);
                image.setDrawable(new TextureRegionDrawable(frame));
            }
        }, 0.2f);
    }
    


    private void deactivateAnimations(Image... images) {
        for (Image image : images) {
            // Ottieni l'animazione associata all'immagine
            Animation<TextureRegion> animation = null;
            if (image == labelCure) {
                animation = nsCure;
            } else if (image == labelBall) {
                animation = nsBall;
            } else if (image == labelKey) {
                animation = nsKey;
            } else if (image == labelMT) {
                animation = nsMT;
            }
    
            // Se l'animazione esiste, reimposta l'immagine al frame 0 dell'animazione
            if (animation != null) {
                TextureRegion frame = animation.getKeyFrame(0); // Ottieni il frame 0 dell'animazione
                image.setDrawable(new TextureRegionDrawable(frame));
            }
        }
    }

    private void showInventoryItems() {

        clearInventoryItems();
        // Simulazione di un inventario
        String[][] inventoryItems = {
            {"Pokeball", "10"},
            {"Pozione", "5"},
            {"Acqua", "3"},
            {"MT", "1"},
            {"RevitalizzanteMax", "2"},
            {"CaramellaRara", "4"},
            {"MegaBall", "4"},
            {"UltraBall", "8"},
            {"Campana", "8"},
            {"MasterBall", "309"}
        };
    
        // Calcola la posizione di partenza per visualizzare le etichette

        float itemWidth = 65;
        float itemHeight = 65;
        float padding = 50;
    
        // Visualizza al massimo 4 oggetti contemporaneamente
        int maxNumPages = inventoryItems.length/4;

        if (maxNumPages*4<inventoryItems.length){
            maxNumPages++;
        }
        int maxItemsToShow = 4;
        int numItems = Math.min(inventoryItems.length, maxItemsToShow); // Numero di oggetti da mostrare
    
        for (int i = 0; i < numItems; i++) {
            if (i+currentPageIndex*4<inventoryItems.length){
            String itemName = inventoryItems[i+currentPageIndex*4][0];
            String itemQuantity = inventoryItems[i+currentPageIndex*4][1];
    
            float itemX = 100;
            float itemY =110+  i * (itemWidth + padding);

            Texture backgroundTexture = new Texture("sfondo/sfondo1.png");
            Image background2 = new Image(backgroundTexture);
            background2.setSize(680, 82);
            background2.setPosition(itemX, itemY-5);  
            stage.addActor(background2);
            inventoryItemActors.add(background2);
            

            background2.addListener(new InputListener() { 
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // Questo metodo viene chiamato quando il cursore entra nell'area dell'immagine
                    background2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sfondo/sfondo2.png")))));
                }
                
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    // Questo metodo viene chiamato quando il cursore esce dall'area dell'immagine
                    background2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sfondo/sfondo1.png")))));
                }
            });


            // Carica l'immagine dell'oggetto
            Texture itemTexture = new Texture(Gdx.files.internal("oggetti/" + itemName.toLowerCase() + ".png"));
            Image itemImage = new Image(itemTexture);
            itemImage.setSize(itemWidth, itemHeight);
            itemImage.setPosition(itemX + 20, itemY);
            stage.addActor(itemImage);
            inventoryItemActors.add(itemImage);
    
            // Crea una label per il nome dell'oggetto
            Label itemNameLabel = new Label(itemName, new Label.LabelStyle(font, null));
            itemNameLabel.setPosition(itemX+110, itemY+25);
            itemNameLabel.setFontScale(5);
            stage.addActor(itemNameLabel);
            inventoryItemActors.add(itemNameLabel);
    
            // Crea una label per la quantità dell'oggetto
            Label itemQuantityLabel = new Label("x " + itemQuantity, new Label.LabelStyle(font, null));
            itemQuantityLabel.setPosition(itemX + 565, itemY+ 25);
            itemQuantityLabel.setFontScale(4);
            stage.addActor(itemQuantityLabel);
            inventoryItemActors.add(itemQuantityLabel);
        }
    
        float btnX = 100;
        float btnY = 75;

        // Aggiungi un pulsante per visualizzare gli oggetti successivi
        if (inventoryItems.length > maxItemsToShow && maxNumPages!=currentPageIndex+1) {
            Texture nextButtonTexture = new Texture(Gdx.files.internal("sfondo/avanti.png"));
            Image nextButton = new Image(nextButtonTexture);
            nextButton.setSize(45, 80);
            nextButton.setPosition(btnX + 50, btnY - 60);
            nextButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPageIndex += 1;
                    showInventoryItems();
                }
            });
            stage.addActor(nextButton);
            inventoryItemActors.add(nextButton);
        }
            if (currentPageIndex>0){
            Texture backButtonTexture = new Texture(Gdx.files.internal("sfondo/indietro.png"));
            Image backButton = new Image(backButtonTexture);
            backButton.setSize(45, 80);
            backButton.setPosition(btnX, btnY - 60);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPageIndex -= 1;
                    showInventoryItems();
                }
            });
            stage.addActor(backButton);
            inventoryItemActors.add(backButton);
        }
    }
    }

    private void clearInventoryItems() {
        // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione precedente
        for (Actor actor : inventoryItemActors) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
        inventoryItemActors.clear(); // Pulisci l'array degli attori dell'inventario
    }
}
