package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Battle extends ScreenAdapter {
    private final MercurioMain game;

    private Texture texture;
    TextureRegion[] frames;
    TextureRegion background;


    public Battle(MercurioMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("sfondo/battleBackground.png"));

        // Divide lo spritesheet in 3 colonne e 4 righe
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 4);
        frames = new TextureRegion[12];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Imposta lo sfondo
        background = frames[0]; // Utilizza il primo sprite come sfondo

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


    }

}
