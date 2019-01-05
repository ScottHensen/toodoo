package life.toodoo.api.commandlinerunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.toodoo.api.domain.Event;
import life.toodoo.api.domain.Recurrence;
import life.toodoo.api.domain.RecurrencePattern;
import life.toodoo.api.domain.Schedule;
import life.toodoo.api.repositories.EventRepo;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestDataBootstrap implements CommandLineRunner
{
	private static final String TITLE1     = "Take over the world";
	private static final String STATUS1    = "In Progress";
	private static final Integer PRIORITY1 = 1;
	private static final BigDecimal COMPLETE_PCT1 = BigDecimal.valueOf(0.50);
	private static final LocalDateTime BEG_TS1 = LocalDateTime.of(2019, 01, 02, 21, 30);
	private static final LocalDateTime END_TS1 = LocalDateTime.of(2026, 12, 31, 23, 59);

	private static final String TITLE2     = "Solve for X";
	private static final String STATUS2    = "Complete";
	private static final Integer PRIORITY2 = 2;
	private static final BigDecimal COMPLETE_PCT2 = BigDecimal.valueOf(1.0);
	private static final LocalDateTime BEG_TS2 = LocalDateTime.of(2018, 04, 12, 10, 30);

	private static final String TITLE3     = "Pick my guitar";
	private static final String STATUS3    = "In Progress";
	private static final Integer PRIORITY3 = 4;
	private static final BigDecimal COMPLETE_PCT3 = BigDecimal.valueOf(0.25);
	private static final LocalDateTime BEG_TS3 = LocalDateTime.of(2019, 01, 04, 21, 00);
	private static final LocalDateTime END_TS3 = LocalDateTime.of(2026, 12, 31, 22, 00);

	private static final String TITLE4     = "Go to Mars";
	private static final String STATUS4    = "In Progress";
	private static final Integer PRIORITY4 = 3;
	private static final BigDecimal COMPLETE_PCT4 = BigDecimal.valueOf(0.01);

	private EventRepo eventRepo;
	
	public TestDataBootstrap(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception 
	{
		log.debug( "Bootstrapping local test data..." );
		eventRepo.saveAll(buildTestEvents());
		log.debug( eventRepo.count() + " Events Loaded.");

	}

	private List<Event> buildTestEvents() 
	{
		List<Event> events = new ArrayList<>(4);
		
		Event event1 = new Event();
		event1.setTitle(TITLE1);
		event1.setStatus(STATUS1);
		event1.setPriority(PRIORITY1);
		event1.setCompletePct(COMPLETE_PCT1);
		Schedule schedule1 = new Schedule();
		schedule1.setBeginTimestamp(BEG_TS1);
		schedule1.setEndTimestamp(END_TS1);
		schedule1.setEvent(event1);
		event1.setSchedule(schedule1);
		events.add(event1);
		
		Event event2 = new Event();
		event2.setTitle(TITLE2);
		event2.setStatus(STATUS2);
		event2.setPriority(PRIORITY2);
		event2.setCompletePct(COMPLETE_PCT2);
		Schedule schedule2 = new Schedule();
		schedule2.setBeginTimestamp(BEG_TS2);
		schedule2.setEvent(event2);
		event2.setSchedule(schedule2);
		events.add(event2);
		
		Event event3 = new Event();
		event3.setTitle(TITLE3);
		event3.setStatus(STATUS3);
		event3.setPriority(PRIORITY3);
		event3.setCompletePct(COMPLETE_PCT3);
		Schedule schedule3 = new Schedule();
		schedule3.setBeginTimestamp(BEG_TS3);
		schedule3.setEndTimestamp(END_TS3);
		schedule3.setDurationMins(30);
		Recurrence recurs3 = new Recurrence();
		recurs3.setPattern(RecurrencePattern.DAILY);
		recurs3.setSchedule(schedule3);
		schedule3.setRecurrence(recurs3);
		schedule3.setEvent(event3);
		event2.setSchedule(schedule3);
		events.add(event3);
		
		Event event4 = new Event();
		event4.setTitle(TITLE4);
		event4.setStatus(STATUS4);
		event4.setPriority(PRIORITY4);
		event4.setCompletePct(COMPLETE_PCT4);
		events.add(event4);
		
		return events;
	}
}
