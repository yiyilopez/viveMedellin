package co.medellin.eventos.repository;

import co.medellin.eventos.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
