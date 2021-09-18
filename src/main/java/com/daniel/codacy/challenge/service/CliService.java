package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.model.CommitDto;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CliService {
    private static final String PIPE_DELIMITER = "\\|";
    private static final String URL_PREFIX = "git@github.com";
    private static final String PATH = "repos";

    public String runCommand(String owner, String repository, String... command) throws IOException {

        Path path = Files.createTempDirectory(PATH);
        File file = path.toFile();
        File repoFile = new File(path + "/" + URL_PREFIX + owner + "/" + repository);

        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(command)
                .directory(file);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String currentLine = null;

        while ( (currentLine = reader.readLine()) != null) {
            result.append(currentLine);
            result.append(PIPE_DELIMITER);
        }

        return result.toString();
    }

    public List<CommitDto> parseCommits(String commitList) throws ParseException {

        List<CommitDto> commits = new ArrayList<>();
        String[] parsedList = commitList.split(PIPE_DELIMITER);
        int i = 0;
        String sha, author, date, message;

        while(i < parsedList.length) {
            sha = parsedList[i].split(" ")[1].trim().replace("\\", "");
            author = parsedList[i+1].split(":")[1].split(" ")[1].trim();
            date = parsedList[i+2].replace("Date:", "").trim().replace("\\", "");
            message = parsedList[i+4].trim().replace("\\", "");

            commits.add(CommitDto.builder()
                    .sha(sha)
                    .author(author)
                    //.date(ZonedDateTime.parse(date, DateTimeFormatter.ofPattern("EE MMM dd HH:mm:ss yyyy XX", Locale.ENGLISH)))
                    .message(message)
                    .build());
            i+=5;
        }

        return commits;
    }

    private String[] buildCloneCommand(final String owner, final String repository) {
        final String repoUrl = URL_PREFIX + owner + "/" + repository;
        return new String[] {"git", "clone", repoUrl};
    }

    private String[] buildLogCommand(final String owner, final String repository) {
        return new String[] {"git", "log"};
    }
}
