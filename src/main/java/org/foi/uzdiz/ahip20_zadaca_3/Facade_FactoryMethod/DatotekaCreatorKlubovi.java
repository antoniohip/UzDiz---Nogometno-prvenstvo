/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Facade_FactoryMethod;

/**
 *
 * @author NWTiS_2
 */
public class DatotekaCreatorKlubovi implements IDatotekaCreator{
    
    @Override
    public IDatotekaProduct createProduct() {
        return new DatotekaProductKlubovi();
    }
}
