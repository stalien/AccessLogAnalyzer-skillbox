package org.example;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App 
{

    private static final String path = "/Users/stalien/Desktop/access.log";
    private static final String outputPath = "/Users/stalien/Desktop/result.json";
    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
    private static String dateTimeRegex = ".+\\[([^;\\]]+)\\].+";

    public static void main( String[] args ) throws IOException {

        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        int requestsCount = 0;

        List<String> lines = Files.readAllLines(Paths.get(path));
        Pattern dateTimePattern = Pattern.compile(dateTimeRegex);

        HashMap<Long, Integer> countPerSecond = new HashMap<>();

        for (String line : lines) {
            Matcher matcher = dateTimePattern.matcher(line);
            if (!matcher.find()) {
                continue;
            }
            requestsCount++;

            String dateTime = matcher.group(1);
            long time = getTimestamp(dateTime);

            countPerSecond.put(time, countPerSecond.getOrDefault(time, 0) + 1);

            minTime = Math.min(minTime, time);
            maxTime = Math.max(maxTime, time);
        }

        int maxRequestsPerSecond = Collections.max(countPerSecond.values());

        float averageRequestsPerSecond = requestsCount / (float)(maxTime - minTime);

        Statistics statistics = new Statistics(maxRequestsPerSecond, averageRequestsPerSecond);
        Gson gson = new Gson();
        String json = gson.toJson(statistics);

        System.out.println(json);

        FileWriter writer = new FileWriter(outputPath);
        writer.write(json);
        writer.flush();
        writer.close();
        
    }

    public static long getTimestamp(String dateTime) {
        LocalDateTime time = LocalDateTime.parse(dateTime, formatter);
        return time.toEpochSecond(ZoneOffset.UTC);
    }
}
