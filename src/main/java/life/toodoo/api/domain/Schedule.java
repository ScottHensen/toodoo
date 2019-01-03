package life.toodoo.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode( exclude = {"event"} )
public class Schedule 
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( name = "beg_ts" )
	private LocalDateTime beginTimestamp;
	
	@Column( name = "end_ts" )
	private LocalDateTime endTimestamp;
	
	@OneToOne
	private Event event;
}
