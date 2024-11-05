package com.mercurio.game.pokemon;

import java.util.ArrayList;

import org.json.JSONArray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class ApprendimentoMosse extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Battle chiamanteB;
    int indexPoke;
    private Array<Actor> itemActors = new Array<>(); // Array per tracciare gli attori degli oggetti dell'inventario


    public ApprendimentoMosse(Battle chiamanteB, Stage stage, int indexPoke){
        chiamanteB.destroyTimers();
        this.indexPoke = indexPoke;
        this.chiamanteB = chiamanteB;
        this.batch = (SpriteBatch) stage.getBatch();
        this.stage = stage;
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);

        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString2 = file2.readString();
        JsonValue json2 = new JsonReader().parse(jsonString2);
        JsonValue poke = json2.get("poke" + indexPoke);
        JsonValue mosse = poke.get("mosse");

        if (mosse.size == 4) {
            newMoveOver4();
        }
        else{
            newMoveUnder4();
        }

    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime
        // Disegna la UI
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    @Override
    public void show() {

        Texture textureBack = new Texture("sfondo/newMoveBG.png");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Add background 
        Image backgroundNM = new Image(textureBack);
        // Ritaglia l'immagine per adattarla alla dimensione dello schermo
        backgroundNM.setSize(screenWidth, screenHeight);
        stage.addActor(backgroundNM);
        itemActors.add(backgroundNM);

        clearItems();
        chiamanteB.cancelAP();
    }

    private void newMoveOver4(){

        Gdx.input.setInputProcessor(stage);

        FileHandle filePoke = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonStringPoke = filePoke.readString();
        JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString2 = file2.readString();
        JsonValue json2 = new JsonReader().parse(jsonString2);
        JsonValue poke = json2.get("poke" + (indexPoke));
        String pokeName = poke.getString("nomePokemon");

        // Ottieni la lista delle mosse imparabili
        JsonValue mosseImparabili = jsonPoke.get(pokeName).get("mosseImparabili"); 
        String mossa = mosseImparabili.getString("M" + poke.getInt("livello")/2);

        ArrayList<String> mosseList = new ArrayList<>();
        JsonValue mosseArray = poke.get("mosse");
        for (JsonValue mossaTest : mosseArray) {
            mosseList.add(mossaTest.getString("nome"));
        }

        if (!mossa.isEmpty() && !mosseList.contains(mossa)) {
            show();
        }
        else{
            return;
        }
    }

    public void newMoveUnder4(){

        FileHandle filePoke = Gdx.files.internal("pokemon/Pokemon.json");
        String jsonStringPoke = filePoke.readString();
        JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

        FileHandle filePoke2 = Gdx.files.internal("pokemon/mosse.json");
        String jsonStringPoke2 = filePoke2.readString();
        JsonValue jsonPoke2 = new JsonReader().parse(jsonStringPoke2);

        // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
        FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
        String jsonString2 = file2.readString();
        JsonValue json2 = new JsonReader().parse(jsonString2);
        JsonValue poke = json2.get("poke" + (indexPoke));
        String pokeName = poke.getString("nomePokemon");

        // Ottieni la lista delle mosse imparabili
        JsonValue mosseImparabili = jsonPoke.get(pokeName).get("mosseImparabili"); 
        String mossa = mosseImparabili.getString("M" + poke.getInt("livello")/2);

        ArrayList<String> mosseList = new ArrayList<>();
        JsonValue mosseArray = poke.get("mosse");
        for (JsonValue mossaTest : mosseArray) {
            mosseList.add(mossaTest.getString("nome"));
        }

        if (!mossa.isEmpty() && !mosseList.contains(mossa)) {            
            JsonValue mossaJson = jsonPoke2.get(mossa);
            JsonValue newMossa = new JsonValue(JsonValue.ValueType.object);
            newMossa.addChild("nome",new JsonValue(mossa));
            newMossa.addChild("tipo",mossaJson.get("tipo"));
            newMossa.addChild("ppTot",new JsonValue(mossaJson.getInt("pp")));
            newMossa.addChild("ppAtt",new JsonValue(mossaJson.getString("pp")));

            json2.get("poke" + (indexPoke)).get("mosse").addChild(newMossa);
            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);
        }
        else{
            return;
        }
    }

    public void clearItems() {
        // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione precedente
        for (Actor actor : itemActors) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
        itemActors.clear(); // Pulisci l'array degli
    }
    
}
