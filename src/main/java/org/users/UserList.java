package org.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static UserList instance;
    private List<User> userList;

    // Costruttore privato per impedire l'istanza esterna
    private UserList() {
        userList = new ArrayList<>();
    }

    // Metodo synchronized per ottenere l'istanza singleton
    public static synchronized UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    // Metodo per ottenere una copia della lista degli utenti
    @JsonProperty
    public List<User> getUserList() {
        return new ArrayList<>(userList);
    }

    // Metodo per aggiungere un utente
    public void add(User user) {
        userList.add(user);
    }

    // Metodo per rimuovere un utente
    public void remove(User user) {
        userList.remove(user);
    }
}
