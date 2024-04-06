package com.mercurio.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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



    @Override
    public void create() {
        setPage(Constant.MENU_SCREEN);
        ash = new Ash(this);
        batch = new SpriteBatch();
        
    }

    @Override
    public void render () {

        super.render();

        // Calcolo del tempo trascorso dall'inizio
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (screen_id != 0) {

            float cameraX = MathUtils.clamp(ash.getPlayerPosition().x + ash.getPlayerWidth() / 2, camera.viewportWidth / 2, map_size.x - camera.viewportWidth / 2);
            float cameraY = MathUtils.clamp(ash.getPlayerPosition().y + ash.getPlayerHeight() / 2, camera.viewportHeight / 2, map_size.y - camera.viewportHeight / 2);

            ash.move(collisionLayer);

            // Imposta la posizione della telecamera in modo che segua il giocatore
            camera.position.set(cameraX, cameraY, 0);

            camera.update();

            tileRenderer.setView(camera);
        }

    }

    public void renderPlayer() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(ash.getAnimazione(), ash.getPlayerPosition().x, ash.getPlayerPosition().y);

        batch.end();
    }

    public void renderPersonaggiSecondari(TextureRegion animazione, float x, float y, float width, float height) {
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
                newScreen = new CasaSpawn(this);
                screen_id = 1;
                break;

            default:
                break;
        }

        setScreen (newScreen);
        
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
        return lineeLayer;
    }

    public Ash getPlayer() {
        return ash;
    }

    /*
    //visualizza dettagliato bianco
    public void showInteractionBorder(Object obj) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        if (obj instanceof PolygonMapObject) {
            PolygonMapObject polygonObject = (PolygonMapObject) obj;
            float[] vertices = polygonObject.getPolygon().getTransformedVertices();

            shapeRenderer.polygon(vertices);
        }

        shapeRenderer.end();
    }
    */


    
}
