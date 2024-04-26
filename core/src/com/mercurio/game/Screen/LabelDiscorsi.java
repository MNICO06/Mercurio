package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import javafx.stage.Stage;

public class LabelDiscorsi {
    // Dichiarazioni delle texture
    private Label label;
    private BitmapFont font;
    private SpriteBatch batch;
    private ArrayList<String> righeDiscorso;
    private int rigaCorrente;
    private Timer timer;
    private TimerTask letterTask;
    private String testoCorrente;
    private int indiceLettera;
    private boolean isPrimaRigaStampata = false;
    private boolean isPrimaRiga;
    private TextureRegion[] textBoxTextures;

    // Variabili per il timer
    private float intervalloLettera = 0.05f; // Intervallo di tempo tra le lettere
    private int indiceDiscorso;
    private boolean continuaDiscorso;
    private String discorso;
    private String pezzoDiscorso;
    private ArrayList<String> discorsoDivisoMamma;
    
    private boolean checkBattle;
    private Texture standardGrigioBordoTexture;
    private Texture standardGrigioTexture;
    private Texture standardRossoTexture;
    private Texture standardBluTexture;
    private Texture standardVerdeTexture;
    private Texture standardArancioneTexture;
    private Texture standardViolaTexture;
    private Texture nuvolaTexture;
    private Texture stranoGialloTexture;
    private Texture nuvolaRosaTexture;
    private Texture gialloFigoTexture;
    private Texture grigioFigoTexture;
    private Texture grigioScuroTexture;
    private Texture cianoTexture;
    private Texture rosaTexture;
    private Texture siepeTexture;
    private Texture pergamenaTexture;
    private Texture muroTexture;
    private Texture tappetoTexture;
    private Texture erbettaTexture;


    public LabelDiscorsi(String disc, int dimMax, int index, boolean battle) {
        this.checkBattle=battle;
        this.discorso=disc;
        this.textBoxTextures = loadTextBoxTextures();
        righeDiscorso = splitTestoInRighe(discorso, dimMax);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        createLabel(index);

        
        rigaCorrente = 0;
        isPrimaRigaStampata = false;
    }

    public Label getLabel() {
        return label;
    }


    private TextureRegion[] loadTextBoxTextures() {
        TextureRegion[] textures = new TextureRegion[20]; 
    
        // Carica l'immagine contenente tutte le label
        Texture textBoxesImage = new Texture("sfondo/boxText.png");
    
        // Calcola le dimensioni di ogni label nella griglia
        int textBoxWidth = textBoxesImage.getWidth() / 2; // Due colonne
        int textBoxHeight = textBoxesImage.getHeight() / 10; // Dieci righe

        // Estrai ogni label dall'immagine e salvala nell'array di texture
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 2; col++) {
                int index = row * 2 + col; // Calcola l'indice dell'array
    
                int startX = col * textBoxWidth;
                int startY = row * textBoxHeight;  
                textures[index] = new TextureRegion(textBoxesImage, startX, startY, textBoxWidth, textBoxHeight);
            }
        }
    
        return textures;
    }
    
    
    
    

    private void createLabel(int index) {
        Skin skin = new Skin();
        skin.add("custom-font", font);

        TextureRegion backgroundTexture = textBoxTextures[index];
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(new NinePatch(backgroundTexture, 10, 10, 10, 10));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");
        style.font.getData().setScale(2.5f);
        style.background = backgroundDrawable;

        label = new Label("", style);
        if (!checkBattle){
            label.setPosition(280, 20); // Posizione della label
            label.setWidth(400);
            label.setHeight(75); // Altezza sufficiente per due righe
            label.setWrap(true);
        }
        else {
            label.setPosition(0, 0); // Posizione della label
            label.setWidth(1024);
            label.setHeight(125); // Altezza sufficiente per due righe
            style.font.getData().setScale(3.5f);
            label.setWrap(true);
        }
    }


    public void renderDisc() {
    	
        batch.begin();
        label.draw(batch, 1);
        batch.end();

        if (!isPrimaRigaStampata && label.getPrefHeight() > 0) {
        	isPrimaRigaStampata = true;

            // Avvia l'animazione della seconda riga se presente
        	 if (isPrimaRigaStampata && rigaCorrente + 1 < righeDiscorso.size()) {
                startLetterAnimationFirstLine(righeDiscorso.get(rigaCorrente) + " " + righeDiscorso.get(rigaCorrente + 1));
            }
        	 
        	 else {
        		 startLetterAnimationFirstLine(righeDiscorso.get(rigaCorrente)); 
        	 }
        }
    }

    private ArrayList<String> splitTestoInRighe(String testo, int lunghezzaMassima) {
        ArrayList<String> righe = new ArrayList<>();
        String[] parole = testo.split(" ");
        StringBuilder rigaCorrente = new StringBuilder(parole[0]);

        for (int i = 1; i < parole.length; i++) {
            if (rigaCorrente.length() + 1 + parole[i].length() <= lunghezzaMassima) {
                rigaCorrente.append(" ").append(parole[i]);
            } else {
                righe.add(rigaCorrente.toString());
                rigaCorrente = new StringBuilder(parole[i]);
            }
        }

        if (rigaCorrente.length() > 0) {
            righe.add(rigaCorrente.toString());
        }

        return righe;
    }

    public void advanceText() {
        if (isPrimaRigaStampata && rigaCorrente < righeDiscorso.size() - 2) {
            rigaCorrente++;
            updateTwoLines();
        } 
    }

    private void updateTwoLines() {
        String testoSecondaRiga = righeDiscorso.get(rigaCorrente + 1);

        // Avvia l'animazione solo per la seconda riga
        startLetterAnimation(testoSecondaRiga);
    }

    private void startLetterAnimation(String testo) {
        testoCorrente = testo;
        indiceLettera = 0;

        if (letterTask != null) {
            letterTask.cancel();
            letterTask = null;
        }

        letterTask = new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    if (indiceLettera <= testoCorrente.length()) {
                    	
                		// Aggiorna solo la seconda riga con l'animazione carattere per carattere
                        String testoCompleto = righeDiscorso.get(rigaCorrente) + "\n" + testoCorrente.substring(0, indiceLettera);
                        label.setText(testoCompleto);
                        indiceLettera++;
                        
                    } else {
                        cancelTextAnimation();
                    }
                });
            }
        };

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(letterTask, 0, (long) (intervalloLettera * 1000)); // Parte subito e si ripete ogni intervalloLettera secondi
    }

    
    private void startLetterAnimationFirstLine(String testo) {
        testoCorrente = testo;
        indiceLettera = 0;

        if (letterTask != null) {
            letterTask.cancel();
            letterTask = null;
        }

        letterTask = new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    if (indiceLettera <= testoCorrente.length()) {
                            String testoCompleto = testoCorrente.substring(0, indiceLettera);
                            label.setText(testoCompleto);
                            indiceLettera++;
                        
                    } else {
                        cancelTextAnimation();
                    }
                });
            }
        };

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(letterTask, 0, (long) (intervalloLettera * 1000)); // Parte subito e si ripete ogni intervalloLettera secondi
    }

    
    public void cancelTextAnimation() {
        if (letterTask != null) {
            letterTask.cancel();
            letterTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
     
    public void reset() {
        // Interrompi qualsiasi animazione in corso
        cancelTextAnimation();

        // Reimposta le variabili di stato
        rigaCorrente = 0;
        isPrimaRigaStampata = false;

        // Ricrea la label con il testo vuoto
        label.setText("");

        // Avvia nuovamente l'animazione della prima e seconda riga
        if (righeDiscorso.size() > 0) {
            startLetterAnimation(righeDiscorso.get(0)); // Avvia l'animazione per la prima riga
        }
        if (righeDiscorso.size() > 1) {
            startLetterAnimation(righeDiscorso.get(1)); // Avvia l'animazione per la seconda riga se presente
        }
    }
}
