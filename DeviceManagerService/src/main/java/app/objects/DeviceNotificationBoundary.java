package app.objects;

import java.util.Date;
import java.util.List;

public class DeviceNotificationBoundary {
	private String messageId;
	private Date publishedTimestamp;
	private String messageType;
	private String summary;
	private List<ExternalReferenceBoundary> externalReferences;
	private DeviceBoundaryWrapper messageDetails;
	
	public DeviceNotificationBoundary() {
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

	public DeviceBoundaryWrapper getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(DeviceBoundaryWrapper messageDetails) {
		this.messageDetails = messageDetails;
	}

	
	@Override
	public String toString() {
		return "DeviceNotificationBoundary [messageId=" + messageId + ", publishedTimestamp=" + publishedTimestamp
				+ ", messageType=" + messageType + ", summary=" + summary + ", externalReferences=" + externalReferences
				+ ", messageDetails=" + messageDetails + "]";
	}
	
}
