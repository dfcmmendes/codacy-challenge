package com.daniel.codacy.challenge.resource.api;

import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.*;
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
            @QueryParam("page")
            @DefaultValue("1")
            @Parameter(
                    name = "page",
                    description = "page number",
                    example = "1",
                    in = ParameterIn.QUERY
            )
            Integer page,
            @QueryParam("per_page")
            @DefaultValue("5")
            @Parameter(
                    name = "per_page",
                    description = "Number of commits per page",
                    example = "5",
                    in = ParameterIn.QUERY
            )
            Integer perPage,
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
    ) throws IOException, InterruptedException, ParseException;
}