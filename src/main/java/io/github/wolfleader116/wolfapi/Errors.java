package io.github.wolfleader116.wolfapi;

public enum Errors {

	NO_PERMISSION,
	NOT_ONLINE,
	NEVER_JOINED,
	NOT_NUMBER,
	NOT_A_COMMAND,
	DEFAULT,
	CUSTOM;
	
	String info = null;
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return this.info;
	}

}
