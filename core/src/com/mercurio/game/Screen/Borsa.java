package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Borsa {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    public Borsa() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        batch.begin();
        stage.draw(); // Disegna lo stage sullo SpriteBatch
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    private void createUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Sfondo della borsa
        Texture backgroundTexture = new Texture("sfondo/sfondo.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);

        // Etichetta della borsa
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label label = new Label("Contenuto della borsa", labelStyle);
        label.setPosition(50, screenHeight - 100);
        stage.addActor(label);

        // Pulsante per chiudere la borsa
        Texture closeButtonTexture = new Texture("sfondo/x.png");
        NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
        NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);

        Label closeLabel = new Label("", labelStyle);
        closeLabel.setStyle(label.getStyle());
        closeLabel.setWidth(75);
        closeLabel.setHeight(75);
        closeLabel.setPosition(screenWidth - 100, screenHeight - 100);
        closeLabel.setStyle(label.getStyle());
        closeLabel.getStyle().background = closeButtonDrawable;
        closeLabel.addListener(event -> {
            close();
            return false;
        });
        stage.addActor(closeLabel);
    }

    private void close() {
        Array<Actor> actors = stage.getActors();
        Array<Actor> actorsToRemove = new Array<>();

        for (Actor actor : actors) {
            actorsToRemove.add(actor);
        }

        // Rimuovi gli attori in modo ritardato per simulare una chiusura graduale
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Actor actor : actorsToRemove) {
                    actor.remove();
                }
            }
        }, 0.1f); // Ritardo di 0.1 secondi prima di rimuovere gli attori
    }
}
