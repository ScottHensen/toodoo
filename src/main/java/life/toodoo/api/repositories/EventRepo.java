package life.toodoo.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import life.toodoo.api.domain.entity.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Long>
{
}
