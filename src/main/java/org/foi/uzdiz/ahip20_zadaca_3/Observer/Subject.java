/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.ahip20_zadaca_3.Observer;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.ahip20_zadaca_3.Dogadaji;
import org.foi.uzdiz.ahip20_zadaca_3.Semafor;

/**
 *
 * @author NWTiS_2
 */
public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    private Semafor state;

    public Semafor getState() {
        return state;
    }

    public void setState(Semafor state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
