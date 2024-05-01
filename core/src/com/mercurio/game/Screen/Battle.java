package com.mercurio.game.Screen;

import java.util.ArrayList;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;

public class Battle extends ScreenAdapter {

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
    private boolean isBotFight = true;
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
    private TextureRegion[] ballBot;
    private TextureRegion ball2;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private float stateTime;
    private TextureRegion frame;
    private Image imagePlayer;
    private Image imageBall;
    private Image imageBallBot;
    private Image imageBall2;
    private Animation<TextureRegion> muoviPlayer;
    private Animation<TextureRegion> muoviBall;
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
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    private Label label6;
    private Label label7;
    private Label label8;
    private Label label9;
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
    private String soldiPresi;
    private int dimMax;
    private String nomeBot;
    private ArrayList<Mossa> listaMosse = new ArrayList<>();
    private ArrayList<MossaBot> listaMosseBot = new ArrayList<>();
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
    private MenuLabel chiamante;

    public Battle(MenuLabel chiamante) {

        this.chiamante = chiamante;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);
        dimMax=200;
        leggiPoke(1);
        leggiBot(1);
        leggiPokeBot(1,1);
        ballTexture = new Texture("battle/"+nomeBall+"Player.png");

        String discorso1= "Parte la sfida di "+nomeBot+" ("+tipoBot+")"+"!";
        labelDiscorsi1 = new LabelDiscorsi(discorso1,dimMax,0,true);
        
        String discorso2= "Vai "+ nomePoke + "!";
        labelDiscorsi2 = new LabelDiscorsi(discorso2,dimMax,0,true);

        String discorso3= "Hai sconfitto "+ nomeBot+"!";
        labelDiscorsi3 = new LabelDiscorsi(discorso3,dimMax,0,true);

        String discorso5= "E' superefficace!";
        labelDiscorsi5 = new LabelDiscorsi(discorso5,dimMax,0,true);

        String discorso6= "Non e' molto efficace...!";
        labelDiscorsi6 = new LabelDiscorsi(discorso6,dimMax,0,true);

        String discorso7= "Non ha effetto!";
        labelDiscorsi7 = new LabelDiscorsi(discorso7,dimMax,0,true);

        String discorso8= "Hai guadagnato "+ soldiPresi+".";
        labelDiscorsi8 = new LabelDiscorsi(discorso8,dimMax,0,true);

        String discorso9= "Brutto colpo!";
        labelDiscorsi9 = new LabelDiscorsi(discorso9,dimMax,0,true);
        
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

        Texture botTexture = new Texture("bots/bot1.png");
        botImage = new Image(botTexture);
        botImage.setPosition(labelBaseU.getX()+68, labelBaseU.getY());
        botImage.setSize(320, 320);
        stage.addActor(botImage);
        
        labelDiscorsi1.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di quello degli altri attori
        label1=labelDiscorsi1.getLabel();
        stage.addActor(label1);

        }


        @Override
        public void dispose() {
            if (!isBotFight){
            batch.dispose();
            font.dispose();
            stage.dispose();
            textureLancio.dispose();
            ballTexture.dispose();
            Gdx.input.setInputProcessor(null);
            chiamante.closeBattle();
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
            botImage.setPosition(labelBaseU.getX()+40, labelBaseU.getY()+70);
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
                                botImage.remove();
                            }
                        }, 2f);
                    }
                }
                
                

                if(imagePlayer!=null)
                    imagePlayer.setPosition(newX, imagePlayer.getY());

                if(botImage!=null)
                    botImage.setPosition(newXBot, botImage.getY());    
                
            }
            if (lanciato==1){
                showBall(ballTexture);
            }
            if (label2!=null){
                labelDiscorsi2.renderDisc();
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
            if (label9!=null){
                labelDiscorsi9.renderDisc();
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

        ballBot = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            ballBot[i] = new TextureRegion(ballTextureBot, i * regionWidth, 0, regionWidth, regionHeight);
        }
    
        // Crea e aggiungi l'immagine della ball allo stage
        imageBallBot = new Image(ballBot[0]);
        imageBallBot.setSize(16*4, 25*4);

        muoviBallBot = new Animation<>(0.5f, ballBot);

        imageBall = new Image(ball[0]);
        imageBall.setSize(16*4, 25*4);

        muoviBall = new Animation<>(0.5f, ball);
    
        // Posizione iniziale della ball (NON CAMBIATELE)
        float startX = 1;
        float startY = 220;

        float startXBot = stage.getHeight();
        float startYBot = 800;
        
        imageBall.setPosition(0, startY);
        imageBallBot.setPosition(01000, 1000);

        stage.addActor(imageBall);
        stage.addActor(imageBallBot);

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
                    imageBall.setPosition((startX * percent * 175)+35, startY - (startY - 110)* percent * percent); 
                    imageBallBot.setPosition(startXBot - (startXBot * percent) + 800, startYBot - (startYBot - 330) * percent * percent * percent); // Utilizza una curva quadratica
                    elapsed += 0.1f;
                } else {
                    // Avvia l'animazione dei frame della ball
                    activateAnimation(imageBall, muoviBall);
                    activateAnimation(imageBallBot, muoviBallBot);
                    label1.remove();

                    label2=labelDiscorsi2.getLabel();
                    stage.addActor(label2);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            label2.remove();
                            createFightLabels();
                        }
                    }, 2f);
                    
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
                        squadra = new Squadra(stage,true);
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
                        borsa = new Borsa(stage,true);
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
                        dispose();
                    }
                }, 0.3f);
            }
        });
        


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
                HPbot=placeHpBar(botHPBar,100,16,currentPokeHPBot,maxPokeHPBot); 
            }
        }, 0.51f);


        Label labelNomePokemon = new Label(nomePoke, new Label.LabelStyle(font, null));
        labelNomePokemon.setPosition(playerHPBar.getX()+55,playerHPBar.getY()+50); // Posiziona la label accanto all'immagine della mossa
        labelNomePokemon.setFontScale(1f);
        stage.addActor(labelNomePokemon);
        labelNomeHPBars.add(labelNomePokemon);

        Action moveActionNomePoke = Actions.moveTo(1024-256+55, playerHPBar.getY()+50, 0.5f);
        // Applica l'azione all'immagine
        labelNomePokemon.addAction(moveActionNomePoke);


        Label labelNomePokemonBot = new Label(nomePokeBot, new Label.LabelStyle(font, null));
        labelNomePokemonBot.setPosition(-300,botHPBar.getY()+27); // Posiziona la label accanto all'immagine della mossa
        labelNomePokemonBot.setFontScale(1f);
        stage.addActor(labelNomePokemonBot);
        labelNomeHPBars.add(labelNomePokemonBot);

        Action moveActionNomePokeBot = Actions.moveTo(25, botHPBar.getY()+27, 0.5f);
        // Applica l'azione all'immagine
        labelNomePokemonBot.addAction(moveActionNomePokeBot);


        Label labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
        labelLV.setPosition(labelNomePokemon.getX(),labelNomePokemon.getY()); // Posiziona la label accanto all'immagine della mossa
        labelLV.setFontScale(1f);
        stage.addActor(labelLV);
        labelNomeHPBars.add(labelLV);

        Action moveActionLV= Actions.moveTo(1024-256+210, labelNomePokemon.getY(), 0.5f);
        // Applica l'azione all'immagine
        labelLV.addAction(moveActionLV);


        Label labelLVBot = new Label(LVPokeBot, new Label.LabelStyle(font, null));
        labelLVBot.setPosition(labelNomePokemonBot.getX(),labelNomePokemonBot.getY()); // Posiziona la label accanto all'immagine della mossa
        labelLVBot.setFontScale(1f);
        stage.addActor(labelLVBot);
        labelNomeHPBars.add(labelLVBot);

        Action moveActionLVBot = Actions.moveTo(170,labelNomePokemonBot.getY(), 0.5f);
        // Applica l'azione all'immagine
        labelLVBot.addAction(moveActionLVBot);

        Label labelHP= new Label(currentPokeHP, new Label.LabelStyle(font, null));
        labelHP.setPosition(1024,149); // Posiziona la label accanto all'immagine della mossa
        labelHP.setFontScale(0.8f);
        stage.addActor(labelHP);
        labelNomeHPBars.add(labelHP);

        Action moveActionHP= Actions.moveTo(1024-110,149, 0.5f);
        // Applica l'azione all'immagine
        labelHP.addAction(moveActionHP);


        Label labelHPTot= new Label(maxPokeHP, new Label.LabelStyle(font, null));
        labelHPTot.setPosition(1024,149); // Posiziona la label accanto all'immagine della mossa
        labelHPTot.setFontScale(0.8f);
        stage.addActor(labelHPTot);
        labelNomeHPBars.add(labelHPTot);

        Action moveActionHPTot= Actions.moveTo(1024-55,149, 0.5f);
        // Applica l'azione all'immagine
        labelHPTot.addAction(moveActionHPTot);

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
        for (int i=0;i<4;i++){
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

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                int danno;
                if (tipologiaMossa.equals("fisico")){
                    danno=listaMosse.get(X).calcolaDanno(statsPlayer.get(2),statsBot.get(3),Integer.parseInt(LVPoke),nomePoke,nomePokeBot);
                }
                else{
                    danno=listaMosse.get(X).calcolaDanno(statsPlayer.get(4),statsBot.get(5),Integer.parseInt(LVPoke),nomePoke,nomePokeBot);
                }   

                currentPokeHPBot= Integer.toString((Integer.parseInt(currentPokeHPBot))-danno);

                if(Integer.parseInt(currentPokeHPBot)<0){
                    currentPokeHPBot= Integer.toString(0);
                }


                if (danno!=0){
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        updateHpBarWidth(HPbot, currentPokeHPBot, maxPokeHPBot);
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
                    }
                }, 2.5f);
            }
        };
        labelMosse.addListener(listener);
        labelNomeMossa.addListener(listener);
        labelPPTot.addListener(listener);
        labelPPatt.addListener(listener);
        }
    }

    private void sistemaLabel4(String nome){
        this.nomeMossa=nome;
        String discorso4= nomePoke + " utilizza " + nomeMossa+"!";
        labelDiscorsi4 = new LabelDiscorsi(discorso4,dimMax,0,true);
    }
    

    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
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
                            showPokemon(labelBaseD, nomePoke);
                            if (isBotFight){
                                showPokemon(labelBaseU, nomePokeBot);        
                            }                   
                        }
                    }, 0.5f);
                    this.cancel();
                }
            }
        }, 0.3f);
    }

    private void showPokemon(Image baseImage, String nome) {
        Texture pokemonTexture = new Texture("pokemon/"+nome+".png");
        int frameWidth = pokemonTexture.getWidth() / 4;
        int frameHeight = pokemonTexture.getHeight();
    
        TextureRegion[] pokemonFrames;
        Animation<TextureRegion> pokemonAnimation;
        Image pokemonImage;
    
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
        } else if (baseImage == labelBaseU) {
            pokemonFrames = new TextureRegion[2];
            for (int i = 0; i < 2; i++) {
                pokemonFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
            }
            pokemonAnimation = new Animation<>(0.4f, pokemonFrames);
    
            pokemonImage = new Image(pokemonFrames[0]);
            pokemonImage.setSize(frameWidth * 3, frameHeight * 3);
            pokemonImage.setPosition(labelBaseU.getX()+60, labelBaseU.getY()+20);
        } else {
            // Gestisci altri casi se necessario
            return; // Esci dalla funzione in caso di baseImage non gestito
        }
    
        stage.addActor(pokemonImage);

        final Animation<TextureRegion> finalPokemonAnimation = pokemonAnimation; // Copia final dell'animazione
    
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                float stateTime = 0f;
                TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                pokemonImage.setDrawable(new TextureRegionDrawable(frame));
    
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        float stateTime = 1f;
                        TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                        pokemonImage.setDrawable(new TextureRegionDrawable(frame));
    
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                float stateTime = 0f;
                                TextureRegion frame = finalPokemonAnimation.getKeyFrame(stateTime, false);
                                pokemonImage.setDrawable(new TextureRegionDrawable(frame));
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
            for (JsonValue stat : statistiche) {
                statsPlayer.add(stat.asInt());
            }

            maxPokeHP = statistiche.getString("hpTot");
            currentPokeHP = statistiche.getString("hp");
            JsonValue mosse = pokeJson.get("mosse");
            nomeBall = pokeJson.getString("tipoBall");
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                String attPP = mossaJson.getString("ppAtt");
                String maxPP = mossaJson.getString("ppTot");
                
                // Aggiungi la mossa alla lista
                Mossa mossa=new Mossa(nomeMossa, tipoMossa, maxPP, attPP, this); //gli passo Battle stesso con "this" per poter chiamare anche i metodi di Battle da Mossa
                listaMosse.add(mossa);
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

    }

    private void posizionaLabelSquadra(){

        labelPokePiazzate=true;
 
        Texture arrowPlayer = new Texture("battle/playerArrow.png");
        playerArrow = new Image(arrowPlayer);
        //playerArrow.setPosition(1024-250,270);
        playerArrow.setPosition(1024,270); //inizialmente fuori dallo schermo
        playerArrow.setSize(250, 26);
        stage.addActor(playerArrow);

        Texture arrowBot = new Texture("battle/botArrow.png");
        botArrow = new Image(arrowBot);
        //botArrow.setPosition(0,650);
        botArrow.setPosition(-250,650); //inizialmente fuori dallo schermo
        botArrow.setSize(250, 26);
        stage.addActor(botArrow);

        // Creazione delle azioni per lo spostamento delle frecce
        Action playerArrowAction = Actions.moveTo(1024-250, 270, 0.5f); // duration è la durata dell'animazione in secondi
        Action botArrowAction = Actions.moveTo(0, 650, 0.5f);

        // Applicazione delle azioni alle frecce
        playerArrow.addAction(playerArrowAction);
        botArrow.addAction(botArrowAction);

        piazzaSquad();
        piazzaSquadBot();
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



    private void leggiBot(int numero){
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        JsonValue pokeJson = json.get("bot"+numero);
        nomeBot = pokeJson.getString("nome");
        tipoBot = pokeJson.getString("tipo");
        soldiPresi = pokeJson.getString("soldi");

    }

    private void leggiPokeBot(int numero, int numeroPoke) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get("bot"+numero).get("poke"+numeroPoke);
            nomePokeBot = pokeJson.getString("nomePokemon");
            LVPokeBot = pokeJson.getString("livello");

            JsonValue statistiche = pokeJson.get("statistiche"); 
            for (JsonValue stat : statistiche) {
                statsBot.add(stat.asInt());
            }
            maxPokeHPBot = statistiche.getString("hpTot");
            currentPokeHPBot = pokeJson.get("statistiche").getString("HP");

            JsonValue mosse = pokeJson.get("mosse");
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                // Aggiungi la mossa alla lista
                MossaBot mossa=new MossaBot(nomeMossa, tipoMossa);
                listaMosseBot.add(mossa);
            }

    }

    public void leggiPokeSecondarioBot(int numero, int numBot) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("bots/bots.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get("bot"+ numBot).get("poke"+numero);
        nomePokeSquadBot = pokeJson.getString("nomePokemon");
        currentPokeHPBotSquad = pokeJson.get("statistiche").getString("HP");

    }


    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP){
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

        return hpBar;
    }


    // Metodo per aggiornare la larghezza della barra degli HP con un'animazione
    private void updateHpBarWidth(Image hpBar, String currentHP, String maxHP) {

        float percentualeHP = Float.parseFloat(currentHP)  / Float.parseFloat(maxHP);
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
    }

    public void piazzaLabel5(){
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
                    }
                }, 1.2f);
            }
        }, 2f);
    }
    }

    public void piazzaLabel6(){
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
                    }
                }, 1.5f);
            }
        }, 2f);
    }
    }


    public void piazzaLabel7(){
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
                }
            }, 1.2f);
        }
    }, 2f+delay);
    }
    
}
