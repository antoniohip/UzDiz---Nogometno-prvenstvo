/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3;

import java.security.Timestamp;
import java.util.Date;

/**
 *
 * @author NWTiS_2
 */
public class Utakmice {
    public int broj;
    public int kolo;
    public String domacin;
    public String gost;
    public Date pocetak;

    public Utakmice(int broj, int kolo, String domacin, String gost, Date pocetak) {
        this.broj = broj;
        this.kolo = kolo;
        this.domacin = domacin;
        this.gost = gost;
        this.pocetak = pocetak;
    }
    
    
}
