package io.github.wolfleader116.wolfapi;

public class PluginNotRegisteredException extends Exception {

	private static final long serialVersionUID = -2000106685910962803L;
	
	public PluginNotRegisteredException() {}
	
	public PluginNotRegisteredException(String message) {
		super(message);
	}
	
	public PluginNotRegisteredException(Throwable cause) {
		super(cause);
	}
	
	public PluginNotRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PluginNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
