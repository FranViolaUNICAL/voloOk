package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.ObjectMapperSingleton;
import org.users.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            Map<String,Object> map = mapper.readValue(new File("src/userDatabase.json"),new TypeReference<Map<String,Object>>(){});
            List mainMap2 = (List) map.get("userlist");
            for(int i = 0; i < mainMap2.size(); i++){
                String email = (String)((Map)mainMap2.get(i)).get("email");
                System.out.println(email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendUser(User user){

    }
}