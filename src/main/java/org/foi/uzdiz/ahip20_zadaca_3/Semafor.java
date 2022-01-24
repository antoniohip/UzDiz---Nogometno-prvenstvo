/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3;

/**
 *
 * @author NWTiS_2
 */
public class Semafor {
    public int broj;
    public String min;
    public int vrsta;
    public String klub;
    public String imeIgraca;
    public String imeIgracaZamjena;
    public String domacin;
    public String gost;
    public String oznakaDom;
    public String oznakaGost;

    public Semafor(int broj, String min, int vrsta, String klub, String imeIgraca, String imeIgracaZamjena, String domacin, String gost, String oznakaDom, String oznakaGost) {
        this.broj = broj;
        this.min = min;
        this.vrsta = vrsta;
        this.klub = klub;
        this.imeIgraca = imeIgraca;
        this.imeIgracaZamjena = imeIgracaZamjena;
        this.domacin = domacin;
        this.gost = gost;
        this.oznakaDom = oznakaDom;
        this.oznakaGost = oznakaGost;
    }

    

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public String getKlub() {
        return klub;
    }

    public void setKlub(String klub) {
        this.klub = klub;
    }

    public String getImeIgraca() {
        return imeIgraca;
    }

    public void setImeIgraca(String imeIgraca) {
        this.imeIgraca = imeIgraca;
    }

    public String getImeIgracaZamjena() {
        return imeIgracaZamjena;
    }

    public void setImeIgracaZamjena(String imeIgracaZamjena) {
        this.imeIgracaZamjena = imeIgracaZamjena;
    }

    public String getDomacin() {
        return domacin;
    }

    public void setDomacin(String domacin) {
        this.domacin = domacin;
    }

    public String getGost() {
        return gost;
    }

    public void setGost(String gost) {
        this.gost = gost;
    }

    public String getOznakaDom() {
        return oznakaDom;
    }

    public void setOznakaDom(String oznakaDom) {
        this.oznakaDom = oznakaDom;
    }

    public String getOznakaGost() {
        return oznakaGost;
    }

    public void setOznakaGost(String oznakaGost) {
        this.oznakaGost = oznakaGost;
    }
    
    
    
    
}
