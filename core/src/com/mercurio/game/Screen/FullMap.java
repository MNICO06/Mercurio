package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.mercurio.game.personaggi.Bot;
import com.mercurio.game.personaggi.TeenagerF;
import com.mercurio.game.personaggi.TeenagerM;


public class FullMap extends ScreenAdapter{
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
    private ArrayList<Bot> botListBack;
    private ArrayList<Bot> botListFore;

    private Image puntoEsclamativoImage;
    private Stage stage;

    private Timer timer;
    private float stateTime;

    //quando entra la mette a true e fino a quando non termina la battaglia rimane a true, quando termina la battaglia viene rimessa
    //a false e viene salvato il dato sul json e anche sul bot. (momentaneamente poi non funziona più)
    private boolean inEsecuzione = false;

    private boolean faiMuovereBot = false;

    private boolean isChanging = false;

    private boolean giaInAcqua = false;

    //---------------------------CLASSE DENTRO CLASSE PER LA TASK-------------------------------------------------
    private class MyTimerTask extends TimerTask {
        private Bot bot;

        // Costruttore che accetta i parametri
        public MyTimerTask(Bot bot) {
            this.bot = bot;
        }

        @Override
        public void run() {
            puntoEsclamativoImage.remove();
            game.getPlayer().setMovement(true);
            faiMuovereBot = true;
            cancel();
        }
    }
    //----------------------------------------------------------------------------

    public FullMap(MercurioMain game, TiledMap mappa) {
        this.game = game;
        this.mappa = mappa;
        rectList = new ArrayList<Rectangle>();
        botList = new ArrayList<Bot>();
        botListBack = new ArrayList<Bot>();
        botListFore = new ArrayList<Bot>();
        stage = new Stage();
        timer = new Timer();
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

        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
        lineeLayer = game.getLineeLayer();
        alberiBack = game.getAlberiBack();
        alberiFore = game.getAlberiFore();
        divAlberi = game.getDivAlberi();
        cambiaProfondita(lineeLayer);

        checkLuogo();
        teleport();
        if (game.getLuogo().equals("percorso3") || game.getLuogo().equals("cittaMare") ) {
            checkInAcqua();
        }
    
        
        game.setRectangleList(rectList);
        
        /*
        for (Bot bot : botList) {
            bot.updateStateTime(stateTime);
        }
        */

        //System.out.println(inEsecuzione);
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
        float y;
        float x;

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
                    background.add(layerName);
                }

                else if (y < 0 && y > -250 && x > -500 && x < 500) {
                    foreground.add(layerName);
                }
            }
        }

        //stessa cosa che faccio con i layer ma con la lista dei bot
        for (Bot bot : botList) {
            if (game.getPlayer().getPlayerPosition().y < bot.getPosition().y) {
                botListBack.add(bot);
            }
            else {
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
                    background.remove("AlberiCima");
                    background.remove("FixingLayer1");
                    background.remove("AlberiFondo");
                    background.remove("FixingLayer2");
                    background.remove("AlberiMezzo");
                    
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

                if (y < 0 && y > -250 && x > -500 && x < 500) {
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
    public void checkInteractionBot() {
        for (Bot bot : botList) {
            if (checkOverlaps(bot)) {
                giraPlayer(bot);
                triggerBot(bot);

                stateTime += Gdx.graphics.getDeltaTime();
                bot.updateStateTime(stateTime);

                game.getPlayer().setMovement(false);
                MyTimerTask myTimerTask = new MyTimerTask(bot);
                timer.schedule(myTimerTask, 2000);
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
        String direzione = bot.getDirezione();
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


    public boolean checkOverlaps(Bot bot) {
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

    public void muoviBot(Bot bot) {
        float tot;
        switch (bot.getDirezione()) {
            case "-y":
                
                bot.setCamminaIndietro();

                tot = bot.getPosition().y - game.getPlayer().getPlayerPosition().y;
                if (tot > 15) {
                    bot.setY(bot.getPosition().y - 40f * Gdx.graphics.getDeltaTime());
                }
                else {
                    faiMuovereBot = false;

                    //da mettere a false alla fine della battaglia
                    inEsecuzione = true;

                    bot.setFermoIndietro();
                    bot.setPosition(bot.getXbase(), bot.getYbase());
                }
                break;
                
            case "y":
                
                break;
                
            case "-x":
            
                break;

            case "x":
            
                break;
        
            default:
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
        game.setPage(rectangleObject.getName());
    }

    public void setPositionPlayer() {
        String luogo = game.getTeleport();
        float x = 100;
        float y = 3000;
        MapLayer uscita = mappa.getLayers().get("uscita");
        for (MapObject object : uscita.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                
                if (object.getName().equals(luogo)) {
                    x = rect.getX();
                    y = rect.getY();
                }
            }
        }
        game.getPlayer().setPosition(x, y);
    }

    //metodo che va a posizionare i bot
    public void setPositionBot() {
        MapLayer rettangoliBlocca = mappa.getLayers().get("p1_bot_linea");
        MapLayer posizione = mappa.getLayers().get("p1_bot");
        for (MapObject object : posizione.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();

                switch (object.getName()) {
                    case "TeenagerM":
                        //se il rettangolo di posizione si chiama così allora mi salvo come bot di tipo TeenagerM nella lista
                        TeenagerM bot = new TeenagerM();
                        bot.setPosition(rect.getX(),rect.getY());
                        bot.setXbase(rect.getX());
                        bot.setYbase(rect.getY());

                        //ciclo per andare a controllare se la proprietà stringa check è uguale a quella del rettangolo per fermare il bot
                        //se si faccio un set di quel rettangolo
                        for (MapObject obj : rettangoliBlocca.getObjects()) {
                            if (obj instanceof RectangleMapObject) {
                                RectangleMapObject rectObj = (RectangleMapObject)obj;
                
                                String layerName = (String)rectObj.getProperties().get("check");
                                bot.setDirezione((String)rectObj.getProperties().get("direzione"));
                
                                if (layerName.equals((String)rectObject.getProperties().get("check"))) {
                                    bot.setBoxBlocca(rectObj.getRectangle());
                                }
                                
                            }
                        }

                        botList.add(bot);
                        rectList.add(bot.getBoxPlayer());

                        //assegno al layer che mi da la poszione un layer che ha il nome del layer della collisione
                        break;
                    
                    case "TeenagerF":
                        TeenagerF bot1 = new TeenagerF();
                        bot1.setPosition(rect.getX(),rect.getY());
                        bot1.setXbase(rect.getX());
                        bot1.setYbase(rect.getY());

                        for (MapObject obj : rettangoliBlocca.getObjects()) {
                            if (obj instanceof RectangleMapObject) {
                                RectangleMapObject rectObj = (RectangleMapObject)obj;
                
                                String layerName = (String)rectObj.getProperties().get("check");
                                bot1.setDirezione((String)rectObj.getProperties().get("direzione"));
                
                                if (layerName.equals((String)rectObject.getProperties().get("check"))) {
                                    bot1.setBoxBlocca(rectObj.getRectangle());
                                }
                                
                            }
                        }

                        botList.add(bot1);
                        rectList.add(bot1.getBoxPlayer());


                        break;
                
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void dispose() {
        //mappa.dispose();
    }

}
