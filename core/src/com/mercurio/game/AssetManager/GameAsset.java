package com.mercurio.game.AssetManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameAsset {
    private static final AssetManager assetManager = new AssetManager();

    /*
     * 
     * Asset Ash
     * 
     */
    public enum AssetsAsh {
        P_INDIETRO("player/personaggioIndietro.png", Texture.class),
        P_AVANTI("player/personaggioAvanti.png", Texture.class),
        P_DESTRA("player/personaggioDestra.png", Texture.class),
        P_SINISTRA("player/personaggioSinistra.png", Texture.class),
        SURF_P_INDIETRO("player/surf indietro.png", Texture.class),
        SURF_P_AVANTI("player/surf avanti.png", Texture.class),
        SURF_P_DESTRA("player/surf destra.png", Texture.class),
        SURF_P_SINISTRA("player/surf sinistra.png", Texture.class);

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

    public Texture getAsh(AssetsAsh asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllAsh (){
        for (AssetsAsh asset : AssetsAsh.values()) {
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
        RIVALE("player/barry.png", Texture.class),
        COMMESSO("player/commesso.png", Texture.class);

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

    public Texture getBot(AssetsBot asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
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
    
    /*
     * 
     * Asset Borsa
     * 
     */
    public enum AssetBorsa {
        SF_1("sfondo/sfondo1.png", Texture.class),
        SF_2("sfondo/sfondo2.png", Texture.class),
        SF_USA("sfondo/usa.png", Texture.class),
        SF_CURE_BG("sfondo/cureBag.png", Texture.class),
        SF_KEY_BG("sfondo/keyBag.png", Texture.class),
        SF_MT_BAG("sfondo/mtBag.png", Texture.class),
        SF_BALL_BG("sfondo/ballBag.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetBorsa(String path, Class<?> type) {
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

    public void loadBorsaAsset() {
        for (AssetBorsa asset : AssetBorsa.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBorsa(AssetBorsa asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBorsa (){
        for (AssetBorsa asset : AssetBorsa.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Pokedex
     * 
     */
    public enum AssetPokeD {
        SF_POKEDEX("sfondo/sfondoPokedex.png", Texture.class),
        SF_POKEDEX_OP("sfondo/sfondoPokedexAperto.png", Texture.class),
        SF_POKEDEX_DS("sfondo/descrizionePokedex.png", Texture.class),
        SF_POKEDEX_OPG("sfondo/tastoAperturaPokedex.png", Texture.class),
        SF_POKEDEX_FI("sfondo/freccaIndietro.png", Texture.class),
        SF_POKEDEX_INT("sfondo/puntoInterrogativo.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetPokeD(String path, Class<?> type) {
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

    public void loadPokeDexAsset() {
        for (AssetPokeD asset : AssetPokeD.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getPokeD(AssetPokeD asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllPokeD (){
        for (AssetPokeD asset : AssetPokeD.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Medaglie
     * 
     */
    public enum AssetMedaglie {
        SF_MEDAGLIE_CL("sfondo/medaglieChiuse.png", Texture.class),
        SF_MEDAGLIE_OP("sfondo/medaglieAperte.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetMedaglie(String path, Class<?> type) {
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

    public void loadMedaglieAsset() {
        for (AssetMedaglie asset : AssetMedaglie.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getMedaglie(AssetMedaglie asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllMedaglie (){
        for (AssetMedaglie asset : AssetMedaglie.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Shop
     * 
     */
    public enum AssetShop {
        SF_POKE_MARKET("sfondo/sfondoPokemarket.png", Texture.class),
        SF_MARKET_UP("sfondo/frecciaMarketUp.png", Texture.class),
        SF_MARKET_DW("sfondo/frecciaMarketDown.png", Texture.class),
        SF_SELECT_OB("sfondo/lineaOggettoSelezionato.png", Texture.class),
        SF_OGGETTO_LN("sfondo/lineaOggetto.png", Texture.class),
        SF_QUANTITA_SH("sfondo/mostraQuantita.png", Texture.class),
        SF_FRECCIA_QUP("sfondo/frecciaQtaSu.png", Texture.class),
        SF_FRECCIA_QDW("sfondo/frecciaQtaGiu.png", Texture.class),
        SF_LABEL_OK("sfondo/okLabel.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetShop(String path, Class<?> type) {
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

    public void loadShopAsset() {
        for (AssetShop asset : AssetShop.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getShop(AssetShop asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllShop (){
        for (AssetShop asset : AssetShop.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     *  
     * Asset MiniMappa
     * 
     */
    public enum AssetMMappa {
        SF_MINI_COM("sfondo/miniMappaCompleta.png", Texture.class),
        SF_MINI_MBG("sfondo/miniMapBG.png", Texture.class),
        CS_CURSORE_MM1("cursore/cursoreMiniMap1.png", Texture.class),
        CS_CURSORE_MM2("cursore/cursoreMiniMap2.png", Texture.class),
        SF_X("sfondo/X.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetMMappa(String path, Class<?> type) {
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

    public void loadMiniMappaAsset() {
        for (AssetMMappa asset : AssetMMappa.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getMiniMappa(AssetMMappa asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllMiniM (){
        for (AssetMMappa asset : AssetMMappa.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset SceltaStarterScreen
     * 
     */
    public enum AssetSStarterS {
        SF_FUOCO("sfondo/sfondoFuoco.png", Texture.class),
        SF_ERBA("sfondo/sfondoErba.png", Texture.class),
        SF_ACQUA("sfondo/sfondoAcqua.png", Texture.class),
        PK_LITTEN("pokemon/litten.png", Texture.class),
        PK_ROWLET("pokemon/rowlet.png", Texture.class),
        PK_POPPLIO("pokemon/popplio.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetSStarterS(String path, Class<?> type) {
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

    public void loadStartScreenAsset() {
        for (AssetSStarterS asset : AssetSStarterS.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getStartScreen(AssetSStarterS asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSStarterS (){
        for (AssetSStarterS asset : AssetSStarterS.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Battle
     * 
     */
    public enum AssetBattle {
        BL_BATTLE_SF("battle/sfondoBattle.png", Texture.class),
        BL_BASE_D("battle/baseD.png", Texture.class),
        BL_BASE_U("battle/baseU.png", Texture.class),
        BL_LANCIO_BA("battle/lancioBall.png", Texture.class),
        BL_BALL_PL("battle/pokeBallPlayer.png", Texture.class),
        BL_ARROW_PL("battle/playerArrow.png", Texture.class),
        BL_ARROW_BT("battle/botArrow.png", Texture.class),
        BL_NUMBER_BF("battle/ballsForNumber.png", Texture.class),
        BL_FIGHT_BX("battle/fightBox.png", Texture.class),
        BL_PLAYER_HP("battle/playerHPBar.png", Texture.class),
        BL_BOT_HP("battle/botHPBar.png", Texture.class),
        BL_MOVE_NO("battle/noMove.png", Texture.class),
        BL_B("battle/b.png", Texture.class),
        PK_LIGHT_CL("pokemon/lightCircle.png", Texture.class),
        SF_EVOLUTION_BG("sfondo/evolutionBG.png", Texture.class);


        private final String path;
        private final Class<?> type;

        AssetBattle(String path, Class<?> type) {
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

    public void loadBattleAsset() {
        for (AssetBattle asset : AssetBattle.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBattle(AssetBattle asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBattle (){
        for (AssetBattle asset : AssetBattle.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Menu
     * 
     */
    public enum AssetMenu {
        MI_IMAGINE_SF("menuImage/sfondoImmagine.png", Texture.class),
        MI_MERCURIO_LG("menuImage/logoMercurio.png", Texture.class),
        MI_MERCURIO_SC("menuImage/scrittaMercurio.jpg", Texture.class),
        MI_GRUPPO_LG("menuImage/logoGruppo.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetMenu(String path, Class<?> type) {
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

    public void loadMenuAsset() {
        for (AssetMenu asset : AssetMenu.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getMenu(AssetMenu asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllMenu (){
        for (AssetMenu asset : AssetMenu.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset XSfondo
     * 
     */
    public enum AssetXSfondo {
        // Textures per la battaglia
        SF_SFONDO("sfondo/sfondo.png", Texture.class),
        SF_X("sfondo/X.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetXSfondo(String path, Class<?> type) {
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

    public void loadXSfondoAsset() {
        for (AssetXSfondo asset : AssetXSfondo.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getXSfondo(AssetXSfondo asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllXSfondo (){
        for (AssetXSfondo asset : AssetXSfondo.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset XSfondoAIT
     * 
     */
    public enum AssetXSfondoAIT {
        // Textures per la battaglia
        SF_AVANTI("sfondo/avanti.png", Texture.class),
        SF_INDIETRO("sfondo/indietro.png", Texture.class),
        SF_TYPES("sfondo/types.png", Texture.class),
        SF_X("sfondo/X.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetXSfondoAIT(String path, Class<?> type) {
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

    public void loadXSfondoAITAsset() {
        for (AssetXSfondoAIT asset : AssetXSfondoAIT.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getXSfondoAIT(AssetXSfondoAIT asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllXSfondoAIT (){
        for (AssetXSfondoAIT asset : AssetXSfondoAIT.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset SQPCURE
     * 
     */
    public enum AssetSQPCure {
        SQ_SQUADRA_NS("squadra/nsSquadra.png", Texture.class),
        SQ_FIRST_SQ("squadra/nsFirstSquadra.png", Texture.class),
        SQ_FIRST_SL("squadra/selFirst.png", Texture.class),
        SQ_SQUADRA_SL("squadra/selSquadra.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetSQPCure(String path, Class<?> type) {
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

    public void loadSQPCAsset() {
        for (AssetSQPCure asset : AssetSQPCure.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getSQPC(AssetSQPCure asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSQPC (){
        for (AssetSQPCure asset : AssetSQPCure.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Box
     * 
     */
    public enum AssetBox {
        PK_SPHEAL_LB("pokemon/sphealLabel.png", Texture.class),
        SF_BOX_CM("sfondo/sfondiBoxCompleti.png", Texture.class),
        SQ_POKE_SQ("squadra/sfondoPokeSquadra.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetBox(String path, Class<?> type) {
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
        for (AssetBox asset : AssetBox.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBox(AssetBox asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBox (){
        for (AssetBox asset : AssetBox.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset SquadraBox
     * 
     */
    public enum AssetSQBox {
        SQ_INFO("squadra/info.png", Texture.class),
        SQ_SPOSTA("squadra/sposta.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetSQBox(String path, Class<?> type) {
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

    public void loadSQBoxAsset() {
        for (AssetSQBox asset : AssetSQBox.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getSQBox(AssetSQBox asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSQBox (){
        for (AssetSQBox asset : AssetSQBox.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset BorsaPokedexBox
     * 
     */
    public enum AssetBPB {
        SF_AVANTI("sfondo/avanti.png", Texture.class),
        SF_INDIETRO("sfondo/indietro.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetBPB(String path, Class<?> type) {
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

    public void loadBPBAsset() {
        for (AssetBPB asset : AssetBPB.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBPB(AssetBPB asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBPB (){
        for (AssetBPB asset : AssetBPB.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset SquadraInfoSquadraCureBox
     * 
     */
    public enum AssetSISCB {
        SQ_CANCEL("squadra/cancel.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetSISCB(String path, Class<?> type) {
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

    public void loadSISCBAsset() {
        for (AssetSISCB asset : AssetSISCB.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getSISCB(AssetSISCB asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSISCB (){
        for (AssetSISCB asset : AssetSISCB.values()) {
            assetManager.unload(asset.getPath());
        }
    }
    
    /*
     * 
     * Asset SquadraBattleInfoPoke
     * 
     */
    public enum AssetSBIP {
        BL_WHITE_PX("battle/white_pixel.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetSBIP(String path, Class<?> type) {
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

    public void loadSBIPAsset() {
        for (AssetSBIP asset : AssetSBIP.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getSBIP(AssetSBIP asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSBIP (){
        for (AssetSBIP asset : AssetSBIP.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset MossaMosseBot
     * 
     */
    public enum AssetMMB {
        BL_LABEL_FG("battle/fullLabelFight.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetMMB(String path, Class<?> type) {
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

    public void loadMMBAsset() {
        for (AssetMMB asset : AssetMMB.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getMMB(AssetMMB asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllMMB (){
        for (AssetMMB asset : AssetMMB.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset LabFullMap
     * 
     */
    public enum AssetLFM {
        BT_MARK_EXL("bots/ExlMark.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetLFM(String path, Class<?> type) {
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

    public void loadLFMAsset() {
        for (AssetLFM asset : AssetLFM.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getLFM(AssetLFM asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllLFM (){
        for (AssetLFM asset : AssetLFM.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset LabelDiscorsi
     * 
     */
    public enum AssetLD {
        SF_TEXT_BOX("sfondo/boxText.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetLD(String path, Class<?> type) {
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

    public void loadLDsset() {
        for (AssetLD asset : AssetLD.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getLD(AssetLD asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllLD (){
        for (AssetLD asset : AssetLD.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset ApprendimentoMosse
     * 
     */
    public enum AssetAM {
        SF_MOVE_BG("sfondo/newMoveBG.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetAM(String path, Class<?> type) {
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

    public void loadAMAsset() {
        for (AssetAM asset : AssetAM.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getAM(AssetAM asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllAM (){
        for (AssetAM asset : AssetAM.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset Squadra
     * 
     */
    public enum AssetSquadra {
        SQ_CAMBIA("squadra/cambia.png", Texture.class),
        SQ_SQUADRA_NS("squadra/nsSquadra.png", Texture.class),
        SQ_FIRST_SQ("squadra/nsFirstSquadra.png", Texture.class),
        SQ_FIRST_SL("squadra/selFirst.png", Texture.class),
        SQ_SQUADRA_SL("squadra/selSquadra.png", Texture.class),
        SF_SFONDO("sfondo/sfondo.png", Texture.class),
        SQ_INFO("squadra/info.png", Texture.class),
        SQ_SPOSTA("squadra/sposta.png", Texture.class),
        SQ_CANCEL("squadra/cancel.png", Texture.class),
        BL_WHITE_PX("battle/white_pixel.png", Texture.class);


        private final String path;
        private final Class<?> type;

        AssetSquadra(String path, Class<?> type) {
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

    public void loadSquadraAsset() {
        for (AssetSquadra asset : AssetSquadra.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getSquadra(AssetSquadra asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllSquadra (){
        for (AssetSquadra asset : AssetSquadra.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset InfoPoke
     * 
     */
    public enum AssetInfoPoke {
        SQ_INFO_PK("squadra/infoPoke.png", Texture.class),
        SQ_CANCEL("squadra/cancel.png", Texture.class),
        SQ_TIPO("squadra/types.png", Texture.class),
        BL_WHITE_PX("battle/white_pixel.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetInfoPoke(String path, Class<?> type) {
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

    public void loadInfoPAsset() {
        for (AssetInfoPoke asset : AssetInfoPoke.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getInfoP(AssetInfoPoke asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllInfoP (){
        for (AssetInfoPoke asset : AssetInfoPoke.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset BorsaMedaglieMenuLabelPokedex
     * 
     */
    public enum AssetBMMLP {
        SF_X("sfondo/X.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetBMMLP(String path, Class<?> type) {
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

    public void loadBMMLPAsset() {
        for (AssetBMMLP asset : AssetBMMLP.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBMMLP(AssetBMMLP asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBMMLP (){
        for (AssetBMMLP asset : AssetBMMLP.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset BorsaMenuLabelSquadraSquadraCure
     * 
     */
    public enum AssetBMLSSC {
        SQ_SQUADRA_NS("squadra/nsSquadra.png", Texture.class),
        SQ_FIRST_SQ("squadra/nsFirstSquadra.png", Texture.class),
        SQ_FIRST_SL("squadra/selFirst.png", Texture.class),
        SQ_SQUADRA_SL("squadra/selSquadra.png", Texture.class),
        SF_SFONDO("sfondo/sfondo.png", Texture.class),
        SQ_CANCEL("squadra/cancel.png", Texture.class),
        BL_WHITE_PX("battle/white_pixel.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetBMLSSC(String path, Class<?> type) {
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

    public void loadBMLSSCPAsset() {
        for (AssetBMLSSC asset : AssetBMLSSC.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getBMLSSC(AssetBMLSSC asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllBMLSSC (){
        for (AssetBMLSSC asset : AssetBMLSSC.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * Asset PokedexInfoPoke
     * 
     */
    public enum AssetPIP {
        SQ_TIPO("squadra/types.png", Texture.class);

        private final String path;
        private final Class<?> type;

        AssetPIP(String path, Class<?> type) {
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

    public void loadPIPAsset() {
        for (AssetPIP asset : AssetPIP.values()) {
            assetManager.load(asset.getPath(), asset.getType());
        }
    }

    public Texture getPIP(AssetPIP asset) {
        return (Texture) assetManager.get(asset.getPath(), asset.getType());
    }

    public void unloadAllPIP (){
        for (AssetPIP asset : AssetPIP.values()) {
            assetManager.unload(asset.getPath());
        }
    }

    /*
     * 
     * General Method
     * 
     */
    public void finishLoading() {
        assetManager.finishLoading();
    }

    public void dispose() {
        assetManager.dispose();
    }
}
