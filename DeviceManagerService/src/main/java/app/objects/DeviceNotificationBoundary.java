package app.objects;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeviceNotificationBoundary {
	private String messageId;
	private Date publishedTimestamp;
	private String messageType;
	private String summary;
	private List<ExternalReferenceBoundary> externalReferences;
	private Map<String, DeviceBoundary> messageDetails;
	
	public DeviceNotificationBoundary() {
		super();
	}

	///////////////////////////////////
	//  SUPPORT GETTERS AND SETTERS  //
	///////////////////////////////////
	public DeviceBoundary getDevice() {
		return this.messageDetails.values().iterator().next();
	}
	
	public void setDevice(DeviceBoundary device) {
		this.messageDetails.put(this.getDeviceAlias(), device);
	}
	
	public String getDeviceAlias() {
		return this.messageDetails.keySet().iterator().next();
	}
	
	public void setDeviceAlias(String alias) {
		this.messageDetails = Collections.singletonMap(alias, this.getDevice());
	}
	
	
	///////////////////////////////////
	//  BASIC BEAN METHODS           //
	///////////////////////////////////
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

	public Map<String, DeviceBoundary> getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(Map<String, DeviceBoundary> messageDetails) {
		this.messageDetails = messageDetails;
	}

	
	@Override
	public String toString() {
		return "DeviceNotificationBoundary [messageId=" + messageId + ", publishedTimestamp=" + publishedTimestamp
				+ ", messageType=" + messageType + ", summary=" + summary + ", externalReferences=" + externalReferences
				+ ", messageDetails=" + messageDetails + "]";
	}
	
}
