/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Decorator;

import org.foi.uzdiz.ahip20_zadaca_3.Igraci;

/**
 *
 * @author NWTiS_2
 */
public class Golmani extends PozicijeDecorator {

    private final BrojIgracaPozicije pozicije;

    public Golmani(BrojIgracaPozicije pozicije) {
        this.pozicije = pozicije;
    }

    @Override
    public int brojIgraca() {
        return pozicije.brojIgraca()+izracunajGolmane();
        
    }

    @Override
    public String getOpis() {
        return pozicije.getOpis() + ", golmani " + izracunajGolmane();
    }

    private int izracunajGolmane() {
        int brojac = 0;
        for (Igraci i : org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez.getInstance().getIgraci()) {
            if (i.pozicije.contains("G")) {
                brojac++;
            }
        }
        return brojac;
    }
}


   
    

