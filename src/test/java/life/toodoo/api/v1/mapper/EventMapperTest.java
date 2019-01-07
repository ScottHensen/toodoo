package life.toodoo.api.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import life.toodoo.api.domain.Event;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.ScheduleDTO;

public class EventMapperTest 
{
	private EventMapper eventMapper;
	private ScheduleMapper scheduleMapper;
	private RecurrenceMapper recurrenceMapper;
	
	private static final Long ID = 1L;
	private static final String  TITLE    = "Event Test Title";
	private static final String  STATUS   = "In Progress";
	private static final Integer PRIORITY = 1;
	private static final BigDecimal COMPLETE_PCT = BigDecimal.valueOf(50.0);

	@Before
	public void setUp() throws Exception 
	{
		recurrenceMapper = new RecurrenceMapper();
		scheduleMapper = new ScheduleMapper(recurrenceMapper);
		eventMapper = new EventMapper(scheduleMapper);
	}

	@Test
	public void testEventToEventDTO() 
	{
		// given
		Event event = new Event();
		event.setId(ID);
		event.setTitle(TITLE);
		event.setStatus(STATUS);
		event.setPriority(PRIORITY);
		event.setCompletePct(COMPLETE_PCT);
		
		// when
		EventDTO eventDTO = eventMapper.mapEventToEventDTO(event);
		
		// then
		assertThat(eventDTO.getTitle()).isEqualTo(TITLE);
		assertThat(eventDTO.getStatus()).isEqualTo(STATUS);
		assertThat(eventDTO.getPriority()).isEqualTo(PRIORITY);
		assertThat(eventDTO.getCompletePct()).isEqualTo(COMPLETE_PCT);
	}

	@Test
	public void testEventDtoToEvent()
	{
		// given
		EventDTO eventDTO = 
				EventDTO.builder()
						.title(TITLE)
						.status(STATUS)
						.priority(PRIORITY)
						.completePct(COMPLETE_PCT)
						.schedule(new ScheduleDTO())
						.build();
		// when 
		Event event = eventMapper.mapEventDTOtoEvent(eventDTO);
		
		// then
		assertThat(event.getTitle()).isEqualTo(eventDTO.getTitle());
		assertThat(event.getStatus()).isEqualTo(eventDTO.getStatus());
		assertThat(event.getPriority()).isEqualTo(eventDTO.getPriority());
		assertThat(event.getCompletePct()).isEqualTo(eventDTO.getCompletePct());
	}
}
