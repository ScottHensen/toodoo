package life.toodoo.api.errorhandling;

import org.springframework.util.StringUtils;

import life.toodoo.api.v1.model.EventDTO;

public class EditErrorException extends Exception 
{
	private static final long serialVersionUID = 1L;

	public EditErrorException(Class<EventDTO> clazz, String rule, String message) 
	{
		super(exceptionBuilder(clazz.getSimpleName(), rule, message));
	}

	
	private static String exceptionBuilder(String clazz, String rule, String message) 
	{
		return "Rule: "  + rule                          + ".  "
	         + "Class: " + StringUtils.capitalize(clazz) + ".  "
		     + "Data: "  + message                       + "."
			 ;
	}

}
