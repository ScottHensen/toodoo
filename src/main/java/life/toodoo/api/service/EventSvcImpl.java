package life.toodoo.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import life.toodoo.api.domain.entity.Event;
import life.toodoo.api.errorhandling.EditErrorException;
import life.toodoo.api.errorhandling.EntityNotFoundException;
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
	public EventDTO getEventById(Long id) throws EntityNotFoundException 
	{
		Optional<Event> event = eventRepo.findById(id);

		if ( ! event.isPresent() )
			throw new EntityNotFoundException(Event.class, "id", id.toString() );
		
		return eventMapper.mapEventToEventDTO(event.get());
	}

	@Override
	public EventListDTO getAllEvents() 
	{
		List<EventDTO> events =
				eventRepo.findAll()
					.stream()
					.map(eventMapper::mapEventToEventDTO)
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
	public EventDTO patchEvent(Long id, EventDTO eventDTO) throws EntityNotFoundException, EditErrorException 
	{
		Optional<Event> event = eventRepo.findById(id);

		if ( ! event.isPresent() )
			throw new EntityNotFoundException(Event.class, "id", id.toString() );
		
		//TODO: remove.  this is a kluge to test edit error
		if ( eventDTO.getTitle() != null && eventDTO.getTitle().equals("fail") )
			throw new EditErrorException(EventDTO.class, "Do not store 'failing' events", "title=" + eventDTO.getTitle() );
		
		Event savedEvent = updateEventWithDto(eventDTO, event.get());
		
		return eventMapper.mapEventToEventDTO(savedEvent);
	}
	
	@Override
	public void deleteEventById(long id) 
	{
		//TODO: maybe change this to a logical delete
		eventRepo.deleteById(id);
	}

	
	protected Event updateEventWithDto(EventDTO eventDTO, Event event) 
	{
		if (eventDTO.getTitle()       != null ) {
			event.setTitle(eventDTO.getTitle());
		}
		if (eventDTO.getPriority()    != null ) {
			event.setPriority(eventDTO.getPriority());
		}
		if (eventDTO.getStatus()      != null ) {
			event.setStatus(eventDTO.getStatus());
		}
		if (eventDTO.getCompletePct() != null ) {
			event.setCompletePct(eventDTO.getCompletePct());
		}
		
		return eventRepo.save(event);
	}

}
