package org.serverSide.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Unit;
import org.serverSide.components.units.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserList extends SingletonListAbstract {
    private static UserList instance;
    // Costruttore privato per impedire l'istanza esterna
    private UserList() {
        super();
        try{
            list = SingletonListsFactory.createSingletonList("src/userDatabase.json","userList");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Metodo synchronized per ottenere l'istanza singleton
    public static synchronized UserList getInstance() throws IOException {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    // Metodo per ottenere una copia della lista degli utenti
    @JsonProperty("userList")
    public List<Unit> getUserList() {
        return new ArrayList<>(list);
    }

    public void addFidelityPoints(User u, int points){
        list.remove(u);
        int p = u.getFidelityPoints();
        u.setFidelityPoints(p+points);
        add(u);
    }

}
