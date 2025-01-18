package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mercurio.game.personaggi.Commesso;
import com.mercurio.game.utility.MapsAbstract;

public class PokeMarket2 extends MapsAbstract {
    private final MercurioMain game;

    // dati per render della mappa
    private TiledMap pokeMarket;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private Commesso commesso;

    // private SpriteBatch batch;
    Vector3 screenPosition;

    // rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    List<Render> render = new ArrayList<>();
    ArrayList<String> listaAllawaysBack = new ArrayList<String>();
    private List<Render> renderBot = new ArrayList<>();

    private Rectangle rectangleUscita;

    public PokeMarket2(MercurioMain game) {
        super(game);
        this.game = game;

        rectList = new ArrayList<Rectangle>();
        commesso = new Commesso(game);

    }

    @Override
    public void show() {
        try {

            game.setLuogo("pokeCenter1");
            game.getMusica().startMusic("pokeCenter");

            TmxMapLoader mapLoader = new TmxMapLoader();
            pokeMarket = mapLoader.load(Constant.POKEMARKET2_MAP);
            tileRenderer = new OrthogonalTiledMapRenderer(pokeMarket);
            settaCommessoPosition();

            // calcolo e assegno dimensioni alla mappa
            int mapWidth = pokeMarket.getProperties().get("width", Integer.class)
                    * pokeMarket.getProperties().get("tilewidth", Integer.class);
            int mapHeight = pokeMarket.getProperties().get("height", Integer.class)
                    * pokeMarket.getProperties().get("tileheight", Integer.class);
            map_size = new Vector2(mapWidth, mapHeight);

            // creo la camera
            camera = new OrthographicCamera();
            camera.setToOrtho(false, map_size.x / 1.8f, map_size.y / 1.8f);
            camera.update();

            game.setMap(pokeMarket, tileRenderer, camera, map_size.x, map_size.y);

            listaAllawaysBack.add("floor");
            listaAllawaysBack.add("WallAlwaysBack");
            listaAllawaysBack.add("AlwaysBack");
            listaAllawaysBack.add("tappeto");
            listaAllawaysBack.add("bancone");
            listaAllawaysBack.add("cassa");
            listaAllawaysBack.add("camino");
            listaAllawaysBack.add("orsetto");
            game.aggiornaListaAllawaysBack(listaAllawaysBack);

            settaPlayerPosition();

            // recupero il rettangolo per uscire dalla mappa
            MapObjects objects = pokeMarket.getLayers().get("exit").getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    // Se l'oggetto è un rettangolo
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;

                    // Ottieni il rettangolo
                    rectangleUscita = rectangleObject.getRectangle();

                }
            }


        } catch (Exception e) {
            System.out.println("Errore show PokeMarket2, " + e);
        }
    }


    //TODO: da verificare se esiste veramente questo rettangolo
    private void settaCommessoPosition() {
        try {
            // recupero il rettangolo per uscire dalla mappa
            MapObjects objects = pokeMarket.getLayers().get("posizioneCasse").getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;
                    Rectangle rect = rectangleObject.getRectangle();
                    commesso.setPosition(rect.getX(), rect.getY());
                }
            }

            cambiaBot();

        } catch (Exception e) {
            System.out.println("Errore settaCommessiPosition pokeMarket1, " + e);
        }

    }

    @Override
    protected void settaPlayerPosition() {
        try {
            // recupero il rettangolo per uscire dalla mappa
            MapObjects objects = pokeMarket.getLayers().get("teleport").getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    // Se l'oggetto è un rettangolo
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;

                    // Ottieni il rettangolo
                    game.getPlayer().setPosition(rectangleObject.getRectangle().getX(),
                            rectangleObject.getRectangle().getY());
                    rectangleUscita = rectangleObject.getRectangle();

                }
            }
        } catch (Exception e) {
            System.out.println("Errore settaPlayerPosition pokemarket2, " + e);
        }

    }

    @Override
    public void render(float delta) {
        try {
            game.setRectangleList(rectList);
            game.addBotRender(renderBot);

            controllaUscita();
        } catch (Exception e) {
            System.out.println("Errore render pokemarket2, " + e);
        }

    }

    private void cambiaBot() {
        renderBot.clear();
        renderBot.add(new Render("bot", commesso.getTexture(), commesso.getPosition().x, commesso.getPosition().y, commesso.getWidth(), commesso.getHeight(), "commesso"));
    }

    @Override
    public void dispose() {
        if (pokeMarket != null) {
            pokeMarket.dispose();
        }

        tileRenderer.dispose();
    }
}
