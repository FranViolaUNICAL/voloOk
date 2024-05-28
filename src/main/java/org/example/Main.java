package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.components.ObjectMapperSingleton;
import org.users.User;
import user.UserServiceGrpc;
import user.UserServices;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        Map<String,Object> map = mapper.readValue(new File("src/flightDatabase.json"),new TypeReference<Map<String,Object>>(){});
        System.out.println(map);

    }
}