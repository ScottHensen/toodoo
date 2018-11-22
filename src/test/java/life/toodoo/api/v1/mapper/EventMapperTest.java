package life.toodoo.api.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import life.toodoo.api.domain.Event;
import life.toodoo.api.v1.model.EventDTO;

public class EventMapperTest 
{
	private static final Long ID = 1L;
	private static final String TITLE    = "Event Test Title";
	private static final String STATUS   = "In Progress";
	private static final Integer PRIORITY = 1;
	private static final BigDecimal COMPLETE_PCT = BigDecimal.valueOf(50.0);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEventToEventDTO() 
	{
		// given
		EventMapper eventMapper = new EventMapper();
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

}
