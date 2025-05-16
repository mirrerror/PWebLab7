package md.mirrerror.pweblab7.services;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.models.TVSeriesStatus;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.repositories.TVSeriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TVSeriesService {

    private final TVSeriesRepository tvSeriesRepository;

    public List<TVSeries> getAllSeries() {
        List<TVSeries> series = new ArrayList<>();
        tvSeriesRepository.findAll().forEach(series::add);
        return series;
    }

    public Page<TVSeries> getAllSeries(Pageable pageable) {
        return tvSeriesRepository.findAll(pageable);
    }

    public List<TVSeries> getSeriesByUser(User user) {
        return tvSeriesRepository.findAllByUser(user);
    }

    public Page<TVSeries> getSeriesByUser(User user, Pageable pageable) {
        return tvSeriesRepository.findAllByUser(user, pageable);
    }

    public Page<TVSeries> getSeriesByUserAndStatus(User user, TVSeriesStatus status, Pageable pageable) {
        return tvSeriesRepository.findAllByUserAndStatus(user, status, pageable);
    }

    public Optional<TVSeries> getSeriesById(Long id) {
        return tvSeriesRepository.findById(id);
    }

    public void deleteSeriesById(Long id) {
        tvSeriesRepository.deleteById(id);
    }

    public TVSeries save(TVSeries tvSeries) {
        return tvSeriesRepository.save(tvSeries);
    }

}
