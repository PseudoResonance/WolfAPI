package io.github.wolfleader116.wolfapi;

public class Hover {
	
	public HoverActions type;
	public String text;
	public String value;
	
	public Hover(HoverActions type, String text, String value) {
		this.type = type;
		this.text = text;
		this.value = value;
	}
	
	public HoverActions type() {
		return this.type;
	}
	
	public String text() {
		return this.text;
	}
	
	public String value() {
		return this.value;
	}

}
