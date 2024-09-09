package no.sample.pricecalculator;

import no.sample.pricecalculator.exception.InvalidWatchIdException;
import no.sample.pricecalculator.model.WatchRecord;
import no.sample.pricecalculator.repository.WatchRepository;
import no.sample.pricecalculator.service.WatchPriceCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WatchPriceCalculatorTest {

    @Mock
    private WatchRepository mockRepository;

    private WatchPriceCalculationService serviceUnderTest;

    private final Map<String, WatchRecord> watchRecords = Map.of(
            "001", new WatchRecord("001", "Rolex", 100, 3, 200L),
            "002", new WatchRecord("002", "Michael Kors", 80, 2, 120L),
            "003", new WatchRecord("003", "Swatch", 50, 0, 0L),
            "004", new WatchRecord("004", "Casio", 30, 0, 0L)
    );

    @BeforeEach
    void setUp() {
        serviceUnderTest = new WatchPriceCalculationService(mockRepository);
    }

    @Test
    void testSingleEntityPrice() {
        mock("001");
        assertEquals(100, serviceUnderTest.calculateCartPrice(List.of("001")));
    }

    @Test
    void testWrongWatchId() {
        Mockito.reset(mockRepository);
        Mockito.when(mockRepository.findWatchesByWatchIdIn(any())).thenReturn(new ArrayList<>());
        assertThrows(InvalidWatchIdException.class, ()-> serviceUnderTest.calculateCartPrice(List.of("101")));
    }

    @Test
    void testSingleEntityPriceWithDiscountApplied() {
        mock("001");
        assertEquals(200, serviceUnderTest.calculateCartPrice(List.of("001", "001", "001")));
        assertEquals(300, serviceUnderTest.calculateCartPrice(List.of("001", "001", "001", "001")));
        assertEquals(400, serviceUnderTest.calculateCartPrice(List.of("001", "001", "001", "001", "001", "001")));
        mock("002");
        assertEquals(120, serviceUnderTest.calculateCartPrice(List.of("002", "002")));
        assertEquals(200, serviceUnderTest.calculateCartPrice(List.of("002", "002", "002")));
        assertEquals(240, serviceUnderTest.calculateCartPrice(List.of("002", "002", "002", "002")));
    }

    @Test
    void testMultipleWithoutDiscount() {
        mock("001", "002", "003", "004");
        assertEquals(260, serviceUnderTest.calculateCartPrice(List.of("001", "002", "003", "004")));
    }

    @Test
    void testWithMultipleOffersApplied() {
        mock("001", "002", "003", "004");
        assertEquals(480, serviceUnderTest.calculateCartPrice(List.of("001", "002", "001", "003", "002", "001", "002", "004")));
    }

    private void mock(String... element) {
        Mockito.reset(mockRepository);
        Mockito.when(mockRepository.findWatchesByWatchIdIn(any()))
                .thenReturn(
                        Arrays.stream(element).map(watchRecords::get).toList());
    }
}
