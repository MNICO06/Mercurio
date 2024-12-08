package com.mercurio.game.personaggi;

import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mercurio.game.AssetManager.GameAsset;
import com.mercurio.game.AssetManager.GameAsset.AssetsBot;
import com.mercurio.game.Screen.MercurioMain;

public class Dottoressa {
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;


    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;

    private Animation<TextureRegion> characterAnimation;
    private TextureRegion currentAnimation;

    private float stateTime;
    private Vector2 characterPosition;

    private Texture texture;

    private int player_width;
    private int player_height;

    private float speed_Camminata_orizontale = 50;
    private float speed_Camminata_verticale = 40;

    private float camminataFrame_speed = 0.14f;

    private Rectangle boxPlayer;

    private GameAsset asset;

    public Dottoressa(MercurioMain game) {
        this.asset = game.getGameAsset();

        texture = asset.getBot(AssetsBot.DOC);
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 4);
        indietro = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        destra = new TextureRegion[3];
        avanti = new TextureRegion[3];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j< 3; j++) {
                if (i == 0) {
                    avanti[j] = tmp [i][j];
                }
                else if (i == 1) {
                    indietro[j] = tmp [i][j];
                }
                else if (i == 2) {
                    sinistra[j] = tmp [i][j];
                }
                else if (i == 3) {
                    destra[j] = tmp [i][j];
                }
            }
        }

        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

        characterPosition = new Vector2(188, 110);

        player_width = 19; // Larghezza del personaggio
        player_height = 20; // Altezza del personaggio

        currentAnimation = fermoIndietro.getKeyFrame(0);
        stateTime = 0f;
    }

    public void setSinstra() {
        currentAnimation = fermoSinistra.getKeyFrame(0);
    }
    public void setDestra() {
        currentAnimation = fermoDestra.getKeyFrame(0);
    }
    public void setIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(0);
    }
    public void setAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(0);
    }

    public TextureRegion getTexture() {
        return currentAnimation;
    }

    public Vector2 getPosition() {
        return characterPosition;
    }

    public float getWidth() {
        return player_width;
    }

    public float getHeight() {
        return player_height;
    }

    public Rectangle getBoxPlayer() {
        return boxPlayer;
    }

    public void dispose() {
        texture.dispose();
    }

    public void setPosition(float x, float y) {
        characterPosition.set(x, y);
    }
    

    public void cura(){
        // Carica il file JSON
        FileHandle file = Gdx.files.local("assets/ashJson/squadra.json");
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