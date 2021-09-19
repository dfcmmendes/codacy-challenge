package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.model.CommitDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CliService {
    private static final String PIPE_DELIMITER = "\\|";
    private static final String PATH = "repos";

    @Inject
    ObjectMapper objectMapper;

    public List<CommitDto> getCommitHistory(String owner, String repository) throws IOException, InterruptedException {

        Path path = Files.createTempDirectory(PATH);
        File file = path.toFile();
        File repoFile = new File(path + "/" + repository);

        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(buildCloneCommand(owner, repository))
                .directory(file);

        Process process = processBuilder.start();
        process.waitFor();

        processBuilder = new ProcessBuilder()
                .command(buildLogCommand())
                .directory(repoFile);

        process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String currentLine = null;

        while ( (currentLine = reader.readLine()) != null) {
            if (!currentLine.isEmpty()) {
                result.append(currentLine);
                result.append(reader.readLine());
                result.append(PIPE_DELIMITER);
            }
        }

        return parseCommits(result.toString());
    }

    public List<CommitDto> parseCommits(String commitList) throws JsonProcessingException {
        List<CommitDto> commits = new ArrayList<>();
        CommitDto currentCommit;
        String[] parsedList = commitList.split(PIPE_DELIMITER);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        for (String currentLine: parsedList) {
            currentCommit = objectMapper.readValue(currentLine, CommitDto.class);
            commits.add(currentCommit);
        }



        return commits;
    }

    private String[] buildCloneCommand(final String owner, final String repository) {
        final String repoUrl = "https://github.com/" + owner + "/" + repository + ".git";
        return new String[] {"git", "clone", "-n", repoUrl};
    }

    private String[] buildLogCommand() {
        return new String[] {"git", "log", "--date=format:%Y-%m-%d %H:%M:%S","--pretty=format:{\\\"sha\\\": \\\"%H\\\", \\\"author\\\": \\\"%an\\\", \\\"date\\\": \\\"%ad\\\", \\\"message\\\": \\\"%B\\\"}"};
    }
}
