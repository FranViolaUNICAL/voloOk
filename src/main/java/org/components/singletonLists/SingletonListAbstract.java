package org.components.singletonLists;

import org.components.observers.SubjectAbstract;
import org.components.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class SingletonListAbstract extends SubjectAbstract {
    List<Unit> list;

    public SingletonListAbstract(){
        super();
    }
    public void add(Unit u){
        list.add(u);
        notifyObservers();
    }

    public void remove(Unit u){
        list.remove(u);
        notifyObservers();
    }
}
