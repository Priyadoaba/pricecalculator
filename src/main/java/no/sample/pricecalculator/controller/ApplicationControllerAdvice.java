package no.sample.pricecalculator.controller;

import no.sample.pricecalculator.exception.ApplicationException;
import no.sample.pricecalculator.model.Error;
import no.sample.pricecalculator.model.ErrorResponse;
import no.sample.pricecalculator.utils.MessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    private final MessageResolver messageResolver;

    public ApplicationControllerAdvice(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), messageResolver.resolve(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleMethodValidationException(HandlerMethodValidationException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getAllValidationResults().forEach(violation -> {
            errors.add(new Error(violation.getArgument() == null || "".equals(violation.getArgument()) ?
                                         "invalid input" : violation.getArgument().toString(),
                                 violation.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "))));
        });
        ErrorResponse errorResponse = new ErrorResponse(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultExceptionHandler(Exception ex) {
        LOGGER.error("Generic exception occurred", ex);
        ErrorResponse errorResponse = new ErrorResponse("unknown-error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}