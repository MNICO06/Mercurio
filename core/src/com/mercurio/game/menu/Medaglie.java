package com.mercurio.game.menu;

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


public class Medaglie {
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    private Array<Actor> medaglieActor;
    private MenuLabel chiamanteM;
    private Image background;
    private boolean isMedaglieChiuso;
    private Image tastoXImage;

    public Medaglie(Stage stage, MenuLabel chiamanteM){

        this.stage = stage;
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.chiamanteM = chiamanteM;
        this.medaglieActor = new Array<>();

        show();

    }

    private void show(){

        try {
            
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Stato iniziale
            isMedaglieChiuso = true;
            Texture backgroundTexture = new Texture("sfondo/medaglieChiuse.png");
            background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
            stage.addActor(background);
            medaglieActor.add(background);

            // Attore invisibile per il listener grande
            Image clickArea = new Image();
            clickArea.setSize(381*2, 270*2); // Cambia le dimensioni secondo le necessità
            clickArea.setPosition(63*2, 52*2); // Cambia la posizione secondo le necessità
            stage.addActor(clickArea);
            medaglieActor.add(clickArea);

            // Aggiungi il listener al clickArea
            clickArea.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    cambiaBG();
                }
            });

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
                    chiamanteM.closePokedex();
                }
            });
        } catch (Exception e) {
            System.out.println("Errore show medaglie, " + e);
        }
        

    }

    public void render(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    private void cambiaBG() {

        try {
            // Carico la texture corretta e aggiorno lo stato
            Texture newTexture;
            if (isMedaglieChiuso) {
                newTexture = new Texture("sfondo/medaglieAperte.png");
            } else {
                newTexture = new Texture("sfondo/medaglieChiuse.png");
            }
            isMedaglieChiuso = !isMedaglieChiuso;

            background.setDrawable(new Image(newTexture).getDrawable());

        } catch (Exception e) {
            System.out.println("Errore cambiaBG medaglie, " + e);
        }

        
    }

    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
    }

    private void pulsisciInventario() {
        try {
            for (Actor actor : medaglieActor) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }
            medaglieActor.clear();
        } catch (Exception e) {
            System.out.println("Errore pulisci inventario medaglie, " + e);
        }
    }
}
