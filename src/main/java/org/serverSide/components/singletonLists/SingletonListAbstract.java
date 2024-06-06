package org.serverSide.components.singletonLists;

import org.serverSide.components.observers.SubjectAbstract;
import org.serverSide.components.units.Unit;

import java.util.List;

public class SingletonListAbstract extends SubjectAbstract {
    List<Unit> list;

    public SingletonListAbstract(){
        super();
    }
    public synchronized void add(Unit u){
        list.add(u);
        notifyObservers();
    }

    public synchronized void remove(Unit u){
        list.remove(u);
        notifyObservers();
    }
}
