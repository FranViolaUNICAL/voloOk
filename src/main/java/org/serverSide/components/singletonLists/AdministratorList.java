package org.serverSide.components.singletonLists;

import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorList extends SingletonListAbstract{
    private static AdministratorList instance;

    private AdministratorList(){
        super();
        try{
            list = SingletonListsFactory.createSingletonList("src/administratorsDatabase.json","administratorsList");

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static AdministratorList getInstance(){
        if(instance == null){
            instance = new AdministratorList();
        }
        return instance;
    }

    public synchronized List<Unit> getAdministratorsList() { return new ArrayList<>(list); }

}
