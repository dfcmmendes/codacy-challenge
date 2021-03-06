package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.errorhandling.ServiceException;
import com.daniel.codacy.challenge.model.CommitDto;
import com.daniel.codacy.challenge.model.CommitPageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import static com.daniel.codacy.challenge.errorhandling.ErrorCodeImpl.INTERNAL_SERVER_ERROR;

@ApplicationScoped
public class CliService {
    private static final String PIPE_DELIMITER = "\\|";
    private static final String PATH = "repos";
    private static final Logger log = LoggerFactory.getLogger(ServiceException.class);

    @Inject
    ObjectMapper objectMapper;

    public CommitPageDto getCommitHistory(Integer page, Integer perPage, String owner, String repository) throws IOException {

        Path path = Files.createTempDirectory(PATH);
        File file = path.toFile();
        File repoFile = new File(path + "/" + repository);

        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(buildCloneCommand(owner, repository))
                .directory(file);

        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            final Logger log = LoggerFactory.getLogger(InterruptedException.class);
            log.error("git clone failed");
            file.delete();
            repoFile.delete();
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }

        processBuilder = new ProcessBuilder()
                .command(buildLogCommand())
                .directory(repoFile);

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            final Logger log = LoggerFactory.getLogger(IOException.class);
            log.error("git log failed");
            file.delete();
            repoFile.delete();
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }


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

        file.delete();
        repoFile.delete();

        return parseCommits(result.toString(), page, perPage);
    }

    public CommitPageDto parseCommits(String commitList, Integer page, Integer perPage) throws JsonProcessingException {
        List<CommitDto> commits = new ArrayList<>();
        CommitDto currentCommit;
        String[] parsedList = commitList.split(PIPE_DELIMITER);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        for(int i = (page - 1) * perPage; i < page * perPage; i++) {
            if(i >= parsedList.length) {
                break;
            }
            currentCommit = objectMapper.readValue(parsedList[i], CommitDto.class);
            commits.add(currentCommit);
        }

        return CommitPageDto.builder()
                .page(page)
                .perPage(perPage)
                .count(commits.size())
                .total(parsedList.length)
                .items(commits)
                .build();
    }

    private String[] buildCloneCommand(final String owner, final String repository) {
        final String repoUrl = "https://github.com/" + owner + "/" + repository + ".git";
        return new String[] {"git", "clone", "-n", repoUrl};
    }

    private String[] buildLogCommand() {
        return new String[] {"git", "log", "--date=format:%Y-%m-%d %H:%M:%S","--pretty=format:{\\\"sha\\\": \\\"%H\\\", \\\"author\\\": \\\"%an\\\", \\\"date\\\": \\\"%ad\\\", \\\"message\\\": \\\"%B\\\"}"};
    }
}
