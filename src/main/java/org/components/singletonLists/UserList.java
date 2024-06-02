package org.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.factories.SingletonListsFactory;
import org.components.observers.AbstractSubject;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Unit;
import org.components.units.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserList extends AbstractSubject implements SingletonList {
    private static UserList instance;
    private List<Unit> userList;

    // Costruttore privato per impedire l'istanza esterna
    private UserList() {
        super();
        try{
            userList = SingletonListsFactory.createSingletonList("src/userDatabase.json","userList");
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
        return new ArrayList<>(userList);
    }

    // Metodo per aggiungere un utente
    public void add(User user) {
        userList.add(user);
        notifyObservers();
    }

    // Metodo per rimuovere un utente
    public void remove(User user) {
        userList.remove(user);
        notifyObservers();
    }

    public void addFidelityPoints(User u, int points){
        userList.remove(u);
        int p = u.getFidelityPoints();
        u.setFidelityPoints(p+points);
        add(u);
    }

}
