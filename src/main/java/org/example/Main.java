package org.example;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        String date1String = "03/06/2024 10:55:00";
        Date date1 = sdf.parse(date1String);
        String date2String = "06/06/2024 10:55:00";
        Date date2 = sdf.parse(date2String);
        long diff = (date2.getTime() - date1.getTime());
        System.out.println(diff);
        System.out.println(diff / 1000 / 60 / 60 / 24);
    }
}