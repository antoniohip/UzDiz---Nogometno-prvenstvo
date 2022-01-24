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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.ahip20_zadaca_3.Igraci;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaProductIgraci implements IDatotekaProduct {

    @Override
    public List<Igraci> getConcreteProducts(String fileName) {
        List<Igraci> igraci = new ArrayList<Igraci>();
        String line = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            Igraci i = null;
            while ((line = br.readLine()) != null) {
                String pom[] = line.split(";");
                if (pom.length != 4) {
                    System.out.println("Greška! " + line + " Nisu unjeti svi argumenti, preskacem");
                    continue;
                }
                if (!provjeriDatum(pom[3])) {
                    System.out.println("Greška! " + line + " Ne odgovara format datuma!");
                    continue;
                }
                Date roden = srediDatum(pom[3]);
                if (prazniArgument(line)) continue;
                
                if (imeOK(line)) {
                    List<String> pozicije = ucitajPozicije(pom[2]);
                    i = new Igraci(pozicije, roden, pom[1], pom[0]);
                    igraci.add(i);
                }
            }
            br.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");

        } catch (ParseException ex) {
            Logger.getLogger(DatotekaProductIgraci.class.getName()).log(Level.SEVERE, null, ex);
        }
        return igraci;

    }

    private boolean prazniArgument(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena oznaka kluba!");
            return true;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije uneseo ime igrača!");
            return true;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena pozicija igrača!");
            return true;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen datum rođenja igrača!");
            return true;
        }
        return false;
    }

    private List<String> ucitajPozicije(String pozicije) {
        List<String> svePozicije = new ArrayList<>();
        String pom[] = pozicije.split(",");
        for (String p : pom) {
            svePozicije.add(p);
        }
        return svePozicije;
    }

    private boolean provjeriDatum(String string) {
        Pattern p = Pattern.compile("^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])"
                + "\\.((?:19|20)\\d{2})\\s*.$");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    private Date srediDatum(String datum) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        return formatter.parse(datum);
    }


        
    

    private boolean imeOK(String line) {
        String pom[]=line.split(";");
        String prvi=pom[1].substring(0, 1);
        String zadnji=pom[1].substring(pom[1].length()-1);
        if(prvi.equals(" ") || zadnji.equals(" ")){
            System.out.println("Greška. "+line+" Ime igrača ne može započeti ili završiti razmakom");
            return false;
        }
        return true;
    }

}
