package com.mercurio.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class Shop extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font1;
    // TODO: da fare il controllo con le medaglie

    Array<Image> animationImages = new Array<>();
    Array<Actor> animationTextures = new Array<>();

    private int denaro = 0;
    private int quantita = 0;
    private int qtaInventario = 0;

    private int inizioRiga = 0; // aumento e diminuisco in base a se premo la freccia in giù o in su

    private int numeroRighe = 5; // da cambiare all'inzio controllando il numero di oggetti con le medagli che si
                                 // possiedono

    private Label labelDescrizioneCopia;
    private Label labelQuantitaCompra;
    private Image imageOggettoCopia;
    private Image imageLineaCopia;

    private MercurioMain game;
    Image background;
    Image imageFrecciaSuPag;
    Image imageFrecciaGiuPag;
    Image imageOggettoSelezionato;

    private Label labelDenaro;
    private int yPosIniziale = 560;
    private int yPosInizialeLabel = 590;
    Label labelQuantita;

    public Shop(MercurioMain game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        this.font1 = new BitmapFont(Gdx.files.local("font/small_letters_font.fnt"));
        Gdx.input.setInputProcessor(stage);
        show();
    }

    public void render() {
        try {

            float deltaTime = Gdx.graphics.getDeltaTime();
            stage.act(deltaTime); // Aggiorna lo stage con il deltaTime

            // Disegna la UI della borsa
            stage.draw(); // Disegna lo stage sullo SpriteBatch

        } catch (Exception e) {
            System.out.println("Errore render shop, " + e);
        }

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        game.closeBox();
        for (Actor actor : animationTextures) {
            actor.remove();
        }
        for (Image img : animationImages) {
            img.remove();
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        try {

            Texture textureBack = new Texture("sfondo/sfondoPokemarket.png");
            // Add background
            background = new Image(textureBack);
            background.setSize(1024, 720);
            stage.addActor(background);

            // apertura json per leggere quantitativo denaro da renderizzare
            FileHandle file = Gdx.files.local("ashJson/datiGenerali.json");
            JsonValue json = new JsonReader().parse(file.readString());
            denaro = json.getInt("denaro");
            labelDenaro = new Label(String.valueOf(denaro), new Label.LabelStyle(font1, null));
            labelDenaro.setFontScale(5f);

            int pos = getPos(denaro);
            labelDenaro.setPosition(pos, 610);
            stage.addActor(labelDenaro);

            // settaggio freccia in su
            Texture textureFrecciaSuPag = new Texture("sfondo/frecciaMarketUp.png");
            imageFrecciaSuPag = new Image(textureFrecciaSuPag);
            imageFrecciaSuPag.setPosition(460, 645);
            imageFrecciaSuPag.setSize(46, 25);
            stage.addActor(imageFrecciaSuPag);

            // listener per la freccia in su
            imageFrecciaSuPag.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    inizioRiga--;
                    svuotaTutto();
                    renderizzaLabel();
                    controllaFrecce();
                    imageOggettoSelezionato.setVisible(false);
                }
            });

            // settaggio freccia in giu
            Texture textureFrecciaGiuPag = new Texture("sfondo/frecciaMarketDown.png");
            imageFrecciaGiuPag = new Image(textureFrecciaGiuPag);
            imageFrecciaGiuPag.setPosition(460, 200);
            imageFrecciaGiuPag.setSize(46, 25);
            stage.addActor(imageFrecciaGiuPag);

            // listener per la freccia in giu
            imageFrecciaGiuPag.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    inizioRiga++;
                    svuotaTutto();
                    renderizzaLabel();
                    controllaFrecce();
                    imageOggettoSelezionato.setVisible(false);
                }
            });

            // posizionare le varie linee e poi le label al loro interno
            Texture texture = new Texture("sfondo/lineaOggettoSelezionato.png");
            imageOggettoSelezionato = new Image(texture);
            imageOggettoSelezionato.setSize(280 * 2, 35 * 2);
            stage.addActor(imageOggettoSelezionato);
            imageOggettoSelezionato.setVisible(false);

            controllaFrecce();

            renderizzaLabel();
        } catch (Exception e) {
            System.out.println("Errore show shop, " + e);
        }

    }

    // 240
    private void renderizzaLabel() {
        try {

            FileHandle file = Gdx.files.local("jsonGenerali/oggettiShop.json");
            JsonValue oggettiShop = new JsonReader().parse(file.readString());

            for (int i = inizioRiga; i < inizioRiga + 5; i++) {

                JsonValue oggettoJson = oggettiShop.get(i);

                if (oggettoJson != null) {

                    final String nome = oggettoJson.name;
                    final int index = i;

                    // posizionare le varie linee e poi le label al loro interno
                    Texture texture1 = new Texture("sfondo/lineaOggetto.png");
                    Image imageOggetto1 = new Image(texture1);
                    imageOggetto1.setSize(280 * 2, 35 * 2);
                    imageOggetto1.setPosition(430, yPosIniziale);
                    final float posImageLineaOggetto = yPosIniziale;
                    animationImages.add(imageOggetto1);
                    stage.addActor(imageOggetto1);

                    Label labelSinistra = new Label(nome, new Label.LabelStyle(font1, null));
                    labelSinistra.setFontScale(3f);
                    labelSinistra.setPosition(460, yPosInizialeLabel);
                    animationTextures.add(labelSinistra);
                    stage.addActor(labelSinistra);

                    Label labelDestra = new Label(String.valueOf(oggettoJson.getInt("costo")),
                            new Label.LabelStyle(font1, null));
                    labelDestra.setFontScale(3f);
                    labelDestra.setPosition(900, yPosInizialeLabel);
                    animationTextures.add(labelDestra);
                    stage.addActor(labelDestra);

                    // settaggio dell'immagine dell'oggetto
                    Texture texture = new Texture(oggettoJson.getString("path"));
                    Image imageOggetto = new Image(texture);
                    imageOggetto.setPosition(26, 26);
                    imageOggetto.setSize(75, 75);
                    imageOggetto.setVisible(false);
                    animationImages.add(imageOggetto);
                    stage.addActor(imageOggetto);

                    // settaggio dell'immagine del numero oggetti scelti e delle frecce
                    texture = new Texture("sfondo/mostraQuantita.png");
                    Image imageSceltaQta = new Image(texture);
                    imageSceltaQta.setPosition(10, 250);
                    imageSceltaQta.setSize(75, 85);
                    imageSceltaQta.setVisible(false);
                    animationImages.add(imageSceltaQta);
                    stage.addActor(imageSceltaQta);

                    texture = new Texture("sfondo/frecciaQtaSu.png");
                    Image imageFrecciaQtaSu = new Image(texture);
                    imageFrecciaQtaSu.setPosition(87, 297);
                    imageFrecciaQtaSu.setVisible(false);
                    animationImages.add(imageFrecciaQtaSu);
                    stage.addActor(imageFrecciaQtaSu);
                    imageFrecciaQtaSu.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (quantita < 100) {
                                quantita += 1;
                                labelQuantitaCompra.setText(String.valueOf(quantita));
                                posizionaLabelQuantitaCompra();
                            }
                        }
                    });

                    texture = new Texture("sfondo/frecciaQtaGiu.png");
                    Image imageFrecciaQtaGiu = new Image(texture);
                    imageFrecciaQtaGiu.setPosition(87, 250);
                    imageFrecciaQtaGiu.setVisible(false);
                    animationImages.add(imageFrecciaQtaGiu);
                    stage.addActor(imageFrecciaQtaGiu);
                    imageFrecciaQtaGiu.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (quantita > 0) {
                                quantita -= 1;
                                labelQuantitaCompra.setText(String.valueOf(quantita));
                                posizionaLabelQuantitaCompra();
                            }
                        }
                    });

                    labelQuantitaCompra = new Label(String.valueOf(quantita), new Label.LabelStyle(font1, null));
                    labelQuantitaCompra.setFontScale(5f);
                    posizionaLabelQuantitaCompra();
                    labelQuantitaCompra.setVisible(false);
                    animationTextures.add(labelQuantitaCompra);
                    stage.addActor(labelQuantitaCompra);

                    texture = new Texture("sfondo/okLabel.png");
                    Image imageOk = new Image(texture);
                    imageOk.setPosition(140, 250);
                    imageOk.setSize(35 * 2f, 24 * 2f);
                    imageOk.setVisible(false);
                    animationImages.add(imageOk);
                    stage.addActor(imageOk);
                    imageOk.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            int costo = oggettoJson.getInt("costo") * quantita;

                            // controllo che io abbia abbastanza soldi per comprarlo
                            if (costo < denaro) {
                                // controllo in modo che non vada oltre a 999 in totale
                                if (qtaInventario + quantita < 1000) {

                                    FileHandle file = Gdx.files.local("ashJson/datiGenerali.json");
                                    JsonValue json = new JsonReader().parse(file.readString());
                                    json.get("denaro").set(denaro - costo, "denaro");
                                    denaro = denaro - costo;
                                    file.writeString(json.prettyPrint(JsonWriter.OutputType.json, 1), false);

                                    FileHandle borsa = Gdx.files.local("ashJson/borsa.json");
                                    JsonValue oggettoBorsa = new JsonReader().parse(borsa.readString());
                                    for (int gianni = 0; gianni < oggettoBorsa
                                            .get(oggettiShop.get(index).getString("tipo")).size; gianni++) {
                                        if (oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).get(gianni)
                                                .getString("name").equals(nome)) {
                                            oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).get(gianni)
                                                    .get("quantity").set(qtaInventario + quantita, "quantity");
                                        }
                                    }
                                    borsa.writeString(oggettoBorsa.prettyPrint(JsonWriter.OutputType.json, 1), false);

                                    svuotaTutto();
                                    renderizzaLabel();
                                    aggiornaDenaro();
                                }

                            }
                        }
                    });

                    // codice per preparare la descrizione in modo da dividerla in righe
                    String[] parole = oggettoJson.getString("descrizione").split(" ");
                    StringBuilder rigaCorrente = new StringBuilder(parole[0]);

                    String testoDescrizione = "";

                    for (int j = 1; j < parole.length; j++) {
                        if (rigaCorrente.length() + 1 + parole[j].length() <= 55) {
                            rigaCorrente.append(" ").append(parole[j]);
                        } else {
                            rigaCorrente.append("\n");
                            testoDescrizione += rigaCorrente.toString();
                            rigaCorrente = new StringBuilder(parole[j]);
                        }
                    }

                    if (rigaCorrente.length() > 0) {
                        rigaCorrente.append("\n");
                        testoDescrizione += rigaCorrente.toString();
                    }

                    Label labelDescrizione = new Label(testoDescrizione, new Label.LabelStyle(font1, null));
                    labelDescrizione.setFontScale(3f);
                    labelDescrizione.setPosition(170, 70, Align.topLeft);
                    animationTextures.add(labelDescrizione);
                    labelDescrizione.setVisible(false);
                    stage.addActor(labelDescrizione);

                    labelQuantita = new Label("", new Label.LabelStyle(font1, null));

                    imageOggetto1.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            if (labelDescrizioneCopia != labelDescrizione) {
                                quantita = 0;
                            }

                            // rimuovo se c'enerano di precedenti
                            if (imageOggettoCopia != null) {
                                imageOggettoCopia.setVisible(false);
                                imageOggettoCopia = null;
                            }
                            if (labelDescrizioneCopia != null) {
                                labelDescrizioneCopia.setVisible(false);
                                labelDescrizioneCopia = null;
                            }
                            if (imageLineaCopia != null) {
                                imageLineaCopia.setVisible(true);
                                imageLineaCopia = null;
                            }

                            // vado ad aprire il pokedex regionale
                            labelDescrizione.setVisible(true);
                            imageOggetto.setVisible(true);

                            imageSceltaQta.setVisible(true);
                            imageFrecciaQtaGiu.setVisible(true);
                            imageFrecciaQtaSu.setVisible(true);
                            labelQuantitaCompra.setVisible(true);
                            imageOk.setVisible(true);

                            labelQuantitaCompra.setText(String.valueOf(quantita));
                            posizionaLabelQuantitaCompra();

                            labelDescrizioneCopia = labelDescrizione;
                            imageOggettoCopia = imageOggetto;
                            imageLineaCopia = imageOggetto1;

                            FileHandle borsa = Gdx.files.local("ashJson/borsa.json");
                            JsonValue oggettoBorsa = new JsonReader().parse(borsa.readString());

                            for (int gianni = 0; gianni < oggettoBorsa
                                    .get(oggettiShop.get(index).getString("tipo")).size; gianni++) {
                                if (oggettoBorsa.get(oggettiShop.get(index).getString("tipo")).get(gianni)
                                        .getString("name").equals(nome)) {
                                    qtaInventario = oggettoBorsa.get(oggettiShop.get(index).getString("tipo"))
                                            .get(gianni).getInt("quantity");
                                }
                            }

                            labelQuantita.setText(String.valueOf(qtaInventario));
                            labelQuantita.setFontScale(5f);
                            labelQuantita.setPosition(220, 185);
                            animationTextures.add(labelQuantita);
                            stage.addActor(labelQuantita);

                            imageOggetto1.setVisible(false);
                            imageOggettoSelezionato.setPosition(430, posImageLineaOggetto);
                            imageOggettoSelezionato.setVisible(true);
                        }
                    });

                    yPosIniziale -= 80;
                    yPosInizialeLabel -= 80;
                }
            }
        } catch (Exception e) {
            System.out.println("Errore renderizsaLabel shop, " + e);
        }

    }

    private void svuotaTutto() {
        try {

            for (Actor actor : animationTextures) {
                actor.remove();
            }
            for (Image img : animationImages) {
                img.remove();
            }

            yPosIniziale = 560;
            yPosInizialeLabel = 590;
        } catch (Exception e) {
            System.out.println("Errore svuoltaTutto shop, " + e);
        }

    }

    private void controllaFrecce() {
        if (inizioRiga == 0) {
            imageFrecciaSuPag.setVisible(false);
        } else {
            imageFrecciaSuPag.setVisible(true);
        }

        if (numeroRighe - inizioRiga <= 4) {
            imageFrecciaGiuPag.setVisible(false);
        } else {
            imageFrecciaGiuPag.setVisible(true);
        }
    }

    private void posizionaLabelQuantitaCompra() {
        if (quantita < 10) {
            labelQuantitaCompra.setPosition(35, 285);
        } else {// x = - 10 y = uguale
            labelQuantitaCompra.setPosition(25, 285);
        }
    }

    private void aggiornaDenaro() {
        labelDenaro.setText(String.valueOf(denaro));
        int pos = getPos(denaro);
        labelDenaro.setPosition(pos, 610);
    }

    private int getPos(int denaro) {
        int pos = 180;

        if (denaro > 9 && denaro < 100) {
            pos = pos - 20;
        } else if (denaro > 99 && denaro < 1000) {
            pos = pos - 40;
        } else if (denaro > 999 && denaro < 10000) {
            pos = pos - 60;
        } else if (denaro > 9999 && denaro < 100000) {
            pos = pos - 90;
        } else if (denaro > 99999 && denaro < 1000000) {
            pos = pos - 120;
        } else if (denaro > 999999 && denaro < 10000000) {
            pos = pos - 150;
        } else if (denaro > 9999999 && denaro < 100000000) {
            pos = pos - 170;
        } else if (denaro > 99999999 && denaro < 1000000000) {
            pos = pos - 175;
        }

        return pos;
    }
}
