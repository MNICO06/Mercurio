package com.mercurio.game.pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class Stats {

    public Stats() {

    }

    public JsonValue calcolaStats(int index) {

        try {
            JsonValue stats = new JsonValue(JsonValue.ValueType.object);

            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);

            FileHandle file3 = Gdx.files.local("assets/pokemon/Pokemon.json");
            String jsonString3 = file3.readString();
            JsonValue json3 = new JsonReader().parse(jsonString3);

            // Recupera il Pokémon da modificare
            JsonValue poke = json2.get("poke" + index);

            JsonValue pokeJson = json3.get(poke.getString("nomePokemon"));

            JsonValue ev = poke.get("ev");
            JsonValue iv = poke.get("iv");

            int hp = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("PS")) + iv.getInt("Hp")) * 2)
                    + ((Math.sqrt(ev.getInt("Hp")) / 4))) * poke.getInt("livello")) / 100) + 10
                    + poke.getInt("livello");
            int att = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("Att")) + iv.getInt("Att")) * 2)
                    + ((Math.sqrt(ev.getInt("Att")) / 4))) * poke.getInt("livello")) / 100) + 5;
            int attS = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("AttS")) + iv.getInt("Spec")) * 2)
                    + ((Math.sqrt(ev.getInt("Spec")) / 4))) * poke.getInt("livello")) / 100) + 5;
            int dif = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("Dif")) + iv.getInt("Dif")) * 2)
                    + ((Math.sqrt(ev.getInt("Dif")) / 4))) * poke.getInt("livello")) / 100) + 5;
            int difS = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("DifS")) + iv.getInt("Spec")) * 2)
                    + ((Math.sqrt(ev.getInt("Spec")) / 4))) * poke.getInt("livello")) / 100) + 5;
            int vel = (int) (((((Integer.parseInt(pokeJson.get("stat").getString("Vel")) + iv.getInt("Vel")) * 2)
                    + ((Math.sqrt(ev.getInt("Vel")) / 4))) * poke.getInt("livello")) / 100) + 5;
            int hpAtt = poke.get("statistiche").getInt("hp") + (hp - poke.get("statistiche").getInt("hpTot"));

            stats.addChild("hpTot", new JsonValue(hp));
            stats.addChild("attack", new JsonValue(att));
            stats.addChild("defense", new JsonValue(dif));
            stats.addChild("special_attack", new JsonValue(attS));
            stats.addChild("special_defense", new JsonValue(difS));
            stats.addChild("speed", new JsonValue(vel));
            stats.addChild("hp", new JsonValue(hpAtt));

            return stats;
        } catch (Exception e) {
            System.out.println("Errore calcolaStats, " + e);
            return null;
        }

    }

    public void aggiornaStatistichePokemon(int num) {
        try {
            FileHandle file2 = Gdx.files.local("assets/ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            JsonValue poke = json2.get("poke" + num);

            poke.remove("statistiche");
            JsonValue newStats = calcolaStats(num);
            poke.addChild("statistiche", newStats);

            file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

        } catch (Exception e) {
            System.out.println("Errore AggiornaStatistichePokemon stats, " + e);
        }

    }

    public JsonValue calcolaStatsBot(String nomePoke, int livello, JsonValue iv) {

        try {
            JsonValue stats = new JsonValue(JsonValue.ValueType.object);

            FileHandle file3 = Gdx.files.local("assets/pokemon/Pokemon.json");
            String jsonString3 = file3.readString();
            JsonValue json3 = new JsonReader().parse(jsonString3);

            JsonValue pokeJson = json3.get(nomePoke);

            int hp = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("PS")) + iv.getInt("Hp")) * 2) * livello)
                    / 100) + 10 + livello;
            int att = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("Att")) + iv.getInt("Att")) * 2)
                    * livello) / 100) + 5;
            int attS = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("AttS")) + iv.getInt("Spec")) * 2)
                    * livello) / 100) + 5;
            int dif = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("Dif")) + iv.getInt("Dif")) * 2)
                    * livello) / 100) + 5;
            int difS = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("DifS")) + iv.getInt("Spec")) * 2)
                    * livello) / 100) + 5;
            int vel = (int) ((((Integer.parseInt(pokeJson.get("stat").getString("Vel")) + iv.getInt("Vel")) * 2)
                    * livello) / 100) + 5;

            stats.addChild("hpTot", new JsonValue(hp));
            stats.addChild("hp", new JsonValue(hp));
            stats.addChild("attack", new JsonValue(att));
            stats.addChild("defense", new JsonValue(dif));
            stats.addChild("special_attack", new JsonValue(attS));
            stats.addChild("special_defense", new JsonValue(difS));
            stats.addChild("speed", new JsonValue(vel));

            return stats;
        } catch (Exception e) {
            System.out.println("Errore calcolaStatsBot, " + e);
            return null;
        }

    }

}
