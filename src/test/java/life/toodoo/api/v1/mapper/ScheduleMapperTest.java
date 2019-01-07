package life.toodoo.api.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import life.toodoo.api.domain.Schedule;
import life.toodoo.api.v1.model.ScheduleDTO;

public class ScheduleMapperTest 
{
	RecurrenceMapper recurrenceMapper;
	ScheduleMapper scheduleMapper;
	
	@Before
	public void setUp() throws Exception 
	{
		recurrenceMapper = new RecurrenceMapper();
		scheduleMapper = new ScheduleMapper(recurrenceMapper);
	}
	
	@Test
	public void testScheduleDTOtoSchedule_nullScheduleDTO()
	{
		// when
		Schedule schedule = scheduleMapper.mapScheduleDTOtoSchedule(null);
		
		// then
		assertThat(schedule).isEqualTo(new Schedule()); 
	}
	
	@Test
	public void testScheduleDTOtoSchedule_noBeginDate_noEndDate()
	{
		// given
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		
		// when
		Schedule schedule = scheduleMapper.mapScheduleDTOtoSchedule(scheduleDTO);
		
		// then
		assertThat(schedule.getBeginTimestamp()).isAfter(LocalDateTime.now().minusSeconds(1));
		assertThat(schedule.getBeginTimestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
		assertThat(schedule.getEndTimestamp()).isNull();
	}
	
	@Test
	public void testScheduleDTOtoSchedule_noBeginTime_noEndTime()
	{
		// given
		ScheduleDTO scheduleDTO = 
				ScheduleDTO.builder()
					.beginDate(LocalDate.now())
					.endDate(LocalDate.now())
					.build();
		
		// when
		Schedule schedule = scheduleMapper.mapScheduleDTOtoSchedule(scheduleDTO);
		
		// then
		assertThat(schedule.getBeginTimestamp()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
		assertThat(schedule.getEndTimestamp()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
	}
	
	@Test
	public void testScheduleDTOtoSchedule_beginTime_after_endTime()
	{
		// given
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		
		ScheduleDTO scheduleDTO = 
				ScheduleDTO.builder()
					.beginDate(nowDate)
					.beginTime(nowTime.plusMinutes(1))
					.endDate(nowDate)
					.endTime(nowTime)
					.build();
		
		// when
		Schedule schedule = scheduleMapper.mapScheduleDTOtoSchedule(scheduleDTO);
		
		// then
		assertThat(schedule.getBeginTimestamp()).isEqualTo(LocalDateTime.of(nowDate, nowTime.plusMinutes(1)));
		assertThat(schedule.getEndTimestamp()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
	}
	
	@Test
	public void testScheduleDTOtoSchedule_validBeginTime_validEndTime()
	{
		// given
		LocalDateTime from = LocalDateTime.now();
		LocalDateTime to   = LocalDateTime.now().plusDays(1);
		ScheduleDTO scheduleDTO = 
				ScheduleDTO.builder()
					.beginDate(from.toLocalDate())
					.beginTime(from.toLocalTime())
					.endDate(to.toLocalDate())
					.endTime(to.toLocalTime())
					.build();
		
		// when
		Schedule schedule = scheduleMapper.mapScheduleDTOtoSchedule(scheduleDTO);
		
		// then
		assertThat(schedule.getBeginTimestamp()).isEqualTo(from);
		assertThat(schedule.getEndTimestamp()).isEqualTo(to);
	}

	@Test
	public void testScheduleToScheduleDTO_nullSchedule()
	{
		// when
		ScheduleDTO scheduleDTO = scheduleMapper.mapScheduleToScheduleDto(null);
		
		// then
		assertThat(scheduleDTO).isEqualTo(new ScheduleDTO()); 
	}
	
	@Test
	public void testScheduleToScheduleDto_noBeginTs_noEndTs()
	{
		// given
		Schedule schedule = new Schedule();
		
		// when
		ScheduleDTO scheduleDTO = scheduleMapper.mapScheduleToScheduleDto(schedule);
		
		// then
		assertThat(scheduleDTO.getBeginDate()).isNull();
		assertThat(scheduleDTO.getBeginTime()).isNull();
		assertThat(scheduleDTO.getEndDate()).isNull();
		assertThat(scheduleDTO.getEndTime()).isNull();
	}
	
	@Test
	public void testScheduleToScheduleDto_givenBeginTs_noEndTs()
	{
		// given
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		LocalDateTime now = LocalDateTime.of(nowDate, nowTime);
		
		Schedule schedule = new Schedule();
		schedule.setBeginTimestamp(now);
		
		// when
		ScheduleDTO scheduleDTO = scheduleMapper.mapScheduleToScheduleDto(schedule);
		
		// then
		assertThat(scheduleDTO.getBeginDate()).isEqualTo(nowDate);
		assertThat(scheduleDTO.getBeginTime()).isEqualTo(nowTime);
		assertThat(scheduleDTO.getEndDate()).isNull();
		assertThat(scheduleDTO.getEndTime()).isNull();
	}
	
	@Test
	public void testScheduleToScheduleDto_givenBeginTs_givenEndTs()
	{
		// given
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		LocalDateTime now = LocalDateTime.of(nowDate, nowTime);
		LocalDate tomorrowDate = LocalDate.now().plusDays(1);
		LocalDateTime tomorrow = LocalDateTime.of(tomorrowDate, nowTime);
		Schedule schedule = new Schedule();
		schedule.setBeginTimestamp(now);
		schedule.setEndTimestamp(tomorrow);
		
		// when
		ScheduleDTO scheduleDTO = scheduleMapper.mapScheduleToScheduleDto(schedule);
		
		// then
		assertThat(scheduleDTO.getBeginDate()).isEqualTo(nowDate);
		assertThat(scheduleDTO.getBeginTime()).isEqualTo(nowTime);
		assertThat(scheduleDTO.getEndDate()).isEqualTo(tomorrowDate);
		assertThat(scheduleDTO.getEndTime()).isEqualTo(nowTime);
	}
	
}

