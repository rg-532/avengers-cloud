package app;

import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManagerService {
	
	public Mono<DeviceBoundary> registerDevice(DeviceBoundary device);
	public Mono<Void> updateDevice(String id, DeviceBoundary update);
	public Mono<Void> updateDeviceStatus(String id, Map<String, Object> status);
	public Flux<DeviceBoundary> getAllDevices();
	public Mono<DeviceBoundary> getDeviceById(String id);
	public Flux<DeviceBoundary> getDevicesByExample(DeviceBoundary example);
	public Mono<Void> deleteAllDevices();
	public Mono<Void> deleteDeviceById(String id);

}
