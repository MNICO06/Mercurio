package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.personaggi.Bot;
import com.mercurio.game.personaggi.Professore;
import com.mercurio.game.personaggi.Rivale;
import com.mercurio.game.pokemon.Battle;

/*
 * mettere un rettangolo di controllo tra le due teche, quando lo si passa e non si ha nessun pkemon si viene bloccati,
 * il professere che si trova di fronte alla pokeball ti viene in contro e ti dice qualcosa, poi torna indietro fino ad essere
 * dalla parte opposta delle pokeball e poi puoi tornare a muoverti, poi bisogna avvicinarsi alle pokeball e parlare per scegliere
 * il pokemon, poi quando si sceglie non succede più nulla.
 * quando si entra e si possiede già un pokemon il professore si trova in un altro punto
 */

public class Laboratorio extends ScreenAdapter implements InterfacciaComune {
    private final MercurioMain game;

    //dati per render della mappa
    private TiledMap lab;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;
    private Professore professore;
    private Rivale rivale;
    private boolean haStarter = false;
    private SpriteBatch batch;
    private Stage stage;
    Vector3 screenPosition;
    private float puntoEsclamativoX;
    private float puntoEsclamativoY;

    private Battle battle;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    List<Render> render = new ArrayList<>();
    ArrayList<String> listaAllawaysBack = new ArrayList<String>();

    private Rectangle rectangleUscita;
    private Rectangle rettangoloFerma;

    private Image puntoEsclamativoImage;

    //variabili per animazione
    private boolean renderizzaPunto = true;         //da impostare di nuovo a true quando finisce l'animazione
    private boolean iniziaRiposizionaBot = false;
    private boolean iniziaCamminataProf = false;    //serve per indicare quando il professore deve iniziare a camminare in contro al personaggio
    private boolean iniziaDiscorso1 = false;        //serve per far renderizzare il primo disorso di base deve essere false
    private boolean continuaTesto = true;           //serve per far renderizzare e continuare il testo
    private boolean muoviProfVersoBancone1 = false;   //server per far iniziare la grande camminata del prof al bancone
    private boolean muoviProfVersoBancone2 = false;
    private boolean muoviProfVersoBancone3 = false;
    private boolean muoviProfVersoBancone4 = false;
    private boolean iniziaCamminataAsh = false;
    private boolean iniziaCamminataAsh2 = false;
    private boolean iniziaDiscorso2Bot = false;
    private boolean iniziaDiscorso2Ash = false;
    private boolean iniziaScelta = false;
    private boolean iniziaDiscorso3 = false;
    private boolean iniziaFarComparireRivale = false;       //lo faccio renderizzare
    private boolean faiSalireRivale = false;                //lo faccio andare verso il bancone
    private boolean faiSalireRivale2 = false;
    private boolean faiSalireRivale3 = false;
    private boolean iniziaDiscorso4Rivale = false;
    private boolean inziaDiscorso5Prof = false;
    private boolean iniziaDiscorso6Rivale = false;
    private boolean iniziaDiscorso7Rivale = false;
    private boolean iniziaDiscorso8Prof = false;
    private boolean iniziaDiscorso9Rivale = false;
    private boolean iniziaDiscorso10Prof = false;
    private boolean portaGiuRivale = false;
    private boolean portaDestraRivale = false;
    private boolean portaGiuRivale2 = false;

    //variabili per il testo del professore quando non si ha uno starter
    private LabelDiscorsi primoDiscorso;
    private LabelDiscorsi secondoDiscorso;
    private LabelDiscorsi terzoDiscorso;
    private LabelDiscorsi quartoDiscorsoRivale;
    private LabelDiscorsi quintiDiscorsoProf;
    private LabelDiscorsi sestoDiscorsoRivale;
    private LabelDiscorsi settimoDiscorsoRivale;
    private LabelDiscorsi ottavoDiscorsoProf;
    private LabelDiscorsi nonoDiscorsoRivale;
    private LabelDiscorsi decimoDiscorsoProf;

    private String testoDiscorso1 = "Professor Rowan: Eccoti... E cosi' tu sei Ash... Ti ho chiamato perche' ho bisogno del tuo aiuto. Ora seguimi, hai bisogno di prepararti prima di partire.";
    private String testoDiscorso2 = "Professor Rowan: Da qua parte la tua avventura. Scegli il Pokemon che preferisci, sara' il tuo compagno piu' fidato.";
    private String testoDiscorso3Prof = "Professor Rowan: Hai scelto il tuo primo Pokemon, ora sei un'allenatore Pokemon a tutti gli effetti.";
    private String testoDiscorso4Rivale = "Barry: Hey hey non cosi' in fretta, non lasciero' di certo Ash da solo! Voglio partire con lui e diventare io il campione!";
    private String testoDiscorso5Prof = "Professor Rowan: Mhh... Barry... Capisco il tuo entusiasmo. E va bene, di Pokemon ne ho per entrambi. Poi un po' di sana competizione vi fara' del bene. Puoi scegliere anche tu il tuo compagno.";
    private String testoDiscorso6Rivale = "Barry: Fantastico! Ma ci credi Ash, abbiamo entrabmi ora dei Pokemon, e ora siamo rivali! Dai proviamoli subito in una bella battaglia! ";
    //dopo la battaglia
    private String testoDiscorso7Rivale = "Barry: E' stato incredibile! Sei veramente incredibile! Ma sappilo, io diventero' il piu' forte di tutti, anche di te!";
    private String testoDiscorso8Prof = "Professor Rowan: Bravi ragazzi! Il vostro entusiasmo mi fa tornare il mente la mia gioventu'. Ma torniamo alla vostra missione: vi dovrete mettere in viaggio per aiutarmi a incontrare tutte le specie di Pokemon e completare il Pokedex. Voglio anche che affrontate le 3 palestre di Klower e voglio che miriate a diventare campioni. Ora, IN VIAGGIO!";
    private String testoDiscorso9Rivale = "Barry: Grazie Professore, non la deluderemo. E ricordati Ash, io saro' il piu' forteee!";

    //dopo che il rivale se n'è andato
    private String testoDiscorso10Prof = "Professor Rowan: Quel ragazzo e' pieno di energie, sono sicuro che sara' un ottimo amico e rivale per te. Ora puoi partire anche tu. Il tuo primo obbiettivo sara' Margolia, la capitale. Per raggiungerla dei passare dal Bosco Tempovia. Vai, parti all'avventura!";

    public Laboratorio(MercurioMain game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        professore = new Professore();
        //renderizzo il professore

        rivale = new Rivale();

        rectList = new ArrayList<Rectangle>();
        batch = new SpriteBatch();

        listaAllawaysBack.add("floor");
        listaAllawaysBack.add("WallAlwaysBack");
        listaAllawaysBack.add("AlwaysBack1");
        listaAllawaysBack.add("AlwaysBack2");
        listaAllawaysBack.add("tappeto");
        
        primoDiscorso = new LabelDiscorsi(testoDiscorso1, 30, 0, false, false);
        secondoDiscorso = new LabelDiscorsi(testoDiscorso2, 30, 0, false, false);
        terzoDiscorso = new LabelDiscorsi(testoDiscorso3Prof, 30, 0, false, false);
        quartoDiscorsoRivale = new LabelDiscorsi(testoDiscorso4Rivale, 30, 8, false, false);
        quintiDiscorsoProf = new LabelDiscorsi(testoDiscorso5Prof, 30, 0, false, false);
        sestoDiscorsoRivale = new LabelDiscorsi(testoDiscorso6Rivale, 30, 8, false, false);
        settimoDiscorsoRivale = new LabelDiscorsi(testoDiscorso7Rivale, 30, 8, false, false);
        ottavoDiscorsoProf = new LabelDiscorsi(testoDiscorso8Prof, 30, 0, false, false);
        nonoDiscorsoRivale = new LabelDiscorsi(testoDiscorso9Rivale, 30, 8, false, false);
        decimoDiscorsoProf = new LabelDiscorsi(testoDiscorso10Prof, 30, 0, false, false);

    }

    @Override
    public void show() {
        game.setLuogo("laboratorio");
        game.getMusica().startMusic("labPokemon");

        TmxMapLoader mapLoader = new TmxMapLoader();
        lab = mapLoader.load(Constant.LAB_MAPPA);
        tileRenderer = new OrthogonalTiledMapRenderer(lab);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = lab.getProperties().get("width", Integer.class) * lab.getProperties().get("tilewidth", Integer.class);
        int mapHeight = lab.getProperties().get("height", Integer.class) * lab.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        //creo la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/1.5f, map_size.y/2.5f);
        camera.update();

        game.setMap(lab, tileRenderer, camera, map_size.x, map_size.y);

        //aggiungere alla lista delle collisioni quella del professore

        game.getPlayer().setPosition(55, 20);

        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = lab.getLayers().get("exit").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                // Ottieni il rettangolo
                rectangleUscita = rectangleObject.getRectangle();

            } 
        }

        //ho invertito le cose, TOGLIERE il ! dall'if
        if (controllaPresenzaStarter()) {
            professore.setPosition(109, 135);
            haStarter = true;
            rectList.add(professore.getBox());
        }else {
            professore.setPosition(115, 100);
            rectList.add(professore.getBox());
            haStarter = false;
        }

        rivale.setPosition(115, 20);

        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        lineeLayer = game.getLineeLayer();
        game.setRectangleList(rectList);
        cambiaProfondita(lineeLayer);
        controllaUscita();

        if (!haStarter) {
            settaReggangoliStoria();
            storiaStarter();
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime);
        stage.draw();

        
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        //pulisco l'array in modo tale che non pesi
        render.clear();

        //inserisci i vari layer nella lista
        for (MapObject object : lineeLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                String layerName = (String) rectangleObject.getProperties().get("layer");
                float y = rectangleObject.getRectangle().getY();
                render.add(new Render("layer", layerName, y));
            }
        }

        // Inserisci il professore e il giocatore
        render.add(new Render("bot", professore.getTexture(), professore.getPosition().x, professore.getPosition().y, professore.getWidth(), professore.getHeight(), "professore"));
        render.add(new Render("player", game.getPlayer().getPlayerPosition().y));

        if (iniziaFarComparireRivale) {
            render.add(new Render("bot", rivale.getTexture(), rivale.getPosition().x, rivale.getPosition().y, rivale.getWidth(), rivale.getHeight(), "rivale"));
        }


        // Ordina in base alla posizione `y`
        Collections.sort(render, Comparator.comparingDouble(r -> -r.y));

        //background
        for (String layerName : listaAllawaysBack) {
            renderLayer(layerName);
        }

        // Renderizza nell'ordine corretto
        for (Render renderComponent : render) {
            switch (renderComponent.type) {
                case "layer":
                    renderLayer(renderComponent.layerName);
                    break;
                case "bot":
                    if (renderComponent.persona.equals("professore")) {
                        game.renderPersonaggiSecondari(professore.getTexture(), professore.getPosition().x, professore.getPosition().y, professore.getWidth(), professore.getHeight());
                    }else if (renderComponent.persona.equals("rivale")) {
                        game.renderPersonaggiSecondari(rivale.getTexture(), rivale.getPosition().x, rivale.getPosition().y, rivale.getWidth(), rivale.getHeight());
                    }
                    break;
                case "player":
                    game.renderPlayer();
                    break;
            }
        }

    }

    private void renderLayer(String layerName) {
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        MapLayer layer = lab.getLayers().get(layerName);
        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer);

        tileRenderer.getBatch().end();
    }

    /*
     * TUTTE LE FUNZIONI SOTTO FINO AD UN CERTO PUNTO SERVONO PER L'ANIMAZIONE QUANDO NON SI HA ANCORA UNO STARTER
     * CONSISTONO IN:
     * mostrare il punto esclamativo
     * far camminare in avanti il professore fino al bot
     * far partire il primo discorso del professore
     * far camminare il professore fino al bancono
     * far camminare il personaggio fino al bancone
     * far partire il secondo discorso del professore
     * far comparire l'animazione per la scelta dello starter
     * far partire il terzo discorso e liberare il personaggio
     */
    private void storiaStarter() {
        if (game.getPlayer().getBoxPlayer().overlaps(rettangoloFerma)) {

            if (renderizzaPunto) {
                game.getPlayer().setMovement(false);
                renderizzaPuntoEsclamativo();
            }
            if (iniziaRiposizionaBot) {
                riposizionaBot();
            }
            if (iniziaCamminataProf) {
                iniziaCamminata();
            }
            if (iniziaDiscorso1) {
                iniziaPrimoDiscorso();
            }
            if (muoviProfVersoBancone1) {
                iniziaCamminataProfessoreVersoBancone1();
            }
            if (muoviProfVersoBancone2) {
                iniziaCamminataProfessoreVersoBancone2();
            }
            if (muoviProfVersoBancone3) {
                iniziaCamminataProfessoreVersoBancone3();
            }
            if (iniziaCamminataAsh) {
                iniziaCamminataAshVersoBancone();
            }
            if (muoviProfVersoBancone4) {
                iniziaCamminataProfessoreVersoBancone4();
            }
            if (iniziaCamminataAsh2) {
                iniziaCamminataAshVersoBancone2();
            }
            if (iniziaDiscorso2Ash && iniziaDiscorso2Bot) {
                iniziaDiscorso2();
            }
            if (iniziaScelta) {
                apriSceltaStarter();
            }
            if (iniziaDiscorso3) {
                renderizzaDiscorso3();
            }
            if (faiSalireRivale) {
                portaRivaleSu();
            }
            if (faiSalireRivale2) {
                portaRivaleSu2();
            }
            if (faiSalireRivale3) {
                portaRivaleSu3();
            }
            if (iniziaDiscorso4Rivale) {
                renderizzaDiscorso4();
            }
            if (inziaDiscorso5Prof) {
                renderizzaDiscorso5();
            }
            if (iniziaDiscorso6Rivale) {
                renderizzaDiscorso6();
            }
            if (iniziaDiscorso7Rivale) {
                renderizzaDiscorso7();
            }
            if (iniziaDiscorso8Prof) {
                renderizzaDiscorso8();
            }
            if (iniziaDiscorso9Rivale) {
                renderizzaDiscorso9();
            }
            if (portaGiuRivale) {
                portaRivaleGiu1();
            }
            if (portaDestraRivale) {
                portaRivaleGiu2();
            }
            if (portaGiuRivale2) {
                portaRivaleGiu3();
            }
            if (iniziaDiscorso10Prof) {
                renderizzaDiscorso10();
            }


            if (battle != null) {
                battle.render();
            }
            
        }
    }

    //metodo per mostrare il punto esclamativo in testa al professore
    private void renderizzaPuntoEsclamativo() {
        game.getPlayer().setFermoAvanti();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                renderizzaPunto = false;
                puntoEsclamativoImage.remove();
                iniziaRiposizionaBot = true;
                cancel();
            }
        }, 1f);

        if (puntoEsclamativoImage != null) {
            puntoEsclamativoImage.remove();
        }

        //comandi per il rendering del punto esclamativo
        Texture puntoEsclamativo = new Texture("bots/ExlMark.png");
        puntoEsclamativoImage = new Image(puntoEsclamativo);
        puntoEsclamativoImage.setSize(52,52);
        Vector3 botPosition = new Vector3(professore.getPosition().x, professore.getPosition().y,0);
        Vector3 screenCoords = camera.project(botPosition);
        puntoEsclamativoImage.setPosition(screenCoords.x + 50, screenCoords.y + 100);

        stage.addActor(puntoEsclamativoImage);
    }

    private void riposizionaBot() {
        if (professore.getPosition().x > game.getPlayer().getPlayerPosition().x) {
            if (professore.getPosition().x - game.getPlayer().getPlayerPosition().x > 2) {
                professore.muoviBotSinistra();
            }else {
                iniziaRiposizionaBot = false;
                iniziaCamminataProf = true;
            }
        }else if (professore.getPosition().x < game.getPlayer().getPlayerPosition().x) {
            if (game.getPlayer().getPlayerPosition().x - professore.getPosition().x > 2) {
                professore.muoviBotDestra();
            }else {
                iniziaRiposizionaBot = false;
                iniziaCamminataProf = true;
            }
        }else {
            iniziaRiposizionaBot = false;
            iniziaCamminataProf = true;
        }
    }

    //metodo per far camminare il professore fino ad arrivare di fronte al personaggio
    private void iniziaCamminata() {
        if ((professore.getPosition().y - game.getPlayer().getPlayerPosition().y) > 15) {
            professore.muoviBotBasso();
        }else {
            professore.setFermoIndietro();
            iniziaCamminataProf = false;    //la setto a false per far terminare la chiama a questa funzione
            iniziaDiscorso1 = true;         //lo setto a true per far iniziare il discorso 1
        }
    }

    //metodo per far iniziare il primo discorso
    private void iniziaPrimoDiscorso() {
        if (continuaTesto) {
            primoDiscorso.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = primoDiscorso.advanceText();
            }
        }else {
            iniziaDiscorso1 = false;
            continuaTesto = false;
            muoviProfVersoBancone1 = true;
            primoDiscorso.reset();
        }
    }

    //metodo per spostare il professore sopra
    private void iniziaCamminataProfessoreVersoBancone1() {
        if ((110 - professore.getPosition().y) > 5) {
            professore.muoviBotAlto();
        }else {
            muoviProfVersoBancone1 = false;
            muoviProfVersoBancone2 = true;
        }
    }

    //metodo per far spostare il professore verso destra
    private void iniziaCamminataProfessoreVersoBancone2() {
        if ((174 - professore.getPosition().x) > 15) {
            professore.muoviBotDestra();
        }else {
            muoviProfVersoBancone2 = false;
            muoviProfVersoBancone3 = true;
            iniziaCamminataAsh = true;
        }
    }

    //metodo per far spostare il professore verso l'alto fino a sopra il bancone
    private void iniziaCamminataProfessoreVersoBancone3() {
        if ((138 - professore.getPosition().y) > 5) {
            professore.muoviBotAlto();
        }else {
            muoviProfVersoBancone3 = false;
            muoviProfVersoBancone4 = true;
        }
    }

    //metodo per far spostare il professore verso l'alto fino a sopra il bancone
    private void iniziaCamminataProfessoreVersoBancone4() {
        if ((professore.getPosition().x - 104) > 5) {
            professore.muoviBotSinistra();
        }else {
            muoviProfVersoBancone4 = false;
            professore.setFermoIndietro();
            iniziaDiscorso2Bot = true;
        }
    }

    //metodo per far muovere in contemporanea il professore e ash
    private void iniziaCamminataAshVersoBancone() {
        if ((110 - game.getPlayer().getPlayerPosition().y) > 5) {
            game.getPlayer().muoviBotAlto();
        }else {
            iniziaCamminataAsh = false;
            iniziaCamminataAsh2 = true;
            continuaTesto = true;
        }
    }

    //metodo per far muovere ash verso sinistra     considera tra: 106 e 114
    private void iniziaCamminataAshVersoBancone2() {
        if (game.getPlayer().getPlayerPosition().x > 112) {
            if ((game.getPlayer().getPlayerPosition().x) - 110 > 2) {
                game.getPlayer().muoviBotSinistra();
            }else {
                iniziaCamminataAsh2 = false;
                iniziaDiscorso2Ash = true;
                game.getPlayer().setFermoAvanti();
            }
        }else if (game.getPlayer().getPlayerPosition().x < 108) {
            if (110 - (game.getPlayer().getPlayerPosition().x) > 2) {
                game.getPlayer().muoviBotDestra();
            }else {
                iniziaCamminataAsh2 = false;
                iniziaDiscorso2Ash = true;
                game.getPlayer().setFermoAvanti();
            }
        }else {
            iniziaCamminataAsh2 = false;
            iniziaDiscorso2Ash = true;
            game.getPlayer().setFermoAvanti();
        }
    }

    private void iniziaDiscorso2() {
        if (continuaTesto) {
            secondoDiscorso.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = secondoDiscorso.advanceText();
            }
        }else {
            iniziaDiscorso2Ash = false;
            iniziaDiscorso2Bot = false;
            continuaTesto = false;
            iniziaScelta = true;
        }
    }

    private void apriSceltaStarter() {
        game.creaSceltaStarter();
        if (game.sceltoStarter()) {
            iniziaScelta = false;
            game.closeSceltaStarter();
            iniziaDiscorso3 = true;
            continuaTesto = true;
        }
    }

    private void renderizzaDiscorso3() {
        if (continuaTesto) {
            terzoDiscorso.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = terzoDiscorso.advanceText();
            }
        }else {
            continuaTesto = false;
            iniziaDiscorso3 = false;
            iniziaFarComparireRivale = true;
            faiSalireRivale = true;
        }
    }

    private void portaRivaleSu() {
        if ((95 - rivale.getPosition().y) > 5) {
            rivale.muoviBotAlto();
        }else {
            faiSalireRivale = false;
            faiSalireRivale2 = true;
        }
    }

    private void portaRivaleSu2() {
        if ((rivale.getPosition().x - 85) > 5) {
            rivale.muoviBotSinistra();
        }else {
            faiSalireRivale2 = false;
            faiSalireRivale3 = true;
        }
    }

    private void portaRivaleSu3() {
        if ((110 - rivale.getPosition().y) > 5) {
            rivale.muoviBotAlto();
        }else {
            faiSalireRivale3 = false;
            iniziaDiscorso4Rivale = true;
            continuaTesto = true;
        }
    }

    private void renderizzaDiscorso4() {
        if (continuaTesto) {
            quartoDiscorsoRivale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = quartoDiscorsoRivale.advanceText();
            }
        }else {
            iniziaDiscorso4Rivale = false;
            inziaDiscorso5Prof = true;
            continuaTesto = true;
        }
    }

    private void renderizzaDiscorso5() {
        if (continuaTesto) {
            quintiDiscorsoProf.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = quintiDiscorsoProf.advanceText();
            }
        }else {
            inziaDiscorso5Prof = false;
            iniziaDiscorso6Rivale = true;
            continuaTesto = true;
            rivale.setFermoDestra();
            game.getPlayer().setFermoSinistra();
        }
    }

    private void renderizzaDiscorso6() {
        if (continuaTesto) {
            sestoDiscorsoRivale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = sestoDiscorsoRivale.advanceText();
            }
        }else {
            iniziaDiscorso6Rivale = false;
            battle = new Battle(this, "rivale", true, null, null);

            /* per evitare battaglia committa la creazione e togli questo
            iniziaDiscorso7Rivale = true; 
            continuaTesto = true;
            */
        }
    }

    private void renderizzaDiscorso7() {
        if (continuaTesto) {
            settimoDiscorsoRivale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = settimoDiscorsoRivale.advanceText();
            }
        }else {
            iniziaDiscorso7Rivale = false;
            iniziaDiscorso8Prof = true; 
            continuaTesto = true;
            rivale.setFermoAvanti();
            game.getPlayer().setFermoAvanti();
        }
    }

    private void renderizzaDiscorso8() {
        if (continuaTesto) {
            ottavoDiscorsoProf.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = ottavoDiscorsoProf.advanceText();
            }
        }else {
            iniziaDiscorso8Prof = false;
            iniziaDiscorso9Rivale = true;
            continuaTesto = true; 
        }
    }

    private void renderizzaDiscorso9() {
        if (continuaTesto) {
            nonoDiscorsoRivale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = nonoDiscorsoRivale.advanceText();
            }
        }else {
            iniziaDiscorso9Rivale = false;
            portaGiuRivale = true;
            continuaTesto = true;
        }
    }

    private void portaRivaleGiu1() {
        if ((rivale.getPosition().y - 80) > 5) {
            rivale.muoviBotBasso();
        }else {
            portaGiuRivale = false;
            portaDestraRivale = true;
        }
    }

    private void portaRivaleGiu2() {
        if ((120 - rivale.getPosition().x) > 5) {
            rivale.muoviBotDestra();
        }else {
            portaDestraRivale = false;
            portaGiuRivale2 = true;
        }
    }
    private void portaRivaleGiu3() {
        if ((rivale.getPosition().y - 20) > 5) {
            rivale.muoviBotBasso();
        }else {
            portaGiuRivale2 = false;
            iniziaDiscorso10Prof = true;
            iniziaFarComparireRivale = false;
        }
    }
    
    private void renderizzaDiscorso10() {
        if (continuaTesto) {
            decimoDiscorsoProf.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = decimoDiscorsoProf.advanceText();
            }
        }else {
            iniziaDiscorso10Prof = false;
            game.getPlayer().setMovement(true);
        }
    }
    

    //--------------------------------------------------------------------------------------------------------------------------------

    //metodo per il recupero del rettangolo con il tipo
    private void settaReggangoliStoria() {
        //recupero il rettangolo per uscire dalla mappa
        MapObjects objects = lab.getLayers().get("controlliStoria").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                String tipo = (String) object.getProperties().get("tipo");

                if("ferma".equals(tipo)) {
                    rettangoloFerma = rectangleObject.getRectangle();
                }
            } 
        }
    }

    //ritorna false se non si ha ancora lo starter, se no ritorna true
    private boolean controllaPresenzaStarter() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue poke1 = json.get("poke1");

            if (poke1 != null) {
                String nomePokemon = poke1.getString("nomePokemon", "");

                if (!nomePokemon.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    private void controllaUscita() {
        if(game.getPlayer().getBoxPlayer().overlaps(rectangleUscita)) {
            game.setTeleport("uscitaLab");
            game.setPage(Constant.MAPPA_SCREEN);
        }
    }

    @Override
    public void dispose() {
        if (lab != null)  {
            lab.dispose();
        }

        tileRenderer.dispose();
    }

    @Override
    public void closeBattle() {
        //game.getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        battle = null;
        iniziaDiscorso7Rivale = true; 
        continuaTesto = true;
    }

}
