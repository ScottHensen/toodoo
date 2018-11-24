package life.toodoo.api.service;

import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

public interface EventSvc 
{
	EventDTO     getEventById(Long id);

	EventListDTO getAllEvents();

	EventDTO     createEvent(EventDTO eventDTO);

	EventDTO     updateEvent(long id, EventDTO eventDTO);

	EventDTO     patchEvent(long id, EventDTO eventDTO);

	void         deleteEventById(long id);
}
