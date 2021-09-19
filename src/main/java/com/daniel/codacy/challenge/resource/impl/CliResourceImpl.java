package com.daniel.codacy.challenge.resource.impl;

import com.daniel.codacy.challenge.errorhandling.ServiceException;
import com.daniel.codacy.challenge.resource.api.CliResource;
import com.daniel.codacy.challenge.service.CliService;
import com.daniel.codacy.challenge.service.GitApiService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;

@ApplicationScoped
public class CliResourceImpl implements CliResource {

    @Inject
    CliService cliService;

    @Inject
    GitApiService gitApiService;

    //Could use a CompletableFuture in order to do Async requests for longer commits' lists
    @Override
    @Timeout(20000)
    @Fallback(fallbackMethod = "commitHistoryCli", skipOn = ServiceException.class)
    public Response commitHistory(Integer page, Integer perPage, String owner, String repository) {
        return Response.ok(gitApiService.commitHistory(owner, repository, page, perPage)).build();
    }

    @Timeout(20000)
    public Response commitHistoryCli(Integer page, Integer perPage, String owner, String repository) throws IOException, InterruptedException, ParseException {
        return Response.ok(cliService.getCommitHistory(page, perPage, owner, repository))
                .build();
    }
}
