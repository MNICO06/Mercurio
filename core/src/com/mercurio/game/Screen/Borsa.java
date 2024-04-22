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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Borsa {

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
        cure = new TextureRegion[3];
        ball = new TextureRegion[3];
        key = new TextureRegion[3];
        mt = new TextureRegion[3];
        textureCure = new Texture(Gdx.files.internal("sfondo/cureBag.png"));
        textureBall = new Texture(Gdx.files.internal("player/ballBag.png"));
        textureKey = new Texture(Gdx.files.internal("player/keyBag.png"));
        textureMT = new Texture(Gdx.files.internal("player/mtBag.png"));
        
        int regionWidthCure = textureCure.getWidth();
        int regionHeightCure = textureCure.getHeight() / 3;
        int regionWidthBall = textureBall.getWidth();
        int regionHeightBall = textureBall.getHeight() / 3;
        int regionWidthKey = textureKey.getWidth();
        int regionHeightKey = textureKey.getHeight() / 3;
        int regionWidthMT = textureMT.getWidth();
        int regionHeightMT = textureMT.getHeight() / 3;

        for (int i = 0; i < 3; i++) {
            cure[i] = new TextureRegion(textureCure, i * regionWidthCure, 0, regionWidthCure, regionHeightCure);
            ball[i] = new TextureRegion(textureBall, i * regionWidthBall, 0, regionWidthBall, regionHeightBall);
            key[i] = new TextureRegion(textureKey, i * regionWidthKey, 0, regionWidthKey, regionHeightKey);
            mt[i] = new TextureRegion(textureMT, i * regionWidthMT, 0, regionWidthMT, regionHeightMT);
        }
        
        cambiaCure = new Animation<>(cambioFrame_speed, cure);
        cambiaBall = new Animation<>(cambioFrame_speed, ball);
        cambiaKey = new Animation<>(cambioFrame_speed, key);
        cambiaMT = new Animation<>(cambioFrame_speed, mt);
        
        nsCure = new Animation<>(cambioFrame_speed, cure[0]);
        nsBall = new Animation<>(cambioFrame_speed, ball[0]);
        nsKey = new Animation<>(cambioFrame_speed, key[0]);
        nsMT = new Animation<>(cambioFrame_speed, mt[0]);
        
        Image labelCure = createImageWithTexture(textureCure);
        labelCure.setPosition(50, screenHeight - 100);
        stage.addActor(labelCure);
        borsaActors.add(labelCure); // Aggiungi l'etichetta Cure agli attori della borsa

        Image labelBall = createImageWithTexture(textureBall);
        labelBall.setPosition(150, screenHeight - 100);
        stage.addActor(labelBall);
        borsaActors.add(labelBall); // Aggiungi l'etichetta Ball agli attori della borsa

        Image labelKey = createImageWithTexture(textureKey);
        labelKey.setPosition(250, screenHeight - 100);
        stage.addActor(labelKey);
        borsaActors.add(labelKey); // Aggiungi l'etichetta Key agli attori della borsa

        Image labelMT = createImageWithTexture(textureMT);
        labelMT.setPosition(350, screenHeight - 100);
        stage.addActor(labelMT);
        borsaActors.add(labelMT); // Aggiungi l'etichetta MT agli attori della borsa


        
     // Gestisci il comportamento quando le etichette vengono premute
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
        
        // Sfondo della borsa
        Texture backgroundTexture = new Texture("sfondo/sfondo.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
        borsaActors.add(background); // Aggiungi il background agli attori della borsa

        // Etichetta della borsa
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;


        // Pulsante per chiudere la borsa
        Texture closeButtonTexture = new Texture("sfondo/x.png");
        NinePatch closeButtonPatch = new NinePatch(closeButtonTexture, 10, 10, 10, 10);
        NinePatchDrawable closeButtonDrawable = new NinePatchDrawable(closeButtonPatch);

        Label closeLabel = new Label("", labelStyle);
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
        borsaActors.add(closeLabel); // Aggiungi il pulsante di chiusura agli attori della borsa
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
    
 // Metodo per creare un'immagine con una texture specifica
    private Image createImageWithTexture(Texture texture) {
        Image image = new Image(texture);
        return image;
    }

    // Metodo per attivare l'animazione su un'immagine specifica e disattivare le altre
    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        image.setDrawable(new NinePatchDrawable());
        image.setDrawable((Drawable) animation.getKeyFrame(0, true));
    }

    // Metodo per disattivare le animazioni su una serie di immagini specifiche
    private void deactivateAnimations(Image... images) {
        for (Image image : images) {
            image.setDrawable(new NinePatchDrawable());
        }
}
}
