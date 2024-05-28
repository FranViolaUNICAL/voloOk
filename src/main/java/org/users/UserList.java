package org.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.ObjectMapperSingleton;
import org.flights.Flight;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserList {
    private static UserList instance;
    private List<User> userList;

    // Costruttore privato per impedire l'istanza esterna
    private UserList() throws IOException {
        userList = new ArrayList<>();
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        Map<String,Object> map = mapper.readValue(new File("src/userDatabase.json"),new TypeReference<Map<String,Object>>(){});
        List mainMap2 = (List) map.get("userList");
        for(Object object : mainMap2) {
            for(int i = 0; i < mainMap2.size(); i++){
                String email = (String)((Map)mainMap2.get(i)).get("email");
                String password = (String)((Map)mainMap2.get(i)).get("password");
                String luogoDiNascita = (String)((Map)mainMap2.get(i)).get("luogoDiNascita");
                String regioneDiNascita = (String)((Map)mainMap2.get(i)).get("regioneDiNascita");
                String dataDiNascita = (String)((Map)mainMap2.get(i)).get("dataDiNascita");
                User u = new User(email,password,luogoDiNascita,regioneDiNascita,dataDiNascita);
                userList.add(u);
            }
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
