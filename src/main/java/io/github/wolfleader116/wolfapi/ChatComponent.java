package io.github.wolfleader116.wolfapi;

public enum ChatComponent {
	
	RUN_COMMAND("run_command"),
	SUGGEST_COMMAND("suggest_command"),
	OPEN_URL("open_url"),
	SHOW_TEXT("show_text"),
	SHOW_ITEM("show_item"),
	SHOW_ACHIEVEMENT("show_achievement"),
	SHOW_ENTITY("show_entity"),
	INSERTION("insertion");
	
	private ChatComponent(String type) {
		this.type = type;
	}
	
	public String value;
	public String type;
	
	public String type() {
		return this.type;
	}
	
	public String value() {
		return this.value;
	}
	
	public ChatComponent value(String value) {
		this.value = value;
		return this;
	}

}