package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.errorhandling.ServiceException;
import com.daniel.codacy.challenge.model.CommitDto;
import com.daniel.codacy.challenge.model.CommitPageDto;
import com.daniel.codacy.challenge.model.GitApiCommitDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.client.exception.ResteasyWebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.codacy.challenge.errorhandling.ErrorCodeImpl.NOT_FOUND_ERROR;

@ApplicationScoped
public class GitApiService {

    @Inject
    @RestClient
    GitApiClient gitAPIClient;

    public CommitPageDto commitHistory(String owner, String repository, Integer page, Integer perPage) {
        List<GitApiCommitDto> commitList = new ArrayList<>();

        try {
             commitList = gitAPIClient.
                    commitHistory(owner, repository, page, perPage);
        } catch (ResteasyWebApplicationException e) {
            final Logger log = LoggerFactory.getLogger(ResteasyWebApplicationException.class);
            log.error("Request to GitHub API failed");
            if(e.unwrap().getResponse().getStatus() == 404) {
                throw new ServiceException(NOT_FOUND_ERROR);
            }
        }


        List<CommitDto> commitDtos = new ArrayList<>();

        //could use a mapper if more objects existed
        //or for improved readability
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
