package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

class Mossa {
    private String nome;
    private String tipo;
    private String maxPP;
    private String attPP;
    private String pokePlay;
    private String pokeEnemy;
    private float modifier;
    private int potenza;

    private Texture texture;
    private Sprite[] spriteArray;
    private HashMap<String, Integer> tipoToIndex;

    //liste dei poco efficace / per controllare e aggiungere il 0.25
    ArrayList<String> listaPEAcqua = new ArrayList<>(
        Arrays.asList("fuoco","acqua","ghiaccio","acciaio")
    );
    ArrayList<String> listaPEFuoco = new ArrayList<>(
        Arrays.asList("fuoco","erba","ghiaccio","coleottero","accaio","folletto")
    );
    ArrayList<String> listaPEErba = new ArrayList<>(
        Arrays.asList("acqua","elettro","erba","terra")
    );
    ArrayList<String> listaPEElettro = new ArrayList<>(
        Arrays.asList("volante","elettro","acciaio")
    );
    ArrayList<String> listaPENormale = new ArrayList<>(
        Arrays.asList()
    );
    ArrayList<String> listaPEPsico = new ArrayList<>(
        Arrays.asList("lotta","psico")
    );
    ArrayList<String> listaPELotta = new ArrayList<>(
        Arrays.asList("coleottero","roccia","buio")
    );
    ArrayList<String> listaPEVolante = new ArrayList<>(
        Arrays.asList("erba","lotta","coleottero")
    );
    ArrayList<String> listaPETerra = new ArrayList<>(
        Arrays.asList("veleno","roccia")
    );
    ArrayList<String> listaPEGhiaccio = new ArrayList<>(
        Arrays.asList("ghiaccio")
    );
    ArrayList<String> listaPERoccia = new ArrayList<>(
        Arrays.asList("normale","fuoco","veleno","volante")
    );
    ArrayList<String> listaPEBuio = new ArrayList<>(
        Arrays.asList("spettro","buio")
    );
    ArrayList<String> listaPEAcciaio = new ArrayList<>(
        Arrays.asList("normale","erba","ghiaccio","volante","psico","coleottero","roccia","drago","accaio","folletto")
    );
    ArrayList<String> listaPEFolletto = new ArrayList<>(
        Arrays.asList("lotta","coleottero","buio")
    );
    ArrayList<String> listaPEVeleno = new ArrayList<>(
        Arrays.asList("erba","lotta","veleno","coleottero","folletto")
    );
    ArrayList<String> listaPEColeottero = new ArrayList<>(
        Arrays.asList("erba","lotta","terra")
    );
    ArrayList<String> listaPEDrago = new ArrayList<>(
        Arrays.asList("acqua","erba","fuoco","elettro")
    );
    ArrayList<String> listaPESpettro = new ArrayList<>(
        Arrays.asList("veleno","coleottero")
    );

    //lsite dei super efficace / per controllare e aggiungre il *2
    ArrayList<String> listaSEAcqua = new ArrayList<>(
        Arrays.asList("erba","elettro")
    );
    ArrayList<String> listaSEFuoco = new ArrayList<>(
        Arrays.asList("terra","roccia","acqua")
    );
    ArrayList<String> listaSEErba = new ArrayList<>(
        Arrays.asList("fuoco","coleottero","ghiaccio","veleno","volante")
    );
    ArrayList<String> listaSEElettro = new ArrayList<>(
        Arrays.asList("terra")
    );
    ArrayList<String> listaSENormale = new ArrayList<>(
        Arrays.asList("lotta")
    );
    ArrayList<String> listaSEPsico = new ArrayList<>(
        Arrays.asList("buio","spettro","coleottero")
    );
    ArrayList<String> listaSELotta = new ArrayList<>(
         Arrays.asList("psico","folletto","volante")
    );
    ArrayList<String> listaSEVolante = new ArrayList<>(
        Arrays.asList("roccia","elettro","ghiaccio")
    );
    ArrayList<String> listaSETerra = new ArrayList<>(
        Arrays.asList("erba","acqua","ghiaccio")
    );
    ArrayList<String> listaSEGhiaccio = new ArrayList<>(
        Arrays.asList("fuoco","lotta","roccia","acciaio")
    );
    ArrayList<String> listaSERoccia = new ArrayList<>(
        Arrays.asList("lotta","erba","acqua","acciaio","terra")
    );
    ArrayList<String> listaSEBuio = new ArrayList<>(
        Arrays.asList("folletto","lotta","coleottero")
    );
    ArrayList<String> listaSEAcciaio = new ArrayList<>(
        Arrays.asList("terra","fuoco","lotta")
    );
    ArrayList<String> listaSEFolletto = new ArrayList<>(
        Arrays.asList("veleno","acciaio")
    );
    ArrayList<String> listaSEVeleno = new ArrayList<>(
        Arrays.asList("terra","psico")
    );
    ArrayList<String> listaSEColeottero = new ArrayList<>(
        Arrays.asList("fuoco","roccia","volante")
    );
    ArrayList<String> listaSEDrago = new ArrayList<>(
        Arrays.asList("drago","folletto","ghiaccio")
    );
    ArrayList<String> listaSESpettro = new ArrayList<>(
        Arrays.asList("buio","spettro")
    );

    //liste dei non efficace / per controllare e aggiungere il *0
    ArrayList<String> listaNEAcqua = new ArrayList<>();
    ArrayList<String> listaNEFuoco = new ArrayList<>();
    ArrayList<String> listaNEErba = new ArrayList<>();
    ArrayList<String> listaNEElettro = new ArrayList<>();
    ArrayList<String> listaNENormale = new ArrayList<>(
        Arrays.asList("spettro")
    );
    ArrayList<String> listaNEPsico = new ArrayList<>();
    ArrayList<String> listaNELotta = new ArrayList<>();
    ArrayList<String> listaNEVolante = new ArrayList<>(
        Arrays.asList("terra")
    );
    ArrayList<String> listaNETerra = new ArrayList<>(
        Arrays.asList("elettro")
    );
    ArrayList<String> listaNEGhiaccio = new ArrayList<>();
    ArrayList<String> listaNERoccia = new ArrayList<>();
    ArrayList<String> listaNEBuio = new ArrayList<>(
        Arrays.asList("psico")
    );
    ArrayList<String> listaNEAcciaio = new ArrayList<>();
    ArrayList<String> listaNEFolletto = new ArrayList<>(
        Arrays.asList("drago")
    );
    ArrayList<String> listaNEVeleno = new ArrayList<>();
    ArrayList<String> listaNEColeottero = new ArrayList<>();
    ArrayList<String> listaNEDrago = new ArrayList<>();
    ArrayList<String> listaNESpettro = new ArrayList<>(
        Arrays.asList("normale")
    );



    public void SpriteCutter() {
        // Carica la texture
        texture = new Texture("battle/fullLabelFight.png");

        // Divide la texture in 18 parti in altezza
        TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 6);

        // Inizializza l'array di sprite
        spriteArray = new Sprite[18];

        // Riempie l'array di sprite
        int indice = 0;
        for (int riga = 0; riga < 6; riga++) {
            for (int colonna = 0; colonna < 3; colonna++) {
                spriteArray[indice++] = new Sprite(textureRegions[riga][colonna]);
            }
        }

        // Inizializza la mappa di associazione tra nome del tipo e indice
        tipoToIndex = new HashMap<>();
        tipoToIndex.put("Terra", 0);
        tipoToIndex.put("Acqua", 1);
        tipoToIndex.put("Spettro", 2);
        tipoToIndex.put("Lotta", 3);
        tipoToIndex.put("Psico", 4);
        tipoToIndex.put("Erba", 5);
        tipoToIndex.put("Normale", 6);
        tipoToIndex.put("Veleno", 7);
        tipoToIndex.put("Elettro", 8);
        tipoToIndex.put("Acciaio", 9);
        tipoToIndex.put("Roccia", 10);
        tipoToIndex.put("Drago", 11);
        tipoToIndex.put("Volante", 12);
        tipoToIndex.put("Fuoco", 13);
        tipoToIndex.put("Ghiaccio", 14);
        tipoToIndex.put("Coleottero", 15);
        tipoToIndex.put("Buio", 16);
        tipoToIndex.put("Folletto", 17);
        // Aggiungi gli altri tipi pokemon...
    }

    // Restituisce lo sprite corrispondente al nome del tipo
    public Sprite getLabelTipo(String nomeTipo) {
        SpriteCutter();
        Integer index = tipoToIndex.get(nomeTipo);
        if (index != null) {
            return spriteArray[index];
        } else {
            return null; // Se il nome del tipo non è valido, restituisce null
        }
    }

    // Restituisce l'array di sprite
    public Sprite[] tagliaTipi() {
        return spriteArray;
    }

    public Mossa(String nome, String tipo, String maxPP, String attPP) {
        this.nome = nome;
        this.tipo = tipo;
        this.maxPP = maxPP;
        this.attPP = attPP;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getmaxPP() {
        return maxPP;
    }

    public String getattPP() {
        return attPP;
    }

    public void estraiPotenza(){
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("pokemon/mosse.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get(nome);
            potenza = Integer.parseInt(pokeJson.getString("potenza"));
    }


    public void calcolaModifier(){
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get(pokeEnemy);
        String tipo1 = pokeJson.getString("tipo1");
        String tipo2 = pokeJson.getString("tipo2");

    }


    public int calcolaDanno(int attacco, int difesa, int livello, String pokePlay, String pokeEnemy){
        this.pokePlay=pokePlay;
        this.pokeEnemy=pokeEnemy;
        estraiPotenza();
        calcolaModifier();
        float danno;

        if (potenza==0){
            return 1000;
        }
        else if (potenza==999){
            return 40;
        }
        else{
            danno = ((((((livello*2)/5)+2)*potenza*attacco/difesa)/2)+2)+modifier;
            int dannoIntero = (int) danno;
            return dannoIntero;
        }
        
    }
}