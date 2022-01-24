/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Decorator;

/**
 *
 * @author NWTiS_2
 */
public abstract class PozicijeDecorator implements BrojIgracaPozicije{

    @Override
    public String getOpis() {
        return "Dodaci";
    }

    @Override
    public int brojIgraca() {
        return 0;
    }
    
}
