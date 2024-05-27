package org.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static UserList instance;
    private static List<User> list;

    private UserList() {
    }

    public static UserList getInstance() {
        if(instance == null) {
            instance = new UserList();
            list = new ArrayList<>();
        }
        return instance;
    }

    @JsonProperty
    public static List<User> getUserlist() {
        return new ArrayList<>(list);
    }

    public static void add(User user) {
        list.add(user);
    }

    public static void remove(User user) {
        list.remove(user);
    }
}
