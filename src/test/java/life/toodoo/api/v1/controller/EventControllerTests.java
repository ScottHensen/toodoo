package life.toodoo.api.v1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.service.EventSvc;
import life.toodoo.api.util.TestUtil;
import life.toodoo.api.v1.controller.EventController;
import life.toodoo.api.v1.model.EventDTO;
import life.toodoo.api.v1.model.EventListDTO;

public class EventControllerTests 
{
	private static final String URL   = "/api/v1/events";

//	private static final Long ID1 = 1L;
	private static final String TITLE1     = "Take over the world";
	private static final String STATUS1    = "In Progress";
	private static final Integer PRIORITY1 = 1;
	private static final BigDecimal COMPLETE_PCT1 = BigDecimal.valueOf(50.0);

//	private static final Long ID2 = 2L;
	private static final String TITLE2     = "Solve the world's problems";
	private static final String STATUS2    = "Complete";
	private static final Integer PRIORITY2 = 2;
	private static final BigDecimal COMPLETE_PCT2 = BigDecimal.valueOf(100.0);

	@Mock
	private EventSvc eventSvc;
	
	private EventDTO eventDTO;
	
	EventController eventController;
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception 
	{
		eventDTO = new EventDTO();
		eventDTO.setTitle(TITLE1);
		eventDTO.setPriority(PRIORITY1);
		eventDTO.setStatus(STATUS1);
		eventDTO.setCompletePct(COMPLETE_PCT1);

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
				.andExpect(jsonPath("$.title", equalTo(TITLE1)));
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
	
	@Test
	public void testGetAllEvents() throws Exception
	{
		// given
		EventDTO eventDTO2 = 
				EventDTO.builder()
						.title(TITLE2)
						.priority(PRIORITY2)
						.status(STATUS2)
						.completePct(COMPLETE_PCT2)
						.build();

		EventListDTO returnEventListDTO = new EventListDTO(Arrays.asList(eventDTO, eventDTO2));
		
		when(eventSvc.getAllEvents()).thenReturn(returnEventListDTO);
		
		// expect
		mockMvc.perform(get(URL)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.events", hasSize(2)));
		
	}

//TODO:
//	@Test
//	public void testGetEventByUserName throws Exception
//	{
//		
//	}

	@Test
	public void testCreateEvent() throws Exception
	{
		// given
		EventDTO returnedEvent = 
				EventDTO.builder()
						.title(TITLE1)
						.status(STATUS1)
						.priority(PRIORITY1)
						.completePct(COMPLETE_PCT1)
						.build();
		
		when(eventSvc.createEvent(any(EventDTO.class))).thenReturn(returnedEvent);
		
		// expect
		mockMvc.perform(post(URL)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(eventDTO)))
				.andExpect(status().isCreated())
				.andDo(print())
				.andExpect(jsonPath("$.title", is(TITLE1)))
				.andExpect(jsonPath("$.status", is(STATUS1)))
				.andExpect(jsonPath("$.priority", is(PRIORITY1)));
	}
}
