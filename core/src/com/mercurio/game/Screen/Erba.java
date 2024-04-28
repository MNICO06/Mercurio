package com.mercurio.game.Screen;

import java.util.Random;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Erba {
    private MercurioMain game;

    public Erba(MercurioMain game) {
        this.game = game;
    }

    public void controllaPokemon(TiledMap map) {
        Random random = new Random();
        
        switch (game.getLuogo()) {
            case "percorso1":
                if (check(map, "erbaAltaPercorso1") && game.getIsInMovement()) {
                    //è dentro nell'erba quindi fa un calcolo randomico

                    int num = random.nextInt(200);
                    if (num == 4) {
                        
                    }
                }
                break;
            
            case "bosco":
                if (check(map, "erbaAltaBosco") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (num == 5) {
                    
                    }
                }
                break;

            case "percorso2":
                if (check(map, "erbaAltaPercorso2") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (num == 6) {
                    
                    }
                }
                break;

            case "percorso3":
                if (check(map, "acquaPercoso3") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (num == 6) {
                    
                    }
                }
                break;
                
            case "percorso4":
                if (check(map, "erbaAltaPercorso4") && game.getIsInMovement()) {
                    int num = random.nextInt(200);
                    if (num == 6) {
                    
                    }
                }
                break;
            default:
                break;
        }
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

}
