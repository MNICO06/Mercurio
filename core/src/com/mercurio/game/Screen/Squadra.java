package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;

public class Squadra {


    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Array<Actor> squadActors;
    private String currentPokeHPforSquad;
    private String maxPokeHPforSquad;
    private String nomePokeSquad;
    private String LVPoke;
    private boolean battaglia;
    boolean isCursorInside = false;

    Array<Texture> animationTextures = new Array<>();
    Array<Image> animationImages = new Array<>();
    Array<Boolean> animazionePartita = new Array<>();
    Array<Boolean> controllo = new Array<>();

    public Squadra(Stage stage, boolean battaglia){
        this.battaglia=battaglia;
        this.batch = (SpriteBatch) stage.getBatch();
        this.font = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
        this.stage = stage;
        this.squadActors = new Array<>(); // Inizializza l'array degli attori della borsa
        Gdx.input.setInputProcessor(stage);


        showSquad();
    }

    private void showSquad() {
        // Carica le texture
        Texture normalTexture = new Texture("squadra/nsSquadra.png");
        Texture firstTexture = new Texture("squadra/nsFirstSquadra.png");
        Texture selectedFirstTexture = new Texture("squadra/selFirst.png");
        Texture selectedTexture = new Texture("squadra/selSquadra.png");
    
        // Posizione iniziale per la prima label
        float initialX = 150;
        float initialY = 470;
    
        // Spaziatura tra le colonne e le righe
        float columnSpacing = 300;
        float rowSpacing = 150;
    
        // Larghezza e altezza di un'immagine
        float imageWidth = normalTexture.getWidth();
        float imageHeight = normalTexture.getHeight();
    
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        // Add background 
        Texture backgroundTexture = new Texture("sfondo/sfondo.png");
        Image background = new Image(backgroundTexture);
        background.setSize(screenWidth, screenHeight);
        stage.addActor(background);
        squadActors.add(background);
    
        // Creazione delle label della squadra
        for (int i = 0; i < 6; i++) {
            leggiPokeSecondario(i+1);
            if (!nomePokeSquad.isEmpty()) {

                // Calcola l'indice di colonna e di riga
                int column = i % 2; // Due colonne
                int row = i / 2;    // Tre righe
    
                // Calcola la posizione dell'immagine
                float posX = initialX + column * (imageWidth + columnSpacing);
                float posY = initialY - row * (imageHeight + rowSpacing);
    
                if (column == 0) {
                    posY += 50;
                }

                // Se è la prima posizione, usa un'immagine differente
                String texturePath = "squadra/nsSquadra.png";
                Texture selectedTex = selectedTexture;
                if (i == 0) {
                    texturePath = "squadra/nsFirstSquadra.png";
                    selectedTex = selectedFirstTexture;
                }
    
                Image image = new Image(new Texture(texturePath));
                image.setPosition(posX, posY);
                image.setSize(126*3, 45*3);

                Image hpBar= placeHpBar(image,63*3,18*3,currentPokeHPforSquad,maxPokeHPforSquad);

                Label labelNomePokemon = new Label(nomePokeSquad, new Label.LabelStyle(font, null));
                labelNomePokemon.setPosition(image.getX()+90,image.getY()+93); 
                labelNomePokemon.setFontScale(3.5f);

                Label labelLV = new Label(LVPoke, new Label.LabelStyle(font, null));
                labelLV.setPosition(image.getX()+65,image.getY()+23); 
                labelLV.setFontScale(2.5f);

                int diff;
                if (Integer.parseInt(currentPokeHPforSquad) > 99) {
                    diff = 0;
                } else if (Integer.parseInt(currentPokeHPforSquad) > 9) {
                    diff = 10;
                } else {
                    diff = 20;
                }
                Label labelHP= new Label(currentPokeHPforSquad, new Label.LabelStyle(font, null));
                labelHP.setPosition(image.getX()+210+diff,image.getY()+25);
                labelHP.setFontScale(2.5f);

                Label labelHPTot= new Label(maxPokeHPforSquad, new Label.LabelStyle(font, null));
                labelHPTot.setPosition(image.getX()+278,image.getY()+25);
                labelHPTot.setFontScale(2.5f);


                Texture animationTexture = new Texture("pokemon/" + nomePokeSquad + "Label.png");
                animationTextures.add(animationTexture);
                TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
                // Crea un'immagine utilizzando solo la prima metà dell'immagine
                Image animationImage = new Image(animationRegion);
                animationImage.setSize(animationTexture.getWidth(), animationTexture.getHeight()*2);
                animationImage.setPosition(posX + 13, posY + 45 * 3 - 55);
                animationImages.add(animationImage);

                animazionePartita.add(false);
                controllo.add(false);
                
    
                // Aggiungi l'azione per il cambio di texture al passaggio del cursore
                TextureRegionDrawable normalDrawable = new TextureRegionDrawable(normalTexture);
                TextureRegionDrawable fisrtDrawable = new TextureRegionDrawable(firstTexture);
                TextureRegionDrawable selectedDrawable = new TextureRegionDrawable(selectedTex);
                final int index=i;
                InputListener imageListener = new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        image.setDrawable(selectedDrawable);
                        image.setSize(126*3, 49*3);
                        isCursorInside = true;
                        controllo.set(index,true);
                        hpBar.setPosition(image.getX()+63*3-1, image.getY()+19*3);
                        labelLV.setY(labelLV.getY()+3);
                        labelHP.setY(labelHP.getY()+3);
                        labelHPTot.setY(labelHPTot.getY()+3);
                    }
    
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        // Se è il primo elemento, reimposta la texture normale
                        if (index == 0) {
                            image.setDrawable(fisrtDrawable);
                        } else {
                            image.setDrawable(normalDrawable);
                        }
                        image.setSize(126*3, 45*3);
                        isCursorInside = false;
                        controllo.set(index,false);
                        //ferma animazione
                        TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
                        animationImage.setDrawable(new TextureRegionDrawable(newRegion));
                        hpBar.setPosition(image.getX()+63*3, image.getY()+18*3);
                        labelLV.setY(labelLV.getY()-3);
                        labelHP.setY(labelHP.getY()-3);
                        labelHPTot.setY(labelHPTot.getY()-3);

                    }
                };
                
                image.addListener(imageListener);
                stage.addActor(image);
                squadActors.add(image);
                
                hpBar.addListener(imageListener);
                hpBar.toFront();

                labelNomePokemon.addListener(imageListener);
                stage.addActor(labelNomePokemon);
                squadActors.add(labelNomePokemon);

                labelLV.addListener(imageListener);
                stage.addActor(labelLV);
                squadActors.add(labelLV);

                labelHP.addListener(imageListener);
                stage.addActor(labelHP);
                squadActors.add(labelHP);

                labelHPTot.addListener(imageListener);
                stage.addActor(labelHPTot);
                squadActors.add(labelHPTot);

                animationImage.addListener(imageListener);
                stage.addActor(animationImage);
                squadActors.add(animationImage);
                
            }
        }
    
        // Label "Cancel"
        Texture cancelTexture = new Texture("squadra/cancel.png");
        Image cancelImage = new Image(cancelTexture);
        cancelImage.setPosition(70, 10);
        cancelImage.setSize(56*3, 24*3);
        cancelImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearInventoryItems();
            }
        });
        stage.addActor(cancelImage);
        squadActors.add(cancelImage);
    }
    
    


    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        for (int i = 0; i < animazionePartita.size; i++) {
            if (controllo.get(i)) {
                startAnimation(i);
            } else {
                stopAnimation(i);
            }
        }

        // Disegna la UI della squadra
        stage.draw(); // Disegna lo stage sullo SpriteBatch
    }


    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    private void clearInventoryItems() {
        // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione precedente
        for (Actor actor : squadActors) {
            actor.remove(); // Rimuovi l'attore dalla stage
        }
        squadActors.clear(); // Pulisci l'array degli attori dell'inventario
    }


    public void leggiPokeSecondario(int numero) {
        // Carica il file JSON
        FileHandle file = Gdx.files.internal("ashJson/squadra.json");
        String jsonString = file.readString();
        // Utilizza la classe JsonReader di LibGDX per leggere il file JSON
        JsonValue json = new JsonReader().parse(jsonString);
        JsonValue pokeJson = json.get("poke"+numero);
        currentPokeHPforSquad = pokeJson.get("Statistiche").getString("hp");
        maxPokeHPforSquad = pokeJson.get("Statistiche").getString("hpTot");
        nomePokeSquad = pokeJson.getString("nomePokemon");
        LVPoke = pokeJson.getString("livello");

    }


    public void startAnimation(int index) {
        if (!animazionePartita.get(index)) {
            animazionePartita.set(index, true);
            Texture animationTexture = animationTextures.get(index); // Ottieni la texture corrispondente all'indice
            Image animationImage = animationImages.get(index); // Ottieni l'immagine corrispondente all'indice
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    TextureRegion newRegion = new TextureRegion(animationTexture, 33, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
                    animationImage.setDrawable(new TextureRegionDrawable(newRegion));
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
                            animationImage.setDrawable(new TextureRegionDrawable(newRegion));
                            animazionePartita.set(index, false);
                        }
                    }, 0.7f);
                }
            }, 0.7f);
        }
    }
    
    public void stopAnimation(int index) {
        controllo.set(index, false);
        Texture animationTexture = animationTextures.get(index); // Ottieni la texture corrispondente all'indice
        Image animationImage = animationImages.get(index); // Ottieni l'immagine corrispondente all'indice
        TextureRegion newRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2, animationTexture.getHeight());
        animationImage.setDrawable(new TextureRegionDrawable(newRegion));
    }


    private Image placeHpBar(Image image, int diffX, int diffY, String currentHP, String maxHP){
        // Calcola la percentuale degli HP attuali rispetto agli HP totali
        float percentualeHP = Float.parseFloat(currentHP)  / Float.parseFloat(maxHP);
        float lunghezzaHPBar = 48*3 * percentualeHP;
         // Crea e posiziona la hpBar sopra imageHPPlayer con l'offset specificato
         Image hpBar = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("battle/white_pixel.png"))));
         hpBar.setSize((int)lunghezzaHPBar, 12);
         hpBar.setPosition(image.getX() + diffX, image.getY() + diffY);
         //hpBar.setPosition(400, 400);
        // Determina il colore della hpBar in base alla percentuale calcolata
        Color coloreHPBar;
        if (percentualeHP >= 0.5f) {
            coloreHPBar = Color.GREEN; // Verde se sopra il 50%
        } else if (percentualeHP > 0.15f && percentualeHP < 0.5f) {
            coloreHPBar = Color.YELLOW; // Giallo se tra il 15% e il 50%
        } else {
            coloreHPBar = Color.RED; // Rosso se sotto il 15%
        }

        hpBar.setColor(coloreHPBar);
        // Aggiungi hpBar allo stage
        stage.addActor(hpBar);
        squadActors.add(hpBar);

        return hpBar;
    }

}