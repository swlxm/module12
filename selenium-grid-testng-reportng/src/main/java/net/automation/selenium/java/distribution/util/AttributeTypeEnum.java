package net.automation.selenium.java.distribution.util;

public enum AttributeTypeEnum {
	
	TEXT("text"),
	VALUE("value"), 
	CLASS("class"), 
	SRC("src"), 
	ALT("alt"), 
	ID("id"),
	NAME("name"),
	HREF("href"),
	STYLE("style");

	private String type;
	
	AttributeTypeEnum(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
