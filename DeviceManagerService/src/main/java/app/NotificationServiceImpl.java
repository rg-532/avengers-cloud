package app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.objects.DeviceEntity;
import app.objects.DeviceNotificationBoundary;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotificationServiceImpl implements NotificationService {
	ManagerService service;
	String appName;
	NotificationToEntityHelper helper;

	ObjectMapper jackson;
	StreamBridge kafka;
	String targetTopic;
	
	public NotificationServiceImpl(ManagerService service, StreamBridge kafka) {
		this.service = service;
		this.jackson = new ObjectMapper();
		this.kafka = kafka;
		
		this.helper = null;
	}
	
	@Value("${spring.application.name}")
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Value("${target.topic.name}")
	public void setTargetTopic(String targetTopic) {
		this.targetTopic = targetTopic;
	}
	
	@PostConstruct
	public void init() {
		this.helper = new NotificationToEntityHelper(this.appName);
	}
	
	/**Sends a notification to Kafka.
	 * 
	 * @param notification	Notification to send to Kafka.
	 * @return				Received notification.
	 */
	public Mono<DeviceNotificationBoundary> sendToKafka(DeviceNotificationBoundary notification) {
		return Mono.just(notification)
				.flatMap(n -> {
					try {
						return Mono.just(this.jackson.writeValueAsString(notification))
								.map(notifStr -> {return this.kafka.send(this.targetTopic, notifStr);})
								.then(Mono.just(notification));
					} catch (JsonProcessingException jpe) {
						return Mono.error(jpe);
					}
				});
	}
	

	@Override
	public Mono<DeviceNotificationBoundary> registerDevice(DeviceNotificationBoundary notification) {
		notification = this.helper.initNotification(notification);
		DeviceEntity entity = this.helper.extractEntity(notification);
		
		return this.service
				.registerDevice(entity)
				.zipWith(Mono.just(notification))
				.map(this.helper::insertEntityIntoNotification)
				.flatMap(this::sendToKafka)
				.log();
	}

	@Override
	public Mono<Void> updateDevice(String id, DeviceNotificationBoundary update) {
		update = this.helper.initNotification(update);
		DeviceEntity entity = this.helper.extractEntity(update);
		
		return this.service
				.updateDevice(id, entity)
				.then(this.service
						.getDeviceById(id))
				.zipWith(Mono.just(update))
				.map(this.helper::insertEntityIntoNotification)
				.flatMap(this::sendToKafka)
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
				.flatMap(this::sendToKafka)
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
		System.err.println(exampleEntity);
		
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
				.flatMap(this::sendToKafka)
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
				.flatMap(this::sendToKafka)
				.log()
				.then(this.service
						.deleteDeviceById(id));
	}
	
	

}
