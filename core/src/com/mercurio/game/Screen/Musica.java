package com.mercurio.game.Screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

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
                    playMusic("musica/start.mp3");
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
                    playMusic("musica/canzoneCheSpacca.mp3");
                    current = "capitale";
                }
                break;

            default:
                break;
        }
    }

}
