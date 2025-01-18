package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.personaggi.Dottoressa;
import com.mercurio.game.utility.MapsAbstract;
import com.mercurio.game.utility.UtilityFunctions;

public class PokeCenter extends MapsAbstract {
    private final MercurioMain game;
    private List<Render> renderBot = new ArrayList<>();
    ArrayList<String> background = new ArrayList<String>();

    // dati per render della mappa
    private TiledMap pokeCenterMap;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private float xPosition;
    private float yPosition;
    private int risposta = -1;

    private Dottoressa dottoressa;
    private UtilityFunctions utilityFunctions;
    private LabelDiscorsi discorso;

    private boolean renderTesto = true;
    private boolean muoviPlayer = false;

    private boolean nelBox = false;
    private boolean renderizzaTesto = false;
    private boolean deveScegliere = false;

    
    public PokeCenter(MercurioMain game, String mappaDestinazione, String rettangoloPosizione) {
        super(game);
        this.game = game;
        this.mappaDestinazione = mappaDestinazione;
        this.rettangoloPosizione = rettangoloPosizione;
        dottoressa = new Dottoressa(game);
        utilityFunctions = new UtilityFunctions();
    }

    @Override
    public void show() {
        try {

            TmxMapLoader mapLoader = new TmxMapLoader();
            pokeCenterMap = mapLoader.load(Constant.CENTRO_POKEMON);
            tileRenderer = new OrthogonalTiledMapRenderer(pokeCenterMap);

            // calcolo e assegno dimensioni alla mappa
            int mapWidth = pokeCenterMap.getProperties().get("width", Integer.class)
                    * pokeCenterMap.getProperties().get("tilewidth", Integer.class);
            int mapHeight = pokeCenterMap.getProperties().get("height", Integer.class)
                    * pokeCenterMap.getProperties().get("tileheight", Integer.class);
            map_size = new Vector2(mapWidth, mapHeight);

            // creo la camera
            camera = new OrthographicCamera();
            camera.setToOrtho(false, map_size.x / 1.9f, map_size.y / 2f);
            camera.update();

            game.setMap(pokeCenterMap, tileRenderer, camera, map_size.x, map_size.y);


            background.add("floor");
            background.add("WallAlwaysBack");
            background.add("AlwaysBack_1");
            background.add("bancone");
            background.add("computer");
            background.add("deco bancone");
            game.aggiornaListaAllawaysBack(background);

            getPostionDoctor();

            game.getMusica().startMusic(game.getLuogo());

            setPosition();
        } catch (Exception e) {
            System.out.println("Errore show pokecenter, " + e);
        }

    }

    @Override
    public void render(float delta) {
        try {
            game.addBotRender(renderBot);

            esci();
            checkCure();
            checkBox();
            if (renderizzaTesto) {
                checkTesto();
            }
            if (muoviPlayer) {
                muoviPlayerPosition();
            }
        } catch (Exception e) {
            System.out.println("Errore render pokecenter, " + e);
        }

    }

    /*
    private void cambiaBot() {
        renderBot.clear();
        renderBot.add(new Render("bot", dottoressa.getTexture(), dottoressa.getPosition().x, dottoressa.getPosition().y, dottoressa.getWidth(), dottoressa.getHeight(), "dottoressa"));
    }
    */

    private void muoviPlayerPosition() {
        game.getPlayer().setMovement(true);
        muoviPlayer = false;
    }

    public void getPostionDoctor() {
        try {

            MapLayer layerTeleport = pokeCenterMap.getLayers().get("posDottoressa");
            MapObject obj = layerTeleport.getObjects().get("posizioneDott");
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) obj;
                Rectangle rect = rectObject.getRectangle();
                dottoressa.setPosition(rect.getX(), rect.getY());
            }
        } catch (Exception e) {
            System.out.println("Errore getPostionDoctor pokeCenter, " + e);
        }

    }

    public void checkCure() {
        try {

            MapLayer layerTeleport = pokeCenterMap.getLayers().get("cura");
            MapObject obj = layerTeleport.getObjects().get("curaPokemon");
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) obj;
                Rectangle rect = rectObject.getRectangle();
                if (game.getPlayer().getBoxPlayer().overlaps(rect)) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                        discorso = new LabelDiscorsi(
                                "Benvenuto! Questo e' un centro pokemon! riportero' i tuoi pokemon in perfetta forma in un batter d'occhio! Vuoi che mi prenda cura dei tuoi pokemon??",
                                30, 0, false, true);
                        deveScegliere = true;
                        renderizzaTesto = true;
                        game.getPlayer().setMovement(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore checkCure pokecenter, " + e);
        }

    }

    public void checkBox() {
        try {

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
        } catch (Exception e) {
            System.out.println("Errore checkBox pokecenter, " + e);
        }

    }

    private void checkTesto() {
        try {

            if (renderTesto) {

                if (deveScegliere) {
                    risposta = discorso.renderDisc();
                    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                        discorso.advanceText();
                    }

                    if (risposta != -1) {
                        if (risposta == 1) {
                            utilityFunctions.cura();
                            renderTesto = false;
                            discorso.setSceltaUtente(-1);
                        } else if (risposta == 0) {
                            renderTesto = false;
                            discorso.setSceltaUtente(-1);
                        }
                    }
                }else {
                    utilityFunctions.cura();
                    discorso.renderDisc();
                    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                        renderTesto = discorso.advanceText();
                    }
                }
            } else {
                renderizzaTesto = false;
                renderTesto = true;
                game.getPlayer().setMovement(true);
                discorso.reset();
                discorso = null;
            }
        } catch (Exception e) {
            System.out.println("Errore checktesto pokecenter, " + e);
        }

    }

    public void setPosition() {
        try {

            if (game.getPokemonMorti()) {
                game.getPlayer().setFermoAvanti();

                MapLayer layerTeleport = pokeCenterMap.getLayers().get("cura");
                MapObject obj = layerTeleport.getObjects().get("curaPokemon");
                if (obj instanceof RectangleMapObject) {
                    RectangleMapObject rectObject = (RectangleMapObject) obj;
                    Rectangle rect = rectObject.getRectangle();
                    game.getPlayer().setPosition(rect.getX(), rect.getY());

                    discorso = new LabelDiscorsi(
                            "Benvenuto! Questo e' un centro pokemon! I tuoi pokemon sono stati tutti quanti curati",
                            30, 0, false, false);
                    deveScegliere = false;
                    renderizzaTesto = true;
                    game.getPlayer().setMovement(false);
                }

                game.setPokemonMorti(false);

            }else {

                MapLayer layerTeleport = pokeCenterMap.getLayers().get("teleport");
                MapObject obj = layerTeleport.getObjects().get("entra");
                if (obj instanceof RectangleMapObject) {
                    RectangleMapObject rectObject = (RectangleMapObject) obj;
                    Rectangle rect = rectObject.getRectangle();
                    game.getPlayer().setPosition(rect.getX(), rect.getY());
                }

            }

        } catch (Exception e) {
            System.out.println("Errore setPosition pokecenter, " + e);
        }

    }

    //TODO: da studiare come fare in questo caso che ci sono più pokecenter
    public void esci() {
        try {

            MapLayer layerTeleport = pokeCenterMap.getLayers().get("exit");
            MapObject obj = layerTeleport.getObjects().get("uscita");
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) obj;
                Rectangle rect = rectObject.getRectangle();
                if (game.getPlayer().getBoxPlayer().overlaps(rect)) {
                    if (game.getIngressoPokeCenter().equals("cittaRoccia")) {
                        game.setIngressoCittaMontagna("ingressoDaPokecenter");
                        game.setPage(Constant.CITTAMONTAGNA);
                    } else {
                        //game.setTeleport(game.getIngressoPokeCenter());
                        //game.setPage(Constant.MAPPA_SCREEN);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore esci pokecenter, " + e);
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