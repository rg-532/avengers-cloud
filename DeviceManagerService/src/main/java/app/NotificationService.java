package app;

import java.util.Map;

import app.objects.DeviceNotificationBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {
	
	public Mono<DeviceNotificationBoundary> registerDevice(DeviceNotificationBoundary notification);
	public Mono<Void> updateDevice(String id, DeviceNotificationBoundary update);
	public Mono<Void> updateDeviceStatus(String id, Map<String, Object> status);
	public Flux<DeviceNotificationBoundary> getAllDevices();
	public Mono<DeviceNotificationBoundary> getDeviceById(String id);
	public Flux<DeviceNotificationBoundary> getDevicesByExample(DeviceNotificationBoundary example);
	public Mono<Void> deleteAllDevices();
	public Mono<Void> deleteDeviceById(String id);

}
