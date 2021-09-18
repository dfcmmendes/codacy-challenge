package com.daniel.codacy.challenge.resource;

import com.daniel.codacy.challenge.service.CliService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

@Path("/codacy")
public class CliResource {

    @Inject
    CliService cliService;

    @GET
    @Path("/log")
    public String commitHistory() throws IOException {
        return cliService.runCommand("git", "log");
    }
}