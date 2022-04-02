package com.sep.carsharingbusiness.log;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class Log {

    public static void addLog(String text) {
        var logLine = new LogLine(text);
        addToFile(logLine);
        System.out.println(logLine);
    }

    private static void addToFile(LogLine log) {
        if (log == null) return;
        String filename = "Log-" + log.getDate() + ".txt";
        try (var out = new BufferedWriter(new FileWriter(filename, true))) {
            out.write(log + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
