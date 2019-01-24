package life.toodoo.api.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EventParents 
{
	@Id
	@Column( name = "event_id" )
	private Long eventId;
	
	@Column( name = "parent_id" )
	private Long parentId;
}
