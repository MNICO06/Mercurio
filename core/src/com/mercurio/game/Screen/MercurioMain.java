package com.mercurio.game.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.personaggi.Ash;


public class MercurioMain extends Game{

    private ShapeRenderer shapeRenderer;

    private int screen_id;

    private TiledMap map;

    private Vector2 map_size;

    private OrthogonalTiledMapRenderer tileRenderer;

    Ash ash;

    private OrthographicCamera camera;

    private MapLayer collisionLayer;
    private MapLayer lineeLayer;

    private float elapsedTime = 0;

    private SpriteBatch batch;

    private ArrayList<Rectangle> rectList = null;

    private MenuLabel menuLabel;

    private Stage stage;
    private TiledMap mappa;

    private String teleport;

    private Screen currentScreen;
    
    @Override
    public void create() {
        ash = new Ash(this);
        batch = new SpriteBatch();
        menuLabel = new MenuLabel();
        setPage(Constant.SCHERMATA_LOGO);
        

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Carica la mappa
                TmxMapLoader mapLoader = new TmxMapLoader();
                mappa = mapLoader.load(Constant.MAPPA);

                setPage(Constant.MENU_SCREEN);
            }
        }, 1); // Ritarda di 2 secondi (puoi modificare questo valore)
    }

    @Override
    public void render () {

        super.render();

        // Calcolo del tempo trascorso dall'inizio
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (screen_id != 0) {

            float cameraX = MathUtils.clamp(ash.getPlayerPosition().x + ash.getPlayerWidth() / 2, camera.viewportWidth / 2, map_size.x - camera.viewportWidth / 2);
            float cameraY = MathUtils.clamp(ash.getPlayerPosition().y + ash.getPlayerHeight() / 2, camera.viewportHeight / 2, map_size.y - camera.viewportHeight / 2);

            ash.move(collisionLayer, rectList);

            menuLabel.render();
            
            // Imposta la posizione della telecamera in modo che segua il giocatore
            camera.position.set(cameraX, cameraY, 0);

            camera.update();

            tileRenderer.setView(camera);

        }
    }


    public void setRectangleList(ArrayList<Rectangle> rectList) {
        this.rectList = rectList;
    }

    public void renderPlayer() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(ash.getAnimazione(), ash.getPlayerPosition().x, ash.getPlayerPosition().y);

        batch.end();
    }

    public void renderPersonaggiSecondari(TextureRegion animazione, float x, float y, float width, float height) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(animazione, x, y, width, height);

        batch.end();

    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (ash != null) {
            ash.dispose();
        }
        currentScreen.dispose();
        map.dispose();
    }

    //avvia un altra scheda
    public void setPage(String screen) {
        Screen newScreen = null;
        switch(screen) {
            case Constant.MENU_SCREEN:
                newScreen = new Menu(this);
                screen_id = 0;
                break;

            case Constant.CASA_ASH_SCREEN:
                System.out.println("no");
                newScreen = new CasaSpawn(this);
                screen_id = 1;
                break;

                case Constant.CENTRO_POKEMON_SCREEN:
                newScreen = new PokeCenter(this);
                screen_id = 2;
                break;

                case Constant.MAPPA_SCREEN:
                newScreen = new FullMap(this, mappa);
                screen_id = 3;
                break;

                case Constant.SCHERMATA_LOGO:
                newScreen = new SchermataLogo(this);
                setPage(Constant.MENU_SCREEN);
                break;

            default:
                break;
        }

        if (newScreen != null) {
            if (currentScreen != null) {
                currentScreen.dispose();
            }

            currentScreen = newScreen;

            setScreen (newScreen);

        }
        
    }

    //carica gioco con i vecchi dati
    public void loadGame(String path) {

    }

    public void setMap(TiledMap map, OrthogonalTiledMapRenderer render, OrthographicCamera camera, float larghezza, float altezza) {
        this.map = map;
        map_size = new Vector2(larghezza, altezza);
        tileRenderer = render;
        this.camera = camera;
        camera.update();

        //prendo il layer delle collisioni
        try {
            collisionLayer = map.getLayers().get("collisioni");
            lineeLayer = map.getLayers().get("linee");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public MapLayer getLineeLayer() {
        if (lineeLayer != null) {
            return lineeLayer;
        }
        return null;
    }

    public Ash getPlayer() {
        if (ash != null) {
            return ash;
        }
        return null;
    }

    public void setTeleport(String teleport) {
        this.teleport = teleport;
    }

    public String getTeleport() {
        return teleport;
    }
 
}
