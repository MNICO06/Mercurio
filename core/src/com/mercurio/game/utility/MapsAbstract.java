package com.mercurio.game.utility;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.mercurio.game.Screen.MercurioMain;

public abstract class MapsAbstract extends ScreenAdapter{
    //TODO: tutte le variabili qua dentro non vanno ridefinite negli altri punti
    protected final MercurioMain game;

    //informazioni per l'uscita dalle case
    protected Rectangle rettangoloUscita; //il rettangolo per l'uscita dalle case
    protected String mappaDestinazione;   //il nome della mappa in cui devo andare
    protected String rettangoloPosizione; //il nome del rettangolo da cui prendere la posizione quando esco

    /*
     * Idee per come fare l'uscita delle mappe multiple
     * Idea1 : funzione da chiamare in mercurioMain che salvi i nomi dell'uscita (mappaDestinazione e rettangoloPosizione)
     * 
     */
    
    
    protected TiledMap map;

    
    public MapsAbstract(MercurioMain game) {
        this.game = game;
    }

    protected void settaPlayerPosition() {
        //true se proviene dal fullmap, se no non faccio nulla che prende le posizioni salvate nel json
        if (game.getProvieneDaMappa()) {
            MapObjects objects = map.getLayers().get("teleport").getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    // Se l'oggetto è un rettangolo
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;
                    game.getPlayer().setPosition(rectangleObject.getRectangle().getX(), rectangleObject.getRectangle().getY());
                } 
            }
            game.setProvieneDaMappa(false);
        }
        game.setPokemonMorti(false);
    }



    /*
     * FUNZIONI DA USARE SOLO NELLE CASE
     */

    /* 
     * funzione per il recupero del rettangolo di uscita dalle case con le rispettive variabili necessarie
     *
     * IMPORTANTE: chiamare questa funzione in show di ogni casa
     */
    protected void prendiUscitaCase() {
        try {
            
        //prendere rettangolo per mappa
        MapObjects objects = map.getLayers().get("exit").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                // Se l'oggetto è un rettangolo
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                // Ottieni il rettangolo con i rispettivi dati utili
                rettangoloUscita = rectangleObject.getRectangle();
                mappaDestinazione = (String) object.getProperties().get("mappaDestinazione");
                rettangoloPosizione = (String) object.getProperties().get("rettangoloPosizione");
            }
        }
        } catch (Exception e) {
            System.out.println("Errore prendiUscitaCase" + e);
        }
    }

    /*
     * funzione per uscire da una casa
     * 
     * IMPORTANTE: chiamare questa funzione in render di ogni casa
     */
    public void controllaUscita() {
        try {
            if (game.getPlayer().getBoxPlayer().overlaps(rettangoloUscita)) {
                game.setRettangoloPosizioneUscita(rettangoloPosizione); //modificare poi setTeleport
                game.setPage(mappaDestinazione);
            }
        } catch (Exception e) {
            System.out.println("Errore controlloUscita" + e);
        }
    }
}
