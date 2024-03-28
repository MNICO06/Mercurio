package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mercurio.game.personaggi.Ash;

public class CasaSpawn extends ScreenAdapter{
    private final MercurioMain game;


    private TiledMap casaAsh;
    private MapLayer collisionLayer;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Ash ash;

    private int speed = 1;

    public CasaSpawn(MercurioMain game) {
        this.game = game;
        this.ash = new Ash();
        
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(100, 100);
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.setToOrtho(false, Gdx.graphics.getWidth()  , Gdx.graphics.getHeight());

        TmxMapLoader mapLoader = new TmxMapLoader();
        casaAsh = mapLoader.load(Constant.CASA_ASH);

        //prendo il layer delle collisioni
        collisionLayer = casaAsh.getLayers().get("collisioni");
        ash.gestioneCollisioni(collisionLayer);

        tileRenderer = new OrthogonalTiledMapRenderer(casaAsh);
        float scale = 0.2f;
        camera.zoom = scale;
        camera.position.set(220,220,0);
        camera.update();
        
        
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tileRenderer.setView(camera);
        tileRenderer.render();

        batch.begin();
        ash.render(batch);
        batch.end();


        muoviMappa();
        camera.update();

        /* accedere a posizioni per controllo collisioni
        camera.position.x
        camera.position.y
        */

    }

    //cambio continuamente forground e background in base alla pos del personaggio
    private void cambiaCollisioni() {
        
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
