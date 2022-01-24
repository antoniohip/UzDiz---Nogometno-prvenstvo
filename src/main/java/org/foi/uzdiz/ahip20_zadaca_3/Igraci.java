/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3;

import java.util.Date;
import java.util.List;

/**
 *
 * @author NWTiS_2
 */
public class Igraci extends Osoba {
    public List<String> pozicije;
    public Date roden;
    public int golovi;
    public String dogadajNaUtakmici="";

    public Igraci(List<String> pozicije, Date roden, String ime, String klub) {
        super(ime, klub);
        this.pozicije = pozicije;
        this.roden = roden;
        this.dogadajNaUtakmici = "";
    }



    public int getGolovi() {
        return golovi;
    }

    public void setGolovi(int golovi) {
        this.golovi = golovi;
    }

    public String getDogadajNaUtakmici() {
        return dogadajNaUtakmici;
    }

    public void setDogadajNaUtakmici(String dogadajNaUtakmici) {
        this.dogadajNaUtakmici = dogadajNaUtakmici;
    }
    
    
    
    
}
