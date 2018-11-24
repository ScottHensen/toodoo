package life.toodoo.api.v1.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping( { "" } )
	@ResponseStatus( HttpStatus.CREATED )
	@ApiOperation( value = "Create an event" )
	public EventDTO createEvent( @Valid @RequestBody EventDTO eventDTO )
	{
		return eventSvc.createEvent(eventDTO);
	}
	
	@PutMapping( { "/{id}" } )
	@ApiOperation( value = "Update (overwrite) an event" )
	public EventDTO updateEvent( @Valid @RequestBody EventDTO eventDTO, @PathVariable Long id )
	{
		return eventSvc.updateEvent(id, eventDTO);
	}
	
	@PatchMapping( { "/{id}" } )
	@ApiOperation( value = "Update (merge) an event" )
	public EventDTO patchEvent( @Valid @RequestBody EventDTO eventDTO, @PathVariable Long id )
	{
		return eventSvc.patchEvent(id, eventDTO);
	}
	
	@DeleteMapping( { "/{id}" } )
	@ResponseStatus( HttpStatus.OK )
	@ApiOperation( value = "Delete an event by ID" )
	public void deleteEvent( @PathVariable Long id )
	{
		eventSvc.deleteEventById(id);
	}
}
