package no.sample.pricecalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Error(@JsonProperty("errorCode") String errorCode, @JsonProperty("errorMessage") String errorMessage) {}
