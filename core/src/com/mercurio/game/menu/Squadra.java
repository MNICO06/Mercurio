package com.mercurio.game.menu;

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
import com.mercurio.game.Screen.MercurioMain;
import com.mercurio.game.pokemon.Battle;
import com.mercurio.game.pokemon.infoPoke;
import com.mercurio.game.AssetManager.GameAsset.AssetSquadra;

public class Squadra {

    private boolean checkPerRimozione = false;
    private infoPoke infoPoke;
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
    private Battle chiamanteB;
    private MenuLabel chiamanteM;
    private Image infoImage;
    private Image spostaImage;
    private Image cambiaImage;
    private Image cancelImage;
    private boolean checkPerSwitch = false;
    private int indexDaSwitch = 0;
    private Image background;
    private boolean cambioObbligatorio;
    private MercurioMain game;
    private GameAsset asset;

    Array<Texture> animationTextures = new Array<>();
    Array<Image> animationImages = new Array<>();
    Array<Boolean> animazionePartita = new Array<>();
    Array<Boolean> controllo = new Array<>();

    public Squadra(Stage stage, boolean battaglia, Battle chiamanteB, MenuLabel chiamanteM,
                   boolean cambioObbligatorio) {
        this.chiamanteB = chiamanteB;
        this.chiamanteM = chiamanteM;
        this.game = chiamanteM.getGame();
        this.asset = game.getGameAsset();
        this.cambioObbligatorio = cambioObbligatorio;
        this.battaglia = battaglia;
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.stage = stage;
        this.squadActors = new Array<>(); // Inizializza l'array degli attori della borsa

        asset.loadSquadraAsset();
        asset.finishLoading();

        Gdx.input.setInputProcessor(stage);
        showSquad();
    }

    private void showSquad() {

        try {
            // Carica le texture
            Texture normalTexture = asset.getSquadra(AssetSquadra.SQ_SQUADRA_NS);
            Texture firstTexture = asset.getSquadra(AssetSquadra.SQ_FIRST_SQ);
            Texture selectedFirstTexture = asset.getSquadra(AssetSquadra.SQ_FIRST_SL);
            Texture selectedTexture = asset.getSquadra(AssetSquadra.SQ_SQUADRA_SL);

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
            Texture backgroundTexture = asset.getSquadra(AssetSquadra.SF_SFONDO);
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

                    Texture tmp = asset.getSquadra(AssetSquadra.SQ_SQUADRA_NS);
                    Texture selectedTex = selectedTexture;
                    if (i == 0) {
                        tmp = asset.getSquadra(AssetSquadra.SQ_FIRST_SQ);
                        selectedTex = selectedFirstTexture;
                    }


                    Image image = new Image(tmp);
                    image.setPosition(posX, posY);
                    image.setSize(126 * 3, 45 * 3);

                    Image hpBar = placeHpBar(image, 63 * 3, 18 * 3, currentPokeHPforSquad, maxPokeHPforSquad);

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
                                    if (!battaglia) {
                                        spostaImage.remove();
                                    } else {
                                        cambiaImage.remove();
                                    }
                                    checkPerRimozione = true;
                                }

                                for (int i = 0; i < booleanList.size(); i++) {
                                    booleanList.set(i, false);
                                }

                                booleanList.set(index, true);

                                if (checkPerSwitch) {
                                    indexDaSwitch = indexNumPoke;
                                }

                                // Crea e posiziona l'immagine "squadra/info.png"
                                Texture infoTexture = asset.getSquadra(AssetSquadra.SQ_INFO);
                                infoImage = new Image(infoTexture);
                                infoImage.setPosition(650, 10); // Posizionamento personalizzato
                                infoImage.setSize(56 * 3, 24 * 3);
                                stage.addActor(infoImage); // Aggiungi allo stage
                                squadActors.add(infoImage); // Aggiungi all'array degli attori della squadra

                                infoImage.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        infoPoke = new infoPoke(stage, indexNumPoke, false, asset);
                                    }
                                });

                                // Crea e posiziona l'immagine "squadra/sposta.png" o di "squadra/cambia.png"
                                Texture spostaTexture = asset.getSquadra(AssetSquadra.SQ_SPOSTA);
                                spostaImage = new Image(spostaTexture);
                                Texture cambiaTexture = asset.getSquadra(AssetSquadra.SQ_CAMBIA);
                                cambiaImage = new Image(cambiaTexture);
                                if (!battaglia) {
                                    spostaImage.setSize(56 * 3, 24 * 3);
                                    spostaImage.setPosition(650 + 56 * 3 + 20, 10); // Posizionamento personalizzato
                                    stage.addActor(spostaImage); // Aggiungi allo stage
                                    squadActors.add(spostaImage); // Aggiungi all'array degli attori della squadra
                                    spostaImage.addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            spostaPoke(indexNumPoke);
                                        }
                                    });
                                } else if (!hpList.get(indexNumPoke - 1).equals(0)) {
                                    cambiaImage.setSize(56 * 3, 24 * 3);
                                    cambiaImage.setPosition(650 + 56 * 3 + 20, 10); // Posizionamento personalizzato
                                    stage.addActor(cambiaImage); // Aggiungi allo stage
                                    squadActors.add(cambiaImage); // Aggiungi all'array degli attori della squadra
                                    cambiaImage.addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            chiamanteB.cambiaPokemon(index);
                                            clearInventoryItems();
                                        }
                                    });
                                }
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
                                    checkPerSwitch = true;
                                    // Rimuovi le immagini "info" e "sposta" quando si clicca in un punto diverso
                                    // dall'immagine
                                    booleanList.set(index, false);
                                    changeIntoNOTSelected(image, index, hpBar, labelLV, labelHP, labelHPTot,
                                            animationImage, animationTexture, fisrtDrawable, normalDrawable);
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            if (!checkPerRimozione) {
                                                infoImage.remove();
                                                if (!battaglia) {
                                                    spostaImage.remove();
                                                } else {
                                                    cambiaImage.remove();
                                                }
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
            Texture cancelTexture = asset.getSquadra(AssetSquadra.SQ_CANCEL);
            cancelImage = new Image(cancelTexture);
            cancelImage.setName("image cancel");
            cancelImage.setPosition(70, 10);
            cancelImage.setSize(56 * 3, 24 * 3);
            cancelImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clearInventoryItems();
                    if (chiamanteM != null) {
                        chiamanteM.closeSquadra();
                    } else if (chiamanteB != null) {
                        chiamanteB.closeSquadra();
                    }
                }
            });

            if (!cambioObbligatorio) { // se non è un cambio obbligatorio da la possibilità di tornare indietro senza
                                       // cambiare pokemon
                stage.addActor(cancelImage);
                squadActors.add(cancelImage);
            }
        } catch (Exception e) {
            System.out.println("Errore showSquad, " + e);
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
            if (infoPoke != null) {
                infoPoke.render();
            }

            // Disegna la UI della squadra
            stage.draw(); // Disegna lo stage sullo SpriteBatch

        } catch (Exception e) {
            System.out.println("Errore render squad, " + e);
        }

    }

    public void dispose() {
        asset.unloadAllSquadra();
        batch.dispose();
        font.dispose();
    }

    private void clearInventoryItems() {

        try {
            // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione
            // precedente
            for (Actor actor : squadActors) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }
            if (checkPerSwitch) {
                squadActors.clear(); // Pulisci l'array degli attori dell'inventario
                booleanList.clear();
                hpList.clear();
                isCursorInside = false;
                animazionePartita.clear();
                controllo.clear();
                animationImages.clear();
                animationTextures.clear();
                checkPerSwitch = false;
            }

        } catch (Exception e) {
            System.out.println("Errore clearInventiryItem squadra, " + e);
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
            hpList.add(Integer.parseInt(currentPokeHPforSquad));
            maxPokeHPforSquad = pokeJson.get("Statistiche").getString("hpTot");
            nomePokeSquad = pokeJson.getString("nomePokemon");
            LVPoke = pokeJson.getString("livello");

        } catch (Exception e) {
            System.out.println("Errore leggiPokeSecondario, " + e);
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
            System.out.println("Errore startAnimation Squadra, " + e);
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
            System.out.println("Errore stopAnimation squad, " + e);
        }

    }

    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP) {

        try {

            // Calcola la percentuale degli HP attuali rispetto agli HP totali
            float percentualeHP = Float.parseFloat(currentHP) / Float.parseFloat(maxHP);
            float lunghezzaHPBar = 48 * 3 * percentualeHP;
            // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
            Image hpBar = new Image(
                    new TextureRegionDrawable(new TextureRegion(asset.getSquadra(AssetSquadra.BL_WHITE_PX))));
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
            System.out.println("Errore placeHpBar squad, " + e);
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
            System.out.println("Errore changeIntoSelected, " + e);
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
            System.out.println("Errore changeIntoNOTSelected squad, " + e);
        }

    }

    public void spostaPoke(int index1) {

        try {

            // toglie le label dei comandi
            infoImage.remove();
            cancelImage.remove();

            if (!battaglia) {
                spostaImage.remove();
            } else {
                cambiaImage.remove();
            }
            checkPerSwitch = true;
            // Aggiungi un ClickListener allo stage
            stage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Codice da eseguire quando viene rilevato un clic sullo schermo

                    if (indexDaSwitch != 0 && index1 != indexDaSwitch) {
                        FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
                        String jsonString = file.readString();
                        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
                        JsonValue json = new JsonReader().parse(jsonString);

                        // Ottieni i nomi dei due Pokémon da scambiare
                        String poke1Name = "poke" + index1;
                        String poke2Name = "poke" + indexDaSwitch;

                        // Conserva i valori dei Pokémon da scambiare
                        JsonValue poke1Data = json.get(poke1Name);
                        JsonValue poke2Data = json.get(poke2Name);

                        // Rimuovi i Pokémon dalle posizioni correnti
                        json.remove(poke1Name);
                        json.remove(poke2Name);

                        // Assegna un nuovo nome generico a tutti i campi di ciascun Pokémon
                        poke1Data.setName(poke2Name);
                        poke2Data.setName(poke1Name);

                        // Aggiungi i Pokémon scambiati nelle posizioni corrispondenti
                        json.addChild(poke1Data);
                        json.addChild(poke2Data);

                        // Sovrascrivi il file JSON con i dati modificati
                        file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

                    }
                    clearInventoryItems();
                    showSquad();

                    checkPerRimozione = false;
                    indexDaSwitch = 0;
                    checkPerSwitch = false;

                    stage.removeListener(this);

                }
            });

        } catch (Exception e) {
            System.out.println("Errore spostaPoke sqladra, " + e);
        }
    }
}