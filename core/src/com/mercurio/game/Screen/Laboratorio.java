package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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

/*
 * mettere un rettangolo di controllo tra le due teche, quando lo si passa e non si ha nessun pkemon si viene bloccati,
 * il professere che si trova di fronte alla pokeball ti viene in contro e ti dice qualcosa, poi torna indietro fino ad essere
 * dalla parte opposta delle pokeball e poi puoi tornare a muoverti, poi bisogna avvicinarsi alle pokeball e parlare per scegliere
 * il pokemon, poi quando si sceglie non succede più nulla.
 * quando si entra e si possiede già un pokemon il professore si trova in un altro punto
 */

public class Laboratorio extends ScreenAdapter{
    private final MercurioMain game;

    //dati per render della mappa
    private TiledMap lab;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;

    private Rectangle rectangleUscita;

    public Laboratorio(MercurioMain game) {
        this.game = game;
        //renderizzo il professore

        rectList = new ArrayList<Rectangle>();
    }

    @Override
    public void show() {
        game.setLuogo("laboratorio");
        game.getMusica().startMusic("labPokemon");

        TmxMapLoader mapLoader = new TmxMapLoader();
        lab = mapLoader.load(Constant.LAB_MAPPA);
        tileRenderer = new OrthogonalTiledMapRenderer(lab);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = lab.getProperties().get("width", Integer.class) * lab.getProperties().get("tilewidth", Integer.class);
        int mapHeight = lab.getProperties().get("height", Integer.class) * lab.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.5f, map_size.y/2.5f);
        camera.update();

        game.setMap(lab, tileRenderer, camera, map_size.x, map_size.y);

        //aggiungere alla lista delle collisioni quella del professore

        game.getPlayer().setPosition(55, 20);

        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = lab.getLayers().get("exit").getObjects();
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        game.setRectangleList(rectList);
        cambiaProfondita(lineeLayer);
        controllaUscita();
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();

        //aggiungo nel background tutto quello che sta sempre dietro
        //background.add("");

        background.add("floor");
        background.add("WallAlwaysBack");
        background.add("AlwaysBack1");
        background.add("AlwaysBack2");
        background.add("tappeto");


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

        /*  da usare in futuro per il professore
        boolean isForeground = false;
        if (game.getPlayer().getPlayerPosition().y < mammaAsh.getPosition().y){
            isForeground = true;
        }
        */

        //background
        for (String layerName : background) {
            renderLayer(layerName);
        }

        /*  da usare in futuro per il professore
        if (isForeground) {
            game.renderPersonaggiSecondari(mammaAsh.getTexture(), mammaAsh.getPosition().x, mammaAsh.getPosition().y, mammaAsh.getWidth(), mammaAsh.getHeight());
        }
        */

        game.renderPlayer();

        //foreground
        for (String layerName : foreground) {
            renderLayer(layerName);
        }

        /*  da usare in futuro per il professore
        if (!isForeground) {
            game.renderPersonaggiSecondari(mammaAsh.getTexture(), mammaAsh.getPosition().x, mammaAsh.getPosition().y, mammaAsh.getWidth(), mammaAsh.getHeight());
        }
        */

    }

    private void renderLayer(String layerName) {
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        MapLayer layer = lab.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
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

    private void controllaUscita() {
        if(game.getPlayer().getBoxPlayer().overlaps(rectangleUscita)) {
            game.setTeleport("uscitaLab");
            game.setPage(Constant.MAPPA_SCREEN);
        }
    }

    @Override
    public void dispose() {
        if (lab != null)  {
            lab.dispose();
        }

        tileRenderer.dispose();
    }

}
