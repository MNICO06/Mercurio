package com.mercurio.game.effects;

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


//import javafx.stage.Stage;

public class LabelDiscorsi {
    // Dichiarazioni delle texture
    private Label label;
    private Label labelSi;
    private Label labelNo;
    // Dimensioni e posizioni per le label
    private float siX, siY, noX, noY;
    private float siWidth = 60, siHeight = 36;
    private float noWidth = 60, noHeight = 36;
    private int sceltaUtente = -1;
    private boolean deveScegliere;
    private boolean mostraDecisionLabels = false;

    private BitmapFont font;
    private SpriteBatch batch;
    private ArrayList<String> righeDiscorso;
    private int rigaCorrente;
    private Timer timer;
    private Timer timer2;
    private TimerTask letterTask;
    private TimerTask task;
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



    public LabelDiscorsi(String disc, int dimMax, int index, boolean battle, boolean deveScegliere) {
        this.checkBattle = battle;
        this.discorso = disc;
        this.textBoxTextures = loadTextBoxTextures();
        this.deveScegliere = deveScegliere;
        righeDiscorso = splitTestoInRighe(discorso, dimMax);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
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
        style.background = backgroundDrawable;

        label = new Label("", style);

        if (deveScegliere) {
            // Crea le label per "Sì" e "No"
            labelSi = new Label("Si'", style);
            labelNo = new Label("No", style);
        }
        

        if (!checkBattle){
            label.setPosition(280, 20); // Posizione della label
            label.setWidth(400);
            label.setHeight(75); // Altezza sufficiente per due righe
            label.setWrap(true);

            if (deveScegliere) {
                labelSi.setWidth(siWidth);
                labelNo.setWidth(noWidth);
                labelSi.setHeight(siHeight);
                labelNo.setHeight(noHeight);
                // Imposta le posizioni delle label
                siX = label.getX() + label.getWidth() + 2;
                siY = label.getY() + labelSi.getHeight() + 3;
                noX = label.getX() + label.getWidth() + 2;
                noY = label.getY();
    
                labelSi.setPosition(siX, siY);
                labelNo.setPosition(noX, noY);
            }
        }
        else {
            label.setPosition(0, 0); // Posizione della label
            label.setWidth(1024);
            label.setHeight(125); // Altezza sufficiente per due righe
            style.font.getData().setScale(1.5f);
            label.setWrap(true);
        }
    }


    public int renderDisc() {
    	
        batch.begin();
        label.draw(batch, 1);

        if (deveScegliere) {
            // Disegna le label "Sì" e "No" solo se il flag è true
            if (mostraDecisionLabels) {
                labelSi.draw(batch, 1);
                labelNo.draw(batch, 1);
            }
        }
        
        if (deveScegliere) {
            // Gestisci il click
            if (mostraDecisionLabels){
                if (Gdx.input.justTouched()) {
                    handleClick(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                }
            }
        }
        
        if (!isPrimaRigaStampata && label.getPrefHeight() > 0) {
        	isPrimaRigaStampata = true;

            // Avvia l'animazione della seconda riga se presente
        	 if (isPrimaRigaStampata && rigaCorrente + 1 < righeDiscorso.size()) {
                startLetterAnimationFirstLine(righeDiscorso.get(rigaCorrente) + "\n" + righeDiscorso.get(rigaCorrente + 1));
            }
        	 
        	 else {
        		 startLetterAnimationFirstLine(righeDiscorso.get(rigaCorrente)); 
        	 }
        }

        if (deveScegliere) {

            if (timer2 != null) {
                timer2.cancel();
                timer2 = null;
            }
            timer2 = new Timer();

            if (task != null) {
                task.cancel();
                task = null;
            }

            task = new TimerTask() {
                @Override
                public void run() {
                    modificaMostraLabel();
                }
            };
            
            if (righeDiscorso.size() -2 == 0) {
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        modificaMostraLabel();
                    }
                }, 3f);
            }else if (righeDiscorso.size() - 2 > 0) {
                if (rigaCorrente == righeDiscorso.size() - 2) {
                    com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                        @Override
                        public void run() {
                            modificaMostraLabel();
                        }
                    }, 1f);
                }
            }else if (righeDiscorso.size() - 2 < 0) {
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        modificaMostraLabel();
                    }
                }, 0.7f);
            }
        }

        batch.end();

        return sceltaUtente;

    }

    private void handleClick(float mouseX, float mouseY) {
        // Controlla se il click è sopra "Sì"
        if (mouseX >= siX && mouseX <= siX + siWidth && mouseY >= siY && mouseY <= siY + siHeight) {
            sceltaUtente = 1;
            // Logica per "Sì"
            mostraDecisionLabels = false;
        }
        // Controlla se il click è sopra "No"
        else if (mouseX >= noX && mouseX <= noX + noWidth && mouseY >= noY && mouseY <= noY + noHeight) {
            sceltaUtente = 0;
            // Logica per "No"
            mostraDecisionLabels = false;
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

    public boolean advanceText() {
        
        if (isPrimaRigaStampata && rigaCorrente < righeDiscorso.size() - 2) {
            rigaCorrente++;
            updateTwoLines();
            return true;
        }
        else {
            return false;
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

        // Non far renderizzare più il si e no
        mostraDecisionLabels = false;

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

    public void setSceltaUtente(int sceltaUtente) {
        this.sceltaUtente = sceltaUtente;
    }

    private void modificaMostraLabel(){
        mostraDecisionLabels=true;
    }
}