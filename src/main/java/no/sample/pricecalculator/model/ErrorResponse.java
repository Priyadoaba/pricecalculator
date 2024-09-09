package no.sample.pricecalculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record ErrorResponse(@JsonProperty("errors") List<Error> errors) {

    public ErrorResponse(String errorCode, String errorMessage) {
        this ( List.of ( new Error ( errorCode , errorMessage ) ) );
    }
}