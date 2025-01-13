package com.mercurio.game.utility;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mercurio.game.Screen.MercurioMain;

public abstract class MapsAbstract extends ScreenAdapter{
    protected final MercurioMain game;
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
    
}
