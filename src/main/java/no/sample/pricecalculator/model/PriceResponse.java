package no.sample.pricecalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PriceResponse(@JsonProperty("price") long price) {}
