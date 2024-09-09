package no.sample.pricecalculator.utils;

import no.sample.pricecalculator.model.WatchRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void testApplyDiscountAndCalculatePrice() {
        WatchRecord watch = new WatchRecord("001", "Rolex", 100, 3, 200L);
        assertEquals(100, Discount.applyDiscountAndCalculatePrice(1, watch));
        assertEquals(200, Discount.applyDiscountAndCalculatePrice(2, watch));
        assertEquals(200, Discount.applyDiscountAndCalculatePrice(3, watch));
        assertEquals(300, Discount.applyDiscountAndCalculatePrice(4, watch));
    }

    @Test
    void testNoDiscount() {
        WatchRecord watch = new WatchRecord("001", "Rolex", 100, 0, 0);
        assertEquals(100, Discount.applyDiscountAndCalculatePrice(1, watch));
        assertEquals(300, Discount.applyDiscountAndCalculatePrice(3, watch));
    }

    @Test
    void testLowestPriceDiscount() {
        WatchRecord watch = new WatchRecord("001", "Rolex", 100, 3, 400L);
        assertEquals(300, Discount.applyDiscountAndCalculatePrice(3, watch));
    }

    @Test
    void testFixedPriceDiscount() {
        WatchRecord watch = new WatchRecord("001", "Rolex", 100, 1, 80L);
        assertEquals(240, Discount.applyDiscountAndCalculatePrice(3, watch));
    }

}