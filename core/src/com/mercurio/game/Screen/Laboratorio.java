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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Laboratorio extends ScreenAdapter {
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
        rectList = new ArrayList<Rectangle>();
    }


    @Override
    public void show() {

        game.setLuogo("Laboratorio");

        TmxMapLoader mapLoader = new TmxMapLoader();
        lab = mapLoader.load(Constant.LAB_MAPPA);
        tileRenderer = new OrthogonalTiledMapRenderer(lab);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = lab.getProperties().get("width", Integer.class) * lab.getProperties().get("tilewidth", Integer.class);
        int mapHeight = lab.getProperties().get("height", Integer.class) * lab.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.9f, map_size.y/2f);
        camera.update();

        game.setMap(lab, tileRenderer, camera, map_size.x, map_size.y);

        game.getPlayer().setPosition(100, 50);
        
        //prendere rettangolo per mappa
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
    public void resize(int width, int height) {

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

    //cambio continuamente forground e background in base alla pos del personaggio
    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> backbackground = new ArrayList<String>();
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();
        

        backbackground.add("floor");

        background.add("WallAlwaysBack");
        background.add("AlwaysBack1");
        background.add("AlwaysBack2");


        for (MapObject object : lineeLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject)object;

                //salvo il nome del layer che verrà inserito in una delle due liste
                String layerName = (String)rectangleObject.getProperties().get("layer");

                if (game.getPlayer().getPlayerPosition().y < rectangleObject.getRectangle().getY()) {
                    if (layerName == "bancone"){
                        background.add(layerName);
                        background.add("pokebal");
                    }else{
                        background.add(layerName);
                    }
                }else {
                    if (layerName == "bancone"){
                        foreground.add(layerName);
                        foreground.add("pokebal");
                    }else{
                        foreground.add(layerName);
                    }
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

    // Metodo per renderizzare un singolo layer
    private void renderLayer(String layerName) {
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        MapLayer layer = lab.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }

    public void controllaUscita() {
        if(game.getPlayer().getBoxPlayer().overlaps(rectangleUscita)) {
            game.setTeleport("uscitaCasa");
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
