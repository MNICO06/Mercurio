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
    private final int numeroPokemonP1 = 20;
    private final int numeroPokemonBosco = 2;
    private final String nomeJsonP1 = "percorso1";
    private final String nomeJsonBosco = "bosco";

    public Erba(MercurioMain game) {
        this.game = game;
        random = new Random();
    }

    public void controllaPokemon(TiledMap map) {
        
        switch (game.getLuogo()) {
            case "percorso1":
                if (check(map, "erbaAltaPercorso1") && game.getIsInMovement()) {
                    //è dentro nell'erba quindi fa un calcolo randomico

                    int num = random.nextInt(200);
                    if (estratto==0){
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            if (checkPerDoppioPoke>1){
                                return; //Se viene estratto un pokemon quando è già in corso una battaglia, viene bloccato il secondo (è un bug che succedeva e bloccava tutto il programma)
                            }
                            estraiPokemonP1();
                            checkPerDoppioPoke=0;
                            //System.out.println("no");
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
                            if (checkPerDoppioPoke>1){
                                return;
                            }
                            estraiPokemonBosco();
                            checkPerDoppioPoke=0;
                        }
                    }
                }
                break;

            case "percorso2":
                if (check(map, "erbaAltaPercorso2") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            if (checkPerDoppioPoke>1){
                                return;
                            }
                            checkPerDoppioPoke=0;
                    }
                }
                break;

            case "percorso3":
                if (check(map, "acquaPercoso3") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            if (checkPerDoppioPoke>1){
                                return;
                            }
                            checkPerDoppioPoke=0;
                    }
                }
                break;
                
            case "percorso4":
                if (check(map, "erbaAltaPercorso4") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                        if (num == 39) {
                            checkPerDoppioPoke++;
                            if (checkPerDoppioPoke>1){
                                return;
                            }
                            checkPerDoppioPoke=0;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void estraiPokemonP1() {
        int num = 1 + random.nextInt(numeroPokemonP1);
        game.creaBattaglia(nomeJsonP1, String.valueOf(num));
        estratto=1;
    }

    private void estraiPokemonBosco() {
        int num = 1 + random.nextInt(numeroPokemonBosco);
        game.creaBattaglia(nomeJsonBosco, String.valueOf(num));
        estratto=1;
    }

    private boolean check(TiledMap map, String nome) {
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
    }

    private void setEstraibile(){
        estratto=0;
    }

}
