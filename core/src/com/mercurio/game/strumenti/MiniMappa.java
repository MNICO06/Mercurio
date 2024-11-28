package com.mercurio.game.strumenti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.mercurio.game.menu.Borsa;
import com.mercurio.game.menu.MenuLabel;

public class MiniMappa {
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    private Array<Actor> medaglieActor;
    private Borsa chiamanteB;
    private Image background;
    private boolean isMedaglieChiuso;
    private Image tastoXImage;

    public MiniMappa(Stage stage, Borsa chiamanteB){
        
        this.stage = stage;
        this.font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));
        this.chiamanteB = chiamanteB;
        this.medaglieActor = new Array<>();

        Gdx.input.setInputProcessor(stage);
        show();
    }

    private void show(){

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Stato iniziale
        isMedaglieChiuso = true;
        Texture backgroundTexture = new Texture("sfondo/miniMappaCompleta.png");
        background = new Image(backgroundTexture);
        background.setSize(700, 700);
        stage.addActor(background);
        medaglieActor.add(background);


        Texture closeButtonTexture = new Texture("sfondo/x.png");
        NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
        NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);
    
        Texture tastoX = new Texture("sfondo/X.png");
        tastoXImage = new Image(tastoX);
        tastoXImage.setPosition(screenWidth - 86, 10);
        stage.addActor(tastoXImage);
        medaglieActor.add(tastoXImage);
        
        tastoXImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pulsisciInventario();
                chiamanteB.closeMiniMappa();
            }
        });
    }


    public void render(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
    }

    private void pulsisciInventario() {
        for (Actor actor : medaglieActor) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
        medaglieActor.clear();
    }
    
}
