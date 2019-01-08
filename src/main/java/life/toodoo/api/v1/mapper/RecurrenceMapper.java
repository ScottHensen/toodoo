package life.toodoo.api.v1.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import life.toodoo.api.domain.Recurrence;
import life.toodoo.api.v1.model.RecurrenceDTO;

@Component
public class RecurrenceMapper 
{

	public RecurrenceDTO mapRecurenceToRecurrenceDto(Recurrence recurrence) 
	{
		RecurrenceDTO recurrenceDTO = new RecurrenceDTO();
		
		if ( recurrence == null )
			return recurrenceDTO;
		
		if ( recurrence.getPattern() != null )
			recurrenceDTO.setRecurrencePattern(recurrence.getPattern().toString());
		
		if ( recurrence.getOrdinalInterval() != null )
			recurrenceDTO.setOrdinalInterval(recurrence.getOrdinalInterval().toString());
		
		if ( recurrence.getDayOfWeek() != null )
			recurrenceDTO.setDayOfWeek(recurrence.getDayOfWeek().toString());
		
		if ( recurrence.getMonth() != null )
			recurrenceDTO.setMonth(recurrence.getMonth().toString());
		
		recurrenceDTO.setNumericInterval(recurrence.getNumericInterval());
		recurrenceDTO.setDayOfMonth(recurrence.getDayOfMonth());
		
		List<String> onDays    = new ArrayList<>();
		List<String> notOnDays = new ArrayList<>();

		if ( recurrence.getOnMonday() )
			onDays.add("Monday");
		else
			notOnDays.add("Monday");
		
		if ( recurrence.getOnTuesday() )
			onDays.add("Tuesday");
		else
			notOnDays.add("Tuesday");
		
		if ( recurrence.getOnWednesday() )
			onDays.add("Wednesday");
		else
			notOnDays.add("Wednesday");
		
		if ( recurrence.getOnThursday() )
			onDays.add("Thursday");
		else
			notOnDays.add("Thursday");
		
		if ( recurrence.getOnFriday() )
			onDays.add("Friday");
		else
			notOnDays.add("Friday");
		
		if ( recurrence.getOnSaturday() )
			onDays.add("Saturday");
		else
			notOnDays.add("Saturday");
		
		if ( recurrence.getOnSunday() )
			onDays.add("Sunday");
		else
			notOnDays.add("Sunday");
		
		
		Map<Boolean,List<String>> recursOnDays = new LinkedHashMap<>();
		recursOnDays.put(true, onDays);
		recursOnDays.put(false, notOnDays);
		
		recurrenceDTO.setRecursOnDays(recursOnDays);
		return recurrenceDTO;
	}

}
