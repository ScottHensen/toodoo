package life.toodoo.api.v1.model;

import java.time.LocalDate;
import java.time.LocalTime;

//import life.toodoo.api.v1.model.ScheduleDTO.ScheduleDTOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScheduleDTO 
{
	public ScheduleDTO() {
		this.recurrence = new RecurrenceDTO();
	}

	private LocalDate beginDate;
	private LocalTime beginTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private Integer   durationInMinutess;
//	private Boolean   allDay;
	private RecurrenceDTO recurrence;
	
	
}
