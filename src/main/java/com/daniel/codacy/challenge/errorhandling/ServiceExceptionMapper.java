package com.daniel.codacy.challenge.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
    private static final Logger log = LoggerFactory.getLogger(ServiceException.class);

    public ServiceExceptionMapper() {

    }


    public Response toResponse(ServiceException exception) {
        log.error("Exception name: {} Exception: {}", ServiceException.class.getName(), exception);
        ErrorPayload errorPayload = ErrorPayload.builder()
                                                .code(exception.getErrorCode().getCode())
                                                .message(exception.getMessage())
                                                .build();

        return Response.status(exception.getErrorCode().getHttpStatusCode())
                       .entity(errorPayload)
                       .build();
    }

}
