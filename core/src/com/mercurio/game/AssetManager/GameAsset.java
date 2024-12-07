package com.mercurio.game.AssetManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAsset{
    private static final AssetManager assetManager = new AssetManager();

    /*
    *  
    *  Asset Battle
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
        NO_MOVE("battle/noMove.png", Texture.class),
        B("battle/b.png", Texture.class),
        WHITE_PX("battle/white_pixel.png", Texture.class),
        CIRCLE_LG("pokemon/lightCircle.png", Texture.class),

        //JSON
        MOSSE_JS("pokemon/mosse.json", FileHandle.class),
        POKEMON_JS("pokemon/Pokemon.json", FileHandle.class),

        // Font
        FONT("font/font.fnt", BitmapFont.class);

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

    @SuppressWarnings("unchecked")
    public static <T> T getBattle(Assets asset) {
        return (T) assetManager.get(asset.getPath(), asset.getType());
    }    

    public static void unloadBattle(Assets asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
    *  
    *  Asset Ash
    * 
    */
    public enum AssetsAsh {
        //JSON
        BORSA_JS("ashJson/borsa.json", FileHandle.class),
        BOX_JS("ashJson/box.json", FileHandle.class),
        GEN_DATA_JS("ashJson/datiGenerali.json", FileHandle.class),
        POS_DATA_JS("ashJson/datiPosizione.json", FileHandle.class),
        DIS_POKE_JS("ashJson/pokemonScoperti.json", FileHandle.class),
        SQUAD_JS("ashJson/squadra.json", FileHandle.class);

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

    public void loadAshAssets() {
        for (Assets asset : Assets.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateAsh() {
        return assetManager.update();
    }

    public static float getProgressAsh() {
        return assetManager.getProgress();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAhs(AssetsAsh asset) {
        return (T) assetManager.get(asset.getPath(), asset.getType());
    }    

    public static void unloadAsh(AssetsAsh asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
    *  
    *  Asset Bot
    * 
    */
    public enum AssetsBot {
        //JSON
        BOT_JS("bots/bots.json", FileHandle.class);

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

    public void loadBotAssets() {
        for (Assets asset : Assets.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateBot() {
        return assetManager.update();
    }

    public static float getProgressBot() {
        return assetManager.getProgress();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBot(AssetsBot asset) {
        return (T) assetManager.get(asset.getPath(), asset.getType());
    }    

    public static void unloadBot(AssetsBot asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
    *  
    *  Asset Strumenti
    * 
    */
    public enum AssetsStrumenti {
        //JSON
        STRUMENTI_JS("oggetti/strumenti.json", FileHandle.class);

        private final String path;
        private final Class<?> type;

        AssetsStrumenti(String path, Class<?> type) {
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

    public void loadStrumentiAssets() {
        for (Assets asset : Assets.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public static boolean updateStrumenti() {
        return assetManager.update();
    }

    public static float getProgressStrumenti() {
        return assetManager.getProgress();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getStrumenti(AssetsStrumenti asset) {
        return (T) assetManager.get(asset.getPath(), asset.getType());
    }    

    public static void unloadStrumenti(AssetsStrumenti asset) {
        if (assetManager.isLoaded(asset.getPath())) {
            assetManager.unload(asset.getPath());
        }
    }

    public static void dispose() {
        assetManager.dispose();
    }
}
