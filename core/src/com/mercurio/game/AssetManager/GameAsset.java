package com.mercurio.game.AssetManager;

import java.util.EnumSet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameAsset {
    private static final AssetManager assetManager = new AssetManager();

    /*
     * 
     * Asset Battle
     * 
     */
    public enum Assets {
        // Textures per la battaglia
        SFONDO_BATTLE("battle/sfondoBattle.png", Texture.class),
        BASE_D("battle/baseD.png", Texture.class),
        BASE_U("battle/baseU.png", Texture.class),
        LANCIO_BALL("battle/lancioBall.png", Texture.class),
        BALL_PLAYER("battle/pokeBallPlayer.png", Texture.class),
        PLAYER_ARROW("battle/playerArrow.png", Texture.class),
        BOT_ARROW("battle/botArrow.png", Texture.class),
        BALLS_FOR_NUMBER("battle/ballsForNumber.png", Texture.class),
        FIGHT_BOX("battle/fightBox.png", Texture.class),
        HP_BAR("battle/playerHPBar.png", Texture.class),
        BOT_HP_BAR("battle/botHPBar.png", Texture.class),
        NO_MOVE("battle/noMove.png", Texture.class),
        B("battle/b.png", Texture.class),
        WHITE_PX("battle/white_pixel.png", Texture.class),
        CIRCLE_LG("pokemon/lightCircle.png", Texture.class);

        private final String path;
        private final Class<?> type;

        Assets(String path, Class<?> type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public Class<?> getType() {
            return type;
        }
    }

    public void loadBattleAssets() {
        for (Assets asset : Assets.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateBattle() {
        return assetManager.update();
    }

    public static float getProgressBattle() {
        return assetManager.getProgress();
    }

    public Texture getBattle(Assets asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public static void unloadBattle(Assets asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    public void unloadAllBattle(){
        for (Assets asset: Assets.values()){
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Ash
     * 
     */
    public enum AssetsAsh {
        // Textures per la battaglia
        P_INDIETRO("assets/player/personaggioIndietro.png", Texture.class),
        P_AVANTI("assets/player/personaggioAvanti.png", Texture.class),
        P_DESTRA("assets/player/personaggioDestra.png", Texture.class),
        P_SINISTRA("assets/player/personaggioSinistra.png", Texture.class),
        SURF_P_INDIETRO("assets/player/surf indietro.png", Texture.class),
        SURF_P_AVANTI("assets/player/surf avanti.png", Texture.class),
        SURF_P_DESTRA("assets/player/surf destra.png", Texture.class),
        SURF_P_SINISTRA("assets/player/surf sinistra.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetsAsh(String path, Class<?> type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public Class<?> getType() {
            return type;
        }
    }

    public void loadAshAsset() {
        for (AssetsAsh asset : AssetsAsh.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateAsh() {
        return assetManager.update();
    }

    public static float getProgressAsh() {
        return assetManager.getProgress();
    }

    public Texture getAsh(AssetsAsh asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public static void unloadAsh(AssetsAsh asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Bot
     * 
     */
    public enum AssetsBot {
        // Textures per la battaglia
        DOC("player/dottoressa.png", Texture.class),
        MOM("player/mammaAsh.png", Texture.class),
        PROF("player/professorRowan.png", Texture.class),
        RIVALE("player/barry.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetsBot(String path, Class<?> type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public Class<?> getType() {
            return type;
        }
    }

    public void loadBotAsset() {
        for (AssetsBot asset : AssetsBot.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateBot() {
        return assetManager.update();
    }

    public static float getProgressBot() {
        return assetManager.getProgress();
    }

    public Texture getBot(AssetsBot asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public static void unloadBot(AssetsBot asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    public void unloadAllBot (){
        for (AssetsBot asset : AssetsBot.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    public boolean areAssetsLoaded() {
        for (AssetsBot asset : AssetsBot.values()) {
            if (!assetManager.isLoaded(asset.getPath(), asset.getType())) {
                return false;
            }
        }
        return true;
    }

    public enum AssetsBox {
        SPHEAL_LB("pokemon/sphealLabel.png", Texture.class),
        BOX_SF_COM("sfondo/sfondiBoxCompleti.png", Texture.class),
        SF_AVANTI("assets/sfondo/avanti.png", Texture.class),
        SF_INDIETRO("assets/sfondo/indietro.png", Texture.class),
        SPOSTA("assets/squadra/sposta.png", Texture.class),
        INFO("assets/squadra/info.png", Texture.class),
        CANCEL("assets/squadra/cancel.png", Texture.class),
        SF_POKE_SQ("assets/squadra/sfondoPokeSquadra.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetsBox(String path, Class<?> type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public Class<?> getType() {
            return type;
        }
    }

    public void loadBoxAsset() {
        for (AssetsBox asset : AssetsBox.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateBox() {
        return assetManager.update();
    }

    public static float getProgressBox() {
        return assetManager.getProgress();
    }

    public Texture getBox(AssetsBox asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public static void unloadBox(AssetsBox asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    public void finishLoading() {
        assetManager.finishLoading();
    }

    public void dispose() {
        assetManager.dispose();
    }
}
