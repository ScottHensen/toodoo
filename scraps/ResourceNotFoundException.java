package life.toodoo.api.exception;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import life.toodoo.api.domain.entity.Event;
import life.toodoo.api.util.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
		log.debug("sup cat?");
	}	
	public ResourceNotFoundException(String msg) {
		super(msg);
		log.debug("sup dog?");
	}
	public ResourceNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
		log.debug("sup pig?");
	}
	public ResourceNotFoundException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
		log.debug("sup dek?");
	}
	public ResourceNotFoundException(Class<Event> class1, String... searchParamsMap) {
		super(generateMessage(
        		class1.getSimpleName(), 
           		Converter.toMap(String.class, String.class, searchParamsMap)));
		log.debug("sup fin?");
	}
	
    private static String generateMessage(String entity, Map<String, String> searchParams ) 
    {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }
//    private static String generateMessage(String entity, Map<String, String> searchParams) 
//    {
//        return StringUtils.capitalize(entity) +
//                " was not found for parameters " +
//                searchParams;
//    }

}
