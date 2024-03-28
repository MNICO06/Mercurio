package com.mercurio.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mercurio.game.personaggi.Ash;


public class MercurioMain extends Game{

    private ShapeRenderer shapeRenderer;

    //dimensioni
    private int screen_id;


    Ash ash;



    @Override
    public void create() {
        setPage(Constant.MENU_SCREEN);

        
    }

    public void render (Stage stage) {

    }

    public void renderPlayer() {

    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
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
