package fr.mch.mdo.restaurant.beans.dto;

public class AcknowledgmentMessage {

	public enum Type {
		ERROR, SUCCESS
	}
	
	private Type type = Type.SUCCESS;

	private String title;

	private String message;

	private Object attachment;
	
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

	/**
	 * @return the attachment
	 */
	public Object getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "MessageDTO [type=" + type 
				+ ", title=" + title 
				+ ", message=" + message 
				+ ", attachment=" + attachment 
				+ "]";
	}
}
