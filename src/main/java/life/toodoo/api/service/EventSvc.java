package life.toodoo.api.service;

import life.toodoo.api.errorhandling.EditErrorException;
import life.toodoo.api.errorhandling.EntityNotFoundException;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

public interface EventSvc 
{
	EventDTO     getEventById(Long id) throws EntityNotFoundException;

	EventListDTO getAllEvents();

	EventDTO     createEvent(EventDTO eventDTO);

	EventDTO     updateEvent(long id, EventDTO eventDTO);

	EventDTO     patchEvent(Long id, EventDTO eventDTO) throws EntityNotFoundException, EditErrorException;

	void         deleteEventById(long id);
}
