/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3;

import java.sql.Timestamp;
import java.util.List;
public class NogometniSavez {
    
    private static NogometniSavez prvaInstanca=null;
    
    private List<Igraci> Igraci;
    private List<Dogadaji> Dogadaji;
    private List<Klubovi> Klubovi;
    private List<SastaviUtakmica> Sastavi;
    private List<Utakmice> Utakmice;
    private List<Treneri> Treneri;
    
       

   
    
    private NogometniSavez () {}
    
    public static NogometniSavez getInstance() {
        if(prvaInstanca == null) {
            prvaInstanca = new NogometniSavez();
        }
        return prvaInstanca;
    }  

    public static NogometniSavez getPrvaInstanca() {
        return prvaInstanca;
    }

    public static void setPrvaInstanca(NogometniSavez prvaInstanca) {
        NogometniSavez.prvaInstanca = prvaInstanca;
    }
    
    public List<Igraci> getIgraci() {
        return Igraci;
    }

    public void setIgraci(List<Igraci> Igraci) {
        this.Igraci = Igraci;
    }

    public List<Klubovi> getKlubovi() {
        return Klubovi;
    }

    public List<SastaviUtakmica> getSastavi() {
        return Sastavi;
    }

    public void setSastavi(List<SastaviUtakmica> Sastavi) {
        this.Sastavi = Sastavi;
    }

    public void setKlubovi(List<Klubovi> Klubovi) {
        this.Klubovi = Klubovi;
    }
     public List<Utakmice> getUtakmice() {
        return Utakmice;
    }

    public void setUtakmice(List<Utakmice> Utakmice) {
        this.Utakmice = Utakmice;
    }
      public List<Dogadaji> getDogadaji() {
        return Dogadaji;
    }

    public void setDogadaji(List<Dogadaji> Dogadaji) {
        this.Dogadaji = Dogadaji;
    }

    public List<Treneri> getTreneri() {
        return Treneri;
    }

    public void setTreneri(List<Treneri> Treneri) {
        this.Treneri = Treneri;
    }
    
    
}
