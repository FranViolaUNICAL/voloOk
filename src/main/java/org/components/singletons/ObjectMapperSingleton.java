package org.components.singletons;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperSingleton {
    private static ObjectMapperSingleton instance;
    private ObjectMapper objectMapper;

    private ObjectMapperSingleton() {
        objectMapper = new ObjectMapper();
    }

    public static synchronized ObjectMapperSingleton getInstance() throws IOException {
        if (instance == null) {
            instance = new ObjectMapperSingleton();
        }
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }




}
