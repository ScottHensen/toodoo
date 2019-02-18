package life.toodoo.api.errorhandling;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
class EditError extends ErrorRootCause 
{
    EditError(String message) {
        super(message);
    }
    
}
