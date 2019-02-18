package life.toodoo.api.v1.model;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotEmpty
	private String       title;
	
	private String       status;
	
	@Min(0) @Max(99)
	@NotNull( message = "tasks must be assigned a priority between 0 and 99" )
	private Integer      priority;

	private BigDecimal   completePct;
	
	private ScheduleDTO  schedule;
	
	private EventListDTO children;
	
//TODO:  add these...
//	private byte[]      description;
//	private LocationDTO location;
//	private ScheduleDTO schedule;
	
}
