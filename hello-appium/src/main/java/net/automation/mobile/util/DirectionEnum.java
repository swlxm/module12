package net.automation.mobile.util;

public enum DirectionEnum {

	UP("Single line of text"),
	DOWN("Rich Text"),
	LEFT("Drop Down List"),
	RIGHT("Check Box");

	private String direction;
	
	DirectionEnum(String direction) {
		this.direction = direction;
	}
	
	public String getType() {
		return this.direction;
	}
	
	@Override
	public String toString() {
		return direction;
	}

}
