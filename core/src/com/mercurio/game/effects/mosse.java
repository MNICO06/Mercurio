package com.mercurio.game.effects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class mosse {
    //posizione pokemon selvatico: 1230 595

    private Texture texture;
    private ArrayList<FrameData[]> frameDataList;
    private final String nomeMossaMinuscolo;
    private float speed;

    /*
     * continua: animazione che continua per un certo periodo, per esempio sabbiotomba
     * istantanea: animazione che ha giusto 2/3 sprite, oltre che il movimento del pokemon
     */
    private String tipologia;

    public mosse(String nomeMossa) {
        nomeMossaMinuscolo = nomeMossa.substring(0, 1).toLowerCase() + nomeMossa.substring(1);
        frameDataList = new ArrayList<>(); // Inizializza l'ArrayList
    }

    // Rendi FrameData una classe pubblica
    public static class FrameData {
        private int numeroSprite;  
        private float x;           
        private float y;           
        private float altezza;
        private float larghezza;
        private float opacity;   

        public FrameData(int numeroSprite, float x, float y, float altezza, float larghezza, float opacity) {
            this.numeroSprite = numeroSprite;
            this.x = x;
            this.y = y;
            this.altezza = altezza;
            this.larghezza = larghezza;
            this.opacity = opacity;
        }

        // Getter per numeroSprite
        public int getNumeroSprite() {
            return numeroSprite;
        }

        // Getter per x
        public float getX() {
            return x;
        }

        // Getter per y
        public float getY() {
            return y;
        }

        public float getAltezza() {
            return altezza;
        }

        public float getLarghezza() {
            return larghezza;
        }

        public float getOpacity() {
            return opacity;
        }
    }


    public TextureRegion[] getSpriteMossa() {
        String jsonPath = "jsonMosse/" + nomeMossaMinuscolo + ".json";
        TextureRegion[] spriteMossa;

        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.internal(jsonPath);
            String jsonString = file.readString();

            // Parsea il JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue mossaJson = json.get("data");

            // Estrai i valori dal JSON
            String imagePath = mossaJson.getString("image");
            int numeroColonne = mossaJson.getInt("numeroColonne");
            int numeroRighe = mossaJson.getInt("numeroRighe");
            tipologia = mossaJson.getString("tipologia");
            speed = mossaJson.has("speed") ? mossaJson.getFloat("speed") : 0.1f;
            boolean speculari = mossaJson.has("speculari") ? mossaJson.getBoolean("speculari") : false;


            //prendo lo spriteshet
            texture = new Texture(Gdx.files.internal(imagePath));

            int totaleSprite = numeroColonne * numeroRighe;

            spriteMossa = creaFrames(texture, numeroColonne, numeroRighe, totaleSprite);

            if(speculari) {
                spriteMossa = aggiungiSpeculari(spriteMossa);
            }
        
        } catch(Exception e) {
            Gdx.app.error("Mossa", "File JSON non trovato: " + jsonPath, e);
            // Genera uno sprite di default vuoto
            spriteMossa = new TextureRegion[0];
        }

        return spriteMossa;
    }


    public ArrayList<FrameData[]> getFrameDataList() {
        String jsonPath = "jsonMosse/" + nomeMossaMinuscolo + ".json";

        try {

            // Carica il file JSON
            FileHandle file = Gdx.files.internal(jsonPath);
            String jsonString = file.readString();

            // Parsea il JSON
            JsonValue json = new JsonReader().parse(jsonString);
            JsonValue mossaJson = json.get("data");

            // Estrai i frame dal JSON
            JsonValue framesJson = mossaJson.get("frame");
            

            for (JsonValue frameSetJson : framesJson) {
                ArrayList<FrameData> currentFrameDataList = new ArrayList<>();
                for (JsonValue frameJson : frameSetJson) {
                    int numeroSprite = frameJson.getInt("numeroSprite");
                    float x = frameJson.getFloat("x");
                    float y = frameJson.getFloat("y");
                    float altezza = frameJson.getFloat("altezza");
                    float larghezza = frameJson.getFloat("larghezza");
                    float opacity = frameJson.getFloat("opacity");
                    currentFrameDataList.add(new FrameData(numeroSprite, x, y, altezza, larghezza,opacity)); // Aggiungi i dati del frame alla lista
                }
                frameDataList.add(currentFrameDataList.toArray(new FrameData[0])); // Aggiungi l'array di FrameData a frameDataList
            }

        } catch (Exception e) {
            Gdx.app.error("Mossa", "File JSON non trovato: " + jsonPath, e);
        }

        return frameDataList;

    }
    

    // Funzione per rendere la region sempre unidimensionale
    private TextureRegion[] creaFrames(Texture texture, int numeroColonne, int numeroRighe, int totaleSprite) {

        try {
            // Calcola la larghezza e l'altezza di ogni frame
            int frameWidth = texture.getWidth() / numeroColonne;
            int frameHeight = texture.getHeight() / numeroRighe;

            // Divide la texture in una griglia di (numeroRighe x numeroColonne)
            TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);

            // Crea un array unidimensionale per contenere tutti i frame
            TextureRegion[] frames = new TextureRegion[totaleSprite];

            // Riempie l'array unidimensionale con i frame
            int index = 0;
            for (int r = 0; r < numeroRighe; r++) {
                for (int c = 0; c < numeroColonne && index < totaleSprite; c++) {
                    frames[index++] = tmp[r][c];  // Copia ogni frame nell'array unidimensionale
                }
            }

            return frames;  // Restituisce l'array unidimensionale di tutti i frame
        } catch (Exception e) {
            System.out.println("Errore creaFrames, " + e);
            return null;
        }
        
    }

    private TextureRegion[] aggiungiSpeculari(TextureRegion[] framesOriginali) {

        try {
            TextureRegion[] framesConSpeculari = new TextureRegion[framesOriginali.length * 2];
        
            // Aggiungi gli sprite originali
            for (int i = 0; i < framesOriginali.length; i++) {
                framesConSpeculari[i] = framesOriginali[i];
            }
        
            // Aggiungi gli sprite speculari
            for (int i = 0; i < framesOriginali.length; i++) {
                framesConSpeculari[framesOriginali.length + i] = new TextureRegion(framesOriginali[i]);
                framesConSpeculari[framesOriginali.length + i].flip(true, false); // Specchia orizzontalmente
            }
        
            return framesConSpeculari;
            
        } catch (Exception e) {
            System.out.println("Errore aggiungiSpeculati, " + e);
            return null;
        }
        
    }


    public String getTipologia() {
        return tipologia;
    }
    public float getSpeed() {
        return speed;
    }
}
