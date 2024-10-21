package com.mercurio.game.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

    // Metodo per riprodurre suoni brevi
    public void playSound(String soundPath) {
        if (!assetManager.isLoaded(soundPath, Sound.class)) {
            assetManager.load(soundPath, Sound.class);
            assetManager.finishLoading();
        }
        Sound sound = assetManager.get(soundPath, Sound.class);
        sound.play();
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

            case "battagliaCampione":
                if (current != "battagliaCampione") {
                    playMusic("musica/battagliaCampione.mp3");
                    current = "battagliaCampione";
                }
                break;
            
            case "battagliaCapoPalestra":
                if (current != "battagliaCapoPalestra") {
                    playMusic("musica/battagliaCapoPalestra.mp3");
                    current = "battagliaCapoPalestra";
                }
                break;
            
            case "battagliaSuper4":
                if (current != "battagliaSuper4") {
                    playMusic("musica/battagliaSuper4.mp3");
                    current = "battagliaSuper4";
                }
                break;

            case "battagliaTeamRocket":
                if (current != "battagliaTeamRocket") {
                    playMusic("musica/battagliaTeamRocket.mp3");
                    current = "battagliaTeamRocket";
                }
                break;

            case "battagliaTeamRoketBoss":
                if (current != "battagliaTeamRoketBoss") {
                    playMusic("musica/battagliaTeamRocketBoss.mp3");
                    current = "battagliaTeamRoketBoss";
                }
                break;

            case "battleTrainer":
                if (current != "battleTrainer") {
                    playMusic("musica/battleTrainer.mp3");
                    current = "battleTrainer";
                }
                break;
            
            case "campione":
                if (current != "campione") {
                    playMusic("musica/campione.mp3");
                    current = "campione";
                }
                break;
            
            case "catturaPokemon":
                if (current != "catturaPokemon") {
                    playMusic("musica/catturaPokemon.mp3");
                    current = "catturaPokemon";
                }
                break;
            
            case "cittaMontagna":
                if (current != "cittaMontagna") {
                    playMusic("musica/cittaMontagna.mp3");
                    current = "cittaMontagna";
                }
                break;
            
            case "comparsaTeamRocket":
                if (current != "comparsaTeamRocket") {
                    playMusic("musica/comparsaTeamRocket.mp3");
                    current = "comparsaTeamRocket";
                }
                break;
            
            case "grotta":
                if (current != "grotta") {
                    playMusic("musica/grotta.mp3");
                    current = "grotta";
                }
                break;

            case "labPokemon":
                if (current != "labPokemon") {
                    playMusic("musica/labPokemon.mp3");
                    current = "labPokemon";
                }
                break;
            
            case "legaPokemon":
                if (current != "legaPokemon") {
                    playMusic("musica/legaPokemon.mp3");
                    current = "legaPokemon";
                }
                break;

            case "monteCorona":
                if (current != "monteCorona") {
                    playMusic("musica/monteCorona.mp3");
                    current = "monteCorona";
                }
                break;
            
            case "opening":
                if (current != "opening") {
                    playMusic("musica/opening.mp3");
                    current = "opening";
                }
                break;
            
            case "palazzoTeamRocket":
                if (current != "palazzoTeamRocket") {
                    playMusic("musica/palazzoTeamRocket.mp3");
                    current = "palazzoTeamRocket";
                }
                break;
            
            case "palestraPokemon":
                if (current != "palestraPokemon") {
                    playMusic("musica/palestraPokemon.mp3");
                    current = "palestraPokemon";
                }
                break;
        
            case "pokeMarket":
                if (current != "pokeMarket") {
                    playMusic("musica/pokeMarket.mp3");
                    current = "pokeMarket";
                }
                break;
            
            case "preLega":
                if (current != "preLega") {
                    playMusic("musica/preLega.mp3");
                    current = "preLega";
                }
                break;
            
            case "sconfittaCapoPalestra":
                if (current != "sconfittaCapoPalestra") {
                    playMusic("musica/sconfittaCapoPalestra.mp3");
                    current = "sconfittaCapoPalestra";
                }
                break;

            case "sconfittiSuper4":
                if (current != "sconfittiSuper4") {
                    playMusic("musica/sconfittiSuper4.mp3");
                    current = "sconfittiSuper4";
                }
                break;

            case "sconfittoCampione":
                if (current != "sconfittoCampione") {
                    playMusic("musica/sconfittoCampione.mp3");
                    current = "sconfittoCampione";
                }
                break;
            
            case "startBattle":
                if (current != "startBattle") {
                    playMusic("musica/startBattle.mp3");
                    current = "startBattle";
                }
                break;

            case "trainerDefeated":
                if (current != "trainerDefeated") {
                    playMusic("musica/trainerDefeated.mp3");
                    current = "trainerDefeated";
                }
                break;

            case "viaVittoria":
                if (current != "viaVittoria") {
                    playMusic("musica/viaVittoria.mp3");
                    current = "viaVittoria";
                }
                break;

                
            // - - - - - - - - - - - - - - - -
            // Sound effects
        
            case "curaggioPokemon":
                playSound("audio/curaggioPokemon.mp3");
                break;

            case "levelUp":
                playSound("audio/levelUp.mp3");
                break;

            case "ottieniItem":
                playSound("audio/ottieniItem.mp3");
                break;

            case "ottieniMedaglie":
                playSound("audio/ottieniMedaglie.mp3");
                break;

            case "ottieniOggettoImp":
                playSound("audio/ottieniOggettoImp.mp3");
                break;

            case "ottieniPokemon":
                playSound("audio/ottieniPokemon.mp3");
                break;

            case "trainerDefeat":
                playSound("audio/trainerDefeat.mp3");
                break;

            default:
                break;
        }
    }
}
