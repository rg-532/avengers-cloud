/**TODO - Add calls for sending messages via Apache Kafka.
 */

package app;

import java.util.Map;

import org.springframework.stereotype.Service;

import app.objects.DeviceEntity;
import app.objects.DeviceNotificationBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
public class NotificationServiceImpl implements NotificationService {
	ManagerService service;
	NotificationToEntityHelper helper;
	
	public NotificationServiceImpl(ManagerService service) {
		this.service = service;
		this.helper = new NotificationToEntityHelper();
	}

	@Override
	public Mono<DeviceNotificationBoundary> registerDevice(DeviceNotificationBoundary notification) {
		notification = this.helper.initNotification(notification);
		DeviceEntity entity = this.helper.extractEntity(notification);
		
		return this.service
				.registerDevice(entity)
				.zipWith(Mono.just(notification))
				.map(this.helper::insertEntityIntoNotification)
				// Eject message here.
				.log();
	}

	@Override
	public Mono<Void> updateDevice(String id, DeviceNotificationBoundary update) {
		update = this.helper.initNotification(update);
		DeviceEntity entity = this.helper.extractEntity(update);
		
		return this.service
				.updateDevice(id, entity)
				.thenReturn(Tuples.of(entity, update))
				.map(this.helper::insertEntityIntoNotification)
				// Eject message here.
				.log()
				.then();
	}

	@Override
	public Mono<Void> updateDeviceStatus(String id, Map<String, Object> status) {
		String statusUpdateMessage = "Status of device with (id=" + id +") was updated.";
		
		return this.service
				.updateDeviceStatus(id, status)
				.then(this.service
						.getDeviceById(id))
				.map(entity -> {
					return this.helper
							.entityToNewNotification(entity, statusUpdateMessage);
				})
				// Eject message here.
				.log()
				.then();
	}

	@Override
	public Flux<DeviceNotificationBoundary> getAllDevices() {
		String getMessage = "Retrieval of all devices - device with (id=%s) returned.";
		
		return this.service
				.getAllDevices()
				.map(entity -> {
					return this.helper
							.entityToNewNotification(entity, getMessage.formatted(entity.getId()));
				})
				.log();
	}

	@Override
	public Mono<DeviceNotificationBoundary> getDeviceById(String id) {
		String getMessage = "Retrieval of device with (id=" + id + ").";
		
		return this.service
				.getDeviceById(id)
				.map(entity -> {
					return this.helper
							.entityToNewNotification(entity, getMessage);
				})
				.log();
	}

	@Override
	public Flux<DeviceNotificationBoundary> getDevicesByExample(DeviceNotificationBoundary example) {
		example = this.helper.initNotification(example);
		DeviceEntity exampleEntity = this.helper.extractEntity(example);
		String getMessage = "Retrieval of devices by example - device with (id=%s) returned.";
		
		return this.service
				.getDevicesByExample(exampleEntity)
				.map(entity -> {
					return this.helper
							.entityToNewNotification(entity, getMessage.formatted(entity.getId()));
				})
				.log();
	}

	@Override
	public Mono<Void> deleteAllDevices() {
		String deleteMessage = "Deletion of all devices.";
		
		return this.service
				.deleteAllDevices()
				.thenReturn(this.helper
						.entityToNewNotification(null, deleteMessage))
				// Eject message here.
				.log()
				.then();
	}

	@Override
	public Mono<Void> deleteDeviceById(String id) {
		String deleteMessage = "Deletion of device with (id=" + id + ").";
		
		return this.service
				.getDeviceById(id)
				.map(entity -> {
					return this.helper
							.entityToNewNotification(entity, deleteMessage);
				})
				// Eject message here.
				.log()
				.then(this.service
						.deleteDeviceById(id));
	}
	
	

}
