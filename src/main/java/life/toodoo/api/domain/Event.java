package life.toodoo.api.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	
    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;
    
//TODO
//	private Blob     description;
//	private Location location;
    
    
    public void setSchedule(Schedule schedule) {
        if (schedule != null) {
            this.schedule = schedule;
            schedule.setEvent(this);
        }
    }

}
