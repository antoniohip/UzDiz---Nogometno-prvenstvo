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
import org.foi.uzdiz.ahip20_zadaca_3.Klubovi;
import org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez;
import org.foi.uzdiz.ahip20_zadaca_3.Utakmice;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaProductUtakmice implements IDatotekaProduct {

    @Override
    public List<Utakmice> getConcreteProducts(String fileName) {
        List<Utakmice> utakmice = new ArrayList<Utakmice>();
        String line = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            Utakmice u = null;
            while ((line = br.readLine()) != null) {
                String pom[] = line.split(";");
                if (pom.length != 5) {
                    System.out.println("Greska. "+line+" Nisu upisani svi argumenti!");
                    continue;
                }
                if(!koloJeBroj(line)) continue;
                if (idJeBroj(line) && nisuPrazniArgumenti(line) && postojiId(pom[0], utakmice, line)
                        && jednoKolo(pom[1], pom[2], pom[3], utakmice, line)
                        && postojiKlub(pom[2], pom[3], utakmice, line) 
                        && neIgraSamSaSobom(pom[2], pom[3], line)
                        && odgovaraDatum(pom[4], line)) {
                    Date vrijeme = srediVrijeme(pom[4]);
                    u = new Utakmice(Integer.parseInt(pom[0]), Integer.parseInt(pom[1]),
                             pom[2], pom[3], vrijeme);
                    utakmice.add(u);
                }
                
            }
            br.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"+ fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");

        } catch (ParseException ex) {
            Logger.getLogger(DatotekaProductUtakmice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utakmice;

    }

    private Date srediVrijeme(String pocetak) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.parse(pocetak);

    }

    private boolean nisuPrazniArgumenti(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj kola!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen domaćin!");
            return false;
        }
        if (pom[3].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen gost!");
            return false;
        }
        if (pom[4].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen početak!");
            return false;
        }
        return true;
    }

    private boolean postojiId(String broj, List<Utakmice> utakmice, String line) {
        for (Utakmice u : utakmice) {
            if (u.broj == Integer.parseInt(broj)) {
                System.out.println("Greška! " + line + " Broj utakmice već postoji");
                return false;
            }
        }
        return true;
    }

    private boolean jednoKolo(String kolo, String domacin, String gost, List<Utakmice> utakmice, String line) {
        for (Utakmice u : utakmice) {
            if (u.kolo == Integer.parseInt(kolo) && ((u.domacin.equals(domacin) || u.domacin.equals(gost))
                    || (u.gost.equals(domacin) || u.gost.equals(gost)))) {
                System.out.println("Greška! "+line+" Klub ne može igrati dvije utakmice u jednom kolu!");
                return false;
            }
        }
        return true;
    }

    private boolean postojiKlub(String domacin, String gost, List<Utakmice> utakmice, String line) {
        boolean prvi = false;
        boolean drugi = false;
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            if (domacin.equals(k.klub)) {
                prvi = true;
            }
            if (gost.equals(k.klub)) {
                drugi = true;
            }
            if (prvi && drugi) {
                return true;
            }
        }
        if (!prvi) {
            System.out.println("Greška! " + line + " Ne postoji klub " + domacin);
        }
        if (!drugi) {
            System.out.println("Greška! " + line + " Ne postoji klub " + gost);
        }
        return false;
    }

    private boolean neIgraSamSaSobom(String domacin, String gost, String line) {
        if (domacin.equals(gost)) {
            System.out.println("Greška! " + line + " Klub ne može igrati sam sa sobom!");
            return false;
        }
        return true;
    }

    private boolean odgovaraDatum(String string, String line) {
        Pattern p = Pattern.compile("^([1-9]|([012][0-9])|(3[01])).([0]{0,1}[1-9]|1[012])."
                + "\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$");
        Matcher m = p.matcher(string);
        if (!m.matches()) {
            System.out.println("Greška! " + line + " Ne odgovara format datuma!");
        }
        return m.matches();
    }

    private boolean idJeBroj(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m =p.matcher(pom[0]);
        if(!m.matches()) System.out.println("Greška! "+line+" Broj utakmice nije broj!");
        return m.matches();
    }

    private boolean koloJeBroj(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m =p.matcher(pom[1]);
        if(!m.matches()) System.out.println("Greška! "+line+" Kolo utakmice nije broj!");
        return m.matches();
    }

}
