package co.medellin.eventos.service;

import co.medellin.eventos.dto.EventDto;
import co.medellin.eventos.model.Event;
import co.medellin.eventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public EventDto findById(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            EventDto dto = new EventDto();
            dto.setId(event.getId());
            dto.setTitle(event.getTitle());
            dto.setDescription(event.getDescription());
            dto.setStartsAt(event.getStartsAt());
            dto.setEndsAt(event.getEndsAt());
            dto.setLocationText(event.getLocationText());
            dto.setImageUrl(event.getImageUrl());
            dto.setCreatedBy(event.getCreatedBy());
            dto.setIsActive(event.getIsActive());
            dto.setCreatedAt(event.getCreatedAt());
            dto.setUpdatedAt(event.getUpdatedAt());
            return dto;
        } else {
            return null;
        }
    }

    public Event saveEvent(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartsAt(dto.getStartsAt());
        event.setEndsAt(dto.getEndsAt());
        event.setLocationText(dto.getLocationText());
        event.setImageUrl(dto.getImageUrl());
        event.setCreatedBy(dto.getCreatedBy());
        event.setIsActive(dto.getIsActive());
        event.setCreatedAt(dto.getCreatedAt());
        event.setUpdatedAt(dto.getUpdatedAt());

        return eventRepository.save(event);
    }
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Evento con id " + id + " no existe");
        }
        eventRepository.deleteById(id);
    }


    public EventDto updateEvent( EventDto dto) {
        Optional<Event> optionalEvent = eventRepository.findById(dto.getId());

        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Event" + dto.getId() + " not found");
        }

        Event event = optionalEvent.get();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartsAt(dto.getStartsAt());
        event.setEndsAt(dto.getEndsAt());
        event.setLocationText(dto.getLocationText());
        event.setImageUrl(dto.getImageUrl());
        event.setIsActive(dto.getIsActive());
        event.setUpdatedAt(dto.getUpdatedAt());
        eventRepository.save(event);

        return dto;
    }

    public List<EventDto> getEventsByUser(Long userId) {
        List<Event> events = eventRepository.findByCreatedBy(userId);

        return events.stream().map(event -> {
            EventDto dto = new EventDto();
            dto.setId(event.getId());
            dto.setTitle(event.getTitle());
            dto.setDescription(event.getDescription());
            dto.setStartsAt(event.getStartsAt());
            dto.setEndsAt(event.getEndsAt());
            dto.setLocationText(event.getLocationText());
            dto.setImageUrl(event.getImageUrl());
            dto.setIsActive(event.getIsActive());
            dto.setCreatedAt(event.getCreatedAt());
            dto.setUpdatedAt(event.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

}
