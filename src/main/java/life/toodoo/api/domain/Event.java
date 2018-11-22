package life.toodoo.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Event 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long     id;   

    @Column( name = "title" )
    private String   title;
    
    @Column( name = "status" )
	private String   status;
    
    @Column( name = "complete_pct" )
	private BigDecimal completePct;
    
    @Column( name = "priority" )
	private Integer  priority;
	
//TODO
//	private Blob     description;
//	private Location location;
//	private Schedule schedule;
}
