package org.serverSide.components.singletons;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperSingleton {
    private static ObjectMapperSingleton instance;
    private final ObjectMapper objectMapper;

    private ObjectMapperSingleton() {
        objectMapper = new ObjectMapper();
    }

    public static synchronized ObjectMapperSingleton getInstance(){
        if (instance == null) {
            instance = new ObjectMapperSingleton();
        }
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }




}
