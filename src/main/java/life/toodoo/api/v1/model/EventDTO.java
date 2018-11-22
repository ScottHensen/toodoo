package life.toodoo.api.v1.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EventDTO 
{
	private String      title;
	private String      status;
	private Integer     priority;
	private BigDecimal  completePct;
//TODO:  add these...
//	private byte[]      description;
//	private LocationDTO location;
//	private ScheduleDTO schedule;
}
