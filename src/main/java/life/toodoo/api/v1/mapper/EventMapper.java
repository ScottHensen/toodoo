package life.toodoo.api.v1.mapper;

import org.springframework.stereotype.Component;

import life.toodoo.api.domain.Event;
import life.toodoo.api.v1.model.EventDTO;

@Component
public class EventMapper 
{	
	private final ScheduleMapper scheduleMapper;
	
	public EventMapper(ScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}
	
	
	public EventDTO mapEventToEventDTO(Event event)
	{
		if ( event == null )
			return null;
		
		EventDTO eventDTO = new EventDTO();
		eventDTO.setId(event.getId());
		eventDTO.setTitle(event.getTitle());
		eventDTO.setStatus(event.getStatus());
		eventDTO.setPriority(event.getPriority());
		eventDTO.setCompletePct(event.getCompletePct());
		eventDTO.setSchedule(scheduleMapper.mapScheduleToScheduleDto(event.getSchedule()));
		
		return eventDTO;
	}

	public Event mapEventDTOtoEvent(EventDTO eventDTO) 
	{
		if ( eventDTO == null )
			return null;
		
		Event event = new Event();
		event.setId(eventDTO.getId());
		event.setTitle(eventDTO.getTitle());
		event.setStatus(eventDTO.getStatus());
		event.setPriority(eventDTO.getPriority());
		event.setCompletePct(eventDTO.getCompletePct());
		event.setSchedule(scheduleMapper.mapScheduleDTOtoSchedule(eventDTO.getSchedule()));
		return event;
	}
}
