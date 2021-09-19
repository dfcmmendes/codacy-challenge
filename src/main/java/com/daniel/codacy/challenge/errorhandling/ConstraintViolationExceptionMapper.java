package com.daniel.codacy.challenge.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@ApplicationScoped
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger log = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.error("Exception nam: {} Exception: {}", ConstraintViolationException.class.getName(), exception);
        String genericErrorMessage = "Constraint violations(s) occurred during input validation.";
        final List<ErrorPayload.Field> fields = exception.getConstraintViolations().stream()
                                                                                   .map(k -> ErrorPayload.Field.of(k.getPropertyPath().toString(), k.getMessage()))
                                                                                   .collect(Collectors.toList());


        final ErrorPayload errorPayload = ErrorPayload.builder()
                                                      .code(ErrorCodeImpl.BAD_REQUEST.getCode())
                                                      .message(genericErrorMessage)
                                                      .fields(fields)
                                                      .build();

        return Response.status(BAD_REQUEST)
                       .entity(errorPayload)
                       .build();
    }
}
