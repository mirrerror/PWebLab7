package md.mirrerror.pweblab7.repositories;

import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TVSeriesRepository extends CrudRepository<TVSeries, Long> {
    Optional<TVSeries> findByTitle(String name);
    Page<TVSeries> findAll(Pageable pageable);
    List<TVSeries> findAllByUser(User user);
    Page<TVSeries> findAllByUser(User user, Pageable pageable);
}
