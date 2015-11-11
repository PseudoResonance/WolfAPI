package io.github.wolfleader116.wolfapi;

import java.util.Arrays;
import java.util.List;

public class ChatElement {
	
	public ChatComponent[] components;
	public String text;
	
	public ChatElement(String text, ChatComponent... components) {
		this.components = components;
		this.text = text;
	}
	
	public ChatComponent[] components() {
		return this.components;
	}
	
	public String text() {
		return this.text;
	}
	
	public void components(ChatComponent[] components) {
		this.components = components;
	}
	
	public void text(String text) {
		this.text = text;
	}
	
	public void addComponent(ChatComponent component) {
		List<ChatComponent> componentsl = Arrays.asList(this.components);
		componentsl.add(component);
		ChatComponent[] newcomponents = (ChatComponent[]) componentsl.toArray();
		this.components = newcomponents;
	}
	
	public void addComponent(ChatComponent[] components) {
		List<ChatComponent> componentsl = Arrays.asList(this.components);
		for (ChatComponent chat : components) {
			componentsl.add(chat);
		}
		ChatComponent[] newcomponents = (ChatComponent[]) componentsl.toArray();
		this.components = newcomponents;
	}
	
	public void addComponent(List<ChatComponent> components) {
		List<ChatComponent> componentsl = Arrays.asList(this.components);
		componentsl.addAll(components);
		ChatComponent[] newcomponents = (ChatComponent[]) componentsl.toArray();
		this.components = newcomponents;
	}
	
	public String build() {
		String ret = ",{\"text\":\"" + text + "\"";
		if (components.length == 0) {
			ret = ret + "}";
		} else {
			for (ChatComponent c : components) {
				if ((c.type() == ComponentType.OPEN_URL) || (c.type() == ComponentType.RUN_COMMAND) || (c.type() == ComponentType.SUGGEST_COMMAND)) {
					ret = ret + ",\"clickEvent\":{\"action\":\"" + c.type().toString().toLowerCase() + "\",\"value\":\"" + c.value() + "\"}";
				} else if ((c.type() == ComponentType.SHOW_TEXT) || (c.type() == ComponentType.SHOW_ENTITY) || (c.type() == ComponentType.SHOW_ITEM) || (c.type() == ComponentType.SHOW_ACHIEVEMENT)) {
					ret = ret + ",\"hoverEvent\":{\"action\":\"" + c.type().toString().toLowerCase() + "\",\"value\":\"" + c.value() + "\"}";
				} else if  (c.type() == ComponentType.INSERTION) {
					ret = ret + ",\"insertion\":\"" + c.value() + "\"";
				}
			}
			ret = ret + "}";
		}
		return ret;
	}

}