package com.mercurio.game.pokemon;

public class Experience {

    int esperienza=0;

    private int irregolare(int livello){

        if (livello<=50){
            esperienza=(livello*livello*livello*(100-livello))/50;
        }
        else if (livello>=51 && livello<=68){
            esperienza=(livello*livello*livello*(150-livello))/100;
        }
        else if (livello>=69 && livello<=98){
            esperienza=(livello*livello*livello*((1911-(livello*10))/3))/500;
        }
        else if (livello>=99 && livello<=100){
            esperienza=(livello*livello*livello*(160-livello))/100;
        }

        return esperienza;
    }

    private int medio_Lenta(int livello){

        esperienza=((6*livello*livello*livello)/5) - (15*livello*livello) + (100*livello) - 140;

        return esperienza;
    }

    private int lenta(int livello){

        esperienza=(5*livello*livello*livello)/4;

        return esperienza;
    }

    private int fluttuante(int livello){

        if (livello<=15){
            esperienza=(livello*livello*livello*((24+((livello+1)/3))/50));
        }
        else if (livello>=16 && livello<=35){
            esperienza=(livello*livello*livello*((14+livello)/50));
        }
        else if (livello>=36 && livello<=100){
            esperienza=(livello*livello*livello*((32+(livello/2))/50));
        }

        return esperienza;
    }

    private int veloce(int livello){

        esperienza=(4*livello*livello*livello)/5;

        return esperienza;
    }

    private int medio_Veloce(int livello){

        esperienza=livello*livello*livello;

        return esperienza;
    }
    
}
