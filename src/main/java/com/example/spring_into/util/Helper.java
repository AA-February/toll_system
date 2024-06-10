package com.example.spring_into.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Helper {


    public static String formatDate(Instant instant) {
        Date date = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

}
