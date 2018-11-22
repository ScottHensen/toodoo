package life.toodoo.api.service;

import org.springframework.stereotype.Service;

import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.model.EventDTO;

@Service
public class EventSvcimpl implements EventSvc
{
	private final EventMapper eventMapper;
	private final EventRepo   eventRepo;
	
	public EventSvcimpl(EventMapper eventMapper, EventRepo eventRepo) {
		this.eventMapper = eventMapper;
		this.eventRepo   = eventRepo;
	}

	@Override
	public EventDTO getEventById(Long id) 
	{
		return eventRepo.findById(id)
						.map(eventMapper::mapEventToEventDTO)
						.orElseThrow(ResourceNotFoundException::new);
	}
	
}
