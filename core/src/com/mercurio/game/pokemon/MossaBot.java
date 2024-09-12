package com.mercurio.game.pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

class MossaBot {
    private String nome;
    private String tipo;
    private String maxPP;
    private String attPP;
    private String pokePlay;
    private String pokeEnemy;
    private float modifier=1.0f;
    private int potenza;
    private String tipo1;
    private String tipo2;
    private String tipo3;
    private String tipo4;
    private Battle chiamante;
    private int modifierPerText=0;

    private Texture texture;
    private Sprite[] spriteArray;
    private HashMap<String, Integer> tipoToIndex;

    // Lista di tipi di Pokémon e relativa resistenza
    ArrayList<String> listaPEAcqua = new ArrayList<>(
        Arrays.asList("Fuoco","Acqua","Ghiaccio","Acciaio")
    );
    ArrayList<String> listaPEFuoco = new ArrayList<>(
        Arrays.asList("Fuoco","Erba","Ghiaccio","Coleottero","Acciaio","Folletto")
    );
    ArrayList<String> listaPEErba = new ArrayList<>(
        Arrays.asList("Acqua","Elettro","Erba","Terra")
    );
    ArrayList<String> listaPEElettro = new ArrayList<>(
        Arrays.asList("Volante","Elettro","Acciaio")
    );
    ArrayList<String> listaPENormale = new ArrayList<>(
        Arrays.asList()
    );
    ArrayList<String> listaPEPsico = new ArrayList<>(
        Arrays.asList("Lotta","Psico")
    );
    ArrayList<String> listaPELotta = new ArrayList<>(
        Arrays.asList("Coleottero","Roccia","Buio")
    );
    ArrayList<String> listaPEVolante = new ArrayList<>(
        Arrays.asList("Erba","Lotta","Coleottero")
    );
    ArrayList<String> listaPETerra = new ArrayList<>(
        Arrays.asList("Veleno","Roccia")
    );
    ArrayList<String> listaPEGhiaccio = new ArrayList<>(
        Arrays.asList("Ghiaccio")
    );
    ArrayList<String> listaPERoccia = new ArrayList<>(
        Arrays.asList("Normale","Fuoco","Veleno","Volante")
    );
    ArrayList<String> listaPEBuio = new ArrayList<>(
        Arrays.asList("Spettro","Buio")
    );
    ArrayList<String> listaPEAcciaio = new ArrayList<>(
        Arrays.asList("Normale","Erba","Ghiaccio","Volante","Psico","Coleottero","Roccia","Drago","Acciaio","Folletto")
    );
    ArrayList<String> listaPEFolletto = new ArrayList<>(
        Arrays.asList("Lotta","Coleottero","Buio")
    );
    ArrayList<String> listaPEVeleno = new ArrayList<>(
        Arrays.asList("Erba","Lotta","Veleno","Coleottero","Folletto")
    );
    ArrayList<String> listaPEColeottero = new ArrayList<>(
        Arrays.asList("Erba","Lotta","Terra")
    );
    ArrayList<String> listaPEDrago = new ArrayList<>(
        Arrays.asList("Acqua","Erba","Fuoco","Elettro")
    );
    ArrayList<String> listaPESpettro = new ArrayList<>(
        Arrays.asList("Veleno","Coleottero")
    );

    // Lista di tipi di Pokémon e relativi danni super efficaci / per controllare e aggiungere il *2
    ArrayList<String> listaSEAcqua = new ArrayList<>(
        Arrays.asList("Erba","Elettro")
    );
    ArrayList<String> listaSEFuoco = new ArrayList<>(
        Arrays.asList("Terra","Roccia","Acqua")
    );
    ArrayList<String> listaSEErba = new ArrayList<>(
        Arrays.asList("Fuoco","Coleottero","Ghiaccio","Veleno","Volante")
    );
    ArrayList<String> listaSEElettro = new ArrayList<>(
        Arrays.asList("Terra")
    );
    ArrayList<String> listaSENormale = new ArrayList<>(
        Arrays.asList("Lotta")
    );
    ArrayList<String> listaSEPsico = new ArrayList<>(
        Arrays.asList("Buio","Spettro","Coleottero")
    );
    ArrayList<String> listaSELotta = new ArrayList<>(
        Arrays.asList("Psico","Folletto","Volante")
    );
    ArrayList<String> listaSEVolante = new ArrayList<>(
        Arrays.asList("Roccia","Elettro","Ghiaccio")
    );
    ArrayList<String> listaSETerra = new ArrayList<>(
        Arrays.asList("Erba","Acqua","Ghiaccio")
    );
    ArrayList<String> listaSEGhiaccio = new ArrayList<>(
        Arrays.asList("Fuoco","Lotta","Roccia","Acciaio")
    );
    ArrayList<String> listaSERoccia = new ArrayList<>(
        Arrays.asList("Lotta","Erba","Acqua","Acciaio","Terra")
    );
    ArrayList<String> listaSEBuio = new ArrayList<>(
        Arrays.asList("Folletto","Lotta","Coleottero")
    );
    ArrayList<String> listaSEAcciaio = new ArrayList<>(
        Arrays.asList("Terra","Fuoco","Lotta")
    );
    ArrayList<String> listaSEFolletto = new ArrayList<>(
        Arrays.asList("Veleno","Acciaio")
    );
    ArrayList<String> listaSEVeleno = new ArrayList<>(
        Arrays.asList("Terra","Psico")
    );
    ArrayList<String> listaSEColeottero = new ArrayList<>(
        Arrays.asList("Fuoco","Roccia","Volante")
    );
    ArrayList<String> listaSEDrago = new ArrayList<>(
        Arrays.asList("Drago","Folletto","Ghiaccio")
    );
    ArrayList<String> listaSESpettro = new ArrayList<>(
        Arrays.asList("Buio","Spettro")
    );


    //liste dei non efficace / per controllare e aggiungere il *0
    ArrayList<String> listaNEAcqua = new ArrayList<>();
    ArrayList<String> listaNEFuoco = new ArrayList<>();
    ArrayList<String> listaNEErba = new ArrayList<>();
    ArrayList<String> listaNEElettro = new ArrayList<>();
    ArrayList<String> listaNENormale = new ArrayList<>(
        Arrays.asList("Spettro")
    );
    ArrayList<String> listaNEPsico = new ArrayList<>();
    ArrayList<String> listaNELotta = new ArrayList<>();
    ArrayList<String> listaNEVolante = new ArrayList<>(
        Arrays.asList("Terra")
    );
    ArrayList<String> listaNETerra = new ArrayList<>(
        Arrays.asList("Elettro")
    );
    ArrayList<String> listaNEGhiaccio = new ArrayList<>();
    ArrayList<String> listaNERoccia = new ArrayList<>();
    ArrayList<String> listaNEBuio = new ArrayList<>(
        Arrays.asList("Psico")
    );
    ArrayList<String> listaNEAcciaio = new ArrayList<>();
    ArrayList<String> listaNEFolletto = new ArrayList<>(
        Arrays.asList("Drago")
    );
    ArrayList<String> listaNEVeleno = new ArrayList<>();
    ArrayList<String> listaNEColeottero = new ArrayList<>();
    ArrayList<String> listaNEDrago = new ArrayList<>();
    ArrayList<String> listaNESpettro = new ArrayList<>(
        Arrays.asList("Normale","Lotta")
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


    // Restituisce l'array di sprite
    public Sprite[] tagliaTipi() {
        return spriteArray;
    }

    public MossaBot(String nome, String tipo, Battle battle) {
        this.nome = nome;
        this.tipo = tipo;
        if (battle!=null){
            this.chiamante = battle;
        }

    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
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
        tipo1 = pokeJson.getString("tipo1");
        tipo2 = pokeJson.getString("tipo2");

        ArrayList<String> listaPEControllo1 = new ArrayList<>(reimpiListeControllo(tipo1,"PE"));
        ArrayList<String> listaPEControllo2 = new ArrayList<>(reimpiListeControllo(tipo2,"PE"));
        ArrayList<String> listaSEControllo1 = new ArrayList<>(reimpiListeControllo(tipo1,"SE"));
        ArrayList<String> listaSEControllo2 = new ArrayList<>(reimpiListeControllo(tipo2,"SE"));
        ArrayList<String> listaNEControllo1 = new ArrayList<>(reimpiListeControllo(tipo1,"NE"));
        ArrayList<String> listaNEControllo2 = new ArrayList<>(reimpiListeControllo(tipo2,"NE"));

        // Controllo se tipo1 è presente nell'ArrayList listaPEControllo1
        int indexPEControllo1 = 0;
        while (indexPEControllo1 < listaPEControllo1.size()) {
            if (tipo.equals(listaPEControllo1.get(indexPEControllo1))) {
                modifier *= 0.5f;
                modifierPerText-=1;
                break;
            }
            indexPEControllo1++;
        }

        // Controllo se tipo2 è presente nell'ArrayList listaPEControllo2
        int indexPEControllo2 = 0;
        while (indexPEControllo2 < listaPEControllo2.size()) {
            if (!tipo2.equals("") && tipo.equals(listaPEControllo2.get(indexPEControllo2))) {
                modifier *= 0.5f;
                modifierPerText-=1;
                break;
            }
            indexPEControllo2++;
        }

        // Controllo se tipo1 è presente nell'ArrayList listaSEControllo1
        int indexSEControllo1 = 0;
        while (indexSEControllo1 < listaSEControllo1.size()) {
            if (tipo.equals(listaSEControllo1.get(indexSEControllo1))) {
                modifier *= 2;
                modifierPerText+=1;
                break;
            }
            indexSEControllo1++;
        }

        // Controllo se tipo2 è presente nell'ArrayList listaSEControllo2
        int indexSEControllo2 = 0;
        while (indexSEControllo2 < listaSEControllo2.size()) {
            if (!tipo2.equals("") && tipo.equals(listaSEControllo2.get(indexSEControllo2))) {
                modifier *= 2;
                modifierPerText+=1;
                break;
            }
            indexSEControllo2++;
        }

        if (modifierPerText>0){
            chiamante.piazzaLabel5();
        }
        else if (modifierPerText<0){
            chiamante.piazzaLabel6();
        }

        // Controllo se tipo1 è presente nell'ArrayList listaNEControllo1
        int indexNEControllo1 = 0;
        while (indexNEControllo1 < listaNEControllo1.size()) {
            if (tipo.equals(listaNEControllo1.get(indexNEControllo1))) {
                modifier *= 0;
                chiamante.piazzaLabel7();
                break;
            }
            indexNEControllo1++;
        }

        // Controllo se tipo2 è presente nell'ArrayList listaNEControllo2
        int indexNEControllo2 = 0;
        while (indexNEControllo2 < listaNEControllo2.size()) {
            if (!tipo2.equals("") && tipo.equals(listaNEControllo2.get(indexNEControllo2))) {
                modifier *= 0;
                chiamante.piazzaLabel7();
                break;
            }
            indexNEControllo2++;
        }

        if(modifier!=0){
            Random random = new Random();
            // Genera un numero casuale compreso tra 1 e 24
            int randomNumber = random.nextInt(24) + 1; // Genera un numero tra 1 e 24 inclusi
            // Verifica se il numero generato è uguale a 1 (probabilità 1/24)
            //randomNumber=16; //mi serve per fare dei controlli sui brutti colpi :)
            if (randomNumber == 16) {
                modifier *= 2f; // Modifica il modifier di conseguenza
                chiamante.piazzaLabel9();
            }

            float stab=calcolaStab();

            modifier *= stab;
        }
    }

    public float calcolaStab(){
        float stab=1;

        // Carica il file JSON
        FileHandle file = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        JsonValue pokeJson = json.get(pokePlay);
        tipo3 = pokeJson.getString("tipo1");
        tipo4 = pokeJson.getString("tipo2");


        if (tipo.equals(tipo3)||tipo.equals(tipo4)){
            stab=1.5f;
        }

        return stab;

    }


    public ArrayList<String> reimpiListeControllo(String tipo, String typeLista) {
        ArrayList<String> listaControllo = new ArrayList<>();
    
        switch (tipo) {
            case "Acqua":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEAcqua);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEAcqua);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEAcqua);
                }
                break;
            case "Fuoco":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEFuoco);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEFuoco);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEFuoco);
                }
                break;
            case "Erba":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEErba);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEErba);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEErba);
                }
                break;
            case "Elettro":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEElettro);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEElettro);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEElettro);
                }
                break;
            case "Normale":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPENormale);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSENormale);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNENormale);
                }
                break;
            case "Psico":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEPsico);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEPsico);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEPsico);
                }
                break;
            case "Lotta":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPELotta);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSELotta);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNELotta);
                }
                break;
            case "Volante":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEVolante);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEVolante);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEVolante);
                }
                break;
            case "Terra":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPETerra);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSETerra);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNETerra);
                }
                break;
            case "Ghiaccio":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEGhiaccio);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEGhiaccio);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEGhiaccio);
                }
                break;
            case "Roccia":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPERoccia);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSERoccia);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNERoccia);
                }
                break;
            case "Buio":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEBuio);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEBuio);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEBuio);
                }
                break;
            case "Acciaio":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEAcciaio);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEAcciaio);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEAcciaio);
                }
                break;
            case "Folletto":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEFolletto);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEFolletto);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEFolletto);
                }
                break;
            case "Veleno":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEVeleno);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEVeleno);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEVeleno);
                }
                break;
            case "Coleottero":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEColeottero);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEColeottero);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEColeottero);
                }
                break;
            case "Drago":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPEDrago);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSEDrago);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNEDrago);
                }
                break;
            case "Spettro":
                if (typeLista.equals("PE")) {
                    listaControllo.addAll(listaPESpettro);
                } else if (typeLista.equals("SE")) {
                    listaControllo.addAll(listaSESpettro);
                } else if (typeLista.equals("NE")) {
                    listaControllo.addAll(listaNESpettro);
                }
                break;
            default:
                // Gestisci il caso in cui il tipo non sia gestito
                break;
        }       

        return listaControllo;
    }
    

    public String getmaxPP() {
        FileHandle file = Gdx.files.internal("pokemon/mosse.json");
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        maxPP=json.get(nome).getString("pp");
        return maxPP;
    }


    public int calcolaDanno(int attacco, int difesa, int livello, String pokePlay, String pokeEnemy){
        modifier=1;
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
            danno = ((((((livello*2)/5)+2)*potenza*attacco/difesa)/50)+2)*modifier;
            int dannoIntero = (int) danno;
            return dannoIntero;
        }
        
    }
}