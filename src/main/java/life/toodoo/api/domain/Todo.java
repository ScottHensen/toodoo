package life.toodoo.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Todo 
{
    @Id
	private Long id;
    
    @Column( name = "status" )
	private String status;
    
    @Column( name = "complete_pct" )
	private BigDecimal completePct;
    
    @Column( name = "priority" )
	private String priority;
    
    @OneToOne( fetch = FetchType.LAZY )
    @MapsId
    private Event event;
    
}
