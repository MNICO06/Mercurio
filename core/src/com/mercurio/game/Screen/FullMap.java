package com.mercurio.game.Screen;

import java.util.ArrayList;

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
import com.badlogic.gdx.math.Vector2;

public class FullMap extends ScreenAdapter{
    private final MercurioMain game;
    
    private TiledMap mappa;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    public FullMap(MercurioMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        TmxMapLoader mapLoader = new TmxMapLoader();
        mappa = mapLoader.load(Constant.MAPPA);
        tileRenderer = new OrthogonalTiledMapRenderer(mappa);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = mappa.getProperties().get("width", Integer.class) * mappa.getProperties().get("tilewidth", Integer.class);
        int mapHeight = mappa.getProperties().get("height", Integer.class) * mappa.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.9f, map_size.y/2f);
        camera.update();

        game.setMap(mappa, tileRenderer, camera, map_size.x, map_size.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        cambiaProfondita(lineeLayer);
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();

        background.add("Livello tile ground");
        background.add("Livello tile case");
        background.add("Livello tile deco2");
        background.add("Livello tile deco1");
        background.add("Livello tile deco");
        background.add("Livello tile path");
        background.add("Livello tile piantine");
        background.add("AlberiMezzo");
        background.add("AlberiFondo");
        background.add("FixingLayer1");

        foreground.add("AlberiCima");

        
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

        //background
        for (String layerName : background) {
            renderLayer(layerName);
        }

        game.renderPlayer();

        //foreground
        for (String layerName : foreground) {
            renderLayer(layerName);
        }

    }

    private void renderLayer(String layerName) {
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        MapLayer layer = mappa.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }
}
