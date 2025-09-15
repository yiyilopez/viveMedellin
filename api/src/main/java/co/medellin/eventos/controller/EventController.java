package co.medellin.eventos.controller;

import co.medellin.eventos.dto.EventDto;
import co.medellin.eventos.dto.EventSummaryDto;
import co.medellin.eventos.model.Event;
import co.medellin.eventos.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventSummaryDto>> getEvents(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = eventService.findAll(pageable);
        List<EventSummaryDto> result = events.stream().map(event ->
                new EventSummaryDto(
                        event.getId(),
                        event.getTitle(),
                        event.getStartsAt(),
                        event.getEndsAt(),
                        event.getLocationText(),
                        event.getImageUrl()
                )
        ).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return eventService.findById(id)
                .map(event -> new EventDto(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartsAt(),
                        event.getEndsAt(),
                        event.getLocationText(),
                        event.getImageUrl(),
                        event.getCreatedBy() != null ? event.getCreatedBy().getId() : null,
                        event.getIsActive(),
                        event.getCreatedAt(),
                        event.getUpdatedAt()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
