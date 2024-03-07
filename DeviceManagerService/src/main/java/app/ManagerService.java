package app;

import java.util.Map;

import app.objects.DeviceEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManagerService {
	
	public Mono<DeviceEntity> registerDevice(DeviceEntity device);
	public Mono<Void> updateDevice(String id, DeviceEntity update);
	public Mono<Void> updateDeviceStatus(String id, Map<String, Object> status);
	public Flux<DeviceEntity> getAllDevices();
	public Mono<DeviceEntity> getDeviceById(String id);
	public Flux<DeviceEntity> getDevicesByExample(DeviceEntity example);
	public Mono<Void> deleteAllDevices();
	public Mono<Void> deleteDeviceById(String id);

}
