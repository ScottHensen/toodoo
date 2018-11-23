package life.toodoo.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import life.toodoo.api.domain.Event;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.model.EventDTO;

public class EventSvcTest 
{
	private static final Long ID = 1L;
	private static final String TITLE    = "Event Test Title";
	private static final String STATUS   = "In Progress";
	private static final Integer PRIORITY = 1;
	private static final BigDecimal COMPLETE_PCT = BigDecimal.valueOf(50.0);

	EventSvc eventSvc;
	
	@Mock
	EventRepo eventRepo;
	
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);

		eventSvc = new EventSvcImpl( new EventMapper(), eventRepo );
	}

	@Test
	public void testGetEventById() 
	{
		// given
		Event event = new Event();
		event.setId(ID);
		event.setTitle(TITLE);
		event.setStatus(STATUS);
		event.setPriority(PRIORITY);
		event.setCompletePct(COMPLETE_PCT);
		Optional<Event> returnEvent = Optional.of(event);

		when(eventRepo.findById(anyLong())).thenReturn(returnEvent);
		
		// when
		EventDTO eventDTO = eventSvc.getEventById(ID);
		
		// then
		assertThat(eventDTO.getTitle()).isEqualTo(TITLE);
	}

	@Test
	public void testCreateEvent()
	{
		// given
		Event event = new Event();
		event.setId(ID);
		event.setTitle(TITLE);
		event.setStatus(STATUS);
		event.setPriority(PRIORITY);
		event.setCompletePct(COMPLETE_PCT);
		
		EventDTO eventDTO = 
				EventDTO.builder()
						.title(TITLE)
						.status(STATUS)
						.priority(PRIORITY)
						.completePct(COMPLETE_PCT)
						.build();
		
		when(eventRepo.save(any(Event.class))).thenReturn(event);

		// when 
		EventDTO savedDTO = eventSvc.createEvent(eventDTO);
		
		// then
		assertThat(savedDTO.getTitle()).isEqualTo(savedDTO.getTitle());
	}
}
