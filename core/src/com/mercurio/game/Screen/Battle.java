package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

public class Battle extends ScreenAdapter {

    private Texture ballTexture = new Texture("battle/pokeBallPlayer.png"); //questo lo si prende dal json
    private boolean isInNext=true; 
    private int lanciato = 0;
    private Image labelBaseU;
    private Image labelBaseD;
    private Texture textureLancio;
    private TextureRegion[] player;
    private TextureRegion[] ball;
    private TextureRegion ball2;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private float stateTime;
    private TextureRegion frame;
    private Image imagePlayer;
    private Image imageBall;
    private Image imageBall2;
    private Animation<TextureRegion> muoviPlayer;
    private Animation<TextureRegion> muoviBall;
    private float cambioFrame_speed = 0.7f;
    private float animationTime;
    private float animationDuration = 3f; // Durata dell'animazione in secondi
    private float initialPosXD;
    private float targetPosXD;
    private float initialPosXU;
    private float targetPosXU;


    public Battle() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        show();
    }

    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        targetPosXD = -200;
        initialPosXD = screenWidth; 

        targetPosXU = screenWidth-128*3+15;
        initialPosXU = 0;

        animationTime = 0f;

        

        int ballWidth = ballTexture.getWidth() / 3;
        int ballHeight = ballTexture.getHeight();
    
        ball2 =  new TextureRegion(ballTexture, 0, 0, ballWidth, ballHeight);
        

        // Add background 
        Texture backgroundTexture = new Texture("battle/sfondoBattle.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
        
        textureLancio = new Texture(Gdx.files.internal("battle/lancioBall.png"));
        int regionHeight = textureLancio.getHeight();
        int regionWidth = textureLancio.getWidth()/4;
        // Divide lo spritesheet in 4colonne
        player = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            player[i] = new TextureRegion(textureLancio, i*regionWidth, 0, regionWidth, regionHeight);
            }

        muoviPlayer = new Animation<>(cambioFrame_speed, player);


        Texture imageBaseD = new Texture("battle/baseD.png");
        labelBaseD = new Image(imageBaseD);
        labelBaseD.setSize(256*3, 32*3);
        stage.addActor(labelBaseD);

        Texture imageBaseU = new Texture("battle/baseU.png");
        labelBaseU = new Image(imageBaseU);
        labelBaseU.setSize(128*3, 62*3);
        stage.addActor(labelBaseU);

        imageBall2 = new Image(ball2);
        imageBall2.setSize(16*4, 25*4);
        imageBall2.setPosition(-300, -300); //fuori dallo schermo che non si deve vedere
        stage.addActor(imageBall2);

        imagePlayer = new Image(player[0]);
        imagePlayer.setSize(66*4, 52*4);
        stage.addActor(imagePlayer);

        

        
            
        }


    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    public void render() {
        animationTime += 0.045f;
        if (animationTime < animationDuration) {
            // Continua l'animazione della baseD e baseU
            float progress = animationTime / animationDuration;
            float newXD = MathUtils.lerp(initialPosXD, targetPosXD, progress);
            labelBaseD.setPosition(newXD, 125);
            float newXU = MathUtils.lerp(initialPosXU, targetPosXU, progress);
            labelBaseU.setPosition(newXU, 300);
            imagePlayer.setPosition(newXD + 370, 125);
        } else {
            // Avvia l'animazione del player
            if (animationTime < animationDuration + 1f) {
                // Aspetta un secondo prima di cambiare l'immagine del player
                if (animationTime > animationDuration) {
                    // Cambia l'immagine del player a player[1]
                    imagePlayer.setDrawable(new TextureRegionDrawable(player[1]));
                    imageBall2.setPosition(190, 195);
                }
            } else {
                
                // Dopo un secondo, sposta il player fino al bordo dello schermo
                float newX = imagePlayer.getX() - 300 * Gdx.graphics.getDeltaTime(); // Spostamento di 300 pixel al secondo

                if (isInNext){
                    imageBall2.setPosition(newX+10, 195);
                }
                else{
                    imageBall2.setPosition(newX+10, 220);
                }

                if (newX + imagePlayer.getWidth() < 120) {
                    // Una volta che il player è fuori dallo schermo, cambia l'animazione a player[2] e player[3]
                    imagePlayer.setDrawable(new TextureRegionDrawable(player[3]));
                    lanciato++;  
                   isInNext=true;
                    // Rimuovi il player dallo stage dopo l'ultima animazione
                }
                else if (newX + imagePlayer.getWidth() > 120 && newX + imagePlayer.getWidth() < 270) {
                    imagePlayer.setDrawable(new TextureRegionDrawable(player[2]));
                    isInNext=false;
                    if (muoviPlayer.isAnimationFinished(3f)) {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                imagePlayer.remove(); // Rimuovi lo sprite dallo stage
                            }
                        }, 2f);
                    }
                }
                
                

                if(imagePlayer!=null)
                    imagePlayer.setPosition(newX, imagePlayer.getY());
                
            }
            if (lanciato==1){
                showBall(ballTexture);
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        // Disegna la UI della borsa
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }
    
    
    private void showBall(Texture textureBall) {
        int regionWidth = textureBall.getWidth() / 3;
        int regionHeight = textureBall.getHeight();
    
        // Inizializza l'array delle TextureRegion della ball
        ball = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            ball[i] = new TextureRegion(textureBall, i * regionWidth, 0, regionWidth, regionHeight);
        }
    
        // Crea e aggiungi l'immagine della ball allo stage
        imageBall = new Image(ball[0]);
        imageBall.setSize(16*4, 25*4);

        muoviBall = new Animation<>(0.5f, ball);
    
        // Posizione iniziale della ball (NON CAMBIATELE)
        float startX = 1;
        float startY = 220;

        imageBall.setPosition(0, startY);
        stage.addActor(imageBall);
        // Durata del movimento della ball (secondi)
        float duration = 2.5f;
    
        // Animazione di spostamento lungo una traiettoria curva
        Timer.schedule(new Timer.Task() {
            float elapsed = 0;
    
            @Override
            public void run() {
                if (elapsed <= duration) {
                    // Calcola la posizione sulla traiettoria curva
                    float percent = elapsed / duration;;
                    imageBall.setPosition((startX * percent * 175)+35, startY - (startY - 110)* percent * percent); // Utilizza una curva quadratica
                    elapsed += 0.1f;
                } else {
                    // Avvia l'animazione dei frame della ball
                    activateAnimation(imageBall, muoviBall);
                    this.cancel(); // Interrompi il Timer.Task
                }
            }
        }, 0, Gdx.graphics.getDeltaTime());
    }
    

    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stateTime = 2f;
                frame = animation.getKeyFrame(stateTime, false);
                imageBall.setDrawable(new TextureRegionDrawable(frame));

                if (animation.isAnimationFinished(stateTime)) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            imageBall.remove();
                            
                        }
                    }, 0.5f);
                    this.cancel();
                }
            }
        }, 0.3f);
    }
    
}
