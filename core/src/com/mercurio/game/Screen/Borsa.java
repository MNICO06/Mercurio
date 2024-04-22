package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Borsa {

    private TextureRegion frame;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Array<Actor> borsaActors; // Array per tracciare gli attori della borsa
    private TextureRegion[] cure;
    private TextureRegion[] ball;
    private TextureRegion[] key; 
    private TextureRegion[] mt;
    
    private Texture textureCure;
    private Texture textureBall;
    private Texture textureKey;
    private Texture textureMT;
    
    private float cambioFrame_speed = 0.14f;
    
    private Animation<TextureRegion> cambiaCure;
    private Animation<TextureRegion> cambiaBall;
    private Animation<TextureRegion> cambiaKey;
    private Animation<TextureRegion> cambiaMT;
    private Animation<TextureRegion> nsCure; /* ns sta per not selected*/
    private Animation<TextureRegion> nsBall;
    private Animation<TextureRegion> nsKey;
    private Animation<TextureRegion> nsMT;

    private Image labelBall;
    private Image labelCure;
    private Image labelMT;
    private Image labelKey;

    private float stateTime;
    
    public Borsa(Stage stage) {
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        this.stage = stage;
        this.borsaActors = new Array<>(); // Inizializza l'array degli attori della borsa

        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    private void createUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Add background and close button
        Texture backgroundTexture = new Texture("sfondo/sfondo.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
    
        // Load textures and create TextureRegions
        textureCure = new Texture(Gdx.files.internal("sfondo/cureBag.png"));
        textureBall = new Texture(Gdx.files.internal("sfondo/ballBag.png"));
        textureKey = new Texture(Gdx.files.internal("sfondo/keyBag.png"));
        textureMT = new Texture(Gdx.files.internal("sfondo/mtBag.png"));
    
        cure = new TextureRegion[3];
        ball = new TextureRegion[3];
        key = new TextureRegion[3];
        mt = new TextureRegion[3];
    
        int regionHeight = textureCure.getHeight() / 3; // Assuming each texture has 3 rows
    
        for (int i = 0; i < 3; i++) {
            cure[i] = new TextureRegion(textureCure, 0, i * regionHeight, textureCure.getWidth(), regionHeight);
            ball[i] = new TextureRegion(textureBall, 0, i * regionHeight, textureBall.getWidth(), regionHeight);
            key[i] = new TextureRegion(textureKey, 0, i * regionHeight, textureKey.getWidth(), regionHeight);
            mt[i] = new TextureRegion(textureMT, 0, i * regionHeight, textureMT.getWidth(), regionHeight);
        }
        
        cambiaCure = new Animation<>(cambioFrame_speed, cure);
        cambiaBall = new Animation<>(cambioFrame_speed, ball);
        cambiaKey = new Animation<>(cambioFrame_speed, key);
        cambiaMT = new Animation<>(cambioFrame_speed, mt);

        nsCure = new Animation<>(cambioFrame_speed, cure[0]);
        nsBall = new Animation<>(cambioFrame_speed, ball[0]);
        nsKey = new Animation<>(cambioFrame_speed, key[0]);
        nsMT = new Animation<>(cambioFrame_speed, mt[0]);
    
        // Create Image actors using TextureRegions
         labelCure = new Image(cure[0]);
        labelCure.setSize(100, 100);
        labelCure.setPosition(70, screenHeight - 150);
        stage.addActor(labelCure);
    
         labelBall = new Image(ball[0]);
        labelBall.setSize(100, 100);
        labelBall.setPosition(200, screenHeight - 150);
        stage.addActor(labelBall);
    
         labelKey = new Image(key[0]);
        labelKey.setSize(100, 100);
        labelKey.setPosition(330, screenHeight - 150);
        stage.addActor(labelKey);
    
         labelMT = new Image(mt[0]);
        labelMT.setSize(100, 100);
        labelMT.setPosition(460, screenHeight - 150);
        stage.addActor(labelMT);
    
        // Handle click events on labels
        labelCure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelCure, cambiaCure);
                deactivateAnimations(labelBall, labelKey, labelMT);
            }
        });
    
        labelBall.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelBall, cambiaBall);
                deactivateAnimations(labelCure, labelKey, labelMT);
            }
        });
    
        labelKey.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelKey, cambiaKey);
                deactivateAnimations(labelCure, labelBall, labelMT);
            }
        });
    
        labelMT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateAnimation(labelMT, cambiaMT);
                deactivateAnimations(labelCure, labelBall, labelKey);
            }
        });
    
        
    
        Texture closeButtonTexture = new Texture("sfondo/x.png");
        NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
        NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);
    
        Label closeLabel = new Label("", new Label.LabelStyle(font, null));
        closeLabel.setWidth(75);
        closeLabel.setHeight(75);
        closeLabel.setPosition(screenWidth - 100, screenHeight - 100);
        closeLabel.getStyle().background = closeButtonDrawable;
        closeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        stage.addActor(closeLabel);
    
        // Add actors to the borsaActors array
        borsaActors.addAll(labelCure, labelBall, labelKey, labelMT, background, closeLabel);

    }
    

    private void close() {
        // Rimuovi gli attori della borsa in modo ritardato per simulare una chiusura graduale
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Actor actor : borsaActors) {
                    actor.remove();
                }
                borsaActors.clear(); // Pulisci l'array degli attori della borsa dopo la rimozione
            }
        }, 0.1f); // Ritardo di 0.1 secondi prima di rimuovere gli attori
    }
    
    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        stateTime = 1f;
        frame = animation.getKeyFrame(stateTime, true);
        image.setDrawable(new TextureRegionDrawable(frame));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stateTime = 2f;
                frame = animation.getKeyFrame(stateTime, true);
                image.setDrawable(new TextureRegionDrawable(frame));
            }
        }, 0.02f);
    }
    


    private void deactivateAnimations(Image... images) {
        for (Image image : images) {
            // Ottieni l'animazione associata all'immagine
            Animation<TextureRegion> animation = null;
            if (image == labelCure) {
                animation = nsCure;
            } else if (image == labelBall) {
                animation = nsBall;
            } else if (image == labelKey) {
                animation = nsKey;
            } else if (image == labelMT) {
                animation = nsMT;
            }
    
            // Se l'animazione esiste, reimposta l'immagine al frame 0 dell'animazione
            if (animation != null) {
                TextureRegion frame = animation.getKeyFrame(0); // Ottieni il frame 0 dell'animazione
                image.setDrawable(new TextureRegionDrawable(frame));
            }
        }
    }
}
