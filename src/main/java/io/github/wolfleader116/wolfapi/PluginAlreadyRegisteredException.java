package io.github.wolfleader116.wolfapi;

public class PluginAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = -2000106685910962803L;
	
	public PluginAlreadyRegisteredException() {}
	
	public PluginAlreadyRegisteredException(String message) {
		super(message);
	}
	
	public PluginAlreadyRegisteredException(Throwable cause) {
		super(cause);
	}
	
	public PluginAlreadyRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PluginAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
