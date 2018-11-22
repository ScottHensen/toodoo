package life.toodoo.api.v1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.service.EventSvc;
import life.toodoo.api.v1.controller.EventController;
import life.toodoo.api.v1.model.EventDTO;

public class EventControllerTests 
{
	private static final String URL   = "/api/v1/events";
	private static final String TITLE = "Test Event Title";

	@Mock
	private EventSvc eventSvc;
	
	private EventDTO eventDTO;
	
	EventController eventController;
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception 
	{
		eventDTO = new EventDTO();
		eventDTO.setTitle(TITLE);

		MockitoAnnotations.initMocks(this);
        
		eventController = new EventController(eventSvc);

		mockMvc = MockMvcBuilders
				.standaloneSetup(eventController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	@Test
	public void testGetEventByIdSuccess() throws Exception 
	{
		when(eventSvc.getEventById(anyLong())).thenReturn(eventDTO);
		
		mockMvc.perform(get(URL + "/1")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));
	}

	@Test
	public void testGetByIdNotFound() throws Exception
	{
		when(eventSvc.getEventById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get(URL + "/999")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(status().isNotFound());
	}

}
