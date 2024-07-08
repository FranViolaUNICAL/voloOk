package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.serverSide.components.singletons.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ObjectMapper m = ObjectMapperSingleton.getInstance().getObjectMapper();
        JsonNode root = m.readTree(new File("src/airports.json"));
        int n = 0;
        for(JsonNode node : root){
            String IATA = node.path("iata").asText();
            String country = node.path("country").asText();
            System.out.println(country);
            n++;
        }
        System.out.println(n);
    }
}