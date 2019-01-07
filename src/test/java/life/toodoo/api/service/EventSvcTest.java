package life.toodoo.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import life.toodoo.api.domain.Event;
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
	public void testGetEventById() 
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
	public void testPatchEvent()
	{
		// given
		Long id = 1L;

		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
		
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
	public void testMergeNullEventDtoIntoEvent()
	{
		// given
		EventDTO nullEventDTO = new EventDTO();
		
		// when 
		EventDTO mergedEventDTO = ((EventSvcImpl) eventSvc).mergeEventDtoIntoEvent(nullEventDTO, event);

		// then 
		assertThat(mergedEventDTO.getTitle()).isEqualTo(event.getTitle());
		assertThat(mergedEventDTO.getStatus()).isEqualTo(event.getStatus());
		assertThat(mergedEventDTO.getPriority()).isEqualTo(event.getPriority());
		assertThat(mergedEventDTO.getCompletePct()).isEqualTo(event.getCompletePct());
	}
	
	@Test
	public void testMergeEventDtoIntoNullEvent()
	{
		// given
		Event nullEvent = new Event();
		
		// when 
		EventDTO mergedEventDTO = ((EventSvcImpl) eventSvc).mergeEventDtoIntoEvent(eventDTO, nullEvent);

		// then 
		assertThat(mergedEventDTO.getTitle()).isEqualTo(event.getTitle());
		assertThat(mergedEventDTO.getStatus()).isEqualTo(event.getStatus());
		assertThat(mergedEventDTO.getPriority()).isEqualTo(event.getPriority());
		assertThat(mergedEventDTO.getCompletePct()).isEqualTo(event.getCompletePct());
	}
	
	@Test
	public void testMergeNullEventDtoIntoNullEvent()
	{
		// given
		EventDTO nullEventDTO = new EventDTO();
		Event    nullEvent    = new Event();
		
		// when 
		EventDTO mergedEventDTO = ((EventSvcImpl) eventSvc).mergeEventDtoIntoEvent(nullEventDTO, nullEvent);

		// then 
		assertThat(mergedEventDTO.getTitle()).isEqualTo(nullEvent.getTitle());
		assertThat(mergedEventDTO.getStatus()).isEqualTo(nullEvent.getStatus());
		assertThat(mergedEventDTO.getPriority()).isEqualTo(nullEvent.getPriority());
		assertThat(mergedEventDTO.getCompletePct()).isEqualTo(nullEvent.getCompletePct());
	}
	
	@Test
	public void testMergeEventDtoIntoEvent()
	{
		// given
		eventDTO.setStatus("foo");
		// when 
		EventDTO mergedEventDTO = ((EventSvcImpl) eventSvc).mergeEventDtoIntoEvent(eventDTO, event);

		// then 
		assertThat(mergedEventDTO.getTitle()).isEqualTo(event.getTitle());
		assertThat(mergedEventDTO.getStatus()).isEqualTo("foo");
		assertThat(mergedEventDTO.getPriority()).isEqualTo(event.getPriority());
		assertThat(mergedEventDTO.getCompletePct()).isEqualTo(event.getCompletePct());
	}
}
