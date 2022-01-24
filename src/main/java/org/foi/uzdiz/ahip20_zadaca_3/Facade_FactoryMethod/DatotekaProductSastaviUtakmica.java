/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Facade_FactoryMethod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.ahip20_zadaca_3.Igraci;
import org.foi.uzdiz.ahip20_zadaca_3.Klubovi;
import org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez;
import org.foi.uzdiz.ahip20_zadaca_3.SastaviUtakmica;
import org.foi.uzdiz.ahip20_zadaca_3.Utakmice;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaProductSastaviUtakmica implements IDatotekaProduct {

    @Override
    public List<SastaviUtakmica> getConcreteProducts(String fileName) {
        List<SastaviUtakmica> sastavi = new ArrayList<>();
        String line = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            SastaviUtakmica s = null;
            while ((line = br.readLine()) != null) {
                String pom[]=line.split(";");
                if(pom.length!=5){
                    System.out.println("Greska! "+line+ "Nisu uneseni svi argumenti!");
                    continue;
                }
                if(!provjeriBroj(line) || !provjeriVrstu(line)) continue;
                if(nisuPrazniArgumenti(line) && klubIgraoUtakmicu(pom[0],pom[1],line) && 
                        igracIgraZaKlub(pom[1], pom[3],line) && provjeriBroj(line)){
                   s = new SastaviUtakmica(Integer.parseInt(pom[0]), pom[1], pom[2], pom[3], pom[4]);
                   sastavi.add(s);
                }
            }
            br.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");

        }
        
        return sastavi;

    }
    
        private boolean nisuPrazniArgumenti(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen klub!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesea vrsta!");
            return false;
        }
        if (pom[3].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen igrač!");
            return false;
        }
        if (pom[4].equals("")) {
            System.out.println("Greška! " + line + " Nije unesea pozicija!");
            return false;
        }
        return true;
    }

    private boolean klubIgraoUtakmicu(String broj, String klub, String line) {
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if(u.broj==Integer.parseInt(broj)){
                if(!(klub.equals(u.domacin) || klub.equals(u.gost))){
                    System.out.println("Greška! "+line+" Klub nije igrao ovu utakmicu!");
                    return false;
                }
            }            
        }
        return true;
    }

    private boolean igracIgraZaKlub(String klub, String igrac, String line) {
        
       for(Igraci i : NogometniSavez.getInstance().getIgraci()){
           if(i.ime.equals(igrac) && i.klub.equals(klub)){
               return true;
           }
       }
        System.out.println("Greška! "+line+" Igrač ne igra za ovaj klub!");
        return false;
    }

    private boolean provjeriBroj(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m =p.matcher(pom[0]);
        if(!m.matches()) System.out.println("Greška! "+line+" Broj utakmice nije broj!");
        return m.matches();
    }

    private boolean provjeriVrstu(String line) {
        String pom[]=line.split(";");
        if(pom[2].equals("S") || pom[2].equals("P")) return true;
        else{
            System.out.println("Greska! "+line+" Ne odgovara vrsta!");
            return false;
        }
    }

    

}
