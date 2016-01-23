package io.github.wolfleader116.wolfapi;

public class PluginNotFoundException extends Exception {

	private static final long serialVersionUID = -2000106685910962803L;
	
	public PluginNotFoundException() {}
	
	public PluginNotFoundException(String message) {
		super(message);
	}
	
	public PluginNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public PluginNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PluginNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
