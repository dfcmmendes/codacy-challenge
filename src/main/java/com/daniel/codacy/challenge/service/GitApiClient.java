package com.daniel.codacy.challenge.service;

import com.daniel.codacy.challenge.model.GitApiCommitDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@RegisterRestClient
public interface GitApiClient {
    //https://docs.github.com/en/rest/reference/repos?query=commit#list-commits
    @GET
    @Path("/repos/{owner}/{repository}/commits")
    @Produces(MediaType.APPLICATION_JSON)
    List<GitApiCommitDto> commitHistory(@PathParam("owner") String owner,
                                        @PathParam("repository") String repository,
                                        @QueryParam("page") int page,
                                        @QueryParam("per_page") int perPage);
}
