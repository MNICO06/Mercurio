package com.mercurio.game.Screen;

import org.json.JSONArray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.pokemon.Battle;
import com.mercurio.game.pokemon.infoPoke;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Box extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    Array<Image> animationImages = new Array<>();
    Array<Texture> animationTextures = new Array<>();
    private HashMap<Image, Integer> imageIds = new HashMap<>();
    private String nomePoke;
    private TextureRegion[] sfondi;
    private MercurioMain game;
    private Image avantiImage;
    private Image indietroImage;
    private Image infoImage;
    private Image spostaImage;
    private Image liberaImage;
    Image background;

    private float yPoke;                //questa è la y fissa da mettere al pokemon
    private float valoreCambioRiga = 70; //questo è il valore da sommare ogni volta che va cambiata la riga
    private int cont = 0;

    private int boxAttuale = 1;         //mi salvo il box attuale e grazie a questo riesco a capire da che numero partire per il render dei pokemon nel box
    private int totalePagineBox = 1;    //questo non appena viene chiamato per la prima volta caricaBorsa viene calcolato il numero totale di pagine

    private int posizionePokemonSelezionato = -1;
    private Image immagineSelezionato;

    private infoPoke infoPoke;

    //TODO: per le info poke chiedo al chatty di fare in modo di accettare un altro parametro che sarà (se è da squadra o no) e poi va a prendere nel caso in cui è nel box il nome e recupera tutti i dati
    

    public Box(MercurioMain game){
        this.game=game;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);
        getNumeroPagine();

        show();
        caricaPagina(1);
        //caricaBorsa();
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
        Gdx.input.setInputProcessor(stage);
    }

    public void leggiPoke(int numero) {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get(numero);
        nomePoke = pokeJson.getString("nomePokemon", "invisibile");
    }

    public void disegnaPoke(int param, int cont){

        if (!nomePoke.isEmpty()) {

            Texture animationTexture = new Texture("pokemon/" + nomePoke + "Label.png");
            animationTextures.add(animationTexture);
            TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
            // Crea un'immagine utilizzando solo la prima metà dell'immagine
            Image animationImage = new Image(animationRegion);
            animationImage.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight()*2 + 10);
            animationImage.setPosition(250 + cont*80,  yPoke);

            imageIds.put(animationImage, param);

            animationImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (param == posizionePokemonSelezionato) {
                        //nel caso in cui viene ripremuto il pokemon già selezionato non fa nulla

                    }else {

                        if (posizionePokemonSelezionato != -1) {
                            //resettare le dimensioni di quello di prima
                            immagineSelezionato.setSize(animationTexture.getWidth() + 10, animationTexture.getHeight()*2 + 10);
                        }

                        float originalWidth = animationImage.getWidth();
                        float originalHeight = animationImage.getHeight();

                        // Aumenta leggermente la dimensione dell'immagine
                        animationImage.setSize(originalWidth * 1.1f, originalHeight * 1.1f);
                        immagineSelezionato = animationImage;

                        posizionePokemonSelezionato = param;

                        //rendi i 3 pulsanti visibili
                        liberaImage.setVisible(true);
                        infoImage.setVisible(true);
                        spostaImage.setVisible(true);
                    }

                    
                    
                }
            });

            animationImages.add(animationImage);
            stage.addActor(animationImage);

        }
    }

    @Override
    public void show() {

        Texture textureBack = new Texture("sfondo/sfondoBox.png");

        int regionWidth = textureBack.getWidth() / 4;
        int regionHeight = textureBack.getHeight() / 6;

        sfondi = new TextureRegion[24];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                sfondi[i+j] = new TextureRegion(textureBack, i * regionWidth, j* regionHeight, regionWidth, regionHeight);
            }
        }

        // Add background
        background = new Image(sfondi[0]);
        // Ritaglia l'immagine per adattarla alla dimensione dello schermo
        background.setSize(700, 600);
        background.setPosition(((Gdx.graphics.getWidth() - background.getWidth()) / 1.5f), (Gdx.graphics.getHeight() - background.getHeight()) / 2);
        stage.addActor(background);

        // Aggiungi le immagini "avanti" e "indietro" in alto
        Texture avantiTexture = new Texture("assets/sfondo/avanti.png");
        Texture indietroTexture = new Texture("assets/sfondo/indietro.png");

        avantiImage = new Image(avantiTexture);
        indietroImage = new Image(indietroTexture);

        //aggiungi le immagini con i vari pulsanti (sposta, info, libera)
        Texture pulsanteSposta = new Texture("assets/squadra/sposta.png");
        Texture pulsanteInfo = new Texture("assets/squadra/info.png");
        Texture pulsanteLibera = new Texture("assets/squadra/cancel.png");            //c'è da fare il tasto libera per ora metto cancel

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

        //listener per la freccia avanti
        avantiImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //se non la pagina successiva non esiste non fa nulla
                if ((boxAttuale + 1) > totalePagineBox) {

                }else {
                    boxAttuale += 1;
                    caricaPagina(boxAttuale);
                    aggiornaVisibilitaFreccie();
                    label.setText(getBoxLabel(boxAttuale));
                }

                liberaImage.setVisible(false);
                infoImage.setVisible(false);
                spostaImage.setVisible(false);

                posizionePokemonSelezionato = -1;
            }
        });

        //listener per la frecia indietro
        indietroImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //se la pagina precedente è la 0 o minore non fa nulla
                if ((boxAttuale - 1) <= 0) {

                }else {
                    boxAttuale -= 1;
                    caricaPagina(boxAttuale);
                    aggiornaVisibilitaFreccie();
                    label.setText(getBoxLabel(boxAttuale));
                }
                liberaImage.setVisible(false);
                infoImage.setVisible(false);
                spostaImage.setVisible(false);

                posizionePokemonSelezionato = -1;
            }
        });

        stage.addActor(avantiImage);
        stage.addActor(indietroImage);

        // Calcola la posizione in X per posizionare liberaImage sulla destra del background
        float tastiX = background.getX() + background.getWidth() - 170;

        // Calcola la fine del background in Y (la posizione più bassa del background)
        float tastiY = background.getY() - 30;

        liberaImage.setSize(56*3, 24*3);
        infoImage.setSize(56*3, 24*3);
        spostaImage.setSize(56*3, 24*3);
        liberaImage.setPosition(tastiX, tastiY);
        infoImage.setPosition(tastiX - 170, tastiY);
        spostaImage.setPosition(tastiX  - 340, tastiY);

        liberaImage.setVisible(false);
        infoImage.setVisible(false);
        spostaImage.setVisible(false);

        liberaImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
            }
        });
        infoImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                infoPoke = new infoPoke(stage, posizionePokemonSelezionato, true);
            }
        });
        spostaImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
            }
        });

        stage.addActor(liberaImage);
        stage.addActor(infoImage);
        stage.addActor(spostaImage);


        //da controllare se rimuovere le frecce in caso in cui non ci sia la prossima pagina
        aggiornaVisibilitaFreccie();

    }

    //da chiamare ad avvio del box per calolare il numero di pagine (salvate in una variabile globale)
    private void getNumeroPagine() {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        totalePagineBox = (int) Math.ceil(json.size / 48.0);

    }


    //basta chiamare la funzione mettendo il numero della pagina attuale
    private void caricaPagina(int pagina) {
        //va a rimuovere tutte le immagini dei pokemon della vecchia pagina
        clearPage();

        background.setDrawable(new TextureRegionDrawable(new TextureRegion(sfondi[pagina - 1])));

        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        

        if (pagina < totalePagineBox) {

            for (int i = (48 * (pagina - 1)); i < (48 * (pagina)); i++) {
                leggiPoke(i);
                disegnaPoke(i, cont);
                cont ++;
                if (cont == 8) {
                    cont = 0;
                    yPoke = yPoke - valoreCambioRiga;
                }
            }

        } else if (pagina == totalePagineBox) {

            for (int i = (48 * (pagina - 1)); i < json.size; i++) {
                leggiPoke(i);
                disegnaPoke(i, cont);
                cont ++;
                if (cont == 8) {
                    cont = 0;
                    yPoke = yPoke - valoreCambioRiga;
                }
            }

        }
    }

    /*in teoria non mi serve più
    public void caricaBorsa() {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/box.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        totalePagineBox = (int) Math.ceil(48 / json.size);
        System.out.println("json size: " + json.size);                  //48
        System.out.println("formula: " + (48 * (boxAttuale - 1)));      //0
        System.out.println("calcolo fine: " + (48 * (boxAttuale)));     //48

        // Crea un'etichetta per ogni oggetto nel file JSON
        //48 pokemon e si riempie preciso il box
        for (int i = 0; i < json.size; i++) {
            leggiPoke(i);
            disegnaPoke(i, cont);
            cont ++;
            if (cont == 8) {
                cont = 0;
                yPoke = yPoke - valoreCambioRiga;
            }
        }
    }
    */

    private void clearPage() {        
        // Rimuove tutti gli attori delle immagini di Pokémon dalla stage
        for (Image img : animationImages) {
            img.remove();
        }
        animationImages.clear(); // Pulisce l'array per evitare riferimenti residui
        cont = 0; // Resetta il contatore
        yPoke = ((Gdx.graphics.getHeight() + sfondi[0].getRegionHeight()) / 2) + 10; // Resetta la posizione verticale
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

}