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
import org.foi.uzdiz.ahip20_zadaca_3.Dogadaji;
import org.foi.uzdiz.ahip20_zadaca_3.Igraci;
import org.foi.uzdiz.ahip20_zadaca_3.NogometniSavez;
import org.foi.uzdiz.ahip20_zadaca_3.SastaviUtakmica;
import org.foi.uzdiz.ahip20_zadaca_3.Utakmice;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaProductDogadaji implements IDatotekaProduct {

    @Override
    public List<Dogadaji> getConcreteProducts(String fileName) {
        List<Dogadaji> dogadaji = new ArrayList<Dogadaji>();
        String line = "";
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            while ((line = br.readLine()) != null) {
                if(!provjeriBroj(line) || !odgovaraVrsta(line) || !odgovaraMin(line)) continue;
                Dogadaji d=napraviObjekt(line);
                if(d!=null) dogadaji.add(d);
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
        return dogadaji;

    }

    private Dogadaji napraviObjekt(String line) {
        String pom[] = line.split(";");
        Dogadaji d =null;
        switch (pom[2]) {
            case "1":
            case "2":
            case "3":
                d=obradi123(line);
                break;
            case "10":
            case "11":
                d=obradi1011(line);
                break;
            case "20":
                d=obradi20(line);
                break;
            case "0":
            case "99":
                d=obradi099(line);
                break;
            default:
        }

        return d;

    }

    private Dogadaji obradi123(String line) {
        String pom[] = line.split(";");
        if (pom.length != 5) {
            System.out.println("Greška. " + line + "Pogrešno uneseni  argumenti!");
            return null;
        }
        if (provjeriArgumente123(line) && igraoKlub(line) && igracIgraZaKlub(pom[3], pom[4], line)
                && bioUsastavu(line) && bioUIgri(line)) {
            Dogadaji d = new Dogadaji(Integer.parseInt(pom[0]), pom[1], Integer.parseInt(pom[2]),
                    pom[3], pom[4], "");
            return d;
        }
        return null;

    }

    private Dogadaji obradi1011(String line) {
        String pom[] = line.split(";");
        if (pom.length != 5) {
            System.out.println("Greška. " + line + "Pogrešno uneseni  argumenti!");
            return null;
        }
        if (provjeriArgumente1011(line) && igraoKlub(line) && igracIgraZaKlub(pom[3], pom[4], line)
                && bioUsastavu(line)) {
            Dogadaji d = new Dogadaji(Integer.parseInt(pom[0]), pom[1], Integer.parseInt(pom[2]),
                    pom[3], pom[4], "");
            return d;
        }
        return null;
    }

    private Dogadaji obradi20(String line) {
        String pom[] = line.split(";");
        if (pom.length != 6) {
            System.out.println("Greška. " + line + "Pogrešno uneseni  argumenti!");
            return null;
        }
        if (provjeriArgumente20(line) && igraoKlub(line) && igracIgraZaKlub(pom[3], pom[4], line) &&
                        igracIgraZaKlub(pom[3], pom[5], line) && bioUsastavu(line) && provjeriZamjenu(line)) {
            Dogadaji d = new Dogadaji(Integer.parseInt(pom[0]), pom[1], Integer.parseInt(pom[2]),
                    pom[3], pom[4], pom[5]);
            return d;
        }
        return null;
    }

    private Dogadaji obradi099(String line) {
        String pom[] = line.split(";");
        if (pom.length != 3) {
            System.out.println("Greška! "+line+" Pogrešno uneseni  argumenti!");
            return null;
        }
        if (provjeriArgumente099(line)) {
            Dogadaji d = new Dogadaji(Integer.parseInt(pom[0]), pom[1], Integer.parseInt(pom[2]),
                    "", "", "");
            return d;
        }
        return null;

    }

    private boolean igraoKlub(String line) {
        String pom[] = line.split(";");
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (u.broj == Integer.parseInt(pom[0]) && (u.domacin.equals(pom[3]) || u.gost.equals(pom[3]))) {
                return true;
            }
        }
        System.out.println("Greška. " + line + " Klub nije igrao ovu utakmicu!");
        return false;
    }

    private boolean bioUsastavu(String line) {
        String pom[] = line.split(";");
        for (SastaviUtakmica s : NogometniSavez.getInstance().getSastavi()) {
            if (s.broj == Integer.parseInt(pom[0]) && s.igrac.equals(pom[4]) && s.klub.equals(pom[3])) {
                return true;
            }
        }
        System.out.println("Greška! " + line + " Igrač nije bio u sastavu za ovu utakmicu!");
        return false;
    }

    private boolean provjeriZamjenu(String line) {
        String pom[] = line.split(";");
        boolean prvi = false;
        boolean drugi = false;
        for (SastaviUtakmica s : NogometniSavez.getInstance().getSastavi()) {
            if (s.broj == Integer.parseInt(pom[0]) && s.igrac.equals(pom[4])) {
                prvi = true; 
            }
            if (s.broj == Integer.parseInt(pom[0]) && s.igrac.equals(pom[5]) && s.klub.equals(pom[3])) {
                drugi = true;
            }
            if(prvi && drugi) return true;

        }
        System.out.println("Greška! " + line + " Igrač nije bio u sastavu za ovu utakmicu!");
        return false;
    }

    private boolean provjeriArgumente123(String line) {
         String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesea minuta događaja!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena vrsta događaja!");
            return false;
        }
        if (pom[3].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen klub!");
            return false;
        }
        if (pom[4].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen igrač!");
            return false;
        }
        return true;
    }

    private boolean provjeriArgumente1011(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesea minuta događaja!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena vrsta događaja!");
            return false;
        }
        if (pom[3].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen klub!");
            return false;
        }
        if (pom[4].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen igrač!");
            return false;
        }
        return true;
    }

    private boolean provjeriArgumente20(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesea minuta događaja!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena vrsta događaja!");
            return false;
        }
        if (pom[3].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen klub!");
            return false;
        }
        if (pom[4].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen igrač!");
            return false;
        }
        if (pom[5].equals("")) {
            System.out.println("Greška! " + line + " Nije unesen igrač zamjene!");
            return false;
        }
        return true;
    }

    private boolean provjeriArgumente099(String line) {
        String pom[] = line.split(";");
        if (pom[0].equals("")) {
            System.out.println("Greška! " + line + " Nije broj utakmice!");
            return false;
        }
        if (pom[1].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena minuta događaja!");
            return false;
        }
        if (pom[2].equals("")) {
            System.out.println("Greška! " + line + " Nije unesena vrsta događaja!");
            return false;
        }
        return true;
    }

    private boolean provjeriBroj(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m =p.matcher(pom[0]);
        if(!m.matches()) System.out.println("Greška! "+line+" Broj utakmice nije broj!");
        return m.matches();
    }

    private boolean odgovaraVrsta(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("^(0|1|2|3|10|11|20|99)");
        Matcher m =p.matcher(pom[2]);
        if(!m.matches()) System.out.println("Greška! "+line+" Neispravna vrsta događaja!");
        return m.matches();
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

    private boolean bioUIgri(String line) {
        String pom[]=line.split(";");
        int broj=Integer.parseInt(pom[0]);
        String min=pom[1];
        int minuta=odrediMinutu(pom[1]);
        //todo
        return true;        
    }

    private boolean odgovaraMin(String line) {
        String pom[]=line.split(";");
        Pattern p = Pattern.compile("^([0-9]{1,2}$|([0-9]{1,2}\\+?\\d$))");
        Matcher m =p.matcher(pom[1]);
        if(!m.matches()) System.out.println("Greška! "+line+" Neispravna minuta događaja!");
        return m.matches();
    }

    private int odrediMinutu(String sMin) {
        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m =p.matcher(sMin);
        if(m.matches()) return Integer.parseInt(sMin);
        else{
            String pom[]=sMin.split("\\+");
            int min=Integer.parseInt(pom[0]);
            int nadoknada=Integer.parseInt(pom[1]);
            return min+nadoknada;            
        }
    }

}
