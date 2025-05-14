package md.mirrerror.pweblab7.repositories;

import md.mirrerror.pweblab7.models.TVSeries;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TVSeriesRepository extends CrudRepository<TVSeries, Long> {
    Optional<TVSeries> findByTitle(String name);

}
