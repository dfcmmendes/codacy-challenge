package com.daniel.codacy.challenge.service;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

@ApplicationScoped
public class CliService {

    public static String runCommand(String... command) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(command)
                .redirectErrorStream(true);


        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder response = new StringBuilder();
        String currentLine = null;

        while ( (currentLine = reader.readLine()) != null) {
            response.append(currentLine);
        }

        return response.toString();
    }
}
