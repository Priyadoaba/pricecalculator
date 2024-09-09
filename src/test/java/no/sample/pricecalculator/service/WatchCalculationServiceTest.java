package no.sample.pricecalculator.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static no.sample.pricecalculator.service.WatchPriceCalculationService.convertToWatchIdOccurencesMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WatchCalculationServiceTest {

    @Test
    void testConvertToWatchIdOccurencesMap() {
        Map<String, Long> stringLongMap = convertToWatchIdOccurencesMap(List.of("001", "002", "001", "003", "004"));
        assertEquals(4, stringLongMap.size());
        assertEquals(2, stringLongMap.get("001"));
        assertEquals(1, stringLongMap.get("002"));
        assertEquals(1, stringLongMap.get("003"));
        assertEquals(1, stringLongMap.get("004"));
    }
}