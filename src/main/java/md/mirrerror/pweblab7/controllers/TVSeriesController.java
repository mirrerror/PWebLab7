package md.mirrerror.pweblab7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.TVSeriesCreationDto;
import md.mirrerror.pweblab7.dtos.TVSeriesDto;
import md.mirrerror.pweblab7.dtos.mappers.TVSeriesCreationMapper;
import md.mirrerror.pweblab7.dtos.mappers.TVSeriesMapper;
import md.mirrerror.pweblab7.exceptions.TVSeriesNotFoundException;
import md.mirrerror.pweblab7.exceptions.UserNotFoundException;
import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.models.TVSeriesStatus;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.services.TVSeriesService;
import md.mirrerror.pweblab7.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/series")
@RestController
@Tag(name = "TV Series", description = "Endpoints for managing TV series")
@RequiredArgsConstructor
public class TVSeriesController {

    private final TVSeriesService tvSeriesService;
    private final UserService userService;

    private final TVSeriesCreationMapper tvSeriesCreationMapper;
    private final TVSeriesMapper tvSeriesMapper;

    @GetMapping
    @Operation(summary = "Get all TV series for the current user, with optional filtering")
    public ResponseEntity<Map<String, Object>> getAllSeries(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number") Integer page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") Integer size,
            @RequestParam(defaultValue = "status") @Parameter(description = "Sort by field") String sortBy,
            @RequestParam(defaultValue = "desc") @Parameter(description = "Sort direction") String sortDirection,
            @RequestParam(defaultValue = "all") @Parameter(description = "Filter by status") String status) {

        Optional<User> currentUser = userService.loadCurrentUser();
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<TVSeries> series;

        if (status.equalsIgnoreCase("all")) {
            series = tvSeriesService.getSeriesByUser(currentUser.get(), pageable);
        } else {
            try {
                TVSeriesStatus statusEnum = TVSeriesStatus.valueOf(status.toUpperCase());
                series = tvSeriesService.getSeriesByUserAndStatus(currentUser.get(), statusEnum, pageable);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("series", tvSeriesMapper.mapToDtoList(series.getContent()));
        response.put("totalPages", series.getTotalPages());
        response.put("totalElements", series.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a TV series by ID")
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
    @Operation(summary = "Create a new TV series")
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
    @Operation(summary = "Update a TV series by ID")
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
    @Operation(summary = "Delete a TV series by ID")
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