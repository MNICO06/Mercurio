package com.mercurio.game.Screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Render {
    public String type;
    public String layerName;
    public float x, y, width, height;
    public TextureRegion texture;
    public String persona;

    // Costruttore per i layer
    public Render(String type, String layerName, float y) {
        this.type = type;
        this.layerName = layerName;
        this.y = y;
    }

    // Costruttore per bot
    public Render(String type, TextureRegion texture, float x, float y, float width, float height, String persona) {
        this.type = type;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.persona = persona;
    }


    public Render(String type, float y) {
        this.y = y;
        this.type = type;
    }
}
