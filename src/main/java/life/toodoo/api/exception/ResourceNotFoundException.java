package life.toodoo.api.exception;

public class ResourceNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
	}	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	public ResourceNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public ResourceNotFoundException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}
}
