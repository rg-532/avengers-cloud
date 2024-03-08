package app.objects;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("DEVICES")
public class DeviceEntity {
	@Id private String id;
	private String type;
	private String subType;
	private Date registrationTimestamp;
	private Date lastUpdateTimestamp;
	private String location;
	private Double manufacturerPowerInWatts;
	private Map<String, Object> additionalAttributes;
	private Map<String, Object> status;
	
	// Stored from messageDetails key.
	private String alias;

	
	public DeviceEntity() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSubType() {
		return subType;
	}


	public void setSubType(String subType) {
		this.subType = subType;
	}


	public Date getRegistrationTimestamp() {
		return registrationTimestamp;
	}


	public void setRegistrationTimestamp(Date registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}


	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}


	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Double getManufacturerPowerInWatts() {
		return manufacturerPowerInWatts;
	}


	public void setManufacturerPowerInWatts(Double manufacturerPowerInWatts) {
		this.manufacturerPowerInWatts = manufacturerPowerInWatts;
	}


	public Map<String, Object> getAdditionalAttributes() {
		return additionalAttributes;
	}


	public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}


	public Map<String, Object> getStatus() {
		return status;
	}


	public void setStatus(Map<String, Object> status) {
		this.status = status;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	@Override
	public String toString() {
		return "DeviceEntity [id=" + id + ", type=" + type + ", subType=" + subType + ", registrationTimestamp="
				+ registrationTimestamp + ", lastUpdateTimestamp=" + lastUpdateTimestamp + ", location=" + location
				+ ", manufacturerPowerInWatts=" + manufacturerPowerInWatts + ", additionalAttributes="
				+ additionalAttributes + ", status=" + status + ", alias=" + alias + "]";
	}

}
