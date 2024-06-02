package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.singletons.ObjectMapperSingleton;
import org.threads.ThreadManager;
import org.users.User;
import org.users.UserList;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new ThreadManager().cleanup();
        System.out.println("ciao");
    }
}