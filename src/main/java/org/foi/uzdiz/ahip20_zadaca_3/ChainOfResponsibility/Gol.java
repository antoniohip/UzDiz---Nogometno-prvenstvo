/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility;

import org.foi.uzdiz.ahip20_zadaca_3.Dogadaji;
import org.foi.uzdiz.ahip20_zadaca_3.Igraci;
import org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez;

/**
 *
 * @author NWTiS_2
 */
public class Gol extends ProvjeriDogadaj {


    public Gol() {        
    }


    public boolean provjeri(Dogadaji d) { 
        String dog = "";
        if(d.vrsta == 1){
            for(Igraci i : NogometniSavez.getInstance().getIgraci()){

                if(i.ime.equals(d.imeIgraca)){
                    dog = i.dogadajNaUtakmici + "G";
                    i.setDogadajNaUtakmici(dog);
                    break;
                }
              
            }
            return true;
        }
        return provjeriSljedeci(d);
    }
}
