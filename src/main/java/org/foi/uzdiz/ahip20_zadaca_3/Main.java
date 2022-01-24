/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.Autogol;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.Crveni;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.ProvjeriDogadaj;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.Branici;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.BrojIgracaPozicije;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.Golmani;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.Pozicije;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.Napadaci;
import org.foi.uzdiz.ahip20_zadaca_3.Decorator.Vezni;
import org.foi.uzdiz.ahip20_zadaca_3.Facade_FactoryMethod.FacadeUcitavanje;
import org.foi.uzdiz.ahip20_zadaca_3.Observer.SemaforObserver;
import org.foi.uzdiz.ahip20_zadaca_3.Observer.Subject;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.Zuti;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.Gol;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.GolPenal;
import org.foi.uzdiz.ahip20_zadaca_3.ChainOfResponsibility.Zamjena;

/**
 *
 * @author NWTiS_2
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String argumenti = "";
        for (String arg : args) {
            argumenti += arg + " ";

        }
        if (argumenti == "") {
            System.out.println("Nisu unijeti argumenti");
            return;
        }
        argumenti = argumenti.substring(0, argumenti.length() - 1);
        if (!ispravniArgumenti(argumenti)) {
            return;
        }
        String postojeDatoteke = postojeDatoteke(argumenti);
        if (!postojeDatoteke.equals("")) {
            System.out.println("Ne postoji datoteka: " + postojeDatoteke);
            return;
        }
        Inicijaliziraj(argumenti);
        int prekid = 0;
        while (prekid == 0) {
            Scanner unos = new Scanner(System.in);
            System.out.println("Upišite naredbu: (Za kraj unesite 0)");;
            String naredba = unos.nextLine();
            if (naredba.equals("0")) {
                return;
            }
            if (provjeriNaredbu(naredba)) {
                naredbaManager(naredba);
            } else {
                System.out.println("Niste ispravno unjeli naredbu!");
            }
        }

    }

    private static void Inicijaliziraj(String argumenti) {
        FacadeUcitavanje ucitavanje = new FacadeUcitavanje();

        List<Klubovi> sviKlubovi = ucitavanje.ucitajKlubove(dajDatoteku("-k", argumenti));
        NogometniSavez.getInstance().setKlubovi(sviKlubovi);

        List<Igraci> sviIgraci = ucitavanje.ucitajIgrace(dajDatoteku("-i", argumenti));
        NogometniSavez.getInstance().setIgraci(sviIgraci);

        if (argumenti.contains("-u")) {
            List<Utakmice> sveUtakmice = ucitavanje.ucitajUtakmice(dajDatoteku("-u", argumenti));
            NogometniSavez.getInstance().setUtakmice(sveUtakmice);
        }
        if (argumenti.contains("-s")) {
            List<SastaviUtakmica> sviSastavi = ucitavanje.ucitajSastave(dajDatoteku("-s", argumenti));
            NogometniSavez.getInstance().setSastavi(sviSastavi);
        }
        if (argumenti.contains("-d")) {
            List<Dogadaji> sviDogadaji = ucitavanje.ucitajDagadaje(dajDatoteku("-d", argumenti));
            NogometniSavez.getInstance().setDogadaji(sviDogadaji);
        }

        System.out.println("Podaci ucitani!");
    }

    public static String dajDatoteku(String oznaka, String argumenti) {
        String pom[] = argumenti.split(" ");
        for (int i = 0; i < pom.length; i++) {
            if (pom[i].equals(oznaka)) {
                return pom[i + 1];
            }
        }
        return null;
    }

    private static void naredbaManager(String naredba) {
        String pom[] = naredba.split(" ");
        if (pom[0].equals("T") || pom[0].equals("S") || pom[0].equals("K") || pom[0].equals("R")) {
            if (!provjeriPodatke()) {
                return;
            }
        }
        switch (pom[0]) {
            case "T":
                obrisiPodatkeLjestvice();
                if (pom.length == 1) {
                    kreirajLjestvicu("");
                } else {
                    //if (!provjeriKolo(pom[1])) {
                    //break;
                    //}
                    kreirajLjestvicu(pom[1]);
                }
                ispisiLjestvicu();
                break;
            case "S":
                obrisiGoloveIgracima();
                if (pom.length == 1) {
                    kreirajLjestvicuStrijelaca("");
                } else {
                    //if (!provjeriKolo(pom[1])) {
                    //     break;
                    //}
                    kreirajLjestvicuStrijelaca(pom[1]);
                }
                ispisiLjestvicuStrijelaca();
                break;
            case "K":
                obrisiKartoneKlubovima();
                if (pom.length == 1) {
                    kreirajLjestvicuKartona("");
                } else {
                    //if (!provjeriKolo(pom[1])) {
                    //break;
                    //}
                    kreirajLjestvicuKartona(pom[1]);
                }
                ispisiLjestvicuKartona();
                break;
            case "R":
                if (!postojiKlub(pom[1])) {
                    System.out.println("Ne postoji klub " + pom[1]);
                    break;
                }
                List<RezultatUtakmica> rezultati = new ArrayList<>();
                if (pom.length == 2) {
                    rezultati = ispisRezultataZaKlub(pom[1], "");
                } else {
                    //if (!provjeriKolo(pom[2])) {
                    //break;
                    //}
                    rezultati = ispisRezultataZaKlub(pom[1], pom[2]);
                }
                ispisiRezultateKluba(rezultati);
                break;
            case "NU":
            case "ND":
            case "NS":
                naknadnoUcitavanje(naredba);
                break;
            case "D":
                ispisujSemafor(naredba);
                break;
            case "DEC":
                decorator(naredba);
                break;
            case "SU":

                sastaviPrijeIPoslije(naredba);
                break;
        }
    }

    private static void kreirajLjestvicu(String sKolo) {
        boolean svaKola = false;
        int kolo = 0;
        if (sKolo.equals("")) {
            svaKola = true;
        } else {
            kolo = Integer.parseInt(sKolo);
        }
        Klubovi domacin = null;
        Klubovi gost = null;
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (!svaKola && u.kolo > kolo) {
                break;
            }
            boolean postoji = false;
            for (Dogadaji dog : NogometniSavez.getInstance().getDogadaji()) {
                if (dog.broj == u.broj) {
                    postoji = true;
                }
            }
            if (postoji == false) {
                continue;
            }
            domacin = dohvatiKlubove(u.domacin);
            gost = dohvatiKlubove(u.gost);
            obradiGoloveIIshod(domacin, gost, u);

        }
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            int bodovi = (k.brojPobjeda * 3) + (k.brojNerješenih * 1);
            k.bodovi = bodovi;
            k.razlika = k.brojDanihgolova - k.brojPrimljenihGolova;
        }

    }

    private static void obradiGoloveIIshod(Klubovi domacin, Klubovi gost, Utakmice u) {
        int brojGolovaDomacina = 0;
        int brojGolovaGosta = 0;
        for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
            if (d.broj == u.broj) {
                //gol
                if (d.vrsta == 1 || d.vrsta == 2) {
                    if (d.klub.equals(domacin.klub)) {
                        brojGolovaDomacina++;
                        domacin.brojDanihgolova++;
                        gost.brojPrimljenihGolova++;
                    }
                    if (d.klub.equals(gost.klub)) {
                        brojGolovaGosta++;
                        gost.brojDanihgolova++;
                        domacin.brojPrimljenihGolova++;
                    }
                }
                if (d.vrsta == 3) {
                    if (d.klub.equals(domacin.klub)) {
                        brojGolovaGosta++;
                        domacin.brojPrimljenihGolova++;
                        gost.brojDanihgolova++;
                    }
                    if (d.klub.equals(gost.klub)) {
                        brojGolovaDomacina++;
                        domacin.brojDanihgolova++;
                        gost.brojPrimljenihGolova++;
                    }
                }
            }

        }
        domacin.brojKola++;
        gost.brojKola++;

        if (brojGolovaDomacina > brojGolovaGosta) {
            domacin.brojPobjeda++;
            gost.brojPoraza++;
        } else if (brojGolovaDomacina < brojGolovaGosta) {
            domacin.brojPoraza++;
            gost.brojPobjeda++;
        } else {
            domacin.brojNerješenih++;
            gost.brojNerješenih++;
        }
    }

    private static Klubovi dohvatiKlubove(String oznakaKluba) {
        return NogometniSavez.getInstance().getKlubovi().stream()
                .filter(klub -> oznakaKluba.equals(klub.klub))
                .findAny().orElse(null);
    }

    private static void obrisiPodatkeLjestvice() {
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            k.setBodovi(0);
            k.setBrojDanihgolova(0);
            k.setBrojKola(0);
            k.setBrojNerješenih(0);
            k.setBrojPobjeda(0);
            k.setBrojPoraza(0);
            k.setBrojPrimljenihGolova(0);
        }
    }

    private static void ispisiLjestvicu() {
        Comparator<Klubovi> sortiraj = Comparator.comparing(Klubovi::getBodovi)
                .thenComparing(Klubovi::getRazlika).thenComparing(Klubovi::getBrojDanihgolova)
                .thenComparing(Klubovi::getBrojPobjeda);
        List<Klubovi> sortirano = NogometniSavez.getInstance().getKlubovi().stream().
                sorted(sortiraj.reversed()).collect(Collectors.toList());
        final Object[][] table = new String[sortirano.size() + 2][];
        table[0] = new String[]{"Klub", "Naziv", "Trener", "Odigranih", "Pobjeda",
            "Nerješeno", "Poraz", "Dao", "Primio", "Razlika", "Bodovi"};
        int brojac = 0;
        int sumaOdigranih = 0;
        int sumaPobjeda = 0;
        int sumaNerjeseno = 0;
        int sumaPoraz = 0;
        int sumaBodova = 0;
        int sumaDao = 0;
        int sumaPrimio = 0;
        for (Klubovi k : sortirano) {
            brojac++;
            table[brojac] = new String[]{k.klub, k.naziv, k.trener, String.valueOf(k.brojKola),
                String.valueOf(k.brojPobjeda), String.valueOf(k.brojNerješenih),
                String.valueOf(k.brojPoraza), String.valueOf(k.brojDanihgolova),
                String.valueOf(k.brojPrimljenihGolova), String.valueOf(k.razlika),
                String.valueOf(k.bodovi)};
            sumaBodova += k.bodovi;
            sumaDao += k.brojDanihgolova;
            sumaPrimio += k.brojPrimljenihGolova;
            sumaOdigranih += k.brojKola;
            sumaPobjeda += k.brojPobjeda;
            sumaNerjeseno += k.brojNerješenih;
            sumaPoraz += k.brojPoraza;
        }
        table[++brojac] = new String[]{"ZBROJ", "", "", String.valueOf(sumaOdigranih), String.valueOf(sumaPobjeda),
            String.valueOf(sumaNerjeseno), String.valueOf(sumaPoraz), String.valueOf(sumaDao),
            String.valueOf(sumaPrimio), "", String.valueOf(sumaBodova)};
        for (final Object[] row : table) {
            System.out.format("%-7s%-25s%-20s%10s%10s%10s%10s%10s%10s%10s%10s%n", row);
        }

    }

    private static void obrisiGoloveIgracima() {
        for (Igraci i : NogometniSavez.getInstance().getIgraci()) {
            i.setGolovi(0);
        }
    }

    private static void kreirajLjestvicuStrijelaca(String sKolo) {
        boolean svaKola = false;
        int kolo = 0;
        if (sKolo.equals("")) {
            svaKola = true;
        } else {
            kolo = Integer.parseInt(sKolo);
        }
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (!svaKola && u.kolo > kolo) {
                break;
            }
            for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
                if (u.broj == d.broj) {
                    if (d.vrsta == 1 || d.vrsta == 2) {
                        for (Igraci i : NogometniSavez.getInstance().getIgraci()) {
                            if (i.ime.equals(d.imeIgraca)) {
                                i.golovi++;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void ispisiLjestvicuStrijelaca() {
        List<Igraci> sortirano = NogometniSavez.getInstance().getIgraci().stream().
                sorted(Comparator.comparingInt(Igraci::getGolovi).reversed())
                .collect(Collectors.toList());
        List<Igraci> imajuGol = new ArrayList<>();
        for (Igraci i : sortirano) {
            if (i.golovi > 0) {
                imajuGol.add(i);
            }
        }
        final Object[][] table = new String[imajuGol.size() + 2][];
        table[0] = new String[]{"Igrač", "Klub", "Ime kluba", "Golovi"};
        int brojac = 0;
        int sumaGola = 0;
        for (Igraci i : imajuGol) {
            brojac++;
            sumaGola += i.golovi;
            Klubovi k = dohvatiKlubove(i.klub);
            table[brojac] = new String[]{i.ime, i.klub, k.naziv,
                String.valueOf(i.golovi)};
        }
        table[++brojac] = new String[]{"ZBROJ", "", "", String.valueOf(sumaGola)};
        for (final Object[] row : table) {
            System.out.format("%-25s%-7s%-25s%15s%n", row);
        }
    }

    private static void obrisiKartoneKlubovima() {
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            k.setBrojZutihKartona(0);
            k.setBrojDrugihZutihKartona(0);
            k.setBrojCrvenihKartona(0);
            k.setUkupnoKartona(0);
        }
    }

    private static void kreirajLjestvicuKartona(String sKolo) {
        boolean svaKola = false;
        int kolo = 0;
        if (sKolo.equals("")) {
            svaKola = true;
        } else {
            kolo = Integer.parseInt(sKolo);
        }
        Klubovi domacin = null;
        Klubovi gost = null;
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (!svaKola && u.kolo > kolo) {
                break;
            }
            domacin = dohvatiKlubove(u.domacin);
            gost = dohvatiKlubove(u.gost);
            obradiKartone(domacin, gost, u);
        }
    }

    private static void obradiKartone(Klubovi domacin, Klubovi gost, Utakmice u) {
        String igrac;
        for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
            if (d.broj == u.broj) {
                //zuti
                if (d.vrsta == 10) {
                    if (d.klub.equals(domacin.klub)) {
                        int brojac = 0;
                        igrac = d.imeIgraca;
                        domacin.brojZutihKartona++;
                        domacin.ukupnoKartona++;
                        for (Dogadaji drugi : NogometniSavez.getInstance().getDogadaji()) {
                            if (d.broj == u.broj && drugi.vrsta == 10 && igrac.equals(drugi.imeIgraca)) {
                                brojac++;
                                if (brojac == 2) {
                                    domacin.brojDrugihZutihKartona++;
                                    break;
                                }
                            }
                        }

                    }
                    if (d.klub.equals(gost.klub)) {
                        int brojac = 0;
                        gost.brojZutihKartona++;
                        gost.ukupnoKartona++;
                        igrac = d.imeIgraca;
                        for (Dogadaji drugi : NogometniSavez.getInstance().getDogadaji()) {
                            if (d.broj == u.broj && drugi.vrsta == 10 && igrac.equals(drugi.imeIgraca)) {
                                brojac++;
                            }
                        }
                        if (brojac == 2) {
                            gost.brojDrugihZutihKartona++;
                        }
                    }
                }
                if (d.vrsta == 11) {
                    if (d.klub.equals(domacin.klub)) {
                        domacin.brojCrvenihKartona++;
                        domacin.ukupnoKartona++;
                    }
                    if (d.klub.equals(gost.klub)) {
                        gost.brojCrvenihKartona++;
                        gost.ukupnoKartona++;
                    }
                }
            }
        }
    }

    private static void ispisiLjestvicuKartona() {
        Comparator<Klubovi> sortiraj = Comparator.comparing(Klubovi::getUkupnoKartona)
                .thenComparing(Klubovi::getBrojCrvenihKartona)
                .thenComparing(Klubovi::getBrojDrugihZutihKartona);

        List<Klubovi> sortirano = NogometniSavez.getInstance().getKlubovi().stream().
                sorted(sortiraj.reversed())
                .collect(Collectors.toList());
        final Object[][] table = new String[sortirano.size() + 2][];
        table[0] = new String[]{"Klub", "Naziv", "Žuti", "Drugi žuti", "Crveni", "Ukupno"};
        int brojac = 0;
        int sumaZuti = 0;
        int sumaDrugiZuti = 0;
        int sumaCrveni = 0;
        int sumaUkupno = 0;
        for (Klubovi k : sortirano) {
            brojac++;
            table[brojac] = new String[]{k.klub, k.naziv, String.valueOf(k.brojZutihKartona),
                String.valueOf(k.brojDrugihZutihKartona / 2), String.valueOf(k.brojCrvenihKartona),
                String.valueOf(k.ukupnoKartona)};
            sumaZuti += k.brojZutihKartona;
            sumaDrugiZuti += k.brojDrugihZutihKartona;
            sumaCrveni += k.brojCrvenihKartona;
            sumaUkupno += k.ukupnoKartona;
        }
        table[++brojac] = new String[]{"ZBROJ", "", String.valueOf(sumaZuti), String.valueOf(sumaDrugiZuti / 2),
            String.valueOf(sumaCrveni), String.valueOf(sumaUkupno)};

        for (final Object[] row : table) {
            System.out.format("%-5s%-25s%10s%15s%10s%10s%n", row);
        }
    }

    private static List<RezultatUtakmica> ispisRezultataZaKlub(String klub, String sKolo) {
        boolean svaKola = false;
        int kolo = 0;
        if (sKolo.equals("")) {
            svaKola = true;
        } else {
            kolo = Integer.parseInt(sKolo);
        }
        List<RezultatUtakmica> listaRu = new ArrayList<>();
        RezultatUtakmica ru;
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (!svaKola && u.kolo > kolo) {
                break;
            }
            boolean postoji = false;
            for (Dogadaji dog : NogometniSavez.getInstance().getDogadaji()) {
                if (dog.broj == u.broj) {
                    postoji = true;
                }
            }
            if (u.domacin.equals(klub) || u.gost.equals(klub)) {
                if (postoji) {
                    ru = new RezultatUtakmica(u, 0, 0, true);
                } else {
                    ru = new RezultatUtakmica(u, 0, 0, false);
                    listaRu.add(ru);
                    break;
                }
                for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
                    if (u.broj == d.broj) {
                        if (d.vrsta == 1 || d.vrsta == 2) {
                            if (d.klub.equals(u.domacin)) {
                                ru.goloviDomacina++;
                            } else {
                                ru.goloviGosta++;
                            }
                        }
                        if (d.vrsta == 3) {
                            if (d.klub.equals(u.domacin)) {
                                ru.goloviGosta++;
                            } else {
                                ru.goloviDomacina++;
                            }
                        }
                    }
                }
                listaRu.add(ru);
            }
        }
        return listaRu;
    }

    private static void ispisiRezultateKluba(List<RezultatUtakmica> rezultati) {

        final Object[][] table = new String[rezultati.size() + 1][];
        table[0] = new String[]{"Kolo", "Datum i vrijeme", "Domacin", "Gost", "Rezultat"};
        int brojac = 0;
        String datum = "";
        Klubovi domacin;
        Klubovi gost;
        String rezultat = "";
        for (RezultatUtakmica r : rezultati) {
            brojac++;
            datum = formatirajDatum(r.utakmica.pocetak);
            domacin = dohvatiKlubove(r.utakmica.domacin);
            gost = dohvatiKlubove(r.utakmica.gost);
            int maxRazmak = 99;
            if (domacin.naziv.length() < maxRazmak) {
                maxRazmak = domacin.naziv.length();
            }
            if (gost.naziv.length() < maxRazmak) {
                maxRazmak = gost.naziv.length();
            }
            if (r.odigrana) {
                rezultat = String.valueOf(r.goloviDomacina) + ":" + String.valueOf(r.goloviGosta);
            } else {
                rezultat = "-:-";
            }
            table[brojac] = new String[]{String.valueOf(r.utakmica.kolo), datum,
                domacin.naziv, gost.naziv, rezultat};
        }
        for (final Object[] row : table) {
            System.out.format("%-5s%-20s%-25s%-25s%10s%n", row);
        }
    }

    private static String formatirajDatum(Date pocetak) {
        DateFormat df = new SimpleDateFormat("dd.MM.YYYY HH:mm");
        return df.format(pocetak);

    }

    private static Boolean provjeriNaredbu(String naredba) {
        Pattern p = Pattern.compile("^((T)$|(T [0-9]+$)|((S)$|(S [0-9]+$))|"
                + "((K)$|(K [0-9]+$))|((R)[A-ZŠĐČĆŽ]+)$|((R) [A-ZŠĐČĆŽ]+ [0-9]+$)|"
                + "((NU|ND|NS) ([A-z]|[0-9]|[-_])*.csv)$)|((D )[0-9]+ [A-ZŠĐČĆŽ]+ "
                + "[A-ZŠĐČĆŽ]+ [0-9]+$)|((DEC)$)|(DEC ([N,G,V,B])+$)|(SU [0-9]+ [A-ZŠĐČĆŽ]+ "
                + "[A-ZŠĐČĆŽ]+$)");
        Matcher m = p.matcher(naredba);
        return m.matches();
    }

    private static void dajIgrace() {
        List<Igraci> sviIgraci = NogometniSavez.getInstance().getIgraci();
        for (Igraci i : sviIgraci) {
            System.out.println(i.klub + " " + i.ime + " " + i.pozicije + " " + i.roden);

        }
    }

    private static void dajDogadaje() {
        for (Dogadaji dog : NogometniSavez.getInstance().getDogadaji()) {
            System.out.println(dog.broj + " " + dog.min + " " + dog.vrsta
                    + " " + dog.klub + " " + dog.imeIgraca + " " + dog.imeIgracaZamjena);

        }

    }

    private static void dajKlubove() {
        List<Klubovi> sviKlubovi = NogometniSavez.getInstance().getKlubovi();
        for (Klubovi k : sviKlubovi) {
            System.out.println(k.klub + " " + k.naziv + " " + k.trener);
        }
    }

    private static void dajSastave() {
        List<SastaviUtakmica> sviSastavi = NogometniSavez.getInstance().getSastavi();
        for (SastaviUtakmica s : sviSastavi) {
            System.out.println(s.broj + " " + s.klub + " " + s.vrsta + " " + s.igrac + " " + s.pozicija);
        }
    }

    private static void dajTrenere() {
        List<Treneri> treneri = NogometniSavez.getInstance().getTreneri();
        for (Treneri t : treneri) {
            System.out.println(t.ime + " " + t.klub);
        }
    }

    private static void dajUtakmice() {
        List<Utakmice> sveUtakmice = NogometniSavez.getInstance().getUtakmice();
        for (Utakmice u : sveUtakmice) {
            System.out.println(u.broj + " " + u.kolo + " " + u.domacin + " " + u.gost + " " + u.pocetak);
        }
    }

    private static boolean postojiKlub(String klub) {
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            if (k.klub.equals(klub)) {
                return true;
            }
        }
        return false;
    }

    private static boolean ispravniArgumenti(String argumenti) {
        String poruka = "";
        String pom[] = argumenti.split(" ");
        Boolean provjera = true;
        if (!(pom.length == 4 || pom.length == 6 || pom.length == 8 || pom.length == 10)) {
            poruka = "Netočan broj argumenata!";
            provjera = false;
        }
        if (!(argumenti.contains("-i") && argumenti.contains("-k"))) {
            poruka = "Nisu unijeti argumenti za igrace i klubove";
            provjera = false;
        }
        Pattern p = Pattern.compile("^((((-k)|(-i)|(-u)|(-s)|(-d)) ([A-z]|[0-9]|[-_])*.csv ){1,4}"
                + "((-k)|(-i)|(-u)|(-s)|(-d)) ([A-z]|[0-9]|[-_])*.csv)$");
        Matcher m = p.matcher(argumenti);
        if (!m.matches()) {
            System.out.println("Ne odgovara format argumenata!!");
            provjera = false;
        }

        if (!provjera) {
            System.out.println(poruka);
        }
        return provjera;
    }

    private static String postojeDatoteke(String argumenti) {
        String igraci = "";
        String utakmice = "";
        String sastavi = "";
        String dogadaji = "";
        String klubovi = "";
        List<String> datoteke = new ArrayList<>();
        if (argumenti.contains("-i")) {
            igraci = dajDatoteku("-i", argumenti);
            datoteke.add(igraci);
        }
        if (argumenti.contains("-u")) {
            utakmice = dajDatoteku("-u", argumenti);
            datoteke.add(utakmice);
        }
        if (argumenti.contains("-s")) {
            sastavi = dajDatoteku("-s", argumenti);
            datoteke.add(sastavi);
        }
        if (argumenti.contains("-d")) {
            dogadaji = dajDatoteku("-d", argumenti);
            datoteke.add(dogadaji);
        }
        if (argumenti.contains("-k")) {
            klubovi = dajDatoteku("-k", argumenti);
            datoteke.add(klubovi);
        }

        String nepostojeceDatoteke = "";
        for (String d : datoteke) {
            File f = new File(d);
            if (!f.exists()) {
                nepostojeceDatoteke += d + " ";
            }
        }
        return nepostojeceDatoteke;
    }

    private static boolean provjeriKolo(String sKolo) {
        int kolo = Integer.parseInt(sKolo);
        int max = 0;
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            boolean postoji = false;
            for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
                if (u.broj == d.broj) {
                    postoji = true;
                }
            }
            if ((u.kolo > max) && postoji) {
                max = u.kolo;
            }
        }
        if (kolo > max) {
            System.out.println("Greška. Odigrano je samo " + max + " kola.");
            return false;
        }
        return true;
    }

    private static boolean provjeriPodatke() {
        if (NogometniSavez.getInstance().getDogadaji() != null && NogometniSavez.getInstance().getSastavi() != null
                && NogometniSavez.getInstance().getDogadaji() != null) {
            return true;
        } else {
            if (NogometniSavez.getInstance().getDogadaji() == null) {
                System.out.println("Nema podataka o događajima");
            }
            if (NogometniSavez.getInstance().getUtakmice() == null) {
                System.out.println("Nema podataka o utakmicama");
            }
            if (NogometniSavez.getInstance().getSastavi() == null) {
                System.out.println("Nema podataka o sastavima");
            }
            return false;
        }
    }

    private static void naknadnoUcitavanje(String naredba) {
        String pom[] = naredba.split(" ");
        FacadeUcitavanje ucitavanje = new FacadeUcitavanje();
        switch (pom[0]) {
            case "NU":
                List<Utakmice> sveUtakmice = ucitavanje.ucitajUtakmice(pom[1]);
                if (NogometniSavez.getInstance().getUtakmice() == null) {
                    NogometniSavez.getInstance().setUtakmice(sveUtakmice);
                    return;
                }
                List<Utakmice> spojiUtakmice = new ArrayList<>();
                spojiUtakmice.addAll(NogometniSavez.getInstance().getUtakmice());
                spojiUtakmice.addAll(sveUtakmice);
                NogometniSavez.getInstance().setUtakmice(spojiUtakmice);
                break;
            case "ND":
                List<Dogadaji> sviDogadaji = ucitavanje.ucitajDagadaje(pom[1]);

                if (NogometniSavez.getInstance().getDogadaji() == null) {
                    NogometniSavez.getInstance().setDogadaji(sviDogadaji);
                    return;
                }
                List<Dogadaji> spojiDogadaje = new ArrayList<>();
                spojiDogadaje.addAll(NogometniSavez.getInstance().getDogadaji());
                spojiDogadaje.addAll(sviDogadaji);
                NogometniSavez.getInstance().setDogadaji(spojiDogadaje);
                break;
            case "NS":
                List<SastaviUtakmica> sviSastavi = ucitavanje.ucitajSastave(pom[1]);
                if (NogometniSavez.getInstance().getSastavi() == null) {
                    NogometniSavez.getInstance().setSastavi(sviSastavi);
                    return;
                }
                List<SastaviUtakmica> spojiSastave = new ArrayList<>();
                spojiSastave.addAll(NogometniSavez.getInstance().getSastavi());
                spojiSastave.addAll(sviSastavi);
                NogometniSavez.getInstance().setSastavi(spojiSastave);
                break;

        }
    }

    private static void ispisujSemafor(String naredba) {
        String pom[] = naredba.split(" ");
        int broj = provjeriUtakmicu(naredba);
        if (broj == -1) {
            return;
        }
        Utakmice u = dohvatiUtakmicu(broj);
        String dom = u.domacin;
        String go = u.gost;
        String domacin = dohvatiKlub(dom);
        String gost = dohvatiKlub(go);
        Subject subject = new Subject();
        new SemaforObserver(subject);
        for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
            if (d.broj == broj) {
                Semafor s = new Semafor(d.broj, d.min, d.vrsta, d.klub, d.imeIgraca, d.imeIgracaZamjena, domacin, gost, dom, go);
                subject.setState(s);
                pauza(Integer.parseInt(pom[4]) * 1000);
            }

        }
    }

    public static void pauza(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int provjeriUtakmicu(String naredba) {
        String pom[] = naredba.split(" ");
        int kolo = Integer.parseInt(pom[1]);
        String klub1 = pom[2];
        String klub2 = pom[3];
        if (!postojiKlub(klub1)) {
            System.out.println("Ne postoji klub " + klub1);
            return -1;
        }
        if (!postojiKlub(klub2)) {
            System.out.println("Ne postoji klub " + klub2);
            return -1;
        }
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (((u.domacin.equals(klub1) && u.gost.equals(klub2)) || (u.domacin.equals(klub2)
                    && u.gost.equals(klub1))) && u.kolo == kolo) {

                return u.broj;
            }

        }
        System.out.println("Klubovi nisu igrali utakmicu u ovom kolu");
        return -1;
    }

    private static String dohvatiKlub(String klub) {
        for (Klubovi k : NogometniSavez.getInstance().getKlubovi()) {
            if (k.klub.equals(klub)) {
                return k.naziv;
            }
        }
        return "";
    }

    private static Utakmice dohvatiUtakmicu(int broj) {
        for (Utakmice u : NogometniSavez.getInstance().getUtakmice()) {
            if (u.broj == broj) {
                return u;
            }
        }
        return null;
    }

    private static void decorator(String naredba) {
        String[] pom = naredba.split(" ");
        String pozicije = pom[1];
        System.out.println(naredba);
        BrojIgracaPozicije bip = new Pozicije();
        if (pozicije.contains("G")) {
            bip = new Golmani(bip);
        }
        if (pozicije.contains("B")) {
            bip = new Branici(bip);
        }
        if (pozicije.contains("V")) {
            bip = new Vezni(bip);
        }
        if (pozicije.contains("N")) {
            bip = new Napadaci(bip);
        }
        System.out.println("Igraci: " + bip.getOpis());
        System.out.println("UKUPNO: " + bip.brojIgraca());
    }

    private static void sastaviPrijeIPoslije(String naredba) {
        String pom[] = naredba.split(" ");
        int kolo = Integer.parseInt(pom[1]);
        String klub1 = pom[2];
        String klub2 = pom[3];
        System.out.println("Naredba: " + naredba);
        int utakmica = provjeriUtakmicu(naredba);
        Utakmice u = dohvatiUtakmicu(utakmica);

        if (utakmica == -1) {
            return;
        }
        System.out.println("Ispis1 : " + u.domacin+" : "+u.gost);
        List<SastaviUtakmica> domacinPrijePocetni = new ArrayList<>();
        List<SastaviUtakmica> domacinPrijeRezerva = new ArrayList<>();
        List<SastaviUtakmica> domacinNakonPocetni = new ArrayList<>();
        List<SastaviUtakmica> domacinNakonRezerva = new ArrayList<>();
        List<SastaviUtakmica> gostPrijePocetni = new ArrayList<>();
        List<SastaviUtakmica> gostPrijeRezerva = new ArrayList<>();
        List<SastaviUtakmica> gostNakonPocetni = new ArrayList<>();
        List<SastaviUtakmica> gostNakonRezerva = new ArrayList<>();
        List<SastaviUtakmica> gost = new ArrayList<>();
        for (SastaviUtakmica s : NogometniSavez.getInstance().getSastavi()) {
            if (s.broj == utakmica && s.klub.equals(u.domacin) && s.vrsta.equals("S")) {
                domacinPrijePocetni.add(s);
            }
            if (s.broj == utakmica && s.klub.equals(u.domacin) && s.vrsta.equals("P")) {
                domacinPrijeRezerva.add(s);
            }
            if (s.broj == utakmica && s.klub.equals(u.gost) && s.vrsta.equals("S")) {
                gostPrijePocetni.add(s);
            }
            if (s.broj == utakmica && s.klub.equals(u.gost) && s.vrsta.equals("P")) {
                gostPrijeRezerva.add(s);
            }
        }
        domacinPrijePocetni = sortirajSastav(domacinPrijePocetni);
        domacinPrijeRezerva = sortirajSastav(domacinPrijeRezerva);
        gostPrijePocetni = sortirajSastav(gostPrijePocetni);
        gostPrijeRezerva = sortirajSastav(gostPrijeRezerva);
        System.out.println("Pocetni:");
        ispisiSastav(domacinPrijePocetni, gostPrijePocetni, u);
        System.out.println("Rezerva:");
        ispisiSastav(domacinPrijeRezerva, gostPrijeRezerva, u);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        obradiDogadaje(u);
        obradiZamjene(domacinPrijePocetni, domacinPrijeRezerva, gostPrijePocetni, gostPrijeRezerva, u);

    }

    private static List<SastaviUtakmica> sortirajSastav(List<SastaviUtakmica> sastav) {
        List<SastaviUtakmica> sortirano = new ArrayList<>();
        for (SastaviUtakmica s : sastav) {
            if (s.pozicija.equals("G")) {
                sortirano.add(s);
            }
        }
        for (SastaviUtakmica s : sastav) {
            if (s.pozicija.equals("B")) {
                sortirano.add(s);
            }
        }
        for (SastaviUtakmica s : sastav) {
            if (s.pozicija.equals("V")) {
                sortirano.add(s);
            }
        }
        for (SastaviUtakmica s : sastav) {
            if (s.pozicija.equals("N")) {
                sortirano.add(s);
            }
        }

        return sortirano;
    }

    private static void ispisiSastav(List<SastaviUtakmica> domacin,
            List<SastaviUtakmica> gost, Utakmice u) {
        int max = 0;
        if (domacin.size() > max) {
            max = domacin.size();
        }
        if (gost.size() > max) {
            max = gost.size();
        }
        System.out.format("%-63s%-63s%n", dohvatiKlub(u.domacin), dohvatiKlub(u.gost));
        String dIgrac = "";
        String dPozicija = "";
        String gIgrac = "";
        String gPozicija = "";
        String dDogadaj="";
        String gDogadaj="";
        for (int i = 0; i < max; i++) {
            if (i > domacin.size()-1) {
                dIgrac = "";
                dPozicija = "";
                dDogadaj="";
            } else {
                dIgrac = domacin.get(i).igrac;
                dPozicija = domacin.get(i).pozicija;
                for(Igraci ig : NogometniSavez.getInstance().getIgraci()){
                    if(ig.ime.equals(dIgrac)) dDogadaj=ig.dogadajNaUtakmici;
                }
            }
            if ( i > gost.size()-1 ) {
                gIgrac = "";
                gPozicija = "";
                gPozicija = "";
            } else {
                gIgrac = gost.get(i).igrac;
                gPozicija = gost.get(i).pozicija;
                for(Igraci ig : NogometniSavez.getInstance().getIgraci()){
                    if(ig.ime.equals(gIgrac)) gDogadaj=ig.dogadajNaUtakmici;
                }
            }
            System.out.format("%-50s%-3s%-10s%-50s%-3s%-10s%n", dIgrac, dPozicija, dDogadaj, gIgrac, gPozicija,gDogadaj);
        }
    }

    private static void obradiDogadaje(Utakmice u) {
        ProvjeriDogadaj provjeriDogadaj = new Gol();
        provjeriDogadaj.poveziSa(new GolPenal()).poveziSa(new Autogol())
                .poveziSa(new Zuti()).poveziSa(new Crveni())
                .poveziSa(new Zamjena()).poveziSa(new Gol());

        for (Dogadaji d : NogometniSavez.getInstance().getDogadaji()) {
            if (d.broj == u.broj) {
                provjeriDogadaj.provjeri(d);
            }
        }
    }

    private static void obradiZamjene(List<SastaviUtakmica> domacinPrijePocetni,
            List<SastaviUtakmica> domacinPrijeRezerva,
            List<SastaviUtakmica> gostPrijePocetni, List<SastaviUtakmica> gostPrijeRezerva,
            Utakmice u) {
        SastaviUtakmica obrisi = null;
        for (Igraci i : NogometniSavez.getInstance().getIgraci()) {
            if (i.klub.equals(u.domacin)) {
                if (i.getDogadajNaUtakmici().contains("<-")) {
                    for (SastaviUtakmica s : domacinPrijeRezerva) {
                        if (s.igrac.equals(i.ime)) {
                            domacinPrijePocetni.add(s);
                            obrisi = s;
                            domacinPrijeRezerva.remove(obrisi);
                            break;
                        }
                    }
                    
                }

                if (i.dogadajNaUtakmici.contains("->")) {
                    for (SastaviUtakmica s : domacinPrijePocetni) {
                        if (s.igrac.equals(i.ime)) {
                            domacinPrijeRezerva.add(s);
                            obrisi = s;
                            domacinPrijePocetni.remove(obrisi);
                            break;
                        }
                    }
                    
                }
            }
            if (i.klub.equals(u.gost)) {
                if (i.dogadajNaUtakmici.contains("<-")) {
                    for (SastaviUtakmica s : gostPrijeRezerva) {
                        if (s.igrac.equals(i.ime)) {
                            gostPrijePocetni.add(s);
                            obrisi = s;
                            gostPrijeRezerva.remove(obrisi);
                            break;
                        }
                    }
                    
                }

                if (i.dogadajNaUtakmici.contains("->")) {
                    for (SastaviUtakmica s : gostPrijePocetni) {
                        if (s.igrac.equals(i.ime)) {
                            gostPrijeRezerva.add(s);
                            obrisi = s;
                            gostPrijePocetni.remove(obrisi);
                            break;
                        }
                    }
                    
                }
            }

        }
        ispisiSastavNakon(domacinPrijePocetni, domacinPrijeRezerva, gostPrijePocetni,
                gostPrijeRezerva,u);

    }

    private static void ispisiSastavNakon(List<SastaviUtakmica> domacinPrijePocetni, List<SastaviUtakmica> domacinPrijeRezerva, List<SastaviUtakmica> gostPrijePocetni, List<SastaviUtakmica> gostPrijeRezerva, Utakmice u) {
         domacinPrijePocetni = sortirajSastav(domacinPrijePocetni);
         domacinPrijeRezerva = sortirajSastav(domacinPrijeRezerva);
         gostPrijePocetni = sortirajSastav(gostPrijePocetni);
         gostPrijeRezerva = sortirajSastav(gostPrijeRezerva);
         
         ispisiSastav(domacinPrijePocetni, gostPrijePocetni, u);
         System.out.println("Rezerve: ");
         ispisiSastav(domacinPrijeRezerva, gostPrijeRezerva, u);
         obrisiPodatkeIgracima();
    }

    private static void obrisiPodatkeIgracima() {
        for(Igraci i : NogometniSavez.getInstance().getIgraci()){
            i.setDogadajNaUtakmici("");
        }
    }
}
