package life.toodoo.api.commandlinerunner;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import life.toodoo.api.domain.Event;
import life.toodoo.api.repositories.EventRepo;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalTestDataBootstrap implements CommandLineRunner
{
	private static final Long ID1 = 1L;
	private static final String TITLE1     = "Take over the world";
	private static final String STATUS1    = "In Progress";
	private static final Integer PRIORITY1 = 1;
	private static final BigDecimal COMPLETE_PCT1 = BigDecimal.valueOf(50.0);

	private static final Long ID2 = 2L;
	private static final String TITLE2     = "Solve the world's problems";
	private static final String STATUS2    = "Complete";
	private static final Integer PRIORITY2 = 2;
	private static final BigDecimal COMPLETE_PCT2 = BigDecimal.valueOf(100.0);

	private EventRepo eventRepo;
	
	public LocalTestDataBootstrap(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}

	@Override
	public void run(String... args) throws Exception 
	{
		log.debug( "Bootstrapping local test data..." );
		loadEvents();
	}

	private void loadEvents() {
		Event event1 = new Event();
		event1.setId(ID1);
		event1.setTitle(TITLE1);
		event1.setStatus(STATUS1);
		event1.setPriority(PRIORITY1);
		event1.setCompletePct(COMPLETE_PCT1);
		eventRepo.save(event1);
		
		Event event2 = new Event();
		event2.setId(ID2);
		event2.setTitle(TITLE2);
		event2.setStatus(STATUS2);
		event2.setPriority(PRIORITY2);
		event2.setCompletePct(COMPLETE_PCT2);
		eventRepo.save(event2);
		
		log.debug( eventRepo.count() + " Events Loaded.");
	}
}
