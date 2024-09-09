package no.sample.pricecalculator.model;

public record WatchRecord(String watchId, String watchName, long unitPrice, int discountOnQuantity, long discountedPrice) {}
