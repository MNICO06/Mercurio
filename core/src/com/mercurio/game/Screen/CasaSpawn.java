package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mercurio.game.personaggi.MammaAsh;

public class CasaSpawn extends ScreenAdapter {
    private final MercurioMain game;
    private MammaAsh mammaAsh;


    private TiledMap casaAsh;
    
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;

    private int speed = 1;

    private Vector2 map_size;

    private MapLayer lineeLayer;



    public CasaSpawn(MercurioMain game) {
        this.game = game;
        mammaAsh = new MammaAsh();
    }

    @Override
    public void show() {

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
        
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        cambiaProfondita(lineeLayer);
        controllaCollisionePorta();
        controllaInterazioni();

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

    //controlla la collisione con la porta per uscire dalla casa
    private void controllaCollisionePorta() {

    }

    //controllo interazioni con oggetti (tecnicamente mamma e poi bho)
    private void controllaInterazioni() {

    }


    @Override
    public void dispose() {
        casaAsh.dispose();
    }

}
