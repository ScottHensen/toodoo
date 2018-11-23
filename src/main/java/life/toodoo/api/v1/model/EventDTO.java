package life.toodoo.api.v1.model;

import java.math.BigDecimal;

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
	@ApiModelProperty( required = true )
	private String      title;
	
	private String      status;
	private Integer     priority;
	private BigDecimal  completePct;
//TODO:  add these...
//	private byte[]      description;
//	private LocationDTO location;
//	private ScheduleDTO schedule;
}
