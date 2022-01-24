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
public class Klubovi {
    public String klub;
    public String naziv;
    public String trener;
    
    public int brojKola;
    public int brojPobjeda;
    public int brojNerješenih;
    public int brojPoraza;
    public int brojDanihgolova;
    public int brojPrimljenihGolova;
    public int bodovi;
    public int brojZutihKartona;
    public int brojDrugihZutihKartona;
    public int brojCrvenihKartona;
    public int ukupnoKartona;
    public int razlika;


    public Klubovi(String klub, String naziv, String trener) {
        this.klub = klub;
        this.naziv = naziv;
        this.trener = trener;
    }

    public int getBodovi() {
        return bodovi;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }

    public String getKlub() {
        return klub;
    }

    public void setKlub(String klub) {
        this.klub = klub;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTrener() {
        return trener;
    }

    public void setTrener(String trener) {
        this.trener = trener;
    }

    public int getBrojKola() {
        return brojKola;
    }

    public void setBrojKola(int brojKola) {
        this.brojKola = brojKola;
    }
    
    public void setBrojZutihKartona(int brojZutihKartona) {
        this.brojZutihKartona = brojZutihKartona;
    }

    public void setBrojDrugihZutihKartona(int brojDrugihZutihKartona) {
        this.brojDrugihZutihKartona = brojDrugihZutihKartona;
    }

    public void setBrojCrvenihKartona(int brojCrvenihKartona) {
        this.brojCrvenihKartona = brojCrvenihKartona;
    }

    public int getBrojZutihKartona() {
        return brojZutihKartona;
    }

    public int getBrojDrugihZutihKartona() {
        return brojDrugihZutihKartona;
    }

    public int getBrojCrvenihKartona() {
        return brojCrvenihKartona;
    }

    public int getUkupnoKartona() {
        return ukupnoKartona;
    }

    public void setUkupnoKartona(int ukupnoKartona) {
        this.ukupnoKartona = ukupnoKartona;
    }

    public int getBrojPobjeda() {
        return brojPobjeda;
    }

    public void setBrojPobjeda(int brojPobjeda) {
        this.brojPobjeda = brojPobjeda;
    }

    public int getBrojNerješenih() {
        return brojNerješenih;
    }

    public void setBrojNerješenih(int brojNerješenih) {
        this.brojNerješenih = brojNerješenih;
    }

    public int getBrojPoraza() {
        return brojPoraza;
    }

    public void setBrojPoraza(int brojPoraza) {
        this.brojPoraza = brojPoraza;
    }

    public int getBrojDanihgolova() {
        return brojDanihgolova;
    }

    public void setBrojDanihgolova(int brojDanihgolova) {
        this.brojDanihgolova = brojDanihgolova;
    }

    public int getBrojPrimljenihGolova() {
        return brojPrimljenihGolova;
    }

    public void setBrojPrimljenihGolova(int brojPrimljenihGolova) {
        this.brojPrimljenihGolova = brojPrimljenihGolova;
    }

    public int getRazlika() {
        return razlika;
    }

    public void setRazlika(int razlika) {
        this.razlika = razlika;
    }
    
    
    
}
