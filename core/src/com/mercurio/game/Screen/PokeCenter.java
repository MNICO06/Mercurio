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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.personaggi.Dottoressa;

import com.badlogic.gdx.utils.Timer;

public class PokeCenter extends ScreenAdapter {
    private final MercurioMain game;

    //dati per render della mappa
    private TiledMap pokeCenterMap;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;
    private float xPosition;
    private float yPosition;
    private int risposta = -1;

    private Dottoressa dottoressa;
    private LabelDiscorsi discorso;

    private boolean renderTesto = true;
    private boolean continuaTesto = true;
    private boolean muoviPlayer = false;

    private boolean nelBox = false;
    private boolean renderizzaTesto = false;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;

    public PokeCenter(MercurioMain game) {
        this.game = game;
        dottoressa = new Dottoressa();
    }

    @Override
    public void show() {
        TmxMapLoader mapLoader = new TmxMapLoader();
        pokeCenterMap = mapLoader.load(Constant.CENTRO_POKEMON);
        tileRenderer = new OrthogonalTiledMapRenderer(pokeCenterMap);
        
        //calcolo e assegno dimensioni alla mappa
        int mapWidth = pokeCenterMap.getProperties().get("width", Integer.class) * pokeCenterMap.getProperties().get("tilewidth", Integer.class);
        int mapHeight = pokeCenterMap.getProperties().get("height", Integer.class) * pokeCenterMap.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.9f, map_size.y/2f);
        camera.update();

        game.setMap(pokeCenterMap, tileRenderer, camera, map_size.x, map_size.y);

        getPostionDoctor();

        game.getMusica().startMusic(game.getLuogo());

        setPosition();

        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lineeLayer = game.getLineeLayer();
        cambiaProfondita(lineeLayer);
        esci();
        checkCure();
        checkBox();
        if (renderizzaTesto) {
            checkTesto();
        }
        if (muoviPlayer) {
            muoviPlayerPosition();
        }
    }

    private void muoviPlayerPosition() {
        game.getPlayer().setMovement(true);
        muoviPlayer = false;
    }

    public void getPostionDoctor() {
        MapLayer layerTeleport = pokeCenterMap.getLayers().get("posDottoressa");
        MapObject obj = layerTeleport.getObjects().get("posizioneDott");
        if (obj instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) obj;
            Rectangle rect = rectObject.getRectangle();
            dottoressa.setPosition(rect.getX(), rect.getY());
        }
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();
        ArrayList<String> backbackground = new ArrayList<String>();

        backbackground.add("floor");

        background.add("WallAlwaysBack");
        background.add("AlwaysBack_1");
        background.add("bancone");
        background.add("computer");
        background.add("deco bancone");
        

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

        
        //pavimento
        for (String LayerName : backbackground) {
            renderLayer(LayerName);
        }

        game.renderPersonaggiSecondari(dottoressa.getTexture(), dottoressa.getPosition().x, dottoressa.getPosition().y, dottoressa.getWidth(), dottoressa.getHeight());

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
        MapLayer layer = pokeCenterMap.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }

    public void checkCure() {
        MapLayer layerTeleport = pokeCenterMap.getLayers().get("cura");
        MapObject obj = layerTeleport.getObjects().get("curaPokemon");
        if (obj instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) obj;
            Rectangle rect = rectObject.getRectangle();
            if (game.getPlayer().getBoxPlayer().overlaps(rect)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    discorso = new LabelDiscorsi("Benvenuto! Questo e' un centro pokemon! riportero' i tuoi pokemon in perfetta forma in un batter d'occhio! Vuoi che mi prenda cura dei tuoi pokemon??", 30, 0, false, true);
                    renderizzaTesto = true;
                    game.getPlayer().setMovement(false);
                }
            }
        }
    }

    public void checkBox() {
        MapLayer layerTeleport = pokeCenterMap.getLayers().get("box");
        MapObject obj = layerTeleport.getObjects().get("box");
        if (obj instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) obj;
            Rectangle rect = rectObject.getRectangle();
            if (game.getPlayer().getBoxPlayer().overlaps(rect)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    if (!nelBox) {
                        nelBox = true;
                        game.creaBox();
                    } else {
                        game.closeBox();
                        nelBox = false;
                    }
                   
                }
            }
        }
    }

    private void checkTesto() {

        if (renderTesto) {

            risposta = discorso.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                discorso.advanceText();
            }

            if (risposta != -1) {
                if (risposta == 1) {
                    dottoressa.cura();
                    renderTesto = false;
                    discorso.setSceltaUtente(-1);
                }
                else if (risposta == 0) {
                    renderTesto = false;
                    discorso.setSceltaUtente(-1);
                }
            }

        }
        else {
            renderizzaTesto = false;
            renderTesto = true;
            game.getPlayer().setMovement(true);
            discorso.reset();
            discorso = null;
        }
    }

    public void setPosition() {
        MapLayer layerTeleport = pokeCenterMap.getLayers().get("teleport");
        MapObject obj = layerTeleport.getObjects().get("entra");
        if (obj instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) obj;
            Rectangle rect = rectObject.getRectangle();
            game.getPlayer().setPosition(rect.getX(), rect.getY());
        }
    }

    public void esci() {
        MapLayer layerTeleport = pokeCenterMap.getLayers().get("exit");
        MapObject obj = layerTeleport.getObjects().get("uscita");
        if (obj instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) obj;
            Rectangle rect = rectObject.getRectangle();
            if (game.getPlayer().getBoxPlayer().overlaps(rect)) {
                if (game.getIngressoPokeCenter().equals("cittaRoccia")) {
                    game.setIngressoCittaMontagna("ingressoDaPokecenter");
                    game.setPage(Constant.CITTAMONTAGNA);
                }else {
                    game.setTeleport(game.getIngressoPokeCenter());
                game.setPage(Constant.MAPPA_SCREEN);
                }
            }
        }
    }

    @Override
    public void dispose() {
        pokeCenterMap.dispose();
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

}