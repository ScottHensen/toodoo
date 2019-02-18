package life.toodoo.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import life.toodoo.api.domain.entity.Event;
import life.toodoo.api.errorhandling.EditErrorException;
import life.toodoo.api.errorhandling.EntityNotFoundException;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.mapper.RecurrenceMapper;
import life.toodoo.api.v1.mapper.ScheduleMapper;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

public class EventSvcTest 
{
	private static final Long ID = 1L;
	private static final String TITLE    = "Event Test Title";
	private static final String STATUS   = "In Progress";
	private static final Integer PRIORITY = 1;
	private static final BigDecimal COMPLETE_PCT = BigDecimal.valueOf(50.0);

	private EventDTO eventDTO;
	private Event event;
	
	EventSvc eventSvc;
	
	@Mock
	EventRepo eventRepo;
	
	@Before
	public void setUp() throws Exception 
	{
		event = new Event();
		event.setId(ID);
		event.setTitle(TITLE);
		event.setStatus(STATUS);
		event.setPriority(PRIORITY);
		event.setCompletePct(COMPLETE_PCT);

		eventDTO = EventDTO.builder()
						.title(TITLE)
						.status(STATUS)
						.priority(PRIORITY)
						.completePct(COMPLETE_PCT)
						.build();

		MockitoAnnotations.initMocks(this);

		eventSvc = new EventSvcImpl( new EventMapper(new ScheduleMapper(new RecurrenceMapper())), eventRepo );
	}

	@Test
	public void testGetEventById() throws EntityNotFoundException 
	{
		// given
		Optional<Event> returnEvent = Optional.of(event);

		when(eventRepo.findById(anyLong())).thenReturn(returnEvent);
		
		// when
		EventDTO eventDTO = eventSvc.getEventById(ID);
		
		// then
		assertThat(eventDTO.getTitle()).isEqualTo(TITLE);
	}

	@Test
	public void testGetAllEvents() 
	{
		// given
		Event event2 = new Event();
		event2.setId(2L);
		event2.setTitle("title2");
		List<Event> returnEvents = new ArrayList<>();
		returnEvents.add(event);
		returnEvents.add(event2);
		
		when(eventRepo.findAll()).thenReturn(returnEvents);
		
		// when
		EventListDTO eventsDTO = eventSvc.getAllEvents();
		
		// then
		assertThat(eventsDTO.getEvents().get(0).getTitle()).isEqualTo(TITLE);
		assertThat(eventsDTO.getEvents().get(1).getTitle()).isEqualTo("title2");
	}

	@Test
	public void testCreateEvent()
	{
		// given		
		when(eventRepo.save(any(Event.class))).thenReturn(event);

		// when 
		EventDTO savedDTO = eventSvc.createEvent(eventDTO);
		
		// then
		assertThat(savedDTO.getTitle()).isEqualTo(eventDTO.getTitle());
	}
	
	@Test
	public void testUpdateEvent()
	{
		// given
		Long id = 1L;
		when(eventRepo.save(any(Event.class))).thenReturn(event);
		
		// when
		EventDTO updatedDTO = eventSvc.updateEvent(id, eventDTO);
		
		// then
		assertThat(updatedDTO.getTitle()).isEqualTo(eventDTO.getTitle());
	}
	
	@Test
	public void testPatchEvent() throws EntityNotFoundException, EditErrorException
	{
		// given
		Long id = 1L;
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
		when(eventRepo.save(any(Event.class))).thenReturn(event);
		
		// when
		EventDTO updatedDTO = eventSvc.patchEvent(id, eventDTO);
		
		// then
		assertThat(updatedDTO.getTitle()).isEqualTo(eventDTO.getTitle());
	}
	
	@Test
	public void testDeleteEvent()
	{
		Long id = 1L;
		eventSvc.deleteEventById(id);
		verify(eventRepo, times(1)).deleteById(anyLong());
	}

	@Test
	public void testUpdateEventWithDto_EmptyEventDTO()
	{
		// given
		EventDTO nullEventDTO = new EventDTO();
		when(eventRepo.save(any(Event.class))).thenReturn(event);
		
		// when 
		Event updatedEvent = ((EventSvcImpl) eventSvc).updateEventWithDto(nullEventDTO, event);

		// then 
		assertThat(updatedEvent.getTitle()).isEqualTo(event.getTitle());
		assertThat(updatedEvent.getStatus()).isEqualTo(event.getStatus());
		assertThat(updatedEvent.getPriority()).isEqualTo(event.getPriority());
		assertThat(updatedEvent.getCompletePct()).isEqualTo(event.getCompletePct());
		
		verify(eventRepo, times(1)).save(any(Event.class));
	}
	
	@Test
	public void testUpdateEventWithDto_EmptyEvent()
	{
		// given
		Event nullEvent = new Event();
		when(eventRepo.save(any(Event.class))).thenReturn(event);
		
		// when 
		Event updatedEvent = ((EventSvcImpl) eventSvc).updateEventWithDto(eventDTO, nullEvent);

		// then 
		assertThat(updatedEvent.getTitle()).isEqualTo(event.getTitle());
		assertThat(updatedEvent.getStatus()).isEqualTo(event.getStatus());
		assertThat(updatedEvent.getPriority()).isEqualTo(event.getPriority());
		assertThat(updatedEvent.getCompletePct()).isEqualTo(event.getCompletePct());
	}
	
	@Test
	public void testUpdateEventWithDto_EmptyEventDto_EmptyEvent()
	{
		// given
		EventDTO nullEventDTO = new EventDTO();
		Event    nullEvent    = new Event();
		when(eventRepo.save(any(Event.class))).thenReturn(nullEvent);
		
		// when 
		Event updatedEvent = ((EventSvcImpl) eventSvc).updateEventWithDto(nullEventDTO, nullEvent);

		// then 
		assertThat(updatedEvent.getTitle()).isNull();
		assertThat(updatedEvent.getStatus()).isNull();
		assertThat(updatedEvent.getPriority()).isNull();
		assertThat(updatedEvent.getCompletePct()).isNull();
	}
	
	@Test
	public void testUpdateEventWithDto() 
	{
		// given
		eventDTO.setStatus("foo");
		event.setStatus("foo");
		when(eventRepo.save(any(Event.class))).thenReturn(event);

		// when 
		Event updatedEvent = ((EventSvcImpl) eventSvc).updateEventWithDto(eventDTO, event);

		// then 
		assertThat(updatedEvent.getTitle()).isEqualTo(event.getTitle());
		assertThat(updatedEvent.getStatus()).isEqualTo("foo");
		assertThat(updatedEvent.getPriority()).isEqualTo(event.getPriority());
		assertThat(updatedEvent.getCompletePct()).isEqualTo(event.getCompletePct());
	}
}
