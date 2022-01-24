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
public class RezultatUtakmica {
    public Utakmice utakmica;
    public int goloviDomacina;
    public int goloviGosta;
    public boolean odigrana;
    
    public RezultatUtakmica(Utakmice utakmica, int goloviDomacina, int goloviGosta,boolean odigrana) {
        this.utakmica = utakmica;
        this.goloviDomacina = goloviDomacina;
        this.goloviGosta = goloviGosta;
        this.odigrana=odigrana;
    }

    public Utakmice getUtakmica() {
        return utakmica;
    }

    public void setUtakmica(Utakmice utakmica) {
        this.utakmica = utakmica;
    }

    public int getGoloviDomacina() {
        return goloviDomacina;
    }

    public void setGoloviDomacina(int goloviDomacina) {
        this.goloviDomacina = goloviDomacina;
    }

    public int getGoloviGosta() {
        return goloviGosta;
    }

    public void setGoloviGosta(int goloviGosta) {
        this.goloviGosta = goloviGosta;
    }

    public boolean isOdigrana() {
        return odigrana;
    }

    public void setOdigrana(boolean odigrana) {
        this.odigrana = odigrana;
    }
    
    
    
}
