package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SchermataLogo extends ScreenAdapter{
    private final MercurioMain game;
    private SpriteBatch spriteBatch;
    private Image background;
    private Image logo;


    private Stage stage;


    public SchermataLogo(MercurioMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();

        spriteBatch = new SpriteBatch();

        Texture background_texture = new Texture(Gdx.files.local("assets/menuImage/scrittaMercurio.jpg"));
        
        background = new Image(background_texture);
        background.setSize(1024,720);
        background.setPosition(0,0);
        stage.addActor(background);

        Texture logoTexture = new Texture(Gdx.files.local("assets/menuImage/logoGruppo.png"));
        logo = new Image(logoTexture);
        logo.setSize(64,64);
        logo.setPosition(940,10);
        stage.addActor(logo);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }
}
