package com.mercurio.game.utility;

import com.mercurio.game.Screen.Constant;

public class UtilityVariables {

    /*
     * Variabili STRING utilizzate in mercurio main ma usate da tutti
    */
    private String luogo = "";                                  //luogo per comprendere musiche, erba e altri dati
    private String ultimaVisita = Constant.CASA_ASH_SCREEN;     //variabile per capire dove essere riportati in caso di sconfitta
    private String ultimaVisitaLuogo = "casaSpawn";             //stessa cosa di prima ma per cambiare il luogo


    /*
     * Variabili BOOLEAN utilizzate in mercurio main ma usate da tutti
    */
    private boolean isInMovement = false;                       //variabile per comprendere se il personaggio è in movimento




    public UtilityVariables() {

    }


    //set
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
    public void setUltimaVisita(String ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }
    public void setUltimaVisitaLuogo(String ultimaVisitaLuogo) {
        this.ultimaVisitaLuogo = ultimaVisitaLuogo;
    }
    public void setIsInMovement(boolean isInMovement) {
        this.isInMovement = isInMovement;
    }

    //get
    public String getLuogo() {
        return luogo;
    }
    public String getUltimaVisita() {
        return ultimaVisita;
    }
    public String getUltimaVisitaLuogo() {
        return ultimaVisitaLuogo;
    }
    public boolean getIsInMovement() {
        return isInMovement;
    }
}
