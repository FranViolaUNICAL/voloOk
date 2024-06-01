package org.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.observers.AbstractSubject;
import org.components.observers.Observer;
import org.components.observers.Subject;
import org.components.singletons.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserList extends AbstractSubject {
    private static UserList instance;
    private List<User> userList;

    // Costruttore privato per impedire l'istanza esterna
    private UserList() throws IOException {
        super();
        userList = new ArrayList<>();
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        Map<String,Object> map = mapper.readValue(new File("src/userDatabase.json"),new TypeReference<Map<String,Object>>(){});
        List mainMap2 = (List) map.get("userList");
        for (Object o : mainMap2) {
            String name = (String) ((Map) o).get("name");
            String surname = (String) ((Map) o).get("surname");
            String email = (String) ((Map) o).get("email");
            String password = (String) ((Map) o).get("password");
            String luogoDiNascita = (String) ((Map) o).get("luogoDiNascita");
            String regioneDiNascita = (String) ((Map) o).get("regioneDiNascita");
            String dataDiNascita = (String) ((Map) o).get("dataDiNascita");
            User u = new User(name, surname, email, password, luogoDiNascita, regioneDiNascita, dataDiNascita);
            userList.add(u);
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
        notifyObservers();
    }

    // Metodo per rimuovere un utente
    public void remove(User user) {
        userList.remove(user);
        notifyObservers();
    }

}
