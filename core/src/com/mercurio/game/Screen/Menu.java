package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Menu extends ScreenAdapter{
    private final MercurioMain game;

    private SpriteBatch spriteBatch;
    private Image background;
    private Image pokemon;
    Label labelContinua;
    String testoContinua = "Premi un qualsiasi tasto per continuare";
    Label labelNome;

    // Schermata
    private Stage stage;

    private OrthographicCamera camera;


    float x = 0 ;
    private float durata = 6f;

    public Menu(MercurioMain game) {
        this.game = game;
    }


    private void createMenu() {
        //tutti i comandi per andare a creare e scrivere il testo con il font giusto (per continuare)
        BitmapFont fontBase = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        fontBase.getData().setScale(3.0f);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = fontBase; 
        labelContinua = new Label(testoContinua, labelStyle);
        labelContinua.setPosition(220, 20);

    }

    @Override
    public void show() {
        stage = new Stage();

        spriteBatch = new SpriteBatch();

        Texture background_texture = new Texture(Gdx.files.internal("menuImage/introImage.jpg"));
        
        background = new Image(background_texture);
        background.setSize(1024,600);
        background.setPosition(0,x);

        //da cambiare con quello nuovo
        Texture titoloPokemon = new Texture(Gdx.files.internal("menuImage/logoMercurio.png"));
        pokemon = new Image(titoloPokemon);
        pokemon.setSize(700,400);
        pokemon.setPosition(150, 400);

        MoveToAction moveUpAction = new MoveToAction();
        moveUpAction.setPosition(x, 126); // Imposta la posizione in alto
        moveUpAction.setDuration(durata);
        

        // Crea un'azione per muovere l'immagine verso il basso
        MoveToAction moveDownAction = new MoveToAction();
        moveDownAction.setPosition(x, 20); // Imposta la posizione in basso
        moveDownAction.setDuration(durata);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 720);
        camera.update();

        SequenceAction sequenceAction = Actions.sequence(moveUpAction, moveDownAction);


        float durationNonVisibile = 0.5f;
        float durationVisibile = 2f;
        Action lampeggia = Actions.sequence(
            Actions.visible(true),  // Mostra il testo
            Actions.delay(durationVisibile),  // Attende di nuovo
            Actions.visible(false), // Nasconde il testo
            Actions.delay(durationNonVisibile) // Attende per la durata specificata
        );

        background.addAction(Actions.forever(sequenceAction));

        stage.addActor(background);
        stage.addActor(pokemon);

        createMenu();

        labelContinua.addAction(Actions.forever(lampeggia));

        stage.addActor(labelContinua);

    }

    @Override
    public void render(float delta) {
        
        // Cancella il buffer del colore con il colore nero
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controlloTasti();

        stage.act(delta);
        stage.draw();

        camera.update();
    }

    //quando viene messa in pausa la scheda si interromono le animazioni
    @Override
    public void pause() {
        stage.getRoot().clearActions();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }

    private void controlloTasti() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.loadGame();
        }
    }
}

