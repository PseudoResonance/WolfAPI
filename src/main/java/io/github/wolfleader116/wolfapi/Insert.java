package io.github.wolfleader116.wolfapi;

public class Insert {
	
	public String text;
	public String value;
	
	public Insert(String text, String value) {
		this.text = text;
		this.value = value;
	}
	
	public String text() {
		return this.text;
	}
	
	public String value() {
		return this.value;
	}

}
