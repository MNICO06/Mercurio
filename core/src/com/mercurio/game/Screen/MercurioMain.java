package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.effects.Musica;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.personaggi.Ash;
import com.mercurio.game.pokemon.Battle;


public class MercurioMain extends Game implements InterfacciaComune{

    private ShapeRenderer shapeRenderer;

    private int screen_id;

    private TiledMap map;

    private Vector2 map_size;

    private OrthogonalTiledMapRenderer tileRenderer;

    private Battle battle;

    Ash ash;
    Erba erba;
    Musica musica;
    AssetManager assetManager = new AssetManager();

    private OrthographicCamera camera;

    private MapLayer collisionLayer;
    private MapLayer oggettiStoria;
    private MapLayer lineeLayer;
    private MapLayer alberiBack;
    private MapLayer alberiFore;
    private MapLayer divAlberi;

    private float elapsedTime = 0;

    private SpriteBatch batch;

    private ArrayList<Rectangle> rectList = null;

    private MenuLabel menuLabel;

    private TiledMap mappa;

    private String teleport;

    private Screen currentScreen;

    private String luogo;

    private boolean isInMovement = false;

    private String ingressoPokeCenter;

    private String screenString;

    private Box box;
    
    @Override
    public void create() {
        ash = new Ash(this);
        erba = new Erba (this);
        musica = new Musica (this, assetManager);
        batch = new SpriteBatch();
        menuLabel = new MenuLabel(this);
        setPage(Constant.SCHERMATA_LOGO);

        copiaJson("jsonSalvati/borsaSalvato.json", "assets/ashJson/borsa.json");
        copiaJson("jsonSalvati/squadraSalvato.json", "assets/ashJson/squadra.json");
        copiaJson("jsonSalvati/botsSalvato.json", "assets/bots/bots.json");
        copiaJson("jsonSalvati/datiGeneraliSalvato.json", "assets/ashJson/datiGenerali.json");
        copiaJson("jsonSalvati/boxSalvato.json", "assets/ashJson/box.json");


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Carica la mappa
                TmxMapLoader mapLoader = new TmxMapLoader();
                mappa = mapLoader.load(Constant.MAPPA);

                setPage(Constant.MENU_SCREEN);
            }
        }, 1); // Ritarda di 2 secondi (puoi modificare questo valore)
    }

    public void copiaJson(String pathSorgente, String pathDestinazione) {
        try {
            // Leggi il contenuto del file JSON sorgente
            FileHandle sourceFile = Gdx.files.internal(pathSorgente);
            String jsonString = sourceFile.readString();

            // Scrivi il contenuto nel file JSON di destinazione
            FileHandle destinationFile = Gdx.files.local(pathDestinazione);
            destinationFile.writeString(jsonString, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render () {

        super.render();

        // Calcolo del tempo trascorso dall'inizio
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (screen_id != 0) {

            float cameraX = MathUtils.clamp(ash.getPlayerPosition().x + ash.getPlayerWidth() / 2, camera.viewportWidth / 2, map_size.x - camera.viewportWidth / 2);
            float cameraY = MathUtils.clamp(ash.getPlayerPosition().y + ash.getPlayerHeight() / 2, camera.viewportHeight / 2, map_size.y - camera.viewportHeight / 2);

            ash.move(oggettiStoria, collisionLayer, rectList);

            menuLabel.render();
            
            // Imposta la posizione della telecamera in modo che segua il giocatore
            camera.position.set(cameraX, cameraY, 0);

            camera.update();

            tileRenderer.setView(camera);

            if (map != null) {
                erba.controllaPokemon(map);
            }

            if (battle != null){
                battle.render();
            }

            if (box != null){
                box.render();
            }

        }
    }

    public void creaBattaglia(String nomeJson, String nomePokemon) {
        System.out.println("prova battaglia selvatici");
        FileHandle file = Gdx.files.local("assets/jsonPokeSelvatici/"+ nomeJson +".json");
        String jsonString = file.readString();
        JsonValue json = new JsonReader().parse(jsonString);
        String pokeName = json.get(nomePokemon).getString("nomePokemon");

        //System.out.println(pokeName);

        getPlayer().setMovement(false);
        battle = new Battle(this, pokeName, false, nomeJson, nomePokemon);
    }

    public void setRectangleList(ArrayList<Rectangle> rectList) {
        this.rectList = rectList;
    }

    public void renderPlayer() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(ash.getAnimazione(), ash.getPlayerPosition().x, ash.getPlayerPosition().y, ash.getCurrentWidht(), ash.getCurrentHeght());

        batch.end();
    }

    public void renderPersonaggiSecondari(TextureRegion animazione, float x, float y, float width, float height) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(animazione, x, y, width, height);

        batch.end();

    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        //System.out.println("Spegnimento in corso...");
    
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
        // Chiudi l'applicazione
        Gdx.app.exit();
        // Chiudi l'applicazione
        System.exit(0);
    }

    //avvia un altra scheda
    public void setPage(String screen) {
        Screen newScreen = null;
        switch(screen) {
            case Constant.MENU_SCREEN:
                newScreen = new Menu(this);
                screen_id = 0;
                break;

            case Constant.CASA_ASH_SCREEN:
                newScreen = new CasaSpawn(this);
                luogo = "casaSpawn";
                screen_id = 1;
                screenString=screen;
                break;

            case Constant.CENTRO_POKEMON_CAPITALE_SCREEN:
                newScreen = new PokeCenter(this);
                screen_id = 2;
                luogo = "pokeCenter";
                ingressoPokeCenter = "uscitaPokeCenterC";
                screenString=screen;
                break;

            case Constant.CENTRO_POKEMON_NORD_SCREEN:
                newScreen = new PokeCenter(this);
                screen_id = 2;
                luogo = "pokeCenter";
                ingressoPokeCenter = "uscitaPokeCenterN";
                screenString=screen;
                break;

            case Constant.CENTRO_POKEMON_MARE_SCREEN:
                newScreen = new PokeCenter(this);
                screen_id = 2;
                luogo = "pokeCenter";
                ingressoPokeCenter = "uscitaPokeCenterMontagna";
                screenString=screen;
                break;

            case Constant.MAPPA_SCREEN:
                newScreen = new FullMap(this, mappa);
                screen_id = 3;
                screenString=screen;
                break;

            case Constant.SCHERMATA_LOGO:
                newScreen = new SchermataLogo(this);
                setPage(Constant.MENU_SCREEN);
                break;

            

            default:
                break;
        }

        if (newScreen != null) {
            if (currentScreen != null) {
                currentScreen.dispose();
            }

            currentScreen = newScreen;

            setScreen (newScreen);
        }

    }

    //carica gioco con i vecchi dati
    public void loadGame() {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/datiPosizione.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        getPlayer().setPosition(Float.parseFloat(json.getString("x")), Float.parseFloat(json.getString("y")));
        setLuogo(json.getString("luogo"));
        setPage(json.getString("screen"));
        musica.startMusic(luogo);

        //System.out.println(getPlayer().getPlayerPosition().x);
        //System.out.println(getPlayer().getPlayerPosition().y);
        //System.out.println(getLuogo());
        //System.out.println(getScreenString());
    }

    public void setMap(TiledMap map, OrthogonalTiledMapRenderer render, OrthographicCamera camera, float larghezza, float altezza) {
        this.map = map;
        map_size = new Vector2(larghezza, altezza);
        tileRenderer = render;
        this.camera = camera;
        camera.update();

        //prendo il layer delle collisioni
        try {
            collisionLayer = map.getLayers().get("collisioni");
            oggettiStoria = map.getLayers().get("oggettiStoria");
            lineeLayer = map.getLayers().get("linee");
            alberiBack = map.getLayers().get("alberiBack");
            alberiFore = map.getLayers().get("alberiFore");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public MapLayer getLineeLayer() {
        if (lineeLayer != null) {
            return lineeLayer;
        }
        return null;
    }

    public Ash getPlayer() {
        if (ash != null) {
            return ash;
        }
        return null;
    }

    public MapLayer getAlberiBack() {
        if (alberiBack != null) {
            return alberiBack;
        }
        return null;
    }

    public MapLayer getAlberiFore() {
        if (alberiFore != null) {
            return alberiFore;
        }
        return null;
    }

    public MapLayer getDivAlberi() {
        if (divAlberi != null){
            return divAlberi;
        }
        return null;
    }

    public void setTeleport(String teleport) {
        this.teleport = teleport;
    }

    public String getTeleport() {
        return teleport;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
    public String getLuogo () {
        return luogo;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setisInMovement(boolean isInMovement) {
        this.isInMovement = isInMovement;
    }
    public boolean getIsInMovement () {
        return isInMovement;
    }

    public Musica getMusica () {
        return musica;
    }

    public String getIngressoPokeCenter() {
        return ingressoPokeCenter;
    }

    public void setIngressoPokeCenter(String ingressoPokeCenter) {
        this.ingressoPokeCenter = ingressoPokeCenter;
    }

    public String getScreenString(){
        return screenString;
    }

    @Override
    public void closeBattle() {
        getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        battle = null;
    }

    public void creaBox() {
        getPlayer().setMovement(false);
        box = new Box(this);
    }

    public void closeBox() {
        getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        box = null;
    }

    public MapLayer getOggettiStoria() {
        return oggettiStoria;
    }

}
