package com.mercurio.game.pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.bullet.collision.btBvhTree;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.ai.btree.utils.DistributionAdapters.IntegerAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.Screen.Erba;
import com.mercurio.game.Screen.InterfacciaComune;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.menu.Borsa;
import com.mercurio.game.menu.BorsaModifier;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.menu.Squadra;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

public class Battle extends ScreenAdapter {

    private int checkPerDoppioPoke = 0;
    private int checkPerDoppiaBarra = 0;
    private Image nextMove;
    private boolean globalOtherAttack;
    private Image backImage;
    private boolean nextMoveBot;
    private int counterForNextMove=0;
    private BorsaModifier borsaModifier = new BorsaModifier();
    private String nameUsedBall;
    private int danno;
    private int dannoBot;
    private final Object lock = new Object(); // Dichiarazione di un oggetto di blocco
    private boolean isBattleEnded=false;
    private Label labelNomePokemonBot;
    private Label labelLVBot;
    private float delaySecondText=0;
    private int checkInt;
    private Image pokemonImage;
    private Image pokemonImageBot;
    private Borsa borsa;
    private Squadra squadra;
    private Sprite[] spriteArrayBallsSquadra;
    private boolean labelPokePiazzate = false;
    private ArrayList<Image> labelMosseArray = new ArrayList<>();
    private ArrayList<Label> labelNomeMosseArray = new ArrayList<>();
    private ArrayList<Label> labelNomeHPBars = new ArrayList<>();
    private int currentIndex = 0; // Variabile per tenere traccia dell'indice corrente
    private int index;
    private TextureRegion[] fightLabels;
    private boolean isBotFight;
    private Image botImage;
    private Image playerArrow;
    private Image botArrow;
    private Texture ballTexture ;
    private Texture ballTextureBot = new Texture("battle/pokeBallPlayer.png"); //questo lo si prende dal json
    private boolean isInNext=true; 
    private int lanciato = 0;
    private Image labelBaseU;
    private Image labelBaseD;
    private Texture textureLancio;
    private TextureRegion[] player;
    private TextureRegion[] ball;
    private TextureRegion[] ballLanciata;
    private TextureRegion[] ballBot;
    private TextureRegion ball2;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private float stateTime;
    private TextureRegion frame;
    private Image imagePlayer;
    private Image imageBall;
    private Image imageBallLanciata;
    private Image imageBallBot;
    private Image imageBall2;
    private Animation<TextureRegion> muoviPlayer;
    private Animation<TextureRegion> muoviBall;
    private Animation<TextureRegion> muoviBallLanciata;
    private Animation<TextureRegion> muoviBallBot;
    private TextureRegion newTextureRegionFight;
    private float cambioFrame_speed = 0.7f;
    private float animationTime;
    private float animationDuration = 3f; // Durata dell'animazione in secondi
    private float initialPosXD;
    private float targetPosXD;
    private float initialPosXU;
    private float targetPosXU;
    private LabelDiscorsi labelDiscorsi1;
    private LabelDiscorsi labelDiscorsi2;
    private LabelDiscorsi labelDiscorsi3;
    private LabelDiscorsi labelDiscorsi4;
    private LabelDiscorsi labelDiscorsi5;
    private LabelDiscorsi labelDiscorsi6;
    private LabelDiscorsi labelDiscorsi7;
    private LabelDiscorsi labelDiscorsi8;
    private LabelDiscorsi labelDiscorsi9;
    private LabelDiscorsi labelDiscorsi10;
    private LabelDiscorsi labelDiscorsi11;
    private LabelDiscorsi labelDiscorsi12;
    private LabelDiscorsi labelDiscorsi13;
    private LabelDiscorsi labelDiscorsi14;
    private LabelDiscorsi labelDiscorsi15;
    private LabelDiscorsi labelDiscorsi16;
    private LabelDiscorsi labelDiscorsi17;
    private LabelDiscorsi labelDiscorsi18;
    private LabelDiscorsi labelDiscorsi19;
    private LabelDiscorsi labelDiscorsi20;
    private LabelDiscorsi labelDiscorsi21;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    private Label label6;
    private Label label7;
    private Label label8;
    private Label label9;
    private Label label10;
    private Label label11;
    private Label label12;
    private Label label13;
    private Label label14;
    private Label label15;
    private Label label16;
    private Label label17;
    private Label label18;
    private Label label19;
    private Label label20;
    private Label label21;
    private String nomePoke;
    private String nomePokeSquad;
    private String currentPokeHP;
    private String currentPokeHPforSquad;
    private String maxPokeHP;
    private String maxPokeHPBot;
    private String nomePokeSquadBot;
    private String currentPokeHPBot;
    private String currentPokeHPBotSquad;
    private String nomeMossa;
    private String nomeMossaBot;
    private int soldiPresi;
    private int dimMax;
    private String nomeBot;
    private ArrayList<Mossa> listaMosse = new ArrayList<>();
    private ArrayList<Mossa> listaMosseBot = new ArrayList<>();
    private String nomeBall;
    private String nomePokeBot;
    private String tipoBot;
    private Image playerHPBar;
    private Image botHPBar;
    private String LVPokeBot;
    private String LVPoke;
    private ArrayList<Integer> statsPlayer = new ArrayList<>();
    private ArrayList<Integer> statsBot = new ArrayList<>();
    private Image HPplayer;
    private Image HPbot;
    private boolean checkLabel5 = true;
    private boolean checkLabel6 = true;
    private InterfacciaComune chiamante;
    private int numeroIndexPokeBot;
    private int numeroIndexPoke=1;
    private String nameBot;
    private Battle battle=this;
    private Label labelNomePokemon;
    private Label labelLV;
    private Label labelHP;
    private Label labelHPTot;
    private boolean switched=false;
    private boolean switchForzato=false;
    private boolean sconfitta=false;
    private String zona;
    private int denaroPerso;
    private int levelMax=0;
    private String levelLastPokeBot;
    private Semaphore semaphore;
    private String pokeHPbeforeFight;
    private float tassoCattura;
    private String nomeSelvatico;
    private boolean cattura=false;
    private float x=0;

    public Battle(InterfacciaComune chiamante, String nameBot, boolean isBotFight, String zona, String nomeSelvatico) {
        MenuLabel.openMenuLabel.setVisible(false);
        semaphore = new Semaphore(1); // Semaforo binario
        this.nameBot=nameBot;
        this.nomeSelvatico=nomeSelvatico;
        this.isBotFight=isBotFight;
        this.zona=zona;
        checkInt=0;
        numeroIndexPokeBot=1;
        this.chiamante = chiamante;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);
        dimMax=200;
        leggiPoke(numeroIndexPoke);
        if (isBotFight){
            leggiBot(nameBot);
            leggiPokeBot(nameBot,numeroIndexPokeBot);
        }
        ballTexture = new Texture("battle/"+nomeBall+"Player.png");

        String discorso1= "Parte la sfida di "+nomeBot+" ("+tipoBot+")"+"!";
        labelDiscorsi1 = new LabelDiscorsi(discorso1,dimMax,0,true);
        
        String discorso2= "Vai "+ nomePoke + "!";
        labelDiscorsi2 = new LabelDiscorsi(discorso2,dimMax,0,true);

        String discorso3= "Hai sconfitto "+ nomeBot+" ("+tipoBot+")"+"!";
        labelDiscorsi3 = new LabelDiscorsi(discorso3,dimMax,0,true);

        String discorso5= "E' superefficace!";
        labelDiscorsi5 = new LabelDiscorsi(discorso5,dimMax,0,true);

        String discorso6= "Non e' molto efficace...";
        labelDiscorsi6 = new LabelDiscorsi(discorso6,dimMax,0,true);

        String discorso7= "Non ha effetto!";
        labelDiscorsi7 = new LabelDiscorsi(discorso7,dimMax,0,true);

        String discorso9= "Brutto colpo!";
        labelDiscorsi9 = new LabelDiscorsi(discorso9,dimMax,0,true);

        String discorso10= "Non puoi sottrarti alla lotta!";
        labelDiscorsi10 = new LabelDiscorsi(discorso10,dimMax,0,true);

        String discorso11= "Scampato pericolo!";
        labelDiscorsi11 = new LabelDiscorsi(discorso11,dimMax,0,true);

        String discorso15= "E' apparso un "+ nameBot +" selvatico!";
        labelDiscorsi15 = new LabelDiscorsi(discorso15,dimMax,0,true);

        String discorso16= "Non hai piu' Pokemon disponibili...";
        labelDiscorsi16 = new LabelDiscorsi(discorso16,dimMax,0,true);

        String discorso18= "Sei stato portato d'urgenza al Centro Pokémon!";
        labelDiscorsi18 = new LabelDiscorsi(discorso18,dimMax,0,true);
        
        
        show();
    }

    @Override
    public void show() {
        checkInt=0;
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


        if (isBotFight){

            Texture botTexture = new Texture("bots/"+ tipoBot + ".png");
            botImage = new Image(botTexture);
            botImage.setPosition(labelBaseU.getX()+68, labelBaseU.getY());
            botImage.setSize(320, 320);
            stage.addActor(botImage);
            
            labelDiscorsi1.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
            label1=labelDiscorsi1.getLabel();
            stage.addActor(label1);
        }
        else{
            leggiPokeSelvatico(nameBot,zona);

            Texture pokemonTexture = new Texture("pokemon/"+nameBot+".png");
            int frameWidth = pokemonTexture.getWidth() / 4;
            int frameHeight = pokemonTexture.getHeight();
        
            TextureRegion[] pokeSelvFrames;

            pokeSelvFrames = new TextureRegion[2];
            for (int i = 0; i < 2; i++) {
                pokeSelvFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
            }
    
            botImage = new Image(pokeSelvFrames[0]);
            botImage.setSize(frameWidth * 3, frameHeight * 3);
            botImage.setPosition(labelBaseU.getX()+60, labelBaseU.getY()+20);
            stage.addActor(botImage);

            labelDiscorsi15.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
            label15=labelDiscorsi15.getLabel();
            stage.addActor(label15);
        }

        }


        @Override
        public void dispose() {
            if (!isBotFight && !isBattleEnded){
                label11=labelDiscorsi11.getLabel();
                    stage.addActor(label11);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            label11.remove();
                            batch.dispose();
                            font.dispose();
                            stage.dispose();
                            textureLancio.dispose();
                            ballTexture.dispose();
                            Gdx.input.setInputProcessor(null);
                            chiamante.closeBattle();
                            MenuLabel.openMenuLabel.setVisible(true);
                        }
                    }, 2f);
            }
            else if(isBattleEnded){
                batch.dispose();
                font.dispose();
                stage.dispose();
                textureLancio.dispose();
                ballTexture.dispose();
                Gdx.input.setInputProcessor(null);
                chiamante.closeBattle();
                MenuLabel.openMenuLabel.setVisible(true);
            }
            else {
                label10=labelDiscorsi10.getLabel();
                    stage.addActor(label10);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi10.reset();
                            label10.remove();
                            label10=null;
                        }
                    }, 2f);
            }
    
        }

    public void render() {
        
        animationTime += 0.045f;
        if (animationTime < animationDuration) {
            labelDiscorsi1.renderDisc();
            // Continua l'animazione della baseD e baseU
            float progress = animationTime / animationDuration;
            float newXD = MathUtils.lerp(initialPosXD, targetPosXD, progress);
            labelBaseD.setPosition(newXD, 125);
            float newXU = MathUtils.lerp(initialPosXU, targetPosXU, progress);
            labelBaseU.setPosition(newXU, 300);
            if (isBotFight){
                botImage.setPosition(labelBaseU.getX()+40, labelBaseU.getY()+70);
            }
            else{
                botImage.setPosition(labelBaseU.getX()+60, labelBaseU.getY()+20);
            }
            
            imagePlayer.setPosition(newXD + 370, 125);

        } else {


            // Avvia l'animazione del player
            if (animationTime < animationDuration + 2f) {
                // Aspetta due secondi prima di cambiare l'immagine del player
                if (animationTime > animationDuration) {
                    // Cambia l'immagine del player a player[1]
                    imagePlayer.setDrawable(new TextureRegionDrawable(player[1]));
                    imageBall2.setPosition(190, 195);

                }
            } else {
                if(!labelPokePiazzate){
                    posizionaLabelSquadra();
                }

                
                // Dopo un secondo, sposta il player fino al bordo dello schermo
                float newX = imagePlayer.getX() - 300 * Gdx.graphics.getDeltaTime(); // Spostamento di 300 pixel al secondo
                float newXBot = botImage.getX() + 300 * Gdx.graphics.getDeltaTime();

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
                                if (isBotFight){
                                    botImage.remove();
                                }
                            }
                        }, 2f);
                    }
                }
                
                

                if(imagePlayer!=null)
                    imagePlayer.setPosition(newX, imagePlayer.getY());

                if(botImage!=null && isBotFight)
                    botImage.setPosition(newXBot, botImage.getY());    
                
            }
            try { //semaforo per evitare che piazzi tutto due volte (non si sa perchè lo faccia ma si sistema tutto con un semaforo) *non va tuttora*
                synchronized (lock) { 
                    semaphore.acquire();
                    if (lanciato==1){
                            showBall(ballTexture);
                            lanciato++;
                        if (isBotFight){
                            showBallBot();
                        }
                        else{
                            checkPerDoppioPoke++;
                            showPokemon(labelBaseU, nameBot);
                        }
                    }
                } // Fine del blocco sincronizzato
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            if (label2!=null){
                labelDiscorsi2.renderDisc();
            }
            if (label3!=null){
                labelDiscorsi3.renderDisc();
            }
            if (label4!=null){
                labelDiscorsi4.renderDisc();
            }
            if (label5!=null){
                labelDiscorsi5.renderDisc();
            }
            if (label6!=null){
                labelDiscorsi6.renderDisc();
            }
            if (label7!=null){
                labelDiscorsi7.renderDisc();
            }
            if (label8!=null){
                labelDiscorsi8.renderDisc();
            }
            if (label9!=null){
                labelDiscorsi9.renderDisc();
            }
            if (label10!=null){
                labelDiscorsi10.renderDisc();
            }
            if (label11!=null){
                labelDiscorsi11.renderDisc();
            }
            if (label12!=null){
                labelDiscorsi12.renderDisc();
            }
            if (label13!=null){
                labelDiscorsi13.renderDisc();
            }
            if (label14!=null){
                labelDiscorsi14.renderDisc();
            }
            if (label15!=null){
                labelDiscorsi15.renderDisc();
            }
            if (label16!=null){
                labelDiscorsi16.renderDisc();
            }
            if (label17!=null){
                labelDiscorsi17.renderDisc();
            }
            if (label18!=null){
                labelDiscorsi18.renderDisc();
            }
            if (label19!=null){
                labelDiscorsi19.renderDisc();
            }
            if (label20!=null){
                labelDiscorsi20.renderDisc();
            }
            if (label21!=null){
                labelDiscorsi21.renderDisc();
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
    
        muoviBall = new Animation<>(0.5f, ball);

        // Crea e aggiungi l'immagine della ball allo stage
        imageBall = new Image(ball[0]);
        imageBall.setSize(16*4, 25*4);
    
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
                    float percent = elapsed / duration;
                    imageBall.setPosition((startX * percent * 175)+35, startY - (startY - 110)* percent * percent); 
                    elapsed += 0.1f;
                } else {
                    // Avvia l'animazione dei frame della ball
                    activateAnimation(imageBall, muoviBall);
                    if (isBotFight){
                        label1.remove();
                    }
                    else{
                        label15.remove();
                    }
                    

                    label2=labelDiscorsi2.getLabel();
                    stage.addActor(label2);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            label2.remove();
                            if (!switched){
                                createFightLabels();
                            }
                        }
                    }, 2f);
                    this.cancel(); // Interrompi il Timer.Task
                    
                }
            }
        }, 0, Gdx.graphics.getDeltaTime());

    }
    


    private void showBallBot() {
        int regionWidth = ballTextureBot.getWidth() / 3;
        int regionHeight = ballTextureBot.getHeight();
        // Inizializza l'array delle TextureRegion della ball del bot
        ballBot = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            ballBot[i] = new TextureRegion(ballTextureBot, i * regionWidth, 0, regionWidth, regionHeight);
        }

        muoviBallBot = new Animation<>(0.5f, ballBot);
    
        // Crea e aggiungi l'immagine della ball del bot allo stage
        imageBallBot = new Image(ballBot[0]);
        imageBallBot.setSize(16*4, 25*4);
    
        // Posizione iniziale della ball del bot (NON CAMBIATELE)
        float startXBot = stage.getHeight();
        float startYBot = 800;
        
        imageBallBot.setPosition(01000, 1000);
    
        stage.addActor(imageBallBot);
    
        // Durata del movimento della ball del bot (secondi)
        float duration = 2.5f;
    
        // Animazione di spostamento lungo una traiettoria curva
        Timer.schedule(new Timer.Task() {
            float elapsed = 0;
    
            @Override
            public void run() {
                if (elapsed <= duration) {
                    // Calcola la posizione sulla traiettoria curva
                    float percent = elapsed / duration;
                    imageBallBot.setPosition(startXBot - (startXBot * percent) + 800, startYBot - (startYBot - 330) * percent * percent * percent); // Utilizza una curva quadratica
                    elapsed += 0.1f;
                } else {
                    // Avvia l'animazione dei frame della ball del bot
                    activateAnimation(imageBallBot, muoviBallBot);
                    this.cancel(); // Interrompi il Timer.Task
                }
            }
        }, 0, Gdx.graphics.getDeltaTime());
    }

    

    private void createFightLabels() {
        fightLabels = new TextureRegion[8]; 
    
        // Carica l'immagine contenente tutte le label
        Texture textBoxesImage = new Texture("battle/fightBox.png");
    
        // Calcola le dimensioni di ogni label nella griglia
        int textBoxWidth = textBoxesImage.getWidth() / 2; // Due colonne
        int textBoxHeight = textBoxesImage.getHeight() / 4; // Dieci righe
    
        // Estrai ogni label dall'immagine e salvala nell'array di texture
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                int index = row * 2 + col; // Calcola l'indice dell'array
    
                int startX = col * textBoxWidth;
                int startY = row * textBoxHeight;  
                fightLabels[index] = new TextureRegion(textBoxesImage, startX, startY, textBoxWidth, textBoxHeight);
            }
        }
    

        Image label1 = new Image(fightLabels[0]);
        label1.setSize(256, 125);
        label1.setPosition(0, 0); // Posizione delle etichette sulla schermata
        stage.addActor(label1);

        label1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex = 0; // Imposta l'indice corrente sulla base di quale etichetta è stata cliccata
                updateLabelImage(label1); // Aggiorna l'immagine della label cliccata
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        piazzaMosse();
                    }
                }, 0.3f);
            }
        });


        Image label2 = new Image(fightLabels[2]);
        label2.setSize(256, 125);
        label2.setPosition(256, 0); // Posizione delle etichette sulla schermata
        stage.addActor(label2);

        label2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex = 2; // Imposta l'indice corrente sulla base di quale etichetta è stata cliccata
                updateLabelImage(label2); // Aggiorna l'immagine della label cliccata
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        squadra = new Squadra(stage,true, battle, null, false);
                        switchForzato=false;
                    }
                }, 0.3f);
            }
        });


        Image label3 = new Image(fightLabels[4]);
        label3.setSize(256, 125);
        label3.setPosition(256*2, 0); // Posizione delle etichette sulla schermata
        stage.addActor(label3);

        label3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex = 4; // Imposta l'indice corrente sulla base di quale etichetta è stata cliccata
                updateLabelImage(label3); // Aggiorna l'immagine della label cliccata
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        borsa = new Borsa(stage,true, battle);
                    }
                }, 0.3f);
                
            }
        });


        Image label4 = new Image(fightLabels[6]);
        label4.setSize(256, 125);
        label4.setPosition(3*256, 0); // Posizione delle etichette sulla schermata
        stage.addActor(label4);

        label4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex = 6; // Imposta l'indice corrente sulla base di quale etichetta è stata cliccata
                updateLabelImage(label4); // Aggiorna l'immagine della label cliccata
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Erba.estratto=0;
                        dispose();
                    }
                }, 0.3f);
            }
        });

        

        piazzaLabelLottaPlayer();
        piazzaLabelLottaPerBot();
    }

    private void piazzaLabelLottaPlayer(){

        //e ora piazza le hp Bar
        Texture imageHPPlayer = new Texture("battle/playerHPBar.png");
        playerHPBar = new Image(imageHPPlayer);
        playerHPBar.setSize(256, 47*2);
        playerHPBar.setPosition(1024, 140);
        stage.addActor(playerHPBar);

        Action moveActionPlayer = Actions.moveTo(1024-256, 140, 0.5f);
        // Applica l'azione all'immagine
        playerHPBar.addAction(moveActionPlayer);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            HPplayer=placeHpBar(playerHPBar,144,38,currentPokeHP,maxPokeHP); 
            }
        }, 0.51f);

        labelNomePokemon = new Label(nomePoke, new Label.LabelStyle(font, null));
        labelNomePokemon.setPosition(playerHPBar.getX()+55,playerHPBar.getY()+50); // Posiziona la label accanto all'immagine della mossa
        labelNomePokemon.setFontScale(1f);
        stage.addActor(labelNomePokemon);
        labelNomeHPBars.add(labelNomePokemon);

        Action moveActionNomePoke = Actions.moveTo(1024-256+55, playerHPBar.getY()+50, 0.5f);
        // Applica l'azione all'immagine
        labelNomePokemon.addAction(moveActionNomePoke);


        labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
        labelLV.setPosition(labelNomePokemon.getX(),labelNomePokemon.getY()); // Posiziona la label accanto all'immagine della mossa
        labelLV.setFontScale(1f);
        stage.addActor(labelLV);
        labelNomeHPBars.add(labelLV);

        Action moveActionLV= Actions.moveTo(1024-256+210, labelNomePokemon.getY(), 0.5f);
        // Applica l'azione all'immagine
        labelLV.addAction(moveActionLV);


        labelHP= new Label(currentPokeHP, new Label.LabelStyle(font, null));
        labelHP.setPosition(1024,149); // Posiziona la label accanto all'immagine della mossa
        labelHP.setFontScale(0.8f);
        stage.addActor(labelHP);
        labelNomeHPBars.add(labelHP);

        Action moveActionHP= Actions.moveTo(1024-110,149, 0.5f);
        // Applica l'azione all'immagine
        labelHP.addAction(moveActionHP);


        labelHPTot= new Label(maxPokeHP, new Label.LabelStyle(font, null));
        labelHPTot.setPosition(1024,149); // Posiziona la label accanto all'immagine della mossa
        labelHPTot.setFontScale(0.8f);
        stage.addActor(labelHPTot);
        labelNomeHPBars.add(labelHPTot);

        Action moveActionHPTot= Actions.moveTo(1024-55,149, 0.5f);
        // Applica l'azione all'immagine
        labelHPTot.addAction(moveActionHPTot);
        if (switched && !switchForzato){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    utilizzoMossaBot(false,null);
                }
            }, 2.5f);
            
        }
    }

    private void piazzaLabelLottaPerBot(){
        
        //e ora piazza le hp Bar
        Texture imageHPBot = new Texture("battle/botHPBar.png");
        botHPBar = new Image(imageHPBot);
        botHPBar.setSize(244, 70);
        botHPBar.setPosition(-250, 520);
        stage.addActor(botHPBar);

        Action moveActionBot = Actions.moveTo(0, 520, 0.5f);
        // Applica l'azione all'immagine
        botHPBar.addAction(moveActionBot);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checkPerDoppiaBarra++;
                HPbot=placeHpBar(botHPBar,100,16,currentPokeHPBot,maxPokeHPBot); 
            }
        }, 0.51f);
        

        labelNomePokemonBot = new Label(nomePokeBot, new Label.LabelStyle(font, null));
        labelNomePokemonBot.setPosition(-300,botHPBar.getY()+27); // Posiziona la label accanto all'immagine della mossa
        labelNomePokemonBot.setFontScale(1f);
        stage.addActor(labelNomePokemonBot);
        labelNomeHPBars.add(labelNomePokemonBot);

        Action moveActionNomePokeBot = Actions.moveTo(25, botHPBar.getY()+27, 0.5f);
        // Applica l'azione all'immagine
        labelNomePokemonBot.addAction(moveActionNomePokeBot);


        labelLVBot = new Label(LVPokeBot, new Label.LabelStyle(font, null));
        labelLVBot.setPosition(labelNomePokemonBot.getX(),labelNomePokemonBot.getY()); // Posiziona la label accanto all'immagine della mossa
        labelLVBot.setFontScale(1f);
        stage.addActor(labelLVBot);
        labelNomeHPBars.add(labelLVBot);

        Action moveActionLVBot = Actions.moveTo(170,labelNomePokemonBot.getY(), 0.5f);
        // Applica l'azione all'immagine
        labelLVBot.addAction(moveActionLVBot);

    }

    private void restorePreviousImage(Image label) {
        Drawable newDrawable = new TextureRegionDrawable(fightLabels[currentIndex]);
        label.setDrawable(newDrawable);
    }
    
    private void updateLabelImage(Image label) {
        Drawable newDrawable = new TextureRegionDrawable(fightLabels[currentIndex+1]);
        label.setDrawable(newDrawable);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                restorePreviousImage(label); // Ripristina l'immagine precedente dopo 0.5 secondi
            }
        }, 0.3f);
    }
    


    private void piazzaMosse(){
        for (int i=0;i<listaMosse.size();i++){
            Image labelMosse = new Image(listaMosse.get(i).getLabelTipo(listaMosse.get(i).getTipo()));
            labelMosse.setPosition(i*256, 0);
            labelMosse.setSize(256,125);
            stage.addActor(labelMosse);
            labelMosseArray.add(labelMosse);

            Label labelNomeMossa = new Label(listaMosse.get(i).getNome(), new Label.LabelStyle(font, null));
            labelNomeMossa.setPosition(labelMosse.getX() + 20, labelMosse.getY() + 68); // Posiziona la label accanto all'immagine della mossa
            labelNomeMossa.setFontScale(1.5f);
            stage.addActor(labelNomeMossa);
            labelNomeMosseArray.add(labelNomeMossa);

            Label labelPPTot = new Label(listaMosse.get(i).getmaxPP(), new Label.LabelStyle(font, null));
            labelPPTot.setPosition(labelMosse.getX() + 195, labelMosse.getY() + 30); 
            labelPPTot.setFontScale(1.3f);
            stage.addActor(labelPPTot);
            labelNomeMosseArray.add(labelPPTot);


            Label labelPPatt = new Label(listaMosse.get(i).getattPP(), new Label.LabelStyle(font, null));
            if(Integer.parseInt(listaMosse.get(i).getattPP())>9){
                labelPPatt.setPosition(labelMosse.getX() + 145, labelMosse.getY() + 30); 
            }
            else{
                labelPPatt.setPosition(labelMosse.getX() + 158, labelMosse.getY() + 30);
            }
            labelPPatt.setFontScale(1.3f);
            stage.addActor(labelPPatt);
            labelNomeMosseArray.add(labelPPatt);

            final int indiceMossa=i;
            ClickListener listener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    backImage.remove();
                    if (statsPlayer.get(4)>=statsBot.get(4)){
                        listaMosse.get(indiceMossa).setattPP();
                        utilizzoMossa(labelMosse, true);
                        modificaPPMossePoke(numeroIndexPoke,listaMosse);
                        /*if (Integer.parseInt(currentPokeHPBot)>0){
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    utilizzoMossaBot();
                                }
                            }, 3.51f);
                            
                        }*/
                    }
                    else{
                        utilizzoMossaBot(true, labelMosse);
                        if (Integer.parseInt(currentPokeHP)>0){
                            listaMosse.get(indiceMossa).setattPP();
                            modificaPPMossePoke(numeroIndexPoke,listaMosse);
                        }
                        /*if (Integer.parseInt(currentPokeHP)>0){
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    utilizzoMossa(labelMosse,false);
                                }
                            }, 3.51f);
                            
                        }*/
                    }
                }
            };
            if (Integer.parseInt(listaMosse.get(i).getattPP())>0){
                labelMosse.addListener(listener);
                labelNomeMossa.addListener(listener);
                labelPPTot.addListener(listener);
                labelPPatt.addListener(listener);
            }
            }
        
            for (int j=0; j<4-listaMosse.size(); j++){
                Texture noMoveTexture = new Texture("battle/noMove.png");
                Image labelMosse = new Image(noMoveTexture);
                labelMosse.setPosition((j+listaMosse.size())*256, 0);
                labelMosse.setSize(256,125);
                stage.addActor(labelMosse);
                labelMosseArray.add(labelMosse);
            }
        Texture backButton = new Texture("battle/b.png");
        backImage = new Image(backButton);
        backImage.setPosition(0,130);
        backImage.setSize(60, 60);
        stage.addActor(backImage);
        labelMosseArray.add(backImage);
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Image label : labelMosseArray) {
                    label.remove();
                }
                for (Label label : labelNomeMosseArray) {
                    label.remove();
                }
                labelMosseArray.clear();
                labelNomeMosseArray.clear();
            }
        };
        backImage.addListener(listener);
    }

    private void utilizzoMossa(Image labelMosse, boolean otherAttack){
        nextMoveBot=true;
        nextMove=labelMosse;
        globalOtherAttack=otherAttack;
        counterForNextMove=0;
        float trovaX= (labelMosse.getX())/256;
        int X = (int) trovaX;
        nomeMossa=listaMosse.get(X).getNome();
        sistemaLabel4(nomeMossa);
        labelDiscorsi4.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
        label4=labelDiscorsi4.getLabel(); 
        stage.addActor(label4);

        FileHandle mosseFile = Gdx.files.internal("pokemon/mosse.json");
        String mosseJsonString = mosseFile.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON delle mosse
        JsonValue mosseJson = new JsonReader().parse(mosseJsonString);
        JsonValue tipoJson = mosseJson.get(nomeMossa);
        String tipologiaMossa = tipoJson.getString("attacco");
        if (tipologiaMossa.equals("fisico")){
            danno=listaMosse.get(X).calcolaDanno(statsPlayer.get(0),statsBot.get(1),Integer.parseInt(LVPoke),nomePoke,nomePokeBot);
        }
        else{
            danno=listaMosse.get(X).calcolaDanno(statsPlayer.get(2),statsBot.get(3),Integer.parseInt(LVPoke),nomePoke,nomePokeBot);
        }   

        currentPokeHPBot= Integer.toString((Integer.parseInt(currentPokeHPBot))-danno);

        if(Integer.parseInt(currentPokeHPBot)<=0){
            currentPokeHPBot= Integer.toString(0);
        }

        if (danno!=0){
            if (isBotFight){
                modificaHPPokeBot(numeroIndexPokeBot, currentPokeHPBot);
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    updateHpBarWidth(HPbot, currentPokeHPBot, maxPokeHPBot, pokemonImageBot, nomePokeBot);
                }
            }, 1.5f);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                label4.remove();
                for (Image label : labelMosseArray) {
                    label.remove();
                }
                for (Label label : labelNomeMosseArray) {
                    label.remove();
                }
                labelMosseArray.clear();
                labelNomeMosseArray.clear();

                if(otherAttack==true){
                    if (counterForNextMove == 0){
                        if (Integer.parseInt(currentPokeHPBot)>0){
                            utilizzoMossaBot(false, null);
                        }                
                    }
                }
            }
        }, 2.5f);
    }

    private void utilizzoMossaBot(boolean otherAttack, Image labelMosse){
        nextMoveBot=false;
        nextMove=labelMosse;
        globalOtherAttack=otherAttack;
        counterForNextMove=0;
        Random random = new Random();
        int X = random.nextInt(listaMosseBot.size());
        while (Integer.parseInt(listaMosseBot.get(X).getattPP())<=0){
            X = random.nextInt(listaMosseBot.size());
        }
        nomeMossaBot=listaMosseBot.get(X).getNome();
        listaMosseBot.get(X).setattPP();
        sistemaLabel14(nomeMossaBot);
        labelDiscorsi14.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
        label14=labelDiscorsi14.getLabel(); 
        stage.addActor(label14);

        FileHandle mosseFile = Gdx.files.internal("pokemon/mosse.json");
        String mosseJsonString = mosseFile.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON delle mosse
        JsonValue mosseJson = new JsonReader().parse(mosseJsonString);
        JsonValue tipoJson = mosseJson.get(nomeMossaBot);
        String tipologiaMossa = tipoJson.getString("attacco");
        if (tipologiaMossa.equals("fisico")){
            dannoBot=listaMosseBot.get(X).calcolaDanno(statsBot.get(0),statsPlayer.get(1),Integer.parseInt(LVPokeBot),nomePokeBot,nomePoke);
        }
        else{
            dannoBot=listaMosseBot.get(X).calcolaDanno(statsBot.get(2),statsPlayer.get(3),Integer.parseInt(LVPokeBot),nomePokeBot,nomePoke);
        }   

        pokeHPbeforeFight=currentPokeHP;

        currentPokeHP= Integer.toString((Integer.parseInt(currentPokeHP))-dannoBot);


        if(Integer.parseInt(currentPokeHP)<=0){
            currentPokeHP= Integer.toString(0);
        }


        if (dannoBot!=0){
            modificaHPPoke(numeroIndexPoke, currentPokeHP);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    updateHpBarWidth(HPplayer, currentPokeHP, maxPokeHP, pokemonImage, nomePoke);
                }
            }, 1.5f);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                label14.remove();
                for (Image label : labelMosseArray) {
                    label.remove();
                }
                for (Label label : labelNomeMosseArray) {
                    label.remove();
                }
                labelMosseArray.clear();
                labelNomeMosseArray.clear();

                if(otherAttack==true){
                    if (counterForNextMove==0){
                        if (Integer.parseInt(currentPokeHP)>0){
                            utilizzoMossa(labelMosse,false);
                        }                
                    }
                }
            }
        }, 2.5f);
    }

    private void sistemaLabel4(String nome){
        this.nomeMossa=nome;
        String discorso4= nomePoke + " utilizza " + nomeMossa+"!";
        labelDiscorsi4 = new LabelDiscorsi(discorso4,dimMax,0,true);
    }

    private void sistemaLabel14(String nome){
        this.nomeMossa=nome;
        String discorso14= nomePokeBot + " utilizza " + nomeMossa+"!";
        labelDiscorsi14 = new LabelDiscorsi(discorso14,dimMax,0,true);
    }
    

    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                boolean check;
        
                if (image==imageBall){
                    check=true;
                    checkInt++;
                }
                else{
                    check=false;
                    checkInt++;
                }
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        stateTime = 2f;
                        frame = animation.getKeyFrame(stateTime, false);
                        image.setDrawable(new TextureRegionDrawable(frame));

                        if (animation.isAnimationFinished(stateTime)) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    image.remove();
                                    //nel caso dovesse esplodere un giorno tutto questo, di fianco ad entrambi i check aggiungere && checkInt<3; da problemi ma se qualcosa esplode lo si riaggiunge
                                    if (check ){
                                        showPokemon(labelBaseD, nomePoke);
                                    }
                                    else if (isBotFight && !check){
                                        synchronized(lock){
                                            try {
                                                semaphore.acquire();
                                                checkPerDoppioPoke++;
                                                showPokemon(labelBaseU, nomePokeBot);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            semaphore.release();
                                        }
                                    }         
                                }
                            }, 0.5f);
                            this.cancel();
                        }
                    }
                }, 0.1f);
            }
        }, 0.3f);
    }

    private void showPokemon(Image baseImage, String nome) {
        if (baseImage.equals(labelBaseU) && checkPerDoppioPoke>1){
            return;
        }
        Texture pokemonTexture = new Texture("pokemon/"+nome+".png");
        int frameWidth = pokemonTexture.getWidth() / 4;
        int frameHeight = pokemonTexture.getHeight();
    
        TextureRegion[] pokemonFrames;
        Animation<TextureRegion> pokemonAnimation;
        
    
        if (baseImage == labelBaseD) {
            pokemonFrames = new TextureRegion[2];
            int startX = frameWidth * 2; // Inizia dalla terza parte dell'immagine
            for (int i = 0; i < 2; i++) {
                pokemonFrames[i] = new TextureRegion(pokemonTexture, startX + i * frameWidth, 0, frameWidth, frameHeight);
            }
            pokemonAnimation = new Animation<>(0.4f, pokemonFrames);
    
            pokemonImage = new Image(pokemonFrames[0]);
            pokemonImage.setSize(frameWidth * 3, frameHeight * 3);
            pokemonImage.setPosition(labelBaseD.getX()+270, labelBaseD.getY());
            pokemonImage.setName("pokemon player");
            stage.addActor(pokemonImage);
            animation(pokemonImage, pokemonAnimation);
        } 
        
        else if (baseImage == labelBaseU) {
            if (!isBotFight){
                botImage.remove();
            }
            pokemonFrames = new TextureRegion[2];
            for (int i = 0; i < 2; i++) {
                pokemonFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
            }
            pokemonAnimation = new Animation<>(0.4f, pokemonFrames);
    
            pokemonImageBot = new Image(pokemonFrames[0]);
            pokemonImageBot.setSize(frameWidth * 3, frameHeight * 3);
            pokemonImageBot.setPosition(labelBaseU.getX()+60, labelBaseU.getY()+20);
            pokemonImageBot.setName("pokemon bot");
            stage.addActor(pokemonImageBot);
            animation(pokemonImageBot, pokemonAnimation);
        } else {
            return; // Esci dalla funzione in caso di baseImage non gestito
        }

        if (baseImage.equals(labelBaseU)){
            checkPerDoppioPoke=0;
        }
    }

    private void animation(Image pokeImage, Animation<TextureRegion> pokemonAnimation){   

        final Animation<TextureRegion> finalPokemonAnimation = pokemonAnimation; // Copia final dell'animazione
    
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                float stateTime = 0f;
                TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                pokeImage.setDrawable(new TextureRegionDrawable(frame));
    
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        float stateTime = 1f;
                        TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                        pokeImage.setDrawable(new TextureRegionDrawable(frame));

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                float stateTime = 0f;
                                TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                                pokeImage.setDrawable(new TextureRegionDrawable(frame));
                            }
                        }, 0.7f); // Mostra il secondo frame per 1 secondo
                    }
                }, 0.3f); // Aspetta 1 secondo prima di mostrare il secondo frame
            }
        }, 0f); // Inizia subito l'animazione

    }
    

    public void leggiPoke(int numero) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("ashJson/squadra.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get("poke"+numero);
            nomePoke = pokeJson.getString("nomePokemon");
            LVPoke = pokeJson.getString("livello");

            JsonValue statistiche = pokeJson.get("statistiche"); 

            currentPokeHP = pokeJson.get("statistiche").getString("hp");
            maxPokeHP = pokeJson.get("statistiche").getString("hpTot");

            statsPlayer.clear();
            for (JsonValue stat : statistiche) {
                if (!stat.name.equals("hp") && !stat.name.equals("hpTot")){
                    statsPlayer.add(stat.asInt());
                }
            }
            
            JsonValue mosse = pokeJson.get("mosse");
            nomeBall = pokeJson.getString("tipoBall");
            listaMosse.clear();
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                String attPP = mossaJson.getString("ppAtt");
                String maxPP = mossaJson.getString("ppTot");
                
                // Aggiungi la mossa alla lista
                Mossa mossa=new Mossa(nomeMossa, tipoMossa, maxPP, attPP, this); //gli passo Battle stesso con "this" per poter chiamare anche i metodi di Battle da Mossa
                listaMosse.add(mossa);
            }

            if (currentPokeHP.equals("0") || nomePoke.equals("")){
                if (numeroIndexPoke<6){
                    numeroIndexPoke++;
                    leggiPoke(numeroIndexPoke);
                }
            }

    }

    public void leggiPokeSecondario(int numero) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("ashJson/squadra.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        JsonValue pokeJson = json.get("poke"+numero);
        currentPokeHPforSquad = pokeJson.get("Statistiche").getString("hp");
        nomePokeSquad = pokeJson.getString("nomePokemon");
        if (!pokeJson.getString("livello").equals("")){
            if (pokeJson.getInt("livello")>levelMax){
                levelMax=pokeJson.getInt("livello");
            }
        }

    }

    private void posizionaLabelSquadra(){

        labelPokePiazzate=true;
 
        Texture arrowPlayer = new Texture("battle/playerArrow.png");
        playerArrow = new Image(arrowPlayer);
        //playerArrow.setPosition(1024-250,270);
        playerArrow.setPosition(1024,270); //inizialmente fuori dallo schermo
        playerArrow.setSize(250, 26);
        stage.addActor(playerArrow);

        if (isBotFight){
        Texture arrowBot = new Texture("battle/botArrow.png");
        botArrow = new Image(arrowBot);
        //botArrow.setPosition(0,650);
        botArrow.setPosition(-250,650); //inizialmente fuori dallo schermo
        botArrow.setSize(250, 26);
        stage.addActor(botArrow);
        Action botArrowAction = Actions.moveTo(0, 650, 0.5f);
        botArrow.addAction(botArrowAction);
        piazzaSquadBot();
        }

        // Creazione delle azioni per lo spostamento delle frecce
        Action playerArrowAction = Actions.moveTo(1024-250, 270, 0.5f); // duration è la durata dell'animazione in secondi

        // Applicazione delle azioni alle frecce
        playerArrow.addAction(playerArrowAction);

        piazzaSquad();
    }


    private void piazzaSquad(){
        Texture texture = new Texture("battle/ballsForNumber.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight() / 3);
        // Inizializza l'array di sprite
        spriteArrayBallsSquadra = new Sprite[8];
        int colonna=-1;

        for (int i=0; i<6; i++){
            leggiPokeSecondario(i+1);
            if (nomePokeSquad.isEmpty()){
                colonna=2;
            }
            else {
                if (currentPokeHPforSquad.equals("0")){
                    colonna=1;
                }
                else{
                    colonna=0;
                }
            }


            // Riempie l'array di sprite
            int indice = 0;
            for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
            }

            final int index = i;
            final int col =colonna;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    TextureRegion region = textureRegions[col][0];
                    Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

                    // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al suo asse centrale
                    image.setOrigin(Align.center);
                    image.setPosition(1024, 270); // Posiziona l'immagine fuori dallo schermo
                    image.setSize(32, 30);
                    stage.addActor(image); // Aggiungi l'immagine allo stage
                    

                    Action moveAction = Actions.moveTo(playerArrow.getX() + (30 * (6-index) + 10 * (6-index) - 20), playerArrow.getY(), 1f); // Sposta l'immagine sulla freccia del giocatore
                    image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                    Action rotateAction = Actions.rotateBy(360, 1f); // Ruota l'immagine di 360 gradi in 1 secondi
                    Action changeImageAction = Actions.run(() -> {
                        image.setDrawable(new TextureRegionDrawable(region)); // Cambia l'immagine
                    });
                    ParallelAction parallelAction = Actions.parallel(moveAction, rotateAction);
                    SequenceAction sequenceAction = Actions.sequence(parallelAction, changeImageAction);
                    image.addAction(sequenceAction); // Applica le azioni all'immagine

                }
            }, 0.5f*(6-i));
        }


    }

    private void piazzaSquadBot(){
        Texture texture = new Texture("battle/ballsForNumber.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight() / 3);
        // Inizializza l'array di sprite
        spriteArrayBallsSquadra = new Sprite[8];
        int colonna=-1;

        for (int i=0; i<6; i++){
            leggiPokeSecondarioBot(i+1,1);
            if (nomePokeSquadBot.isEmpty()){
                colonna=2;
            }
            else {
                if (currentPokeHPBotSquad.equals("0")){
                    colonna=1;
                }
                else{
                    colonna=0;
                }
            }


            // Riempie l'array di sprite
            int indice = 0;
            for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
            }

            final int index = i;
            final int col =colonna;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    TextureRegion region = textureRegions[col][0];
                    Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

                    // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al suo asse centrale
                    image.setOrigin(Align.center);
                    image.setPosition(0, 650); // Posiziona l'immagine fuori dallo schermo
                    image.setSize(32, 30);
                    stage.addActor(image); // Aggiungi l'immagine allo stage
                    

                    Action moveAction = Actions.moveTo(botArrow.getX() + (30 * index + 10 * index), botArrow.getY(), 1f); // Sposta l'immagine sulla freccia del giocatore
                    image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                    Action rotateAction = Actions.rotateBy(360, 1f); // Ruota l'immagine di 360 gradi in 1.5 secondi
                    Action changeImageAction = Actions.run(() -> {
                        image.setDrawable(new TextureRegionDrawable(region)); // Cambia l'immagine
                    });
                    ParallelAction parallelAction = Actions.parallel(moveAction, rotateAction);
                    SequenceAction sequenceAction = Actions.sequence(parallelAction, changeImageAction);
                    image.addAction(sequenceAction); // Applica le azioni all'immagine

                }
            }, 0.5f*(6-i));
        }


    }



    private void leggiBot(String nameBot){
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        JsonValue pokeJson = json.get(nameBot);
        nomeBot = pokeJson.getString("nome");
        tipoBot = pokeJson.getString("tipo");


    }

    private void leggiPokeBot(String nBot, int numeroPoke) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();

        FileHandle file2 = Gdx.files.internal("pokemon/mosse.json");
        String jsonString2 = file2.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue json2 = new JsonReader().parse(jsonString2);

            JsonValue pokeJson = json.get(nBot).get("poke"+numeroPoke);
            nomePokeBot = pokeJson.getString("nomePokemon");
            LVPokeBot = pokeJson.getString("livello");

            if (!LVPokeBot.equals("")){
                levelLastPokeBot=LVPokeBot;
            }

            JsonValue statistiche = pokeJson.get("statistiche"); 
            statsBot.clear();
            for (JsonValue stat : statistiche) {
                if (!stat.name.equals("hp") && !stat.name.equals("hpTot")){
                    statsBot.add(stat.asInt());
                }
            }
            maxPokeHPBot = statistiche.getString("hpTot");
            currentPokeHPBot = pokeJson.get("statistiche").getString("HP");

            JsonValue mosse = pokeJson.get("mosse");
            listaMosseBot.clear(); 
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                String ppMossa = "0";
                if(!nomeMossa.isEmpty()){
                    ppMossa = json2.get(nomeMossa).getString("pp");
                }
                // Aggiungi la mossa alla lista
                Mossa mossa=new Mossa(nomeMossa, tipoMossa, ppMossa, ppMossa, this);
                listaMosseBot.add(mossa);
            }

            if (isBotFight){
                if (currentPokeHPBot.equals("0") || nomePokeBot.equals("")){
                    if (numeroIndexPokeBot<6){
                        numeroIndexPokeBot++;
                        leggiPokeBot(nBot,numeroIndexPokeBot);
                    }
                    else if(numeroIndexPokeBot==6){
                        calcolaDenaroVintoDaNPC();
                        modifcicaDenaro(soldiPresi);
                        fineBattaglia();
                    }
                }
            }

    }


    private void leggiPokeSelvatico(String nBot, String zona) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("jsonPokeSelvatici/"+ zona +".json");
        String jsonString = file.readString();
        FileHandle file2 = Gdx.files.internal("pokemon/mosse.json");
        String jsonString2 = file2.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue json2 = new JsonReader().parse(jsonString2);

        JsonValue pokeJson = json.get(nomeSelvatico);
        nomePokeBot = pokeJson.getString("nomePokemon");
        LVPokeBot = pokeJson.getString("livello");

        x = (float) (Math.random() * (1.3 - 0.4)) + 0.4f;

        FileHandle filePoke = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonStringPoke = filePoke.readString();
        JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

        JsonValue statistiche = jsonPoke.get(nameBot).get("stat"); 
        statsBot.clear();
        for (JsonValue stat : statistiche) {
            if (!stat.name.equals("PS")){
                statsBot.add((int)(stat.asInt()*x));
            }
        }
        maxPokeHPBot = statistiche.getString("PS");
        currentPokeHPBot = statistiche.getString("PS");

        JsonValue mosse = pokeJson.get("mosse");
        for (JsonValue mossaJson : mosse) {
            String nomeMossa = mossaJson.getString("nome");
            String tipoMossa = mossaJson.getString("tipo");
            String ppMossa = "0";
            if(!nomeMossa.isEmpty()){
                ppMossa = json2.get(nomeMossa).getString("pp");
            }
            // Aggiungi la mossa alla lista
            Mossa mossa=new Mossa(nomeMossa, tipoMossa, ppMossa, ppMossa, this);
            listaMosseBot.add(mossa);
        }

    }

    public void leggiPokeSecondarioBot(int numero, int numBot) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get(nameBot).get("poke"+numero);
        nomePokeSquadBot = pokeJson.getString("nomePokemon");
        currentPokeHPBotSquad = pokeJson.get("statistiche").getString("HP");

    }


    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP){
        if (image.equals(botHPBar) && checkPerDoppiaBarra>1){
            return null; //ferma l'esecuzione se è già attiva (evita il problema della doppia barra)
        }
        // Calcola la percentuale degli HP attuali rispetto agli HP totali
        float percentualeHP = Float.parseFloat(currentHP)  / Float.parseFloat(maxHP);
        float lunghezzaHPBar = 96 * percentualeHP;
         // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
         Image hpBar = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("battle/white_pixel.png"))));
         hpBar.setSize((int)lunghezzaHPBar, 6);
         hpBar.setPosition(image.getX() + diffX, image.getY() + diffY);
         //hpBar.setPosition(400, 400);
        // Determina il colore della hpBar in base alla percentuale calcolata
        Color coloreHPBar;
        if (percentualeHP >= 0.5f) {
            coloreHPBar = Color.GREEN; // Verde se sopra il 50%
        } else if (percentualeHP > 0.15f && percentualeHP < 0.5f) {
            coloreHPBar = Color.YELLOW; // Giallo se tra il 15% e il 50%
        } else {
            coloreHPBar = Color.RED; // Rosso se sotto il 15%
        }

        hpBar.setColor(coloreHPBar);
        // Aggiungi hpBar allo stage
        stage.addActor(hpBar);

        if (image.equals(botHPBar)){
            checkPerDoppiaBarra=0;
        }
        
        return hpBar;
    }


    // Metodo per aggiornare la larghezza della barra degli HP con un'animazione
    private void updateHpBarWidth(Image hpBar, String currentHP, String maxHP, Image pokeImage, String nomePokem) {
        synchronized (lock) { // Inizio del blocco sincronizzato
            float percentualeHP = Float.parseFloat(currentHP) / Float.parseFloat(maxHP);
            float lunghezzaHPBar = 96 * percentualeHP;

            Color coloreHPBar;
            if (percentualeHP >= 0.5f) {
                coloreHPBar = Color.GREEN; // Verde se sopra il 50%
            } else if (percentualeHP > 0.15f && percentualeHP < 0.5f) {
                coloreHPBar = Color.YELLOW; // Giallo se tra il 15% e il 50%
            } else {
                coloreHPBar = Color.RED; // Rosso se sotto il 15%
            }
            // Crea un'azione parallela per aggiornare la larghezza della barra
            hpBar.addAction(Actions.sizeTo(lunghezzaHPBar, hpBar.getHeight(), 1f));

            // Aggiungi un'azione per cambiare il colore della barra
            hpBar.addAction(Actions.color(coloreHPBar, 1f));

            if (hpBar == HPplayer) {
                int passi;
                // Calcola il numero di passi necessari per raggiungere currentHP
                if (dannoBot>Integer.parseInt(pokeHPbeforeFight)){
                    passi = Integer.parseInt(pokeHPbeforeFight);
                }
                else{
                    passi = dannoBot;
                }
                
                // Calcola il ritardo tra ogni passo
                float ritardoTraPassi = 1f / passi;

                // Crea un'azione parallela per gestire contemporaneamente l'aggiornamento della larghezza della barra HP e l'animazione del cambiamento di labelHP
                ParallelAction parallelAction = new ParallelAction();

                // Aggiungi un'azione per aggiornare la larghezza della barra HP
                parallelAction.addAction(Actions.sizeTo(lunghezzaHPBar, hpBar.getHeight(), 1f));

                // Aggiungi un'azione per l'animazione del cambiamento del valore di labelHP
                for (int i = 0; i < passi; i++) {
                    int nuovoValore = Integer.parseInt(pokeHPbeforeFight) - (i+1);
                    
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelHP.setText(String.valueOf(nuovoValore));
                        }
                    }, i * ritardoTraPassi);
                }

                // Esegui l'azione parallela
                hpBar.addAction(parallelAction);
            }

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (Integer.parseInt(currentHP) == 0) {
                        piazzaLabel12(nomePokem);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                faintPokemon(pokeImage);
                            }
                        }, 1.5f + delaySecondText);

                    }
                }
            }, 1f + delaySecondText);
            delaySecondText = 0;

        } // Fine del blocco sincronizzato
    }


    public void piazzaLabel5(){
        counterForNextMove++; 
        delaySecondText=0.7f;
        if (checkLabel5){
            checkLabel5=false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                labelDiscorsi5.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
                label5=labelDiscorsi5.getLabel();
                stage.addActor(label5);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi5.reset();
                        label5.remove();
                        label5=null;
                        checkLabel5=true;
                        if(globalOtherAttack==true){
                            if (counterForNextMove==1){
                                    if (nextMoveBot){
                                        if (Integer.parseInt(currentPokeHPBot)>0)
                                            utilizzoMossaBot(false, null);
                                    }
                                    else{
                                        if (Integer.parseInt(currentPokeHP)>0)
                                            utilizzoMossa(nextMove,false);
                                    }
                                }
                            }
                    }
                }, 1.2f);
            }
        }, 2f);
    }

    }
    

    public void piazzaLabel6(){
        counterForNextMove++; 
        delaySecondText=0.7f;
        if (checkLabel6){
            checkLabel6=false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                labelDiscorsi6.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
                label6=labelDiscorsi6.getLabel();
                stage.addActor(label6);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi6.reset();
                        label6.remove();
                        label6=null;
                        checkLabel6=true;
                        if(globalOtherAttack==true){
                            if (counterForNextMove==1){
                                    if (nextMoveBot){
                                        if (Integer.parseInt(currentPokeHPBot)>0)
                                            utilizzoMossaBot(false, null);
                                    }
                                    else{
                                        if (Integer.parseInt(currentPokeHP)>0)
                                            utilizzoMossa(nextMove,false); 
                                    }
                                }
                            }
                    }
                }, 1.2f);
            }
        }, 2f);
    }
    }


    public void piazzaLabel7(){
        counterForNextMove++; 
        delaySecondText=0.7f;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            labelDiscorsi7.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
            label7=labelDiscorsi7.getLabel();
            stage.addActor(label7);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi7.reset();
                    label7.remove();
                    label7=null;
                    if(globalOtherAttack==true){
                        if (counterForNextMove==1){
                                if (nextMoveBot){
                                    if (Integer.parseInt(currentPokeHPBot)>0)
                                        utilizzoMossaBot(false, null);
                                }
                                else{
                                    if (Integer.parseInt(currentPokeHP)>0)
                                        utilizzoMossa(nextMove,false); 
                                }
                            }
                        }
                }
            }, 1.2f);
        }
    }, 2f);
    }

    public void piazzaLabel9(){
        //delay se è attiva già un'altra animazione
        float d=0;
        if (!checkLabel5 || !checkLabel6){
            d=1.2f;
        }

        final float delay=d;
        delaySecondText=0.7f+delay;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            labelDiscorsi9.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
            label9=labelDiscorsi9.getLabel();
            stage.addActor(label9);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi9.reset();
                    label9.remove();
                    label9=null; 
                    if(globalOtherAttack==true){
                            if (nextMoveBot){
                                if (Integer.parseInt(currentPokeHPBot)>0)
                                    utilizzoMossaBot(false, null);
                            }
                            else{
                                if (Integer.parseInt(currentPokeHP)>0)
                                    utilizzoMossa(nextMove,false); 
                            }
                        }
                }
            }, 1.2f);
        }
    }, 2f+delay);

    }



    public void faintPokemon(final Image pokeImage) { 

        modificaPPMossePoke(numeroIndexPoke,listaMosse); //Aggiorna i PP delle mosse del pokemon se viene sconfitto

        Rectangle scissors = new Rectangle(pokeImage.getX(), pokeImage.getY(), pokeImage.getWidth(), pokeImage.getHeight());
    
        float initialY = pokeImage.getY();
    
        // Calcola la nuova posizione Y, che sarà sotto lo schermo
        float newY = -initialY;
    
        // Crea un'azione di spostamento dell'immagine verso la nuova posizione Y
        Action moveAction = Actions.moveBy(0, newY, 0.6f); // Durata: 0.6 secondi
    
        // Applica l'azione di spostamento all'immagine
        pokeImage.addAction(moveAction);
    
        // Ritaglia l'area dell'immagine mentre diminuisci la sua altezza
        ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(), new Rectangle(pokeImage.getX(), pokeImage.getY(), pokeImage.getWidth(), pokeImage.getHeight()), scissors);
        ScissorStack.pushScissors(scissors);
    

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Ferma il ritaglio
                ScissorStack.popScissors();
                pokeImage.remove();

                if (isBotFight && pokeImage!=pokemonImage){
                    if (numeroIndexPokeBot<6 && Integer.parseInt(currentPokeHPBot)==0){
                        numeroIndexPokeBot++;
                        rimuoviPokeDaBattagliaBot();
                        leggiPokeBot(nameBot,numeroIndexPokeBot);
                        if (!isBattleEnded){
                        piazzaLabelLottaPerBot();
                        checkInt--;
                        showBallBot();
                        checkPerDoppiaBarra++;
                        HPbot=placeHpBar(botHPBar,100,16,currentPokeHPBot,maxPokeHPBot);
                        } 
                    }
                    else if (numeroIndexPokeBot==6 && Integer.parseInt(currentPokeHPBot)==0 && !isBattleEnded){
                        calcolaDenaroVintoDaNPC();
                        modifcicaDenaro(soldiPresi);
                        fineBattaglia();
                    }
                }
                else if(pokeImage==pokemonImage){
                    int counter=0;
                    for (int i=0; i<6; i++){
                        leggiPokeSecondario(i+1);
                        if (Integer.parseInt(currentPokeHPforSquad)>0){
                            counter++;
                        }
                    }
                    if (counter!=0){
                        squadra = new Squadra(stage,true, battle, null, true);
                        switchForzato=true;
                    }
                    else{
                        sconfitta=true;
                        calcolaDenaroPerso();
                        modifcicaDenaro(denaroPerso);
                        fineBattaglia();
                    }
                }
                else{
                    fineBattaglia();
                }
            }
        }, 0.6f);
        
    }



    public void piazzaLabel12(String nomePokem){
        String discorso12= nomePokem+ " non ha piu' energie!";
        labelDiscorsi12 = new LabelDiscorsi(discorso12,dimMax,0,true);
        labelDiscorsi12.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
        label12=labelDiscorsi12.getLabel();
        stage.addActor(label12);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                labelDiscorsi12.reset();
                label12.remove();
                label12=null;
                if (isBotFight){
                    updatePokeSquadBot();
                }
                updatePokeSquad();
            }
        }, 2f);
    }

    public void modificaHPPokeBot(int numero, String currentHP) {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/bots/bots.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
    
        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        JsonValue pokeJson = json.get(nameBot).get("poke" + numero);
    
        // Ottieni l'oggetto "statistiche" all'interno del Pokémon
        JsonValue statistiche = pokeJson.get("statistiche");
    
        // Rimuovi il campo "hp" corrente
        statistiche.remove("hp");
        // Aggiungi il nuovo campo "hp" con il valore aggiornato
        statistiche.addChild("hp", new JsonValue(Integer.parseInt(currentHP)));
        // Scrivi il JSON aggiornato nel file mantenendo la formattazione
        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        
    }

    public void modificaHPPoke(int numero, String currentHP) {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
    
        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        JsonValue pokeJson = json.get("poke" + numero);
    
        // Ottieni l'oggetto "statistiche" all'interno del Pokémon
        JsonValue statistiche = pokeJson.get("statistiche");
    
        // Rimuovi il campo "hp" corrente
        statistiche.remove("hp");
        // Aggiungi il nuovo campo "hp" con il valore aggiornato
        statistiche.addChild("hp", new JsonValue(Integer.parseInt(currentHP)));
        // Scrivi il JSON aggiornato nel file mantenendo la formattazione
        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        
    }
    

    public void updatePokeSquadBot(){
        Texture texture = new Texture("battle/ballsForNumber.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight() / 3);
        // Inizializza l'array di sprite
        spriteArrayBallsSquadra = new Sprite[8];
        int colonna=-1;

        for (int i=0; i<6; i++){
            leggiPokeSecondarioBot(i+1,1);
            if (nomePokeSquadBot.isEmpty()){
                colonna=2;
            }
            else {
                if (currentPokeHPBotSquad.equals("0")){
                    colonna=1;
                }
                else{
                    colonna=0;
                }
            }

            // Riempie l'array di sprite
            int indice = 0;
            for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
            }

            final int index = i;
            final int col =colonna;

            TextureRegion region = textureRegions[col][0];
            Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

            // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al suo asse centrale
            image.setOrigin(Align.center);
            image.setPosition(botArrow.getX() + (30 * index + 10 * index), botArrow.getY()); 
            image.setSize(32, 30);
            stage.addActor(image); // Aggiungi l'immagine allo stage

        }
    }

    public void updatePokeSquad(){
        Texture texture = new Texture("battle/ballsForNumber.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight() / 3);
        // Inizializza l'array di sprite
        spriteArrayBallsSquadra = new Sprite[8];
        int colonna=-1;

        for (int i=0; i<6; i++){
            leggiPokeSecondario(i+1);
            if (nomePokeSquad.isEmpty()){
                colonna=2;
            }
            else {
                if (currentPokeHPforSquad.equals("0")){
                    colonna=1;
                }
                else{
                    colonna=0;
                }
            }

            // Riempie l'array di sprite
            int indice = 0;
            for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
            }

            final int index = i;
            final int col =colonna;

            TextureRegion region = textureRegions[col][0];
            Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

            // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al suo asse centrale
            image.setOrigin(Align.center);
            image.setPosition(playerArrow.getX() + (30 * (6-index) + 10 * (6-index) - 20), playerArrow.getY()); 
            image.setSize(32, 30);
            stage.addActor(image); // Aggiungi l'immagine allo stage

        }
    }


    public void rimuoviPokeDaBattagliaBot(){
        botHPBar.remove();
        HPbot.remove();
        // Rimuovi labelNomePokemonBot
        labelNomeHPBars.remove(labelNomePokemonBot);
        labelNomePokemonBot.remove();

        // Rimuovi labelLVBot
        labelNomeHPBars.remove(labelLVBot);
        labelLVBot.remove();
    }

    public void rimuoviPokeDaBattaglia(){
        playerHPBar.remove();
        HPplayer.remove();

        labelNomeHPBars.remove(labelNomePokemon);
        labelNomePokemon.remove();

        labelNomeHPBars.remove(labelLV);
        labelLV.remove();

        labelNomeHPBars.remove(labelHP);
        labelHP.remove();

        labelNomeHPBars.remove(labelHPTot);
        labelHPTot.remove();
    }



    private void fineBattaglia(){
        isBattleEnded= true;
        modificaPPMossePoke(numeroIndexPoke,listaMosse); //Aggiorna i PP delle mosse del pokemon se la battaglia finisce

        if (isBotFight && !sconfitta){
            
            label3=labelDiscorsi3.getLabel();
            stage.addActor(label3);
        
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                labelDiscorsi3.reset();
                label3.remove();
                label3=null;

                label8=labelDiscorsi8.getLabel();
                stage.addActor(label8);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi8.reset();
                        label8.remove();
                        label8=null;
                        Erba.estratto=0;
                        dispose();
                    }
                }, 2f);
                
            }
        }, 2f);
        }
        else if (sconfitta){
            label16=labelDiscorsi16.getLabel();
            stage.addActor(label16);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi16.reset();
                    label16.remove();
                    label16=null;

                    label17=labelDiscorsi17.getLabel();
                    stage.addActor(label17);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi17.reset();
                            label17.remove();
                            label17=null;

                            label18=labelDiscorsi18.getLabel();
                            stage.addActor(label18);
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    labelDiscorsi18.reset();
                                    label18.remove();
                                    label18=null;
                                    Erba.estratto=0;
                                    dispose();
                                }
                            }, 3.5f);
                        }
                    }, 2.5f);
                }
            }, 2f);
        }
        else{
            Erba.estratto=0;
            dispose();
        }

    }


    public void cambiaPokemon(int newIndex){
        modificaPPMossePoke(numeroIndexPoke,listaMosse); //Aggiorna i PP delle mosse del pokemon se viene cambiato
        switched=true; 
        rimuoviPokeDaBattaglia();
        listaMosse.clear();
        leggiPoke(newIndex+1);
        ballTexture = new Texture("battle/"+nomeBall+"Player.png");
        String discorso2= "Vai "+ nomePoke + "!";
        labelDiscorsi2 = new LabelDiscorsi(discorso2,dimMax,0,true);
        pokemonImage.remove();
        numeroIndexPoke=newIndex+1;
        showBall(ballTexture);
        piazzaLabelLottaPlayer();
    }

    public void closeSquadra() {
        Gdx.input.setInputProcessor(stage);
        squadra = null;
    }

    public void modifcicaDenaro(int soldiModifica){
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/datiGenerali.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        int soldi= json.getInt("denaro");

        json.remove("denaro");

        json.addChild("denaro", new JsonValue(soldi+soldiModifica));
        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        
    }

    public void calcolaDenaroPerso(){
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("assets/ashJson/datiGenerali.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        int nMedaglie = json.getInt("numero_medaglie");
        denaroPerso=levelMax*(nMedaglie*33 +1);
        if (json.getInt("denaro")<denaroPerso){
            denaroPerso=json.getInt("denaro");
        }

        String discorso17= "Hai perso " + denaroPerso + " Pokédollari nella fuga.";
        labelDiscorsi17 = new LabelDiscorsi(discorso17,dimMax,0,true);

        denaroPerso*=-1;
    }

    public void calcolaDenaroVintoDaNPC(){
        soldiPresi=Integer.parseInt(levelLastPokeBot)*150;

        String discorso8= "Hai guadagnato "+ soldiPresi+" Pokédollari.";
        labelDiscorsi8 = new LabelDiscorsi(discorso8,dimMax,0,true);
    }


    public void calcoloTassoCattura(String nameUsedBall){
        this.nameUsedBall=nameUsedBall;
        int tasso;
        float bonusBall;

        borsaModifier.removeInventoryBall(nameUsedBall);

        // Carica il file JSON
        FileHandle file = Gdx.files.internal("assets/oggetti/strumenti.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        bonusBall = json.get("pokeballs").get(nameUsedBall).getFloat("cattura");

        // Carica il file JSON
        FileHandle file2 = Gdx.files.internal("assets/pokemon/Pokemon.json");
        String jsonString2 = file2.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json2 = new JsonReader().parse(jsonString2);
        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        JsonValue pokeJson = json2.get(nameBot);
        tasso = pokeJson.getInt("tassoCattura");

        tassoCattura=((((3*Integer.parseInt(maxPokeHPBot)) - (2*Integer.parseInt(currentPokeHPBot)))*tasso*bonusBall))/(3*Integer.parseInt(maxPokeHPBot));

        //System.out.println(tassoCattura);

        String discorso19= "Hai lanciato una "+ nameUsedBall + "!";
        labelDiscorsi19 = new LabelDiscorsi(discorso19,dimMax,0,true);

        label19=labelDiscorsi19.getLabel();
        stage.addActor(label19);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                calcoloVibrazioni(nameUsedBall);
            }
        }, 1.5f);
        
        
    }

    public void calcoloVibrazioni(String ballName){
        synchronized(lock){
        int nVibrazioni=0;
        Texture lanciaBallTexture = new Texture("battle/"+ballName+"Cattura.png");

        if(tassoCattura<255){
            int vibrazione = (int)(1048560/Math.sqrt(Math.sqrt(16711680/(int)tassoCattura)));

            for (int i=0; i<4; i++){
                int randomNumber = MathUtils.random(65535);
                if (randomNumber<vibrazione){
                    nVibrazioni++;
                }
            }

            
            lanciaCatturaBall(lanciaBallTexture, nVibrazioni);
            

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi19.reset();
                    label19.remove();
                    label19=null;
                }
            }, (1.5f+1.5f*nVibrazioni));

            /*if (nVibrazioni==4){
                System.out.println("catturato");
            }
            else{
                System.out.println("uscito, numero vibrazioni "+ nVibrazioni);
            }*/

        }
        else{
            lanciaCatturaBall(lanciaBallTexture, 4);
        }
    } // fine blocco sincronizzato
    }

    private void lanciaCatturaBall(Texture textureBall, int nVibrazioni){
        

        int regionWidth = textureBall.getWidth() / 12;
        int regionHeight = textureBall.getHeight();
    
        // Inizializza l'array delle TextureRegion della ball
        ballLanciata = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            ballLanciata[i] = new TextureRegion(textureBall, i * regionWidth, 0, regionWidth, regionHeight);
        }
    
        muoviBallLanciata = new Animation<>(0.5f, ballLanciata);

        // Crea e aggiungi l'immagine della ball allo stage
        imageBallLanciata = new Image(ballLanciata[1]);
        imageBallLanciata.setSize(8*4, 12.5f*4);
    
        // Posizione iniziale della ball (NON CAMBIATELE) *alla fine le ho cambiate io
        float startX = 300;
        float startY = 100;
    
        imageBallLanciata.setPosition(startX, startY);
    
        stage.addActor(imageBallLanciata);
    
        // Durata del movimento della ball
        float duration = 1.5f;
    
        // Animazione di spostamento lungo una traiettoria curva
        Timer.schedule(new Timer.Task() {
            float elapsed = 0;
    
            @Override
            public void run() {
                if (elapsed <= duration) {
                    // Calcola la posizione sulla traiettoria curva
                    float percent = elapsed / duration;
                    imageBallLanciata.setPosition((820) * percent, startY +(330)* percent); 
                    elapsed += 0.05f;
                } 
                else {
                    // Cambia l'immagine della ball a [0]
                    imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[0]));
    
                    // Nasconde l'immagine del Pokémon
                    pokemonImageBot.setVisible(false);
    
                    float bounceHeight = 30f; // Altezza del rimbalzo
                    float bounceDuration = 0.25f; // Durata di ogni rimbalzo

                    
                    // Esegue l'animazione di rimbalzo dopo un breve ritardo
                    imageBallLanciata.addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[1]));
                            }
                        }),
                        
                        Actions.sequence(
                            Actions.moveBy(0, -bounceHeight - 5f, bounceDuration), // cade a terra
                            Actions.moveBy(0, bounceHeight, bounceDuration), // Primo rimbalzo
                            Actions.moveBy(0, -bounceHeight, bounceDuration),
                            Actions.moveBy(0, bounceHeight / 2, bounceDuration/2), // Secondo rimbalzo
                            Actions.moveBy(0, -bounceHeight / 2, bounceDuration/2),
                            Actions.moveBy(0, bounceHeight / 4, bounceDuration/4), // Terzo rimbalzo
                            Actions.moveBy(0, -bounceHeight / 4, bounceDuration/4),
                            Actions.delay(0.5f) // Attende 0.5 secondi
                        ),
                        // Controllo del numero di vibrazioni
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                if (nVibrazioni == 0) {
                                    Actions.delay(0.5f);
                                    // Se nVibrazioni è 0, esegue l'animazione come richiesto
                                    imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[0]));
                                    // Solita animazione
                                    Actions.delay(0.5f);
                                    Actions.removeActor();
                                    String discorso20= "Oh No! Il pokemon selvatico si e' liberato!";
                                    labelDiscorsi20 = new LabelDiscorsi(discorso20,dimMax,0,true);
                                    label20=labelDiscorsi20.getLabel();
                                    stage.addActor(label20);

                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            utilizzoMossaBot(false,null);
                                            labelDiscorsi20.reset();
                                            label20.remove();
                                            label20=null;
                                        }
                                    }, 2.5f);

                                } else if (nVibrazioni == 1) {
                                    // Se nVibrazioni è 1, passa sui frame 7 e 8 e torna a 0 dopo 0.5 secondi
                                    imageBallLanciata.addAction(Actions.sequence(
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[0]));
                                                pokemonImageBot.setVisible(true);
                                                String discorso20= "Oh No! Il pokemon selvatico si e' liberato!";
                                                labelDiscorsi20 = new LabelDiscorsi(discorso20,dimMax,0,true);
                                                label20=labelDiscorsi20.getLabel();
                                                stage.addActor(label20);

                                                Timer.schedule(new Timer.Task() {
                                                    @Override
                                                    public void run() {
                                                        labelDiscorsi20.reset();
                                                        label20.remove();
                                                        label20=null;
                                                        utilizzoMossaBot(false, null);
                                                    }
                                                }, 2.5f);
                                            }
                                        }),
                                        // Solita animazione
                                        Actions.delay(0.5f),
                                        Actions.removeActor()

                                    ));
                                }

                                else if (nVibrazioni == 2) {
                                    // Se nVibrazioni è 2, esegue l'animazione specifica
                                    imageBallLanciata.addAction(Actions.sequence(
                                        // Passa su 7 e 8
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        Actions.delay(0.5f),
                                        // Passa su 9 e 8
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[9]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        // Ritorna all'animazione normale
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[0]));
                                                pokemonImageBot.setVisible(true);
                                                String discorso20= "Oh No! Il pokemon selvatico si e' liberato!";
                                                labelDiscorsi20 = new LabelDiscorsi(discorso20,dimMax,0,true);
                                                label20=labelDiscorsi20.getLabel();
                                                stage.addActor(label20);

                                                Timer.schedule(new Timer.Task() {
                                                    @Override
                                                    public void run() {
                                                        labelDiscorsi20.reset();
                                                        label20.remove();
                                                        label20=null; 
                                                        utilizzoMossaBot(false, null);
                                                    }
                                                }, 2.5f);
                                            }
                                        }),
                                        // Solita animazione
                                        Actions.delay(0.5f),
                                        Actions.removeActor()

                                    ));
                                }

                                else if (nVibrazioni == 3) {
                                    // Se nVibrazioni è 3, esegue l'animazione specifica
                                    imageBallLanciata.addAction(Actions.sequence(
                                        // Passa su 7 e 8
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        Actions.delay(0.5f),
                                        // Passa su 9 e 8
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[9]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        Actions.delay(0.5f),
                                        // Passa su 7 e 8 di nuovo
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        // Torna a 0 dopo 0.5 secondi
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[0]));
                                                pokemonImageBot.setVisible(true);
                                                String discorso20= "Oh No! Il pokemon selvatico si e' liberato!";
                                                labelDiscorsi20 = new LabelDiscorsi(discorso20,dimMax,0,true);
                                                label20=labelDiscorsi20.getLabel();
                                                stage.addActor(label20);

                                                Timer.schedule(new Timer.Task() {
                                                    @Override
                                                    public void run() {
                                                        labelDiscorsi20.reset();
                                                        label20.remove();
                                                        label20=null;
                                                        utilizzoMossaBot(false, null);
                                                    }
                                                }, 2.5f);
                                            }
                                        }),
                                
                                        // Solita animazione
                                        Actions.delay(0.5f),
                                        Actions.removeActor()
                                    ));
                                }

                                else if (nVibrazioni == 4) {
                                    // Se nVibrazioni è 4, esegue l'animazione specifica
                                    imageBallLanciata.addAction(Actions.sequence(
                                        // Passa su 7 e 8
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        Actions.delay(0.5f),

                                        // Passa su 9 e 8
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[9]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),

                                        Actions.delay(0.5f),
                                        // Passa su 7 e 8
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[7]));
                                            }
                                        }),
                                        Actions.delay(0.25f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[8]));
                                            }
                                        }),
                                
                                        Actions.delay(0.5f),
                                        
                                        Actions.sequence(
                                            Actions.delay(0.25f),
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[10]));
                                                }
                                            }),
                                            Actions.delay(0.03f),
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imageBallLanciata.setDrawable(new TextureRegionDrawable(ballLanciata[11]));
                                                    String discorso21= "Hai catturato "+ nameBot+"!";
                                                    labelDiscorsi21 = new LabelDiscorsi(discorso21,dimMax,0,true);
                                                    label21=labelDiscorsi21.getLabel();
                                                    stage.addActor(label21);
                                                    salvaPokemonNelBox();

                                                    Timer.schedule(new Timer.Task() {
                                                        @Override
                                                        public void run() {
                                                            labelDiscorsi21.reset();
                                                            if (label21!=null)
                                                                label21.remove();                                              
                                                            label21=null;
                                                            isBattleEnded=true;
                                                            dispose();
                                                            Erba.estratto=0;
                                                        }
                                                    }, 2.5f);
                                                }
                                            })
                                        )
                                        
                                    ));
                                }
                            }
                        })

                    ));
                    
    
                    cancel(); 
                }
            }
        }, 0, Gdx.graphics.getDeltaTime());

    }

    private void salvaPokemonNelBox(){
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        int i = 1;
        while (json.has(Integer.toString(i))) {
            i++;
        }

        JsonValue newPokemon = new JsonValue(JsonValue.ValueType.object);
        newPokemon.addChild("nomePokemon", new JsonValue(nameBot));
        newPokemon.addChild("livello", new JsonValue(LVPokeBot));

        JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
        statistiche.addChild("hp", new JsonValue(currentPokeHPBot));
        statistiche.addChild("hpTot", new JsonValue(maxPokeHPBot));
        statistiche.addChild("attack", new JsonValue(statsBot.get(0)));
        statistiche.addChild("defense", new JsonValue(statsBot.get(1)));
        statistiche.addChild("special_attack", new JsonValue(statsBot.get(2)));
        statistiche.addChild("special_defense", new JsonValue(statsBot.get(3)));
        statistiche.addChild("speed", new JsonValue(statsBot.get(4)));
        newPokemon.addChild("statistiche", statistiche);
        

        JsonValue mosseJson = new JsonValue(JsonValue.ValueType.array);
        for (Mossa mossaBot : listaMosseBot) {
            JsonValue mossa = new JsonValue(JsonValue.ValueType.object);
            mossa.addChild("nome", new JsonValue(mossaBot.getNome()));
            mossa.addChild("tipo", new JsonValue(mossaBot.getTipo()));
            mossa.addChild("ppTot", new JsonValue(mossaBot.getmaxPP()));
            mossa.addChild("ppAtt", new JsonValue(mossaBot.getattPP()));
            mosseJson.addChild(mossa);
        }
        newPokemon.addChild("mosse", mosseJson);

        newPokemon.addChild("tipoBall", new JsonValue(nomeBall));
        newPokemon.addChild("x", new JsonValue(x));

        json.addChild(Integer.toString(i), newPokemon);


        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

    }


    public void modificaContPerText(){
        counterForNextMove=2;
    }

    public void modificaPPMossePoke(int numero, ArrayList<Mossa> listaMossePoke) {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
    
        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        JsonValue pokeJson = json.get("poke" + numero);
    
        // Ottieni l'oggetto "statistiche" all'interno del Pokémon
        JsonValue mosse = pokeJson.get("mosse");

        for (Mossa mossa : listaMossePoke) {
            // Ottieni il nome della mossa per cercarla nel JSON
            String nomeMossa = mossa.getNome(); // Suppongo che tu abbia un metodo per ottenere il nome della mossa
    
            // Cerca l'oggetto JSON corrispondente alla mossa per aggiornare attPP
            for (JsonValue mossaJson : mosse) {
                if (mossaJson.getString("nome").equals(nomeMossa)) {
                    mossaJson.remove("ppAtt"); // Ottieni e aggiorna attPP
                    mossaJson.addChild("ppAtt", new JsonValue(mossa.getattPP())); // Aggiorna attPP con il nuovo valore
                    break; // Esci dal ciclo una volta trovato e aggiornato l'attPP
                }
            }
        }

        // Scrivi il JSON aggiornato nel file mantenendo la formattazione
        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        
    }

} //Fine battaglia :)