package com.daniel.codacy.challenge.resource.api;

import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;

@Path("/codacy")
@Produces(MediaType.APPLICATION_JSON)
public interface CliResource {

    @GET
    @Path("/log/{owner}/{repository}")
    @APIResponse(description = "Commits history", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    Response commitHistory(
            @PathParam("owner")
            @Parameter(
                    name = "owner",
                    description = "Username of the repo's owner",
                    example = "dfcmmendes",
                    in = ParameterIn.PATH
            )
            String owner,
            @PathParam("repository")
            @Parameter(
                    name = "repository",
                    description = "Name of the repo",
                    example = "codacy-challenge",
                    in = ParameterIn.PATH
            )
            String repository
    ) throws IOException, ParseException;
}