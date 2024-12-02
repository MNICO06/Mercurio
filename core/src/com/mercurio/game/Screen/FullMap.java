package com.mercurio.game.Screen;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.personaggi.Bot;
import com.mercurio.game.pokemon.Battle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;



public class FullMap extends ScreenAdapter implements InterfacciaComune {
    private final MercurioMain game;
    
    private TiledMap mappa;
    private OrthogonalTiledMapRenderer tileRenderer;
    private OrthographicCamera camera;
    private Vector2 map_size;
    private MapLayer lineeLayer;
    private MapLayer alberiBack;
    private MapLayer alberiFore;
    private MapLayer divAlberi;

    //rettangolo con la lista delle persone che collidono
    private ArrayList<Rectangle> rectList = null;
    private ArrayList<Bot> botList;
    private ArrayList<Bot> nuotatoriList;
    private ArrayList<Bot> botListBack;
    private ArrayList<Bot> botListFore;

    private Image puntoEsclamativoImage;
    private Stage stage;

    //quando entra la mette a true e fino a quando non termina la battaglia rimane a true, quando termina la battaglia viene rimessa
    //a false e viene salvato il dato sul json e anche sul bot. (momentaneamente poi non funziona più)
    private boolean inEsecuzione = false;

    private boolean faiMuovereBot = false;


    private float y;
    private float x;

    private LabelDiscorsi discorsoIniziale;
    private LabelDiscorsi discorsoFinale;
    private boolean continuaTesto = true;
    private boolean renderDiscorso = false;
    private boolean battagliaIsFinished = false;
    private boolean torna = false;
    private String nomeJson;
    private boolean faiAvvicinare = false;
    private boolean isNormalBot = true;
    private Bot bot;

    private Battle battle;

    //quando il personaggio passa di fronte al bot mette il testo di inzio del bot e true il boolean, poi legge e rimette a false e aspetta di nuovo
    private boolean leggiTesto = false;

    //----------------------------------------------------------------------------
    public FullMap(MercurioMain game, TiledMap mappa) {
        this.game = game;
        this.mappa = mappa;
        rectList = new ArrayList<Rectangle>();
        botList = new ArrayList<Bot>();
        nuotatoriList = new ArrayList<Bot>();
        
        stage = new Stage();
        new Timer();
    }

    @Override
    public void show() {
        tileRenderer = new OrthogonalTiledMapRenderer(mappa);

        //calcolo e assegno dimensioni alla mappa
        int mapWidth = mappa.getProperties().get("width", Integer.class) * mappa.getProperties().get("tilewidth", Integer.class);
        int mapHeight = mappa.getProperties().get("height", Integer.class) * mappa.getProperties().get("tileheight", Integer.class);
        map_size = new Vector2(mapWidth,mapHeight);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, map_size.x/38f, map_size.y/40f);
        camera.update();

        setPositionPlayer();
        setPositionBot();

        game.setMap(mappa, tileRenderer, camera, map_size.x, map_size.y);
        controllaPresenzaStarter();
    }

    @Override
    public void render(float delta) {
        lineeLayer = game.getLineeLayer();
        alberiBack = game.getAlberiBack();
        alberiFore = game.getAlberiFore();
        divAlberi = game.getDivAlberi();
        cambiaProfondita(lineeLayer);
        if (leggiTesto) {
            renderizzaDiscorsoInizio();
        }
        if (battagliaIsFinished && isNormalBot) {
            fineBattaglia();
        }
        else if (battagliaIsFinished && isNormalBot == false) {
            fineBattagliaNuotatore();
        }


        if (battle != null){
            battle.render();
        }

        checkLuogo();
        teleport();
        if (game.getLuogo().equals("percorso3") || game.getLuogo().equals("cittaMare") ) {
            checkInAcqua();
        }
        
        game.setRectangleList(rectList);
        
        
        for (Bot bot : botList) {
            bot.muovilBot();
        }

        for (Bot bot : nuotatoriList) {
            bot.faiNuotareBot(game.getPlayer());
        }

        if (inEsecuzione == false) {
            if (game.getLuogo().equals("percorso3")) {
                checkFerma();
            }
        }
        

        //controlla presenza bot solo se è nei percorsi
        if (inEsecuzione == false) {
            if (game.getLuogo().equals("percorso1") || game.getLuogo().equals("bosco") || game.getLuogo().equals("percorso2") ||
                game.getLuogo().equals("percorso3") || game.getLuogo().equals("perocroso4")) {

                checkInteractionBot();
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime
        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    private void cambiaProfondita(MapLayer lineeLayer) {
        ArrayList<String> background = new ArrayList<String>();
        ArrayList<String> foreground = new ArrayList<String>();
        botListBack = new ArrayList<Bot>();
        botListFore = new ArrayList<Bot>();

        background.add("Livello tile ground");
        background.add("Livello tile path");
        background.add("Livello tile case");
        background.add("Livello tile deco2");
        background.add("Livello tile deco1");
        background.add("Livello tile deco");
        background.add("Livello tile piantine");

        foreground.add("AlberiCima");
        foreground.add("FixingLayer1");
        foreground.add("AlberiFondo");
        foreground.add("FixingLayer2");
        foreground.add("AlberiMezzo");
        
        //background.add("");

        for (MapObject obj : lineeLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject)obj;

                String layerName = (String)rectObj.getProperties().get("layer");

                y = rectObj.getRectangle().getY() - game.getPlayer().getPlayerPosition().y;
                x = rectObj.getRectangle().getX() - game.getPlayer().getPlayerPosition().x;
                if (y > 0 && y < 250 && x > -500 && x < 500) {
                    if(layerName.equals("alberi1N") == true){
                        background.add(layerName);
                        background.add("alberi2N");
                        background.add("alberi3N");
                        background.add("alberi4N");
                    }else if(layerName.equals("alberi5N") == true){
                        background.add(layerName);
                        background.add("alberi6N");
                        background.add("alberi7N");
                        background.add("alberi8N");
                        background.add("alberi9N");
                        background.add("alberi10N");
                    }else{
                        background.add(layerName);
                    }
                }

                else if (y < 0 && y > -250 && x > -500 && x < 500) {
                    if(layerName.equals("alberi1N") == true){
                        foreground.add(layerName);
                        foreground.add("alberi2N");
                        foreground.add("alberi3N");
                        foreground.add("alberi4N");
                    }else if(layerName.equals("alberi5N") == true){
                        foreground.add(layerName);
                        foreground.add("alberi6N");
                        foreground.add("alberi7N");
                        foreground.add("alberi8N");
                        foreground.add("alberi9N");
                        foreground.add("alberi10N");
                    }
                    else{
                        foreground.add(layerName);
                    }
                }
            }
        }

        //stessa cosa che faccio con i layer ma con la lista dei bot
        for (Bot bot : botList) {
            if (game.getPlayer().getPlayerPosition().y < bot.getPosition().y) {
                botListBack.add(bot);
            }
            else if (game.getPlayer().getPlayerPosition().y > bot.getPosition().y){
                botListFore.add(bot);
            }
        }

        for (Bot bot : nuotatoriList) {
            if (game.getPlayer().getPlayerPosition().y < bot.getPosition().y) {
                botListBack.add(bot);
            }
            else if (game.getPlayer().getPlayerPosition().y > bot.getPosition().y){
                botListFore.add(bot);
            }
        }

        //metodi controllo alberi
        for (MapObject obj : alberiFore.getObjects()){
            if (obj instanceof RectangleMapObject){
                RectangleMapObject lineObj = (RectangleMapObject)obj;

                y = lineObj.getRectangle().getY() - game.getPlayer().getPlayerPosition().y;
                x = lineObj.getRectangle().getX() - game.getPlayer().getPlayerPosition().x;

                if (y < 0 && y > -250 && x > -500 && x < 500) {
                    foreground.add("AlberiCima");
                    foreground.add("FixingLayer1");
                    foreground.add("AlberiFondo");
                    foreground.add("FixingLayer2");
                    foreground.add("AlberiMezzo");
                }
            }
        }

        for (MapObject obj : alberiBack.getObjects()){
            if (obj instanceof RectangleMapObject){
                RectangleMapObject lineObj = (RectangleMapObject)obj;

                y = lineObj.getRectangle().getY() - game.getPlayer().getPlayerPosition().y;
                x = lineObj.getRectangle().getX() - game.getPlayer().getPlayerPosition().x;

                if (y < 0 && y > -125 && x > -165 && x < 165) {
                    foreground.remove("AlberiCima");
                    foreground.remove("FixingLayer1");
                    foreground.remove("AlberiFondo");
                    foreground.remove("FixingLayer2");
                    foreground.remove("AlberiMezzo");
                    
                    background.add("AlberiCima");
                    background.add("FixingLayer1");
                    background.add("AlberiFondo");
                    background.add("FixingLayer2");
                    background.add("AlberiMezzo");
                }
            }
        }

        //background
        for (String layerName : background) {
            if (layerName != null) {
                renderLayer(layerName);
            }
        }

        for (Bot bot : botListBack) {
            game.renderPersonaggiSecondari(bot.getCurrentAnimation(),bot.getPosition().x, bot.getPosition().y, bot.getWidth(), bot.getHeight());
        }
        
        game.renderPlayer();

        //foreground
        for (String layerName : foreground) {
            if (layerName != null) {
                renderLayer(layerName);
            }
        }

        for (Bot bot : botListFore) {
            game.renderPersonaggiSecondari(bot.getCurrentAnimation(),bot.getPosition().x, bot.getPosition().y, bot.getWidth(), bot.getHeight());
        }
    }


    //-----------------------RENDERING E RICERCA LAYER------------------------------------------------------
    private void renderLayer(String layerName) {
        MapLayer layer;
        String nomeFolder = findGroupByLayerName(mappa, layerName);
        tileRenderer.getBatch().begin();
        
        // Recupera il layer dalla mappa
        if (nomeFolder != null) {
            layer = findLayerInGroup(mappa, nomeFolder, layerName);
        }
        else {
            layer = mappa.getLayers().get(layerName);
        }

        // Renderizza il layer
        tileRenderer.renderTileLayer((TiledMapTileLayer)layer); 

        tileRenderer.getBatch().end();

    }

    private String findGroupByLayerName(TiledMap map, String layerName) {
        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer instanceof MapGroupLayer) {
                MapGroupLayer groupLayer = (MapGroupLayer) mapLayer;
                for (MapLayer subLayer : groupLayer.getLayers()) {
                    if (subLayer.getName().equals(layerName)) {
                        return groupLayer.getName();
                    }
                }
            }
        }
        return null; // Se il layer non è all'interno di alcuna cartella
    }

    private MapLayer findLayerInGroup(TiledMap map, String groupName, String layerName) {
        MapLayer groupLayer = map.getLayers().get(groupName);
        if (groupLayer instanceof MapGroupLayer) {
            MapGroupLayer mapGroupLayer = (MapGroupLayer) groupLayer;
            for (MapLayer mapLayer : mapGroupLayer.getLayers()) {
                if (mapLayer.getName().equals(layerName)) {
                    return mapLayer;
                }
            }
        }
        return null;
    }

    //---------------------------------------CONTROLLO NPC------------------------------------------------------------------

    //metodo per fermare l'utente se passa di fronte al bot
    private void checkInteractionBot() {
        for (Bot bot : botList) {
            if (checkOverlaps(bot) && bot.getAffrontato() != true) {
                isNormalBot = true;
                giraPlayer(bot);
                triggerBot(bot);

                //apertura del Json e lettura per il testo
                if (bot.getPathBot() != null) {
                    FileHandle file = Gdx.files.internal(bot.getPathBot());
                    String jsonString = file.readString();
                    JsonValue json = new JsonReader().parse(jsonString);
                    JsonValue botTutto = json.get(bot.getNomeJson());
                    discorsoIniziale = new LabelDiscorsi(botTutto.getString("testo1"), 30, 0, false, false);
                    discorsoFinale = new LabelDiscorsi(botTutto.getString("testo2"), 30, 0, false, false);
                    leggiTesto = true;
                    nomeJson = bot.getNomeJson();
                }

                game.getPlayer().setMovement(false);

                if (faiMuovereBot == false) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            puntoEsclamativoImage.remove();
                            faiMuovereBot = true;
                            cancel();
                        }
                    }, 2f);
                }
                

                
                if (faiMuovereBot == true) {
                    muoviBot(bot);
                }
            }
        }
    }

    //metodo che fa muovere il bot verso il player e per disegnare il punto esclamativo
    private void triggerBot(Bot bot) {

        if (puntoEsclamativoImage != null) {
            puntoEsclamativoImage.remove();
        }

        if (faiMuovereBot == false) {
            Texture puntoEsclamativo = new Texture("bots/ExlMark.png");
            puntoEsclamativoImage = new Image(puntoEsclamativo);
            puntoEsclamativoImage.setSize(48,48);
            Vector3 botPosition = new Vector3(bot.getPosition().x, bot.getPosition().y,0);
            Vector3 screenCoords = camera.project(botPosition);

            // Imposta la posizione di puntoEsclamativoImage sulla schermata
            puntoEsclamativoImage.setPosition(screenCoords.x+bot.getPuntoX(), screenCoords.y+bot.getPuntoY());
            puntoEsclamativoImage.toFront();
            puntoEsclamativoImage.setName("puntoEsclamativoImage"); 
            stage.addActor(puntoEsclamativoImage);
        }
    }

    //metodo che gira il player verso il bot
    private void giraPlayer(Bot bot) {
        
        String direzione = bot.getDirezioneFissa();

        if (direzione.equals("-y")) {
            game.getPlayer().setFermoAvanti();
        }
        else if (direzione.equals("y")) {
            game.getPlayer().setFermoIndietro();
        }
        else if (direzione.equals("-x")) {
            game.getPlayer().setFermoDestra();
        }
        else if (direzione.equals("x")) {
            game.getPlayer().setFermoSinistra();
        }
    }


    private boolean checkOverlaps(Bot bot) {
        Rectangle playerBox = game.getPlayer().getBoxPlayer();
        Rectangle botBox = bot.getBoxBlocca();
        
        // Controlla se il rettangolo del personaggio è completamente contenuto nel rettangolo del bot
        if (playerBox.x >= botBox.x && playerBox.y >= botBox.y &&
            playerBox.x + playerBox.width <= botBox.x + botBox.width &&
            playerBox.y + playerBox.height <= botBox.y + botBox.height) {
            return true;
        }
        
        return false;
    }

    private void muoviBot(Bot bot) {
        float tot;
        Gdx.graphics.getDeltaTime();

        switch (bot.getDirezioneFissa()) {
            case "-y":

                if (torna) {

                    float positionY = (float)(int)bot.getPosition().y;
                    float yBase = (float)(int)bot.getYbase();

                    if (positionY < yBase) {
                        
                        bot.setDirezione("y");
                        bot.setMuovi(true);
                        faiMuovereBot = true;
                    }
                    else {
                        
                        bot.setMuovi(false);

                        bot.setFermoIndietro();
                        game.getPlayer().setMovement(true);
                        bot.setDirezione("-y");
                        faiMuovereBot = false;
                        bot.setAffrontato(true);

                        renderDiscorso = false;

                        //da mettere a false alla fine della battaglia (considerare di bloccare quel bot se finisce la battaglia prima)
                        inEsecuzione = false;

                        torna = false;
                        
                        
                    }
                }
                else {

                    tot = bot.getPosition().y - game.getPlayer().getPlayerPosition().y;
                    if (tot > 15) {
                        bot.setMuovi(true);
                    }
                    else {
                        bot.setMuovi(false);
                        faiMuovereBot = false;
                        inEsecuzione = true;

                        bot.setFermoIndietro();
                        game.getPlayer().setMovement(false);

                        renderDiscorso = true;
                    }
                }
                break;
                
            case "y":

                if (torna) {
                    float positionY = (float)(int)bot.getPosition().y;
                    float yBase = (float)(int)bot.getYbase();

                    if (positionY > yBase) {
                        bot.setDirezione("-y");
                        bot.setMuovi(true);
                        
                    }
                    else {
                        bot.setDirezione("y");
                        bot.setMuovi(false);
                        
                        bot.setAffrontato(true);
                        bot.setFermoIndietro();
                        game.getPlayer().setMovement(true);

                        renderDiscorso = false;

                        //da mettere a false alla fine della battaglia (considerare di bloccare quel bot se finisce la battaglia prima)
                        inEsecuzione = false;

                        torna = false;
                        faiMuovereBot = false;
                    }
                }
                else {

                    tot = bot.getPosition().y - game.getPlayer().getPlayerPosition().y;
                    if (tot > 15) {
                        bot.setMuovi(true);
                    }
                    else {
                        bot.setMuovi(false);
                        faiMuovereBot = false;
                        inEsecuzione = true;

                        bot.setFermoAvanti();
                        game.getPlayer().setMovement(false);

                        renderDiscorso = true;

                    }
                }
                
                break;
                
            case "-x":

                if (torna) {
                    float positionX = (float)(int)bot.getPosition().x;
                    float xBase = (float)(int)bot.getXbase();

                    if (positionX < xBase) {
                        bot.setDirezione("x");
                        bot.setMuovi(true);
                        
                    }
                    else {
                        bot.setDirezione("-x");
                        bot.setMuovi(false);
                        
                        bot.setAffrontato(true);
                        bot.setFermoSinistra();
                        game.getPlayer().setMovement(true);

                        renderDiscorso = false;
                        faiMuovereBot = false;

                        //da mettere a false alla fine della battaglia (considerare di bloccare quel bot se finisce la battaglia prima)
                        inEsecuzione = false;

                        
                        torna = false;
                    }
                }
                else {

                    tot = bot.getPosition().x - game.getPlayer().getPlayerPosition().x;
                    if (tot > 15) {
                        bot.setMuovi(true);
                    }
                    else {
                        bot.setMuovi(false);
                        faiMuovereBot = false;
                        inEsecuzione = true;

                        bot.setFermoSinistra();
                        game.getPlayer().setMovement(false);

                        renderDiscorso = true;

                    }
                }
            
                break;

            case "x":

                if (torna) {
                    float positionX = (float)(int)bot.getPosition().x;
                    float xBase = (float)(int)bot.getXbase();

                    if (positionX > xBase) {
                        bot.setDirezione("-x");
                        bot.setMuovi(true);
                        
                    }
                    else {
                        bot.setDirezione("x");
                        bot.setMuovi(false);
                        
                        bot.setAffrontato(true);
                        bot.setFermoDestra();
                        game.getPlayer().setMovement(true);

                        renderDiscorso = false;

                        //da mettere a false alla fine della battaglia (considerare di bloccare quel bot se finisce la battaglia prima)
                        inEsecuzione = false;

                        faiMuovereBot = false;
                        battagliaIsFinished = false;

                        
                        torna = false;
                    }
                }
                else {

                    tot = bot.getPosition().x + game.getPlayer().getPlayerPosition().x;
                    if (tot > 15) {
                        bot.setMuovi(true);
                    }
                    else {
                        bot.setMuovi(false);
                        faiMuovereBot = false;
                        inEsecuzione = true;

                        bot.setFermoDestra();
                        game.getPlayer().setMovement(false);

                        renderDiscorso = true;

                    }
                }
            
                break;
        
            default:
                break;
        }
    }

    private void renderizzaDiscorsoInizio() {
        
        if (renderDiscorso && continuaTesto) {
            discorsoIniziale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = discorsoIniziale.advanceText();
            }
        }
        else {
            if (!continuaTesto) {

                leggiTesto = false;
                battle = new Battle(this, nomeJson, true, null, null);
                
                //battagliaIsFinished = true;
                
                renderDiscorso = true;
            }
            continuaTesto = true;
            discorsoIniziale.reset();
        }
    }

    //metodo da chiamare dopo la battaglia per dire della vittoria
    private void fineBattaglia() {
            if (renderDiscorso && continuaTesto) {
                
                discorsoFinale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = discorsoFinale.advanceText();
            }
        }
        else {
            if (!continuaTesto) {
                //va a mettere torna a true e quindi a far partire l'animazione del ritorno del bot

                torna = true;
                inEsecuzione = false;
                battagliaIsFinished = false;
                leggiTesto = true;
                
            }
            
            renderDiscorso = false;
            continuaTesto = true;
            discorsoFinale.reset();
        }
    }

    private void fineBattagliaNuotatore() {
        if (renderDiscorso && continuaTesto) {
            discorsoFinale.renderDisc();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                //da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                continuaTesto = discorsoFinale.advanceText();
            }
        }
        else {
            if (!continuaTesto) {
            //va a mettere torna a true e quindi a far partire l'animazione del ritorno del bot

                bot.setMuovi(true);
                inEsecuzione = false;
                bot.setAffrontato(true);
                game.getPlayer().setMovement(true);
                battagliaIsFinished = false;
                faiAvvicinare = false;
            
            }
        
            renderDiscorso = false;
            continuaTesto = true;
            discorsoFinale.reset();
        }
}

    public void checkFerma() {
        for (Bot bot : nuotatoriList) {
            if (game.getPlayer().getBoxPlayer().overlaps(bot.getBoxNuotatore()) && bot.getAffrontato() != true) {
                isNormalBot = false;
                bot.setMuovi(false);
                triggerBot(bot);
                this.bot = bot;

                if (bot.getPathBot() != null) {
                    FileHandle file = Gdx.files.internal(bot.getPathBot());
                    String jsonString = file.readString();
                    JsonValue json = new JsonReader().parse(jsonString);
                    JsonValue botTutto = json.get(bot.getNomeJson());
                    discorsoIniziale = new LabelDiscorsi(botTutto.getString("testo1"), 30, 0, false, false);
                    discorsoFinale = new LabelDiscorsi(botTutto.getString("testo2"), 30, 0, false, false);
                    leggiTesto = true;
                    nomeJson = bot.getNomeJson();
                }

                game.getPlayer().setMovement(false);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        puntoEsclamativoImage.remove();
                        faiAvvicinare = true;
                        cancel();
                    }
                }, 2f);

                if (faiAvvicinare) {
                    muoviNuotatore(bot);
                }
            }
        }
    }

    private void muoviNuotatore(Bot bot) {
        float playerY = game.getPlayer().getPlayerPosition().y;
        float botY = bot.getPosition().y;
        float distanza;
        switch (bot.getDirezione()) {
            case "-y":
                game.getPlayer().setSurfAvanti();
                distanza = botY - playerY;
                if (distanza < 40) {
                    faiAvvicinare = false;
                    inEsecuzione = true;
                    renderDiscorso = true;
                }
                else {
                    bot.avvicinaNuotatore();
                }
                break;

            case "y":
            game.getPlayer().setSurfIndietro();
                distanza = playerY - botY;
                if (distanza < 30) {
                    faiAvvicinare = false;
                    inEsecuzione = true;
                    renderDiscorso = true;
                }
                else {
                    bot.avvicinaNuotatore();
                }
                break;
        }
    }
    
    

    //-------------------------SETTAGGO POSIZIONI E CONTROLLO LUOGO---------------------------------------
    public void checkInAcqua() {
        boolean inAcqua = false;
        MapObjects objects = mappa.getLayers().get("acquaPercoso3").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    inAcqua = true;
                    break;
                }
            }
        }
        if (inAcqua) {
            game.getPlayer().setDimensionSurf();
        }
        else {
            game.getPlayer().setDimensionCammina();
        }

    }

    public void checkLuogo() {
        MapObjects objects = mappa.getLayers().get("controlloLuogo").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    game.setLuogo(rectangleObject.getName());
                    game.getMusica().startMusic(rectangleObject.getName());
                }
            }
        }
    }

    public void teleport() {
        MapObjects objects = mappa.getLayers().get("teleport").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                    change(rectangleObject);
                    game.setLuogo(rectangleObject.getName());
                }
            } 
        }
    }

    public void change(RectangleMapObject rectangleObject) {
        if (rectangleObject.getName().equals("grotta")) {
            game.setIngressoGrotta("ingressoCapitale");
        }
        game.setPage(rectangleObject.getName());
    }

    public void setPositionPlayer() {
        String luogo = game.getTeleport();
        float x;
        float y;
        MapLayer uscita = mappa.getLayers().get("uscita");
        for (MapObject object : uscita.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                
                if (object.getName().equals(luogo)) {
                    x = rect.getX();
                    y = rect.getY();
                    game.getPlayer().setPosition(x, y);
                }
            }
        }
        
    }

    //------------------------------SETTAGGIO BOT---------------------------------------

    //metodo che va a posizionare i bot
    private void setPositionBot() {
        MapLayer rettangoliBlocca = mappa.getLayers().get("p1_bot_linea");
        MapLayer posizione = mappa.getLayers().get("p1_bot");
        for (MapObject object : posizione.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();

                if (object.getProperties().get("path") != null) {
                    //nel caso in cui ci sono i bot che non sono in acqua
                    if (object.getProperties().get("nuota") == null) {

                        //creo un nuovo bot andando a prendere la path dello spritesheet dalla sua proprietà
                        Bot bot = new Bot(24, 24, (String)object.getProperties().get("path"),35,60);
                        bot.setPosition(rect.getX(),rect.getY());
                        bot.setXbase(rect.getX());
                        bot.setYbase(rect.getY());

                        //metodo per salvare Json e settarlo affrontato o no
                        salvaBotJson(object, bot);

                        //metodo che va a collegare il rettangolo blocca al bot
                        setBlocca(rettangoliBlocca, bot, rectObject);

                        //lista dei bot
                        botList.add(bot);

                        //lista per le collisioni del bot
                        rectList.add(bot.getBoxPlayer());
                    }
                    //bot in acqua
                    else {
                        Bot bot = new Bot(24, 24, (String)object.getProperties().get("path"),35,60, 
                        convertStringToFloat((String)object.getProperties().get("nuota")), rect.getX(), rect.getY());
                        bot.setPosition(rect.getX(),rect.getY());
                        bot.setXbase(rect.getX());
                        bot.setYbase(rect.getY());
                        bot.setInAcqua(true);
                        bot.setCamminataFrameSpeed(0.2f);
                        

                        //metodo che va a collegare il rettangolo blocca al bot
                        setBlocca(rettangoliBlocca, bot, rectObject);
                        
                        String direzione;
                        if (convertStringToFloat((String)object.getProperties().get("nuota")) < 0) {
                            direzione = "-y";
                        }
                        else {
                            direzione = "y";
                        }
                        giraNuotatore(bot, direzione);

                        //metodo per salvare Json e settarlo affrontato o no
                        salvaBotJson(object, bot);

                        nuotatoriList.add(bot);

                        //lista per le collisioni del bot
                        rectList.add(bot.getBoxPlayer());

                        bot.setMuovi(true);

                    }
                }
            }
        }
    }

    public float convertStringToFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return Float.NaN;
        }
    }

    //metodo che salva il nomeJson su bot e lo setta come affrontato o no
    private void salvaBotJson(MapObject object, Bot bot) {
        if ((String)object.getProperties().get("nomeJson") != null) {
            bot.setnomeJson((String)object.getProperties().get("nomeJson"));
            if (bot.getPathBot() != null) {
                FileHandle file = Gdx.files.internal(bot.getPathBot());
                String jsonString = file.readString();
                JsonValue json = new JsonReader().parse(jsonString);
                JsonValue botTutto = json.get(bot.getNomeJson());
                if (botTutto.getInt("affrontato") == 1) {
                    bot.setAffrontato(true);
                }
                else {
                    bot.setAffrontato(false);
                }
            }
        }
    }

    //metodo che va a collegare il rettangolo blocca al bot giusto
    private void setBlocca(MapLayer rettangoliBlocca, Bot bot, RectangleMapObject rectObject) {
        for (MapObject obj : rettangoliBlocca.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject)obj;

                String layerName = (String)rectObj.getProperties().get("check");

                
                if (layerName.equals((String)rectObject.getProperties().get("check"))) {

                    if (rectObject.getProperties().get("nuota") == null) {
                        bot.setBoxBlocca(rectObj.getRectangle());
                        bot.setDirezione((String)rectObj.getProperties().get("direzione"));
                        bot.setDirezioneFissa((String)rectObj.getProperties().get("direzione"));
                        gira(bot, (String)rectObj.getProperties().get("direzione"));
                    }
                    else {
                        //da fare per quelli che nuotano
                        //(sarebbe quella dove deve fermarsi per poi girarsi)
                        bot.setYfinale(rectObj.getRectangle().getY());
                        bot.setDirezione((String)rectObj.getProperties().get("direzione"));
                        bot.setDirezioneFissa((String)rectObj.getProperties().get("direzione"));

                    }
                }
            }
        }
    }

    private void gira(Bot bot, String direzione) {
        switch (direzione) {
            case "-y":
                bot.setFermoIndietro();
                break;
            case "y":
                bot.setFermoAvanti();
                break;
            case "-x":
                bot.setFermoSinistra();
                break;
            case "x":
                bot.setFermoDestra();
                break;
            default:
                break;
        }
    }

    private void giraNuotatore(Bot bot, String direzione) {
        switch (direzione) {
            case "-y":
                bot.setCamminaIndietro();
                break;
            case "y":
                bot.setCamminaAvanti();
                break;
            case "-x":
                bot.setCamminaSinistra();
                break;
            case "x":
                bot.setCamminaDestra();
                break;
            default:
                break;
        }
    }

    private void controllaPresenzaStarter() {
        try {            
            // Carica il file JSON
            FileHandle file = Gdx.files.internal("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            // Parsea il JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue poke1 = json.get("poke1");

            if (poke1 != null) {
                String nomePokemon = poke1.getString("nomePokemon", "");
                
                if (nomePokemon.isEmpty()) {

                    MapLayer oggettiStoria = game.getOggettiStoria();

                    for (MapObject object : oggettiStoria.getObjects()) {
                        // Verifica se l'oggetto ha la proprietà "tipoBlocco" con valore "prendiStater"
                        String tipoBlocco = (String)object.getProperties().get("tipoBlocco");

                        if ("prendiStarter".equals(tipoBlocco)) {

                            // Imposta la proprietà "considerare" su true
                            object.getProperties().put("considerare", true);
                        }
                    }
                }
            } else {
                MapLayer oggettiStoria = game.getOggettiStoria();

                for (MapObject object : oggettiStoria.getObjects()) {
                    // Verifica se l'oggetto ha la proprietà "tipoBlocco" con valore "prendiStater"
                    String tipoBlocco = object.getProperties().get("tipoBlocco", String.class);
                    if ("prendiStarter".equals(tipoBlocco)) {
                        // Imposta la proprietà "considerare" su true
                        object.getProperties().put("considerare", true);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("errore sul prindiStarter: " + e);
        }
    }

    @Override
    public void dispose() {
        //mappa.dispose();
    }

    @Override
    public void closeBattle() {
        //game.getPlayer().setMovement(true);
        Gdx.input.setInputProcessor(MenuLabel.getStage());
        battle = null;
        battagliaIsFinished = true;
    }

}
