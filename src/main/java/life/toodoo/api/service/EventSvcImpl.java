package life.toodoo.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

@Service
public class EventSvcImpl implements EventSvc
{
	private final EventMapper eventMapper;
	private final EventRepo   eventRepo;
	
	public EventSvcImpl(EventMapper eventMapper, EventRepo eventRepo) {
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

	@Override
	public EventListDTO getAllEvents() 
	{
		List<EventDTO> events =
				eventRepo.findAll()
					.stream()
					.map( event -> {
						EventDTO eventDTO = eventMapper.mapEventToEventDTO(event);
						return eventDTO;
					})
					.collect(Collectors.toList());
		return new EventListDTO(events);
						
	}
}
