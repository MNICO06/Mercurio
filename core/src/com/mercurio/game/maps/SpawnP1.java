package com.mercurio.game.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.Screen.Constant;
import com.mercurio.game.Screen.MercurioMain;
import com.mercurio.game.Screen.Render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpawnP1 extends ScreenAdapter {
    private final MercurioMain game;
    public GameAsset asset;

    private TiledMap map;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;
    private Stage stage;

    // rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    private List<Render> render = new ArrayList<>();
    private ArrayList<String> listaAllawaysBack = new ArrayList<String>();


    public SpawnP1(MercurioMain game) {
        this.game = game;
        this.asset = new GameAsset();
        stage = new Stage();



    }

    @Override
    public void show() {
        try{

            //game.setLuogo(""); da capire come fare visto che ci sono 2 luoghi diversi

            //carico la mappa e gli do le dimensioni corrette
            TmxMapLoader mapLoader = new TmxMapLoader();
            map = mapLoader.load(Constant.LAB_MAPPA); //da cambiare con la futura mappa giusta
            tileRenderer = new OrthogonalTiledMapRenderer(map);

            // calcolo e assegno dimensioni alla mappa
            int mapWidth = map.getProperties().get("width", Integer.class)
                    * map.getProperties().get("tilewidth", Integer.class);
            int mapHeight = map.getProperties().get("height", Integer.class)
                    * map.getProperties().get("tileheight", Integer.class);
            map_size = new Vector2(mapWidth, mapHeight);

            // creo la camera
            camera = new OrthographicCamera();
            camera.setToOrtho(false, map_size.x / 1.5f, map_size.y / 2.5f);
            camera.update();

            game.setMap(map, tileRenderer, camera, map_size.x, map_size.y);

            rectList = new ArrayList<Rectangle>();

        } catch (RuntimeException e) {
            System.out.println("Errore show spawnP1, " + e);
        }
    }

    @Override
    public void render(float delta) {
        try{

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            lineeLayer = game.getLineeLayer();
            game.setRectangleList(rectList);
            cambiaProfondita(lineeLayer);


            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime);
            stage.draw();

        } catch (RuntimeException e) {
            System.out.println("Errore show spawnP1, " + e);
        }
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        try {

            // pulisco l'array in modo tale che non pesi
            render.clear();

            // inserisci i vari layer nella lista
            for (MapObject object : lineeLayer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;
                    String layerName = (String) rectangleObject.getProperties().get("layer");
                    float y = rectangleObject.getRectangle().getY();
                    render.add(new Render("layer", layerName, y));
                }
            }

            render.add(new Render("player", game.getPlayer().getPlayerPosition().y));

            // Ordina in base alla posizione `y`
            Collections.sort(render, Comparator.comparingDouble(r -> -r.y));

            // background
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

                        break;
                    case "player":
                        game.renderPlayer();
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Errore cambiaProfondita SpawnP1, " + e);
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
            System.out.println("Errore renderLayer laboratorio, " + e);
        }
    }

    @Override
    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        tileRenderer.dispose();
    }

}
