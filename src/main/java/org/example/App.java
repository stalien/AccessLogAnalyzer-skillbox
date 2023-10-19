package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App 
{

    private static final String path = "/Users/stalien/Desktop/access.log";

    public static void main( String[] args ) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        for(String line : lines) {
            System.out.println(line);
        }
    }
}
