package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.mercurio.game.effects.LabelDiscorsi;
import com.mercurio.game.pokemon.IV;
import com.mercurio.game.pokemon.Stats;

public class SceltaStarterScreen extends ScreenAdapter {

    private MercurioMain game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    Array<Image> animationImages = new Array<>();
    Array<Texture> animationTextures = new Array<>();

    private String sceltaLitten = "Vuoi scegliere Litten, il pokemon pirofelino di tipo fuoco?";
    private String sceltaRowlet = "Vuoi scegliere Rowlet, il pokemon aliderba di tipo erba?";
    private String scegliPopplio = "Vuoi scegliere Popplio, il pokemon otaria di tipo acqua?";
    private LabelDiscorsi labelLitten;
    private LabelDiscorsi labelRowlet;
    private LabelDiscorsi labelPopplio;
    private int risposta = -1;

    private boolean renderizzaDiscLitten = false;
    private boolean renderizzaDiscRowlet = false;
    private boolean renderizzaDiscPopplio = false;

    private boolean scelto = false;

    public SceltaStarterScreen(MercurioMain game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);

        show();
    }

    @Override
    public void show() {

        try {

            Texture litten = new Texture("pokemon/litten.png");
            Texture rowlet = new Texture("pokemon/rowlet.png");
            Texture popplio = new Texture("pokemon/popplio.png");

            Texture cerchioLitten = new Texture("sfondo/sfondoFuoco.png");
            Texture cerchioRowlet = new Texture("sfondo/sfondoErba.png");
            Texture cerchioPopplio = new Texture("sfondo/sfondoAcqua.png");

            Image cerchioLittenImage = new Image(cerchioLitten);
            Image cerchioRowletImage = new Image(cerchioRowlet);
            Image cerchioPopplioImage = new Image(cerchioPopplio);

            cerchioLittenImage.setSize(250, 250);
            cerchioRowletImage.setSize(250, 250);
            cerchioPopplioImage.setSize(250, 250);

            int cols = 4;
            int rows = 1;

            // Crea array per contenere i frame
            TextureRegion[] littenFrames = new TextureRegion[cols];
            TextureRegion[] rowletFrames = new TextureRegion[cols];
            TextureRegion[] popplioFrames = new TextureRegion[cols];

            // Suddividi le textures in frame
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // Suddividi ogni texture in frame usando TextureRegion.split
                    littenFrames[j] = new TextureRegion(litten, j * (litten.getWidth() / cols),
                            i * (litten.getHeight() / rows), litten.getWidth() / cols, litten.getHeight() / rows);
                    rowletFrames[j] = new TextureRegion(rowlet, j * (rowlet.getWidth() / cols),
                            i * (rowlet.getHeight() / rows), rowlet.getWidth() / cols, rowlet.getHeight() / rows);
                    popplioFrames[j] = new TextureRegion(popplio, j * (popplio.getWidth() / cols),
                            i * (popplio.getHeight() / rows), popplio.getWidth() / cols, popplio.getHeight() / rows);
                }
            }

            // Crea immagini per il primo frame (indice 0) di ciascun Pokémon
            Image littenImage = new Image(littenFrames[0]);
            Image rowletImage = new Image(rowletFrames[0]);
            Image popplioImage = new Image(popplioFrames[0]);

            littenImage.setSize(200, 200);
            rowletImage.setSize(200, 200);
            popplioImage.setSize(200, 200);

            // Posiziona le immagini sullo stage
            popplioImage.setPosition(100, 300); // Posizione di litten
            rowletImage.setPosition(400, 300); // Posizione di rowlet
            littenImage.setPosition(700, 300); // Posizione di popplio

            cerchioPopplioImage.setPosition(popplioImage.getX() + popplioImage.getWidth() / 2,
                    popplioImage.getY() + popplioImage.getHeight() / 2, Align.center);
            cerchioRowletImage.setPosition(rowletImage.getX() + rowletImage.getWidth() / 2,
                    rowletImage.getY() + rowletImage.getHeight() / 2, Align.center);
            cerchioLittenImage.setPosition(littenImage.getX() + littenImage.getWidth() / 2,
                    littenImage.getY() + littenImage.getHeight() / 2, Align.center);

            stage.addActor(cerchioPopplioImage);
            stage.addActor(cerchioRowletImage);
            stage.addActor(cerchioLittenImage);

            popplioImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    popplioImage.setDrawable(new TextureRegionDrawable(popplioFrames[1]));

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            popplioImage.setDrawable(new TextureRegionDrawable(popplioFrames[0]));
                        }
                    }, 0.5f);

                    if (!renderizzaDiscPopplio) {
                        labelPopplio = new LabelDiscorsi(scegliPopplio, 30, 0, false, true);
                        renderizzaDiscRowlet = false;
                        renderizzaDiscLitten = false;
                        labelLitten = null;
                        labelRowlet = null;
                        renderizzaDiscPopplio = true;
                    }
                }
            });

            rowletImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    rowletImage.setDrawable(new TextureRegionDrawable(rowletFrames[1]));

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            rowletImage.setDrawable(new TextureRegionDrawable(rowletFrames[0]));
                        }
                    }, 0.5f);

                    if (!renderizzaDiscRowlet) {
                        labelRowlet = new LabelDiscorsi(sceltaRowlet, 30, 0, false, true);
                        renderizzaDiscLitten = false;
                        renderizzaDiscPopplio = false;
                        labelLitten = null;
                        labelPopplio = null;
                        renderizzaDiscRowlet = true;
                    }
                }
            });

            littenImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    littenImage.setDrawable(new TextureRegionDrawable(littenFrames[1]));

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            littenImage.setDrawable(new TextureRegionDrawable(littenFrames[0]));
                        }
                    }, 0.5f);

                    if (!renderizzaDiscLitten) {
                        labelLitten = new LabelDiscorsi(sceltaLitten, 30, 0, false, true);
                        renderizzaDiscPopplio = false;
                        renderizzaDiscRowlet = false;
                        labelPopplio = null;
                        labelRowlet = null;
                        renderizzaDiscLitten = true;
                    }
                }
            });

            // Aggiungi le immagini allo stage
            stage.addActor(littenImage);
            stage.addActor(rowletImage);
            stage.addActor(popplioImage);
        } catch (Exception e) {
            System.out.println("Errore show sceltaStarterScreen, " + e);
        }

    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

        try {

            if (renderizzaDiscLitten && labelLitten != null) {
                risposta = labelLitten.renderDisc();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                    labelLitten.advanceText();
                }

                if (risposta != -1) {
                    if (risposta == 1) {
                        salvaStarter("Litten");
                        labelLitten.setSceltaUtente(-1);
                        salvaStarterRivale("Popplio");
                        scelto = true;

                    } else if (risposta == 0) {
                        labelLitten.setSceltaUtente(-1);
                    }
                }
            }
            if (renderizzaDiscPopplio && labelPopplio != null) {
                risposta = labelPopplio.renderDisc();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                    labelPopplio.advanceText();
                }

                if (risposta != -1) {
                    if (risposta == 1) {
                        salvaStarter("Popplio");
                        labelPopplio.setSceltaUtente(-1);
                        salvaStarterRivale("Rowlet");
                        scelto = true;
                    } else if (risposta == 0) {
                        labelPopplio.setSceltaUtente(-1);
                    }
                }
            }
            if (renderizzaDiscRowlet && labelRowlet != null) {
                risposta = labelRowlet.renderDisc();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // da fare quando il personaggio deve andare avanti di testo (quindi cambiarlo)
                    labelRowlet.advanceText();
                }

                if (risposta != -1) {
                    if (risposta == 1) {
                        salvaStarter("Rowlet");
                        labelRowlet.setSceltaUtente(-1);
                        salvaStarterRivale("Litten");
                        scelto = true;
                    } else if (risposta == 0) {
                        labelRowlet.setSceltaUtente(-1);
                    }
                }
            }

            // Disegna la UI della borsa
            stage.draw(); // Disegna lo stage sullo SpriteBatch
        } catch (Exception e) {
            System.out.println("Errore render sceltaStarterScreen, " + e);
        }

    }

    private void salvaStarter(String pokemon) {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("ashJson/squadra.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            JsonValue newPokemon = new JsonValue(JsonValue.ValueType.object);
            newPokemon.addChild("nomePokemon", new JsonValue(pokemon));
            newPokemon.addChild("livello", new JsonValue("5"));
            newPokemon.addChild("esperienza", new JsonValue(0));

            JsonValue statistiche = new JsonValue(JsonValue.ValueType.object);
            statistiche.addChild("hp", new JsonValue(0));
            statistiche.addChild("hpTot", new JsonValue(0));
            statistiche.addChild("attack", new JsonValue(0));
            statistiche.addChild("defense", new JsonValue(0));
            statistiche.addChild("special_attack", new JsonValue(0));
            statistiche.addChild("special_defense", new JsonValue(0));
            statistiche.addChild("speed", new JsonValue(0));
            newPokemon.addChild("statistiche", statistiche);

            JsonValue evStats = new JsonValue(JsonValue.ValueType.object);
            evStats.addChild("Hp", new JsonValue(0));
            evStats.addChild("Att", new JsonValue(0));
            evStats.addChild("Dif", new JsonValue(0));
            evStats.addChild("Spec", new JsonValue(0));
            evStats.addChild("Vel", new JsonValue(0));

            newPokemon.addChild("ev", evStats);

            IV iv = new IV();
            newPokemon.addChild("iv", iv.creaIV());

            JsonValue mosseJson = new JsonValue(JsonValue.ValueType.array);
            // recupero dal json
            FileHandle filePokemon = Gdx.files.local("pokemon/pokemon.json");
            JsonValue jsonPokemon = new JsonReader().parse(filePokemon.readString());
            String nomeMossa1 = jsonPokemon.get(pokemon).get("mosseImparabili").getString("M1");
            String nomeMossa2 = jsonPokemon.get(pokemon).get("mosseImparabili").getString("M2");

            FileHandle fileMosse = Gdx.files.local("pokemon/mosse.json");
            JsonValue jsonMosse = new JsonReader().parse(fileMosse.readString());

            JsonValue mossa1 = new JsonValue(JsonValue.ValueType.object);
            mossa1.addChild("nome", new JsonValue(nomeMossa1));
            mossa1.addChild("tipo", new JsonValue(jsonMosse.get(nomeMossa1).getString("tipo")));
            mossa1.addChild("ppTot", new JsonValue(jsonMosse.get(nomeMossa1).getInt("pp")));
            mossa1.addChild("ppAtt", new JsonValue(Integer.toString(jsonMosse.get(nomeMossa1).getInt("pp"))));
            mosseJson.addChild(mossa1);

            JsonValue mossa2 = new JsonValue(JsonValue.ValueType.object);
            mossa2.addChild("nome", new JsonValue(nomeMossa2));
            mossa2.addChild("tipo", new JsonValue(jsonMosse.get(nomeMossa2).getString("tipo")));
            mossa2.addChild("ppTot", new JsonValue(jsonMosse.get(nomeMossa2).getInt("pp")));
            mossa2.addChild("ppAtt", new JsonValue(Integer.toString(jsonMosse.get(nomeMossa2).getInt("pp"))));
            mosseJson.addChild(mossa2);

            newPokemon.addChild("mosse", mosseJson);

            newPokemon.addChild("tipoBall", new JsonValue("pokeball"));

            float x = (float) (Math.random() * (1.3 - 0.4)) + 0.4f;
            newPokemon.addChild("x", new JsonValue(x));

            json.remove("poke1");
            json.addChild("poke1", newPokemon);

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

            Stats stats = new Stats();
            stats.aggiornaStatistichePokemon(1);

            // Carica il file JSON
            FileHandle fileScoperti = Gdx.files.local("ashJson/pokemonScoperti.json");
            JsonValue jsonScoperti = new JsonReader().parse(fileScoperti.readString());
            for (int i = 0; i < jsonScoperti.size; i++) {
                if (jsonScoperti.get(i).getString("nome").equals(pokemon)) {
                    jsonScoperti.get(i).get("incontrato").set("1");
                }
            }

            fileScoperti.writeString(jsonScoperti.prettyPrint(JsonWriter.OutputType.json, 1), false);

        }catch(Exception e) {
            System.out.println("Errore salvaStarter SceltaStarterScreen, " + e);
        }
    }

    private void salvaStarterRivale(String pokemon) {
        try {
            // Carica il file JSON
            FileHandle file = Gdx.files.local("bots/bots.json");
            String jsonString = file.readString();

            JsonValue json = new JsonReader().parse(jsonString);

            json.get("rivale").get("poke1").get("nomePokemon").set(pokemon);
            json.get("rivale").get("poke1").get("livello").set("5");

            // recupero dal json del nome delle due mosse
            FileHandle filePokemon = Gdx.files.local("pokemon/pokemon.json");
            JsonValue jsonPokemon = new JsonReader().parse(filePokemon.readString());
            String nomeMossa1 = jsonPokemon.get(pokemon).get("mosseImparabili").getString("M1");
            String nomeMossa2 = jsonPokemon.get(pokemon).get("mosseImparabili").getString("M2");

            // recupero dal json del tipo delle due mosse
            FileHandle fileMosse = Gdx.files.local("pokemon/mosse.json");
            JsonValue jsonMosse = new JsonReader().parse(fileMosse.readString());

            JsonValue mosseArray = json.get("rivale").get("poke1").get("mosse");
            JsonValue primaMossa = mosseArray.get(0);

            primaMossa.get("nome").set(nomeMossa1);
            primaMossa.get("tipo").set(jsonMosse.get(nomeMossa1).getString("tipo"));

            JsonValue secondaMossa = mosseArray.get(1);

            secondaMossa.get("nome").set(nomeMossa2);
            secondaMossa.get("tipo").set(jsonMosse.get(nomeMossa2).getString("tipo"));

            file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

        } catch (Exception e) {
            System.out.println("Errore salvaStarterRivale sceltaStarterScreen, " + e);
        }

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        game.closeBox();
    }

    public boolean getSceltoStarter() {
        return scelto;
    }
}
