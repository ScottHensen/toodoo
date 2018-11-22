package life.toodoo.api.service;

import life.toodoo.api.v1.model.EventDTO;

public interface EventSvc 
{
	EventDTO getEventById(Long id);
}
