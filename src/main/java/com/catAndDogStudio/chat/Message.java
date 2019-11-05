package com.catAndDogStudio.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String username;
    private Date sentTime;
    private String msg;

    public String encodeMsg() {
        return username + "~" + formatDateTime() + "~" + msg;
    }

    public String formatDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sentTime);
    }

    public static Date parseDateTime(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd Hh:mm:ss").parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
}
