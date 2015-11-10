package io.github.wolfleader116.wolfapi;

public class Click {
	
	public ClickActions type;
	public String text;
	public String value;
	
	public Click(ClickActions type, String text, String value) {
		this.type = type;
		this.text = text;
		this.value = value;
	}
	
	public ClickActions type() {
		return this.type;
	}
	
	public String text() {
		return this.text;
	}
	
	public String value() {
		return this.value;
	}

}