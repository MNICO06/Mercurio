package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class FullMap extends ScreenAdapter{
    private final MercurioMain game;
    
    private TiledMap mappa;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    public FullMap(MercurioMain game, TiledMap mappa) {
        this.game = game;
        this.mappa = mappa;
    }

    
    @Override
    public void show() {
        tileRenderer = new OrthogonalTiledMapRenderer(mappa);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = mappa.getProperties().get("width", Integer.class) * mappa.getProperties().get("tilewidth", Integer.class);
        int mapHeight = mappa.getProperties().get("height", Integer.class) * mappa.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/38f, map_size.y/40f);
        camera.update();
        game.getPlayer().setPosition(3000,100);

        setPositionPlayer();

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
        float y;

        background.add("Livello tile ground");
        background.add("Livello tile case");
        background.add("Livello tile deco2");
        background.add("Livello tile deco1");
        background.add("Livello tile deco");
        background.add("Livello tile path");
        background.add("Livello tile piantine");
        background.add("AlberiMezzo");
        background.add("FixingLayer2");
        background.add("AlberiFondo");
        background.add("FixingLayer1");
        background.add("AlberiCima");
        //background.add("");

        for (MapObject obj : lineeLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject)obj;

                String layerName = (String)rectObj.getProperties().get("layer");

                y = rectObj.getRectangle().getY() - game.getPlayer().getPlayerPosition().y;
                if (y > 0 && y < 200) {
                    background.add(layerName);
                    
                }
                else if (y<0 && y > -200) {
                    foreground.add(layerName);
                }
            }
        }

        System.out.println(background+ "\n \n \n \n");
        System.out.println(foreground + "\n \n \n \n");

        tileRenderer.render();
        game.renderPlayer();
    }

    public void setPositionPlayer() {
        String luogo = game.getTeleport();
        float x = 100;
        float y = 3000;
        MapLayer uscita = mappa.getLayers().get("uscita");
        for (MapObject object : uscita.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                
                if (object.getName().equals(luogo)) {
                    x = rect.getX();
                    y = rect.getY();
                }
            }
        }
        game.getPlayer().setPosition(x, y);
        
    }

}
