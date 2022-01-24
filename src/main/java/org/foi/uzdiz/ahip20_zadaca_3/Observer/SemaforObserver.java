/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NWTiS_2
 */
public class SemaforObserver extends Observer {

    public SemaforObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    int golDomacin = 0;
    int golGost = 0;
    List<String> logGolDom = new ArrayList<String>();
    List<String> logGolGost = new ArrayList<String>();
    String povecaj = "";
    String ostaloDom = "";
    String ostaloGost = "";

    @Override
    public void update() {
        ostaloDom = "";
        ostaloGost = "";
        String min = subject.getState().min;
        int vrsta = subject.getState().vrsta;
        String klub = subject.getState().klub;
        String oznakaDom = subject.getState().oznakaDom;
        String oznakaGo = subject.getState().oznakaGost;
        String domacin = subject.getState().domacin;
        String gost = subject.getState().gost;
        String igrac1 = subject.getState().imeIgraca;
        String igrac2 = subject.getState().imeIgracaZamjena;
        if (vrsta == 1 || vrsta == 2) {
            obradiGol(klub, oznakaDom, oznakaGo, igrac1, min);
        }
        if (vrsta == 3) {
            obradiAutoGol(klub, oznakaDom, oznakaGo, igrac1, min);
        }
        if (vrsta == 20) {
            if (klub.equals(oznakaDom)) {
                ostaloDom = igrac1 + "->" + igrac2;
            }
            if (klub.equals(oznakaGo)) {
                ostaloGost = igrac1 + "->" + igrac2;
            }
        }
         if (vrsta == 10) {
            if (klub.equals(oznakaDom)) {
                ostaloDom = "Zuti: " + igrac1;
            }
            if (klub.equals(oznakaGo)) {
                ostaloGost = "Zuti: " + igrac1;
            }
        }
        if (vrsta == 11) {
            if (klub.equals(oznakaDom)) {
                ostaloDom = "Crveni: " + igrac1;
            }
            if (klub.equals(oznakaGo)) {
                ostaloGost = "Crveni: " + igrac1;
            }
        }

        ispisiSemafor(min, domacin, gost, golDomacin, golGost, logGolDom, logGolGost, ostaloDom, ostaloGost);

    }

    private void ispisiSemafor(String min, String domacin, String gost, int golDomacin, int golGost, List<String> logGolDom, List<String> logGolGost, String ostaloDom, String ostaloGost) {
        String redak = "---------------------------------------------------------------------------------------------";
        System.out.println(redak);
        System.out.format("%-1s %45s %45s %n", "|", min, "|");
        System.out.println(redak);
        System.out.format("%-1s %22s %22s %22s %22s %n", "|", domacin, "|", gost, "|");
        System.out.println(redak);
        System.out.format("%-1s %22s %22s %22s %22s %n", "|", golDomacin, "|", golGost, "|"); //golovi
        System.out.println(redak);
        if (logGolDom.size() > 0) {
            for (int i = 0; i < logGolDom.size(); i++) {
                System.out.format("%-1s %-43s %1s %-43s %1s %n", "|", logGolDom.get(i), "|", logGolGost.get(i), "|"); //log gol
            }
        } else {
            System.out.format("%-1s %-43s %1s %-43s %1s %n", "|", " ", "|", " ", "|");
        }
        System.out.println(redak);
        System.out.format("%-1s %-43s %1s %-43s %1s %n", "|", ostaloDom, "|", ostaloGost, "|"); //ostalo
        System.out.println(redak + "\n \n");
    }

    private void obradiGol(String klub, String oznakaDom, String oznakaGo, String igrac1, String min) {
        if (klub.equals(oznakaDom)) {
            golDomacin += 1;
            logGolDom.add(igrac1 + " " + min + "'");
            logGolGost.add("");
        }
        if (klub.equals(oznakaGo)) {
            golGost += 1;
            logGolGost.add(igrac1 + " " + min + "'");
            logGolDom.add("");
        }
    }

    private void obradiAutoGol(String klub, String oznakaDom, String oznakaGo, String igrac1, String min) {
        if (klub.equals(oznakaDom)) {
            golGost++;
            logGolGost.add(igrac1 + " " + min + "'" + "(OG)");
            logGolDom.add("");
        }
        if (klub.equals(oznakaGo)) {
            golDomacin++;
            logGolDom.add(igrac1 + " " + min + "'" + "(OG)");
            logGolGost.add("");
        }
    }

}
