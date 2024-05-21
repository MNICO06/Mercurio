package com.mercurio.game.personaggi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bot {
    private float player_width;
    private float player_height;
    private TextureRegion[] indietro;
    private TextureRegion[] sinistra;
    private TextureRegion[] destra;
    private TextureRegion[] avanti;
    private TextureRegion[][] tmp;
    private Animation<TextureRegion> camminaSinistra;
    private Animation<TextureRegion> camminaDestra;
    private Animation<TextureRegion> camminaAvanti;
    private Animation<TextureRegion> camminaIndietro;
    private Animation<TextureRegion> fermoSinistra;
    private Animation<TextureRegion> fermoDestra;
    private Animation<TextureRegion> fermoAvanti;
    private Animation<TextureRegion> fermoIndietro;
    private Animation<TextureRegion> characterAnimation;
    private TextureRegion currentAnimation;
    private Rectangle boxPlayer;
    private Rectangle boxBlocca;
    private Vector2 characterPosition;
    private float camminataFrame_speed = 0.14f;
    private float stateTime;
    private float xPunto;
    private float yPunto;
    private final String pathBot = "bots/bots.json";
    private String nomeJson;
    private boolean inAcqua = false;

    private float xBase;
    private float yBase;

    private float yFinale;
    private float yIniziale;

    private boolean affrontato = false;

    //rectangle.set(playerX, playerY, 1, 20); per aggiornare la posizione
    private Rectangle boxFerma;
    private final float variabileGira = -1;
    private float direzioneRettangolo;
    private boolean muovi;
    private float muovi_Y = 0;
    private float velMovimento = 40f;

    /* -y = il personaggio si trova sotto
     * y = il personaggio si trova sopra
     * 
     * -x = il personaggio si trova a sinstra
     * x = il personaggio si trova a destra
    */


    /*
     * STANDARD PER SALVARE I BOT
     * -prima lettera minuscola
     * -no spazi
     * -Lettera maiuscola alla nuova parola
     */
    private String direzione;
    private String direzioneFissa;

    public Bot(float width, float height, String texturePath, float xPunto, float yPunto) {
        this.xPunto = xPunto;
        this.yPunto = yPunto;
        player_width = width;
        player_height = height;

        settaTutto(width, height, texturePath, xPunto, yPunto);
    }

    public Bot(float width, float height, String texturePath, float xPunto, float yPunto, float inizio, float x, float y) {
        //inizio lo prendo da codice e serve per capire in base a dove parte (settato come proprietà rettangolo)
        this.xPunto = xPunto;
        this.yPunto = yPunto;
        player_width = width;
        player_height = height;

        if (inizio < 0) {
            inizio = inizio * -1;
        }

        this.direzioneRettangolo = inizio;


        settaTutto(width, height, texturePath, xPunto, yPunto);

        setPosition(x,y);

        boxFerma = new Rectangle(x, y, 1, direzioneRettangolo);
    }

    private void settaTutto(float width, float height, String texturePath, float xPunto, float yPunto) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        tmp = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 4);
        indietro = new TextureRegion[3];
        sinistra = new TextureRegion[3];
        destra = new TextureRegion[3];
        avanti = new TextureRegion[3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    if (j == 0) {
                        avanti[0] = tmp[i][j];
                    } else if (j == 1) {
                        destra[0] = tmp[i][j];
                    } else if (j == 2) {
                        avanti[1] = tmp[i][j];
                    }
                } else if (i == 1) {
                    if (j == 0) {
                        sinistra[0] = tmp[i][j];
                    } else if (j == 1) {
                        destra[1] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[0] = tmp[i][j];
                    }
                } else if (i == 2) {
                    if (j == 0) {
                        sinistra[1] = tmp[i][j];
                    } else if (j == 1) {
                        destra[2] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[1] = tmp[i][j];
                    }
                } else if (i == 3) {
                    if (j == 0) {
                        sinistra[2] = tmp[i][j];
                    } else if (j == 1) {
                        avanti[2] = tmp[i][j];
                    } else if (j == 2) {
                        indietro[2] = tmp[i][j];
                    }
                }
            }
        }

        fermoSinistra = new Animation<>(camminataFrame_speed, sinistra[2]);
        fermoDestra = new Animation<>(camminataFrame_speed, destra[0]);
        fermoAvanti = new Animation<>(camminataFrame_speed, avanti[0]);
        fermoIndietro = new Animation<>(camminataFrame_speed, indietro[0]);
        camminaSinistra = new Animation<>(camminataFrame_speed, sinistra);
        camminaDestra = new Animation<>(camminataFrame_speed, destra);
        camminaAvanti = new Animation<>(camminataFrame_speed, avanti);
        camminaIndietro = new Animation<>(camminataFrame_speed, indietro);
        
        stateTime = 0f;
        currentAnimation = camminaIndietro.getKeyFrame(0);
        characterPosition = new Vector2();
    }

    public void muovilBot() {
        stateTime += Gdx.graphics.getDeltaTime();

        if (muovi) {
            switch (direzione) {
                case "-y":

                    currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
                    characterPosition.y -= 40f * Gdx.graphics.getDeltaTime();
                    break;

                case "y":

                    currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
                    characterPosition.y += 40f * Gdx.graphics.getDeltaTime();
                    break;

                case "-x":

                    currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
                    characterPosition.x -= 40f * Gdx.graphics.getDeltaTime();
                    break;

                case "x":

                    currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
                    characterPosition.x += 40f * Gdx.graphics.getDeltaTime();
                    break;

                default:
                    break;
            }
        }
    }

    public void faiNuotareBot(Ash ash) {

        stateTime += Gdx.graphics.getDeltaTime();

        if (muovi) {
            
            switch (direzione) {
                case "-y":
                    //se il bot parte dall'alto e va verso il basso
                    if (yIniziale > yFinale) {
                        if (characterPosition.y <= yFinale) {
                            direzione = "y";
                        }
                    }
                    //se il bot barte dal basso e va verso l'alto
                    else {
                        if (characterPosition.y <= yIniziale) {
                            direzione = "y";
                        }
                    }

                    
                    currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
                    muovi_Y = velMovimento * -1;
                    //characterPosition.y -= 40f * Gdx.graphics.getDeltaTime();
                    //boxFerma.set(characterPosition.x, characterPosition.y - direzioneRettangolo, 20, direzioneRettangolo);
                    break;

                case "y":
                    //se il bot parte dall'alto e va verso il basso
                    if (yIniziale > yFinale) {
                        if (characterPosition.y >= yIniziale) {
                            direzione = "-y";
                        }
                    }
                    //se il bot barte dal basso e va verso l'alto
                    else {
                        if (characterPosition.y >= yFinale) {
                            direzione = "-y";
                        }
                    }

                    currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
                    muovi_Y = velMovimento;
                    //characterPosition.y += 40f * Gdx.graphics.getDeltaTime();
                    //boxFerma.set(characterPosition.x, characterPosition.y, 20, direzioneRettangolo);
                    break;

                default:
                    break;
            }

            //salvo le vecchie posizioni
            float old_y = characterPosition.y;

            //metodi controllo collisione in orizzontale (sia oggetti che npc)
            if (muovi_Y != 0) {
                characterPosition.y += muovi_Y * Gdx.graphics.getDeltaTime();
                boxPlayer.setPosition(characterPosition.x+player_width/4, characterPosition.y+2);
                if (boxPlayer.overlaps(ash.getBoxPlayer())) {
                    characterPosition.y = old_y;
                }
                else {
                    if (muovi_Y > 0) {
                        boxFerma.set(characterPosition.x, characterPosition.y, 20, direzioneRettangolo);
                    }
                    else {
                        boxFerma.set(characterPosition.x, characterPosition.y - direzioneRettangolo, 20, direzioneRettangolo);
                    }
                }
            }

            muovi_Y = 0;

        }
        boxPlayer.set(characterPosition.x + player_width / 4 - 2, characterPosition.y - 2, player_width / 2 + 2, player_height / 6 + 6);
    }

    public void avvicinaNuotatore() {
        switch (direzione) {
            case "-y":
                currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
                characterPosition.y -= 40f * Gdx.graphics.getDeltaTime();
                boxFerma.set(characterPosition.x, characterPosition.y - direzioneRettangolo, 20, direzioneRettangolo);
                break;

            case "y":
                currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
                characterPosition.y += 40f * Gdx.graphics.getDeltaTime();
                boxFerma.set(characterPosition.x, characterPosition.y, 20, direzioneRettangolo);
                break;
        }
    }

    public float getHeight() {
        return player_height;
    }

    public float getWidth() {
        return player_width;
    }

    public TextureRegion getCurrentAnimation() {
        return currentAnimation;
    }

    public Vector2 getPosition() {
        return characterPosition;
    }

    public Rectangle getBoxPlayer() {
        return boxPlayer;
    }

    public void setPosition(float x, float y) {
        characterPosition.set(x, y);
        boxPlayer = new Rectangle(x + player_width / 4 - 2, y - 2, player_width / 2 + 2, player_height / 6 + 6);
    }

    public void setBoxBlocca(Rectangle boxBlocca) {
        this.boxBlocca = boxBlocca;
    }

    public Rectangle getBoxBlocca() {
        return boxBlocca;
    }

    public void setFermoSinistra() {
        currentAnimation = fermoSinistra.getKeyFrame(stateTime, true);
    }

    public void setFermoDestra() {
        currentAnimation = fermoDestra.getKeyFrame(stateTime, true);
    }

    public void setFermoAvanti() {
        currentAnimation = fermoAvanti.getKeyFrame(stateTime, true);
    }

    public void setFermoIndietro() {
        currentAnimation = fermoIndietro.getKeyFrame(stateTime, true);
    }

    public void setCamminaSinistra() {
        currentAnimation = camminaSinistra.getKeyFrame(stateTime, true);
    }

    public void setCamminaDestra() {
        currentAnimation = camminaDestra.getKeyFrame(stateTime, true);
    }

    public void setCamminaAvanti() {
        currentAnimation = camminaAvanti.getKeyFrame(stateTime, true);
    }

    public void setCamminaIndietro() {
        currentAnimation = camminaIndietro.getKeyFrame(stateTime, true);
    }

    public void setDirezione(String direzione) {
        this.direzione = direzione;
    }

    public void setDirezioneFissa(String direzioneFissa) {
        this.direzioneFissa = direzioneFissa;
    }

    public String getDirezioneFissa() {
        return direzioneFissa;
    }

    public String getDirezione() {
        return direzione;
    }

    public float getPuntoX() {
        return xPunto;
    }

    public float getPuntoY() {
        return yPunto;
    }

    public void setX(float x) {
        characterPosition.x = x;
    }

    public void setY(float y) {
        characterPosition.y = y;
    }

    public void setXbase(float x) {
        this.xBase = x;
    }

    public void setYbase(float y) {
        this.yBase = y;
        this.yIniziale = y;
    }

    public float getXbase() {
        return xBase;
    }

    public float getYbase() {
        return yBase;
    }

    public void updateStateTime() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public float getStateTime() {
        return stateTime;
    }

    public String getPathBot() {
        return pathBot;
    }

    public void setnomeJson(String json) {
        this.nomeJson = json;
    }

    public String getNomeJson() {
        return nomeJson;
    }

    public boolean getAffrontato() {
        return affrontato;
    }

    public void setAffrontato(boolean affrontato) {
        this.affrontato = affrontato;
    }

    public void setInAcqua(boolean inAcqua) {
        this.inAcqua = inAcqua;
    }

    public boolean getInAcqua() {
        return inAcqua;
    }

    public void setYfinale(float y) {
        this.yFinale = y;
    }

    public float getYfinale() {
        return yFinale;
    }

    public void giraRettangolo(float x, float y) {
        direzioneRettangolo = direzioneRettangolo * variabileGira;
        boxFerma = new Rectangle(x, y, 1, direzioneRettangolo);
    }

    public Rectangle getRettangolo() {
        return boxFerma;
    }

    public void setMuovi(boolean muovi) {
        this.muovi = muovi;
    }

    public void setCamminataFrameSpeed(float camminataFrameSpeed) {
        this.camminataFrame_speed = camminataFrameSpeed;
    }

    public Rectangle getBoxNuotatore() {
        return boxFerma;
    }
}
