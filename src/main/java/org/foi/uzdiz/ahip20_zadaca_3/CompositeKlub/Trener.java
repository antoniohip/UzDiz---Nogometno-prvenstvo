/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.CompositeKlub;

/**
 *
 * @author NWTiS_2
 */
public class Trener extends Osoba implements Komponenta {

    public Trener(String ime, String klub) {
        super(ime, klub);
    }
   
  
  
      
    @Override
    public void showEmployeeDetails() 
    {
        System.out.println(ime+" " +klub);
    }
    
}
