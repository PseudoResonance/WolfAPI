package io.github.wolfleader116.wolfapi;

public class SubCommand {
	
	private String name = null;
	private String description = null;
	private String permission = null;
	private SubCommandExecutor subcommandexecutor = null;
	
	public SubCommand(String name, String description, String permission, SubCommandExecutor subcommandexecutor) {
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.subcommandexecutor = subcommandexecutor;
	}
	
	public SubCommand(String name, String description, SubCommandExecutor subcommandexecutor) {
		this.name = name;
		this.description = description;
		this.permission = "";
		this.subcommandexecutor = subcommandexecutor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public SubCommandExecutor getSubCommandExecutor() {
		return this.subcommandexecutor;
	}

}
