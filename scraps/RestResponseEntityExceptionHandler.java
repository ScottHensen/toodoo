package life.toodoo.api.v1.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import life.toodoo.api.exception.ResourceNotFoundException;
import life.toodoo.api.v1.model.EventDTO;

//@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler( { ResourceNotFoundException.class } )
	public ResponseEntity<EventDTO> handleNotFoundException( Exception exception, WebRequest request) 
	{
		return new ResponseEntity<>( new HttpHeaders(), HttpStatus.NOT_FOUND );
	}
}
