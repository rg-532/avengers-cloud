package app;

import java.util.Date;
import java.util.Map;

public class DeviceBoundary {
	private String id;
	private String type;
	private String alias;
	private Date registrationTimestamp;
	private Date lastUpdateTimestamp;
	private Map<String, Object> status;

	
	public DeviceBoundary() {
	}
	
	public DeviceBoundary(DeviceEntity entity) {
		this.setId(entity.getId());
		this.setType(entity.getType());
		this.setAlias(entity.getAlias());
		this.setRegistrationTimestamp(entity.getRegistrationTimestamp());
		this.setLastUpdateTimestamp(entity.getLastUpdateTimestamp());
		this.setStatus(entity.getStatus());
	}
	
	public DeviceEntity toEntity() {
		DeviceEntity entity = new DeviceEntity();
		
		entity.setId(this.getId());
		entity.setType(this.getType());
		entity.setAlias(this.getAlias());
		entity.setRegistrationTimestamp(this.getRegistrationTimestamp());
		entity.setLastUpdateTimestamp(this.getLastUpdateTimestamp());
		entity.setStatus(this.getStatus());
		
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public Map<String, Object> getStatus() {
		return status;
	}

	public void setStatus(Map<String, Object> status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		return "DeviceBoundary [id=" + id + ", type=" + type + ", alias=" + alias + ", registrationTimestamp="
				+ registrationTimestamp + ", lastUpdateTimestamp=" + lastUpdateTimestamp + ", status=" + status + "]";
	}
	
}
