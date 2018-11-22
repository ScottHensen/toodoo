package life.toodoo.api.domain;

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
	private String   title;
	
//TODO
//	private Blob     description;
//	private Location location;
//	private Schedule schedule;
}
