package com.mercurio.game.pokemon;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetBMLSSC;
import com.mercurio.game.AssetManager.GameAsset.AssetSISCB;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.menu.Borsa;
import com.mercurio.game.menu.BorsaModifier;

public class squadraCure {

    private boolean checkPerRimozione = false;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Array<Actor> squadActors;
    private String currentPokeHPforSquad;
    private String maxPokeHPforSquad;
    private String nomePokeSquad;
    private String LVPoke;
    private boolean battaglia;
    boolean isCursorInside = false;
    private ArrayList<Boolean> booleanList = new ArrayList<>(6);
    private ArrayList<Integer> hpList = new ArrayList<>();
    private Image infoImage;
    private Image cancelImage;
    private Image background;
    private Borsa chiamanteBo;
    private String itemName;
    // private String itemQuantity;
    private int curaPs = 0;
    private int rivitalizzazione = 0;
    private BorsaModifier borsaModifier;
    private LabelDiscorsi labelDiscorsi1;
    private LabelDiscorsi labelDiscorsi2;
    private LabelDiscorsi labelDiscorsi3;
    private LabelDiscorsi labelDiscorsi4;
    private LabelDiscorsi labelDiscorsi5;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    Array<Image> hpBarImages = new Array<>();
    Array<Label> hpLabels = new Array<>();

    Array<Texture> animationTextures = new Array<>();
    Array<Image> animationImages = new Array<>();
    Array<Boolean> animazionePartita = new Array<>();
    Array<Boolean> controllo = new Array<>();

    private GameAsset asset;

    public squadraCure(Stage stage, boolean battaglia, Borsa chiamanteBo, String itemName, String itemQuantity) {
        this.battaglia = battaglia;
        this.asset = chiamanteBo.getAsset();
        this.chiamanteBo = chiamanteBo;
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.stage = stage;
        this.squadActors = new Array<>(); // Inizializza l'array degli attori della borsa
        this.itemName = itemName;
        // this.itemQuantity=itemQuantity;

        asset.loadSISCBAsset();
        asset.finishLoading();

        Gdx.input.setInputProcessor(stage);
        showSquad();
    }

    private void showSquad() {
        try {
            // Carica le texture
            Texture normalTexture = asset.getBMLSSC(AssetBMLSSC.SQ_SQUADRA_NS);
            Texture firstTexture = asset.getBMLSSC(AssetBMLSSC.SQ_FIRST_SQ);
            Texture selectedFirstTexture = asset.getBMLSSC(AssetBMLSSC.SQ_FIRST_SL);
            Texture selectedTexture = asset.getBMLSSC(AssetBMLSSC.SQ_SQUADRA_SL);

            // Posizione iniziale per la prima label
            float initialX = 150;
            float initialY = 470;

            // Spaziatura tra le colonne e le righe
            float columnSpacing = 300;
            float rowSpacing = 150;

            // Larghezza e altezza di un'immagine
            float imageWidth = normalTexture.getWidth();
            float imageHeight = normalTexture.getHeight();

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            // Add background
            Texture backgroundTexture = asset.getBMLSSC(AssetBMLSSC.SF_SFONDO);
            background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
            stage.addActor(background);
            squadActors.add(background);

            // Creazione delle label della squadra
            for (int i = 0; i < 6; i++) {
                leggiPokeSecondario(i + 1);
                if (!nomePokeSquad.isEmpty()) {
                    booleanList.add(false);

                    // Calcola l'indice di colonna e di riga
                    int column = i % 2; // Due colonne
                    int row = i / 2; // Tre righe

                    // Calcola la posizione dell'immagine
                    float posX = initialX + column * (imageWidth + columnSpacing);
                    float posY = initialY - row * (imageHeight + rowSpacing);

                    if (column == 0) {
                        posY += 50;
                    }
                    

                    // Se è la prima posizione, usa un'immagine differente
                    Texture tmp = asset.getBMLSSC(AssetBMLSSC.SQ_SQUADRA_NS);
                    Texture selectedTex = selectedTexture;
                    if (i == 0) {
                        tmp = asset.getBMLSSC(AssetBMLSSC.SQ_FIRST_SQ);
                        selectedTex = selectedFirstTexture;
                    }

                    Image image = new Image(tmp);

                    
                    image.setPosition(posX, posY);
                    image.setSize(126 * 3, 45 * 3);

                    Image hpBar = placeHpBar(image, 63 * 3, 18 * 3, currentPokeHPforSquad, maxPokeHPforSquad);
                    hpBarImages.add(hpBar);

                    Label labelNomePokemon = new Label(nomePokeSquad, new Label.LabelStyle(font, null));
                    labelNomePokemon.setPosition(image.getX() + 90, image.getY() + 93);
                    labelNomePokemon.setFontScale(3.5f);

                    Label labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
                    labelLV.setPosition(image.getX() + 65, image.getY() + 23);
                    labelLV.setFontScale(2.5f);

                    int diff;
                    if (Integer.parseInt(currentPokeHPforSquad) > 99) {
                        diff = 0;
                    } else if (Integer.parseInt(currentPokeHPforSquad) > 9) {
                        diff = 10;
                    } else {
                        diff = 20;
                    }
                    Label labelHP = new Label(currentPokeHPforSquad, new Label.LabelStyle(font, null));
                    labelHP.setPosition(image.getX() + 210 + diff, image.getY() + 25);
                    labelHP.setFontScale(2.5f);
                    hpLabels.add(labelHP);

                    Label labelHPTot = new Label(maxPokeHPforSquad, new Label.LabelStyle(font, null));
                    labelHPTot.setPosition(image.getX() + 278, image.getY() + 25);
                    labelHPTot.setFontScale(2.5f);

                    Texture animationTexture = new Texture("pokemon/" + nomePokeSquad + "Label.png");
                    animationTextures.add(animationTexture);
                    TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0,
                            animationTexture.getWidth() / 2, animationTexture.getHeight());
                    // Crea un'immagine utilizzando solo la prima metà dell'immagine
                    Image animationImage = new Image(animationRegion);
                    animationImage.setSize(animationTexture.getWidth(), animationTexture.getHeight() * 2);
                    animationImage.setPosition(posX + 13, posY + 45 * 3 - 55);
                    animationImages.add(animationImage);

                    animazionePartita.add(false);
                    controllo.add(false);

                    // Aggiungi l'azione per il cambio di texture al passaggio del cursore
                    TextureRegionDrawable normalDrawable = new TextureRegionDrawable(normalTexture);
                    TextureRegionDrawable fisrtDrawable = new TextureRegionDrawable(firstTexture);
                    TextureRegionDrawable selectedDrawable = new TextureRegionDrawable(selectedTex);
                    final int index = i;
                    InputListener imageListener = new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            if (booleanList.get(index) == false) {
                                changeIntoSelected(image, selectedDrawable, index, hpBar, labelLV, labelHP, labelHPTot);
                            }
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            if (booleanList.get(index) == false) {
                                changeIntoNOTSelected(image, index, hpBar, labelLV, labelHP, labelHPTot, animationImage,
                                        animationTexture, fisrtDrawable, normalDrawable);
                            }
                        }
                    };

                    image.addListener(imageListener);
                    stage.addActor(image);
                    squadActors.add(image);

                    hpBar.addListener(imageListener);
                    hpBar.toFront();

                    labelNomePokemon.addListener(imageListener);
                    stage.addActor(labelNomePokemon);
                    squadActors.add(labelNomePokemon);

                    labelLV.addListener(imageListener);
                    stage.addActor(labelLV);
                    squadActors.add(labelLV);

                    labelHP.addListener(imageListener);
                    stage.addActor(labelHP);
                    squadActors.add(labelHP);

                    labelHPTot.addListener(imageListener);
                    stage.addActor(labelHPTot);
                    squadActors.add(labelHPTot);

                    animationImage.addListener(imageListener);
                    stage.addActor(animationImage);
                    squadActors.add(animationImage);

                    final int indexNumPoke = i + 1;
                    image.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (!booleanList.get(index)) {

                                if (infoImage != null) {
                                    infoImage.remove();

                                    checkPerRimozione = true;
                                }

                                for (int i = 0; i < booleanList.size(); i++) {
                                    booleanList.set(i, false);
                                }

                                booleanList.set(index, true);

                                curaPokemon(indexNumPoke);

                            }

                            // Aggiungi un listener all'intero stage per rilevare clic in punti diversi
                            // dall'immagine
                            stage.addListener(new InputListener() {
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    // Ottieni la posizione del clic
                                    float clickX = event.getStageX();
                                    float clickY = event.getStageY();

                                    // Verifica se il clic è avvenuto all'interno dell'area dell'immagine
                                    // selezionata
                                    if (isCursorInside) {
                                        float imageX = image.getX();
                                        float imageY = image.getY();
                                        float imageWidth = image.getWidth();
                                        float imageHeight = image.getHeight();
                                        if (clickX >= imageX && clickX <= imageX + imageWidth && clickY >= imageY
                                                && clickY <= imageY + imageHeight) {
                                            // Il clic è avvenuto all'interno dell'area dell'immagine selezionata,
                                            // quindi non fare nulla o esegui azioni aggiuntive se necessario
                                            return true; // Consuma l'evento per impedire la rimozione della selezione
                                        }
                                    }
                                    // Rimuovi le immagini "info" e "sposta" quando si clicca in un punto diverso
                                    // dall'immagine
                                    booleanList.set(index, false);
                                    changeIntoNOTSelected(image, index, hpBar, labelLV, labelHP, labelHPTot,
                                            animationImage, animationTexture, fisrtDrawable, normalDrawable);
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            if (!checkPerRimozione) {
                                                if (infoImage != null)
                                                    infoImage.remove();
                                            }
                                            checkPerRimozione = false;
                                        }
                                    }, 0.3f);
                                    // Rimuovi il listener dall'intero stage dopo l'uso
                                    stage.removeListener(this);
                                    return true;
                                }
                            });
                        }
                    });

                }
            }
            // Label "Cancel"
            Texture cancelTexture = asset.getBMLSSC(AssetBMLSSC.SQ_CANCEL);
            cancelImage = new Image(cancelTexture);
            cancelImage.setName("image cancel");
            cancelImage.setPosition(70, 10);
            cancelImage.setSize(56 * 3, 24 * 3);
            cancelImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clearInventoryItems();
                    if (chiamanteBo != null) {
                        chiamanteBo.closeSquadra();
                    }
                }
            });
            stage.addActor(cancelImage);
            squadActors.add(cancelImage);

        } catch (Exception e) {
            System.out.println("Errore showSquad squadracure, " + e);
        }

    }

    public void render() {
        try {
            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

            for (int i = 0; i < animazionePartita.size; i++) {
                if (controllo.get(i)) {
                    startAnimation(i);
                } else {
                    stopAnimation(i);
                }
            }

            if (label1 != null) {
                labelDiscorsi1.renderDisc();
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
            // Disegna la UI della squadra
            stage.draw(); // Disegna lo stage sullo SpriteBatch

        } catch (Exception e) {
            System.out.println("Errore render squadracure, " + e);
        }

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void clearInventoryItems() {
        try {
            // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione
            // precedente
            rivitalizzazione = 0;
            curaPs = 0;
            hpBarImages.clear();
            hpLabels.clear();
            hpList.clear();
            for (Actor actor : squadActors) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }

        } catch (Exception e) {
            System.out.println("Errore clearInventoryItems, " + e);
        }

    }

    public void leggiPokeSecondario(int numero) {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("ashJson/squadra.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get("poke" + numero);
            currentPokeHPforSquad = pokeJson.get("Statistiche").getString("hp");
            hpList.add(Integer.parseInt(currentPokeHPforSquad));
            maxPokeHPforSquad = pokeJson.get("Statistiche").getString("hpTot");
            nomePokeSquad = pokeJson.getString("nomePokemon");
            LVPoke = pokeJson.getString("livello");

        } catch (Exception e) {
            System.out.println("Errore leggiPokeSecondari, " + e);
        }
    }

    public void startAnimation(int index) {
        try {

            if (!animazionePartita.get(index)) {
                animazionePartita.set(index, true);
                Texture animationTexture = animationTextures.get(index); // Ottieni la texture corrispondente all'indice
                Image animationImage = animationImages.get(index); // Ottieni l'immagine corrispondente all'indice
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        TextureRegion newRegion = new TextureRegion(animationTexture, 33, 0,
                                animationTexture.getWidth() / 2, animationTexture.getHeight());
                        animationImage.setDrawable(new TextureRegionDrawable(newRegion));
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0,
                                        animationTexture.getWidth() / 2, animationTexture.getHeight());
                                animationImage.setDrawable(new TextureRegionDrawable(newRegion));
                                if (animazionePartita.size != 0)
                                    animazionePartita.set(index, false);
                            }
                        }, 0.7f);
                    }
                }, 0.7f);
            }

        } catch (Exception e) {
            System.out.println("Errore startAnimation squadraCure, " + e);
        }

    }

    public void stopAnimation(int index) {
        try {
            controllo.set(index, false);
            Texture animationTexture = animationTextures.get(index); // Ottieni la texture corrispondente all'indice
            Image animationImage = animationImages.get(index); // Ottieni l'immagine corrispondente all'indice
            TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2,
                    animationTexture.getHeight());
            animationImage.setDrawable(new TextureRegionDrawable(newRegion));

        } catch (Exception e) {
            System.out.println("Errore stopAnimation squadraCure, " + e);
        }
    }

    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP) {
        try {

            // Calcola la percentuale degli HP attuali rispetto agli HP totali
            float percentualeHP = Float.parseFloat(currentHP) / Float.parseFloat(maxHP);
            float lunghezzaHPBar = 48 * 3 * percentualeHP;
            // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
            Image hpBar = new Image(
                    new TextureRegionDrawable(new TextureRegion(asset.getBMLSSC(AssetBMLSSC.BL_WHITE_PX))));
            hpBar.setSize((int) lunghezzaHPBar, 12);
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
            squadActors.add(hpBar);

            return hpBar;
        } catch (Exception e) {
            System.out.println("Errore placeHPBar squadraCure, " + e);
            return null;
        }

    }

    public void changeIntoSelected(Image image, TextureRegionDrawable selectedDrawable, int index, Image hpBar,
            Label labelLV, Label labelHP, Label labelHPTot) {
        try {
            image.setDrawable(selectedDrawable);
            image.setSize(126 * 3, 49 * 3);
            isCursorInside = true;
            controllo.set(index, true);
            hpBar.setPosition(image.getX() + 63 * 3 - 1, image.getY() + 19 * 3);

        } catch (Exception e) {
            System.out.println("Errore changeInforSelected squadraCure, " + e);
        }
    }

    public void changeIntoNOTSelected(Image image, int index, Image hpBar, Label labelLV, Label labelHP,
            Label labelHPTot, Image animationImage, Texture animationTexture, TextureRegionDrawable fisrtDrawable,
            TextureRegionDrawable normalDrawable) {
        try {
            // Se è il primo elemento, reimposta la texture normale
            if (index == 0) {
                image.setDrawable(fisrtDrawable);
            } else {
                image.setDrawable(normalDrawable);
            }
            image.setSize(126 * 3, 45 * 3);
            isCursorInside = false;
            controllo.set(index, false);
            // ferma animazione
            TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2,
                    animationTexture.getHeight());
            animationImage.setDrawable(new TextureRegionDrawable(newRegion));
            hpBar.setPosition(image.getX() + 63 * 3, image.getY() + 18 * 3);

        } catch (Exception e) {
            System.out.println("Errore  changeIntoNOTSelected squadraCure, " + e);
        }

    }

    // Metodo per aggiornare la larghezza della barra degli HP con un'animazione
    private void updateHpBarWidth(Image hpBar, String currentHP, String maxHP, int passi, int index, int psCurati) {

        try {
            float percentualeHP = Float.parseFloat(currentHP) / Float.parseFloat(maxHP);
            float lunghezzaHPBar = 48 * 3 * percentualeHP;

            Color coloreHPBar;
            if (percentualeHP >= 0.5f) {
                coloreHPBar = Color.GREEN; // Verde se sopra il 50%
            } else if (percentualeHP > 0.15f && percentualeHP < 0.5f) {
                coloreHPBar = Color.YELLOW; // Giallo se tra il 15% e il 50%
            } else {
                coloreHPBar = Color.RED; // Rosso se sotto il 15%
            }
            // Crea un'azione parallela per aggiornare la larghezza della barra
            hpBar.addAction(Actions.sizeTo(lunghezzaHPBar, hpBar.getHeight(), 2.5f));

            // Aggiungi un'azione per cambiare il colore della barra
            hpBar.addAction(Actions.color(coloreHPBar, 2.5f));

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
                        hpLabels.get(index - 1).setText(String.valueOf(nuovoValore));
                    }
                }, i * ritardoTraPassi);
            }

            // Esegui l'azione parallela
            hpBar.addAction(parallelAction);

        } catch (Exception e) {
            System.out.println("Errore updateHpBarWidth squadraCure, " + e);
        }

    }

    private void curaPokemon(int indexCurato) {

        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.internal("oggetti/strumenti.json");
            String jsonString = file.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue cura = json.get("cure").get(itemName);

            try {
                curaPs = cura.getInt("curaPs");
            } catch (Exception e) {
            }
            try {
                rivitalizzazione = cura.getInt("rivitalizzazione");
            } catch (Exception e) {
            }

            // Carica il file JSON
            FileHandle file2 = Gdx.files.local("ashJson/squadra.json");
            String jsonString2 = file2.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json2 = new JsonReader().parse(jsonString2);

            if (curaPs != 0) {

                if (json2.get("poke" + indexCurato).get("statistiche").getString("hp").equals("0")) {
                    String discorso1 = "Non puoi curare un pokemon privo di forze!";
                    labelDiscorsi1 = new LabelDiscorsi(discorso1, 30, 0, false, false);
                    labelDiscorsi1.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                              // alto di quello degli altri attori
                    label1 = labelDiscorsi1.getLabel();
                    stage.addActor(label1);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi1.reset();
                            if (label1 != null)
                                label1.remove();
                            label1 = null;
                        }
                    }, 3f);
                } else if (json2.get("poke" + indexCurato).get("statistiche").getString("hp")
                        .equals("" + json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot"))) {
                    String discorso2 = "La salute del pokemon e' gia' al massimo!";
                    labelDiscorsi2 = new LabelDiscorsi(discorso2, 30, 0, false, false);
                    labelDiscorsi2.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                              // alto di quello degli altri attori
                    label2 = labelDiscorsi2.getLabel();
                    stage.addActor(label2);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi2.reset();
                            if (label2 != null)
                                label2.remove();
                            label2 = null;
                        }
                    }, 3f);
                } else {
                    borsaModifier = new BorsaModifier();
                    borsaModifier.removeInventoryCure(itemName);
                    int hpVecchi = Integer.parseInt(json2.get("poke" + indexCurato).get("statistiche").getString("hp"));

                    int nuoviHP = Integer.parseInt(json2.get("poke" + indexCurato).get("statistiche").getString("hp"))
                            + curaPs;
                    json2.get("poke" + indexCurato).get("statistiche").remove("hp");

                    if (nuoviHP > json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot")) {
                        nuoviHP = json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot");
                    }

                    json2.get("poke" + indexCurato).get("statistiche").addChild("hp", new JsonValue("" + nuoviHP));

                    file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                    String discorso3 = json2.get("poke" + indexCurato).getString("nomePokemon") + " ha ripreso "
                            + (nuoviHP - hpVecchi) + " punti salute.";

                    if (!battaglia) {
                        updateHpBarWidth(hpBarImages.get(indexCurato - 1), ("" + nuoviHP),
                                ("" + json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot")),
                                nuoviHP - hpVecchi, indexCurato, nuoviHP - hpVecchi);

                        labelDiscorsi3 = new LabelDiscorsi(discorso3, 30, 0, false, false);
                        labelDiscorsi3.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore
                                                                  // più alto di quello degli altri attori
                        label3 = labelDiscorsi3.getLabel();
                        stage.addActor(label3);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi3.reset();
                                if (label3 != null)
                                    label3.remove();
                                label3 = null;

                                chiamanteBo.aggiornaQuantity(itemName);
                                clearInventoryItems();
                            }
                        }, 3f);
                    } else {
                        chiamanteBo.ritornaBattaglia(discorso3, indexCurato, "" + nuoviHP,
                                ("" + json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot")),
                                nuoviHP - hpVecchi, nuoviHP - hpVecchi);
                    }
                }
            } else {
                if (json2.get("poke" + indexCurato).get("statistiche").getString("hp").equals("0")) {
                    borsaModifier = new BorsaModifier();
                    borsaModifier.removeInventoryCure(itemName);

                    int nuoviHP = Integer
                            .parseInt(json2.get("poke" + indexCurato).get("statistiche").getString("hpTot"))
                            / (100 / rivitalizzazione);
                    json2.get("poke" + indexCurato).get("statistiche").remove("hp");
                    json2.get("poke" + indexCurato).get("statistiche").addChild("hp", new JsonValue("" + nuoviHP));

                    file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                    String discorso4 = json2.get("poke" + indexCurato).getString("nomePokemon")
                            + " e' tornato in forma!";

                    if (!battaglia) {
                        updateHpBarWidth(hpBarImages.get(indexCurato - 1), ("" + nuoviHP),
                                ("" + json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot")), nuoviHP,
                                indexCurato, nuoviHP);

                        labelDiscorsi4 = new LabelDiscorsi(discorso4, 30, 0, false, false);
                        labelDiscorsi4.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore
                                                                  // più alto di quello degli altri attori
                        label4 = labelDiscorsi4.getLabel();
                        stage.addActor(label4);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                labelDiscorsi4.reset();
                                if (label4 != null)
                                    label4.remove();
                                label4 = null;

                                chiamanteBo.aggiornaQuantity(itemName);
                                clearInventoryItems();
                            }
                        }, 3f);
                    } else {
                        chiamanteBo.ritornaBattaglia(discorso4, indexCurato, "" + nuoviHP,
                                ("" + json2.get("poke" + indexCurato).get("statistiche").getInt("hpTot")), nuoviHP,
                                nuoviHP);
                    }
                } else {
                    String discorso5 = "Il pokemon è gia' in forma!";
                    labelDiscorsi5 = new LabelDiscorsi(discorso5, 30, 0, false, false);
                    labelDiscorsi5.getLabel().setZIndex(100); // Imposta il valore dello z-index su 100 o un valore più
                                                              // alto di quello degli altri attori
                    label5 = labelDiscorsi5.getLabel();
                    stage.addActor(label5);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            labelDiscorsi5.reset();
                            if (label5 != null)
                                label5.remove();
                            label5 = null;
                        }
                    }, 3f);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore curaPokemon squadraCure, " + e);
        }

    }

}
