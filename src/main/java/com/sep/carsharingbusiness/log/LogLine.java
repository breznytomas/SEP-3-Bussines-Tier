package com.sep.carsharingbusiness.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogLine {

    private final LocalDateTime dateTime;
    private final String text;

    public LogLine(String text) {
        this.text = text;
        dateTime = LocalDateTime.now();
    }

    public String getDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(dateTime);
    }

    public String getDateTime() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dateTime);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                getDateTime(),
                text);
    }
}
