package com.mercurio.game.menu;

import org.json.JSONArray;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetBMLSSC;
import com.mercurio.game.AssetManager.GameAsset.AssetBMMLP;
import com.mercurio.game.AssetManager.GameAsset.AssetBPB;
import com.mercurio.game.AssetManager.GameAsset.AssetBorsa;
import com.mercurio.game.Screen.MercurioMain;
import com.mercurio.game.pokemon.Battle;
import com.mercurio.game.pokemon.squadraCure;
import com.mercurio.game.strumenti.MiniMappa;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;

public class Borsa {
    
    private Texture itemTexture;
    private Array<Actor> inventoryItemActors = new Array<>(); // Array per tracciare gli attori degli oggetti dell'inventario
    private Array<Label> quantityLabels = new Array<>();
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
    private String[][] inventoryCure;
    private String[][] inventoryBall;
    private String[][] inventoryMT;
    private String[][] inventoryKey;
    private JSONArray jsonCure;
    private JSONArray jsonBall;
    private JSONArray jsonKey;
    private JSONArray jsonMT;
    private Texture textureCure;
    private Texture textureBall;
    private Texture textureKey;
    private Texture textureMT;
    private boolean battle;
    private Image usaImage;
    
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
    private squadraCure squadraCure;
    private Battle battaglia;
    private MiniMappa miniMappa;
    private MercurioMain game;

    private GameAsset asset;
    
    public Borsa(Stage stage, boolean battle, Battle battaglia, MercurioMain game) {
        this.batch = (SpriteBatch) stage.getBatch();
        this.battaglia=battaglia;
        this.game = game;
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.stage = stage;
        this.battle=battle;
        this.borsaActors = new Array<>(); // Inizializza l'array degli attori della borsa
        this.asset = game.getGameAsset();
        Gdx.input.setInputProcessor(stage);

        asset.loadBorsaAsset();
        asset.loadBMLSSCPAsset();
        asset.loadBMMLPAsset();
        asset.loadBPBAsset();
        asset.finishLoading();

        try (FileReader fileReader = new FileReader("ashJson/borsa.json")) {

            // Utilizza JSONTokener per leggere il file JSON
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject inventoryData = new JSONObject(tokener);

            // Riempire le matrici di inventario
            jsonCure = inventoryData.getJSONArray("inventoryCure");
            jsonBall = inventoryData.getJSONArray("inventoryBall");
            jsonMT = inventoryData.getJSONArray("inventoryMT");
            jsonKey = inventoryData.getJSONArray("inventoryKey");

            inventoryCure = jsonArrayToMatrix(jsonCure);
            inventoryBall = jsonArrayToMatrix(jsonBall);
            inventoryMT = jsonArrayToMatrix(jsonMT);
            inventoryKey = jsonArrayToMatrix(jsonKey);
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
        }

        createUI();
    }

    //trasforma gli array in matrici E MI RISPARMIA ORE DI LAVORO
    private static String[][] jsonArrayToMatrix(JSONArray jsonArray) {

        try {
            int length = jsonArray.length();
            String[][] matrix = new String[length][2]; // Altezza variabile, larghezza fissa a 2
            for (int i = 0; i < length; i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String itemName = item.getString("name");
                int quantity = item.getInt("quantity");
                matrix[i][0] = itemName;
                matrix[i][1] = String.valueOf(quantity);
            }
            return matrix;
        } catch (Exception e) {
            System.out.println("Errore jsonArrayToMatrix, " + e);
            return null;
        }

        
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        if (squadraCure!=null){
            squadraCure.render();
        }

        if (miniMappa!=null){
            miniMappa.render();
        }
        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    public void dispose() {
        asset.unloadAllBorsa();
        asset.unloadAllBMLSSC();
        asset.unloadAllBMMLP();
        asset.unloadAllBPB();
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    private void createUI() {

        try {
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Add background 
            Texture backgroundTexture = asset.getBMLSSC(AssetBMLSSC.SF_SFONDO);
            Image background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
            stage.addActor(background);
        
            // Load textures and create TextureRegions
            textureCure = asset.getBorsa(AssetBorsa.SF_CURE_BG);
            textureBall = asset.getBorsa(AssetBorsa.SF_BALL_BG);
            textureKey = asset.getBorsa(AssetBorsa.SF_KEY_BG);
            textureMT = asset.getBorsa(AssetBorsa.SF_MT_BAG);
        
            cure = new TextureRegion[3];
            ball = new TextureRegion[3];
            key = new TextureRegion[3];
            mt = new TextureRegion[3];
        
            int regionHeight = textureCure.getHeight() / 3;
        
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
            if (!battle)
                stage.addActor(labelKey);
        
            labelMT = new Image(mt[0]);
            labelMT.setSize(100, 100);
            labelMT.setPosition(460, screenHeight - 150);
            if (!battle)
                stage.addActor(labelMT); 
        
            // Handle click events on labels
            labelCure.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateAnimation(labelCure, cambiaCure);
                    deactivateAnimations(labelBall, labelKey, labelMT);
                    currentPageIndex = 0;
                    showInventoryItems(inventoryCure);
                }
            });
        
            labelBall.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateAnimation(labelBall, cambiaBall);
                    deactivateAnimations(labelCure, labelKey, labelMT);
                    currentPageIndex = 0;
                    showInventoryItems(inventoryBall);
                }
            });
        
            labelKey.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateAnimation(labelKey, cambiaKey);
                    deactivateAnimations(labelCure, labelBall, labelMT);
                    currentPageIndex = 0;
                    showInventoryItems(inventoryKey);
                }
            });
        
            labelMT.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    activateAnimation(labelMT, cambiaMT);
                    deactivateAnimations(labelCure, labelBall, labelKey);
                    currentPageIndex = 0;
                    showInventoryItems(inventoryMT);
                }
            });
        
            
        
            Texture closeButtonTexture = asset.getBMMLP(AssetBMMLP.SF_X);
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
            if (!battle)
                borsaActors.addAll(labelCure, labelBall, labelKey, labelMT, background, closeLabel);
            else
                borsaActors.addAll(labelCure, labelBall, background, closeLabel);

        } catch (Exception e) {
            System.out.println("Errore createUI, " + e);
        }
    }
    

    private void close() {

        try {
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

        } catch (Exception e) {
            System.out.println("Errore close Borsa, " + e);
        }
        
    }
    
    private void activateAnimation(Image image, Animation<TextureRegion> animation) {

        try {
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
            
        } catch (Exception e) {
            System.out.println("Errore activateAnimation, " + e);
        }
        
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

    private void showInventoryItems(String[][] inventoryItems) {

        try {
            clearInventoryItems();
    
            /*Simulazione di un inventario
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
            };*/
        
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
        
                    Texture backgroundTexture = asset.getBorsa(AssetBorsa.SF_1);
                    Image background2 = new Image(backgroundTexture);
                    background2.setSize(680, 82);
                    background2.setPosition(itemX, itemY-5);  
                    stage.addActor(background2);
                    inventoryItemActors.add(background2);
                    
        
                    InputListener listener=new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            // Imposta l'immagine di sfondo con la nuova texture
                            background2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.local("sfondo/sfondo2.png")))));
                            
                        }
                        
                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        
                            background2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.local("sfondo/sfondo1.png")))));
                        }
                    };
        
                    Borsa borsa=this;
        
                    ClickListener clickListener= new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y){
                            //System.out.println(battle);
                            if (battle && inventoryItems==inventoryBall){
                                //System.out.println("a");
                                Texture usaTexture = asset.getBorsa(AssetBorsa.SF_USA);
                                usaImage = new Image(usaTexture);
                                usaImage.setPosition(70, 10);
                                usaImage.setSize(56*3, 24*3);
                                stage.addActor(usaImage);
                                inventoryItemActors.add(usaImage);
                                usaImage.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        close();
                                        battaglia.calcoloTassoCattura(itemName);
                                    }
                                });
                            }
                            if (inventoryItems==inventoryCure){
                                Texture usaTexture = asset.getBorsa(AssetBorsa.SF_USA);
                                usaImage = new Image(usaTexture);
                                usaImage.setPosition(70, 10);
                                usaImage.setSize(56*3, 24*3);
                                stage.addActor(usaImage);
                                inventoryItemActors.add(usaImage);
                                usaImage.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        usaImage.remove();
                                        squadraCure= new squadraCure(stage, battle, borsa, itemName, itemQuantity);
                                    }
                                });
                            }
                            if (inventoryItems==inventoryKey){
                                Texture usaTexture = asset.getBorsa(AssetBorsa.SF_USA);
                                usaImage = new Image(usaTexture);
                                usaImage.setPosition(70, 10);
                                usaImage.setSize(56*3, 24*3);
                                stage.addActor(usaImage);
                                inventoryItemActors.add(usaImage);
                                usaImage.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        usaImage.remove();
                                        if (itemName.equals("Mappa Citta'")){
                                            apriMiniMappa();
                                        }
                                    }
                                });
                            }
                        }
                    };
        
        
        
                    // Carica l'immagine dell'oggetto
                    String primiDueCaratteri = itemName.substring(0, 2);
                    if (primiDueCaratteri.charAt(0) == 'M' && primiDueCaratteri.charAt(1) == 'T'){
                        itemTexture = new Texture(Gdx.files.local("oggetti/MT.png"));
                    }
                    else{
                        itemTexture = new Texture(Gdx.files.local("oggetti/" + itemName + ".png"));
                    }
                    Image itemImage = new Image(itemTexture);
                    itemImage.setSize(itemWidth, itemHeight);
                    itemImage.setPosition(itemX + 20, itemY + 5);
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
                    itemQuantityLabel.setName("quantity"+itemName);
                    quantityLabels.add(itemQuantityLabel);
                    stage.addActor(itemQuantityLabel);
                    inventoryItemActors.add(itemQuantityLabel);
        
                    background2.addListener(listener);
                    itemImage.addListener(listener);
                    itemNameLabel.addListener(listener);
                    itemQuantityLabel.addListener(listener);
        
                    background2.addListener(clickListener);
                    itemImage.addListener(clickListener);
                    itemNameLabel.addListener(clickListener);
                    itemQuantityLabel.addListener(clickListener);
                }
        
                float btnX = 100;
                float btnY = 75;
        
                // Aggiungi un pulsante per visualizzare gli oggetti successivi
                if (inventoryItems.length > maxItemsToShow && maxNumPages!=currentPageIndex+1) {
                    Texture nextButtonTexture = asset.getBPB(AssetBPB.SF_AVANTI);
                    Image nextButton = new Image(nextButtonTexture);
                    nextButton.setSize(45, 80);
                    nextButton.setPosition(btnX + 50, btnY - 60);
                    nextButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            currentPageIndex += 1;
                            showInventoryItems(inventoryItems);
                        }
                    });
                    stage.addActor(nextButton);
                    inventoryItemActors.add(nextButton);
                }
                    if (currentPageIndex>0){
                    Texture backButtonTexture = asset.getBPB(AssetBPB.SF_INDIETRO);
                    Image backButton = new Image(backButtonTexture);
                    backButton.setSize(45, 80);
                    backButton.setPosition(btnX, btnY - 60);
                    backButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            currentPageIndex -= 1;
                            showInventoryItems(inventoryItems);
                        }
                    });
                    stage.addActor(backButton);
                    inventoryItemActors.add(backButton);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Errore showInventoryItems, " + e);
        }
        
    }

    private void clearInventoryItems() {

        try {
            // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione precedente
            for (Actor actor : inventoryItemActors) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }
            inventoryItemActors.clear(); // Pulisci l'array degli attori dell'inventario
            
        } catch (Exception e) {
            System.out.println("Errore clearInvetnory items, " + e);
        }

        
    }

    public void closeSquadra() {
        Gdx.input.setInputProcessor(stage);
        squadraCure = null;
    }

    public void ritornaBattaglia(String discorso, int indexCurato, String currentHP, String maxHP, int passi, int psCurati){
        battaglia.ricominciaBattagliaDopoCura(discorso, indexCurato, currentHP, maxHP, passi, psCurati);
        squadraCure.clearInventoryItems();
        clearInventoryItems();
        close();
    }

    public void aggiornaQuantity(String itemNome){

        try {
            
            for (Label label : quantityLabels) {
                if (label.getName().equals("quantity"+itemNome)){

                    String qty = ""+label.getText().substring(2);

                    label.setText("x "+(Integer.parseInt(qty)-1));
                    if ((Integer.parseInt(qty)-1)<=0){
                        aggiornaBorsa();
                        showInventoryItems(inventoryCure);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Errore aggiornaQuantity, " + e);
        }
        
    }
    
    private void aggiornaBorsa(){
        try (FileReader fileReader = new FileReader("ashJson/borsa.json")) {
            // Utilizza JSONTokener per leggere il file JSON
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject inventoryData = new JSONObject(tokener);

            // Riempire le matrici di inventario
            jsonCure.clear();
            jsonBall.clear();
            jsonMT.clear();
            jsonKey.clear();
            quantityLabels.clear();
            jsonCure = inventoryData.getJSONArray("inventoryCure");
            jsonBall = inventoryData.getJSONArray("inventoryBall");
            jsonMT = inventoryData.getJSONArray("inventoryMT");
            jsonKey = inventoryData.getJSONArray("inventoryKey");

            inventoryCure = jsonArrayToMatrix(jsonCure);
            inventoryBall = jsonArrayToMatrix(jsonBall);
            inventoryMT = jsonArrayToMatrix(jsonMT);
            inventoryKey = jsonArrayToMatrix(jsonKey);

        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
        }
    }

    public squadraCure getSquadraCure(){
        return squadraCure;
    }


    private void apriMiniMappa(){
        miniMappa= new MiniMappa(stage, this, game);
    }

    public void closeMiniMappa(){
        miniMappa=null;
        Gdx.input.setInputProcessor(stage);
    }

    public GameAsset getAsset() {
        return asset;
    }
}
