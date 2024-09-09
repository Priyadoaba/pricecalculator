package no.sample.pricecalculator.service;

import no.sample.pricecalculator.utils.Discount;
import no.sample.pricecalculator.exception.InvalidWatchIdException;
import no.sample.pricecalculator.model.WatchRecord;
import no.sample.pricecalculator.repository.WatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class WatchPriceCalculationService {

    private final WatchRepository watchRepository;

    public WatchPriceCalculationService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    /*This method calculates the price of the watches based on the provided watchIds
     * It calculates the price based on the discount quantity and discount price if the discount quantity is greater than 0
     * It returns the total price of the watches */
    public long calculateCartPrice(List<String> watchIds) {
        final Map<String, Long> sortedMap = convertToWatchIdOccurencesMap(watchIds);
        List<WatchRecord> watches = watchRepository.findWatchesByWatchIdIn(sortedMap.keySet());
        if(sortedMap.size() != watches.size()) {
            throw new InvalidWatchIdException("{invalid.watch.id}");
        }
        return watches.stream().mapToLong(watchRecord -> Discount.applyDiscountAndCalculatePrice(sortedMap.get(watchRecord.watchId()), watchRecord)).sum();
    }

    /*This method groups the watchIds and counts the number of occurrences in the given array and generates a map
     * Protected for testing purposes */
    protected static Map<String, Long> convertToWatchIdOccurencesMap(List<String> watchIds) {
        return watchIds.stream().collect(groupingBy(p -> p, counting()));
    }
}
