package com.mercurio.game.Screen;

import com.mercurio.game.AssetManager.GameAsset;

public interface InterfacciaComune {
    
    void closeBattle();

    public GameAsset getGameAsset();

    void setLuogo(String luogo);

    void setPage(String screen);
}
