package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class infoPoke {

    private String nomePoke;
    private String LVPoke;
    private String maxPokeHP;
    private String currentPokeHP;
    private String nomeBall;
    private ArrayList<Mossa> listaMosse = new ArrayList<>();
     private ArrayList<Integer> statsPlayer = new ArrayList<>();
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private Stage stage;
    private Array<Actor> infoActors;
    private HashMap<String, Integer> tipoToIndex;

    public infoPoke(Stage stage, int numDelPoke) {
        this.stage = stage;
        this.batch = (SpriteBatch) stage.getBatch();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        this.infoActors = new Array<>(); // Inizializza l'array degli attori delle info
        

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Background dell'info stage
        Texture backgroundTexture = new Texture("squadra/infoPoke.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
        infoActors.add(background);

        // Label per la chiusura
        Texture cancelTexture = new Texture("squadra/cancel.png");
        Image cancelImage = new Image(cancelTexture);
        cancelImage.setSize(56*3, 24*3);
        cancelImage.setPosition(screenWidth - cancelImage.getWidth(), screenHeight - cancelImage.getHeight());
        stage.addActor(cancelImage);
        infoActors.add(cancelImage);

        cancelImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearInfoPoke();
            }
        });

        leggiPoke(numDelPoke);

        //riempie la hash map con gli index della image dei tipi per piazzarli dopo
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

        aggiungiDati();
        
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime
        stage.draw();
    }

    private void clearInfoPoke() {
        // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione precedente
        for (Actor actor : infoActors) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
        infoActors.clear(); // Pulisci l'array degli attori dell'inventario
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
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
                Mossa mossa=new Mossa(nomeMossa, tipoMossa, maxPP, attPP, null); //gli passo null invece che Battle se non ne ho bisogno
                listaMosse.add(mossa);
            }

    }

    private void aggiungiDati(){
        //nome del pokemon
        Label labelNomePokemon = new Label(nomePoke, new Label.LabelStyle(font, null));
        labelNomePokemon.setPosition(28,565); 
        labelNomePokemon.setFontScale(5f);
        stage.addActor(labelNomePokemon);
        infoActors.add(labelNomePokemon);


        //livello del pokemon
        Label labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
        labelLV.setPosition(520,505); 
        labelLV.setFontScale(5f);
        stage.addActor(labelLV);
        infoActors.add(labelLV);


        //pokeball del pokemon
        Texture textureBall = new Texture("battle/"+nomeBall+"Player.png");
        int regionWidth = textureBall.getWidth() / 3;
        int regionHeight = textureBall.getHeight();
        // Inizializza l'array delle TextureRegion della ball
        TextureRegion[] ball = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            ball[i] = new TextureRegion(textureBall, i * regionWidth, 0, regionWidth, regionHeight);
        }
        // Crea e aggiungi l'immagine della ball allo stage
        Image imageBall = new Image(ball[0]);
        imageBall.setSize(16*3.5f, 25*3.5f);
        imageBall.setPosition(430, 542);
        stage.addActor(imageBall);
        infoActors.add(imageBall);


        //immagine del pokemon
        Texture texturePoke = new Texture("pokemon/"+nomePoke+".png");
        int regionWidthPoke = texturePoke.getWidth() / 4;
        int regionHeightPoke = texturePoke.getHeight();
        // Inizializza l'array delle TextureRegion della ball
        TextureRegion[] poke = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            poke[i] = new TextureRegion(texturePoke, i * regionWidthPoke, 0, regionWidthPoke, regionHeightPoke);
        }
        Image imagePoke = new Image(poke[0]);
        imagePoke.setSize(172, 172);
        imagePoke.setPosition(760, 428);
        stage.addActor(imagePoke);
        infoActors.add(imagePoke);


        //tipi del pokemon
        FileHandle file = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        JsonValue pokeJson = json.get(nomePoke);
        String tipo1 = pokeJson.getString("tipo1");
        String tipo2 = pokeJson.getString("tipo2");
        Integer index = tipoToIndex.get(tipo1);
        Texture textureTipi = new Texture("squadra/types.png");
        int regionWidthType = textureTipi.getWidth();
        int regionHeightType = textureTipi.getHeight()/18;
        TextureRegion[] types = new TextureRegion[18];
        for (int i = 0; i < 18; i++) {
            types[i] = new TextureRegion(textureTipi,0, regionHeightType*i, regionWidthType, regionHeightType);
        }
        Image imageType1 = new Image(types[index]);
        imageType1.setSize(32*3.7f, 15*3.7f);
        imageType1.setPosition(45, 483);
        stage.addActor(imageType1);
        infoActors.add(imageType1);
        if (!tipo2.equals("/")){
            Integer index2 = tipoToIndex.get(tipo2);
            Image imageType2 = new Image(types[index2]);
            imageType2.setSize(32*3.7f, 15*3.7f);
            imageType2.setPosition(215, 483);
            stage.addActor(imageType2);
            infoActors.add(imageType2);
        }


        //HP Tot
        Label hpTot = new Label(maxPokeHP, new Label.LabelStyle(font2, null));
        hpTot.setPosition(904,375); 
        hpTot.setFontScale(2f);
        stage.addActor(hpTot);
        infoActors.add(hpTot);


        //HP Attuali
        int diff=0;
        if (Integer.parseInt(currentPokeHP)<=99 && Integer.parseInt(currentPokeHP)>9){
            diff=20;
        }
        else if(Integer.parseInt(currentPokeHP)<=9){
            diff=40;
        }
        Label hpAtt = new Label(currentPokeHP, new Label.LabelStyle(font2, null));
        hpAtt.setPosition(790+diff,375); 
        hpAtt.setFontScale(2f);
        stage.addActor(hpAtt);
        infoActors.add(hpAtt);


    }

}
