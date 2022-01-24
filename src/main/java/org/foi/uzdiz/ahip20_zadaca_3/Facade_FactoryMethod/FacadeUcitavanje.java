/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Facade_FactoryMethod;

import java.util.List;
import org.foi.uzdiz.ahip20_zadaca_3.Dogadaji;
import org.foi.uzdiz.ahip20_zadaca_3.Igraci;
import org.foi.uzdiz.ahip20_zadaca_3.Klubovi;
import org.foi.uzdiz.ahip20_zadaca_3.SastaviUtakmica;
import org.foi.uzdiz.ahip20_zadaca_3.Utakmice;

/**
 *
 * @author NWTiS_2
 */
public class FacadeUcitavanje {
    
    public List<Klubovi> ucitajKlubove(String datoteka){
        IDatotekaCreator dc = new DatotekaCreatorKlubovi();
        IDatotekaProduct dp = dc.createProduct();
        System.out.println("Učitavanje klubova....");
        List<Klubovi> sviKlubovi = (List<Klubovi>) dp.getConcreteProducts(datoteka);
        System.out.println("Učitavanje klubova završeno. ");
        return sviKlubovi;
    }
    
    public List<Igraci> ucitajIgrace(String datoteka){
        IDatotekaCreator dc = new DatotekaCreatorIgraci();
        IDatotekaProduct dp = dc.createProduct();
        System.out.println("Učitavanje igrača....");
        List<Igraci> sviIgraci = (List<Igraci>) dp.getConcreteProducts(datoteka);
        System.out.println("Učitavanje igrača završeno. ");
        return sviIgraci;
    }
    
    public List<Utakmice> ucitajUtakmice(String datoteka){
        IDatotekaCreator dc = new DatotekaCreatorUtakmice();
        IDatotekaProduct dp = dc.createProduct();
        System.out.println("Učitavanje utakmica....");
        List<Utakmice> sveUtakmice = (List<Utakmice>) dp.getConcreteProducts(datoteka);
        System.out.println("Učitavanje utakmica završeno. ");
        return sveUtakmice;
    }
    
    public List<SastaviUtakmica> ucitajSastave(String datoteka){
        IDatotekaCreator dc = new DatotekaCreatorSastaviUtakmica();
        IDatotekaProduct dp = dc.createProduct();
        System.out.println("Učitavanje sastava....");
        List<SastaviUtakmica> sviSastavi = (List<SastaviUtakmica>) dp.getConcreteProducts(datoteka);
        System.out.println("Učitavanje sastava završeno. ");
        return sviSastavi;
    }
    
    public List<Dogadaji> ucitajDagadaje(String datoteka){
        IDatotekaCreator dc = new DatotekaCreatorDogadaji();
        IDatotekaProduct dp = dc.createProduct();
        System.out.println("Učitavanje događaja....");
        List<Dogadaji> sviDogadaji = (List<Dogadaji>) dp.getConcreteProducts(datoteka);
        System.out.println("Učitavanje događaja završeno. ");
        return sviDogadaji;
    }
    
    
}
