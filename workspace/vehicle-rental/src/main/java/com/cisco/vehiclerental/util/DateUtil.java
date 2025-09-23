package com.cisco.vehiclerental.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

    public Date fromString(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return  simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
