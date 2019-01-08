package life.toodoo.api.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import life.toodoo.api.domain.OrdinalInterval;
import life.toodoo.api.domain.Recurrence;
import life.toodoo.api.domain.RecurrencePattern;
import life.toodoo.api.v1.model.RecurrenceDTO;

public class RecurrenceMapperTest 
{
	RecurrenceMapper recurrenceMapper;
	
	@Before
	public void setUp() throws Exception 
	{
		recurrenceMapper = new RecurrenceMapper();
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_nullRecurrence() 
	{
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(null);
		
		// then
		assertThat(recurrenceDTO).isEqualTo(new RecurrenceDTO());
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_emptyRecurrence() 
	{
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(new Recurrence());
		
		// then
		assertThat(recurrenceDTO.getDayOfMonth()).isNull();
		assertThat(recurrenceDTO.getDayOfWeek()).isNull();
		assertThat(recurrenceDTO.getMonth()).isNull();
		assertThat(recurrenceDTO.getNumericInterval()).isNull();
		assertThat(recurrenceDTO.getOrdinalInterval()).isNull();
		assertThat(recurrenceDTO.getRecurrencePattern()).isNull();
		assertThat(recurrenceDTO.getRecursOnDays().get(false).size()).isEqualTo(7);
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_dailyRecurrence() 
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setPattern(RecurrencePattern.DAILY);
		
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.DAILY.toString());
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_secondTuesdayRecurrence() 
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setPattern(RecurrencePattern.WEEKLY);
		recurrence.setDayOfWeek(DayOfWeek.TUESDAY);
		recurrence.setOrdinalInterval(OrdinalInterval.SECOND);
		
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.WEEKLY.toString());
		assertThat(recurrenceDTO.getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY.toString());
		assertThat(recurrenceDTO.getOrdinalInterval()).isEqualTo(OrdinalInterval.SECOND.toString());
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_firstOfTheMonthRecurrence() 
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setPattern(RecurrencePattern.MONTHLY);
		recurrence.setDayOfMonth(1);
		
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.MONTHLY.toString());
		assertThat(recurrenceDTO.getDayOfMonth()).isEqualTo(1);
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_firstMondayOfEachQuarterRecurrence() 
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setOrdinalInterval(OrdinalInterval.FIRST);
		recurrence.setDayOfWeek(DayOfWeek.MONDAY);
		recurrence.setPattern(RecurrencePattern.MONTHLY);
		recurrence.setNumericInterval(3);
		
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.MONTHLY.toString());
		assertThat(recurrenceDTO.getNumericInterval()).isEqualTo(3);
		assertThat(recurrenceDTO.getOrdinalInterval()).isEqualTo(OrdinalInterval.FIRST.toString());
		assertThat(recurrenceDTO.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY.toString());
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_everyOtherFourthOfJuly() 
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setPattern(RecurrencePattern.YEARLY);
		recurrence.setDayOfMonth(4);
		recurrence.setMonth(Month.JULY);
		recurrence.setNumericInterval(2);
		
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.YEARLY.toString());
		assertThat(recurrenceDTO.getNumericInterval()).isEqualTo(2);
		assertThat(recurrenceDTO.getMonth()).isEqualTo(Month.JULY.toString());
		assertThat(recurrenceDTO.getDayOfMonth()).isEqualTo(4);
	}

	@Test
	public void testRecurrenceToRecurrenceDTO_everyMondayWednesdayFriday()
	{
		// given
		Recurrence recurrence = new Recurrence();
		recurrence.setPattern(RecurrencePattern.DAILY);
		recurrence.setOnMonday(true);
		recurrence.setOnWednesday(true);
		recurrence.setOnFriday(true);
				
		// when
		RecurrenceDTO recurrenceDTO = recurrenceMapper.mapRecurenceToRecurrenceDto(recurrence);
		
		// then
		assertThat(recurrenceDTO.getRecurrencePattern()).isEqualTo(RecurrencePattern.DAILY.toString());
		System.out.println(recurrenceDTO.getRecursOnDays().toString());
		assertThat(recurrenceDTO.getRecursOnDays().get(true).size()).isEqualTo(3);
		assertThat(recurrenceDTO.getRecursOnDays().get(true).contains("Monday")).isTrue();
		assertThat(recurrenceDTO.getRecursOnDays().get(false).contains("Tuesday")).isTrue();
	}

}
