package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CittaMontagna extends ScreenAdapter{
    private final MercurioMain game;

    //dati per render della mappa
    private TiledMap cittaGrotta;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    private SpriteBatch batch;
    private Stage stage;
    Vector3 screenPosition;


    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    List<Render> render = new ArrayList<>();
    ArrayList<String> listaAllawaysBack = new ArrayList<String>();

    public CittaMontagna(MercurioMain game) {
        this.game = game;

        rectList = new ArrayList<Rectangle>();
        batch = new SpriteBatch();

        listaAllawaysBack.add("Ground");
        listaAllawaysBack.add("alwaysBack1");
        listaAllawaysBack.add("alwaysBack2");
    }
    
    @Override
    public void show() {
        game.setLuogo("cittamontagna");
        game.getMusica().startMusic("pokeCenter");

        TmxMapLoader mapLoader = new TmxMapLoader();
        cittaGrotta = mapLoader.load(Constant.CITTAMONTAGNA_MAP);
        tileRenderer = new OrthogonalTiledMapRenderer(cittaGrotta);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = cittaGrotta.getProperties().get("width", Integer.class) * cittaGrotta.getProperties().get("tilewidth", Integer.class);
        int mapHeight = cittaGrotta.getProperties().get("height", Integer.class) * cittaGrotta.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/8f, map_size.y/9f);
        camera.update();

        game.setMap(cittaGrotta, tileRenderer, camera, map_size.x, map_size.y);

        //aggiungere alla lista delle collisioni quella del professore

        settaPlayerPotion();

    }

    private void settaPlayerPotion() {
        if (game.getIngressoCittaMontagna() != null) {
            MapObjects objects = cittaGrotta.getLayers().get("teleport").getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    // Se l'oggetto è un rettangolo
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;

                    //METTO IN INGRESSO CITTà MONTGNA IL NOME EFFETTIVO DEL RETTANGOLO IL CUI DEVO ESSERE TELETRASPORTATO
    
                    if (game.getIngressoCittaMontagna().equals(object.getName())) {
    
                        game.getPlayer().setPosition(rectangleObject.getRectangle().getX(), rectangleObject.getRectangle().getY());
                    }
                } 
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
        //pulisco l'array in modo tale che non pesi
        render.clear();

        //inserisci i vari layer nella lista
        for (MapObject object : lineeLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                String layerName = (String) rectangleObject.getProperties().get("layer");
                float y = rectangleObject.getRectangle().getY();
                render.add(new Render("layer", layerName, y));
            }
        }

        // Inserisci eventuali personaggio e il giocatore
        render.add(new Render("player", game.getPlayer().getPlayerPosition().y));



        // Ordina in base alla posizione `y`
        Collections.sort(render, Comparator.comparingDouble(r -> -r.y));

        //background
        for (String layerName : listaAllawaysBack) {
            renderLayer(layerName);
        }

        // Renderizza nell'ordine corretto
        for (Render renderComponent : render) {
            switch (renderComponent.type) {
                case "layer":
                    renderLayer(renderComponent.layerName);
                    break;
                case "bot":
                    //quando si mettono i player gestirli qua
                    break;
                case "player":
                    game.renderPlayer();
                    break;
            }
        }
    }

    //funzione per il rendering dei layer
    private void renderLayer(String layerName) {

        tileRenderer.getBatch().begin();

        MapLayer layer;
        String nomeFolder = findGroupByLayerName(cittaGrotta, layerName);

        // Recupera il layer dalla mappa
        if (nomeFolder != null) {
            layer = findLayerInGroup(cittaGrotta, nomeFolder, layerName);
        }
        else {
            layer = cittaGrotta.getLayers().get(layerName);
            
        }

        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer); 
        
        tileRenderer.getBatch().end();
    }

    private String findGroupByLayerName(TiledMap map, String layerName) {
        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer instanceof MapGroupLayer) {
                MapGroupLayer groupLayer = (MapGroupLayer) mapLayer;
                for (MapLayer subLayer : groupLayer.getLayers()) {
                    if (subLayer.getName().equals(layerName)) {
                        return groupLayer.getName();
                    }
                }
            }
        }
        return null; // Se il layer non è all'interno di alcuna cartella
    }

    private MapLayer findLayerInGroup(TiledMap map, String groupName, String layerName) {
        MapLayer groupLayer = map.getLayers().get(groupName);
        if (groupLayer instanceof MapGroupLayer) {
            MapGroupLayer mapGroupLayer = (MapGroupLayer) groupLayer;
            for (MapLayer mapLayer : mapGroupLayer.getLayers()) {
                if (mapLayer.getName().equals(layerName)) {
                    return mapLayer;
                }
            }
        }
        return null;
    }


    private void controllaUscita() {
        
        //MODOFICARE QUESTO PER CONSIDERARE VARIE POSSIBILITA'
        MapObjects objects = cittaGrotta.getLayers().get("exit").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    
                    if (object.getName().equals("teleportGrotta")) {

                        game.setIngressoGrotta("ingressoCitta");
                        game.setPage(Constant.GROTTA);

                    }else if (object.getName().equals("teleportPokecenter")) {

                        game.setIngressoPokeCenter("cittaRoccia");
                        game.setPage(Constant.CENTRO_POKEMON_ROCCIA);

                    }else if (object.getName().equals("teleportShop")) {
                        //per ora non metto nulla
                    }else {

                        game.setPage(object.getName());
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        if (cittaGrotta != null)  {
            cittaGrotta.dispose();
        }

        tileRenderer.dispose();
    }
}
