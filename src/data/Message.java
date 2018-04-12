package data;

public class Message {
	
	//ATTRIBUTES------------------------------------------------------
	private long chat_id;
	private String text;
	
	//CONSTRUCTORS----------------------------------------------------
	public Message(long chat_id, String text) {
		super();
		this.chat_id = chat_id;
		this.text = text;
	}	
	public Message() {
		super();
	}
	
	//GETTERS and SETTERS
	public long getChat_id() {
		return chat_id;
	}
	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Message [chat_id=" + chat_id + ", text=" + text + "]";
	}
	
}
