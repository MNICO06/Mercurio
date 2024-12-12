package com.mercurio.game.menu;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Pokedex {
    private BitmapFont font;
    private BitmapFont font1;
    private Stage stage;
    private MenuLabel chiamanteM;

    private Image background;
    private Image backgroundPokedex;
    private Image backgroundInfo;
    private Image tastoApriImage;
    private Image tastoXImage;
    private Image avantiImage;
    private Image indietroImage;
    private Image cancel;
    private Label label;
    private Label labelNome;
    private Label labelAltezza;
    private Label labelPeso;
    private Label labelDescrizione;
    private Label labelTipo;
    private Label labeltipoEffettivo1;
    private Label labeltipoEffettivo2;
    private Texture pokemonTexture;
    private Image botImage;
    private Image imageType1;
    private Image imageType2;
    private String testoDescrizione = "";

    private Array<Actor> pokedexActor;
    private Array<Image> animationImages = new Array<>();
    private Array<Actor> pokeActorInfo;
    private Array<Image> pokeImageInfo = new Array<>();
    private HashMap<String, Integer> tipoToIndex;

    private int pagina = 1;
    private int yPoke = 520;
    private int cont = 1;
    private int pokePerPag = 30;

    public Pokedex(Stage stage, MenuLabel chiamanteM) {
        this.stage = stage;
        this.chiamanteM = chiamanteM;
        this.pokedexActor = new Array<>();
        this.pokeActorInfo = new Array<>();

        this.font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        this.font1 = new BitmapFont(Gdx.files.local("font/font.fnt"));

        tipoToIndex = new HashMap<>();
        tipoToIndex.put("Coleottero", 0);
        tipoToIndex.put("Buio", 1);
        tipoToIndex.put("Drago", 2);
        tipoToIndex.put("Elettro", 3);
        tipoToIndex.put("Lotta", 4);
        tipoToIndex.put("Fuoco", 5);
        tipoToIndex.put("Volante", 6);
        tipoToIndex.put("Spettro", 7);
        tipoToIndex.put("Erba", 8);
        tipoToIndex.put("Terra", 9);
        tipoToIndex.put("Ghiaccio", 10);
        tipoToIndex.put("Normale", 11);
        tipoToIndex.put("Veleno", 12);
        tipoToIndex.put("Psico", 13);
        tipoToIndex.put("Roccia", 14);
        tipoToIndex.put("Acciaio", 15);
        tipoToIndex.put("Acqua", 16);
        tipoToIndex.put("Folletto", 17);

        Gdx.input.setInputProcessor(stage);
        renderizzaPokedex();

    }

    public void render() {
        try {
            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

            stage.draw(); // Disegna lo stage sullo SpriteBatch

        } catch (Exception e) {
            System.out.println("Errore render pokedex, " + e);
        }
        
    }

    private void renderizzaPokedex() {

        try {
            
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // renderizzo il background iniziale
            Texture backgroundTexture = new Texture("sfondo/sfondoPokedex.png");
            background = new Image(backgroundTexture);
            background.setSize(screenWidth, screenHeight);
            stage.addActor(background);
            pokedexActor.add(background);

            // renderizzo il background dove ci vanno i pokemon
            Texture backgroundPokedexTexture = new Texture("sfondo/sfondoPokedexAperto.png");
            backgroundPokedex = new Image(backgroundPokedexTexture);
            backgroundPokedex.setSize(256*4.4f, 180*4.4f);
            backgroundPokedex.setPosition(-50, -60);
            backgroundPokedex.setColor(1,1,1,0.95f);
            stage.addActor(backgroundPokedex);
            backgroundPokedex.setVisible(false);
            pokedexActor.add(backgroundPokedex);

            // renderizzo il background dove ci vanno i pokemon
            Texture backgroundInfoPokedex = new Texture("sfondo/descrizionePokedex.png");
            backgroundInfo = new Image(backgroundInfoPokedex);
            backgroundInfo.setSize(256*4.4f, 180*4.4f);
            backgroundInfo.setPosition(-50, -60);
            backgroundInfo.setColor(1,1,1,1f);
            stage.addActor(backgroundInfo);
            backgroundInfo.setVisible(false);
            pokedexActor.add(backgroundInfo);


            Texture tastoApriTexture = new Texture("sfondo/tastoAperturaPokedex.png");
            tastoApriImage = new Image(tastoApriTexture);
            tastoApriImage.setSize(66*4, 27*4);
            tastoApriImage.setPosition((screenWidth/2) - 130, (screenHeight/2) + 100);
            stage.addActor(tastoApriImage);
            pokedexActor.add(tastoApriImage);

            tastoApriImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //vado ad aprire il pokedex regionale

                    tastoApriImage.setVisible(false);
                    backgroundPokedex.setVisible(true);
                    indietroImage.setVisible(true);
                    avantiImage.setVisible(true);
                    label.setVisible(true);
                    renderizzaPoke();
                }
            });

            Texture tastoX = new Texture("sfondo/X.png");
            tastoXImage = new Image(tastoX);
            tastoXImage.setPosition(screenWidth - 86, 10);
            stage.addActor(tastoXImage);
            pokedexActor.add(tastoXImage);
            
            tastoXImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pulsisciInventario();
                    pulisciPagina();
                    chiamanteM.closePokedex();
                    
                }
            });

            Texture esciInfo = new Texture("sfondo/freccaIndietro.png");
            cancel = new Image(esciInfo);
            cancel.setPosition(790, 630);
            cancel.setSize(56*2, 24*2);
            stage.addActor(cancel);
            pokedexActor.add(cancel);
            cancel.setVisible(false);
            
            cancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chiudiInfo();
                }
            });

            // Aggiungi le immagini "avanti" e "indietro" in alto
            Texture avantiTexture = new Texture("sfondo/avanti.png");
            Texture indietroTexture = new Texture("sfondo/indietro.png");

            avantiImage = new Image(avantiTexture);
            indietroImage = new Image(indietroTexture);

            // Carica il font personalizzato
            font = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
            // Crea uno stile per la Label con il font personalizzato
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            // Imposta la scala del font
            labelStyle.font.getData().setScale(5f); // Ingrandisce il font
            indietroImage.setSize(30, 50);
            avantiImage.setSize(30, 50);
            indietroImage.setPosition(300, 60);
            avantiImage.setPosition(700, 60);
            indietroImage.setVisible(false);
            avantiImage.setVisible(false);

            pokedexActor.add(indietroImage);
            pokedexActor.add(avantiImage);


            //listener per la freccia avanti
            indietroImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //se non la pagina successiva non esiste non fa nulla
                    if ((pagina -1) <= 0) {

                    }else {
                        pagina -= 1;
                        renderizzaPoke();
                        aggiornaVisibilitaFreccie();
                        aggiornaVisibilitaFreccie();
                        label.setText(getPaginaLabel(pagina));
                    }
                }
            });

            //listener per la freccia avanti
            avantiImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //se non la pagina successiva non esiste non fa nulla
                    if ((pagina + 1) > 3) {

                    }else {
                        pagina += 1;
                        renderizzaPoke();
                        aggiornaVisibilitaFreccie();
                        label.setText(getPaginaLabel(pagina));
                    }
                }
            });

            stage.addActor(indietroImage);
            stage.addActor(avantiImage);

            // Crea la Label con il testo desiderato
            label = new Label("PAGINA 1", labelStyle);
            label.setPosition(410, 60);
            label.setVisible(false);
            pokedexActor.add(label);

            stage.addActor(label);

        } catch (Exception e) {
            System.out.println("Errore renderizzaPokedex, " + e);
        }
    }


    private void renderizzaPoke() {
        pulisciPagina();

        try {
            FileHandle file = Gdx.files.local("ashJson/pokemonScoperti.json");
            JsonValue json = new JsonReader().parse(file.readString());

            cont = 1;

            for (int i = (pokePerPag * (pagina - 1)) +1; i < (pokePerPag * (pagina)) +1; i ++) {
                JsonValue pokeJson = json.get(String.valueOf(i));

                if (pokeJson != null) {

                    String nomePoke = pokeJson.getString("nome");
                    int numeroPokedex = i;

                    if (!pokeJson.getString("incontrato").equals("-1")) {

                        Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
                        TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
                        Image animationImage = new Image(animationRegion);
                        animationImage.setSize(animationTexture.getWidth() + 40, animationTexture.getHeight()*2 + 40);
                        animationImage.setPosition(50 + (cont * 120),  yPoke);
                        stage.addActor(animationImage);
                        animationImages.add(animationImage);

                        if (pokeJson.getString("incontrato").equals("0")) {

                            TextureData textureData = animationTexture.getTextureData();
                            if (!textureData.isPrepared()) {
                                textureData.prepare();  // Prepare the texture data for pixmap access
                            }
                            Pixmap pokePixmap = textureData.consumePixmap();  // Now we can get the pixmap safely

                            // Create a white Pixmap with the same dimensions and transparency
                            Pixmap blackPixmap = new Pixmap(animationTexture.getWidth() / 2, animationTexture.getHeight(), Pixmap.Format.RGBA8888);
                            for (int x = 0; x < (animationTexture.getWidth() / 2); x++) {
                                for (int y = 0; y < animationTexture.getHeight(); y++) {
                                    int pixel = pokePixmap.getPixel(x, y);
                                    int alpha = pixel & 0x000000FF;  // Extract the alpha component
                                    blackPixmap.drawPixel(x, y, (0x00000000 | alpha));  // Set RGB to white, keep original alpha
                                }
                            }

                            Texture blackTexture = new Texture(blackPixmap);
                            pokePixmap.dispose();  // Dispose original pixmap to free resources
                            blackPixmap.dispose();  // Dispose white pixmap after creating the texture

                            TextureRegion whiteRegion = new TextureRegion(blackTexture);
                            Image blackOverlay = new Image(whiteRegion);  // Create an overlay image with white shape
                            blackOverlay.setSize(animationTexture.getWidth() + 40, animationTexture.getHeight()*2 + 40);
                            blackOverlay.setPosition(50 + (cont * 120),  yPoke);

                            stage.addActor(blackOverlay);
                            animationImages.add(blackOverlay);

                            blackOverlay.addListener(new ClickListener() {
                                private long lastClickTime = 0; // Memorizza il tempo del primo clic
                                private static final long DOUBLE_CLICK_THRESHOLD = 500; // Intervallo massimo (in millisecondi) per un doppio clic
                            
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    long currentTime = System.currentTimeMillis();
                                    if (currentTime - lastClickTime <= DOUBLE_CLICK_THRESHOLD) {
                                        // Esegui azione per doppio clic
                                        infoPoke(0, numeroPokedex);
                                    }
                                    lastClickTime = currentTime; // Aggiorna il tempo dell'ultimo clic
                                }
                            });

                        }

                        //listener per la freccia avanti
                        animationImage.addListener(new ClickListener() {
                            private long lastClickTime = 0; // Memorizza il tempo del primo clic
                            private static final long DOUBLE_CLICK_THRESHOLD = 500; // Intervallo massimo (in millisecondi) per un doppio clic
                        
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                long currentTime = System.currentTimeMillis();
                                if (currentTime - lastClickTime <= DOUBLE_CLICK_THRESHOLD) {
                                    // Esegui azione per doppio clic
                                    infoPoke(1, numeroPokedex);
                                }
                                lastClickTime = currentTime; // Aggiorna il tempo dell'ultimo clic
                            }
                        });

                    }else {
                        Texture animationTexture = new Texture("sfondo/puntoInterrogativo.png");
                        TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() , animationTexture.getHeight());
                        Image animationImage = new Image(animationRegion);
                        animationImage.setSize(animationRegion.getRegionWidth() - 20, animationRegion.getRegionHeight() - 30);
                        animationImage.setPosition(50 + (cont * 120),  yPoke);
                        stage.addActor(animationImage);
                        animationImages.add(animationImage);

                        //listener per la freccia avanti
                        animationImage.addListener(new ClickListener() {
                            private long lastClickTime = 0; // Memorizza il tempo del primo clic
                            private static final long DOUBLE_CLICK_THRESHOLD = 500; // Intervallo massimo (in millisecondi) per un doppio clic
                        
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                long currentTime = System.currentTimeMillis();
                                if (currentTime - lastClickTime <= DOUBLE_CLICK_THRESHOLD) {
                                    // Esegui azione per doppio clic
                                    infoPoke(-1, numeroPokedex);
                                }
                                lastClickTime = currentTime; // Aggiorna il tempo dell'ultimo clic
                            }
                        });

                    }

                    cont ++;
                    if (cont == 7) {
                        cont = 1;
                        yPoke = yPoke - 100;

                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Errore renderizzaPokedex, " + e);
        }
    }

    private void pulisciPagina() {

        try { 
            for (Image img : animationImages) {
                img.remove();
            }

            animationImages.clear();
        
        } catch (Exception e) {
            System.out.println("Errore pulisciPagina pokedex, " + e);
        }

        cont = 0;
        yPoke = 520;
        
    }

    private void pulsisciInventario() {

        try {
            for (Actor actor : pokedexActor) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }

        } catch (Exception e) {
            System.out.println("Errore PulisciInventario pokedex, " + e);
        }
        
        testoDescrizione = "";
    }

    private void aggiornaVisibilitaFreccie() {
        // Nasconde la freccia indietro se sei sulla prima pagina
        indietroImage.setVisible(pagina > 1);
        // Nasconde la freccia avanti se sei sull'ultima pagina
        avantiImage.setVisible(pagina < 3);
    }

    public String getPaginaLabel(int number) {
        return "PAGINA " + number;
    }

    private void rimuoviPagine() {
        indietroImage.setVisible(false);
        avantiImage.setVisible(false);
        label.setVisible(false);
    }

    private void ricaricaPagina() {
        indietroImage.setVisible(true);
        avantiImage.setVisible(true);
        label.setVisible(true);
    }

    private void chiudiInfo() {

        try {
            backgroundInfo.setVisible(false);
            ricaricaPagina();
            renderizzaPoke();
            cancel.setVisible(false);

            for (Actor actor : pokeActorInfo) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }
            for (Image img : pokeImageInfo) {
                img.remove();
            }
            pokeImageInfo.clear();
            testoDescrizione = "";

        } catch (Exception e) {
            System.out.println("Errore chiudiInfo in pokedex, " + e);
        }
    }

    private void infoPoke(int isPokemonFounded, int numeroPokedex) {

        try {
            backgroundInfo.setVisible(true);
            cancel.setVisible(true);
            pulisciPagina();
            rimuoviPagine();

            FileHandle file = Gdx.files.local("ashJson/pokemonScoperti.json");
            JsonValue json = new JsonReader().parse(file.readString());
            JsonValue pokeJson = json.get(String.valueOf(numeroPokedex));



            if (isPokemonFounded == -1) {

                labelAltezza = new Label("???", new Label.LabelStyle(font1, null));
                labelPeso = new Label("???", new Label.LabelStyle(font1, null));
                labelDescrizione = new Label("???", new Label.LabelStyle(font1, null));
                labelNome = new Label("???", new Label.LabelStyle(font1, null));
                labelTipo = new Label("???", new Label.LabelStyle(font1, null));
                labeltipoEffettivo1 = new Label("???", new Label.LabelStyle(font1, null));
                labeltipoEffettivo2 = new Label("???", new Label.LabelStyle(font1, null));

                Texture animationTexture = new Texture("sfondo/puntoInterrogativo.png");
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() , animationTexture.getHeight());
                botImage = new Image(animationRegion);
                botImage.setSize(animationRegion.getRegionWidth(), animationRegion.getRegionHeight());


                //posizioni delle varie label
                labelNome.setPosition(44*4, 143*4);
                labelNome.setFontScale(1.5f);

                labelTipo.setPosition(78*4, 132.5f*4);
                labelTipo.setFontScale(1f);

                labelAltezza.setPosition(47*4, 115*4);
                labelAltezza.setFontScale(2f);

                labelPeso.setPosition(47*4, 96*4);
                labelPeso.setFontScale(1.8f);

                labelDescrizione.setPosition(40*4, 72*4, Align.topLeft);
                labelDescrizione.setFontScale(1f);

                labeltipoEffettivo1.setPosition(133*4, 142*4);
                labeltipoEffettivo1.setFontScale(1f);
                stage.addActor(labeltipoEffettivo1);
                pokedexActor.add(labeltipoEffettivo1);
                pokeActorInfo.add(labeltipoEffettivo1);

                labeltipoEffettivo2.setPosition(154*4, 142*4);
                labeltipoEffettivo2.setFontScale(1f);
                stage.addActor(labeltipoEffettivo2);
                pokedexActor.add(labeltipoEffettivo2);
                pokeActorInfo.add(labeltipoEffettivo2);

                botImage.setSize(37*4, 37*4);
                botImage.setPosition(181*4, 113*4);
                


            }else if (isPokemonFounded == 0) {

                labelAltezza = new Label("???", new Label.LabelStyle(font1, null));
                labelPeso = new Label("???", new Label.LabelStyle(font1, null));
                labelDescrizione = new Label("???", new Label.LabelStyle(font1, null));
                labelNome = new Label(pokeJson.getString("nome"), new Label.LabelStyle(font1, null));
                labelTipo = new Label("???", new Label.LabelStyle(font1, null));
                labeltipoEffettivo1 = new Label("???", new Label.LabelStyle(font1, null));
                labeltipoEffettivo2 = new Label("???", new Label.LabelStyle(font1, null));

                pokemonTexture = new Texture("pokemon/"+(pokeJson.getString("nome"))+".png");

                TextureData textureData = pokemonTexture.getTextureData();
                if (!textureData.isPrepared()) {
                    textureData.prepare();  // Prepare the texture data for pixmap access
                }
                Pixmap pokePixmap = textureData.consumePixmap();  // Now we can get the pixmap safely

                // Create a white Pixmap with the same dimensions and transparency
                Pixmap blackPixmap = new Pixmap(pokemonTexture.getWidth() / 4, pokemonTexture.getHeight(), Pixmap.Format.RGBA8888);
                for (int x = 0; x < (pokemonTexture.getWidth() / 4); x++) {
                    for (int y = 0; y < pokemonTexture.getHeight(); y++) {
                        int pixel = pokePixmap.getPixel(x, y);
                        int alpha = pixel & 0x000000FF;  // Extract the alpha component
                        blackPixmap.drawPixel(x, y, (0x00000000 | alpha));  // Set RGB to white, keep original alpha
                    }
                }

                Texture blackTexture = new Texture(blackPixmap);
                pokePixmap.dispose();  // Dispose original pixmap to free resources
                blackPixmap.dispose();  // Dispose white pixmap after creating the texture

                TextureRegion whiteRegion = new TextureRegion(blackTexture);
                Image blackOverlay = new Image(whiteRegion);  // Create an overlay image with white shape
                blackOverlay.setSize(pokemonTexture.getWidth()/4, pokemonTexture.getHeight());

                botImage = blackOverlay;

                //posizioni delle varie label
                labelNome.setPosition(44*4, 144*4);
                labelNome.setFontScale(1.5f);

                labelTipo.setPosition(78*4, 132.5f*4);
                labelTipo.setFontScale(1f);

                labelAltezza.setPosition(47*4, 115*4);
                labelAltezza.setFontScale(2f);

                labelPeso.setPosition(47*4, 96*4);
                labelPeso.setFontScale(1.8f);

                labelDescrizione.setPosition(40*4, 72*4, Align.topLeft);
                labelDescrizione.setFontScale(1f);

                labeltipoEffettivo1.setPosition(133*4, 142*4);
                labeltipoEffettivo1.setFontScale(1f);
                stage.addActor(labeltipoEffettivo1);
                pokedexActor.add(labeltipoEffettivo1);
                pokeActorInfo.add(labeltipoEffettivo1);

                labeltipoEffettivo2.setPosition(154*4, 142*4);
                labeltipoEffettivo2.setFontScale(1f);
                stage.addActor(labeltipoEffettivo2);
                pokedexActor.add(labeltipoEffettivo2);
                pokeActorInfo.add(labeltipoEffettivo2);

                botImage.setSize(37*4, 37*4);
                botImage.setPosition(182*4, 115*4);


            }else if (isPokemonFounded == 1) {

                labelAltezza = new Label(pokeJson.getString("altezza"), new Label.LabelStyle(font1, null));
                labelPeso = new Label(pokeJson.getString("peso"), new Label.LabelStyle(font1, null));
                labelNome = new Label(pokeJson.getString("nome"), new Label.LabelStyle(font1, null));
                labelTipo = new Label(pokeJson.getString("tipo"), new Label.LabelStyle(font1, null));

                
                pokemonTexture = new Texture("pokemon/"+(pokeJson.getString("nome"))+".png");
                int frameWidth = pokemonTexture.getWidth() / 4;
                int frameHeight = pokemonTexture.getHeight();

                TextureRegion[] pokeSelvFrames;

                pokeSelvFrames = new TextureRegion[2];
                for (int i = 0; i < 2; i++) {
                    pokeSelvFrames[i] = new TextureRegion(pokemonTexture, i * frameWidth, 0, frameWidth, frameHeight);
                }
        
                botImage = new Image(pokeSelvFrames[0]);

                //posizioni delle varie label
                labelNome.setPosition(44*4, 144*4);
                labelNome.setFontScale(1.5f);

                labelTipo.setPosition(78*4, 132.5f*4);
                labelTipo.setFontScale(1f);

                labelAltezza.setPosition(47*4, 115*4);
                labelAltezza.setFontScale(2f);

                labelPeso.setPosition(47*4, 96*4);
                labelPeso.setFontScale(1.8f);
                

                String[] parole = pokeJson.getString("descrizione").split(" ");
                StringBuilder rigaCorrente = new StringBuilder(parole[0]);

                for (int i = 1; i < parole.length; i++) {
                    if (rigaCorrente.length() + 1 + parole[i].length() <= 40) {
                        rigaCorrente.append(" ").append(parole[i]);
                    } else {
                        rigaCorrente.append("\n");
                        testoDescrizione += rigaCorrente.toString();
                        rigaCorrente = new StringBuilder(parole[i]);
                    }
                }

                if (rigaCorrente.length() > 0) {
                    rigaCorrente.append("\n");
                    testoDescrizione += rigaCorrente.toString();
                }

                labelDescrizione = new Label(testoDescrizione, new Label.LabelStyle(font1, null));
                labelDescrizione.setPosition(40*4, 72*4, Align.topLeft);
                labelDescrizione.setFontScale(1f);


                botImage.setSize(37*4, 37*4);
                botImage.setPosition(182*4, 115*4);


                String tipo1 = pokeJson.getString("tipoeffettivo1");
                String tipo2 = pokeJson.getString("tipoeffettivo2");
                Integer index = tipoToIndex.get(tipo1);
                Texture textureTipi = new Texture("squadra/types.png");
                int regionWidthType = textureTipi.getWidth();
                int regionHeightType = textureTipi.getHeight()/18;
                TextureRegion[] types = new TextureRegion[18];
                for (int i = 0; i < 18; i++) {
                    types[i] = new TextureRegion(textureTipi,0, regionHeightType*i, regionWidthType, regionHeightType);
                }
                imageType1 = new Image(types[index]);
                imageType1.setSize(60, 25);
                imageType1.setPosition(130*4, 143*4);
                pokeImageInfo.add(imageType1);
                animationImages.add(imageType1);
                stage.addActor(imageType1);
                
                if (!tipo2.equals("/")){
                    Integer index2 = tipoToIndex.get(tipo2);
                    imageType2 = new Image(types[index2]);
                    imageType2.setSize(60, 25);
                    imageType2.setPosition(151*4, 143*4);
                    stage.addActor(imageType2);
                    pokeImageInfo.add(imageType2);
                    animationImages.add(imageType2);
                }

                

            }

            //da cambiare o inventarsi qualcosa per rimuoverle quando si torna indietro
            pokeImageInfo.add(botImage);
            
            animationImages.add(botImage);
            
            pokeActorInfo.add(labelAltezza);
            pokeActorInfo.add(labelPeso);
            pokeActorInfo.add(labelDescrizione);
            pokeActorInfo.add(labelNome);
            pokeActorInfo.add(labelTipo);
            pokeActorInfo.add(labelDescrizione);

            pokedexActor.add(labelAltezza);
            pokedexActor.add(labelPeso);
            pokedexActor.add(labelDescrizione);
            pokedexActor.add(labelNome);
            pokedexActor.add(labelTipo);
            pokedexActor.add(labelDescrizione);

            stage.addActor(labelAltezza);
            stage.addActor(labelPeso);
            stage.addActor(labelDescrizione);
            stage.addActor(labelNome);
            stage.addActor(labelTipo);
            stage.addActor(botImage);

        } catch (Exception e) {
            System.out.println("Errore infoPoke, " + e);
        }        
    }
}
