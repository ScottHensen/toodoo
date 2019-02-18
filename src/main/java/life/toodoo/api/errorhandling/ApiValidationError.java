package life.toodoo.api.errorhandling;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
class ApiValidationError extends ErrorRootCause 
{
    ApiValidationError(String object, String message) {
       super(message);
       this.object = object;
    }

    ApiValidationError(String object, String field, Object rejectedValue, String message) {
        super(message);
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
    
    private String object;
    private String field;
    private Object rejectedValue;
}
