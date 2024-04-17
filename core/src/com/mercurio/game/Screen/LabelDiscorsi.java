package com.mercurio.game.Screen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LabelDiscorsi {
    // Dichiarazioni delle texture
    private Label labelMamma;
    private BitmapFont font;
    private SpriteBatch batch;
    private ArrayList<String> righeDiscorso;
    private int rigaCorrente;
    private Timer timer;
    private TimerTask letterTask;
    private String testoCorrente;
    private int indiceLettera;
    private boolean isPrimaRigaStampata;
    private boolean isPrimaRiga;
    
    // Variabili per il timer
    private float intervalloLettera = 0.05f; // Intervallo di tempo tra le lettere
    private int indiceDiscorso;
    private boolean continuaDiscorso;
    private String discorsoMamma = "Ciao figliuolo come stai, come mai stai uscendo dove stai andando?";
    private String pezzoDiscorso;
    private ArrayList<String> discorsoDivisoMamma;
    
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


    public LabelDiscorsi() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        createLabel();

        String discorsoMamma = "Ciao figliuolo come stai, come mai stai uscendo e dove stai andando?";
        righeDiscorso = splitTestoInRighe(discorsoMamma, 30);
        rigaCorrente = 0;
        isPrimaRigaStampata = false;
    }

    private void createLabel() {
        Skin skin = new Skin();
        skin.add("custom-font", font);

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(new NinePatch(new Texture("sfondo/boxTextArancio.png"), 10, 10, 10, 10));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("custom-font");
        style.font.getData().setScale(2.5f);
        style.background = backgroundDrawable;

        labelMamma = new Label("", style);
        labelMamma.setPosition(280, 20); // Posizione della label
        labelMamma.setWidth(400);
        labelMamma.setHeight(75); // Altezza sufficiente per due righe
        labelMamma.setWrap(true);
    }

    public void renderDiscMamma() {
    	
        batch.begin();
        labelMamma.draw(batch, 1);
        batch.end();

        if (!isPrimaRigaStampata && labelMamma.getPrefHeight() > 0) {
            // Stampare subito la prima riga con animazione
        	startLetterAnimation(righeDiscorso.get(rigaCorrente));
            isPrimaRigaStampata = true;

            // Avvia l'animazione della seconda riga se presente
            if (rigaCorrente + 1 < righeDiscorso.size()) {
                startLetterAnimation(righeDiscorso.get(rigaCorrente + 1));
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
        String testoPrimaRiga = righeDiscorso.get(rigaCorrente);
        String testoSecondaRiga = righeDiscorso.get(rigaCorrente + 1);
        labelMamma.setText(testoPrimaRiga + "\n" + testoSecondaRiga);

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
                        labelMamma.setText(testoCompleto);
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
        labelMamma.setText("");

        // Avvia nuovamente l'animazione della prima e seconda riga
        if (righeDiscorso.size() > 0) {
            startLetterAnimation(righeDiscorso.get(0)); // Avvia l'animazione per la prima riga
        }
        if (righeDiscorso.size() > 1) {
            startLetterAnimation(righeDiscorso.get(1)); // Avvia l'animazione per la seconda riga se presente
        }
    }
}
