package com.mercurio.game.personaggi;

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

public class MammaAsh {
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;

    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;

    private TextureRegion currentAnimation;

    private float stateTime;
    private Vector2 characterPosition;

    private Texture texture;

    private int player_width;
    private int player_height;

    private float camminataFrame_speed = 0.14f;

    private Rectangle boxPlayer;
    private Rectangle boxInteractionVerticale;
    private Rectangle boxInteractionOrizzontaleDestro;
    private Rectangle boxInteractionOrizzontaleSinistro;

    private GameAsset asset;

    public MammaAsh(MercurioMain game) {
        try {
            this.asset = game.getGameAsset();

            texture = asset.getBot(AssetsBot.MOM);
            TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
            indietro = new TextureRegion[4];
            sinistra = new TextureRegion[4];
            destra = new TextureRegion[4];
            avanti = new TextureRegion[4];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i == 0) {
                        indietro[j] = tmp[i][j];
                    } else if (i == 1) {
                        sinistra[j] = tmp[i][j];
                    } else if (i == 2) {
                        destra[j] = tmp[i][j];
                    } else if (i == 3) {
                        avanti[j] = tmp[i][j];
                    }
                }
            }

            fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[0]);
            fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
            fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
            fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);

            characterPosition = new Vector2(188, 110);

            player_width = 24; // Larghezza del personaggio
            player_height = 22; // Altezza del personaggio

            // per collisione
            boxPlayer = new Rectangle(characterPosition.x + player_width / 4 - 2, characterPosition.y - 2,
                    player_width / 2 + 2, player_height / 6 + 6);
            // per interaction
            boxInteractionVerticale = new Rectangle(characterPosition.x + player_width / 4 - 2,
                    characterPosition.y - 20, player_width / 2 + 2, player_height - 8);
            boxInteractionOrizzontaleDestro = new Rectangle(characterPosition.x + player_width / 4 + 12,
                    characterPosition.y - 2, player_width / 2 + 2, player_height - 8);
            boxInteractionOrizzontaleSinistro = new Rectangle(characterPosition.x + player_width / 4 - 15,
                    characterPosition.y - 2, player_width / 2 + 2, player_height - 8);

            stateTime = 0f;
            currentAnimation = fermoAvanti.getKeyFrame(stateTime);

        } catch (Exception e) {
            System.out.println("Errore costructor mammaAsh, " + e);
        }
    }

    public void setSinstra() {
        currentAnimation = fermoSinistra.getKeyFrame(stateTime);
    }

    public void setDestra() {
        currentAnimation = fermoDestra.getKeyFrame(stateTime);
    }

    public void setIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(stateTime);
    }

    public void setAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(stateTime);
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

    // metodi che mi servono per far spostare la madre
    public Rectangle getInterBoxVert() {
        return boxInteractionVerticale;
    }

    public Rectangle getInterBoxOrizSx() {
        return boxInteractionOrizzontaleSinistro;
    }

    public Rectangle getInterBoxOrizDx() {
        return boxInteractionOrizzontaleDestro;
    }

    public void dispose() {
        texture.dispose();
    }

    public void cura() {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("ashJson/squadra.json");
            String jsonString = file.readString();

            // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
            JsonValue json = new JsonReader().parse(jsonString);

            for (int i = 0; i < 6; i++) {
                int index = i + 1;
                JsonValue pokeJson = json.get("poke" + index);
                // System.out.println(index);
                String nomePoke = pokeJson.getString("nomePokemon");
                // System.out.println(index);

                if (!nomePoke.equals("")) {
                    JsonValue statistiche = pokeJson.get("statistiche");
                    String maxPokeHP = statistiche.getString("hpTot");
                    // ripristina gli hp al massimo
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

        } catch (Exception e) {
            System.out.println("Errore cura mammaASh, " + e);
        }

    }

}
