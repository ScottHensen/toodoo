package life.toodoo.api.v1.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import life.toodoo.api.v1.model.RecurrenceDTO.RecurrenceDTOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecurrenceDTO 
{
	public RecurrenceDTO() {
		this.recursOnDays = new LinkedHashMap<>();
	}
	
	
	private String  recurrencePattern;
	private Integer numericInterval;
	private String  ordinalInterval;
	private Map<Boolean,List<String>> recursOnDays;
	private String  dayOfWeek;
	private Integer dayOfMonth;
	private String  month;
}
