package com.mercurio.game.strumenti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mercurio.game.menu.Borsa;

public class MiniMappa {
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    private Array<Actor> medaglieActor;
    private Borsa chiamanteB;
    private Image background;
    private Image map;
    private Image tastoXImage;

    private Array<Texture> cursoreTextures;
    private int currentCursorIndex = 0;
    private float elapsedTime = 0f; // Tempo accumulato
    private Image cursore;
    private float cambioInterval = 1f; // Intervallo di cambio in secondi

    public MiniMappa(Stage stage, Borsa chiamanteB) {

        this.stage = stage;
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.chiamanteB = chiamanteB;
        this.medaglieActor = new Array<>();

        Gdx.input.setInputProcessor(stage);
        show();
    }

    private void show() {
        try {

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Stato iniziale
            Texture mapTexture = new Texture("sfondo/miniMappaCompleta.png");
            map = new Image(mapTexture);
            map.setPosition((screenWidth - 700) / 2, (screenHeight - 700) / 2);
            map.setSize(700, 700);
            stage.addActor(map);
            medaglieActor.add(map);

            // sta sopra la minimappa anche se è un bg
            Texture backgroundTexture = new Texture("sfondo/miniMapBG.png");
            background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
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

            // Cursori multipli
            cursoreTextures = new Array<>();
            cursoreTextures.add(new Texture(Gdx.files.internal("cursore/cursoreMiniMap1.png")));
            cursoreTextures.add(new Texture(Gdx.files.internal("cursore/cursoreMiniMap2.png")));

            // Inizializza il cursore
            cursore = new Image(cursoreTextures.get(currentCursorIndex));
            cursore.setSize(30, 30);
            medaglieActor.add(cursore);
            stage.addActor(cursore);
        } catch (Exception e) {
            System.out.println("Errore show minimappa, " + e);
        }

    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        elapsedTime += deltaTime;

        try {

            // Ottieni le coordinate del mouse
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Y invertita

            // Controlla se il mouse è sopra la minimappa
            if (isMouseOverMap(mouseX, mouseY)) {
                // Nascondi il cursore del sistema
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

                // Mostra il cursore personalizzato
                cursore.setVisible(true);

                // Cambia immagine del cursore ogni `cambioInterval` secondi
                if (elapsedTime >= cambioInterval) {
                    elapsedTime = 0f; // Resetta il timer
                    currentCursorIndex = (currentCursorIndex + 1) % cursoreTextures.size;
                    cursore.setDrawable(
                            new TextureRegionDrawable(new TextureRegion(cursoreTextures.get(currentCursorIndex))));
                }

                // Aggiorna la posizione del cursore
                cursore.setPosition(mouseX - cursore.getWidth() / 2, mouseY - cursore.getHeight() / 2);
            } else {
                // Ripristina il cursore del sistema
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

                // Nascondi il cursore personalizzato
                cursore.setVisible(false);
            }

            // Aggiorna e disegna lo stage
            stage.act(deltaTime);
            stage.draw();
        } catch (Exception e) {
            System.out.println("Errore render miniMappa, " + e);
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
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        } catch (Exception e) {
            System.out.println("Errore pulisciInventario minimappa, " + e);
        }

    }

    private boolean isMouseOverMap(float mouseX, float mouseY) {
        return mouseX >= map.getX() &&
                mouseX <= map.getX() + map.getWidth() &&
                mouseY >= map.getY() &&
                mouseY <= map.getY() + map.getHeight();
    }
}
