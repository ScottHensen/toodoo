package life.toodoo.api.errorhandling;

import lombok.Data;

@Data
public abstract class ErrorRootCause 
{
    ErrorRootCause(String message) {
        this.message = message;
    }

    
    private String message;
	
}
