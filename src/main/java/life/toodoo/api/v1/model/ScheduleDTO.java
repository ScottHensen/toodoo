package life.toodoo.api.v1.model;

import java.time.LocalDate;
import java.time.LocalTime;

//import life.toodoo.api.v1.model.ScheduleDTO.ScheduleDTOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO 
{
	private LocalDate beginDate;
	private LocalTime beginTime;
	private LocalDate endDate;
	private LocalTime endTime;
//	private Integer   durationHours;
//	private Boolean   allDay;
}
