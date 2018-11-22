package life.toodoo.api.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import life.toodoo.api.service.EventSvc;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

@RestController
@RequestMapping( EventController.BASE_URL )
@Api
public class EventController 
{
	public static final String BASE_URL = "/api/v1/events";
	
	private final EventSvc eventSvc;

	public EventController(EventSvc eventSvc) {
		this.eventSvc = eventSvc;
	}
	
	@GetMapping( { "" } )
	@ApiOperation( value = "Get all events" )
	public EventListDTO getAllEvents()
	{
		return eventSvc.getAllEvents();
	}
	
	@GetMapping( { "/{id}" } )
	@ApiOperation( value = "Get an event for a given id" )
	public EventDTO getEventByID( @PathVariable Long id )
	{
		return eventSvc.getEventById(id);
	}
}
