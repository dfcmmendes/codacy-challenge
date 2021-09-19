package com.daniel.codacy.challenge.resource.impl;

import com.daniel.codacy.challenge.resource.api.CliResource;
import com.daniel.codacy.challenge.service.CliService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;

@ApplicationScoped
public class CliResourceImpl implements CliResource {

    @Inject
    CliService cliService;

    @Override
    public Response commitHistory(String owner, String repository) throws IOException, InterruptedException, ParseException {
        return Response.ok(cliService.getCommitHistory(owner, repository))
                .build();
    }
}
