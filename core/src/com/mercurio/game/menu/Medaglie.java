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
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetBMMLP;
import com.mercurio.game.AssetManager.GameAsset.AssetMedaglie;
import com.mercurio.game.Screen.MercurioMain;


public class Medaglie {
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    private Array<Actor> medaglieActor;
    private MenuLabel chiamanteM;
    private Image background;
    private boolean isMedaglieChiuso;
    private Image tastoXImage;

    private GameAsset asset;

    public Medaglie(Stage stage, MenuLabel chiamanteM, MercurioMain game){

        this.stage = stage;
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.chiamanteM = chiamanteM;
        this.medaglieActor = new Array<>();
        this.asset = game.getGameAsset();

        asset.loadMedaglieAsset();
        asset.loadBMMLPAsset();
        asset.finishLoading();

        show();

    }

    private void show(){

        try {
            
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Stato iniziale
            isMedaglieChiuso = true;
            Texture backgroundTexture = asset.getMedaglie(AssetMedaglie.SF_MEDAGLIE_CL);
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

            Texture closeButtonTexture = asset.getBMMLP(AssetBMMLP.SF_X);
            NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
            NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);
        
            Texture tastoX =  asset.getBMMLP(AssetBMMLP.SF_X);
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
                newTexture =  asset.getMedaglie(AssetMedaglie.SF_MEDAGLIE_OP);
            } else {
                newTexture =  asset.getMedaglie(AssetMedaglie.SF_MEDAGLIE_CL);
            }
            isMedaglieChiuso = !isMedaglieChiuso;

            background.setDrawable(new Image(newTexture).getDrawable());

        } catch (Exception e) {
            System.out.println("Errore cambiaBG medaglie, " + e);
        }

        
    }

    public void dispose() {
        asset.unloadAllMedaglie();
        asset.unloadAllBMMLP();
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
