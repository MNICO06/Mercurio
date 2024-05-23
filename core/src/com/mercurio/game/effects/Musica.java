package com.mercurio.game.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.mercurio.game.Screen.MercurioMain;

public class Musica {
    AssetManager assetManager;
    private Music currentMusic;
    MercurioMain game;
    private String current;

    public Musica(MercurioMain game, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.game = game;
    }
    
    public void playMusic(String musicPath) {
        stopMusic();

        if (!assetManager.isLoaded(musicPath, Music.class)) {
            assetManager.load(musicPath, Music.class);
            assetManager.finishLoading();
        }
        currentMusic = assetManager.get(musicPath, Music.class);

        // Riproduci la musica in loop
        currentMusic.setLooping(true);
        currentMusic.play();
    }

    public void stopMusic() {
        // Interrompi la riproduzione della musica se è attualmente in corso
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }

    public void startMusic(String luogo) {
        switch (luogo) {
            case "start":
                if (current != "start") {
                    playMusic("musica/spawnFinale.mp3");
                    current = "start";
                }
                break;
            
            case "percorso1":
                if (current != "percorso1") {
                    playMusic("musica/firstPercorso.mp3");
                    current = "percorso1";
                }
                break;

            case "bosco":
                if (current != "bosco") {
                    playMusic("musica/bosco.mp3");
                    current = "bosco";
                }
                break;

            case "capitale":
                if (current != "capitale") {
                    playMusic("musica/capitale.mp3");
                    current = "capitale";
                }
                break;

            case "percorso2":
                if (current != "percorso2") {
                    playMusic("musica/percorso2.mp3");
                    current = "percorso2";
                }
                break;
            
            case "percorso3":
                if (current != "percorso3") {
                    playMusic("musica/percorsoAcqua.mp3");
                    current = "percorso3";
                }
                break;

            case "cittaMare":
                if (current != "cittaMare") {
                    playMusic("musica/cittaMare.mp3");
                    current = "cittaMare";
                }
                break;

            case "percorso4":
                if (current != "perocroso4") {
                    playMusic("musica/percorsoAlto.mp3");
                    current = "perocroso4";
                }
                break;

            case "cittaN":
                if (current != "cittaN") {
                    playMusic("musica/cittaAlta.mp3");
                    current = "cittaN";
                }
                break;

            case "casaSpawn":
                if (current != "casaSpawn") {
                    playMusic("musica/casa.mp3");
                    current = "casaSpawn";
                }
                break;
                
            case "pokeCenter":
                if (current != "pokeCenter") {
                    playMusic("musica/pokemonCenter.mp3");
                    current = "pokeCenter";
                }
                break;

            default:
                break;
        }
    }
}
