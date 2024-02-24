package app.message.objects;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageBoundary {
	private String messageId;
	private Date publishedTimestamp;
	private String messageType;
	private String summary;
	private List<ExternalReferenceBoundary> externalReferences;
	private Map<String, Object> messageDetails;
	
	public MessageBoundary() {
	}
	
	public MessageBoundary(MessageEntity entity) {
		this.setMessageId(entity.getMessageId());
		this.setPublishedTimestamp(entity.getPublishedTimestamp());
		this.setMessageType(entity.getMessageType());
		this.setSummary(entity.getSummary());
		this.setExternalReferences(entity.getExternalReferences()
				.stream()
				.map(ExternalReferenceBoundary::new)
				.toList());
		this.setMessageDetails(entity.getMessageDetails());
	}
	
	public MessageEntity toEntity() {
		MessageEntity entity = new MessageEntity();
		
		entity.setMessageId(this.getMessageId());
		entity.setPublishedTimestamp(this.getPublishedTimestamp());
		entity.setMessageType(this.getMessageType());
		entity.setSummary(this.getSummary());
		entity.setExternalReferences(this.getExternalReferences()
				.stream()
				.map(ExternalReferenceBoundary::toEntity)
				.toList());
		entity.setMessageDetails(this.getMessageDetails());
		
		return entity;
	}
	

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getPublishedTimestamp() {
		return publishedTimestamp;
	}

	public void setPublishedTimestamp(Date publishedTimestamp) {
		this.publishedTimestamp = publishedTimestamp;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<ExternalReferenceBoundary> getExternalReferences() {
		return externalReferences;
	}

	public void setExternalReferences(List<ExternalReferenceBoundary> externalReferences) {
		this.externalReferences = externalReferences;
	}

	public Map<String, Object> getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(Map<String, Object> messageDetails) {
		this.messageDetails = messageDetails;
	}

	
	@Override
	public String toString() {
		return "MessageBoundary [messageId=" + messageId + ", publishedTimestamp=" + publishedTimestamp
				+ ", messageType=" + messageType + ", summary=" + summary + ", externalReferences=" + externalReferences
				+ ", messageDetails=" + messageDetails + "]";
	}
}
