package org.serverSide.grpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.serverSide.components.singletons.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromoManager {
    static ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
    private static final File AIRPORTS_JSON = new File("src/airports.json");

    public PromoManager() throws IOException {
        mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
    }

    public static List<String> allAirportsInNation(String countryCode) throws IOException {
        JsonNode root = mapper.readTree(AIRPORTS_JSON);
        List<String> ret = new ArrayList<>();
        for(JsonNode airport : root){
            if(airport.path("country").asText().equals(countryCode)){
                ret.add(airport.path("iata").asText());
            }
        }
        return ret;
    }

    public static boolean isAirportInCountry(String aita, String country) throws IOException{
        List<String> allAirportsInNation = allAirportsInNation(country);
        return allAirportsInNation.contains(aita);
    }

    public static String findCountry(String iata) throws IOException {
        JsonNode root = mapper.readTree(AIRPORTS_JSON);
        String ret = null;
        for(JsonNode airport : root){
            if(airport.path("iata").asText().equals(iata)){
                ret = airport.path("country").asText();
            }
        }
        return ret;
    }
}
