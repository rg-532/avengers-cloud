package app.objects;

import java.util.Date;
import java.util.Map;

public class DeviceBoundary {
	private String id;
	private String type;
	private String subType;
	private Date registrationTimestamp;
	private Date lastUpdateTimestamp;
	private String location;
	private double manufacturerPowerInWatts;
	private Map<String, Object> additionalAttributes;
	private Map<String, Object> status;

	
	public DeviceBoundary() {
	}
	
	public DeviceBoundary(DeviceEntity entity) {
		this.setId(entity.getId());
		this.setType(entity.getType());
		this.setSubType(entity.getSubType());
		this.setRegistrationTimestamp(entity.getRegistrationTimestamp());
		this.setLastUpdateTimestamp(entity.getLastUpdateTimestamp());
		this.setLocation(entity.getLocation());
		this.setManufacturerPowerInWatts(entity.getManufacturerPowerInWatts());
		this.setAdditionalAttributes(entity.getAdditionalAttributes());
		this.setStatus(entity.getStatus());
	}
	
	public DeviceEntity toEntity() {
		DeviceEntity entity = new DeviceEntity();
		
		entity.setId(this.getId());
		entity.setType(this.getType());
		entity.setSubType(this.getSubType());
		entity.setRegistrationTimestamp(this.getRegistrationTimestamp());
		entity.setLastUpdateTimestamp(this.getLastUpdateTimestamp());
		entity.setLocation(this.getLocation());
		entity.setManufacturerPowerInWatts(this.getManufacturerPowerInWatts());
		entity.setAdditionalAttributes(this.getAdditionalAttributes());
		entity.setStatus(this.getStatus());
		
		// Set at service level.
		entity.setAlias("_to_set");
		
		return entity;
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

	public double getManufacturerPowerInWatts() {
		return manufacturerPowerInWatts;
	}

	public void setManufacturerPowerInWatts(double manufacturerPowerInWatts) {
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

	@Override
	public String toString() {
		return "DeviceBoundary [id=" + id + ", type=" + type + ", subType=" + subType + ", registrationTimestamp="
				+ registrationTimestamp + ", lastUpdateTimestamp=" + lastUpdateTimestamp + ", location=" + location
				+ ", manufacturerPowerInWatts=" + manufacturerPowerInWatts + ", additionalAttributes="
				+ additionalAttributes + ", status=" + status + "]";
	}
	
}
