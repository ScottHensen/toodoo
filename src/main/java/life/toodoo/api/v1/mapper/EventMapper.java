package life.toodoo.api.v1.mapper;

import org.springframework.stereotype.Component;

import life.toodoo.api.domain.Event;
import life.toodoo.api.v1.model.EventDTO;

@Component
public class EventMapper 
{
	public EventDTO mapEventToEventDTO(Event event)
	{
		if ( event == null )
			return null;
		
		EventDTO eventDTO = new EventDTO();
		eventDTO.setTitle(event.getTitle());
		eventDTO.setStatus(event.getStatus());
		eventDTO.setPriority(event.getPriority());
		eventDTO.setCompletePct(event.getCompletePct());
		
		return eventDTO;
	}
}
