package life.toodoo.api.v1.model;

import java.math.BigDecimal;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO 
{
	private Long         id;

	@ApiModelProperty( required = true )
	private String       title;
	
	private String       status;
	private Integer      priority;
	private BigDecimal   completePct;
	private ScheduleDTO  schedule;
	private EventListDTO children;
	
//TODO:  add these...
//	private byte[]      description;
//	private LocationDTO location;
//	private ScheduleDTO schedule;
	
//	public EventListDTO setChildren(Set<EventDTO> children) {
//		return null;
//		
//	}
}
