package no.sample.pricecalculator.utils;

import no.sample.pricecalculator.model.WatchRecord;

/**
 * Enum to represent the discount types and calculate the price based on the discount
 * This enables incorporating new discount types in future
 */
public enum Discount {

    QUANTITY_DISCOUNT {

        @Override
        boolean isEligibleForDiscount(long quantity, WatchRecord item) {
            return quantity > 0 && item.discountOnQuantity() > 0 && quantity >= item.discountOnQuantity();
        }

        @Override
        long calculatePrice(long quantity, WatchRecord item) {
            if(!isEligibleForDiscount(quantity, item)) {
                return super.calculatePrice(quantity, item);
            }
            return (quantity % item.discountOnQuantity() * item.unitPrice()) + (quantity / item.discountOnQuantity() * item.discountedPrice());
        }

    }, NO_DISCOUNT {

        @Override
        boolean isEligibleForDiscount(long quantity, WatchRecord item) {
            return quantity > 0 ;
        }
    };

    /**
     * @param quantity of the item
     * @param item
     * @return true if the item is eligible for specific discount
     */
    abstract boolean isEligibleForDiscount(long quantity, WatchRecord item);

    /**
     * @param quantity of the item
     * @param item
     * @return calculated price based on the discount
     */
    long calculatePrice(long quantity, WatchRecord item) {
        return quantity * item.unitPrice();
    }

    /**
     * @param quantity of the item
     * @param item for which the price needs to be calculated
     * @return the minimum price after applying all the discounts
     */
    public static Long applyDiscountAndCalculatePrice(long quantity, WatchRecord item) {
        long possibleMinimumPrice = NO_DISCOUNT.calculatePrice(quantity, item);
        for (Discount value : Discount.values()) {
            if(value.isEligibleForDiscount(quantity, item)) {
                long calculatedPrice = value.calculatePrice(quantity, item);
                if(calculatedPrice < possibleMinimumPrice) {
                    possibleMinimumPrice = calculatedPrice;
                }
            }
        }
        return possibleMinimumPrice;
    }
}
