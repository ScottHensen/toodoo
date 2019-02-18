package life.toodoo.api.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import life.toodoo.api.util.LowerCaseClassNameResolver;

import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 * Swiped from: - https://www.toptal.com/java/spring-boot-rest-api-error-handling
 *              - https://github.com/brunocleite/spring-boot-exception-handling
 *              
 */
@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, 
              property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class Error 
{
    private Error() {
        timestamp = LocalDateTime.now();
    }

    public Error(HttpStatus status) {
        this();
        this.status = status;
    }

    public Error(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public Error(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ErrorRootCause> rootCauseErrors;

    
    
    public void addValidationErrors(List<FieldError> fieldErrors) 
    {
        fieldErrors.forEach(this::addValidationError);
    }

    public void addValidationError(List<ObjectError> globalErrors) 
    {
        globalErrors.forEach(this::addValidationError);
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) 
    {
        constraintViolations.forEach(this::addValidationError);
    }

    
    
    private void addValidationError(FieldError fieldError) 
    {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private void addValidationError(ObjectError objectError) 
    {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    private void addValidationError(ConstraintViolation<?> cv) 
    {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) 
    {
        addRootCauseError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) 
    {
        addRootCauseError(new ApiValidationError(object, message));
    }

    private void addRootCauseError(ErrorRootCause rootCause) 
    {
        if (rootCauseErrors == null) {
            rootCauseErrors = new ArrayList<>();
        }
        rootCauseErrors.add(rootCause);
    }

}
