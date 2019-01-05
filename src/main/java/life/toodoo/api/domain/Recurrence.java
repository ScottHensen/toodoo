package life.toodoo.api.domain;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Recurrence 
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Enumerated( value = EnumType.STRING )
	@Column( name = "pattern", length = 7 )
	private RecurrencePattern pattern;
	
	@Column( name = "numeric_interval", columnDefinition = "smallint" )
	private Integer numericInterval;
	
	@Enumerated( value = EnumType.STRING )
	@Column( name = "ordinal_interval", length = 20 )
	private OrdinalInterval ordinalInterval;
	
	@Column( name = "sun" )
	private Boolean onSunday;
	@Column( name = "mon" )
	private Boolean onMonday;
	@Column( name = "tue" )
	private Boolean onTuesday;
	@Column( name = "wed" )
	private Boolean onWednesday;
	@Column( name = "thu" )
	private Boolean onThursday;
	@Column( name = "fri" )
	private Boolean onFriday;
	@Column( name = "sat" )
	private Boolean onSaturday;
	
	@Column( name = "day_of_month")
	private Integer dayOfMonth;
	
	@Enumerated( value = EnumType.STRING )
	@Column( name = "day_of_week", length = 9 )
	private DayOfWeek dayOfWeek;
	
	@Enumerated( value = EnumType.ORDINAL )
	@Column( name = "month", columnDefinition = "smallint" )
	private Month month;
	
	@OneToOne
	private Schedule schedule;
	
	
	public void setOnWeekend()
	{
		this.onSaturday = true;
		this.onSunday   = true;
	}
	
	public void setOnWeekday()
	{
		this.onMonday    = true;
		this.onTuesday   = true;
		this.onWednesday = true;
		this.onThursday  = true;
		this.onFriday    = true;
	}
	
	//TODO: What's the best collection to use?
//	public Set getDaysOfWeek()
//	{
//		Set<DayOfWeek> daysOfWeek = new HashSet<>();
//		if ()
//	}
}
