/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.CompositeKlub;

import java.util.Date;
import java.util.List;

/**
 *
 * @author NWTiS_2
 */
public class Igrac extends Osoba implements Komponenta  {
     public List<String> pozicije;
    public Date roden;
    public int golovi;

    public Igrac(String ime, String klub) {
        super(ime, klub);
    }


    
   
      
    @Override
    public void showEmployeeDetails() 
    {
        System.out.println(ime+" " +klub);
    }
    
     public int getGolovi() {
        return golovi;
    }

    public void setGolovi(int golovi) {
        this.golovi = golovi;
    }
    

}
