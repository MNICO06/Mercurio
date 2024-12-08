package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.personaggi.Ash;
import com.mercurio.game.personaggi.MammaAsh;
import java.util.Timer;

public class CasaSpawn extends ScreenAdapter {
    private final MercurioMain game;
    private MammaAsh mammaAsh;
    private LabelDiscorsi labelDiscorsiSenzaStarter;
    private LabelDiscorsi labelDiscorsiConStarter;

    //dati per render della mappa
    private TiledMap casaAsh;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;

    private Rectangle rectangleUscita;

    private Timer timer;
    private TimerTask mammaTimerTask;

    private boolean isInBox = false;
    
    private boolean wasInBox = false;

    private boolean tieniApertoDiscorsoPrima = false;
    private boolean tieniApertoDiscorsoDopo = false;
    private boolean fPressed = false;


    public CasaSpawn(MercurioMain game) {
        this.game = game;
        mammaAsh = new MammaAsh(game);

        /*variabili che andranno lette da file */
        String discorsoSenzaStarter = "C'era il professor Pokemon che ti cercava, vai al suo laboratorio per vedere cosa ti deve dire";
        String discorsoConStarter = "Finalmente sei tornato, vedo che i tuoi pokemon sono un po' stanchi, lascia che te li curi";

        int dimMax=30;
        labelDiscorsiSenzaStarter = new LabelDiscorsi(discorsoSenzaStarter,30,10,false, false);
        labelDiscorsiConStarter = new LabelDiscorsi(discorsoConStarter,30,10,false, false);
        
        rectList = new ArrayList<Rectangle>();
    }


    @Override
    public void show() {

        game.setLuogo("casaSpawn");
        game.getMusica().startMusic("casaSpawn");

        //timer da usare dopo per far girare la mamma sui fornelli
        timer = new Timer();
        mammaTimerTask = new TimerTask() {
            @Override
            public void run() {
                mammaAsh.setAvanti();
            }
        };


        TmxMapLoader mapLoader = new TmxMapLoader();
        casaAsh = mapLoader.load(Constant.CASA_ASH);
        tileRenderer = new OrthogonalTiledMapRenderer(casaAsh);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = casaAsh.getProperties().get("width", Integer.class) * casaAsh.getProperties().get("tilewidth", Integer.class);
        int mapHeight = casaAsh.getProperties().get("height", Integer.class) * casaAsh.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.9f, map_size.y/2f);
        camera.update();

        game.setMap(casaAsh, tileRenderer, camera, map_size.x, map_size.y);

        //aggiungo alla lista dei rettangoli per le collisioni quello della mamma
        rectList.add(mammaAsh.getBoxPlayer());

        game.getPlayer().setPosition(100, 50);
        
        //prendere rettangolo per mappa
        MapObjects objects = casaAsh.getLayers().get("exit").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                // Ottieni il rettangolo
                rectangleUscita = rectangleObject.getRectangle();
            } 
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        game.setRectangleList(rectList);
        cambiaProfondita(lineeLayer);
        giraMamma();
        controlloTesto();
        controlloTestoIniziale();
        controllaUscita();

    }

    //cambio continuamente forground e background in base alla pos del personaggio
    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();

        

        background.add("floor");
        background.add("WallAlwaysBack");
        background.add("AlwaysBack_1");
        background.add("AlwaysBack_2");

        

        for (MapObject object : lineeLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject)object;

                //salvo il nome del layer che verrà inserito in una delle due liste
                String layerName = (String)rectangleObject.getProperties().get("layer");

                if (game.getPlayer().getPlayerPosition().y < rectangleObject.getRectangle().getY()) {
                    background.add(layerName);
                }else {
                    foreground.add(layerName);
                }
            }
        }
       
        boolean isForeground = false;
        if (game.getPlayer().getPlayerPosition().y < mammaAsh.getPosition().y){
            isForeground = true;
        }

        //background
        for (String layerName : background) {
            renderLayer(layerName);
        }

        if (isForeground) {
            game.renderPersonaggiSecondari(mammaAsh.getTexture(), mammaAsh.getPosition().x, mammaAsh.getPosition().y, mammaAsh.getWidth(), mammaAsh.getHeight());
        }

        game.renderPlayer();
        
        //foreground
        for (String layerName : foreground) {
            renderLayer(layerName);
        }

        if (!isForeground) {
            game.renderPersonaggiSecondari(mammaAsh.getTexture(), mammaAsh.getPosition().x, mammaAsh.getPosition().y, mammaAsh.getWidth(), mammaAsh.getHeight());
        }
        
    }

    // Metodo per renderizzare un singolo layer
    private void renderLayer(String layerName) {
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        MapLayer layer = casaAsh.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }

    public void startTimerForMamma() {
        
        // Pianifica un nuovo compito per far tornare la mamma nella posizione "avanti" dopo 5 secondi
        mammaTimerTask = new TimerTask() {
            @Override
            public void run() {
                mammaAsh.setAvanti();
            }
        };

        // Avvia il timer per il compito della mamma
        timer.schedule(mammaTimerTask, 3000);
    }

    // Metodo per annullare il compito del timer della mamma
    public void cancelTimerForMamma() {
        // Cancella il compito del timer della mamma se è stato pianificato in precedenza
        if (mammaTimerTask != null) {
            mammaTimerTask.cancel();
            mammaTimerTask = null; // Imposta il compito del timer della mamma su null per indicare che è stato cancellato
        }
    }

    //metodo che fa girare la mamma
    public void giraMamma() {
        isInBox = false;
        boolean gira = false;
        //si trova dentro quello sotto
        if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxVert())) {
            mammaAsh.setIndietro();
            isInBox = true;
        }
        //si trova dentro quello a destra
        else if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxOrizDx())) {
            mammaAsh.setDestra();
            isInBox = true;
        }
        //si trova dentro quello a sinistra
        else if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxOrizSx())) {
            mammaAsh.setSinstra();
            isInBox = true;
        }

        if (isInBox  && !fPressed) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                boolean presenzaStarter = controllaPresenzaStarter();
                if (presenzaStarter) {
                    tieniApertoDiscorsoDopo = true;
                } else {
                    tieniApertoDiscorsoPrima = true;
                }
                
                fPressed = true;
                game.getPlayer().setMovement(false);
            }
        }

        if (isInBox) {
        	if (mammaTimerTask!=null)
            cancelTimerForMamma();
        }
        else {
        	if (!isInBox && wasInBox) {
        		startTimerForMamma();
        	}
        }
        
        wasInBox = isInBox;
        
        
    }

    //ritorna false se non si ha ancora lo starter, se no ritorna true
    private boolean controllaPresenzaStarter() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.internal("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue poke1 = json.get("poke1");

            if (poke1 != null) {
                String nomePokemon = poke1.getString("nomePokemon", "");

                if (nomePokemon.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    //questa funzione serve per il testo base senza scelta
    public void controlloTestoIniziale() {
        if (tieniApertoDiscorsoPrima) {
            labelDiscorsiSenzaStarter.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                tieniApertoDiscorsoPrima = labelDiscorsiSenzaStarter.advanceText();
            }
        }
        else {
            //quando deve terminare 
            tieniApertoDiscorsoPrima = false;
            fPressed = false;
            game.getPlayer().setMovement(true);
            labelDiscorsiSenzaStarter.reset();
        }
    }

    //questa funzione serve per il testo con la scelta
    public void controlloTesto() {

        if (tieniApertoDiscorsoDopo) {
            labelDiscorsiConStarter.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                tieniApertoDiscorsoDopo = labelDiscorsiConStarter.advanceText();
            }
        }
        else {
            //quando deve terminare 
            tieniApertoDiscorsoDopo = false;
            fPressed = false;
            cura();
            game.getPlayer().setMovement(true);
            labelDiscorsiConStarter.reset();
        }
    }

    public void cura(){
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        
        for (int i=0; i<6; i++){
            int index =i+1;
            JsonValue pokeJson = json.get("poke"+index);
            //System.out.println(index);
            String nomePoke = pokeJson.getString("nomePokemon");
            //System.out.println(index);

            if (!nomePoke.equals("")){
                JsonValue statistiche = pokeJson.get("statistiche"); 
                String maxPokeHP = statistiche.getString("hpTot");
                //ripristina gli hp al massimo
                statistiche.remove("hp");
                statistiche.addChild("hp", new JsonValue(maxPokeHP));
                JsonValue mosse = pokeJson.get("mosse");
                for (JsonValue mossaJson : mosse) {
                    String maxPP = mossaJson.getString("ppTot");
                    // ripristina attPP al massimo per ogni mossa
                    mossaJson.remove("ppAtt");
                    mossaJson.addChild("ppAtt", new JsonValue(maxPP));
                }
            }
        
            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        }
    }

    public void controllaUscita() {
        if(game.getPlayer().getBoxPlayer().overlaps(rectangleUscita)) {
            game.setTeleport("uscitaCasa");
            game.setPage(Constant.MAPPA_SCREEN);
        }
    }


    @Override
    public void dispose() {
        if (casaAsh != null)  {
            casaAsh.dispose();
        }
        if (mammaTimerTask != null) {
            mammaTimerTask.cancel();
        }
        if (mammaAsh != null) {
            mammaAsh.dispose();
        }
        tileRenderer.dispose();
    }

}
