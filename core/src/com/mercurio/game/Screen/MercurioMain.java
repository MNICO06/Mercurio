package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.effects.Musica;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.personaggi.Ash;
import com.mercurio.game.pokemon.Battle;
import com.mercurio.game.utility.UtilityVariables;

public class MercurioMain extends Game implements InterfacciaComune {

    private TiledMap map;
    private Vector2 map_size;

    private OrthogonalTiledMapRenderer tileRenderer;

    private Battle battle;
    private UtilityVariables utilityVariables;

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
    private MapLayer lineeBloccaStoria;
    private List<Render> render = new ArrayList<>();    //lista per il rendering dei layer in base alla profondità
    private List<Render> renderBot = new ArrayList<>(); //lista per salvare in anticipo i bot
    private ArrayList<String> listaAllawaysBack = new ArrayList<String>(); //lista per il rendering dei layer che si trovano sempre il background


    private float elapsedTime = 0;

    private SpriteBatch batch;

    private ArrayList<Rectangle> rectList = null;

    private MenuLabel menuLabel;


    private String teleport;

    private Screen currentScreen;



    private String ingressoPokeCenter;
    private String ingressoGrotta;
    private String ingressoCittaMontagna;
    private boolean pokemonMorti = false;
    private boolean provieneDaMappa = false;

    private String screenString;

    private Box box;
    private Shop shop;
    private SceltaStarterScreen sceltaStarterScreen;
    private boolean sconfitta = false;

    // Asset Manager
    public GameAsset asset = new GameAsset();

    @Override
    public void create() {
        try {
            utilityVariables = new UtilityVariables();

            // Asset Manager
            asset.loadAshAsset();
            asset.finishLoading();

            ash = new Ash(this);
            //poke = new PokemonOW(this);
            erba = new Erba(this);
            musica = new Musica(this, assetManager);
            batch = new SpriteBatch();
            menuLabel = new MenuLabel(this);
            setPage(Constant.SCHERMATA_LOGO);

            copiaJson("jsonSalvati/borsaSalvato.json", "ashJson/borsa.json");
            copiaJson("jsonSalvati/squadraSalvato.json", "ashJson/squadra.json");
            copiaJson("jsonSalvati/botsSalvato.json", "bots/bots.json");
            copiaJson("jsonSalvati/datiGeneraliSalvato.json", "ashJson/datiGenerali.json");
            copiaJson("jsonSalvati/boxSalvato.json", "ashJson/box.json");
            copiaJson("jsonSalvati/pokemonScopertiSalvato.json", "ashJson/pokemonScoperti.json");

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setPage(Constant.MENU_SCREEN);
                }
            }, 1); // Ritarda di 2 secondi (puoi modificare questo valore)
        } catch (Exception e) {
            System.out.println("Errore create mercurioMain, " + e);
        }

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
            System.out.println("Errore copiaJson mercurioMain, " + e);
        }
    }

    @Override
    public void render() {
        try {

            super.render();

            // Calcolo del tempo trascorso dall'inizio
            elapsedTime += Gdx.graphics.getDeltaTime();

            if (!utilityVariables.getSchermataMercurio()) {

                float cameraX = MathUtils.clamp(ash.getPlayerPosition().x + ash.getPlayerWidth() / 2,
                        camera.viewportWidth / 2, map_size.x - camera.viewportWidth / 2);
                float cameraY = MathUtils.clamp(ash.getPlayerPosition().y + ash.getPlayerHeight() / 2,
                        camera.viewportHeight / 2, map_size.y - camera.viewportHeight / 2);


                cambiaProfondita();
                ash.move(oggettiStoria, collisionLayer, rectList);

                currentScreen.render(elapsedTime);

                menuLabel.render();

                // Imposta la posizione della telecamera in modo che segua il giocatore
                camera.position.set(cameraX, cameraY, 0);

                camera.update();

                tileRenderer.setView(camera);

                if (map != null) {
                    erba.controllaPokemon(map);
                }

                if (battle != null) {
                    battle.render();
                }

                if (box != null) {
                    box.render();
                }

                if (shop != null) {
                    shop.render();
                }

                if (sceltaStarterScreen != null) {
                    sceltaStarterScreen.render();
                }

            }
        } catch (Exception e) {
            System.out.println("Errore render mercurioMain, " + e);
        }

    }

    public void creaBattaglia(String nomeJson, String nomePokemon) {
        try {

            FileHandle file = Gdx.files.local("jsonPokeSelvatici/" + nomeJson + ".json");
            String jsonString = file.readString();
            JsonValue json = new JsonReader().parse(jsonString);
            String pokeName = json.get(nomePokemon).getString("nomePokemon");

            // System.out.println(pokeName);

            getPlayer().setMovement(false);
            battle = new Battle(this, pokeName, false, nomeJson, nomePokemon);




        } catch (Exception e) {
            System.out.println("Errore creaBattaglia mercurioMain, " + e);
        }

    }

    public void setRectangleList(ArrayList<Rectangle> rectList) {
        this.rectList = rectList;
    }


    // avvia un altra scheda
    @Override
    public void setPage(String screen) {
        Screen newScreen = null;
        switch (screen) {

            case Constant.SCHERMATA_LOGO:
                newScreen = new SchermataLogo(this);
                setPage(Constant.MENU_SCREEN);
                break;

            case Constant.MENU_SCREEN:
                newScreen = new Menu(this);
                utilityVariables.setSchermataMercurio(true);
                break;

            case Constant.CASA_ASH_SCREEN:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new CasaSpawn(this);
                utilityVariables.setLuogo(Constant.CASA_ASH_SCREEN);
                utilityVariables.setUltimaVisitaLuogo(Constant.CASA_ASH_SCREEN);
                utilityVariables.setUltimaVisita(Constant.CASA_ASH_SCREEN);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.SPAWN_SCREEN:
                //TODO: quando chiamo questo bisogna settare per capire se deve essere p1 o bosco
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.CENTRO_POKEMON_CAPITALE_SCREEN:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeCenter(this);
                ingressoPokeCenter = "uscitaPokeCenterC";
                utilityVariables.setLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisitaLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisita(Constant.CENTRO_POKEMON_CAPITALE_SCREEN);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.CENTRO_POKEMON_NORD_SCREEN:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeCenter(this);
                ingressoPokeCenter = "uscitaPokeCenterN";
                utilityVariables.setLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisitaLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisita(Constant.CENTRO_POKEMON_NORD_SCREEN);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.CENTRO_POKEMON_MARE_SCREEN:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeCenter(this);
                ingressoPokeCenter = "uscitaPokeCenterMontagna";
                utilityVariables.setLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisitaLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisita(Constant.CENTRO_POKEMON_MARE_SCREEN);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.CENTRO_POKEMON_ROCCIA:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeCenter(this);
                ingressoPokeCenter = "cittaRoccia";
                utilityVariables.setLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setUltimaVisitaLuogo(Constant.CENTRO_POKEMON_LUOGO);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.LABORATORIO:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new Laboratorio(this);
                utilityVariables.setLuogo(Constant.LABORATORIO);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.POKEMARKET1:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeMarket1(this);
                utilityVariables.setLuogo(Constant.POKEMARKET_LUOGO);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.POKEMARKET2:
                asset.loadBotAsset();
                asset.finishLoading();
                newScreen = new PokeMarket2(this);
                utilityVariables.setLuogo(Constant.POKEMARKET_LUOGO);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.GROTTA:
                newScreen = new Grotta(this);
                utilityVariables.setLuogo(Constant.GROTTA);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            case Constant.CITTAMONTAGNA:
                if(asset.areAssetsLoaded()){
                    asset.unloadAllBot();
                }
                newScreen = new CittaMontagna(this);
                utilityVariables.setLuogo(Constant.CITTAMONTAGNA);
                utilityVariables.setSchermataMercurio(false);
                screenString = screen;
                break;

            default:
                break;
        }

        if (newScreen != null) {
            if (currentScreen != null) {
                currentScreen.dispose();
            }

            currentScreen = newScreen;

            setScreen(newScreen);
        }

    }

    // carica gioco con i vecchi dati
    public void loadGame() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("ashJson/datiPosizione.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            getPlayer().setPosition(Float.parseFloat(json.getString("x")), Float.parseFloat(json.getString("y")));
            utilityVariables.setUltimaVisita(json.getString("ultimaVisita"));
            utilityVariables.setUltimaVisita(json.getString("ultimaVisitaLuogo"));
            setLuogo(json.getString("luogo"));
            setPage(json.getString("screen"));
            musica.startMusic(utilityVariables.getLuogo());


        } catch (Exception e) {
            System.out.println("Errore loadGame mercurioMain, " + e);
        }

    }


    /*
     * Sezione per le mappe, con il settaggio di una nuova mappa, il cambio della telecamera e il rendering in profondità
     */
    public void setMap(TiledMap map, OrthogonalTiledMapRenderer render, OrthographicCamera camera, float larghezza, float altezza) {
        this.map = map;
        map_size = new Vector2(larghezza, altezza);
        tileRenderer = render;
        this.camera = camera;
        camera.update();

        // prendo il layer delle collisioni
        try {
            collisionLayer = map.getLayers().get("collisioni");

            // Controlla se "oggettiStoria" esiste nel layer, altrimenti assegna un elemento
            // vuoto
            if (map.getLayers().get("oggettiStoria") != null) {
                oggettiStoria = map.getLayers().get("oggettiStoria");
            } else {
                oggettiStoria = new TiledMapTileLayer(0, 0, 0, 0); // Crea un layer vuoto
            }

            // Controllo se "lineeBloccaStoria" esiste nel layer, altrimenti assegna un elemento
            // vuoto
            if (map.getLayers().get("lineeBloccaStoria") != null) {
                lineeBloccaStoria = map.getLayers().get("lineeBloccaStoria");
            } else {
                lineeBloccaStoria = new TiledMapTileLayer(0, 0, 0, 0); // Crea un layer vuoto
            }

            
            lineeLayer = map.getLayers().get("linee");
            alberiBack = map.getLayers().get("alberiBack");
            alberiFore = map.getLayers().get("alberiFore");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiaProfondita() {
        render.clear(); // Puliamo la lista

        // Aggiungi i vari oggetti da renderizzare
        // Aggiungi i layer
        for (MapObject object : lineeLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                String layerName = (String) rectangleObject.getProperties().get("layer");
                float y = rectangleObject.getRectangle().getY();
                render.add(new Render("layer", layerName, y));
            }
        }

        // Aggiungi il player
        render.add(new Render("player", ash.getPlayerPosition().y));

        for (Render bot : renderBot) {
            render.add(bot);
        }

        // Ordina la lista in base alla posizione `y` (per la profondità)
        Collections.sort(render, Comparator.comparingDouble(r -> -r.y)); // Ordinamento decrescente

        // Ordina anche il background (se necessario)
        for (String layerName : listaAllawaysBack) {
            renderLayer(layerName);
        }

        // Renderizza nell'ordine corretto
        for (Render renderComponent : render) {
            switch (renderComponent.type) {
                case "layer":
                    renderLayer(renderComponent.layerName);
                    break;

                case "player":
                    renderPlayer();
                    break;

                case "bot":
                    renderPersonaggiSecondari(renderComponent.texture, renderComponent.x, renderComponent.y, renderComponent.width, renderComponent.height);
                    break;
                
            }
        }
    }


    private void renderLayer(String layerName) {
        try {
            tileRenderer.getBatch().begin();
    
            // Recupera il layer dalla mappa
            MapLayer layer = map.getLayers().get(layerName);
            // Renderizza il layer
            tileRenderer.renderTileLayer((TiledMapTileLayer) layer);
    
            tileRenderer.getBatch().end();
        } catch (Exception e) {
            System.out.println("Errore durante il rendering del layer: " + e);
        }
    }

    public void renderPlayer() {
        try {

            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            batch.draw(ash.getAnimazione(), ash.getPlayerPosition().x, ash.getPlayerPosition().y, ash.getCurrentWidht(),
                    ash.getCurrentHeght());

            batch.end();
        } catch (Exception e) {
            System.out.println("Errore renderPlayer mercurioMain, " + e);
        }

    }

    public void renderPersonaggiSecondari(TextureRegion animazione, float x, float y, float width, float height) {
        try {

            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            batch.draw(animazione, x, y, width, height);

            batch.end();
        } catch (Exception e) {
            System.out.println("Errore renderPersonaggiSecondari mercuriomain, " + e);
        }

    }

    public void aggiornaListaAllawaysBack(ArrayList<String> nuovaLista) {
        // Svuota la lista attuale
        listaAllawaysBack.clear();
    
        // Aggiungi tutti gli elementi della nuova lista
        listaAllawaysBack.addAll(nuovaLista);
    }

    public void addBotRender(List<Render> newRenderBot) {
        renderBot = newRenderBot;
    }

    public MapLayer getLineeLayer() {
        if (lineeLayer != null) {
            return lineeLayer;
        }
        return null;
    }

    public MapLayer getLineeBloccaStoria() {
        if (lineeBloccaStoria != null) {
            return lineeBloccaStoria;
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
        if (divAlberi != null) {
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

    @Override
    public void setLuogo(String luogo) {
        utilityVariables.setLuogo(luogo);
    }

    public String getLuogo() {
        return utilityVariables.getLuogo();
    }

    public TiledMap getMap() {
        return map;
    }

    public void setisInMovement(boolean isInMovement) {
        utilityVariables.setIsInMovement(isInMovement);
    }

    public boolean getIsInMovement() {
        return utilityVariables.getIsInMovement();
    }

    public Musica getMusica() {
        return musica;
    }

    public String getIngressoPokeCenter() {
        return ingressoPokeCenter;
    }

    public void setIngressoPokeCenter(String ingressoPokeCenter) {
        this.ingressoPokeCenter = ingressoPokeCenter;
    }

    public String getIngressoGrotta() {
        return ingressoGrotta;
    }

    public void setIngressoGrotta(String ingressoGrotta) {
        this.ingressoGrotta = ingressoGrotta;
    }

    public String getIngressoCittaMontagna() {
        return ingressoCittaMontagna;
    }

    public void setIngressoCittaMontagna(String ingressoCittaMontagna) {
        this.ingressoCittaMontagna = ingressoCittaMontagna;
    }

    public String getScreenString() {
        return screenString;
    }

    @Override
    public void closeBattle() {
        getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        battle = null;
        if (sconfitta) {
            setProvieneDaMappa(true);
            tornaPokecenter();
        }
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

    public void creaShop() {
        getPlayer().setMovement(false);
        shop = new Shop(this);
    }

    public void closeShop() {
        getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        shop = null;
    }

    public void creaSceltaStarter() {
        if (sceltaStarterScreen == null) {
            sceltaStarterScreen = new SceltaStarterScreen(this);
        }
    }

    public void closeSceltaStarter() {
        getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        sceltaStarterScreen = null;
    }

    public boolean sceltoStarter() {
        return sceltaStarterScreen.getSceltoStarter();
    }

    public MapLayer getOggettiStoria() {
        if (oggettiStoria != null) {
            return oggettiStoria;
        }
        return null;
    }

    public void setProvieneDaMappa(boolean provieneDaMappa) {
        this.provieneDaMappa = provieneDaMappa;
    }

    public boolean getProvieneDaMappa() {
        return provieneDaMappa;
    }
    @Override
    public GameAsset getGameAsset() {
        return asset;
    }

    @Override
    public MercurioMain getGame() {
        return this;
    }

    @Override
    public void setSconfitta(boolean sconfitta) {
        this.sconfitta = sconfitta;
    }

    @Override
    public void tornaPokecenter() {
        tornaPokecenterMain();
    }

    public void tornaPokecenterMain() {
        setPokemonMorti(true);
        utilityVariables.setLuogo(utilityVariables.getUltimaVisitaLuogo());
        utilityVariables.setLuogo(utilityVariables.getUltimaVisita());
    }

    public void setPokemonMorti(boolean pokemonMorti) {
        this.pokemonMorti = pokemonMorti;
    }
    public boolean getPokemonMorti() {
        return pokemonMorti;
    }
    public String getUltimaVisita() {
        return utilityVariables.getUltimaVisita();
    }
    public String getUltimaVisitaLuogo() {
        return utilityVariables.getUltimaVisitaLuogo();
    }
    public UtilityVariables getUtilityVariables() {
        return utilityVariables;
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        // System.out.println("Spegnimento in corso...");

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
        
        //Dispose degli asset
        asset.unloadAllAsh();
        asset.dispose();
        // Chiudi il batch
        batch.dispose();
        // Chiudi l'applicazione
        Gdx.app.exit();
        // Chiudi l'applicazione
        System.exit(0);
    }

}
