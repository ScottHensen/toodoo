package life.toodoo.api.domain.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode( exclude = {"children"} )
@ToString( exclude = {"children"} )
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
    @JoinColumn( name = "schedule_id" )
    private Schedule schedule;
    
    // Event is a TREE structure
    @ManyToOne( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn( name = "parent_id" )
    private Event parent;
    
    @OneToMany( mappedBy = "parent" )
    private Set<Event> children;
    
    //TODO:  I am not sure a tree is the best structure.
    //
    //    It makes sense in the way that a task breaks down into subtasks, but..
    //    what about killing two birds with one stone?  What if your goals are  
    //    to socialize more and to learn a new skill?  Doesn't a task like 'go to
    //    the foo meetup' satisfy both?  
    //
    //    I guess doing many to many handles that in an rdbms, but I am starting
    //    to think I've come to a place where switching to neo4j might make sense.
    //
	//	  I don't think I want a true graph though - one with just a set of neighbors.
	//    Can I build a hierarchical graph?  1-* parents and 1-* children?
    //
    //    I think I just need to create an event_parent table that holds
    //    event_id and the parent(s) event_id
    //
    //    small bites...
    //      first load a simple tree and test it, then add the parent event tbl
    
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
