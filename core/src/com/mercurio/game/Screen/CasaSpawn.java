package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.personaggi.MammaAsh;
import com.mercurio.game.utility.MapsAbstract;
import com.mercurio.game.utility.UtilityFunctions;

import java.util.Timer;

public class CasaSpawn extends MapsAbstract {
    private UtilityFunctions utilityFunctions;
    private MammaAsh mammaAsh;
    private List<Render> renderBot = new ArrayList<>();
    private LabelDiscorsi labelDiscorsiSenzaStarter;
    private LabelDiscorsi labelDiscorsiConStarter;

    // rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;

    private Timer timer;
    private TimerTask mammaTimerTask;

    private boolean isInBox = false;

    private boolean wasInBox = false;

    private boolean tieniApertoDiscorsoPrima = false;
    private boolean tieniApertoDiscorsoDopo = false;
    private boolean fPressed = false;
    private boolean ferma = true;
    private boolean iniziaMovimento = false;


    public CasaSpawn(MercurioMain game) {
        super(game);

        utilityFunctions = new UtilityFunctions();
        mammaAsh = new MammaAsh(game);
        

        /* variabili che andranno lette da file */
        String discorsoSenzaStarter = "C'era il professor Pokemon che ti cercava, vai al suo laboratorio per vedere cosa ti deve dire";
        String discorsoConStarter = "Finalmente sei tornato, vedo che i tuoi pokemon sono un po' stanchi, lascia che te li curi";

        labelDiscorsiSenzaStarter = new LabelDiscorsi(discorsoSenzaStarter, 30, 10, false, false);
        labelDiscorsiConStarter = new LabelDiscorsi(discorsoConStarter, 30, 10, false, false);

        rectList = new ArrayList<Rectangle>();
    }

    @Override
    public void show() {

        try {

            game.setLuogo(Constant.CASA_ASH_SCREEN);

            // timer da usare dopo per far girare la mamma sui fornelli
            timer = new Timer();
            mammaTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mammaAsh.setAvanti();
                    cambiaBot();
                }
            };

            TmxMapLoader mapLoader = new TmxMapLoader();
            map = mapLoader.load(Constant.CASA_ASH);
            OrthogonalTiledMapRenderer tileRenderer = new OrthogonalTiledMapRenderer(map);

            // calcolo e assegno dimensioni alla mappa
            int mapWidth = map.getProperties().get("width", Integer.class)
                    * map.getProperties().get("tilewidth", Integer.class);
            int mapHeight = map.getProperties().get("height", Integer.class)
                    * map.getProperties().get("tileheight", Integer.class);
            Vector2 map_size = new Vector2(mapWidth, mapHeight);

            // creo la camera
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false, map_size.x / 1.9f, map_size.y / 2f);
            camera.update();

            game.setMap(map, tileRenderer, camera, map_size.x, map_size.y);


            ArrayList<String> background = new ArrayList<String>();
            background.add("floor");
            background.add("WallAlwaysBack");
            background.add("AlwaysBack_1");
            background.add("AlwaysBack_2");
            game.aggiornaListaAllawaysBack(background);

            // aggiungo alla lista dei rettangoli per le collisioni quello della mamma
            rectList.add(mammaAsh.getBoxPlayer());

            
            prendiUscitaCase();     //funzione per il recupero delle variabii per uscire di casa
            settaPlayerPosition();
            
        } catch(Exception e) {
            System.out.println("Errore show casaSpawn, " + e);
        }
    }

    

    @Override
    public void render(float delta) {

        game.setRectangleList(rectList);
        game.addBotRender(renderBot);
        controllaUscita();

        giraMamma();
        controlloTesto();
        controlloTestoIniziale();
        controllaFermaPlayer();
    }

    private void cambiaBot() {
        renderBot.clear();
        renderBot.add(new Render("bot", mammaAsh.getTexture(), mammaAsh.getPosition().x, mammaAsh.getPosition().y, mammaAsh.getWidth(), mammaAsh.getHeight(), "mammaAsh"));
    }
    

    private void fermaPlayer() {
        game.getPlayer().setMovement(false);
        game.getPlayer().setFermoDestra();
        mammaAsh.setSinstra();
        cambiaBot();

        // Pianifica un nuovo compito per far tornare la mamma nella posizione "avanti" dopo 5 secondi
        mammaTimerTask = new TimerTask() {
            @Override
            public void run() {
                iniziaMovimento = true;
            }
        };
        // Avvia il timer per il compito della mamma
        timer.schedule(mammaTimerTask, 1000);


        if (iniziaMovimento) {
            if ((mammaAsh.getPosition().x - game.getPlayer().getPlayerPosition().x) > 15) {
                game.getPlayer().muoviBotDestra();
            }else {
                ferma = false;
                tieniApertoDiscorsoPrima = true;
            }
        }
    }

    private void controllaFermaPlayer() {
        //true se proviene dal fullmap, se no non faccio nulla che prende le posizioni salvate nel json
        MapObjects objects = map.getLayers().get("lineaFerma").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle()) && !utilityFunctions.controllaPresenzaStarter() && ferma) {
                    fermaPlayer();
                }
            } 
        }
        game.setProvieneDaMappa(false);
    }

    public void startTimerForMamma() {
        try {

            // Pianifica un nuovo compito per far tornare la mamma nella posizione "avanti"
            // dopo 5 secondi
            mammaTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mammaAsh.setAvanti();
                    cambiaBot();
                }
            };

            // Avvia il timer per il compito della mamma
            timer.schedule(mammaTimerTask, 3000);
        } catch (Exception e) {
            System.out.println("Errore startTimerForMamma casaSpawn, " + e);
        }
    }

    // Metodo per annullare il compito del timer della mamma
    public void cancelTimerForMamma() {
        // Cancella il compito del timer della mamma se è stato pianificato in
        // precedenza
        if (mammaTimerTask != null) {
            mammaTimerTask.cancel();
            mammaTimerTask = null; // Imposta il compito del timer della mamma su null per indicare che è stato
                                   // cancellato
        }
    }

    // metodo che fa girare la mamma
    public void giraMamma() {
        try {
            isInBox = false;
            // si trova dentro quello sotto
            if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxVert())) {
                mammaAsh.setIndietro();
                isInBox = true;
            }
            // si trova dentro quello a destra
            else if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxOrizDx())) {
                mammaAsh.setDestra();
                isInBox = true;
            }
            // si trova dentro quello a sinistra
            else if (game.getPlayer().getBoxPlayer().overlaps(mammaAsh.getInterBoxOrizSx())) {
                mammaAsh.setSinstra();
                isInBox = true;
            }

            if (isInBox && !fPressed) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    boolean presenzaStarter = utilityFunctions.controllaPresenzaStarter();
                    if (presenzaStarter) {
                        tieniApertoDiscorsoDopo = true;
                    } else {
                        tieniApertoDiscorsoPrima = true;
                    }

                    fPressed = true;
                    game.getPlayer().setMovement(false);
                }
            }

            if (isInBox) {
                if (mammaTimerTask != null)
                    cancelTimerForMamma();
            } else {
                if (!isInBox && wasInBox) {
                    startTimerForMamma();
                }
            }

            wasInBox = isInBox;
            cambiaBot();
        } catch (Exception e) {
            System.out.println("Errore giraMamma casaSpawn, " + e);
        }
    }


    // questa funzione serve per il testo base senza scelta
    public void controlloTestoIniziale() {
        if (tieniApertoDiscorsoPrima) {
            game.getPlayer().setMovement(false);
            labelDiscorsiSenzaStarter.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                tieniApertoDiscorsoPrima = labelDiscorsiSenzaStarter.advanceText();
            }
        }
        else {
            //quando deve terminare 
            tieniApertoDiscorsoPrima = false;
            fPressed = false;
            game.getPlayer().setMovement(true);
            labelDiscorsiSenzaStarter.reset();
        }
    }

    // questa funzione serve per il testo con la scelta
    public void controlloTesto() {
        try {

            if (tieniApertoDiscorsoDopo) {
                labelDiscorsiConStarter.renderDisc();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                    tieniApertoDiscorsoDopo = labelDiscorsiConStarter.advanceText();
                }
            } else {
                // quando deve terminare
                tieniApertoDiscorsoDopo = false;
                fPressed = false;
                utilityFunctions.cura();
                game.getPlayer().setMovement(true);
                labelDiscorsiConStarter.reset();
            }
        } catch (Exception e) {
            System.out.println("Errore controllotesto casaSPawn, " + e);
        }

    }

    

    @Override
    public void dispose() {
        if (mammaTimerTask != null) {
            mammaTimerTask.cancel();
        }
        if (mammaAsh != null) {
            mammaAsh.dispose();
        }
    }

}
