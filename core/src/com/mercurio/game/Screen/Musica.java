package com.mercurio.game.Screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class Musica {
    AssetManager assetManager;
    private Music currentMusic;

    public Musica(AssetManager assetManager) {
        this.assetManager = assetManager;
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

}
