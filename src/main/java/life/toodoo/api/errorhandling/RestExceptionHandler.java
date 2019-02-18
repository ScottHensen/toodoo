package life.toodoo.api.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/*
 * Swiped from: - https://www.toptal.com/java/spring-boot-rest-api-error-handling
 *              - https://github.com/brunocleite/spring-boot-exception-handling
 *              
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler 
{

    // Handles EntityNotFoundException.  (Data not found on datastore.)
    //    [Hand-Rolled alternative to javax's EntityNotFoundException]
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) 
    {
        Error error = new Error(NOT_FOUND);
        error.setMessage(ex.getMessage());
        ResponseEntity<Object> response = buildResponseEntity(error);
        log.warn(response.toString());
        return response;
    }
    
    // Handles EditErrorException.  (Edit error due to business-rule violation)
    //    [Hand-Rolled]
    @ExceptionHandler(EditErrorException.class)
    protected ResponseEntity<Object> handleEditError(
            EditErrorException ex) 
    {
        Error error = new Error(BAD_REQUEST);
        error.setMessage("Edit Error.  Data did not meet business-rule requirements.");
        error.setDebugMessage(ex.getMessage());
        ResponseEntity<Object> response = buildResponseEntity(error);
        log.warn(response.toString());
        return response;
    }
    

    
    // Handles javax.validation.ConstraintViolationException. (@Validated failed.)
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(
	        javax.validation.ConstraintViolationException ex) 
	{
	    Error apiError = new Error(BAD_REQUEST);
	    apiError.setMessage("API Validation error");
	    apiError.addValidationErrors(ex.getConstraintViolations());
        ResponseEntity<Object> response = buildResponseEntity(apiError);
        log.warn(response.toString());
        return response;
	}

	// Handle DataIntegrityViolationException, inspects the cause for different DB causes.
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) 
	{
	    if (ex.getCause() instanceof ConstraintViolationException) {
	        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.CONFLICT, "Database error", ex.getCause()));
	        log.warn(response.toString());
	        return response;
	    }
        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR, ex));
        log.warn(response.toString());
        return response;
	}

	// Handle javax.persistence.EntityNotFoundException
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) 
	{
        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.NOT_FOUND, ex));
        log.warn(response.toString());
        return response;
	}

	// Handle HttpMediaTypeNotSupportedException.  (Bad mediatype on request) 
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	        HttpMediaTypeNotSupportedException ex,
	        HttpHeaders headers,
	        HttpStatus status,
	        WebRequest request) 
	{
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getContentType());
	    builder.append(" media type is not supported. Supported media types are ");
	    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
        log.warn(response.toString());
        return response;
	}

	// Handle HttpMessageNotReadableException. (JSON is malformed)
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
	    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
	    String error = "Malformed JSON request";
        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, error, ex));
	    log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        log.warn(response.toString());
        return response;
	}

	// Handle HttpMessageNotWritableException.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(
			HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
	    String error = "Error writing JSON output";
        ResponseEntity<Object> response = buildResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
        log.warn(response.toString());
        return response;
	}

	// Handle MethodArgumentNotValidException.  (@Valid failed)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatus status,
	        WebRequest request) 
	{
	    Error apiError = new Error(BAD_REQUEST);
	    apiError.setMessage("Validation error");
	    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
	    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        ResponseEntity<Object> response = buildResponseEntity(apiError);
        log.warn(response.toString());
        return response;
	}

	// Handle Exception, handle generic Exception.class
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
	                                                                  WebRequest request) {
	    Error apiError = new Error(BAD_REQUEST);
	    apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
	    apiError.setDebugMessage(ex.getMessage());
        ResponseEntity<Object> response = buildResponseEntity(apiError);
        log.warn(response.toString());
        return response;
	}

	// Handle MissingServletRequestParameterException.  (required params missing in request)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) 
    {
        String error = ex.getParameterName() + " parameter is missing";
        ResponseEntity<Object> response = buildResponseEntity(new Error(BAD_REQUEST, error, ex));
        log.warn(response.toString());
        return response;
    }


    // Handle NoHandlerFoundException.
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) 
    {
        Error apiError = new Error(BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        ResponseEntity<Object> response = buildResponseEntity(apiError);
        log.warn(response.toString());
        return response;
    }

    
    private ResponseEntity<Object> buildResponseEntity(Error apiError) 
    {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}