package app.message.objects;

public class ExternalReferenceBoundary {
	private String service;
	private String externalServiceId;
	
	public ExternalReferenceBoundary() {
	}
	
	public ExternalReferenceBoundary(ExternalReferenceEntity entity) {
		this.setService(entity.getService());
		this.setExternalServiceId(entity.getExternalServiceId());
	}
	
	public ExternalReferenceEntity toEntity() {
		ExternalReferenceEntity entity = new ExternalReferenceEntity();
		
		entity.setService(this.getService());
		entity.setExternalServiceId(this.getExternalServiceId());
		
		return entity;
	}
	

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getExternalServiceId() {
		return externalServiceId;
	}

	public void setExternalServiceId(String externalServiceId) {
		this.externalServiceId = externalServiceId;
	}
	

	@Override
	public String toString() {
		return "ExternalReferenceBoundary [service=" + service + ", externalServiceId=" + externalServiceId + "]";
	}
}
