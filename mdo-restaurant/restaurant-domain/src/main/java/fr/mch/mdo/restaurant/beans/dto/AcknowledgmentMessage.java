package fr.mch.mdo.restaurant.beans.dto;

public class AcknowledgmentMessage {

	public enum Type {
		ERROR, SUCCESS
	}
	
	private Type type = Type.ERROR;

	private String title;

	private String message;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MessageDTO [type=" + type + ", title=" + title + ", message="
				+ message + "]";
	}
}
