package com.mercurio.game.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.Screen.Constant;
import com.mercurio.game.Screen.MercurioMain;

import java.util.ArrayList;

public class SpawnP1 extends ScreenAdapter {
    private final MercurioMain game;
    public GameAsset asset;


    private Stage stage;

    // rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;

    public SpawnP1(MercurioMain game) {
        this.game = game;
        this.asset = new GameAsset();
        stage = new Stage();

        rectList = new ArrayList<Rectangle>();

    }

    @Override
    public void show() {
        try{

            //game.setLuogo(""); da capire come fare visto che ci sono 2 luoghi diversi

            //carico la mappa e gli do le dimensioni corrette
            TmxMapLoader mapLoader = new TmxMapLoader();
            TiledMap map = mapLoader.load(Constant.SPAWN_MAP); //da cambiare con la futura mappa giusta
            OrthogonalTiledMapRenderer tileRenderer = new OrthogonalTiledMapRenderer(map);

            // calcolo e assegno dimensioni alla mappa
            int mapWidth = map.getProperties().get("width", Integer.class)
                    * map.getProperties().get("tilewidth", Integer.class);
            int mapHeight = map.getProperties().get("height", Integer.class)
                    * map.getProperties().get("tileheight", Integer.class);
            Vector2 map_size = new Vector2(mapWidth, mapHeight);

            // creo la camera
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false, map_size.x / 1.5f, map_size.y / 2.5f);
            camera.update();

            game.setMap(map, tileRenderer, camera, map_size.x, map_size.y);

        } catch (RuntimeException e) {
            System.out.println("Errore show spawnP1, " + e);
        }
    }

    @Override
    public void render(float delta) {
        try{

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.setRectangleList(rectList);

            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime);
            stage.draw();

        } catch (RuntimeException e) {
            System.out.println("Errore show spawnP1, " + e);
        }
    }


    @Override
    public void dispose() {
        
    }

}
