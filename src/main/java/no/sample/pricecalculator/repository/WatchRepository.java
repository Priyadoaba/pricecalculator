package no.sample.pricecalculator.repository;

import no.sample.pricecalculator.entity.Watch;
import no.sample.pricecalculator.model.WatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WatchRepository extends JpaRepository<Watch, Long> {
    List<WatchRecord> findWatchesByWatchIdIn(@Param("watchId") Set<String> watchIds);

}