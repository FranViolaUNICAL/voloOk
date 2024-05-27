package org.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ObjectMapperSingleton {
    private static ObjectMapper objectMapper;
    private static JsonNode root;
    public final static File JSON = new File("src/userDatabase.json");
    private ObjectMapperSingleton() {}

    public static ObjectMapper getInstance() throws IOException {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            root = objectMapper.readTree(JSON);
        }
        return objectMapper;
    }

    public static JsonNode getRoot() throws IOException {
        if(root == null){
            objectMapper = new ObjectMapper();
            root = objectMapper.readTree(JSON);
        }
        return root;
    }


}
