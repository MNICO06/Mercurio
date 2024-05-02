package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.personaggi.Bot;
import com.mercurio.game.personaggi.TeenagerF;
import com.mercurio.game.personaggi.TeenagerM;


public class FullMap extends ScreenAdapter{
    private final MercurioMain game;
    
    private TiledMap mappa;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    private ArrayList<Bot> botList;

    public FullMap(MercurioMain game, TiledMap mappa) {
        this.game = game;
        this.mappa = mappa;
        rectList = new ArrayList<Rectangle>();
        botList = new ArrayList<Bot>();
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

        setPositionPlayer();
        setPositionBot();

        //esempio poi da fare con il ciclo per prendere tutte
        //rectList.add(botList.get(0).getBoxBlocca());

        game.setMap(mappa, tileRenderer, camera, map_size.x, map_size.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        cambiaProfondita(lineeLayer);
        teleport();
        checkLuogo();
        game.setRectangleList(rectList);
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();
        float y;
        float x;

        background.add("Livello tile ground");
        background.add("Livello tile path");
        background.add("Livello tile case");
        background.add("Livello tile deco2");
        background.add("Livello tile deco1");
        background.add("Livello tile deco");
        background.add("Livello tile piantine");

        background.add("AlberiCima");
        background.add("FixingLayer1");
        background.add("AlberiFondo");
        background.add("FixingLayer2");
        background.add("AlberiMezzo");
        
        //background.add("");

        for (MapObject obj : lineeLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject)obj;

                String layerName = (String)rectObj.getProperties().get("layer");

                y = rectObj.getRectangle().getY() - game.getPlayer().getPlayerPosition().y;
                x = rectObj.getRectangle().getX() - game.getPlayer().getPlayerPosition().x;
                if (y > 0 && y < 250 && x > -500 && x < 500) {
                    background.add(layerName);
                }

                else if (y<0 && y > -250 && x > -500 && x < 500) {
                    foreground.add(layerName);
                }
            }
        }

        //background
        for (String layerName : background) {
            if (layerName != null) {
                renderLayer(layerName);
            }
        }

        for (Bot bot : botList) {
            game.renderPersonaggiSecondari(bot.getCurrentAnimation(),bot.getPosition().x, bot.getPosition().y, bot.getWidth(), bot.getHeight());
        }
        

        game.renderPlayer();

        //foreground
        for (String layerName : foreground) {
            if (layerName != null) {
                renderLayer(layerName);
            }
        }
    }

    private void renderLayer(String layerName) {
        MapLayer layer;
        String nomeFolder = findGroupByLayerName(mappa, layerName);
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        if (nomeFolder != null) {
            layer = findLayerInGroup(mappa, nomeFolder, layerName);
        }
        else {
            layer = mappa.getLayers().get(layerName);
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

    public void checkLuogo() {
        MapObjects objects = mappa.getLayers().get("controlloLuogo").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    game.setLuogo(rectangleObject.getName());
                    game.getMusica().startMusic(rectangleObject.getName());
                }
            }
        }
    }

    public void teleport() {
        MapObjects objects = mappa.getLayers().get("teleport").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    change(rectangleObject);
                }
            } 
        }
    }

    public void change(RectangleMapObject rectangleObject) {
        game.setPage(rectangleObject.getName());
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

    public void setPositionBot() {
        MapLayer posizione = mappa.getLayers().get("p1_bot");
        for (MapObject object : posizione.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();

                switch (object.getName()) {
                    case "TeenagerM":
                        TeenagerM bot = new TeenagerM();
                        bot.setPosition(rect.getX(),rect.getY());
                        botList.add(bot);
                        //assegno al layer che mi da la poszione un layer che ha il nome del layer della collisione
                        break;
                    
                    case "TeenagerF":
                        TeenagerF bot1 = new TeenagerF();
                        bot1.setPosition(rect.getX(),rect.getY());
                        botList.add(bot1);
                        break;
                
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void dispose() {
        //mappa.dispose();
    }

}
