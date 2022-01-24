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
import org.foi.uzdiz.ahip20_zadaca_3.Klubovi;
import org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez;
import org.foi.uzdiz.ahip20_zadaca_3.Treneri;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaProductKlubovi implements IDatotekaProduct {

    @Override
    public List<Klubovi> getConcreteProducts(String fileName) {
        List<Klubovi> klubovi = new ArrayList<Klubovi>();
        List<Treneri> treneri = new ArrayList<>();
        String line = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            Klubovi k = null;
            Treneri t = null;
            while ((line = br.readLine()) != null) {
                String pom[] = line.split(";");
                if (pom.length != 3) {
                    System.out.println("Za red " + line + " nisu unjeti svi argumenti, preskacem");                    
                    continue;
                }
                if(prazniArgument(line)) continue;
                if (provjeriSveArgumente(line, klubovi) && provjeriPostojanostKluba(line, klubovi)
                        && provjeriTrenera(line, klubovi) && provjeriOznakuKluba(line)) {
                    k = new Klubovi(pom[0], pom[1], pom[2]);
                    klubovi.add(k); 
                    t = new Treneri(pom[2], pom[0]);
                    treneri.add(t);
                }             
                                
            }
            NogometniSavez.getInstance().setTreneri(treneri);
            br.close();            
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");
        }
        return klubovi;
    }
    
    private boolean provjeriSveArgumente(String line, List<Klubovi> klubovi) {
        String pom[] = line.split(";");
        if (pom.length == 3) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean provjeriPostojanostKluba(String line, List<Klubovi> klubovi) {
        String pom[] = line.split(";");
        
        for (Klubovi k : klubovi) {
            if (k.klub.equals(pom[0])) {
                System.out.println("Greška! "+line + ". Klub s ovom oznakom vec postoji! Preskacem!");
                return false;
            }  
            
        }
        return true;        
    }
    
    private boolean provjeriTrenera(String line, List<Klubovi> klubovi) {
         String pom[] = line.split(";");
        
        for (Klubovi k : klubovi) {
            if (k.trener.equals(pom[2])) {
                System.out.println("Greška! "+line+". Ovaj trener"
                        + " vec trenira drugi klub! Preskacem!");
                return false;
            }  
            
        }
        return true;        
    }

    private boolean prazniArgument(String line) {
        String pom[]=line.split(";");
        if(pom[0].equals("")){
            System.out.println("Greška! "+line+" Nije unesena oznaka kluba!");
            return true;
        }
        if(pom[1].equals("")){
            System.out.println("Greška! "+line+" Nije unesen naziv kluba!");
            return true;
        }
        if(pom[2].equals("")){
            System.out.println("Greška! "+line+" Nije unesen trener kluba!");
            return true;
        }
        return false;
    }

    private boolean provjeriOznakuKluba(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("[A-ZŠĐČĆŽ]+$");
        Matcher m =p.matcher(pom[0]);
        if(!m.matches()) System.out.println("Greška! "+line+" Ozanka kluba ne odgovara formatu!");
        return m.matches();
    }
    
}
