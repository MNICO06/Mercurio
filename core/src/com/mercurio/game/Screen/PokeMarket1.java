package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


public class PokeMarket1 extends ScreenAdapter {
    private final MercurioMain game;

    //dati per render della mappa
    private TiledMap pokeMarket;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    private boolean nelloShop = false;

    private SpriteBatch batch;
    private Stage stage;
    Vector3 screenPosition;


    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    List<Render> render = new ArrayList<>();
    ArrayList<String> listaAllawaysBack = new ArrayList<String>();

    private Rectangle rectangleUscita;

    public PokeMarket1(MercurioMain game) {
        this.game = game;

        rectList = new ArrayList<Rectangle>();
        batch = new SpriteBatch();


        listaAllawaysBack.add("floor");
        listaAllawaysBack.add("tappeto");
        listaAllawaysBack.add("WallAlwaysBack");
        listaAllawaysBack.add("AlwaysBack");
        listaAllawaysBack.add("AlwaysBack2");

    }

    @Override
    public void show() {
        game.setLuogo("pokeCenter1");
        game.getMusica().startMusic("pokeCenter");

        TmxMapLoader mapLoader = new TmxMapLoader();
        pokeMarket = mapLoader.load(Constant.POKEMARKET1_MAP);
        tileRenderer = new OrthogonalTiledMapRenderer(pokeMarket);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = pokeMarket.getProperties().get("width", Integer.class) * pokeMarket.getProperties().get("tilewidth", Integer.class);
        int mapHeight = pokeMarket.getProperties().get("height", Integer.class) * pokeMarket.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.8f, map_size.y/1.8f);
        camera.update();

        game.setMap(pokeMarket, tileRenderer, camera, map_size.x, map_size.y);

        //aggiungere alla lista delle collisioni quella del professore

        settaPlayerPotion();


        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = pokeMarket.getLayers().get("exit").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                // Ottieni il rettangolo
                rectangleUscita = rectangleObject.getRectangle();

            } 
        }
    }

    private void settaPlayerPotion() {
        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = pokeMarket.getLayers().get("teleport").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                // Ottieni il rettangolo
                game.getPlayer().setPosition(rectangleObject.getRectangle().getX(), rectangleObject.getRectangle().getY());
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
        controllaShop();
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
        
        // Recupera il layer dalla mappa
        MapLayer layer = pokeMarket.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }


    private void controllaUscita() {
        if (rectangleUscita != null) {
            if (game.getPlayer().getBoxPlayer().overlaps(rectangleUscita)) {
                game.setTeleport("uscitaPokeMarketC");
                game.setPage(Constant.MAPPA_SCREEN);
            }
        }
    }

    public void controllaShop() {
        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = pokeMarket.getLayers().get("market").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    
                    if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                        if (!nelloShop) {
                            nelloShop = true;
                            game.creaShop();
                        }else {
                            nelloShop = false;
                            game.closeShop();
                        }
                    }
                }


            } 
        }
    }

    @Override
    public void dispose() {
        if (pokeMarket != null)  {
            pokeMarket.dispose();
        }

        tileRenderer.dispose();
    }
}
