package app;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/devices"})
public class HttpController {
	ManagerService service;
	
	public HttpController(ManagerService service) {
		this.service = service;
	}
	
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DeviceBoundary> registerDevice(
			@RequestBody DeviceBoundary device) {
		return this.service
				.registerDevice(device)
				.log();
	}
	
	@PutMapping(
			path = {"/{id}"},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> updateDevice(
			@PathVariable("id") String id,
			@RequestBody DeviceBoundary update) {
		return this.service
				.updateDevice(id, update)
				.log();
	}
	
	@PutMapping(
			path = {"/{id}/status"},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> updateDeviceStatus(
			@PathVariable("id") String id,
			@RequestBody Map<String, Object> status) {
		return this.service
				.updateDeviceStatus(id, status)
				.log();
	}
	
	@GetMapping(
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<DeviceBoundary> getAllDevices() {
		return this.service
				.getAllDevices()
				.log();
	}

	@GetMapping(
			path = {"/{id}"},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DeviceBoundary> getDeviceById(
			@PathVariable("id") String id) {
		return this.service
				.getDeviceById(id)
				.log();
	}
	
	@PostMapping(
			path = {"/getByExample"},
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<DeviceBoundary> getDevicesByExample(
			@RequestBody DeviceBoundary example) {
		return this.service
				.getDevicesByExample(example)
				.log();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAllDevices() {
		return this.service
				.deleteAllDevices()
				.log();
	}

	@DeleteMapping(
			path = "/{id}")
	public Mono<Void> deleteDeviceById(
			@PathVariable("id") String id) {
		return this.service
				.deleteDeviceById(id)
				.log();
	}
	
}
