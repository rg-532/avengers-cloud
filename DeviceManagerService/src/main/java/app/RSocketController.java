package app;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RSocketController {
	ManagerService service;
	private Log logger = LogFactory.getLog(RSocketController.class);
	
	public RSocketController(ManagerService service) {
		this.service = service;
	}
	
	
	@MessageMapping("registerDevice-req-resp")
	public Mono<DeviceBoundary> registerDevice(
			@Payload DeviceBoundary device) {
		this.logger.debug("invoking: registerDevice-req-resp.");
		return this.service.registerDevice(device);
	}
	
	@MessageMapping("updateDevice-{id}-fnf")
	public Mono<Void> updateDevice(
			@DestinationVariable(value = "id") String id,
			@Payload DeviceBoundary update) {
		this.logger.debug("invoking: updateDevice-{id}-fnf with id=" + id +".");
		return this.service.updateDevice(id, update);
	}
	
	@MessageMapping("updateDeviceStatus-{id}-fnf")
	public Mono<Void> updateDeviceStatus(
			@DestinationVariable(value = "id") String id,
			@RequestBody Map<String, Object> status) {
		this.logger.debug("invoking: updateDeviceStatus-{id}-fnf with id=" + id +".");
		return this.service.updateDeviceStatus(id, status);
	}
	
	@MessageMapping("getAllDevices-req-stream")
	public Flux<DeviceBoundary> getAllDevices() {
		this.logger.debug("invoking: getAllDevices-req-stream.");
		return this.service.getAllDevices();
	}

	@MessageMapping("getDeviceById-{id}-req-resp")
	public Mono<DeviceBoundary> getDeviceById(
			@DestinationVariable(value = "id") String id) {
		this.logger.debug("invoking: getDeviceById-{id}-req-resp with id=" + id +".");
		return this.service.getDeviceById(id);
	}
	
	@MessageMapping("getDevicesByExample-req-stream")
	public Flux<DeviceBoundary> getDevicesByExample(
			@Payload DeviceBoundary example) {
		this.logger.debug("invoking: getDevicesByExample-req-stream.");
		return this.service.getDevicesByExample(example);
	}
	
	@MessageMapping("deleteAllDevices-fnf")
	public Mono<Void> deleteAllDevices() {
		this.logger.debug("invoking: deleteAllDevices-fnf.");
		return this.service.deleteAllDevices();
	}

	@MessageMapping("deleteDeviceById-{id}-fnf")
	public Mono<Void> deleteDeviceById(
			@DestinationVariable(value = "id") String id) {
		this.logger.debug("invoking: deleteDeviceById-{id}-fnf with id=" + id +".");
		return this.service.deleteDeviceById(id);
	}
}
