package com.mercurio.game.pokemon;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;

public class ApprendimentoMosse extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private Battle chiamanteB;
    int indexPoke;
    private Array<Actor> itemActors = new Array<>(); // Array per tracciare gli attori degli oggetti dell'inventario
    private ArrayList<Mossa> listaMosse = new ArrayList<>();
    private Actor currentlySelected = null;
    private infoPoke infoPoke;
    Array<Texture> animationTextures = new Array<>();
    private String pokeName;
    private Mossa mossaL;

    public ApprendimentoMosse(Battle chiamanteB, Stage stage, int indexPoke) {
        try {
            this.indexPoke = indexPoke;
            this.chiamanteB = chiamanteB;
            this.batch = (SpriteBatch) stage.getBatch();
            this.stage = stage;
            font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
            font2 = new BitmapFont(Gdx.files.internal("font/small_letters_font.fnt"));
            Gdx.input.setInputProcessor(stage);

            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            FileHandle file2 = Gdx.files.local("ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            JsonValue poke = json2.get("poke" + indexPoke);
            JsonValue mosse = poke.get("mosse");

            if (mosse.size == 4) {
                newMoveOver4();
            } else {
                newMoveUnder4();
            }

        } catch (Exception e) {
            System.out.println("Errore constructor Apprendimento Mosse, " + e);
        }
    }

    public void render() {
        try {
            if (infoPoke != null) {
                infoPoke.render();
            }

            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime); // Aggiorna lo stage con il deltaTime
            // Disegna la UI
            stage.draw(); // Disegna lo stage sullo SpriteBatch

        } catch (Exception e) {
            System.out.println("Errore render apprendimento mosse, " + e);
        }

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        try {
            Texture textureBack = new Texture("sfondo/newMoveBG.png");

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Add background
            Image backgroundNM = new Image(textureBack);
            // Ritaglia l'immagine per adattarla alla dimensione dello schermo
            backgroundNM.setSize(screenWidth, screenHeight);
            stage.addActor(backgroundNM);
            itemActors.add(backgroundNM);

            Texture animationTexture = new Texture("pokemon/" + pokeName + "Label.png");
            animationTextures.add(animationTexture);
            TextureRegion animationRegion = new TextureRegion(animationTexture, 0, 0, animationTexture.getWidth() / 2,
                    animationTexture.getHeight());
            // Crea un'immagine utilizzando solo la prima metà dell'immagine
            Image animationImage = new Image(animationRegion);
            animationImage.setSize(96, 96);
            animationImage.setPosition(48, 26);
            stage.addActor(animationImage);
            itemActors.add(animationImage);

            // Creiamo un attore invisibile per l'area di click
            Actor clickArea = new Actor();
            clickArea.setBounds(740, 720 - 240, 257, 102); // Posizione e dimensioni dell'area

            // Aggiungiamo il listener per intercettare il clic
            clickArea.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (currentlySelected != null) {
                        FileHandle file2 = Gdx.files.local("ashJson/squadra.json");
                        String jsonString2 = file2.readString();
                        JsonValue json2 = new JsonReader().parse(jsonString2);

                        String nomeMossaVecchia = json2.get("poke" + (indexPoke)).get("mosse")
                                .get(Integer.parseInt(currentlySelected.getName())).getString("nome");
                        json2.get("poke" + (indexPoke)).get("mosse")
                                .remove(Integer.parseInt(currentlySelected.getName()));
                        JsonValue newMossa = new JsonValue(JsonValue.ValueType.object);
                        newMossa.addChild("nome", new JsonValue(mossaL.getNome()));
                        newMossa.addChild("tipo", new JsonValue(mossaL.getTipo()));
                        newMossa.addChild("ppTot", new JsonValue(mossaL.getmaxPP()));
                        newMossa.addChild("ppAtt", new JsonValue(mossaL.getmaxPP()));

                        json2.get("poke" + (indexPoke)).get("mosse").addChild(newMossa);
                        file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                        clearItems();
                        chiamanteB.piazzaLabel25(pokeName, mossaL.getNome(), nomeMossaVecchia);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                chiamanteB.cancelAP();
                                chiamanteB.reCreateTimers();
                            }
                        }, 10f);

                    }
                }
            });

            // Aggiungi l'area di clic allo stage
            stage.addActor(clickArea);

            // Creiamo un attore invisibile per l'area di click
            Actor clickArea2 = new Actor();
            clickArea2.setBounds(740, 720 - 454, 257, 102); // Posizione e dimensioni dell'area

            // Aggiungiamo il listener per intercettare il clic
            clickArea2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clearItems();
                    chiamanteB.piazzaLabel26(pokeName, mossaL.getNome());
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            chiamanteB.cancelAP();
                            chiamanteB.reCreateTimers();
                        }
                    }, 4f);
                }
            });

            stage.addActor(clickArea2);

            // Creiamo un attore invisibile per l'area di click
            Actor clickArea3 = new Actor();
            clickArea3.setBounds(688, 720 - 708, 320, 102); // Posizione e dimensioni dell'area

            // Aggiungiamo il listener per intercettare il clic
            clickArea3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    infoPoke = new infoPoke(stage, indexPoke, false);
                }
            });

            // Aggiungi l'area di clic allo stage
            stage.addActor(clickArea3);

            for (int i = 0; i < listaMosse.size(); i++) {
                Image labelMosse = new Image(listaMosse.get(i).getLabelTipo(listaMosse.get(i).getTipo()));
                labelMosse.setPosition(98, ((153 * (4 - i)) - 1) + 33 * i);
                labelMosse.setSize(205, 100);
                labelMosse.setOrigin(Align.center);
                stage.addActor(labelMosse);
                itemActors.add(labelMosse);

                Label labelNomeMossa = new Label(listaMosse.get(i).getNome(), new Label.LabelStyle(font, null));
                labelNomeMossa.setPosition(labelMosse.getX() + 16, labelMosse.getY() + 54); // Posiziona la label
                                                                                            // accanto all'immagine
                                                                                            // della mossa
                labelNomeMossa.setFontScale(1f);
                stage.addActor(labelNomeMossa);
                itemActors.add(labelNomeMossa);

                Label labelPPTot = new Label(listaMosse.get(i).getmaxPP(), new Label.LabelStyle(font, null));
                labelPPTot.setPosition(labelMosse.getX() + 156, labelMosse.getY() + 22);
                labelPPTot.setFontScale(0.8f);
                stage.addActor(labelPPTot);
                itemActors.add(labelPPTot);

                Label labelPPatt = new Label(listaMosse.get(i).getattPP(), new Label.LabelStyle(font, null));
                if (Integer.parseInt(listaMosse.get(i).getattPP()) > 9) {
                    labelPPatt.setPosition(labelMosse.getX() + 116, labelMosse.getY() + 22);
                } else {
                    labelPPatt.setPosition(labelMosse.getX() + 126, labelMosse.getY() + 22);
                }
                labelPPatt.setFontScale(0.8f);
                stage.addActor(labelPPatt);
                itemActors.add(labelPPatt);

                Label labelPotenza = new Label("Potenza = " + listaMosse.get(i).getPotenza(),
                        new Label.LabelStyle(font2, null));
                labelPotenza.setPosition(400, ((153 * (4 - i)) - 1) + 33 * i + 70);
                labelPotenza.setFontScale(3f);
                stage.addActor(labelPotenza);
                itemActors.add(labelPotenza);

                Label labelPrecisione = new Label("Precisione = " + listaMosse.get(i).getPrecisione(),
                        new Label.LabelStyle(font2, null));
                labelPrecisione.setPosition(400, ((153 * (4 - i)) - 1) + 33 * i + 40);
                labelPrecisione.setFontScale(3f);
                stage.addActor(labelPrecisione);
                itemActors.add(labelPrecisione);

                // categoria mossa
                Texture textureCateg = new Texture("squadra/" + listaMosse.get(i).getTipologia() + ".png");
                Image imageCatMove = new Image(textureCateg);
                imageCatMove.setPosition(490, ((153 * (4 - i)) - 1) + 33 * i + 5);
                imageCatMove.setSize(32 * 1.6f, 15 * 1.6f);
                stage.addActor(imageCatMove);
                itemActors.add(imageCatMove);

                final int index = i;
                ClickListener listener = new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // Deselect previous item by resetting its scale or color
                        if (currentlySelected != null) {
                            currentlySelected.setScale(1f); // Reset scale
                            currentlySelected.setColor(Color.WHITE); // Reset color to original
                        }

                        // Set the new selected item
                        currentlySelected = labelMosse;

                        // Apply highlighting effect
                        currentlySelected.setName("" + index);
                        currentlySelected.setScale(1.05f); // Slightly larger
                        currentlySelected.setColor(1f, 1f, 0.95f, 1f); // Change to a highlighted color
                    }
                };

                if (i != 4) {
                    labelMosse.addListener(listener);
                    labelNomeMossa.addListener(listener);
                    labelPPTot.addListener(listener);
                    labelPPatt.addListener(listener);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore show apprendimentoMosse, " + e);
        }

    }

    private void newMoveOver4() {
        Gdx.input.setInputProcessor(stage);

        try {
            FileHandle filePoke = Gdx.files.internal("pokemon/Pokemon.json");
            String jsonStringPoke = filePoke.readString();
            JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

            FileHandle filePoke2 = Gdx.files.internal("pokemon/mosse.json");
            String jsonStringPoke2 = filePoke2.readString();
            JsonValue jsonPoke2 = new JsonReader().parse(jsonStringPoke2);

            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            FileHandle file2 = Gdx.files.local("ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            JsonValue poke = json2.get("poke" + (indexPoke));
            pokeName = poke.getString("nomePokemon");

            JsonValue mosse = poke.get("mosse");
            listaMosse.clear();
            for (JsonValue mossaJson : mosse) {
                String nomeMossa = mossaJson.getString("nome");
                String tipoMossa = mossaJson.getString("tipo");
                String attPP = mossaJson.getString("ppAtt");
                String maxPP = mossaJson.getString("ppTot");

                // Aggiungi la mossa alla lista
                Mossa mossa = new Mossa(nomeMossa, tipoMossa, maxPP, attPP, null); // gli passo Battle stesso con "this"
                                                                                   // per poter chiamare anche i metodi
                                                                                   // di Battle da Mossa
                listaMosse.add(mossa);
            }

            // Ottieni la lista delle mosse imparabili
            JsonValue mosseImparabili = jsonPoke.get(pokeName).get("mosseImparabili");
            String mossa = mosseImparabili.getString("M" + poke.getInt("livello") / 2);

            ArrayList<String> mosseList = new ArrayList<>();
            JsonValue mosseArray = poke.get("mosse");
            for (JsonValue mossaTest : mosseArray) {
                mosseList.add(mossaTest.getString("nome"));
            }

            if (!mossa.isEmpty() && !mosseList.contains(mossa)) {
                chiamanteB.destroyTimers();
                mossaL = new Mossa(mossa, jsonPoke2.get(mossa).getString("tipo"), jsonPoke2.get(mossa).getString("pp"),
                        jsonPoke2.get(mossa).getString("pp"), null); // gli passo Battle stesso con "this" per poter
                                                                     // chiamare anche i metodi di Battle da Mossa
                listaMosse.add(mossaL);
                chiamanteB.piazzaLabel24(pokeName, mossa);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        show();
                    }
                }, 7f);
            } else {
                return;
            }

        } catch (Exception e) {
            System.out.println("Errore newMoveOver4, " + e);
            return;
        }
    }

    public void newMoveUnder4() {
        try {
            FileHandle filePoke = Gdx.files.internal("pokemon/Pokemon.json");
            String jsonStringPoke = filePoke.readString();
            JsonValue jsonPoke = new JsonReader().parse(jsonStringPoke);

            FileHandle filePoke2 = Gdx.files.internal("pokemon/mosse.json");
            String jsonStringPoke2 = filePoke2.readString();
            JsonValue jsonPoke2 = new JsonReader().parse(jsonStringPoke2);

            // Ottieni l'oggetto JSON corrispondente al Pokémon specificato
            FileHandle file2 = Gdx.files.local("ashJson/squadra.json");
            String jsonString2 = file2.readString();
            JsonValue json2 = new JsonReader().parse(jsonString2);
            JsonValue poke = json2.get("poke" + (indexPoke));
            String pokeName = poke.getString("nomePokemon");

            // Ottieni la lista delle mosse imparabili
            JsonValue mosseImparabili = jsonPoke.get(pokeName).get("mosseImparabili");
            String mossa = mosseImparabili.getString("M" + poke.getInt("livello") / 2);

            ArrayList<String> mosseList = new ArrayList<>();
            JsonValue mosseArray = poke.get("mosse");
            for (JsonValue mossaTest : mosseArray) {
                mosseList.add(mossaTest.getString("nome"));
            }

            if (!mossa.isEmpty() && !mosseList.contains(mossa)) {
                chiamanteB.destroyTimers();

                JsonValue mossaJson = jsonPoke2.get(mossa);
                JsonValue newMossa = new JsonValue(JsonValue.ValueType.object);
                newMossa.addChild("nome", new JsonValue(mossa));
                newMossa.addChild("tipo", mossaJson.get("tipo"));
                newMossa.addChild("ppTot", new JsonValue(mossaJson.getInt("pp")));
                newMossa.addChild("ppAtt", new JsonValue(mossaJson.getString("pp")));

                json2.get("poke" + (indexPoke)).get("mosse").addChild(newMossa);
                file2.writeString(json2.prettyPrint(JsonWriter.OutputType.json, 1), false);

                chiamanteB.piazzaLabel27(pokeName, mossa);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        chiamanteB.cancelAP();
                        chiamanteB.reCreateTimers();
                    }
                }, 3.5f);
            } else {
                return;
            }

        } catch (Exception e) {
            System.out.println("Errore newMoveUnder4 apprendimentoMosse, " + e);
            return;
        }
    }

    public void clearItems() {
        try {
            // Rimuovi gli attori dell'inventario aggiunti durante la visualizzazione
            // precedente
            for (Actor actor : itemActors) {
                actor.remove(); // Rimuovi l'attore dalla stage
            }
            itemActors.clear(); // Pulisci l'array degli

        } catch (Exception e) {
            System.out.println("Errore clearItems apprendimentoMosse, " + e);
        }
    }

}
