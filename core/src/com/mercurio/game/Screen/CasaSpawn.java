package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class CasaSpawn extends ScreenAdapter {
    private final MercurioMain game;


    private TiledMap casaAsh;
    
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;

    private int speed = 1;

    private Vector2 map_size;


    public CasaSpawn(MercurioMain game) {
        this.game = game;
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
        cambiaProfondita();
        controllaCollisionePorta();
        controllaInterazioni();

    }

    //cambio continuamente forground e background in base alla pos del personaggio
    private void cambiaProfondita() {
        ArrayList<Integer> background = new ArrayList<Integer>();
        ArrayList<Integer> foreground = new ArrayList<Integer>();

        //background
        tileRenderer.render();
        

        game.renderPlayer();

        //foreground
        
    }

    //controlla la collisione con la porta per uscire dalla casa
    private void controllaCollisionePorta() {

    }

    //controllo interazioni con oggetti (tecnicamente mamma e poi bho)
    private void controllaInterazioni() {

    }

    private void muoviMappa() {
        //movimento a sinistra
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate (speed * -1,0,0);
        }
        //movimento a destra
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate (speed,0,0);
        }
        //movimento in su
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate (0,speed,0);
        }
        //movimento in giù
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate (0,speed * -1,0);
        }

    }

    @Override
    public void dispose() {
        casaAsh.dispose();
    }

}
