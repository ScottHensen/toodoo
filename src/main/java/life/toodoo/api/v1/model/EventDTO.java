package life.toodoo.api.v1.model;

import lombok.Data;

@Data
public class EventDTO 
{
	private String      title;
	private TodoDTO     todo;
//TODO:  add these...
//	private byte[]      description;
//	private LocationDTO location;
//	private ScheduleDTO schedule;
}
