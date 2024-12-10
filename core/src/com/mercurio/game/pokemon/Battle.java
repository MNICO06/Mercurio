package com.mercurio.game.pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.Assets;
import com.mercurio.game.Screen.Erba;
import com.mercurio.game.Screen.InterfacciaComune;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.effects.mosse;
import com.mercurio.game.effects.mosse.FrameData;
import com.mercurio.game.menu.Borsa;
import com.mercurio.game.menu.BorsaModifier;
import com.mercurio.game.menu.MenuLabel;
import com.mercurio.game.menu.Squadra;

public class Battle extends ScreenAdapter {

    private int checkPerDoppioPoke = 0;
    private int checkPerDoppiaBarra = 0;
    private Image nextMove;
    private boolean globalOtherAttack;
    private Image backImage;
    private boolean nextMoveBot;
    private int counterForNextMove = 0;
    private BorsaModifier borsaModifier = new BorsaModifier();
    private int danno;
    private int dannoBot;
    private final Object lock = new Object(); // Dichiarazione di un oggetto di blocco
    private boolean isBattleEnded = false;
    private Label labelNomePokemonBot;
    private Label labelLVBot;
    private float delaySecondText = 0;
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
    private Texture ballTexture;
    private Texture ballTextureBot; // questo lo si prende dal json
    private boolean isInNext = true;
    private boolean lanciato = false;
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
    private LabelDiscorsi labelDiscorsi22;
    private LabelDiscorsi labelDiscorsi23;
    private LabelDiscorsi labelDiscorsi24;
    private LabelDiscorsi labelDiscorsi25;
    private LabelDiscorsi labelDiscorsi26;
    private LabelDiscorsi labelDiscorsi27;
    private LabelDiscorsi labelDiscorsi28;
    private LabelDiscorsi labelDiscorsi29;
    private LabelDiscorsi labelDiscorsiCura;
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
    private Label label22;
    private Label label23;
    private Label label24;
    private Label label25;
    private Label label26;
    private Label label27;
    private Label label28;
    private Label label29;
    private Label labelCura;
    private String nomePoke;
    private String nomePokeSquad;
    private String currentPokeHP;
    private String currentPokeHPforSquad;
    private String maxPokeHP;
    private String maxPokeHPBot;
    private String nomePokeSquadBot;
    private String currentPokeHPBot;
    private int currentPokeHPBotSquad;
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
    private int SelvaticoLVPokeBotMin;
    private int SelvaticoLVPokeBotMax;
    private String LVPoke;
    private ArrayList<Integer> statsPlayer = new ArrayList<>();
    private ArrayList<Integer> statsBot = new ArrayList<>();
    private Image HPplayer;
    private Image expPlayer;
    private Image HPbot;
    private boolean checkLabel5 = true;
    private boolean checkLabel6 = true;
    private InterfacciaComune chiamante;
    private int numeroIndexPokeBot;
    private int numeroIndexPoke = 1;
    private String nameBot;
    private Battle battle = this;
    private Label labelNomePokemon;
    private Label labelLV;
    private Label labelHP;
    private Label labelHPTot;
    private boolean switched = false;
    private boolean switchForzato = false;
    private boolean sconfitta = false;
    private String zona;
    private int denaroPerso;
    private int levelMax = 0;
    private String levelLastPokeBot;
    private Semaphore semaphore;
    private String pokeHPbeforeFight;
    private float tassoCattura;
    private String nomeSelvatico;
    private float x = 0;
    private ArrayList<Integer> pokeInBattaglia = new ArrayList<>();
    private ArrayList<Integer> pokeInBattagliaLU = new ArrayList<>();
    private ArrayList<Integer> pokeInBattagliaNLevelUp = new ArrayList<>();
    private int ritardoLvUp = 0;
    private JsonValue pokeIvBot;
    private ArrayList<Integer> hpBotSquad = new ArrayList<>();
    private int nuovaEsperienza;
    ApprendimentoMosse apprendimentoMosse;
    private ArrayList<Integer> timerCreated = new ArrayList<>();
    private ArrayList<Float> timerCreatedDelay = new ArrayList<>();
    private ArrayList<timerData> timerCreatedData = new ArrayList<>();
    private boolean checkNextLV;
    private boolean continueLVOperations = true;
    private int numberOfLVtoUp;
    private mosse mosse;
    TextureRegion[] frames;
    ArrayList<FrameData[]> frameDataList;
    private String tipologia;
    private boolean checkPerEvo = false;
    private ArrayList<String> pokeEvo = new ArrayList<>();
    private ArrayList<Integer> pokeEvoIndex = new ArrayList<>();
    private Timer.Task lanciatoTask;

    public GameAsset asset;

    public Battle(InterfacciaComune chiamante, String nameBot, boolean isBotFight, String zona, String nomeSelvatico) {

        try {

            MenuLabel.openMenuLabel.setVisible(false);
            semaphore = new Semaphore(1); // Semaforo binario
            this.nameBot = nameBot;
            this.nomeSelvatico = nomeSelvatico;
            this.isBotFight = isBotFight;
            this.zona = zona;
            checkInt = 0;
            numeroIndexPokeBot = 1;
            this.chiamante = chiamante;
            this.asset = chiamante.getGameAsset();
            batch = new SpriteBatch();
            stage = new Stage();
            font = new BitmapFont(Gdx.files.local("assets/font/font.fnt"));
            ballTextureBot = asset.getBattle(Assets.BALL_PLAYER);
            Gdx.input.setInputProcessor(stage);
            dimMax = 200;
            leggiPoke(numeroIndexPoke);
            if (!pokeInBattaglia.contains(numeroIndexPoke)) {
                pokeInBattaglia.add(numeroIndexPoke);
            } else {
                pokeInBattaglia.remove(Integer.valueOf(numeroIndexPoke));
                pokeInBattaglia.add(numeroIndexPoke);
            }
            if (isBotFight) {
                leggiBot(nameBot);
                leggiPokeBot(nameBot, numeroIndexPokeBot);
            }
            ballTexture = asset.getBattle(Assets.BALL_PLAYER);

            String discorso1 = "Parte la sfida di " + nomeBot + " (" + tipoBot + ")" + "!";
            labelDiscorsi1 = new LabelDiscorsi(discorso1, dimMax, 0, true, false);

            String discorso2 = "Vai " + nomePoke + "!";
            labelDiscorsi2 = new LabelDiscorsi(discorso2, dimMax, 0, true, false);

            String discorso3 = "Hai sconfitto " + nomeBot + " (" + tipoBot + ")" + "!";
            labelDiscorsi3 = new LabelDiscorsi(discorso3, dimMax, 0, true, false);

            String discorso5 = "E' superefficace!";
            labelDiscorsi5 = new LabelDiscorsi(discorso5, dimMax, 0, true, false);

            String discorso6 = "Non e' molto efficace...";
            labelDiscorsi6 = new LabelDiscorsi(discorso6, dimMax, 0, true, false);

            String discorso7 = "Non ha effetto!";
            labelDiscorsi7 = new LabelDiscorsi(discorso7, dimMax, 0, true, false);

            String discorso9 = "Brutto colpo!";
            labelDiscorsi9 = new LabelDiscorsi(discorso9, dimMax, 0, true, false);

            String discorso10 = "Non puoi sottrarti alla lotta!";
            labelDiscorsi10 = new LabelDiscorsi(discorso10, dimMax, 0, true, false);

            String discorso11 = "Scampato pericolo!";
            labelDiscorsi11 = new LabelDiscorsi(discorso11, dimMax, 0, true, false);

            String discorso15 = "E' apparso un " + nameBot + " selvatico!";
            labelDiscorsi15 = new LabelDiscorsi(discorso15, dimMax, 0, true, false);

            String discorso16 = "Non hai piu' Pokemon disponibili...";
            labelDiscorsi16 = new LabelDiscorsi(discorso16, dimMax, 0, true, false);

            String discorso18 = "Sei stato portato d'urgenza al Centro Pokémon!";
            labelDiscorsi18 = new LabelDiscorsi(discorso18, dimMax, 0, true, false);

            show();
        } catch (Exception e) {
            System.out.println("Errore costruttore battle, " + e);
        }

    }

    @Override
    public void show() {
        try {

            checkInt = 0;
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            targetPosXD = -200;
            initialPosXD = screenWidth;

            targetPosXU = screenWidth - 128 * 3 + 15;
            initialPosXU = 0;

            animationTime = 0f;

            int ballWidth = ballTexture.getWidth() / 3;
            int ballHeight = ballTexture.getHeight();

            ball2 = new TextureRegion(ballTexture, 0, 0, ballWidth, ballHeight);

            // Add background
            Texture backgroundTexture = asset.getBattle(Assets.SFONDO_BATTLE);
            Image background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
            stage.addActor(background);

            textureLancio = asset.getBattle(Assets.LANCIO_BALL);
            int regionHeight = textureLancio.getHeight();
            int regionWidth = textureLancio.getWidth() / 4;
            // Divide lo spritesheet in 4colonne
            player = new TextureRegion[4];
            for (int i = 0; i < 4; i++) {
                player[i] = new TextureRegion(textureLancio, i * regionWidth, 0, regionWidth, regionHeight);
            }

            muoviPlayer = new Animation<>(cambioFrame_speed, player);

            Texture imageBaseD = asset.getBattle(Assets.BASE_D);
            labelBaseD = new Image(imageBaseD);
            labelBaseD.setSize(256 * 3, 32 * 3);
            stage.addActor(labelBaseD);

            Texture imageBaseU = asset.getBattle(Assets.BASE_U);
            labelBaseU = new Image(imageBaseU);
            labelBaseU.setSize(128 * 3, 62 * 3);
            stage.addActor(labelBaseU);

            imageBall2 = new Image(ball2);
            imageBall2.setSize(16 * 4, 25 * 4);
            imageBall2.setPosition(-300, -300); // fuori dallo schermo che non si deve vedere
            stage.addActor(imageBall2);

            imagePlayer = new Image(player[0]);
            imagePlayer.setSize(66 * 4, 52 * 4);
            stage.addActor(imagePlayer);

            if (isBotFight) {

                Texture botTexture = new Texture("bots/" + tipoBot + ".png");
                botImage = new Image(botTexture);
                botImage.setPosition(labelBaseU.getX() + 68, labelBaseU.getY());
                botImage.setSize(320, 320);
                stage.addActor(botImage);

                labelDiscorsi1.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto
                                                          // di
                                                          // quello degli altri attori
                label1 = labelDiscorsi1.getLabel();
                stage.addActor(label1);
            } else {
                leggiPokeSelvatico(nameBot, zona);

                scopriPokemon(nameBot);

                Texture pokemonTexture = new Texture("pokemon/" + nameBot + ".png");
                int frameWidth = pokemonTexture.getWidth() / 4;
                int frameHeight = pokemonTexture.getHeight();

                TextureRegion[] pokeSelvFrames;

                pokeSelvFrames = new TextureRegion[2];
                for (int i = 0; i < 2; i++) {
                    pokeSelvFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
                }

                botImage = new Image(pokeSelvFrames[0]);
                botImage.setSize(frameWidth * 3, frameHeight * 3);
                botImage.setPosition(labelBaseU.getX() + 60, labelBaseU.getY() + 20);
                stage.addActor(botImage);

                labelDiscorsi15.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                           // alto di
                                                           // quello degli altri attori
                label15 = labelDiscorsi15.getLabel();
                stage.addActor(label15);
            }
        } catch (Exception e) {
            System.out.println("Errore show battle, " + e);
        }

    }

    @Override
    public void dispose() {
        if (!isBotFight && !isBattleEnded) {
            label11 = labelDiscorsi11.getLabel();
            stage.addActor(label11);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    label11.remove();
                    float timerTime = 0f;
                    if (checkPerEvo) {
                        timerTime += 22f;
                        for (int i = 0; i < pokeEvo.size(); i++) {
                            evoluzione(i);
                        }
                    }
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            batch.dispose();
                            font.dispose();
                            stage.dispose();
                            Gdx.input.setInputProcessor(null);
                            chiamante.closeBattle();
                            MenuLabel.openMenuLabel.setVisible(true);
                            Erba.estratto = 0;
                        }
                    }, timerTime);
                }
            }, 2f);
        } else if (isBattleEnded) {
            float timerTime = 0f;
            if (checkPerEvo) {
                timerTime += 22f;
                for (int i = 0; i < pokeEvo.size(); i++) {
                    evoluzione(i);
                }
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    batch.dispose();
                    font.dispose();
                    stage.dispose();
                    Gdx.input.setInputProcessor(null);
                    chiamante.closeBattle();
                    MenuLabel.openMenuLabel.setVisible(true);
                    Erba.estratto = 0;
                }
            }, timerTime);
        } else {
            label10 = labelDiscorsi10.getLabel();
            stage.addActor(label10);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi10.reset();
                    label10.remove();
                    label10 = null;
                }
            }, 2f);
        }

    }

    public void render() {
        try {

            animationTime += 0.045f;
            if (animationTime < animationDuration) {
                labelDiscorsi1.renderDisc();
                // Continua l'animazione della baseD e baseU
                float progress = animationTime / animationDuration;
                float newXD = MathUtils.lerp(initialPosXD, targetPosXD, progress);
                labelBaseD.setPosition(newXD, 125);
                float newXU = MathUtils.lerp(initialPosXU, targetPosXU, progress);
                labelBaseU.setPosition(newXU, 300);
                if (isBotFight) {
                    botImage.setPosition(labelBaseU.getX() + 40, labelBaseU.getY() + 70);
                } else {
                    botImage.setPosition(labelBaseU.getX() + 60, labelBaseU.getY() + 20);
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
                    if (!labelPokePiazzate) {
                        posizionaLabelSquadra();
                    }

                    // Dopo un secondo, sposta il player fino al bordo dello schermo
                    float newX = imagePlayer.getX() - 300 * Gdx.graphics.getDeltaTime(); // Spostamento di 300 pixel al
                                                                                         // secondo
                    float newXBot = botImage.getX() + 300 * Gdx.graphics.getDeltaTime();

                    if (isInNext) {
                        imageBall2.setPosition(newX + 10, 195);
                    } else {
                        imageBall2.setPosition(newX + 10, 220);
                    }

                    if (newX + imagePlayer.getWidth() < 120) {
                        // Una volta che il player è fuori dallo schermo, cambia l'animazione a
                        // player[2] e player[3]
                        imagePlayer.setDrawable(new TextureRegionDrawable(player[3]));
                        lanciato = true;
                        isInNext = true;
                        // Rimuovi il player dallo stage dopo l'ultima animazione
                    } else if (newX + imagePlayer.getWidth() > 120 && newX + imagePlayer.getWidth() < 270) {
                        imagePlayer.setDrawable(new TextureRegionDrawable(player[2]));
                        isInNext = false;
                        if (muoviPlayer.isAnimationFinished(3f)) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    imagePlayer.remove(); // Rimuovi lo sprite dallo stage
                                    if (isBotFight) {
                                        botImage.remove();
                                    }
                                }
                            }, 2f);
                        }
                    }

                    if (imagePlayer != null)
                        imagePlayer.setPosition(newX, imagePlayer.getY());

                    if (botImage != null && isBotFight)
                        botImage.setPosition(newXBot, botImage.getY());

                }

                if (lanciato) {
                    // Se un task precedente esiste, annullalo
                    if (lanciatoTask != null && !lanciatoTask.isScheduled()) {
                        lanciatoTask.cancel();
                    }
                    // Crea un nuovo task
                    lanciatoTask = new Timer.Task() {
                        @Override
                        public void run() {
                            showBall(ballTexture);
                            lanciato = false;

                            if (isBotFight) {
                                showBallBot();
                            } else {
                                checkPerDoppioPoke++;
                                showPokemon(labelBaseU, nameBot);
                            }
                        }
                    };

                    // Pianifica il nuovo task
                    Timer.schedule(lanciatoTask, 0.2f);
                }

                if (label2 != null) {
                    labelDiscorsi2.renderDisc();
                }
                if (label3 != null) {
                    labelDiscorsi3.renderDisc();
                }
                if (label4 != null) {
                    labelDiscorsi4.renderDisc();
                }
                if (label5 != null) {
                    labelDiscorsi5.renderDisc();
                }
                if (label6 != null) {
                    labelDiscorsi6.renderDisc();
                }
                if (label7 != null) {
                    labelDiscorsi7.renderDisc();
                }
                if (label8 != null) {
                    labelDiscorsi8.renderDisc();
                }
                if (label9 != null) {
                    labelDiscorsi9.renderDisc();
                }
                if (label10 != null) {
                    labelDiscorsi10.renderDisc();
                }
                if (label11 != null) {
                    labelDiscorsi11.renderDisc();
                }
                if (label12 != null) {
                    labelDiscorsi12.renderDisc();
                }
                if (label13 != null) {
                    labelDiscorsi13.renderDisc();
                }
                if (label14 != null) {
                    labelDiscorsi14.renderDisc();
                }
                if (label15 != null) {
                    labelDiscorsi15.renderDisc();
                }
                if (label16 != null) {
                    labelDiscorsi16.renderDisc();
                }
                if (label17 != null) {
                    labelDiscorsi17.renderDisc();
                }
                if (label18 != null) {
                    labelDiscorsi18.renderDisc();
                }
                if (label19 != null) {
                    labelDiscorsi19.renderDisc();
                }
                if (label20 != null) {
                    labelDiscorsi20.renderDisc();
                }
                if (label21 != null) {
                    labelDiscorsi21.renderDisc();
                }
                if (label22 != null) {
                    labelDiscorsi22.renderDisc();
                }
                if (label23 != null) {
                    labelDiscorsi23.renderDisc();
                }
                if (label24 != null) {
                    labelDiscorsi24.renderDisc();
                }
                if (label25 != null) {
                    labelDiscorsi25.renderDisc();
                }
                if (label26 != null) {
                    labelDiscorsi26.renderDisc();
                }
                if (label27 != null) {
                    labelDiscorsi27.renderDisc();
                }
                if (label28 != null) {
                    labelDiscorsi28.renderDisc();
                }
                if (label29 != null) {
                    labelDiscorsi29.renderDisc();
                }
                if (labelCura != null) {
                    labelDiscorsiCura.renderDisc();
                }
                if (apprendimentoMosse != null) {
                    apprendimentoMosse.render();
                }
                if (borsa != null) {
                    if (borsa.getSquadraCure() != null) {
                        borsa.render();
                    }
                }
            }

            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

            // Disegna la UI della borsa
            stage.draw(); // Disegna lo stage sullo SpriteBatch
        } catch (Exception e) {
            System.out.println("Errore render battle, " + e);
        }

    }

    private void showBall(Texture textureBall) {
        try {

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
            imageBall.setSize(16 * 4, 25 * 4);

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
                        imageBall.setPosition((startX * percent * 175) + 35,
                                startY - (startY - 110) * percent * percent);
                        elapsed += 0.1f;
                    } else {
                        // Avvia l'animazione dei frame della ball
                        activateAnimation(imageBall, muoviBall);
                        if (isBotFight) {
                            label1.remove();
                        } else {
                            label15.remove();
                        }

                        label2 = labelDiscorsi2.getLabel();
                        stage.addActor(label2);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                label2.remove();
                                if (!switched) {
                                    createFightLabels();
                                }
                            }
                        }, 2f);
                        this.cancel(); // Interrompi il Timer.Task

                    }
                }
            }, 0, Gdx.graphics.getDeltaTime());
        } catch (Exception e) {
            System.out.println("Errore showball battle, " + e);
        }

    }

    private void showBallBot() {
        try {

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
            imageBallBot.setSize(16 * 4, 25 * 4);

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
                        imageBallBot.setPosition(startXBot - (startXBot * percent) + 800,
                                startYBot - (startYBot - 330) * percent * percent * percent); // Utilizza una curva
                                                                                              // quadratica
                        elapsed += 0.1f;
                    } else {
                        // Avvia l'animazione dei frame della ball del bot
                        activateAnimation(imageBallBot, muoviBallBot);
                        this.cancel(); // Interrompi il Timer.Task
                    }
                }
            }, 0, Gdx.graphics.getDeltaTime());
        } catch (Exception e) {
            System.out.println("Errore showballBot battle, " + e);
        }

    }

    private void createFightLabels() {
        try {

            fightLabels = new TextureRegion[8];

            // Carica l'immagine contenente tutte le label
            Texture textBoxesImage = asset.getBattle(Assets.FIGHT_BOX);

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
                            squadra = new Squadra(stage, true, battle, null, false);
                            switchForzato = false;
                        }
                    }, 0.3f);
                }
            });

            Image label3 = new Image(fightLabels[4]);
            label3.setSize(256, 125);
            label3.setPosition(256 * 2, 0); // Posizione delle etichette sulla schermata
            stage.addActor(label3);

            label3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentIndex = 4; // Imposta l'indice corrente sulla base di quale etichetta è stata cliccata
                    updateLabelImage(label3); // Aggiorna l'immagine della label cliccata
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            borsa = new Borsa(stage, true, battle);
                        }
                    }, 0.3f);

                }
            });

            Image label4 = new Image(fightLabels[6]);
            label4.setSize(256, 125);
            label4.setPosition(3 * 256, 0); // Posizione delle etichette sulla schermata
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

            piazzaLabelLottaPlayer();
            piazzaLabelLottaPerBot();
        } catch (Exception e) {
            System.out.println("Errore createFightlables battle, " + e);
        }

    }

    private void piazzaLabelLottaPlayer() {
        try {

            // e ora piazza le hp Bar
            Texture imageHPPlayer = asset.getBattle(Assets.HP_BAR);
            playerHPBar = new Image(imageHPPlayer);
            playerHPBar.setSize(256, 47 * 2);
            playerHPBar.setPosition(1024, 140);
            stage.addActor(playerHPBar);

            Action moveActionPlayer = Actions.moveTo(1024 - 256, 140, 0.5f);
            // Applica l'azione all'immagine
            playerHPBar.addAction(moveActionPlayer);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    HPplayer = placeHpBar(playerHPBar, 144, 38, currentPokeHP, maxPokeHP);
                    expPlayer = placeExpBar(playerHPBar);
                }
            }, 0.51f);

            labelNomePokemon = new Label(nomePoke, new Label.LabelStyle(font, null));
            labelNomePokemon.setPosition(playerHPBar.getX() + 55, playerHPBar.getY() + 50); // Posiziona la label
                                                                                            // accanto
                                                                                            // all'immagine della mossa
            labelNomePokemon.setFontScale(1f);
            stage.addActor(labelNomePokemon);
            labelNomeHPBars.add(labelNomePokemon);

            Action moveActionNomePoke = Actions.moveTo(1024 - 256 + 55, playerHPBar.getY() + 50, 0.5f);
            // Applica l'azione all'immagine
            labelNomePokemon.addAction(moveActionNomePoke);

            labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
            labelLV.setPosition(labelNomePokemon.getX(), labelNomePokemon.getY()); // Posiziona la label accanto
                                                                                   // all'immagine della mossa
            labelLV.setFontScale(1f);
            stage.addActor(labelLV);
            labelNomeHPBars.add(labelLV);

            Action moveActionLV = Actions.moveTo(1024 - 256 + 210, labelNomePokemon.getY(), 0.5f);
            // Applica l'azione all'immagine
            labelLV.addAction(moveActionLV);

            labelHP = new Label(currentPokeHP, new Label.LabelStyle(font, null));
            labelHP.setPosition(1024, 149); // Posiziona la label accanto all'immagine della mossa
            labelHP.setFontScale(0.8f);
            stage.addActor(labelHP);
            labelNomeHPBars.add(labelHP);

            Action moveActionHP = Actions.moveTo(1024 - 110, 149, 0.5f);
            // Applica l'azione all'immagine
            labelHP.addAction(moveActionHP);

            labelHPTot = new Label(maxPokeHP, new Label.LabelStyle(font, null));
            labelHPTot.setPosition(1024, 149); // Posiziona la label accanto all'immagine della mossa
            labelHPTot.setFontScale(0.8f);
            stage.addActor(labelHPTot);
            labelNomeHPBars.add(labelHPTot);

            Action moveActionHPTot = Actions.moveTo(1024 - 55, 149, 0.5f);
            // Applica l'azione all'immagine
            labelHPTot.addAction(moveActionHPTot);
            if (switched && !switchForzato) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        utilizzoMossaBot(false, null);
                    }
                }, 2.5f);

            }
        } catch (Exception e) {
            System.out.println("Errore piazzaLabelLottaPlayer battle, " + e);
        }

    }

    private void piazzaLabelLottaPerBot() {
        try {

            // e ora piazza le hp Bar
            Texture imageHPBot = asset.getBattle(Assets.BOT_HP_BAR);
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
                    HPbot = placeHpBar(botHPBar, 100, 16, currentPokeHPBot, maxPokeHPBot);
                }
            }, 0.51f);

            labelNomePokemonBot = new Label(nomePokeBot, new Label.LabelStyle(font, null));
            labelNomePokemonBot.setPosition(-300, botHPBar.getY() + 27); // Posiziona la label accanto all'immagine
                                                                         // della
                                                                         // mossa
            labelNomePokemonBot.setFontScale(1f);
            stage.addActor(labelNomePokemonBot);
            labelNomeHPBars.add(labelNomePokemonBot);

            Action moveActionNomePokeBot = Actions.moveTo(25, botHPBar.getY() + 27, 0.5f);
            // Applica l'azione all'immagine
            labelNomePokemonBot.addAction(moveActionNomePokeBot);

            labelLVBot = new Label(LVPokeBot, new Label.LabelStyle(font, null));
            labelLVBot.setPosition(labelNomePokemonBot.getX(), labelNomePokemonBot.getY()); // Posiziona la label
                                                                                            // accanto
                                                                                            // all'immagine della mossa
            labelLVBot.setFontScale(1f);
            stage.addActor(labelLVBot);
            labelNomeHPBars.add(labelLVBot);

            Action moveActionLVBot = Actions.moveTo(170, labelNomePokemonBot.getY(), 0.5f);
            // Applica l'azione all'immagine
            labelLVBot.addAction(moveActionLVBot);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabelLottaPerBot battle, " + e);
        }

    }

    private void restorePreviousImage(Image label) {
        Drawable newDrawable = new TextureRegionDrawable(fightLabels[currentIndex]);
        label.setDrawable(newDrawable);
    }

    private void updateLabelImage(Image label) {
        try {

            Drawable newDrawable = new TextureRegionDrawable(fightLabels[currentIndex + 1]);
            label.setDrawable(newDrawable);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    restorePreviousImage(label); // Ripristina l'immagine precedente dopo 0.5 secondi
                }
            }, 0.3f);
        } catch (Exception e) {
            System.out.println("Errore updateLabelImage battle, " + e);
        }

    }

    private void piazzaMosse() {
        try {

            for (int i = 0; i < listaMosse.size(); i++) {
                Image labelMosse = new Image(listaMosse.get(i).getLabelTipo(listaMosse.get(i).getTipo()));
                labelMosse.setPosition(i * 256, 0);
                labelMosse.setSize(256, 125);
                stage.addActor(labelMosse);
                labelMosseArray.add(labelMosse);

                Label labelNomeMossa = new Label(listaMosse.get(i).getNome(), new Label.LabelStyle(font, null));
                labelNomeMossa.setPosition(labelMosse.getX() + 20, labelMosse.getY() + 68); // Posiziona la label
                                                                                            // accanto
                                                                                            // all'immagine della mossa
                labelNomeMossa.setFontScale(1.5f);
                stage.addActor(labelNomeMossa);
                labelNomeMosseArray.add(labelNomeMossa);

                Label labelPPTot = new Label(listaMosse.get(i).getmaxPP(), new Label.LabelStyle(font, null));
                labelPPTot.setPosition(labelMosse.getX() + 195, labelMosse.getY() + 30);
                labelPPTot.setFontScale(1.3f);
                stage.addActor(labelPPTot);
                labelNomeMosseArray.add(labelPPTot);

                Label labelPPatt = new Label(listaMosse.get(i).getattPP(), new Label.LabelStyle(font, null));
                if (Integer.parseInt(listaMosse.get(i).getattPP()) > 9) {
                    labelPPatt.setPosition(labelMosse.getX() + 145, labelMosse.getY() + 30);
                } else {
                    labelPPatt.setPosition(labelMosse.getX() + 158, labelMosse.getY() + 30);
                }
                labelPPatt.setFontScale(1.3f);
                stage.addActor(labelPPatt);
                labelNomeMosseArray.add(labelPPatt);

                final int indiceMossa = i;
                ClickListener listener = new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        backImage.remove();
                        if (statsPlayer.get(4) >= statsBot.get(4)) {
                            listaMosse.get(indiceMossa).setattPP();
                            utilizzoMossa(labelMosse, true);
                            modificaPPMossePoke(numeroIndexPoke, listaMosse);
                            /*
                             * if (Integer.parseInt(currentPokeHPBot)>0){
                             * Timer.schedule(new Timer.Task() {
                             * 
                             * @Override
                             * public void run() {
                             * utilizzoMossaBot();
                             * }
                             * }, 3.51f);
                             * 
                             * }
                             */
                        } else {
                            utilizzoMossaBot(true, labelMosse);
                            if (Integer.parseInt(currentPokeHP) > 0) {
                                listaMosse.get(indiceMossa).setattPP();
                                modificaPPMossePoke(numeroIndexPoke, listaMosse);
                            }
                            /*
                             * if (Integer.parseInt(currentPokeHP)>0){
                             * Timer.schedule(new Timer.Task() {
                             * 
                             * @Override
                             * public void run() {
                             * utilizzoMossa(labelMosse,false);
                             * }
                             * }, 3.51f);
                             * 
                             * }
                             */
                        }
                    }
                };
                if (Integer.parseInt(listaMosse.get(i).getattPP()) > 0) {
                    labelMosse.addListener(listener);
                    labelNomeMossa.addListener(listener);
                    labelPPTot.addListener(listener);
                    labelPPatt.addListener(listener);
                }
            }

            for (int j = 0; j < 4 - listaMosse.size(); j++) {
                Texture noMoveTexture = asset.getBattle(Assets.NO_MOVE);
                Image labelMosse = new Image(noMoveTexture);
                labelMosse.setPosition((j + listaMosse.size()) * 256, 0);
                labelMosse.setSize(256, 125);
                stage.addActor(labelMosse);
                labelMosseArray.add(labelMosse);
            }
            Texture backButton = asset.getBattle(Assets.B);
            backImage = new Image(backButton);
            backImage.setPosition(0, 130);
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
        } catch (Exception e) {
            System.out.println("Errore piazzaMosse battle, " + e);
        }

    }

    private void utilizzoMossa(Image labelMosse, boolean otherAttack) {
        try {

            nextMoveBot = true;
            nextMove = labelMosse;
            globalOtherAttack = otherAttack;
            counterForNextMove = 0;
            float trovaX = (labelMosse.getX()) / 256;
            int X = (int) trovaX;
            nomeMossa = listaMosse.get(X).getNome();
            sistemaLabel4(nomeMossa);
            labelDiscorsi4.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                      // quello degli altri attori
            label4 = labelDiscorsi4.getLabel();
            stage.addActor(label4);

            FileHandle mosseFile = Gdx.files.local("pokemon/mosse.json");
            String mosseJsonString = mosseFile.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON delle mosse
            JsonValue mosseJson = new JsonReader().parse(mosseJsonString);
            JsonValue tipoJson = mosseJson.get(nomeMossa);
            String tipologiaMossa = tipoJson.getString("attacco");

            if (tipologiaMossa.equals("fisico")) {
                danno = listaMosse.get(X).calcolaDanno(statsPlayer.get(0), statsBot.get(1), Integer.parseInt(LVPoke),
                        nomePoke, nomePokeBot);
            } else {
                danno = listaMosse.get(X).calcolaDanno(statsPlayer.get(2), statsBot.get(3), Integer.parseInt(LVPoke),
                        nomePoke, nomePokeBot);
            }

            currentPokeHPBot = Integer.toString((Integer.parseInt(currentPokeHPBot)) - danno);

            if (Integer.parseInt(currentPokeHPBot) <= 0) {
                currentPokeHPBot = Integer.toString(0);
            }

            // TODO: animazione (ferma quello sotto)
            mosse = new mosse(nomeMossa);
            frames = mosse.getSpriteMossa();
            frameDataList = mosse.getFrameDataList();
            tipologia = mosse.getTipologia();
            float speedAnimationMoves = mosse.getSpeed();

            // Crea un array di immagini della mossa
            Image[] imagesMossa = new Image[frameDataList.size()];

            // TODO: eventualmente capire anche il movimento del mio pokemon e di quello
            // avversario
            if (tipologia.equals("continua")) {

                // Inizializza ogni immagine della mossa
                for (int i = 0; i < frameDataList.size(); i++) {
                    FrameData[] currentFrameDataSet = frameDataList.get(i); // Ottieni il set di frame corrente
                    imagesMossa[i] = new Image(frames[currentFrameDataSet[0].getNumeroSprite()]); // Usa il primo sprite
                                                                                                  // del
                                                                                                  // set per
                                                                                                  // l'inizializzazione
                    imagesMossa[i].setSize(currentFrameDataSet[0].getAltezza(), currentFrameDataSet[0].getLarghezza()); // Imposta
                                                                                                                        // le
                                                                                                                        // dimensioni
                                                                                                                        // dell'immagine
                    imagesMossa[i].setPosition(currentFrameDataSet[0].getX(), currentFrameDataSet[0].getY()); // Posizione
                                                                                                              // iniziale
                    stage.addActor(imagesMossa[i]); // Aggiungi l'immagine al palco
                }

                // Timer per animare i frame della mossa
                Timer.schedule(new Timer.Task() {
                    int currentFrameIndex = 0; // Indice per tenere traccia del frame attuale

                    @Override
                    public void run() {
                        boolean allSetsFinished = true; // Verifica se tutti i set di frame sono stati completati

                        // Cicla attraverso tutti i set di frame contemporaneamente
                        for (int currentSetIndex = 0; currentSetIndex < frameDataList.size(); currentSetIndex++) {
                            FrameData[] currentFrameDataSet = frameDataList.get(currentSetIndex); // Ottieni il set di
                                                                                                  // frame
                                                                                                  // corrente

                            // Se non abbiamo raggiunto la fine del set corrente
                            if (currentFrameIndex < currentFrameDataSet.length) {
                                allSetsFinished = false; // Almeno un set non è ancora terminato

                                Image imageMossa = imagesMossa[currentSetIndex]; // Ottieni l'immagine corrispondente

                                // Verifica se la posizione X è diversa da -1 prima di renderizzare
                                if (currentFrameDataSet[currentFrameIndex].getX() != -1) {
                                    // Aggiorna la posizione, dimensione e drawable del frame corrente
                                    imageMossa.setVisible(true);
                                    imageMossa.setPosition(currentFrameDataSet[currentFrameIndex].getX(),
                                            currentFrameDataSet[currentFrameIndex].getY()); // Imposta la posizione
                                    imageMossa.setSize(currentFrameDataSet[currentFrameIndex].getAltezza(),
                                            currentFrameDataSet[currentFrameIndex].getLarghezza()); // Imposta la
                                                                                                    // dimensione
                                    imageMossa.setDrawable(new TextureRegionDrawable(
                                            frames[currentFrameDataSet[currentFrameIndex].getNumeroSprite()])); // Imposta
                                                                                                                // il
                                                                                                                // nuovo
                                                                                                                // sprite
                                    imageMossa.setColor(1, 1, 1, currentFrameDataSet[currentFrameIndex].getOpacity());
                                } else {
                                    // Nascondi lo sprite se la posizione X è -1
                                    imagesMossa[currentSetIndex].setVisible(false);
                                }
                            }
                        }

                        // Passa al frame successivo
                        currentFrameIndex++;

                        // Se tutti i set di frame sono stati completati, esegui il codice finale
                        if (allSetsFinished) {

                            if (danno != 0) {
                                if (isBotFight) {
                                    modificaHPPokeBot(numeroIndexPokeBot, currentPokeHPBot);
                                }

                                updateHpBarWidth(HPbot, currentPokeHPBot, maxPokeHPBot, pokemonImageBot, nomePokeBot);

                            }

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {

                                    for (Image imageMossa : imagesMossa) {
                                        imageMossa.remove(); // Rimuove l'immagine dalla scena
                                    }

                                }
                            }, 0.5f);

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

                                    // Rimuove tutte le immagini della mossa
                                    for (Image imageMossa : imagesMossa) {
                                        imageMossa.remove(); // Rimuove l'immagine dalla scena
                                    }

                                    if (otherAttack == true) {
                                        if (counterForNextMove == 0) {
                                            if (Integer.parseInt(currentPokeHPBot) > 0) {
                                                utilizzoMossaBot(false, null);
                                            }
                                        }
                                    }
                                }
                            }, 2.5f);
                            this.cancel(); // Ferma il timer
                        }
                    }
                }, 0, speedAnimationMoves);
            } else if (tipologia.equals("istantanea")) {

                // con questo timer comincia prima a dire la mossa e poi va a farla renderizzare
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Codice per l'animazione istantanea
                        for (int i = 0; i < frameDataList.size(); i++) {
                            FrameData[] currentFrameDataSet = frameDataList.get(i);
                            imagesMossa[i] = new Image(frames[currentFrameDataSet[0].getNumeroSprite()]);
                            imagesMossa[i].setSize(currentFrameDataSet[0].getAltezza(),
                                    currentFrameDataSet[0].getLarghezza());
                            imagesMossa[i].setPosition(currentFrameDataSet[0].getX(), currentFrameDataSet[0].getY());
                            imagesMossa[i].setColor(1, 1, 1, currentFrameDataSet[0].getOpacity());
                            stage.addActor(imagesMossa[i]);
                        }

                        if (danno != 0) {
                            if (isBotFight) {
                                modificaHPPokeBot(numeroIndexPokeBot, currentPokeHPBot);
                            }

                            updateHpBarWidth(HPbot, currentPokeHPBot, maxPokeHPBot, pokemonImageBot, nomePokeBot);

                        }

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                for (Image imageMossa : imagesMossa) {
                                    imageMossa.remove(); // Rimuove l'immagine dalla scena
                                }

                            }
                        }, 0.5f);

                    }
                }, 1f);

                // Timer per rimuovere l'animazione dopo un breve periodo
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

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

                                if (otherAttack == true) {
                                    if (counterForNextMove == 0) {
                                        if (Integer.parseInt(currentPokeHPBot) > 0) {
                                            utilizzoMossaBot(false, null);
                                        }
                                    }
                                }
                            }
                        }, 2.5f);
                        this.cancel(); // Ferma il timer

                    }
                }, 0.5f); // Animazione istantanea per mezzo secondo

            }
        } catch (Exception e) {
            System.out.println("Errore utilizzoMossa battle, " + e);
        }

    }

    private void utilizzoMossaBot(boolean otherAttack, Image labelMosse) {
        try {

            nextMoveBot = false;
            nextMove = labelMosse;
            globalOtherAttack = otherAttack;
            counterForNextMove = 0;
            Random random = new Random();
            int X = random.nextInt(listaMosseBot.size());
            while (Integer.parseInt(listaMosseBot.get(X).getattPP()) <= 0) {
                X = random.nextInt(listaMosseBot.size());
            }
            nomeMossaBot = listaMosseBot.get(X).getNome();
            listaMosseBot.get(X).setattPP();
            sistemaLabel14(nomeMossaBot);
            labelDiscorsi14.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label14 = labelDiscorsi14.getLabel();
            stage.addActor(label14);

            FileHandle mosseFile = Gdx.files.local("pokemon/mosse.json");
            String mosseJsonString = mosseFile.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON delle mosse
            JsonValue mosseJson = new JsonReader().parse(mosseJsonString);
            JsonValue tipoJson = mosseJson.get(nomeMossaBot);
            String tipologiaMossa = tipoJson.getString("attacco");
            // TODO: animazione (blocca quell oche c'è dopo)
            if (tipologiaMossa.equals("fisico")) {
                dannoBot = listaMosseBot.get(X).calcolaDanno(statsBot.get(0), statsPlayer.get(1),
                        Integer.parseInt(LVPokeBot), nomePokeBot, nomePoke);
            } else {
                dannoBot = listaMosseBot.get(X).calcolaDanno(statsBot.get(2), statsPlayer.get(3),
                        Integer.parseInt(LVPokeBot), nomePokeBot, nomePoke);
            }

            pokeHPbeforeFight = currentPokeHP;

            currentPokeHP = Integer.toString((Integer.parseInt(currentPokeHP)) - dannoBot);

            if (Integer.parseInt(currentPokeHP) <= 0) {
                currentPokeHP = Integer.toString(0);
            }

            if (dannoBot != 0) {
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

                    if (otherAttack == true) {
                        if (counterForNextMove == 0) {
                            if (Integer.parseInt(currentPokeHP) > 0) {
                                utilizzoMossa(labelMosse, false);
                            }
                        }
                    }
                }
            }, 2.5f);
        } catch (Exception e) {
            System.out.println("Errore utilizzoMossaBot battle, " + e);
        }

    }

    private void sistemaLabel4(String nome) {
        this.nomeMossa = nome;
        String discorso4 = nomePoke + " utilizza " + nomeMossa + "!";
        labelDiscorsi4 = new LabelDiscorsi(discorso4, dimMax, 0, true, false);
    }

    private void sistemaLabel14(String nome) {
        this.nomeMossa = nome;
        String discorso14 = nomePokeBot + " utilizza " + nomeMossa + "!";
        labelDiscorsi14 = new LabelDiscorsi(discorso14, dimMax, 0, true, false);
    }

    private void activateAnimation(Image image, Animation<TextureRegion> animation) {
        try {

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    boolean check;

                    if (image == imageBall) {
                        check = true;
                        checkInt++;
                    } else {
                        check = false;
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
                                        // nel caso dovesse esplodere un giorno tutto questo, di fianco ad entrambi i
                                        // check aggiungere && checkInt<3; da problemi ma se qualcosa esplode lo si
                                        // riaggiunge
                                        if (check) {
                                            showPokemon(labelBaseD, nomePoke);
                                        } else if (isBotFight && !check) {
                                            synchronized (lock) {
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
        } catch (Exception e) {
            System.out.println("Errore activateAnimation battle, " + e);
        }

    }

    private void showPokemon(Image baseImage, String nome) {
        try {

            if (baseImage.equals(labelBaseU) && checkPerDoppioPoke > 1) {
                return;
            }
            Texture pokemonTexture = new Texture("pokemon/" + nome + ".png");
            int frameWidth = pokemonTexture.getWidth() / 4;
            int frameHeight = pokemonTexture.getHeight();

            TextureRegion[] pokemonFrames;
            Animation<TextureRegion> pokemonAnimation;

            if (baseImage == labelBaseD) {
                pokemonFrames = new TextureRegion[2];
                int startX = frameWidth * 2; // Inizia dalla terza parte dell'immagine
                for (int i = 0; i < 2; i++) {
                    pokemonFrames[i] = new TextureRegion(pokemonTexture, startX + i * frameWidth, 0, frameWidth,
                            frameHeight);
                }
                pokemonAnimation = new Animation<>(0.4f, pokemonFrames);

                pokemonImage = new Image(pokemonFrames[0]);
                pokemonImage.setSize(frameWidth * 3, frameHeight * 3);
                pokemonImage.setPosition(labelBaseD.getX() + 270, labelBaseD.getY());
                pokemonImage.setName("pokemon player");
                stage.addActor(pokemonImage);
                animation(pokemonImage, pokemonAnimation);
            }

            else if (baseImage == labelBaseU) {
                if (!isBotFight) {
                    botImage.remove();
                }
                pokemonFrames = new TextureRegion[2];
                for (int i = 0; i < 2; i++) {
                    pokemonFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
                }
                pokemonAnimation = new Animation<>(0.4f, pokemonFrames);

                pokemonImageBot = new Image(pokemonFrames[0]);
                pokemonImageBot.setSize(frameWidth * 3, frameHeight * 3);
                pokemonImageBot.setPosition(labelBaseU.getX() + 60, labelBaseU.getY() + 20);
                pokemonImageBot.setName("pokemon bot");
                stage.addActor(pokemonImageBot);
                animation(pokemonImageBot, pokemonAnimation);
            } else {
                return; // Esci dalla funzione in caso di baseImage non gestito
            }

            if (baseImage.equals(labelBaseU)) {
                checkPerDoppioPoke = 0;
            }
        } catch (Exception e) {
            System.out.println("Errore showPokemon battle, " + e);
        }

    }

    private void animation(Image pokeImage, Animation<TextureRegion> pokemonAnimation) {
        try {

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
        } catch (Exception e) {
            System.out.println("Errore animation battle, " + e);
        }

    }

    public void leggiPoke(int numero) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get("poke" + numero);
            nomePoke = pokeJson.getString("nomePokemon");
            LVPoke = pokeJson.getString("livello");

            JsonValue statistiche = pokeJson.get("statistiche");

            currentPokeHP = pokeJson.get("statistiche").getString("hp");
            maxPokeHP = pokeJson.get("statistiche").getString("hpTot");

            statsPlayer.clear();
            for (JsonValue stat : statistiche) {
                if (!stat.name.equals("hp") && !stat.name.equals("hpTot")) {
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
                Mossa mossa = new Mossa(nomeMossa, tipoMossa, maxPP, attPP, this); // gli passo Battle stesso con "this"
                                                                                   // per
                                                                                   // poter chiamare anche i metodi di
                                                                                   // Battle da Mossa
                listaMosse.add(mossa);
            }

            if (currentPokeHP.equals("0") || nomePoke.equals("")) {
                if (numeroIndexPoke < 6) {
                    numeroIndexPoke++;
                    leggiPoke(numeroIndexPoke);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore leggiPoke battle, " + e);
        }

    }

    public void leggiPokeSecondario(int numero) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get("poke" + numero);
            currentPokeHPforSquad = pokeJson.get("Statistiche").getString("hp");
            nomePokeSquad = pokeJson.getString("nomePokemon");
            if (!pokeJson.getString("livello").equals("")) {
                if (pokeJson.getInt("livello") > levelMax) {
                    levelMax = pokeJson.getInt("livello");
                }
            }
        } catch (Exception e) {
            System.out.println("Errore leggiPokeSecondario battle, " + e);
        }

    }

    private void posizionaLabelSquadra() {
        try {

            labelPokePiazzate = true;

            Texture arrowPlayer = asset.getBattle(Assets.PLAYER_ARROW);
            playerArrow = new Image(arrowPlayer);
            // playerArrow.setPosition(1024-250,270);
            playerArrow.setPosition(1024, 270); // inizialmente fuori dallo schermo
            playerArrow.setSize(250, 26);
            stage.addActor(playerArrow);

            if (isBotFight) {
                Texture arrowBot = asset.getBattle(Assets.BOT_ARROW);
                botArrow = new Image(arrowBot);
                // botArrow.setPosition(0,650);
                botArrow.setPosition(-250, 650); // inizialmente fuori dallo schermo
                botArrow.setSize(250, 26);
                stage.addActor(botArrow);
                Action botArrowAction = Actions.moveTo(0, 650, 0.5f);
                botArrow.addAction(botArrowAction);
                piazzaSquadBot();
            }

            // Creazione delle azioni per lo spostamento delle frecce
            Action playerArrowAction = Actions.moveTo(1024 - 250, 270, 0.5f); // duration è la durata dell'animazione in
                                                                              // secondi

            // Applicazione delle azioni alle frecce
            playerArrow.addAction(playerArrowAction);

            piazzaSquad();
        } catch (Exception e) {
            System.out.println("Errore posizionaLabelSquadra battle, " + e);
        }

    }

    private void piazzaSquad() {
        try {

            Texture texture = asset.getBattle(Assets.BALLS_FOR_NUMBER);
            TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8,
                    texture.getHeight() / 3);
            // Inizializza l'array di sprite
            spriteArrayBallsSquadra = new Sprite[8];
            int colonna = -1;

            for (int i = 0; i < 6; i++) {
                leggiPokeSecondario(i + 1);
                if (nomePokeSquad.isEmpty()) {
                    colonna = 2;
                } else {
                    if (currentPokeHPforSquad.equals("0")) {
                        colonna = 1;
                    } else {
                        colonna = 0;
                    }
                }

                // Riempie l'array di sprite
                int indice = 0;
                for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
                }

                final int index = i;
                final int col = colonna;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        TextureRegion region = textureRegions[col][0];
                        Image image = new Image(region); // Crea un'istanza di Image con la texture region
                                                         // corrispondente

                        // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al
                        // suo asse centrale
                        image.setOrigin(Align.center);
                        image.setPosition(1024, 270); // Posiziona l'immagine fuori dallo schermo
                        image.setSize(32, 30);
                        stage.addActor(image); // Aggiungi l'immagine allo stage

                        Action moveAction = Actions.moveTo(
                                playerArrow.getX() + (30 * (6 - index) + 10 * (6 - index) - 20),
                                playerArrow.getY(), 1f); // Sposta l'immagine sulla freccia del giocatore
                        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                        Action rotateAction = Actions.rotateBy(360, 1f); // Ruota l'immagine di 360 gradi in 1 secondi
                        Action changeImageAction = Actions.run(() -> {
                            image.setDrawable(new TextureRegionDrawable(region)); // Cambia l'immagine
                        });
                        ParallelAction parallelAction = Actions.parallel(moveAction, rotateAction);
                        SequenceAction sequenceAction = Actions.sequence(parallelAction, changeImageAction);
                        image.addAction(sequenceAction); // Applica le azioni all'immagine

                    }
                }, 0.5f * (6 - i));
            }
        } catch (Exception e) {
            System.out.println("Errore piazzaSquad battle, " + e);
        }

    }

    private void piazzaSquadBot() {
        try {

            Texture texture = asset.getBattle(Assets.BALLS_FOR_NUMBER);
            TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8,
                    texture.getHeight() / 3);
            // Inizializza l'array di sprite
            spriteArrayBallsSquadra = new Sprite[8];
            int colonna = -1;

            for (int i = 0; i < 6; i++) {
                leggiPokeSecondarioBot(i + 1, 1);
                if (nomePokeSquadBot.isEmpty()) {
                    colonna = 2;
                } else {
                    if (hpBotSquad.get(i).equals(0)) {
                        colonna = 1;
                    } else {
                        colonna = 0;
                    }
                }

                // Riempie l'array di sprite
                int indice = 0;
                for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
                }

                final int index = i;
                final int col = colonna;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        TextureRegion region = textureRegions[col][0];
                        Image image = new Image(region); // Crea un'istanza di Image con la texture region
                                                         // corrispondente

                        // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al
                        // suo asse centrale
                        image.setOrigin(Align.center);
                        image.setPosition(0, 650); // Posiziona l'immagine fuori dallo schermo
                        image.setSize(32, 30);
                        stage.addActor(image); // Aggiungi l'immagine allo stage

                        Action moveAction = Actions.moveTo(botArrow.getX() + (30 * index + 10 * index), botArrow.getY(),
                                1f); // Sposta l'immagine sulla freccia del giocatore
                        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                        Action rotateAction = Actions.rotateBy(360, 1f); // Ruota l'immagine di 360 gradi in 1.5 secondi
                        Action changeImageAction = Actions.run(() -> {
                            image.setDrawable(new TextureRegionDrawable(region)); // Cambia l'immagine
                        });
                        ParallelAction parallelAction = Actions.parallel(moveAction, rotateAction);
                        SequenceAction sequenceAction = Actions.sequence(parallelAction, changeImageAction);
                        image.addAction(sequenceAction); // Applica le azioni all'immagine

                    }
                }, 0.5f * (6 - i));
            }
        } catch (Exception e) {
            System.out.println("Errore piazzaSquadBot battle, " + e);
        }

    }

    private void leggiBot(String nameBot) {
        try {

            FileHandle file = Gdx.files.local("bots/bots.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get(nameBot);
            nomeBot = pokeJson.getString("nome");
            tipoBot = pokeJson.getString("tipo");
        } catch (Exception e) {
            System.out.println("Errore leggiBot battle, " + e);
        }

    }

    private void leggiPokeBot(String nBot, int numeroPoke) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("bots/bots.json");
            String jsonString = file.readString();

            FileHandle file2 = Gdx.files.local("pokemon/mosse.json");
            String jsonString2 = file2.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue json2 = new JsonReader().parse(jsonString2);

            JsonValue pokeJson = json.get(nBot).get("poke" + numeroPoke);
            nomePokeBot = pokeJson.getString("nomePokemon");
            LVPokeBot = pokeJson.getString("livello");

            if (!LVPokeBot.equals("")) {
                levelLastPokeBot = LVPokeBot;
            }

            IV iv = new IV();
            JsonValue pokeIvBot2 = iv.creaIV();

            Stats stats = new Stats();

            if (!nomePokeBot.equals("") && !LVPokeBot.equals("")) {
                scopriPokemon(nomePokeBot);
                JsonValue statistiche = stats.calcolaStatsBot(nomePokeBot, Integer.parseInt(LVPokeBot), pokeIvBot2);
                statsBot.clear();
                for (JsonValue stat : statistiche) {
                    if (!stat.name.equals("hp") && !stat.name.equals("hpTot")) {
                        statsBot.add(stat.asInt());
                    }
                }
                maxPokeHPBot = statistiche.getString("hp");
                currentPokeHPBot = statistiche.getString("hp");
            }

            JsonValue mosse = pokeJson.get("mosse");
            listaMosseBot.clear();
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                String ppMossa = "0";
                if (!nomeMossa.isEmpty()) {
                    ppMossa = json2.get(nomeMossa).getString("pp");
                }
                // Aggiungi la mossa alla lista
                Mossa mossa = new Mossa(nomeMossa, tipoMossa, ppMossa, ppMossa, this);
                listaMosseBot.add(mossa);
            }

            if (isBotFight && currentPokeHPBot != null && nomePokeBot != null) {
                if (currentPokeHPBot.equals("0") || nomePokeBot.equals("")) {
                    if (numeroIndexPokeBot < 6) {
                        numeroIndexPokeBot++;
                        leggiPokeBot(nBot, numeroIndexPokeBot);
                    } else if (numeroIndexPokeBot == 6) {
                        calcolaDenaroVintoDaNPC();
                        modifcicaDenaro(soldiPresi);
                        fineBattaglia();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore leggiPokebot battle, " + e);
        }

    }

    private void leggiPokeSelvatico(String nBot, String zona) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("jsonPokeSelvatici/" + zona + ".json");
            String jsonString = file.readString();
            FileHandle file2 = Gdx.files.local("pokemon/mosse.json");
            String jsonString2 = file2.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue json2 = new JsonReader().parse(jsonString2);

            JsonValue pokeJson = json.get(nomeSelvatico);
            nomePokeBot = pokeJson.getString("nomePokemon");
            SelvaticoLVPokeBotMin = pokeJson.getInt("livello-min");
            SelvaticoLVPokeBotMax = pokeJson.getInt("livello-max");

            x = (float) (Math.random() * (1.3 - 0.4)) + 0.4f;
            float randoLvl = (float) (Math.random() * (SelvaticoLVPokeBotMax - SelvaticoLVPokeBotMin + 1))
                    + SelvaticoLVPokeBotMin;
            int randoLvlInt = (int) randoLvl; // Converte il valore in intero
            LVPokeBot = String.valueOf(randoLvlInt);

            FileHandle filePoke = Gdx.files.local("pokemon/Pokemon.json");
            String jsonStringPoke = filePoke.readString();
            JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

            IV iv = new IV();
            pokeIvBot = iv.creaIV();

            Stats stats = new Stats();
            JsonValue statistiche = stats.calcolaStatsBot(nameBot, Integer.parseInt(LVPokeBot), pokeIvBot);
            statsBot.clear();
            for (JsonValue stat : statistiche) {
                if (!stat.name.equals("hp")) {
                    statsBot.add((int) (stat.asInt() * x));
                }
            }
            maxPokeHPBot = statistiche.getString("hp");
            currentPokeHPBot = statistiche.getString("hp");

            int numMosseImparabili = (int) (randoLvl / 2);

            // Ottieni la lista delle mosse imparabili
            JsonValue mosseImparabili = jsonPoke.get(nameBot).get("mosseImparabili");

            // Seleziona le prime `n` mosse in base al numero calcolato
            List<String> mossePossibili = new ArrayList<>();
            for (int i = 1; i <= numMosseImparabili && i <= 50; i++) { // Assumiamo che ci siano al massimo 50 mosse
                String mossa = mosseImparabili.getString("M" + i);
                if (!mossa.isEmpty()) {
                    mossePossibili.add(mossa);
                }
            }

            // System.out.println(LVPokeBot);

            // Seleziona un numero casuale di mosse (da 1 a 4)
            int numMosseDaSelezionare = (int) (Math.random() * 4) + 1; // Genera un numero da 1 a 4

            // Seleziona casualmente `numMosseDaSelezionare` mosse dalle mosse possibili
            Collections.shuffle(mossePossibili);
            List<String> mosseScelte = mossePossibili.subList(0,
                    Math.min(numMosseDaSelezionare, mossePossibili.size()));

            // Itera sulle mosse selezionate, recuperando i dettagli dal file `mosse.json`
            for (String nomeMossa : mosseScelte) {
                JsonValue mossaJson = json2.get(nomeMossa); // Cerca la mossa in `mosse.json`

                // Verifica che la mossa esista nel file delle mosse
                if (mossaJson != null) {
                    String tipoMossa = mossaJson.getString("tipo", ""); // Ottieni il tipo della mossa
                    String ppMossa = mossaJson.getString("pp", "0"); // Ottieni i PP della mossa

                    // Crea e aggiungi la mossa alla lista `listaMosseBot`
                    // System.out.println(nomeMossa);

                    Mossa mossa = new Mossa(nomeMossa, tipoMossa, ppMossa, ppMossa, this);
                    listaMosseBot.add(mossa);
                } else {
                    // System.out.println("Mossa non trovata: " + nomeMossa);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore leggiPokeSelvatico battle, " + e);
        }

    }

    public void leggiPokeSecondarioBot(int numero, int numBot) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("bots/bots.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get(nameBot).get("poke" + numero);
            nomePokeSquadBot = pokeJson.getString("nomePokemon");
            String LVPokeBotSQ = pokeJson.getString("livello");
            IV iv = new IV();
            pokeIvBot = iv.creaIV();

            Stats stats = new Stats();

            if (!nomePokeSquadBot.isEmpty()) {
                JsonValue statistiche = stats.calcolaStatsBot(nomePokeSquadBot, Integer.parseInt(LVPokeBotSQ),
                        pokeIvBot);
                currentPokeHPBotSquad = statistiche.getInt("hp");
            } else {
                currentPokeHPBotSquad = -90310;
            }

            hpBotSquad.add(currentPokeHPBotSquad);
        } catch (Exception e) {
            System.out.println("Errore leggiPokesecondariBot battle, " + e);
        }

    }

    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP) {
        try {

            if (image.equals(botHPBar) && checkPerDoppiaBarra > 1) {
                return null; // ferma l'esecuzione se è già attiva (evita il problema della doppia barra)
            }
            // Calcola la percentuale degli HP attuali rispetto agli HP totali
            float percentualeHP = Float.parseFloat(currentHP) / Float.parseFloat(maxHP);
            float lunghezzaHPBar = 96 * percentualeHP;
            // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
            Image hpBar = new Image(new TextureRegionDrawable(new TextureRegion(asset.getBattle(Assets.WHITE_PX))));
            hpBar.setSize((int) lunghezzaHPBar, 6);
            hpBar.setPosition(image.getX() + diffX, image.getY() + diffY);
            // hpBar.setPosition(400, 400);
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

            if (image.equals(botHPBar)) {
                checkPerDoppiaBarra = 0;
            }

            return hpBar;
        } catch (Exception e) {
            System.out.println("Errore placeHpBar battle, " + e);
            return null;
        }

    }

    // Metodo per aggiornare la larghezza della barra degli HP con un'animazione
    private void updateHpBarWidth(Image hpBar, String currentHP, String maxHP, Image pokeImage, String nomePokem) {
        try {

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
                    if (dannoBot > Integer.parseInt(pokeHPbeforeFight)) {
                        passi = Integer.parseInt(pokeHPbeforeFight);
                        for (int i = 0; i < pokeInBattaglia.size(); i++) {
                            if (pokeInBattaglia.get(i).equals(numeroIndexPoke)) {
                                pokeInBattaglia.remove(i);
                            }
                        }

                    } else {
                        passi = dannoBot;
                    }

                    // Calcola il ritardo tra ogni passo
                    float ritardoTraPassi = 1f / passi;

                    // Crea un'azione parallela per gestire contemporaneamente l'aggiornamento della
                    // larghezza della barra HP e l'animazione del cambiamento di labelHP
                    ParallelAction parallelAction = new ParallelAction();

                    // Aggiungi un'azione per aggiornare la larghezza della barra HP
                    parallelAction.addAction(Actions.sizeTo(lunghezzaHPBar, hpBar.getHeight(), 1f));

                    // Aggiungi un'azione per l'animazione del cambiamento del valore di labelHP
                    for (int i = 0; i < passi; i++) {
                        int nuovoValore = Integer.parseInt(pokeHPbeforeFight) - (i + 1);

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
        } catch (Exception e) {
            System.out.println("Errore updateHpBarWidth battle, " + e);
        }

    }

    public void piazzaLabel5() {
        try {

            counterForNextMove++;
            delaySecondText = 0.7f;
            if (checkLabel5) {
                checkLabel5 = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi5.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore
                                                                  // più
                                                                  // alto di quello degli altri attori
                        label5 = labelDiscorsi5.getLabel();
                        stage.addActor(label5);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi5.reset();
                                label5.remove();
                                label5 = null;
                                checkLabel5 = true;
                                if (globalOtherAttack == true) {
                                    if (counterForNextMove == 1) {
                                        if (nextMoveBot) {
                                            if (Integer.parseInt(currentPokeHPBot) > 0)
                                                utilizzoMossaBot(false, null);
                                        } else {
                                            if (Integer.parseInt(currentPokeHP) > 0)
                                                utilizzoMossa(nextMove, false);
                                        }
                                    }
                                }
                            }
                        }, 1.2f);
                    }
                }, 2f);
            }
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel5 battle, " + e);
        }

    }

    public void piazzaLabel6() {
        try {

            counterForNextMove++;
            delaySecondText = 0.7f;
            if (checkLabel6) {
                checkLabel6 = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi6.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore
                                                                  // più
                                                                  // alto di quello degli altri attori
                        label6 = labelDiscorsi6.getLabel();
                        stage.addActor(label6);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi6.reset();
                                label6.remove();
                                label6 = null;
                                checkLabel6 = true;
                                if (globalOtherAttack == true) {
                                    if (counterForNextMove == 1) {
                                        if (nextMoveBot) {
                                            if (Integer.parseInt(currentPokeHPBot) > 0)
                                                utilizzoMossaBot(false, null);
                                        } else {
                                            if (Integer.parseInt(currentPokeHP) > 0)
                                                utilizzoMossa(nextMove, false);
                                        }
                                    }
                                }
                            }
                        }, 1.2f);
                    }
                }, 2f);
            }
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel6 battle, " + e);
        }

    }

    public void piazzaLabel7() {
        try {

            counterForNextMove++;
            delaySecondText = 0.7f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi7.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                              // alto
                                                              // di quello degli altri attori
                    label7 = labelDiscorsi7.getLabel();
                    stage.addActor(label7);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi7.reset();
                            label7.remove();
                            label7 = null;
                            if (globalOtherAttack == true) {
                                if (counterForNextMove == 1) {
                                    if (nextMoveBot) {
                                        if (Integer.parseInt(currentPokeHPBot) > 0)
                                            utilizzoMossaBot(false, null);
                                    } else {
                                        if (Integer.parseInt(currentPokeHP) > 0)
                                            utilizzoMossa(nextMove, false);
                                    }
                                }
                            }
                        }
                    }, 1.2f);
                }
            }, 2f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel7 battle, " + e);
        }

    }

    public void piazzaLabel9() {
        try {

            // delay se è attiva già un'altra animazione
            float d = 0;
            if (!checkLabel5 || !checkLabel6) {
                d = 1.2f;
            }

            final float delay = d;
            delaySecondText = 0.7f + delay;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi9.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                              // alto
                                                              // di quello degli altri attori
                    label9 = labelDiscorsi9.getLabel();
                    stage.addActor(label9);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi9.reset();
                            label9.remove();
                            label9 = null;
                            if (globalOtherAttack == true) {
                                if (nextMoveBot) {
                                    if (Integer.parseInt(currentPokeHPBot) > 0)
                                        utilizzoMossaBot(false, null);
                                } else {
                                    if (Integer.parseInt(currentPokeHP) > 0)
                                        utilizzoMossa(nextMove, false);
                                }
                            }
                        }
                    }, 1.2f);
                }
            }, 2f + delay);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel9 battke, " + e);
        }

    }

    public void faintPokemon(final Image pokeImage) {

        if (pokeImage != pokemonImage) {
            updateEV();
        }

        try {

            modificaPPMossePoke(numeroIndexPoke, listaMosse); // Aggiorna i PP delle mosse del pokemon se viene
                                                              // sconfitto

            Rectangle scissors = new Rectangle(pokeImage.getX(), pokeImage.getY(), pokeImage.getWidth(),
                    pokeImage.getHeight());

            float initialY = pokeImage.getY();

            // Calcola la nuova posizione Y, che sarà sotto lo schermo
            float newY = -initialY;

            // Crea un'azione di spostamento dell'immagine verso la nuova posizione Y
            Action moveAction = Actions.moveBy(0, newY, 0.6f); // Durata: 0.6 secondi

            // Applica l'azione di spostamento all'immagine
            pokeImage.addAction(moveAction);

            // Ritaglia l'area dell'immagine mentre diminuisci la sua altezza
            ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(),
                    new Rectangle(pokeImage.getX(), pokeImage.getY(), pokeImage.getWidth(), pokeImage.getHeight()),
                    scissors);
            ScissorStack.pushScissors(scissors);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Ferma il ritaglio
                    ScissorStack.popScissors();
                    pokeImage.remove();

                    if (isBotFight && pokeImage != pokemonImage) {
                        if (numeroIndexPokeBot < 6 && Integer.parseInt(currentPokeHPBot) == 0) {
                            numeroIndexPokeBot++;
                            rimuoviPokeDaBattagliaBot();

                        } else if (numeroIndexPokeBot == 6 && Integer.parseInt(currentPokeHPBot) == 0
                                && !isBattleEnded) {
                            calcolaDenaroVintoDaNPC();
                            modifcicaDenaro(soldiPresi);
                            fineBattaglia();
                        }
                    } else if (pokeImage == pokemonImage) {
                        int counter = 0;
                        for (int i = 0; i < 6; i++) {
                            leggiPokeSecondario(i + 1);
                            if (Integer.parseInt(currentPokeHPforSquad) > 0) {
                                counter++;
                            }
                        }
                        if (counter != 0) {
                            squadra = new Squadra(stage, true, battle, null, true);
                            switchForzato = true;
                        } else {
                            sconfitta = true;
                            calcolaDenaroPerso();
                            modifcicaDenaro(denaroPerso);
                            fineBattaglia();
                        }
                    } else {
                        calcoloEsperienzaVinta(0);
                    }
                }
            }, 0.6f);
        } catch (Exception e) {
            System.out.println("Errore faintPokemon battle, " + e);
        }

    }

    public void piazzaLabel12(String nomePokem) {
        try {

            String discorso12 = nomePokem + " non ha piu' energie!";
            labelDiscorsi12 = new LabelDiscorsi(discorso12, dimMax, 0, true, false);
            labelDiscorsi12.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label12 = labelDiscorsi12.getLabel();
            stage.addActor(label12);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi12.reset();
                    label12.remove();
                    label12 = null;
                    if (isBotFight) {
                        updatePokeSquadBot();
                    }
                    updatePokeSquad();
                }
            }, 2f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel12 battle, " + e);
        }

    }

    public void modificaHPPokeBot(int numero, String currentHP) {
        try {

            // Converti currentHP da String a int
            int hpValue = Integer.parseInt(currentHP);

            // Aggiorna l'elemento alla posizione corretta dell'ArrayList
            hpBotSquad.set(numero - 1, hpValue);
        } catch (Exception e) {
            System.out.println("Errore modificaHPPokeBot battle, " + e);
        }

    }

    public void modificaHPPoke(int numero, String currentHP) {
        try {

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
        } catch (Exception e) {
            System.out.println("Errore modificaHpBarPoke battle, " + e);
        }

    }

    public void updatePokeSquadBot() {
        try {

            Texture texture = asset.getBattle(Assets.BALLS_FOR_NUMBER);
            TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8,
                    texture.getHeight() / 3);
            // Inizializza l'array di sprite
            spriteArrayBallsSquadra = new Sprite[8];
            int colonna = -1;

            for (int i = 0; i < 6; i++) {
                leggiPokeSecondarioBot(i + 1, 1);
                if (nomePokeSquadBot.isEmpty()) {
                    colonna = 2;
                } else {
                    if (hpBotSquad.get(i).equals(0)) {
                        // System.out.println("a");
                        colonna = 1;
                    } else {
                        colonna = 0;
                        // System.out.println("b");
                    }
                }

                // Riempie l'array di sprite
                int indice = 0;
                for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
                }

                final int index = i;
                final int col = colonna;

                TextureRegion region = textureRegions[col][0];
                Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

                // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al
                // suo asse centrale
                image.setOrigin(Align.center);
                image.setPosition(botArrow.getX() + (30 * index + 10 * index), botArrow.getY());
                image.setSize(32, 30);
                stage.addActor(image); // Aggiungi l'immagine allo stage

            }
        } catch (Exception e) {
            System.out.println("Errore updatePokeSquadBot battle, " + e);
        }

    }

    public void updatePokeSquad() {
        try {

            Texture texture = asset.getBattle(Assets.BALLS_FOR_NUMBER);
            TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 8,
                    texture.getHeight() / 3);
            // Inizializza l'array di sprite
            spriteArrayBallsSquadra = new Sprite[8];
            int colonna = -1;

            for (int i = 0; i < 6; i++) {
                leggiPokeSecondario(i + 1);
                if (nomePokeSquad.isEmpty()) {
                    colonna = 2;
                } else {
                    if (currentPokeHPforSquad.equals("0")) {
                        colonna = 1;
                    } else {
                        colonna = 0;
                    }
                }

                // Riempie l'array di sprite
                int indice = 0;
                for (int riga = 0; riga < 3; riga++) {
                    spriteArrayBallsSquadra[indice++] = new Sprite(textureRegions[riga][colonna]);
                }

                final int index = i;
                final int col = colonna;

                TextureRegion region = textureRegions[col][0];
                Image image = new Image(region); // Crea un'istanza di Image con la texture region corrispondente

                // Imposta l'origine dell'immagine al centro per rendere la rotazione attorno al
                // suo asse centrale
                image.setOrigin(Align.center);
                image.setPosition(playerArrow.getX() + (30 * (6 - index) + 10 * (6 - index) - 20), playerArrow.getY());
                image.setSize(32, 30);
                stage.addActor(image); // Aggiungi l'immagine allo stage

            }
        } catch (Exception e) {
            System.out.println("Errore updatePokeSquad battle, " + e);
        }

    }

    public void rimuoviPokeDaBattagliaBot() {
        for (int i = 0; i < pokeInBattaglia.size(); i++) {
            // System.out.println(pokeInBattaglia.get(i));
        }
        calcoloEsperienzaVinta(1);

    }

    public void rimuoviPokeDaBattaglia() {
        try {

            playerHPBar.remove();
            HPplayer.remove();
            expPlayer.remove();

            labelNomeHPBars.remove(labelNomePokemon);
            labelNomePokemon.remove();

            labelNomeHPBars.remove(labelLV);
            labelLV.remove();

            labelNomeHPBars.remove(labelHP);
            labelHP.remove();

            labelNomeHPBars.remove(labelHPTot);
            labelHPTot.remove();
        } catch (Exception e) {
            System.out.println("Errore rimuoviPokeDaBattaglia battle, " + e);
        }

    }

    private void fineBattaglia() {
        try {

            isBattleEnded = true;
            modificaPPMossePoke(numeroIndexPoke, listaMosse); // Aggiorna i PP delle mosse del pokemon se la battaglia
                                                              // finisce

            if (isBotFight && !sconfitta) {
                label3 = labelDiscorsi3.getLabel();
                stage.addActor(label3);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi3.reset();
                        label3.remove();
                        label3 = null;

                        label8 = labelDiscorsi8.getLabel();
                        stage.addActor(label8);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi8.reset();
                                label8.remove();
                                label8 = null;
                                dispose();
                            }
                        }, 2f);

                    }
                }, 2f);
            } else if (sconfitta) {
                label16 = labelDiscorsi16.getLabel();
                stage.addActor(label16);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelDiscorsi16.reset();
                        label16.remove();
                        label16 = null;

                        label17 = labelDiscorsi17.getLabel();
                        stage.addActor(label17);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi17.reset();
                                label17.remove();
                                label17 = null;

                                label18 = labelDiscorsi18.getLabel();
                                stage.addActor(label18);
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        labelDiscorsi18.reset();
                                        label18.remove();
                                        label18 = null;

                                        // TODO: da modificare e mettere una funzione in futuro per il l'ultimo
                                        // pokecenter visitato
                                        chiamante.setLuogo("casaSpawn");
                                        chiamante.setPage("casaSpawn");

                                        dispose();
                                    }
                                }, 3.5f);
                            }
                        }, 2.5f);
                    }
                }, 2f);
            } else {
                dispose();
            }
        } catch (Exception e) {
            System.out.println("Errore fineBattaglia battle, " + e);
        }

    }

    public void cambiaPokemon(int newIndex) {
        try {

            modificaPPMossePoke(numeroIndexPoke, listaMosse); // Aggiorna i PP delle mosse del pokemon se viene cambiato
            switched = true;
            rimuoviPokeDaBattaglia();
            listaMosse.clear();
            leggiPoke(newIndex + 1);
            ballTexture = new Texture("battle/" + nomeBall + "Player.png");
            String discorso2 = "Vai " + nomePoke + "!";
            labelDiscorsi2 = new LabelDiscorsi(discorso2, dimMax, 0, true, false);
            pokemonImage.remove();
            numeroIndexPoke = newIndex + 1;
            if (!pokeInBattaglia.contains(numeroIndexPoke)) {
                pokeInBattaglia.add(numeroIndexPoke);
            }
            showBall(ballTexture);
            piazzaLabelLottaPlayer();
        } catch (Exception e) {
            System.out.println("Errore cambiaPokemon battle, " + e);
        }

    }

    public void closeSquadra() {
        Gdx.input.setInputProcessor(stage);
        squadra = null;
    }

    public void modifcicaDenaro(int soldiModifica) {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/datiGenerali.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            int soldi = json.getInt("denaro");

            json.remove("denaro");

            json.addChild("denaro", new JsonValue(soldi + soldiModifica));
            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore modificaDenaro battle, " + e);
        }

    }

    public void calcolaDenaroPerso() {
        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/datiGenerali.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            int nMedaglie = json.getInt("numero_medaglie");
            denaroPerso = levelMax * (nMedaglie * 33 + 1);
            if (json.getInt("denaro") < denaroPerso) {
                denaroPerso = json.getInt("denaro");
            }

            String discorso17 = "Hai perso " + denaroPerso + " Pokédollari nella fuga.";
            labelDiscorsi17 = new LabelDiscorsi(discorso17, dimMax, 0, true, false);

            denaroPerso *= -1;
        } catch (Exception e) {
            System.out.println("Errore calcolaDenaroPerso battle, " + e);
        }

    }

    public void calcolaDenaroVintoDaNPC() {
        try {

            soldiPresi = Integer.parseInt(levelLastPokeBot) * 150;

            String discorso8 = "Hai guadagnato " + soldiPresi + " Pokédollari.";
            labelDiscorsi8 = new LabelDiscorsi(discorso8, dimMax, 0, true, false);
        } catch (Exception e) {
            System.out.println("Errore calcolaDenaroVIntoDaNPC battle, " + e);
        }

    }

    public void calcoloTassoCattura(String nameUsedBall) {
        int tasso;
        float bonusBall;

        try {

            borsaModifier.removeInventoryBall(nameUsedBall);

            // Carica il file JSON
            FileHandle file = Gdx.files.local("oggetti/strumenti.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            bonusBall = json.get("pokeballs").get(nameUsedBall).getFloat("cattura");

            // Carica il file JSON
            FileHandle file2 = Gdx.files.local("pokemon/Pokemon.json");
            String jsonString2 = file2.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json2 = new JsonReader().parse(jsonString2);
            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            JsonValue pokeJson = json2.get(nameBot);
            tasso = pokeJson.getInt("tassoCattura");

            tassoCattura = ((((3 * Integer.parseInt(maxPokeHPBot)) - (2 * Integer.parseInt(currentPokeHPBot))) * tasso
                    * bonusBall)) / (3 * Integer.parseInt(maxPokeHPBot));

            //// System.out.println(tassoCattura);

            String discorso19 = "Hai lanciato una " + nameUsedBall + "!";
            labelDiscorsi19 = new LabelDiscorsi(discorso19, dimMax, 0, true, false);

            label19 = labelDiscorsi19.getLabel();
            stage.addActor(label19);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    calcoloVibrazioni(nameUsedBall);
                }
            }, 1.5f);
        } catch (Exception e) {
            System.out.println("Errore calcoloTassoCattura battle, " + e);
        }

    }

    public void calcoloVibrazioni(String ballName) {
        try {

            synchronized (lock) {
                int nVibrazioni = 0;
                Texture lanciaBallTexture = new Texture("battle/" + ballName + "Cattura.png");

                if (tassoCattura < 255) {
                    int vibrazione = (int) (1048560 / Math.sqrt(Math.sqrt(16711680 / (int) tassoCattura)));

                    for (int i = 0; i < 4; i++) {
                        int randomNumber = MathUtils.random(65535);
                        if (randomNumber < vibrazione) {
                            nVibrazioni++;
                        }
                    }

                    lanciaCatturaBall(lanciaBallTexture, nVibrazioni);

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi19.reset();
                            label19.remove();
                            label19 = null;
                        }
                    }, (1.5f + 1.5f * nVibrazioni));

                    /*
                     * if (nVibrazioni==4){
                     * //System.out.println("catturato");
                     * }
                     * else{
                     * //System.out.println("uscito, numero vibrazioni "+ nVibrazioni);
                     * }
                     */

                } else {
                    lanciaCatturaBall(lanciaBallTexture, 4);
                }
            } // fine blocco sincronizzato
        } catch (Exception e) {
            System.out.println("Errore calcoloVibrazioni battle, " + e);
        }

    }

    private void lanciaCatturaBall(Texture textureBall, int nVibrazioni) {
        try {

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
            imageBallLanciata.setSize(8 * 4, 12.5f * 4);

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
                        imageBallLanciata.setPosition((820) * percent, startY + (330) * percent);
                        elapsed += 0.05f;
                    } else {
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
                                        Actions.moveBy(0, bounceHeight / 2, bounceDuration / 2), // Secondo rimbalzo
                                        Actions.moveBy(0, -bounceHeight / 2, bounceDuration / 2),
                                        Actions.moveBy(0, bounceHeight / 4, bounceDuration / 4), // Terzo rimbalzo
                                        Actions.moveBy(0, -bounceHeight / 4, bounceDuration / 4),
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
                                            String discorso20 = "Oh No! Il pokemon selvatico si e' liberato!";
                                            labelDiscorsi20 = new LabelDiscorsi(discorso20, dimMax, 0, true, false);
                                            label20 = labelDiscorsi20.getLabel();
                                            stage.addActor(label20);

                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    utilizzoMossaBot(false, null);
                                                    labelDiscorsi20.reset();
                                                    label20.remove();
                                                    label20 = null;
                                                }
                                            }, 2.5f);

                                        } else if (nVibrazioni == 1) {
                                            // Se nVibrazioni è 1, passa sui frame 7 e 8 e torna a 0 dopo 0.5 secondi
                                            imageBallLanciata.addAction(Actions.sequence(
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[0]));
                                                            pokemonImageBot.setVisible(true);
                                                            String discorso20 = "Oh No! Il pokemon selvatico si e' liberato!";
                                                            labelDiscorsi20 = new LabelDiscorsi(discorso20, dimMax, 0,
                                                                    true,
                                                                    false);
                                                            label20 = labelDiscorsi20.getLabel();
                                                            stage.addActor(label20);

                                                            Timer.schedule(new Timer.Task() {
                                                                @Override
                                                                public void run() {
                                                                    labelDiscorsi20.reset();
                                                                    label20.remove();
                                                                    label20 = null;
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
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),
                                                    // Passa su 9 e 8
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[9]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    // Ritorna all'animazione normale
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[0]));
                                                            pokemonImageBot.setVisible(true);
                                                            String discorso20 = "Oh No! Il pokemon selvatico si e' liberato!";
                                                            labelDiscorsi20 = new LabelDiscorsi(discorso20, dimMax, 0,
                                                                    true,
                                                                    false);
                                                            label20 = labelDiscorsi20.getLabel();
                                                            stage.addActor(label20);

                                                            Timer.schedule(new Timer.Task() {
                                                                @Override
                                                                public void run() {
                                                                    labelDiscorsi20.reset();
                                                                    label20.remove();
                                                                    label20 = null;
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
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),
                                                    // Passa su 9 e 8
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[9]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),
                                                    // Passa su 7 e 8 di nuovo
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    // Torna a 0 dopo 0.5 secondi
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[0]));
                                                            pokemonImageBot.setVisible(true);
                                                            String discorso20 = "Oh No! Il pokemon selvatico si e' liberato!";
                                                            labelDiscorsi20 = new LabelDiscorsi(discorso20, dimMax, 0,
                                                                    true,
                                                                    false);
                                                            label20 = labelDiscorsi20.getLabel();
                                                            stage.addActor(label20);

                                                            Timer.schedule(new Timer.Task() {
                                                                @Override
                                                                public void run() {
                                                                    labelDiscorsi20.reset();
                                                                    label20.remove();
                                                                    label20 = null;
                                                                    utilizzoMossaBot(false, null);
                                                                }
                                                            }, 2.5f);
                                                        }
                                                    }),

                                                    // Solita animazione
                                                    Actions.delay(0.5f),
                                                    Actions.removeActor()));
                                        }

                                else if (nVibrazioni == 4) {
                                            // Se nVibrazioni è 4, esegue l'animazione specifica
                                            imageBallLanciata.addAction(Actions.sequence(
                                                    // Passa su 7 e 8
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),

                                                    // Passa su 9 e 8
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[9]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),
                                                    // Passa su 7 e 8
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[7]));
                                                        }
                                                    }),
                                                    Actions.delay(0.25f),
                                                    Actions.run(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            imageBallLanciata.setDrawable(
                                                                    new TextureRegionDrawable(ballLanciata[8]));
                                                        }
                                                    }),

                                                    Actions.delay(0.5f),

                                                    Actions.sequence(
                                                            Actions.delay(0.25f),
                                                            Actions.run(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    imageBallLanciata.setDrawable(
                                                                            new TextureRegionDrawable(
                                                                                    ballLanciata[10]));
                                                                }
                                                            }),
                                                            Actions.delay(0.03f),
                                                            Actions.run(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    imageBallLanciata.setDrawable(
                                                                            new TextureRegionDrawable(
                                                                                    ballLanciata[11]));
                                                                    String discorso21 = "Hai catturato " + nameBot
                                                                            + "!";
                                                                    conosciPokemon(nameBot);
                                                                    labelDiscorsi21 = new LabelDiscorsi(discorso21,
                                                                            dimMax,
                                                                            0, true, false);
                                                                    label21 = labelDiscorsi21.getLabel();
                                                                    stage.addActor(label21);
                                                                    String poke = controllaSquadra();
                                                                    if (poke != null) {
                                                                        scalvaPokemonInSquadra(poke);
                                                                    } else {
                                                                        salvaPokemonNelBox();
                                                                    }

                                                                    Timer.schedule(new Timer.Task() {
                                                                        @Override
                                                                        public void run() {
                                                                            labelDiscorsi21.reset();
                                                                            if (label21 != null)
                                                                                label21.remove();
                                                                            label21 = null;
                                                                            calcoloEsperienzaVinta(2);

                                                                        }
                                                                    }, 2.5f);
                                                                }
                                                            }))

                                ));
                                        }
                                    }
                                })

                        ));

                        cancel();
                    }
                }
            }, 0, Gdx.graphics.getDeltaTime());
        } catch (Exception e) {
            System.out.println("Errore lanciaCatturaBall battle, " + e);
        }

    }

    // prima faccio in modo di controllare se c'è almeno un elemento libero
    private String controllaSquadra() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            // Itera su poke1, poke2, ..., poke6
            for (int i = 1; i <= 6; i++) {
                String pokeKey = "poke" + i;
                JsonValue poke = json.get(pokeKey);

                if (poke != null) {
                    String nomePokemon = poke.getString("nomePokemon", "");

                    // Se il nomePokemon è vuoto, ritorna la chiave del Pokémon (ad esempio "poke1")
                    if (nomePokemon.isEmpty()) {
                        return pokeKey;
                    }
                }
            }

            // Se nessun nomePokemon è vuoto, ritorna null
            return null;

        } catch (Exception e) {
            System.out.println("Errore controlloSQuadra battle, " + e);
            return null;
        }
    }

    private void scalvaPokemonInSquadra(String pokemon) {
        try {

            System.out.println(pokemon);
            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue newPokemon = new JsonValue(JsonValue.ValueType.object);
            newPokemon.addChild("nomePokemon", new JsonValue(nameBot));
            newPokemon.addChild("livello", new JsonValue(LVPokeBot));
            newPokemon.addChild("esperienza", new JsonValue(0));

            JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
            statistiche.addChild("hp", new JsonValue(Integer.parseInt(currentPokeHPBot)));
            statistiche.addChild("hpTot", new JsonValue(Integer.parseInt(maxPokeHPBot)));
            statistiche.addChild("attack", new JsonValue(statsBot.get(0)));
            statistiche.addChild("defense", new JsonValue(statsBot.get(1)));
            statistiche.addChild("special_attack", new JsonValue(statsBot.get(2)));
            statistiche.addChild("special_defense", new JsonValue(statsBot.get(3)));
            statistiche.addChild("speed", new JsonValue(statsBot.get(4)));
            newPokemon.addChild("statistiche", statistiche);

            JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
            evStats.addChild("Hp", new JsonValue(0));
            evStats.addChild("Att", new JsonValue(0));
            evStats.addChild("Dif", new JsonValue(0));
            evStats.addChild("Spec", new JsonValue(0));
            evStats.addChild("Vel", new JsonValue(0));

            newPokemon.addChild("ev", evStats);

            newPokemon.addChild("iv", pokeIvBot);

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

            json.remove(pokemon);
            json.addChild(pokemon, newPokemon);

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

        } catch (Exception e) {
            System.out.println("Errore salvaPokemonInSquadra battle, " + e);
        }
    }

    private void salvaPokemonNelBox() {
        try {

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
            newPokemon.addChild("esperienza", new JsonValue("0"));

            JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
            statistiche.addChild("hp", new JsonValue(currentPokeHPBot));
            statistiche.addChild("hpTot", new JsonValue(Integer.parseInt(maxPokeHPBot)));
            statistiche.addChild("attack", new JsonValue(statsBot.get(0)));
            statistiche.addChild("defense", new JsonValue(statsBot.get(1)));
            statistiche.addChild("special_attack", new JsonValue(statsBot.get(2)));
            statistiche.addChild("special_defense", new JsonValue(statsBot.get(3)));
            statistiche.addChild("speed", new JsonValue(statsBot.get(4)));
            newPokemon.addChild("statistiche", statistiche);

            JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
            evStats.addChild("Hp", new JsonValue(0));
            evStats.addChild("Att", new JsonValue(0));
            evStats.addChild("Dif", new JsonValue(0));
            evStats.addChild("Spec", new JsonValue(0));
            evStats.addChild("Vel", new JsonValue(0));

            newPokemon.addChild("ev", evStats);

            newPokemon.addChild("iv", pokeIvBot);

            JsonValue mosseJson = new JsonValue(JsonValue.ValueType.array);
            for (Mossa mossaBot : listaMosseBot) {
                JsonValue mossa = new JsonValue(JsonValue.ValueType.object);
                mossa.addChild("nome", new JsonValue(mossaBot.getNome()));
                mossa.addChild("tipo", new JsonValue(mossaBot.getTipo()));
                mossa.addChild("ppTot", new JsonValue(Integer.parseInt(mossaBot.getmaxPP())));
                mossa.addChild("ppAtt", new JsonValue(mossaBot.getattPP()));
                mosseJson.addChild(mossa);
            }
            newPokemon.addChild("mosse", mosseJson);

            newPokemon.addChild("tipoBall", new JsonValue(nomeBall));
            newPokemon.addChild("x", new JsonValue(x));

            json.addChild(Integer.toString(i), newPokemon);

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore salvaPokemonNelBox battle, " + e);
        }

    }

    public void modificaContPerText() {
        counterForNextMove = 2;
    }

    public void modificaPPMossePoke(int numero, ArrayList<Mossa> listaMossePoke) {
        try {

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
                        mossaJson.addChild("ppAtt", new JsonValue(mossa.getattPP())); // Aggiorna attPP con il nuovo
                                                                                      // valore
                        break; // Esci dal ciclo una volta trovato e aggiornato l'attPP
                    }
                }
            }

            // Scrivi il JSON aggiornato nel file mantenendo la formattazione
            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore modificaPPMossePoke battle, " + e);
        }

    }

    private void calcoloEsperienzaVinta(int nextFunction) {

        int skipExp = 0;
        int lvNumber = 0;

        Experience esperienza = new Experience();
        float a = 1f;
        int b;
        if (isBotFight) {
            a = 1.5f;
        }

        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("pokemon/Pokemon.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            JsonValue pokeJson = json.get(nomePokeBot);
            JsonValue pokeJson2 = json.get(nomePoke);

            int crescitaType = pokeJson2.getInt("crescita");
            b = pokeJson.getInt("espBase");
            int L = Integer.parseInt(LVPokeBot);
            int s = pokeInBattaglia.size();

            int esperienzaVinta = esperienza.calcoloEsperienzaGuadagnato(a, b, L, s);

            for (int i = 0; i < pokeInBattaglia.size(); i++) {

                FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
                String jsonString2 = file2.readString();
                JsonValue json2 = new JsonReader().parse(jsonString2);
                String nomePokeEsp = json2.get("poke" + pokeInBattaglia.get(i)).getString("nomePokemon");

                if (json2.get("poke" + pokeInBattaglia.get(i)).getInt("livello") != 100) {

                    int expMaxLvl = calcoloEspMaxLivello(crescitaType, Integer.parseInt(LVPoke));
                    nuovaEsperienza = json2.get("poke" + pokeInBattaglia.get(i)).getInt("esperienza") + esperienzaVinta; // +10000
                                                                                                                         // per
                                                                                                                         // i
                                                                                                                         // test
                    int nuovaEsperienzaCheck = nuovaEsperienza;
                    int expMaxLvlCheck = expMaxLvl;
                    int LVPokeCheck = Integer.parseInt(LVPoke);

                    pokeInBattagliaLU.add(lvNumber);
                    pokeInBattagliaNLevelUp.add(0);

                    while (nuovaEsperienzaCheck >= expMaxLvlCheck) {
                        ritardoLvUp++;
                        pokeInBattagliaNLevelUp.set(i, (pokeInBattagliaNLevelUp.get(i) + 1));
                        lvNumber++;
                        nuovaEsperienzaCheck -= expMaxLvlCheck; // Sottrae l'esperienza necessaria per il livello
                                                                // corrente
                        LVPokeCheck++;
                        expMaxLvlCheck = calcoloEspMaxLivello(crescitaType, LVPokeCheck);
                    }

                    final int index = i;

                    float partialDelay = 3f * i + 2.9f * pokeInBattagliaLU.get(index);
                    partialTimer1(partialDelay, esperienzaVinta, expMaxLvl, i);

                } else {
                    skipExp++;
                }
            }

            float delayTot = 3f * pokeInBattaglia.size() - 3f * skipExp + 2.9f * ritardoLvUp;
            timerTotal1(delayTot, nextFunction);
        } catch (Exception e) {
            System.out.println("Errore calcolaEsperienzaVinta battle, " + e);
        }

    }

    private void aumentoLivello(int i) {
        if (continueLVOperations == false) {
            numberOfLVtoUp = i;
            checkNextLV = true;
            return;
        }

        try {

            //// System.out.println("Da fare ancora :)");
            // Apre il file JSON
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);

            // Accedi all'oggetto JSON desiderato
            JsonValue pokeObject = json2.get("poke" + pokeInBattaglia.get(i));

            // Recupera il valore di livello corrente come stringa, convertilo in int
            int livello = Integer.parseInt(pokeObject.getString("livello"));

            // Incrementa il livello di 1 e aggiorna il JSON
            pokeObject.remove("livello"); // Rimuove il campo "livello"
            pokeObject.addChild("livello", new JsonValue(String.valueOf(livello + 1))); // Aggiunge il nuovo valore come
                                                                                        // stringa

            /*
             * //System.out.println(index);
             * //System.out.println(pokeInBattaglia.get(index));
             * //System.out.println(numeroIndexPoke);
             */
            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

            Stats statsM = new Stats();

            statsM.aggiornaStatistichePokemon(pokeInBattaglia.get(i));

            if (pokeInBattaglia.get(i) == numeroIndexPoke) {
                FileHandle file3 = Gdx.files.local("assets/ashJson/squadra.json");
                String jsonString3 = file3.readString();
                JsonValue json3 = new JsonReader().parse(jsonString3);
                // Recupera il Pokémon da modificare
                JsonValue poke = json3.get("poke" + pokeInBattaglia.get(i));
                // Statistiche del Pokémon
                JsonValue stats = poke.get("statistiche");
                int hpTot = stats.getInt("hpTot");
                int hp = stats.getInt("hp");

                labelLV.setText(livello + 1); // Aggiorna il testo della label
                labelHP.setText(hp);
                labelHPTot.setText(hpTot);
            }
            // Salva il file JSON con il livello aggiornato

            String discorso23 = pokeObject.getString("nomePokemon") + " e' salito al livello " + (livello + 1) + " !";
            labelDiscorsi23 = new LabelDiscorsi(discorso23, dimMax, 0, true, false);
            labelDiscorsi22.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label23 = labelDiscorsi23.getLabel();
            stage.addActor(label23);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi23.reset();
                    if (label23 != null) {
                        label23.remove();
                        label23 = null;
                    }
                    if (label23 != null) {
                        labelDiscorsi23.reset();
                        label23.remove();
                        label23 = null;
                    }

                    for (int i = 0; i < timerCreatedDelay.size(); i++) {
                        if (timerCreated.get(i) != 2) {
                            timerCreatedDelay.set(i, (timerCreatedDelay.get(i) - 2.9f));
                        }
                    }

                    FileHandle file = Gdx.files.local("pokemon/Pokemon.json");
                    String jsonString = file.readString();
                    // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
                    JsonValue json = new JsonReader().parse(jsonString);
                    // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
                    JsonValue pokeJson2 = json.get(nomePoke);

                    float livelloIncrementato = (livello + 1) / 2.0f;
                    String chiave = "EVO" + livelloIncrementato;

                    if (pokeJson2.get("mosseImparabili").has(chiave)) {
                        checkPerEvo = true;
                        pokeEvo.add(pokeJson2.get("mosseImparabili").getString(chiave));
                        pokeEvoIndex.add(pokeInBattaglia.get(i));
                    }

                    if ((livello + 1) % 2 == 0) {
                        // continueLVOperations=false; lo lacio perchè sto pezzente mi ha fatto perdere
                        // mezz'ora; mannaggia a me che metto codice a tentativi
                        apprendimentoMosse = new ApprendimentoMosse(Battle.this, stage, pokeInBattaglia.get(i));
                    }
                }
            }, 2.9f);

            if (pokeInBattaglia.get(i) == numeroIndexPoke) {
                if (pokeInBattagliaNLevelUp.get(i) > 1) {
                    pokeInBattagliaNLevelUp.set(i, (pokeInBattagliaNLevelUp.get(i) - 1));
                    updateExpBar(true, i);
                } else {
                    // System.out.println("sadh");
                    updateExpBar(false, i);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore aumentoLivello battle, " + e);
        }

    }

    private int calcoloEspMaxLivello(int crescitaType, int livello) {

        Experience esperienza = new Experience();

        int espMaxLvl = 0;

        try {

            switch (crescitaType) {
                case 0:
                    espMaxLvl = esperienza.irregolare(livello);
                    break;
                case 1:
                    espMaxLvl = esperienza.medio_Lenta(livello);
                    break;
                case 2:
                    espMaxLvl = esperienza.lenta(livello);
                    break;
                case 3:
                    espMaxLvl = esperienza.fluttuante(livello);
                    break;
                case 4:
                    espMaxLvl = esperienza.veloce(livello);
                    break;
                case 5:
                    espMaxLvl = esperienza.medio_Veloce(livello);
                    break;
            }

            return espMaxLvl;
        } catch (Exception e) {
            System.out.println("Errore calcolaEspMaxLivello battle, " + e);
            return 0;
        }

    }

    private Image placeExpBar(Image image) {
        try {

            FileHandle file = Gdx.files.local("pokemon/Pokemon.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            JsonValue pokeJson = json.get(nomePoke);
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);

            int crescitaType = pokeJson.getInt("crescita");
            int currentExp = json2.get("poke" + (numeroIndexPoke)).getInt("esperienza");
            int maxExp = calcoloEspMaxLivello(crescitaType, Integer.parseInt(LVPoke));

            float percentualeExp = (float) currentExp / maxExp;
            float lunghezzaExpBar = 96 * 2 * percentualeExp;

            Image expBar = new Image(new TextureRegionDrawable(new TextureRegion(asset.getBattle(Assets.WHITE_PX))));
            expBar.setSize((int) lunghezzaExpBar, 4);
            expBar.setPosition(image.getX() + 48, image.getY() + 6);

            Color coloreExpBar = new Color(72 / 255f, 168 / 255f, 208 / 255f, 1); // colore barra esperienza

            expBar.setColor(coloreExpBar);
            // Aggiungi expBar allo stage
            stage.addActor(expBar);

            return expBar;
        } catch (Exception e) {
            System.out.println("Errore placeExpBar battle, " + e);
            return null;
        }

    }

    private void updateExpBar(boolean lvChange, int indexLV) {
        if (continueLVOperations == false) {
            numberOfLVtoUp = indexLV;
            checkNextLV = true;
            return;
        }

        try {

            FileHandle file = Gdx.files.local("pokemon/Pokemon.json");
            String jsonString = file.readString();
            JsonValue json = new JsonReader().parse(jsonString);

            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            JsonValue pokeJson = json.get(nomePoke);
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);

            int crescitaType = pokeJson.getInt("crescita");
            int currentExp = json2.get("poke" + (numeroIndexPoke)).getInt("esperienza");
            int maxExp = calcoloEspMaxLivello(crescitaType, Integer.parseInt(LVPoke));

            if (lvChange) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        expPlayer.addAction(Actions.sizeTo(96 * 2, expPlayer.getHeight(), 1.5f));
                        //// System.out.println("Animazione estesa");
                    }
                }, 0);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        expPlayer.addAction(Actions.sizeTo(0, expPlayer.getHeight(), 0f));
                        //// System.out.println("Reset completato");
                    }
                }, 1.5f); // Dopo 1.5 secondi

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        //// System.out.println("Delay completato");
                        aumentoLivello(indexLV);
                    }
                }, 2.9f); // Dopo 1.5f + 1.4f = 2.9 secondi

            } else {
                // System.out.println("fdf");
                // Calcola la percentuale dell'esperienza e la lunghezza della barra
                float percentualeExp = (float) currentExp / maxExp;
                float lunghezzaExpBar = 96 * 2 * percentualeExp;
                // Anima normalmente la barra fino alla lunghezza calcolata
                expPlayer.addAction(Actions.sizeTo(lunghezzaExpBar, expPlayer.getHeight(), 1.5f));
            }
        } catch (Exception e) {
            System.out.println("Errore updateExpBar battle, " + e);
        }

    }

    private void updateEV() {

        try {

            FileHandle file = Gdx.files.local("pokemon/Pokemon.json");
            String jsonString = file.readString();
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get(nomePokeBot);

            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            JsonValue poke = json2.get("poke" + (numeroIndexPoke));

            int evHp = Math.min(poke.get("ev").getInt("Hp") + pokeJson.get("stat").getInt("PS"), 65536);
            int evAtt = Math.min(poke.get("ev").getInt("Att") + pokeJson.get("stat").getInt("Att"), 65536);
            int evDif = Math.min(poke.get("ev").getInt("Dif") + pokeJson.get("stat").getInt("Dif"), 65536);
            int evSpec = Math.min(poke.get("ev").getInt("Spec")
                    + ((pokeJson.get("stat").getInt("AttS") + pokeJson.get("stat").getInt("DifS")) / 2), 65536);
            int evVel = Math.min(poke.get("ev").getInt("Vel") + pokeJson.get("stat").getInt("Vel"), 65536);

            JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
            evStats.addChild("Hp", new JsonValue(evHp));
            evStats.addChild("Att", new JsonValue(evAtt));
            evStats.addChild("Dif", new JsonValue(evDif));
            evStats.addChild("Spec", new JsonValue(evSpec));
            evStats.addChild("Vel", new JsonValue(evVel));

            json2.get("poke" + (numeroIndexPoke)).remove("ev");
            json2.get("poke" + (numeroIndexPoke)).addChild("ev", evStats);

            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore updateEV battle, " + e);
        }

    }

    private void timerTotal1(float delay, int nextFunction) {
        try {

            timerCreated.add(0);
            timerCreatedDelay.add(delay);
            timerCreatedData.add(new timerData(nextFunction, -1, -1));

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    pokeInBattaglia.clear();
                    pokeInBattagliaLU.clear();
                    pokeInBattaglia.add(numeroIndexPoke);
                    ritardoLvUp = 0;

                    if (nextFunction == 0) {
                        fineBattaglia();
                    } else if (nextFunction == 1) {
                        botHPBar.remove();
                        HPbot.remove();
                        // Rimuovi labelNomePokemonBot
                        labelNomeHPBars.remove(labelNomePokemonBot);
                        labelNomePokemonBot.remove();
                        // Rimuovi labelLVBot
                        labelNomeHPBars.remove(labelLVBot);
                        labelLVBot.remove();
                        leggiPokeBot(nameBot, numeroIndexPokeBot);
                        if (!isBattleEnded) {
                            piazzaLabelLottaPerBot();
                            checkInt--;
                            showBallBot();
                            checkPerDoppiaBarra++;
                            HPbot = placeHpBar(botHPBar, 100, 16, currentPokeHPBot, maxPokeHPBot);
                        }
                    } else if (nextFunction == 2) {
                        isBattleEnded = true;
                        dispose();
                    }
                }
            }, delay);
        } catch (Exception e) {
            System.out.println("Errore timerTotal1 battle, " + e);
        }

    }

    private void partialTimer1(float partialDelay, int esperienzaVinta, int expMaxLvl, int i) {

        try {

            timerCreated.add(1);
            timerCreatedDelay.add(partialDelay);
            timerCreatedData.add(new timerData(esperienzaVinta, expMaxLvl, i));

            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            String nomePokeEsp = json2.get("poke" + pokeInBattaglia.get(i)).getString("nomePokemon");

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    String discorso22 = nomePokeEsp + " ha guadagnato " + esperienzaVinta + " punti esperienza.";
                    labelDiscorsi22 = new LabelDiscorsi(discorso22, dimMax, 0, true, false);
                    //// System.out.println("a");
                    labelDiscorsi22.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                               // alto di quello degli altri attori
                    label22 = labelDiscorsi22.getLabel();
                    stage.addActor(label22);
                    if (nuovaEsperienza >= expMaxLvl) {
                        // Aggiorna il valore di "esperienza" nel JSON
                        json2.get("poke" + pokeInBattaglia.get(index)).remove("esperienza");
                        while (nuovaEsperienza >= expMaxLvl) {
                            nuovaEsperienza = nuovaEsperienza - expMaxLvl;
                        }
                        json2.get("poke" + pokeInBattaglia.get(index)).addChild("esperienza",
                                new JsonValue(nuovaEsperienza));

                        file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                        if (pokeInBattaglia.get(index) == numeroIndexPoke) {
                            updateExpBar(true, index);
                        } else {
                            partialTimer2(index);
                        }
                    } else {
                        // Aggiorna il valore di "esperienza" nel JSON
                        json2.get("poke" + pokeInBattaglia.get(index)).remove("esperienza");
                        json2.get("poke" + pokeInBattaglia.get(index)).addChild("esperienza",
                                new JsonValue(nuovaEsperienza));

                        file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                        if (pokeInBattaglia.get(index) == numeroIndexPoke) {
                            updateExpBar(false, index);
                        }
                    }
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            //// System.out.println("b");
                            labelDiscorsi22.reset();
                            if (label22 != null) {
                                label22.remove();
                            }
                            label22 = null;

                            for (int i = 0; i < timerCreatedDelay.size(); i++) {
                                if (timerCreated.get(i) != 2) {
                                    timerCreatedDelay.set(i, (timerCreatedDelay.get(i) - 2.9f));
                                }
                            }
                        }
                    }, 2.9f);
                }
            }, partialDelay);
        } catch (Exception e) {
            System.out.println("Errore partitaTimer1 battle, " + e);
        }

    }

    public void partialTimer2(int index) {
        try {

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    aumentoLivello(index);
                }
            }, 2.8f);
        } catch (Exception e) {
            System.out.println("Errore partialTimer2 battle, " + e);
        }

    }

    public void cancelAP() {
        apprendimentoMosse = null;
        Gdx.input.setInputProcessor(stage);
    }

    public void destroyTimers() {
        continueLVOperations = false;
        Timer.instance().clear();
    }

    public void reCreateTimers() {
        try {

            // Create temporary copies of the lists before clearing the originals
            ArrayList<Integer> tempTimerCreated = new ArrayList<>(timerCreated);
            ArrayList<timerData> tempTimerCreatedData = new ArrayList<>(timerCreatedData);
            ArrayList<Float> tempTimerCreatedDelay = new ArrayList<>(timerCreatedDelay);

            // Clear the original lists
            timerCreated.clear();
            timerCreatedData.clear();
            timerCreatedDelay.clear();

            // System.out.println(checkNextLV);
            if (checkNextLV) {
                continueLVOperations = true;
                aumentoLivello(numberOfLVtoUp);
                checkNextLV = false;
            }
            // Iterate over the copied lists to perform operations
            for (int i = 0; i < tempTimerCreated.size(); i++) {
                if (tempTimerCreatedDelay.get(i) > 0f) {
                    if (tempTimerCreated.get(i) == 0) {
                        timerTotal1(tempTimerCreatedDelay.get(i), tempTimerCreatedData.get(i).getFirst());
                    } else if (tempTimerCreated.get(i) == 1) {
                        partialTimer1(
                                tempTimerCreatedDelay.get(i),
                                tempTimerCreatedData.get(i).getFirst(),
                                tempTimerCreatedData.get(i).getSecond(),
                                tempTimerCreatedData.get(i).getThird());
                    }
                }
            }

            // continueLVOperations=true;
            tempTimerCreated.clear();
            tempTimerCreatedData.clear();
            tempTimerCreatedDelay.clear();
        } catch (Exception e) {
            System.out.println("Errore reCreateTimes battle, " + e);
        }

    }

    public void piazzaLabel24(String nomePoke, String nomeMossa) {
        String discorso24 = nomePoke + " sta cercando di imparare " + nomeMossa + ", ma " + nomePoke
                + " conosce gia' 4 mosse. Quale mossa deve sostituire?";

        try {

            labelDiscorsi24 = new LabelDiscorsi(discorso24, dimMax, 0, true, false);
            labelDiscorsi24.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label24 = labelDiscorsi24.getLabel();
            stage.addActor(label24);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi24.reset();
                    label24.remove();
                    label24 = null;
                }
            }, 7f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel24 battle, " + e);
        }

    }

    public void piazzaLabel25(String nomePoke, String nomeMossa, String nomeMossaVecchia) {
        String discorso25 = "Uno, due e... ...Ta-da! " + nomePoke + " ha dimenticato " + nomeMossaVecchia
                + "... Al suo posto ha imparato " + nomeMossa + "!";

        try {

            labelDiscorsi25 = new LabelDiscorsi(discorso25, dimMax, 0, true, false);
            labelDiscorsi25.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label25 = labelDiscorsi25.getLabel();
            stage.addActor(label25);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi25.reset();
                    label25.remove();
                    label25 = null;
                }
            }, 10f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel25 battle, " + e);
        }

    }

    public void piazzaLabel26(String nomePoke, String nomeMossa) {
        String discorso26 = nomePoke + " ha rinunciato ad imparare " + nomeMossa + ".";

        try {

            labelDiscorsi26 = new LabelDiscorsi(discorso26, dimMax, 0, true, false);
            labelDiscorsi26.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label26 = labelDiscorsi26.getLabel();
            stage.addActor(label26);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi26.reset();
                    label26.remove();
                    label26 = null;
                }
            }, 4f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel26 battle, " + e);
        }

    }

    public void piazzaLabel27(String nomePoke, String nomeMossa) {
        String discorso27 = nomePoke + " ha imparato " + nomeMossa + "!";

        try {

            labelDiscorsi27 = new LabelDiscorsi(discorso27, dimMax, 0, true, false);
            labelDiscorsi27.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto di
                                                       // quello degli altri attori
            label27 = labelDiscorsi27.getLabel();
            stage.addActor(label27);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi27.reset();
                    label27.remove();
                    label27 = null;
                }
            }, 3.5f);
        } catch (Exception e) {
            System.out.println("Errore piazzaLabel27 battle, " + e);
        }

    }

    private void evoluzione(int evoIndex) {
        try {

            // Caricamento dello stato attuale e della grafica di sfondo
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);

            // Rimuove e aggiorna il nome del Pokémon
            String pokeEvoluto = json2.get("poke" + pokeEvoIndex.get(evoIndex)).getString("nomePokemon");
            json2.get("poke" + pokeEvoIndex.get(evoIndex)).remove("nomePokemon");
            json2.get("poke" + pokeEvoIndex.get(evoIndex)).addChild("nomePokemon",
                    new JsonValue(pokeEvo.get(evoIndex)));
            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

            // Aggiorna statistiche del Pokémon
            Stats stats = new Stats();
            stats.aggiornaStatistichePokemon(pokeEvoIndex.get(evoIndex));

            // Imposta lo sfondo dell'animazione
            Texture backgroundTexture = new Texture("sfondo/evolutionBG.png");
            Image background = new Image(backgroundTexture);
            background.setZIndex(100);
            background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(background);

            Texture lightTexture = asset.getBattle(Assets.CIRCLE_LG);

            TextureRegion lightRegion = new TextureRegion(lightTexture, 0, 0, lightTexture.getWidth(),
                    lightTexture.getHeight());
            // Create the Pokémon image
            Image lightSphere = new Image(lightRegion);
            lightSphere.setSize(400, 400);
            lightSphere.setPosition(
                    (Gdx.graphics.getWidth() / 2 - lightSphere.getWidth() / 2),
                    (Gdx.graphics.getHeight() / 2 - lightSphere.getHeight() / 2));
            lightSphere.setOrigin(Align.center);
            lightSphere.setColor(1, 1, 1, 0); // Start fully transparent
            stage.addActor(lightSphere);

            // Load the Pokémon texture and prepare it for Pixmap extraction
            Texture pokeTexture = new Texture("pokemon/" + pokeEvoluto + ".png");
            int widthQuarter = pokeTexture.getWidth() / 4;

            TextureRegion pokeRegion = new TextureRegion(pokeTexture, 0, 0, widthQuarter, pokeTexture.getHeight());
            // Create the Pokémon image
            Image evolvingPoke = new Image(pokeRegion);
            evolvingPoke.setOrigin(Align.center);
            evolvingPoke.setSize(200, 200);
            evolvingPoke.setPosition(
                    (Gdx.graphics.getWidth() / 2 - evolvingPoke.getWidth() / 2),
                    (Gdx.graphics.getHeight() / 2 - evolvingPoke.getHeight() / 2));
            stage.addActor(evolvingPoke);

            TextureData textureData = pokeTexture.getTextureData();

            if (!textureData.isPrepared()) {
                textureData.prepare(); // Prepare the texture data for pixmap access
            }

            // Retrieve the Pixmap from the texture data
            Pixmap pokePixmap = textureData.consumePixmap(); // Now we can get the pixmap safely

            // Create a white Pixmap with the same dimensions and transparency
            Pixmap whitePixmap = new Pixmap(widthQuarter, pokeTexture.getHeight(), Pixmap.Format.RGBA8888);
            for (int x = 0; x < widthQuarter; x++) {
                for (int y = 0; y < pokeTexture.getHeight(); y++) {
                    int pixel = pokePixmap.getPixel(x, y);
                    int alpha = pixel & 0x000000FF; // Extract the alpha component
                    whitePixmap.drawPixel(x, y, (0xFFFFFF00 | alpha)); // Set RGB to white, keep original alpha
                }
            }

            // Create a white texture from the white pixmap
            Texture whiteTexture = new Texture(whitePixmap);
            pokePixmap.dispose(); // Dispose original pixmap to free resources
            whitePixmap.dispose(); // Dispose white pixmap after creating the texture

            // Continue with the rest of the setup using whiteTexture for the white overlay
            TextureRegion whiteRegion = new TextureRegion(whiteTexture);
            Image whiteOverlay = new Image(whiteRegion); // Create an overlay image with white shape
            whiteOverlay.setSize(200, 200);
            whiteOverlay.setPosition(
                    (Gdx.graphics.getWidth() / 2 - whiteOverlay.getWidth() / 2),
                    (Gdx.graphics.getHeight() / 2 - whiteOverlay.getHeight() / 2));
            whiteOverlay.setOrigin(Align.center);
            whiteOverlay.setColor(1, 1, 1, 0); // Start fully transparent
            stage.addActor(whiteOverlay);

            // Testo dell'evoluzione
            String discorso28 = "Cosa? " + pokeEvoluto + " si sta evolvendo!";
            labelDiscorsi28 = new LabelDiscorsi(discorso28, 200, 0, true, false);
            label28 = labelDiscorsi28.getLabel();
            label28.setZIndex(100);
            stage.addActor(label28);

            // Rimozione del testo e animazione finale
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi28.reset();
                    label28.remove();
                    label28 = null;

                    evolvingPoke.addAction(Actions.fadeOut(0f));

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            evolvingPoke.addAction(Actions.fadeIn(0f));
                        }
                    }, 11f);

                    // Add the fade-in and fade-out effect to the white overlay
                    whiteOverlay.addAction(Actions.sequence(
                            Actions.parallel(
                                    Actions.fadeIn(2f),
                                    Actions.repeat(2, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.5f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.5f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.3f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.3f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.1f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.1f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1f)))),
                            Actions.parallel(
                                    Actions.repeat(2, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.8f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.8f)))),
                            Actions.parallel(
                                    Actions.repeat(3, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.5f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.5f)))),
                            Actions.parallel(
                                    Actions.repeat(5, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.3f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.3f)))),
                            Actions.fadeOut(0f)));

                    // Add the fade-in and fade-out effect to the white overlay
                    lightSphere.addAction(Actions.sequence(
                            Actions.parallel(
                                    Actions.fadeIn(2f),
                                    Actions.repeat(2, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.5f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.5f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.3f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.3f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1.1f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1.1f)))),
                            Actions.parallel(
                                    Actions.repeat(1, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 1f),
                                            Actions.scaleBy(-0.3f, -0.3f, 1f)))),
                            Actions.parallel(
                                    Actions.repeat(2, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.8f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.8f)))),
                            Actions.parallel(
                                    Actions.repeat(3, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.5f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.5f)))),
                            Actions.parallel(
                                    Actions.repeat(5, Actions.sequence(
                                            Actions.scaleBy(0.3f, 0.3f, 0.3f),
                                            Actions.scaleBy(-0.3f, -0.3f, 0.3f)))),
                            Actions.fadeOut(0f)));

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            // Cambia l'immagine del Pokémon all'immagine evoluta
                            Texture evolvedTexture = new Texture("pokemon/" + pokeEvo.get(evoIndex) + ".png");
                            // Crea un TextureRegion che prende solo il primo quarto della larghezza della
                            // texture evoluta
                            int evolvedWidthQuarter = evolvedTexture.getWidth() / 4;
                            TextureRegion evolvedRegion = new TextureRegion(evolvedTexture, 0, 0, evolvedWidthQuarter,
                                    evolvedTexture.getHeight());

                            // Imposta il TextureRegionDrawable per mostrare solo il primo quarto della
                            // larghezza
                            evolvingPoke.setDrawable(new TextureRegionDrawable(evolvedRegion));
                            // Effetti conclusivi (scintillio e ingrandimento finale)
                            evolvingPoke.addAction(Actions.sequence(
                                    Actions.parallel(
                                            Actions.scaleTo(1.2f, 1.2f, 0.8f),
                                            Actions.alpha(1, 0.8f)),
                                    Actions.parallel(
                                            Actions.scaleTo(1.0f, 1.0f, 0.5f)),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            conosciPokemon(pokeEvo.get(evoIndex));
                                            String discorso29 = "Congratulazioni! " + pokeEvoluto + " si e' evoluto in "
                                                    + pokeEvo.get(evoIndex) + "!";
                                            labelDiscorsi29 = new LabelDiscorsi(discorso29, dimMax, 0, true, false);
                                            label29 = labelDiscorsi29.getLabel();
                                            label29.setZIndex(102);
                                            stage.addActor(label29);
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    labelDiscorsi29.reset();
                                                    label29.remove();
                                                    label29 = null;
                                                }
                                            }, 6f); // Durata dell'animazione principale
                                        }
                                    })));
                        }
                    }, 11f); // Durata
                }
            }, 3.5f); // Durata dell'animazione principale
        } catch (Exception e) {
            System.out.println("Errore evoluzione battle, " + e);
        }

    }

    private void scopriPokemon(String pokeName) {
        try {

            FileHandle file = Gdx.files.local("assets/ashJson/pokemonScoperti.json");
            String jsonString = file.readString();
            JsonValue json = new JsonReader().parse(jsonString);
            int numPokePokedex = 0;

            while (!json.get(numPokePokedex).getString("nome").equalsIgnoreCase(pokeName)) {
                numPokePokedex++;
            }
            if (!json.get(numPokePokedex).getString("incontrato").equalsIgnoreCase("1")) {

                json.get(numPokePokedex).remove("incontrato");
                json.get(numPokePokedex).addChild("incontrato", new JsonValue("0"));

                file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
            }
        } catch (Exception e) {
            System.out.println("Errore scopriPokemon battle, " + e);
        }

    }

    private void conosciPokemon(String pokeName) {
        try {

            FileHandle file = Gdx.files.local("assets/ashJson/pokemonScoperti.json");
            String jsonString = file.readString();
            JsonValue json = new JsonReader().parse(jsonString);
            int numPokePokedex = 1;

            while (!json.get(numPokePokedex).getString("nome").equalsIgnoreCase(pokeName)) {
                numPokePokedex++;
            }

            json.get(numPokePokedex).remove("incontrato");
            json.get(numPokePokedex).addChild("incontrato", new JsonValue("1"));

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore conoscoPokemon battle, " + e);
        }

    }

    public void ricominciaBattagliaDopoCura(String discorso, int indexCurato, String currentHPCura, String maxHPCura,
            int passiCura, int psCuratiCura) {

        try {

            labelDiscorsiCura = new LabelDiscorsi(discorso, dimMax, 0, true, false);
            labelDiscorsiCura.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più alto
                                                         // di
                                                         // quello degli altri attori
            labelCura = labelDiscorsiCura.getLabel();
            stage.addActor(labelCura);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    labelDiscorsi5.reset();
                    if (labelDiscorsiCura != null)
                        labelCura.remove();
                    labelCura = null;
                }
            }, 3f);

            if (indexCurato == numeroIndexPoke) {
                updateHpBarWidthCura(currentHPCura, maxHPCura, passiCura, indexCurato, psCuratiCura);
                leggiPoke(indexCurato);
            }

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    utilizzoMossaBot(false, null);
                }
            }, 2.5f);
        } catch (Exception e) {
            System.out.println("Errore ricominciaBattagliaDOpoCura battle, " + e);
        }

    }

    private void updateHpBarWidthCura(String currentHP, String maxHP, int passi, int index, int psCurati) {
        try {

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
            HPplayer.addAction(Actions.sizeTo(lunghezzaHPBar, HPplayer.getHeight(), 2.5f));

            // Aggiungi un'azione per cambiare il colore della barra
            HPplayer.addAction(Actions.color(coloreHPBar, 2.5f));

            float ritardoTraPassi = 1.25f / passi;

            // Crea un'azione parallela per gestire contemporaneamente l'aggiornamento della
            // larghezza della barra HP e l'animazione del cambiamento di labelHP
            ParallelAction parallelAction = new ParallelAction();

            // Aggiungi un'azione per l'animazione del cambiamento del valore di labelHP
            for (int i = 0; i < passi; i++) {
                int nuovoValore = Integer.parseInt(currentHP) - psCurati + (i + 1);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        labelHP.setText(String.valueOf(nuovoValore));
                    }
                }, i * ritardoTraPassi);
            }

            // Esegui l'azione parallela
            HPplayer.addAction(parallelAction);
        } catch (Exception e) {
            System.out.println("Errore updateHpBarWidhCura battle, " + e);
        }

    }

} // Fine battaglia :)