package com.mercurio.game.Screen;

import java.util.Random;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Erba {
    private MercurioMain game;
    Random random;
    public static int estratto=0;

    private int checkPerDoppioPoke=0;
    private final int numeroPokemonP1 = 5;
    private final int numeroPokemonBosco = 6;
    private final int numeroPokemonP2 = 4;
    private final int numeroPokemonMare = 7;
    private final int numeroPokemonP4 = 7;
    private final int numeroPokemonGrotta1 = 8;
    private final String nomeJsonP1 = "percorso1";
    private final String nomeJsonBosco = "bosco";
    private final String nomeJsonP2 = "percorso2";
    private final String nomeJsonMare = "percorsoMare";
    private final String nomeJsonP4 = "percorso4";
    private final String nomeJsonGrotta1 = "grotta1";

    public Erba(MercurioMain game) {
        this.game = game;
        random = new Random();
    }

    public void controllaPokemon(TiledMap map) {
        try {
            
        
        switch (game.getLuogo()) {
            case "percorso1":
                if (check(map, "erbaAltaPercorso1") && game.getIsInMovement()) {
                    //è dentro nell'erba quindi fa un calcolo randomico

                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonP1();
                        }
                    }
                }
                break;
            
            case "bosco":
                if (check(map, "erbaAltaBosco") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonBosco();
                        }
                    }
                }
                break;

            case "percorso2":
                if (check(map, "erbaAltaPercorso2") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonP2();
                        }
                    }   
                }
                break;

            case "percorso3":
                if (check(map, "acquaPercoso3") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonMare();
                        }
                    }
                }
                break;
                
            case "percorso4":
                if (check(map, "erbaAltaPercorso4") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonP4();
                        }
                    }
                }
                break;

            case "grotta":
                if (check(map, "erbaAltaGrotta") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            estraiPokemonGrotta();
                        }
                    }
                }
                break;

                
            default:
                break;
        }
        } catch (Exception e) {
            System.out.println("Errore controllaPokemon erba, " + e);
        }
        
    }

    private void estraiPokemonP1() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonP1);
        game.creaBattaglia(nomeJsonP1, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private void estraiPokemonBosco() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonBosco);
        game.creaBattaglia(nomeJsonBosco, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private void estraiPokemonP2() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonP2);
        game.creaBattaglia(nomeJsonP2, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private void estraiPokemonMare() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonMare);
        game.creaBattaglia(nomeJsonMare, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private void estraiPokemonP4() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonP4);
        game.creaBattaglia(nomeJsonP4, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private void estraiPokemonGrotta() {
        if (checkPerDoppioPoke>1){
            return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
        }
        int num = 1 + random.nextInt(numeroPokemonGrotta1);
        game.creaBattaglia(nomeJsonGrotta1, String.valueOf(num));
        estratto=1;
        checkPerDoppioPoke=0;
    }

    private boolean check(TiledMap map, String nome) {
        try {
            
            MapObjects objects = map.getLayers().get(nome).getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    // Se l'oggetto è un rettangolo
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;

                    if (game.getPlayer().getBoxPlayer().overlaps(rectangleObject.getRectangle())) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Errore check erba, " + e);
            return false;
        }
        
    }

}
