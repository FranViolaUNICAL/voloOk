package org.serverSide.grpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        Date d1 = sdf.parse("04/07/2026 11:18:00");
        long differenceInTime = d1.getTime() - d.getTime();
        long divisor = 1000L * 60 * 60 * 24;
        System.out.println(differenceInTime);
        System.out.println(differenceInTime / divisor);
    }
}
