package life.toodoo.api.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import life.toodoo.api.service.EventSvc;
import life.toodoo.api.v1.model.EventDTO;

@RestController
@RequestMapping( EventController.BASE_URL )
public class EventController 
{
	public static final String BASE_URL = "/api/v1/events";
	
	private final EventSvc eventSvc;

	public EventController(EventSvc eventSvc) {
		this.eventSvc = eventSvc;
	}
	
	@GetMapping( { "/{id}" } )
	public EventDTO getEventByID( @PathVariable Long id )
	{
		return eventSvc.getEventById(id);
	}
}
