package md.mirrerror.pweblab7.controllers;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.TVSeriesCreationDto;
import md.mirrerror.pweblab7.dtos.TVSeriesDto;
import md.mirrerror.pweblab7.dtos.mappers.TVSeriesCreationMapper;
import md.mirrerror.pweblab7.dtos.mappers.TVSeriesMapper;
import md.mirrerror.pweblab7.exceptions.TVSeriesNotFoundException;
import md.mirrerror.pweblab7.exceptions.UserNotFoundException;
import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.services.TVSeriesService;
import md.mirrerror.pweblab7.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/series")
@RestController
@RequiredArgsConstructor
public class TVSeriesController {

    private final TVSeriesService tvSeriesService;
    private final UserService userService;

    private final TVSeriesCreationMapper tvSeriesCreationMapper;
    private final TVSeriesMapper tvSeriesMapper;

    @GetMapping
    public ResponseEntity<List<TVSeriesDto>> getAllSeries() {
        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<TVSeries> series = currentUser.get().getTvSeries();
        return ResponseEntity.ok(tvSeriesMapper.mapToDtoList(series));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVSeriesDto> getSeriesById(@PathVariable Long id) {
        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<TVSeries> series = tvSeriesService.getSeriesById(id);
        if (series.isEmpty() || !series.get().getUser().equals(currentUser.get())) {
            throw new TVSeriesNotFoundException("This TV Series is not present in the user's collection");
        }

        return ResponseEntity.ok(tvSeriesMapper.mapToDto(series.get()));
    }

    @PostMapping
    public ResponseEntity<TVSeriesDto> createSeries(@RequestBody TVSeriesCreationDto seriesDto) {
        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        TVSeries tvSeries = tvSeriesCreationMapper.mapToEntity(seriesDto);
        tvSeries.setUser(currentUser.get());
        tvSeries.setCreatedAt(LocalDateTime.now());

        TVSeries savedSeries = tvSeriesService.save(tvSeries);
        return ResponseEntity.status(HttpStatus.CREATED).body(tvSeriesMapper.mapToDto(savedSeries));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TVSeriesDto> updateSeries(@PathVariable Long id, @RequestBody TVSeriesCreationDto seriesDto) {
        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<TVSeries> existingSeries = tvSeriesService.getSeriesById(id);
        if (existingSeries.isEmpty() || !existingSeries.get().getUser().equals(currentUser.get())) {
            throw new TVSeriesNotFoundException("This TV Series is not present in the user's collection");
        }

        TVSeries tvSeries = tvSeriesCreationMapper.mapToEntity(seriesDto);
        tvSeries.setId(existingSeries.get().getId());
        tvSeries.setUser(currentUser.get());
        tvSeries.setCreatedAt(existingSeries.get().getCreatedAt());

        TVSeries updatedSeries = tvSeriesService.save(tvSeries);
        return ResponseEntity.ok(tvSeriesMapper.mapToDto(updatedSeries));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<TVSeries> existingSeries = tvSeriesService.getSeriesById(id);
        if (existingSeries.isEmpty() || !existingSeries.get().getUser().equals(currentUser.get())) {
            throw new TVSeriesNotFoundException("This TV Series is not present in the user's collection");
        }

        tvSeriesService.deleteSeriesById(id);
        return ResponseEntity.ok().build();
    }

}