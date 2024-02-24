package app.message.objects;

public class MessageIdBoundary {
	private String messageId;

	public MessageIdBoundary() {
	}
	

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	

	@Override
	public String toString() {
		return "MessageIdBoundary [messageId=" + messageId + "]";
	}
}
