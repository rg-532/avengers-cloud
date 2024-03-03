package app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/rsocket/devices"})
public class HttpRSocketTestController {
	private RSocketRequester requester;
	private RSocketRequester.Builder requesterBuilder;
	private String rsocketHost;
	private int rsocketPort;
	
	@Autowired
	public void setRequesterBuilder(RSocketRequester.Builder requesterBuilder) {
		this.requesterBuilder = requesterBuilder;
	}
	
	@Value("${custom.app.rsocket.host}")
	public void setRsocketHost(String rsocketHost) {
		this.rsocketHost = rsocketHost;
	}
	
	@Value("${custom.app.rsocket.port}")
	public void setRsocketPort(int rsocketPort) {
		this.rsocketPort = rsocketPort;
	}
	
	@PostConstruct
	public void init() {
		this.requester = this.requesterBuilder
				.tcp(this.rsocketHost, this.rsocketPort);
	}
	
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DeviceBoundary> registerDevice(
			@RequestBody DeviceBoundary device) {
		return this.requester
				.route("registerDevice-req-resp")
				.data(device)
				.retrieveMono(DeviceBoundary.class)
				.log();
	}
	
	@PutMapping(
			path = {"/{id}"},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> updateDevice(
			@PathVariable("id") String id,
			@RequestBody DeviceBoundary update) {
		return this.requester
				.route("updateDevice-{id}-fnf", id)
				.data(update)
				.send()
				.log();
	}
	
	@PutMapping(
			path = {"/{id}/status"},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<Void> updateDeviceStatus(
			@PathVariable("id") String id,
			@RequestBody Map<String, Object> status) {
		return this.requester
				.route("updateDeviceStatus-{id}-fnf", id)
				.data(status)
				.send()
				.log();
	}
	
	@GetMapping(
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<DeviceBoundary> getAllDevices() {
		return this.requester
				.route("getAllDevices-req-stream")
				.retrieveFlux(DeviceBoundary.class)
				.log();
	}

	@GetMapping(
			path = {"/{id}"},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DeviceBoundary> getDeviceById(
			@PathVariable("id") String id) {
		return this.requester
				.route("getDeviceById-{id}-req-resp", id)
				.retrieveMono(DeviceBoundary.class)
				.log();
	}
	
	@PostMapping(
			path = {"/getByExample"},
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<DeviceBoundary> getDevicesByExample(
			@RequestBody DeviceBoundary example) {
		return this.requester
				.route("getDevicesByExample-req-stream")
				.data(example)
				.retrieveFlux(DeviceBoundary.class)
				.log();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAllDevices() {
		return this.requester
				.route("deleteAllDevices-fnf")
				.send()
				.log();
	}

	@DeleteMapping(
			path = "/{id}")
	public Mono<Void> deleteDeviceById(
			@PathVariable("id") String id) {
		return this.requester
				.route("deleteDeviceById-{id}-fnf", id)
				.send()
				.log();
	}

}
