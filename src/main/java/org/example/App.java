package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App 
{

    private static final String path = "/Users/stalien/Desktop/access.log";
    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
    private static String dateTimeRegex = ".+\\[([^;\\]]+)\\].+";

    public static void main( String[] args ) throws IOException {

//        String dateTime = "15/Aug/2023:06:25:15 +0300";
//        System.out.println(getTimestamp(dateTime));

        List<String> lines = Files.readAllLines(Paths.get(path));

//        System.out.println(lines.get(0));

        Pattern dateTimePattern = Pattern.compile(dateTimeRegex);

        for (String line : lines) {
            Matcher matcher = dateTimePattern.matcher(line);
            if (!matcher.find()) {
                continue;
            }
            String dateTime = matcher.group(1);
            System.out.println(dateTime);
        }
//        Statistics statistics = new Statistics(40, 12);
//        Gson gson = new Gson();
//        String json = gson.toJson(statistics);
//
//        System.out.println(json);
    }

    public static long getTimestamp(String dateTime) {
        LocalDateTime time = LocalDateTime.parse(dateTime, formatter);
        return time.toEpochSecond(ZoneOffset.UTC);
    }
}
