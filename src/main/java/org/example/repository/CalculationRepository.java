package org.example.repository;

import org.example.model.Calculation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for {@link Calculation} documents.
 *
 * <p>Follows the Repository Pattern — all MongoDB access is abstracted here,
 * making it straightforward to swap the persistence layer if needed.</p>
 */
@Repository
public interface CalculationRepository extends MongoRepository<Calculation, String> {

    /**
     * Returns all calculations ordered by most recent first.
     *
     * @return list of calculations sorted by timestamp descending
     */
    List<Calculation> findAllByOrderByTimestampDesc();
}
