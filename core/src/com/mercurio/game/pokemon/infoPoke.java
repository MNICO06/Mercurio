package com.mercurio.game.pokemon;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class infoPoke {

    HashMap<String, Integer> indiciStatistiche = new HashMap<>();
    private String nomePoke;
    private String LVPoke;
    private String maxPokeHP;
    private String currentPokeHP;
    private String nomeBall;
    private int exp;
    private ArrayList<Mossa> listaMosse = new ArrayList<>();
     private ArrayList<String> statsPlayer = new ArrayList<>();
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private Stage stage;
    private Array<Actor> infoActors;
    private HashMap<String, Integer> tipoToIndex;
    private int indexAtt;
    private int indexDef;
    private int indexAttSP;
    private int indexDefSP;
    private int indexVel;
    private int numDelPoke;
    private int maxExp;
    private int crescitaType;

    private boolean box;
    String index;


    public infoPoke(Stage stage, int numDelPoke, boolean box) {
        this.stage = stage;
        this.numDelPoke = numDelPoke;
        this.box = box;
        this.batch = (SpriteBatch) stage.getBatch();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.local("assets/font/small_letters_font.fnt"));
        font2 = new BitmapFont(Gdx.files.local("assets/font/font.fnt"));
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

        leggiPoke(numDelPoke, box);

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

    public void leggiPoke(int numero, boolean box) {

        FileHandle file;
        
        if (box) {
            file = Gdx.files.local("assets/ashJson/box.json");
            index = "" + numero;
        }else {
            file = Gdx.files.local("assets/ashJson/squadra.json");
            index = "poke" + numero;
        }

        // Carica il file JSON
        String jsonString = file.readString();
        
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

            JsonValue pokeJson = json.get(index);
            nomePoke = pokeJson.getString("nomePokemon");
            LVPoke = pokeJson.getString("livello");
            exp = pokeJson.getInt("esperienza");

            int indice=0;
            JsonValue statistiche = pokeJson.get("statistiche"); 
            for (JsonValue stat : statistiche) {
                statsPlayer.add(stat.asString());
                indiciStatistiche.put(stat.name, indice);
                indice++;
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
        FileHandle file = Gdx.files.local("assets/pokemon/Pokemon.json");
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



        //HP Attuali
        int diff=0;
        if (Integer.parseInt(currentPokeHP)<=99 && Integer.parseInt(currentPokeHP)>9){
            diff=20;
        }
        else if(Integer.parseInt(currentPokeHP)<=9){
            diff=40;
        }
        Label hpAtt = new Label(currentPokeHP+"/"+maxPokeHP, new Label.LabelStyle(font2, null));
        hpAtt.setPosition(790+diff,375); 
        hpAtt.setFontScale(2f);
        stage.addActor(hpAtt);
        infoActors.add(hpAtt);


        //attacco
        Label attacco = new Label(statsPlayer.get(indiciStatistiche.get("attack")), new Label.LabelStyle(font2, null));
        attacco.setPosition(900,285); 
        attacco.setFontScale(2f);
        stage.addActor(attacco);
        infoActors.add(attacco);


        //difesa
        Label difesa = new Label(statsPlayer.get(indiciStatistiche.get("defense")), new Label.LabelStyle(font2, null));
        difesa.setPosition(900,225); 
        difesa.setFontScale(2f);
        stage.addActor(difesa);
        infoActors.add(difesa);


        //attacco speciale
        Label attaccoSp = new Label(statsPlayer.get(indiciStatistiche.get("special_attack")), new Label.LabelStyle(font2, null));
        attaccoSp.setPosition(900,165); 
        attaccoSp.setFontScale(2f);
        stage.addActor(attaccoSp);
        infoActors.add(attaccoSp);


        //difesa speciale
        Label difesaSp = new Label(statsPlayer.get(indiciStatistiche.get("special_defense")), new Label.LabelStyle(font2, null));
        difesaSp.setPosition(900,105); 
        difesaSp.setFontScale(2f);
        stage.addActor(difesaSp);
        infoActors.add(difesaSp);


        //velocità
        Label velocita = new Label(statsPlayer.get(indiciStatistiche.get("speed")), new Label.LabelStyle(font2, null));
        velocita.setPosition(900,45); 
        velocita.setFontScale(2f);
        stage.addActor(velocita);
        infoActors.add(velocita);


        //hpBar e expBar
        placeHpBar(currentPokeHP, maxPokeHP);
        placeExpBar();

        //exp al livello successivo
        Label expMancante;
        if (Integer.parseInt(LVPoke)!=100){
            expMancante = new Label(""+(maxExp-exp), new Label.LabelStyle(font2, null));
        }
        else{
            expMancante = new Label("0", new Label.LabelStyle(font2, null));
        }
        expMancante.setPosition(49*4,102*4); 
        expMancante.setFontScale(2f);
        stage.addActor(expMancante);
        infoActors.add(expMancante);

        //mosse
        for(int i=0; i<listaMosse.size(); i++){
            //tipo mossa
            String nomeTipo=listaMosse.get(i).getTipo();
            Integer indexMove = tipoToIndex.get(nomeTipo);
            Image imageTypeMove = new Image(types[indexMove]);
            imageTypeMove.setPosition(47, 331-86*i);
            imageTypeMove.setSize(32*2f, 15*2f);
            stage.addActor(imageTypeMove);
            infoActors.add(imageTypeMove);

            //nome mossa
            Label nomeMossa = new Label(listaMosse.get(i).getNome(), new Label.LabelStyle(font, null));
            nomeMossa.setPosition(150,343-86*i); 
            nomeMossa.setFontScale(3.4f);
            stage.addActor(nomeMossa);
            infoActors.add(nomeMossa);


            FileHandle mosseFile = Gdx.files.local("assets/pokemon/mosse.json");
            String mosseJsonString = mosseFile.readString();
            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON delle mosse
            JsonValue mosseJson = new JsonReader().parse(mosseJsonString);
            JsonValue tipoJson = mosseJson.get(listaMosse.get(i).getNome());
            String tipologiaMossa = tipoJson.getString("attacco");

            //categoria mossa
            Texture textureCateg = new Texture("squadra/"+tipologiaMossa+".png");
            Image imageCatMove = new Image(textureCateg);
            imageCatMove.setPosition(35, 299-86*i);
            imageCatMove.setSize(32*1.6f, 15*1.6f);
            stage.addActor(imageCatMove);
            infoActors.add(imageCatMove);

            //potenza
            Label potenzaMossa = new Label(tipoJson.getString("potenza"), new Label.LabelStyle(font2, null));
            potenzaMossa.setPosition(185,297-86*i); 
            potenzaMossa.setFontScale(1.3f);
            stage.addActor(potenzaMossa);
            infoActors.add(potenzaMossa);

            //precizione
            Label precisioneMossa = new Label(tipoJson.getString("precisione"), new Label.LabelStyle(font2, null));
            precisioneMossa.setPosition(330,297-86*i); 
            precisioneMossa.setFontScale(1.3f);
            stage.addActor(precisioneMossa);
            infoActors.add(precisioneMossa);

            //pp
            Label ppMossa = new Label(listaMosse.get(i).getattPP()+"/"+listaMosse.get(i).getmaxPP(), new Label.LabelStyle(font2, null));
            ppMossa.setPosition(460,297-86*i); 
            ppMossa.setFontScale(1.3f);
            stage.addActor(ppMossa);
            infoActors.add(ppMossa);
        }
    }


    private void placeHpBar(String currentHP, String maxHP){
        // Calcola la percentuale degli HP attuali rispetto agli HP totali
        float percentualeHP = Float.parseFloat(currentHP)  / Float.parseFloat(maxHP);
        float lunghezzaHPBar = 192 * percentualeHP;
         // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
         Image hpBar = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("battle/white_pixel.png"))));
         hpBar.setSize((int)lunghezzaHPBar, 7);
         hpBar.setPosition(800, 345);
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
        infoActors.add(hpBar);
    }

    private Image placeExpBar(){

        FileHandle file = Gdx.files.local("assets/pokemon/Pokemon.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        JsonValue pokeJson = json.get(nomePoke);

        FileHandle file2;

        if (box) {
            file2 = Gdx.files.local("assets/ashJson/box.json");
        }else {
            file2 = Gdx.files.local("assets/ashJson/squadra.json");
        }
        
        String jsonString2 = file2.readString();
        JsonValue json2 = new JsonReader().parse(jsonString2);

        crescitaType = pokeJson.getInt("crescita");
        int currentExp= json2.get(index).getInt("esperienza");
        maxExp = calcoloEspMaxLivello(crescitaType,Integer.parseInt(LVPoke));
        
        float percentualeExp = (float) currentExp / maxExp;
        float lunghezzaExpBar = 64*4 * percentualeExp;

        System.out.println(currentExp);
        System.out.println(maxExp);
        System.out.println(lunghezzaExpBar);


        Image expBar = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("battle/white_pixel.png"))));
        expBar.setSize((int)lunghezzaExpBar, 12);
        expBar.setPosition( 19*4, 115*4+1 );

        Color coloreExpBar = new Color(72 / 255f, 168 / 255f, 208 / 255f, 1); //colore barra esperienza

        expBar.setColor(coloreExpBar);
        // Aggiungi expBar allo stage
        stage.addActor(expBar);
        infoActors.add(expBar);

        return expBar;
    }

    private int calcoloEspMaxLivello(int crescitaType, int livello){

        Experience esperienza = new Experience();

        int espMaxLvl=0;

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

    }

}
