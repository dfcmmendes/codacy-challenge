package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.model.CommitDto;
import com.daniel.codacy.challenge.model.CommitPageDto;
import com.daniel.codacy.challenge.model.GitApiCommitDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GitApiService {

    @Inject
    @RestClient
    GitApiClient gitAPIClient;

    public CommitPageDto commitHistory(String owner, String repository, Integer page, Integer perPage) {

        List<GitApiCommitDto> commitList = gitAPIClient.
                commitHistory(owner, repository, page, perPage);

        List<CommitDto> commitDtos = new ArrayList<>();

        for (GitApiCommitDto currentApiCommit: commitList) {
            commitDtos.add(CommitDto.builder()
                    .sha(currentApiCommit.getSha())
                    .message(currentApiCommit.getCommit().getMessage())
                    .date(currentApiCommit.getCommit().getAuthor().getDate())
                    .author(currentApiCommit.getCommit().getAuthor().getName())
                    .build());
        }

        return CommitPageDto.builder()
                .items(commitDtos)
                .page(page)
                .perPage(perPage)
                .count(commitDtos.size())
                .build();
    }
}
