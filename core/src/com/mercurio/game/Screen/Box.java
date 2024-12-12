package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetBPB;
import com.mercurio.game.AssetManager.GameAsset.AssetBox;
import com.mercurio.game.AssetManager.GameAsset.AssetSISCB;
import com.mercurio.game.AssetManager.GameAsset.AssetSQBox;
import com.mercurio.game.pokemon.infoPoke;

import java.util.HashMap;

public class Box extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    Array<Image> animationImages = new Array<>();
    Array<Texture> animationTextures = new Array<>();
    private HashMap<Image, Integer> imageIds = new HashMap<>();
    private String nomePoke;
    private String nomePokeSquadra;
    private TextureRegion[] sfondi;
    private MercurioMain game;
    private Image avantiImage;
    private Image indietroImage;
    private Image infoImage;
    private Image spostaImage;
    private Image liberaImage;
    Image background;

    private float yPoke; // questa è la y fissa da mettere al pokemon
    private float valoreCambioRiga = 70; // questo è il valore da sommare ogni volta che va cambiata la riga
    private int cont = 0;

    private int boxAttuale = 1; // mi salvo il box attuale e grazie a questo riesco a capire da che numero
                                // partire per il render dei pokemon nel box
    private int totalePagineBox = 16; // questo non appena viene chiamato per la prima volta caricaBorsa viene
                                      // calcolato il numero totale di pagine

    private int posizionePokemonSelezionato = 0;
    private int posizioneSpostata = 0;
    private boolean controllaSposta = false;
    private Image immagineSelezionato;

    private infoPoke infoPoke;

    private GameAsset asset;

    public Box(MercurioMain game) {
        this.game = game;
        this.asset = game.getGameAsset();
        asset.loadSISCBAsset();
        asset.loadBPBAsset();
        asset.loadBoxAsset();
        asset.finishLoading();
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);

        show();
        caricaSquadra();
        caricaPagina(1);
        // caricaBorsa();
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
        game.closeBox();
        for (Texture texture : animationTextures) {
            texture.dispose();
        }
        asset.unloadAllBox();
        asset.unloadAllBPB();
        asset.unloadAllSISCB();
        Gdx.input.setInputProcessor(stage);
    }

    public void leggiPoke(int numero) {

        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/box.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get(String.valueOf(numero));

            if (pokeJson == null) {
                nomePoke = "";

            } else {
                // Caso in cui l'elemento esiste
                nomePoke = pokeJson.getString("nomePokemon");
            }
        } catch (Exception e) {
            System.out.println("Errore leggiPoke box, " + e);
        }

    }

    public void disegnaPoke(int param, int cont) {

        try {
            if (!nomePoke.isEmpty()) {

                Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
                animationTextures.add(animationTexture);
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0,
                        animationTexture.getWidth() / 2, animationTexture.getHeight());
                // Crea un'immagine utilizzando solo la prima metà dell'immagine
                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight() * 2 + 10);
                animationImage.setPosition(250 + cont * 80, yPoke);

                imageIds.put(animationImage, param);

                animationImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        if (param == posizionePokemonSelezionato) {
                            // nel caso in cui viene ripremuto il pokemon già selezionato non fa nulla

                        } else {

                            if (!controllaSposta) {

                                if (posizionePokemonSelezionato != 0) {
                                    // resettare le dimensioni di quello di prima
                                    immagineSelezionato.setSize(animationTexture.getWidth() + 10,
                                            animationTexture.getHeight() * 2 + 10);
                                }

                                float originalWidth = animationImage.getWidth();
                                float originalHeight = animationImage.getHeight();

                                // Aumenta leggermente la dimensione dell'immagine
                                animationImage.setSize(originalWidth * 1.1f, originalHeight * 1.1f);
                                immagineSelezionato = animationImage;

                                posizionePokemonSelezionato = param;

                                // rendi i 3 pulsanti visibili
                                liberaImage.setVisible(true);
                                infoImage.setVisible(true);
                                spostaImage.setVisible(true);

                            } else {
                                posizioneSpostata = param;
                            }
                        }

                    }
                });

                animationImages.add(animationImage);
                stage.addActor(animationImage);

                nomePoke = "";

            } else {

                Texture animationTexture = asset.getBox(AssetBox.PK_SPHEAL_LB);

                animationTextures.add(animationTexture);
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0,
                        animationTexture.getWidth() / 2, animationTexture.getHeight());
                // Crea un'immagine utilizzando solo la prima metà dell'immagine
                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight() * 2 + 10);
                animationImage.setPosition(250 + cont * 80, yPoke);

                imageIds.put(animationImage, param);

                // così diventa invisibile ma comunque premibile (per lo sposta)
                animationImage.setColor(1, 1, 1, 0);

                animationImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // da settare per lo sposta
                        if (controllaSposta) {
                            creaPokeBoxVuoto(param);
                            posizioneSpostata = param;
                        }
                    }
                });

                animationImages.add(animationImage);
                stage.addActor(animationImage);

            }

        } catch (Exception e) {
            System.out.println("Errore disegnaPoke box, " + e);
        }

    }

    @Override
    public void show() {
        try {
            Texture textureBack = asset.getBox(AssetBox.SF_BOX_CM);

            sfondi = new TextureRegion[16];

            // Calcola la larghezza e l'altezza di ogni frame
            int frameWidth = textureBack.getWidth() / 4;
            int frameHeight = textureBack.getHeight() / 4;

            // Divide la texture in una griglia di (numeroRighe x numeroColonne)
            TextureRegion[][] tmp = TextureRegion.split(textureBack, frameWidth, frameHeight);

            int index = 0;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4 && index < 16; c++) {
                    sfondi[index++] = tmp[r][c]; // Copia ogni frame nell'array unidimensionale
                }
            }

            // Add background
            background = new Image(sfondi[0]);
            // Ritaglia l'immagine per adattarla alla dimensione dello schermo
            background.setSize(700, 600);
            background.setPosition(((Gdx.graphics.getWidth() - background.getWidth()) / 1.5f),
                    (Gdx.graphics.getHeight() - background.getHeight()) / 2);
            stage.addActor(background);

            // Aggiungi le immagini "avanti" e "indietro" in alto
            Texture avantiTexture = asset.getBPB(AssetBPB.SF_AVANTI);
            Texture indietroTexture = asset.getBPB(AssetBPB.SF_INDIETRO);

            avantiImage = new Image(avantiTexture);
            indietroImage = new Image(indietroTexture);

            // aggiungi le immagini con i vari pulsanti (sposta, info, libera)
            Texture pulsanteSposta = asset.getSQBox(AssetSQBox.SQ_SPOSTA);
            Texture pulsanteInfo = asset.getSQBox(AssetSQBox.SQ_INFO);
            Texture pulsanteLibera = asset.getSISCB(AssetSISCB.SQ_CANCEL); // c'è da fare il tasto libera per ora
                                                                               // metto cancel

            spostaImage = new Image(pulsanteSposta);
            infoImage = new Image(pulsanteInfo);
            liberaImage = new Image(pulsanteLibera);

            // Posiziona le immagini in alto a sinistra e destra
            float marginTop = 70; // distanza dal bordo superiore dello schermo
            // Calcola la posizione centrale per le immagini
            float centerX = Gdx.graphics.getWidth() / 2;
            float yPos = (Gdx.graphics.getHeight() + background.getHeight()) / 2 - avantiImage.getHeight() - marginTop;
            float separator = 240;

            // Carica il font personalizzato
            font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));

            // Crea uno stile per la Label con il font personalizzato
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;

            // Imposta la scala del font
            labelStyle.font.getData().setScale(5f); // Ingrandisce il font

            // Crea la Label con il testo desiderato
            Label label = new Label("BOX 1", labelStyle);

            // Posiziona la label centrata tra le frecce
            label.setPosition(centerX - label.getWidth() / 2, yPos);
            stage.addActor(label);

            indietroImage.setPosition(centerX - indietroImage.getWidth() - separator, yPos);
            indietroImage.setSize(30, 50);

            avantiImage.setPosition(centerX + separator, yPos);
            avantiImage.setSize(30, 50);

            // listener per la freccia avanti
            avantiImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // se non la pagina successiva non esiste non fa nulla
                    if ((boxAttuale + 1) > totalePagineBox) {

                    } else {
                        boxAttuale += 1;
                        System.out.println("aa");
                        caricaPagina(boxAttuale);
                        aggiornaVisibilitaFreccie();
                        label.setText(getBoxLabel(boxAttuale));
                    }

                    if (posizionePokemonSelezionato > 0) {

                        liberaImage.setVisible(false);
                        infoImage.setVisible(false);
                        spostaImage.setVisible(false);

                        posizionePokemonSelezionato = 0;
                    }
                }
            });

            // listener per la frecia indietro
            indietroImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // se la pagina precedente è la 0 o minore non fa nulla
                    if ((boxAttuale - 1) <= 0) {

                    } else {
                        boxAttuale -= 1;
                        caricaPagina(boxAttuale);
                        aggiornaVisibilitaFreccie();
                        label.setText(getBoxLabel(boxAttuale));
                    }

                    if (posizionePokemonSelezionato > 0) {

                        liberaImage.setVisible(false);
                        infoImage.setVisible(false);
                        spostaImage.setVisible(false);

                        posizionePokemonSelezionato = 0;
                    }

                }
            });

            stage.addActor(avantiImage);
            stage.addActor(indietroImage);

            // Calcola la posizione in X per posizionare liberaImage sulla destra del
            // background
            float tastiX = background.getX() + background.getWidth() - 170;

            // Calcola la fine del background in Y (la posizione più bassa del background)
            float tastiY = background.getY() - 30;

            liberaImage.setSize(56 * 3, 24 * 3);
            infoImage.setSize(56 * 3, 24 * 3);
            spostaImage.setSize(56 * 3, 24 * 3);
            liberaImage.setPosition(tastiX, tastiY);
            infoImage.setPosition(tastiX - 170, tastiY);
            spostaImage.setPosition(tastiX - 340, tastiY);

            liberaImage.setVisible(false);
            infoImage.setVisible(false);
            spostaImage.setVisible(false);

            liberaImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    liberaPokemon();
                    clearPage();
                    caricaSquadra();
                    caricaPagina(boxAttuale);

                    posizionePokemonSelezionato = 0;
                    liberaImage.setVisible(false);
                    infoImage.setVisible(false);
                    spostaImage.setVisible(false);
                }
            });
            infoImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (posizionePokemonSelezionato > 0) {
                        infoPoke = new infoPoke(stage, posizionePokemonSelezionato, true);
                    } else {
                        infoPoke = new infoPoke(stage, posizionePokemonSelezionato * -1, false);
                    }
                }
            });
            spostaImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (posizionePokemonSelezionato != 0) {
                        controllaSposta = true;
                        spostaPoke();
                    }
                }
            });

            stage.addActor(liberaImage);
            stage.addActor(infoImage);
            stage.addActor(spostaImage);

            // da controllare se rimuovere le frecce in caso in cui non ci sia la prossima
            // pagina
            aggiornaVisibilitaFreccie();

        } catch (Exception e) {
            System.out.println("Errore show box, " + e);
        }

    }

    // basta chiamare la funzione mettendo il numero della pagina attuale
    private void caricaPagina(int pagina) {

        try {
            // va a rimuovere tutte le immagini dei pokemon della vecchia pagina
            clearPage();

            background.setDrawable(new TextureRegionDrawable(new TextureRegion(sfondi[pagina - 1])));

            for (int i = (48 * (pagina - 1)) + 1; i < (48 * (pagina)) + 1; i++) {
                leggiPoke(i);
                disegnaPoke(i, cont);
                cont++;
                if (cont == 8) {
                    cont = 0;
                    yPoke = yPoke - valoreCambioRiga;
                }
            }
        } catch (Exception e) {
            System.out.println("Errore caricaPagineBox, " + e);
        }

    }

    private void disegnaPokeSquadra(int numero) {

        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue pokeJson = json.get("poke" + String.valueOf(numero * -1));

            if (pokeJson == null) {
                nomePokeSquadra = "";

            } else {
                // Caso in cui l'elemento esiste
                nomePokeSquadra = pokeJson.getString("nomePokemon");
            }

            // Carica l'immagine di sfondo
            Texture backgroundTexture = asset.getBox(AssetBox.SQ_POKE_SQ);
            Image backgroundImage = new Image(backgroundTexture);
            float yBase = background.getImageY() + background.getHeight() - backgroundImage.getHeight() - 45;
            float yFinalePoke = yBase - 70 * (numero * -1);
            float xFInalePoke = background.getImageX() + backgroundImage.getWidth();
            backgroundImage.setSize(80, 60); // Imposta la dimensione dello sfondo, modifica se necessario
            backgroundImage.setPosition(xFInalePoke - 5, yFinalePoke);

            if (!nomePokeSquadra.isEmpty()) {
                Texture animationTexture = new Texture("pokemon/" + nomePokeSquadra + "Label.png");
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0,
                        animationTexture.getWidth() / 2, animationTexture.getHeight());

                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight() * 2 + 10);
                animationImage.setPosition(xFInalePoke, yFinalePoke);

                // aggiungo listener
                animationImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        if (numero == posizionePokemonSelezionato) {
                            // nel caso in cui viene ripremuto il pokemon già selezionato non fa nulla

                        } else {

                            if (!controllaSposta) {

                                if (posizionePokemonSelezionato != 0) {
                                    // resettare le dimensioni di quello di prima
                                    immagineSelezionato.setSize(animationTexture.getWidth() + 10,
                                            animationTexture.getHeight() * 2 + 10);
                                }

                                float originalWidth = animationImage.getWidth();
                                float originalHeight = animationImage.getHeight();

                                // Aumenta leggermente la dimensione dell'immagine
                                animationImage.setSize(originalWidth * 1.1f, originalHeight * 1.1f);
                                immagineSelezionato = animationImage;

                                posizionePokemonSelezionato = numero;

                                // rendi i 3 pulsanti visibili
                                liberaImage.setVisible(true);
                                infoImage.setVisible(true);
                                spostaImage.setVisible(true);

                            } else {

                                posizioneSpostata = numero;
                            }
                        }

                    }
                });

                stage.addActor(backgroundImage);
                stage.addActor(animationImage);

            } else {
                Texture animationTexture = asset.getBox(AssetBox.PK_SPHEAL_LB);
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0,
                        animationTexture.getWidth() / 2, animationTexture.getHeight());

                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight() * 2 + 10);
                animationImage.setPosition(xFInalePoke, yFinalePoke);

                animationImage.setColor(1, 1, 1, 0);

                // aggiungo listener
                animationImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        // da settare per lo sposta
                        if (controllaSposta) {

                            posizioneSpostata = numero;
                        }
                    }
                });

                stage.addActor(backgroundImage);
                stage.addActor(animationImage);
            }
        } catch (Exception e) {
            System.out.println("Errore disegnaPokeSquadra box, " + e);
        }

    }

    private void caricaSquadra() {
        for (int i = -1; i > -7; i--) {
            disegnaPokeSquadra(i);
        }
    }

    private void clearPage() {
        try {
            // Rimuove tutti gli attori delle immagini di Pokémon dalla stage
            for (Image img : animationImages) {
                img.remove();
            }
            animationImages.clear(); // Pulisce l'array per evitare riferimenti residui
            cont = 0; // Resetta il contatore
            yPoke = ((Gdx.graphics.getHeight() + sfondi[0].getRegionHeight()) / 2) + 10 - 200; // Resetta la posizione
                                                                                               // verticale

        } catch (Exception e) {
            System.out.println("Errore clearPage box, " + e);
        }

    }

    public String getBoxLabel(int number) {
        return "BOX " + number;
    }

    private void aggiornaVisibilitaFreccie() {
        // Nasconde la freccia indietro se sei sulla prima pagina
        indietroImage.setVisible(boxAttuale > 1);
        // Nasconde la freccia avanti se sei sull'ultima pagina
        avantiImage.setVisible(boxAttuale < totalePagineBox);
    }

    private void liberaPokemon() {
        try {

            if (posizionePokemonSelezionato > 0) {

                FileHandle file = Gdx.files.local("assets/ashJson/box.json");
                String jsonString = file.readString();

                // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
                JsonValue json = new JsonReader().parse(jsonString);

                json.remove(String.valueOf(posizionePokemonSelezionato));

                file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

            } else {

                if (contaPokeSquadra() > 1) {

                    FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
                    String jsonString = file.readString();

                    JsonValue json = new JsonReader().parse(jsonString);

                    JsonValue newPokemon = new JsonValue(JsonValue.ValueType.object);
                    newPokemon.addChild("nomePokemon", new JsonValue(""));
                    newPokemon.addChild("livello", new JsonValue(""));
                    newPokemon.addChild("esperienza", new JsonValue(0));

                    JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
                    statistiche.addChild("hp", new JsonValue(0));
                    statistiche.addChild("hpTot", new JsonValue(0));
                    statistiche.addChild("attack", new JsonValue(0));
                    statistiche.addChild("defense", new JsonValue(0));
                    statistiche.addChild("special_attack", new JsonValue(0));
                    statistiche.addChild("special_defense", new JsonValue(0));
                    statistiche.addChild("speed", new JsonValue(0));
                    newPokemon.addChild("statistiche", statistiche);

                    JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
                    evStats.addChild("Hp", new JsonValue(0));
                    evStats.addChild("Att", new JsonValue(0));
                    evStats.addChild("Dif", new JsonValue(0));
                    evStats.addChild("Spec", new JsonValue(0));
                    evStats.addChild("Vel", new JsonValue(0));

                    newPokemon.addChild("ev", evStats);

                    JsonValue ivStats = new JsonValue(JsonValue.ValueType.object);
                    ivStats.addChild("Hp", new JsonValue(0));
                    ivStats.addChild("Att", new JsonValue(0));
                    ivStats.addChild("Dif", new JsonValue(0));
                    ivStats.addChild("Spec", new JsonValue(0));
                    ivStats.addChild("Vel", new JsonValue(0));

                    newPokemon.addChild("iv", ivStats);

                    JsonValue mosseJson = new JsonValue(JsonValue.ValueType.array);
                    for (int i = 0; i < 4; i++) {
                        JsonValue mossa = new JsonValue(JsonValue.ValueType.object);
                        mossa.addChild("nome", new JsonValue(""));
                        mossa.addChild("tipo", new JsonValue(""));
                        mossa.addChild("ppTot", new JsonValue(0));
                        mossa.addChild("ppAtt", new JsonValue(""));
                        mosseJson.addChild(mossa);
                    }
                    newPokemon.addChild("mosse", mosseJson);

                    newPokemon.addChild("tipoBall", new JsonValue(""));
                    newPokemon.addChild("x", new JsonValue(0));

                    json.remove("poke" + (posizionePokemonSelezionato * -1));
                    json.addChild("poke" + (posizionePokemonSelezionato * -1), newPokemon);

                    file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

                    sistemaSquadra(posizionePokemonSelezionato * -1);

                }

            }

        } catch (Exception e) {
            System.out.println("Errore liberaPoke box, " + e);
        }

    }

    private int contaPokeSquadra() {
        try {
            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            int cont = 0;

            for (int i = 1; i < 7; i++) {
                JsonValue pokeJson = json.get("poke" + String.valueOf(i));

                if (!pokeJson.getString("nomePokemon").isEmpty()) {
                    cont++;
                }
            }

            return cont;
        } catch (Exception e) {
            System.out.println("Errore contaPokeSquadra box, " + e);
            return 0;
        }

    }

    private void sistemaSquadraSposta() {
        try {

            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            JsonValue json = new JsonReader().parse(file.readString());

            String primoPokeVuoto = "";
            String nomePokePieno = "";

            for (int i = 1; i < 7; i++) {
                String nomePoke = "poke" + i;

                JsonValue poke = json.get(nomePoke);
                if (primoPokeVuoto.isEmpty()) {

                    if (poke.getString("nomePokemon").isEmpty()) {
                        primoPokeVuoto = nomePoke;
                    }
                } else {
                    if (!poke.getString("nomePokemon").isEmpty()) {
                        nomePokePieno = nomePoke;
                    }
                }
            }

            if (!primoPokeVuoto.isEmpty() && !nomePokePieno.isEmpty()) {

                JsonValue primoPokeVuotoJson = json.get(primoPokeVuoto);
                JsonValue pokePienoSbagliato = json.get(nomePokePieno);

                json.remove(primoPokeVuoto);
                json.remove(nomePokePieno);

                primoPokeVuotoJson.setName(nomePokePieno);
                pokePienoSbagliato.setName(primoPokeVuoto);

                json.addChild(primoPokeVuotoJson);
                json.addChild(pokePienoSbagliato);

                file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
            }
        } catch (Exception e) {
            System.out.println("Errore sistemSquadraSPosta box, " + e);
        }

    }

    private void sistemaSquadra(int posPokeEliminato) {
        try {

            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            for (int i = posPokeEliminato + 1; i < 7; i++) {

                String poke1Name = "poke" + (i - 1);
                String poke2Name = "poke" + i;

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
        } catch (Exception e) {
            System.out.println("Errore sistemaSquasra box, " + e);
        }

    }

    // da testare (secondo me esplode)
    private void spostaPoke() {
        try {

            liberaImage.setVisible(false);
            infoImage.setVisible(false);
            spostaImage.setVisible(false);

            stage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    // qua posso falo lo spostamento pk si è selezionato il pokemon giusto
                    if (posizioneSpostata != 0) {

                        // condizione 1: quella della squadra effettiva quindi uso stesso codice di nava
                        if (posizionePokemonSelezionato < 0 && posizioneSpostata < 0) {

                            FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
                            String jsonString = file.readString();
                            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
                            JsonValue json = new JsonReader().parse(jsonString);

                            // Ottieni i nomi dei due Pokémon da scambiare
                            String poke1Name = "poke" + (posizionePokemonSelezionato * -1);
                            String poke2Name = "poke" + (posizioneSpostata * -1);

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

                        } // condizione 2: quella con solo il box, copio codice di nava e lo modifico
                        else if (posizionePokemonSelezionato > 0 && posizioneSpostata > 0) {

                            FileHandle file = Gdx.files.local("assets/ashJson/box.json");
                            String jsonString = file.readString();
                            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
                            JsonValue json = new JsonReader().parse(jsonString);

                            // Ottieni i nomi dei due Pokémon da scambiare
                            String poke1Name = String.valueOf(posizionePokemonSelezionato);
                            String poke2Name = String.valueOf(posizioneSpostata);

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

                            // Sovrascrivi il file JSON con i dati modificati
                            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

                        } else {

                            JsonValue json1;
                            JsonValue json2;

                            FileHandle file1;
                            FileHandle file2;

                            String nomePoke1;
                            String nomePoke2;
                            String nomePoke1Aggiornato;
                            String nomePoke2Aggiornato;

                            if (posizionePokemonSelezionato > 0) {

                                file1 = Gdx.files.local("assets/ashJson/box.json");
                                json1 = new JsonReader().parse(file1.readString());

                                nomePoke1 = String.valueOf(posizionePokemonSelezionato);
                                nomePoke2Aggiornato = nomePoke1;

                            } else {

                                file1 = Gdx.files.local("assets/ashJson/squadra.json");
                                json1 = new JsonReader().parse(file1.readString());

                                nomePoke1 = "poke" + (posizionePokemonSelezionato * -1);
                                nomePoke2Aggiornato = nomePoke1;
                            }

                            if (posizioneSpostata > 0) {

                                file2 = Gdx.files.local("assets/ashJson/box.json");
                                json2 = new JsonReader().parse(file2.readString());

                                nomePoke2 = String.valueOf(posizioneSpostata);
                                nomePoke1Aggiornato = nomePoke2;

                            } else {

                                file2 = Gdx.files.local("assets/ashJson/squadra.json");
                                json2 = new JsonReader().parse(file2.readString());

                                nomePoke2 = "poke" + (posizioneSpostata * -1);
                                nomePoke1Aggiornato = nomePoke2;

                            }

                            // Conserva i valori dei Pokémon da scambiare
                            JsonValue jsonPoke1 = json1.get(nomePoke1);
                            JsonValue jsonPoke2 = json2.get(nomePoke2);

                            json1.remove(nomePoke1);
                            json2.remove(nomePoke2);

                            jsonPoke1.setName(nomePoke1Aggiornato);
                            jsonPoke2.setName(nomePoke2Aggiornato);

                            if (posizioneSpostata < 0 && jsonPoke2.get("nomePokemon").isEmpty()) {

                            } else {
                                json1.addChild(jsonPoke2);
                            }

                            json2.addChild(jsonPoke1);

                            file1.writeString(json1.prettyPrint(JsonWriter.OutputType.json, 1), false);
                            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                        }

                        // comandi da eseguire alla fine per resettare il tutto
                        posizionePokemonSelezionato = 0;
                        posizioneSpostata = 0;
                        controllaSposta = false;

                        sistemaSquadraSposta();
                        clearPage();
                        caricaPagina(boxAttuale);
                        caricaSquadra();

                        stage.removeListener(this);

                    }
                }

            });

        } catch (Exception e) {
            System.out.println("Errore spostaPoke box, " + e);
        }

    }

    public void creaPokeBoxVuoto(int posizionePoke) {
        try {

            FileHandle file = Gdx.files.local("assets/ashJson/box.json");
            JsonValue json = new JsonReader().parse(file.readString());

            JsonValue newPokemon = new JsonValue(JsonValue.ValueType.object);
            newPokemon.addChild("nomePokemon", new JsonValue(""));
            newPokemon.addChild("livello", new JsonValue(""));
            newPokemon.addChild("esperienza", new JsonValue(0));

            JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
            statistiche.addChild("hp", new JsonValue(0));
            statistiche.addChild("hpTot", new JsonValue(0));
            statistiche.addChild("attack", new JsonValue(0));
            statistiche.addChild("defense", new JsonValue(0));
            statistiche.addChild("special_attack", new JsonValue(0));
            statistiche.addChild("special_defense", new JsonValue(0));
            statistiche.addChild("speed", new JsonValue(0));
            newPokemon.addChild("statistiche", statistiche);

            JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
            evStats.addChild("Hp", new JsonValue(0));
            evStats.addChild("Att", new JsonValue(0));
            evStats.addChild("Dif", new JsonValue(0));
            evStats.addChild("Spec", new JsonValue(0));
            evStats.addChild("Vel", new JsonValue(0));

            newPokemon.addChild("ev", evStats);

            JsonValue ivStats = new JsonValue(JsonValue.ValueType.object);
            ivStats.addChild("Hp", new JsonValue(0));
            ivStats.addChild("Att", new JsonValue(0));
            ivStats.addChild("Dif", new JsonValue(0));
            ivStats.addChild("Spec", new JsonValue(0));
            ivStats.addChild("Vel", new JsonValue(0));

            newPokemon.addChild("iv", ivStats);

            JsonValue mosseJson = new JsonValue(JsonValue.ValueType.array);
            for (int i = 0; i < 4; i++) {
                JsonValue mossa = new JsonValue(JsonValue.ValueType.object);
                mossa.addChild("nome", new JsonValue(""));
                mossa.addChild("tipo", new JsonValue(""));
                mossa.addChild("ppTot", new JsonValue(0));
                mossa.addChild("ppAtt", new JsonValue(""));
                mosseJson.addChild(mossa);
            }
            newPokemon.addChild("mosse", mosseJson);

            newPokemon.addChild("tipoBall", new JsonValue(""));
            newPokemon.addChild("x", new JsonValue(0));

            json.addChild(String.valueOf(posizionePoke), newPokemon);

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        } catch (Exception e) {
            System.out.println("Errore creaPokeBoxVuoto box, " + e);
        }

    }
}