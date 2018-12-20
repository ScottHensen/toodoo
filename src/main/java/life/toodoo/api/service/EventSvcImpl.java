package life.toodoo.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import life.toodoo.api.domain.Event;
import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
						return eventMapper.mapEventToEventDTO(event);
					})
					.collect(Collectors.toList());
		return new EventListDTO(events);
						
	}

	@Override
	public EventDTO createEvent(EventDTO eventDTO) 
	{
		Event savedEvent = eventRepo.save( eventMapper.mapEventDTOtoEvent(eventDTO) );
		
		return eventMapper.mapEventToEventDTO(savedEvent);
	}

	@Override
	public EventDTO updateEvent(long id, EventDTO eventDTO) 
	{	 
		log.info("updateEvent: {}", eventDTO);
		Event updatedEvent = eventRepo.save( eventMapper.mapEventDTOtoEvent(eventDTO) );
		
		return eventMapper.mapEventToEventDTO(updatedEvent);
		
	}
	
	@Override
	public EventDTO patchEvent(long id, EventDTO eventDTO) 
	{
		return eventRepo.findById(id)
				.map( event -> mergeEventDtoIntoEvent(eventDTO, event) )
				.orElseThrow(ResourceNotFoundException::new);
		
	}
	@Override
	public void deleteEventById(long id) 
	{
		eventRepo.deleteById(id);
		
	}

	
	protected EventDTO mergeEventDtoIntoEvent(EventDTO eventDTO, Event event) 
	{
		if (eventDTO.getTitle()    != null ) {
			event.setTitle(eventDTO.getTitle());
		}
		if (eventDTO.getPriority() != null ) {
			event.setPriority(eventDTO.getPriority());
		}
		if (eventDTO.getStatus()   != null ) {
			event.setStatus(eventDTO.getStatus());
		}
		if (eventDTO.getClass()    != null ) {
			event.setCompletePct(eventDTO.getCompletePct());
		}
		return eventMapper.mapEventToEventDTO(event);
	}

}
