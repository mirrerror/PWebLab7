package md.mirrerror.pweblab7.controllers;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.exceptions.TVSeriesNotFoundException;
import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.services.TVSeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/series")
@RestController
@RequiredArgsConstructor
public class TVSeriesController {

    private final TVSeriesService tvSeriesService;

    @GetMapping("/all")
    public ResponseEntity<List<TVSeries>> getAllSeries() {
        return ResponseEntity.ok(tvSeriesService.getAllSeries());
    }

    @PostMapping("/add")
    public ResponseEntity<TVSeries> addSeries(TVSeries tvSeries) {
        return ResponseEntity.ok(tvSeriesService.save(tvSeries));
    }

    @PatchMapping("/update")
    public ResponseEntity<TVSeries> updateSeries(TVSeries tvSeries) {
        Optional<TVSeries> series = tvSeriesService.getSeriesById(tvSeries.getId());

        if (series.isEmpty()) {
            throw new TVSeriesNotFoundException("TV Series not found");
        }

        return ResponseEntity.ok(tvSeriesService.save(tvSeries));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        Optional<TVSeries> series = tvSeriesService.getSeriesById(id);

        if (series.isEmpty()) {
            throw new TVSeriesNotFoundException("TV Series not found");
        }

        tvSeriesService.deleteSeriesById(id);
        return ResponseEntity.ok().build();
    }

}
