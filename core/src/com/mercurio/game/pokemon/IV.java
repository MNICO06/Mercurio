package com.mercurio.game.pokemon;
import java.util.Random;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class IV {

    public IV(){

    }
    
    public JsonValue creaIV() {
        Random random = new Random();

        int ivAtt = random.nextInt(16); // da 0 a 15 inclusi
        int ivDif = random.nextInt(16);
        int ivSpec = random.nextInt(16);
        int ivVel = random.nextInt(16);

        // Calcola ivHp combinando l'ultima cifra binaria degli altri IV
        int ivHp = ((ivAtt & 1) << 3) | ((ivDif & 1) << 2) | ((ivSpec & 1) << 1) | (ivVel & 1);

        // Crea il nodo JSON per le statistiche
        JsonValue stats = new JsonValue(JsonValue.ValueType.object);
        stats.addChild("Hp", new JsonValue(ivHp));
        stats.addChild("Att", new JsonValue(ivAtt));
        stats.addChild("Dif", new JsonValue(ivDif));
        stats.addChild("Spec", new JsonValue(ivSpec));
        stats.addChild("Vel", new JsonValue(ivVel));

        return stats;
    }

}