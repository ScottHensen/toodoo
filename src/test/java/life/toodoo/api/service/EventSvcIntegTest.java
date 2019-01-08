package life.toodoo.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import life.toodoo.api.commandlinerunner.TestDataBootstrap;
import life.toodoo.api.domain.Event;
import life.toodoo.api.repositories.EventRepo;
import life.toodoo.api.v1.mapper.EventMapper;
import life.toodoo.api.v1.mapper.RecurrenceMapper;
import life.toodoo.api.v1.mapper.ScheduleMapper;
import life.toodoo.api.v1.model.EventDTO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventSvcIntegTest 
{
	@Autowired
	EventRepo eventRepo;
	
	EventMapper eventMapper;
	EventSvc eventSvc;
	
	@Before
	public void setUp() throws Exception 
	{
		TestDataBootstrap bootstrapper = new TestDataBootstrap(eventRepo);
		bootstrapper.run();
		
		eventSvc = new EventSvcImpl( new EventMapper(new ScheduleMapper(new RecurrenceMapper())), eventRepo );
	}

	@Test
	public void testPatchEventStatus() throws Exception 
	{
		// given
		Long id = getEventId();
		String newStatus = "updated";

		Event beforeEvent = eventRepo.getOne(id);
		assertNotNull(beforeEvent);		
		
		// when
		EventDTO eventDTO = EventDTO.builder().status(newStatus).build();
		
		eventSvc.patchEvent(id, eventDTO);
		
		// then
		Event afterEvent = eventRepo.findById(id).get();
		assertNotNull(afterEvent);
		assertThat(afterEvent.getStatus()).isEqualTo(newStatus);
		assertThat(afterEvent.getTitle()).isEqualTo(beforeEvent.getTitle());
		assertThat(afterEvent.getPriority()).isEqualTo(beforeEvent.getPriority());
		assertThat(afterEvent.getCompletePct()).isEqualTo(beforeEvent.getCompletePct());
	}
	
	private Long getEventId()
	{
		List<Event> events = eventRepo.findAll();
		return events.get(0).getId();
	}


}
