package com.mercurio.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class UtilityFunctions {
    public UtilityFunctions() {

    }

    public boolean controllaPresenzaStarter() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue poke1 = json.get("poke1");

            if (poke1 != null) {
                String nomePokemon = poke1.getString("nomePokemon", "");

                if (nomePokemon.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public void cura() {
        // Carica il file JSON
        FileHandle file = Gdx.files.local("ashJson/squadra.json");
        String jsonString = file.readString();

        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);

        for (int i=0; i<6; i++){
            int index =i+1;
            JsonValue pokeJson = json.get("poke"+index);
            //System.out.println(index);
            String nomePoke = pokeJson.getString("nomePokemon");
            //System.out.println(index);

            if (!nomePoke.equals("")){
                JsonValue statistiche = pokeJson.get("statistiche");
                String maxPokeHP = statistiche.getString("hpTot");
                //ripristina gli hp al massimo
                statistiche.remove("hp");
                statistiche.addChild("hp", new JsonValue(maxPokeHP));
                JsonValue mosse = pokeJson.get("mosse");
                for (JsonValue mossaJson : mosse) {
                    String maxPP = mossaJson.getString("ppTot");
                    // ripristina attPP al massimo per ogni mossa
                    mossaJson.remove("ppAtt");
                    mossaJson.addChild("ppAtt", new JsonValue(maxPP));
                }
            }

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);
        }
    }


}
