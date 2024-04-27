package com.mercurio.game.Screen;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Mossa {
    private String nome;
    private String tipo;

    private Texture texture;
    private Sprite[] spriteArray;
    private HashMap<String, Integer> tipoToIndex;


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

    public Mossa(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}