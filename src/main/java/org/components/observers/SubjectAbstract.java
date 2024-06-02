package org.components.observers;

import java.util.ArrayList;
import java.util.List;

public class SubjectAbstract implements Subject{
    List<Observer> observers;

    public SubjectAbstract() {
        observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
