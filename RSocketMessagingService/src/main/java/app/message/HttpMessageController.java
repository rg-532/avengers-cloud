package app.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.message.objects.ExternalReferenceBoundary;
import app.message.objects.MessageBoundary;
import app.message.objects.MessageIdBoundary;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**Serves as an end-point for testing the service. This controller connects to the RSocketMessageController,
 * and is accessible on server.port.
 */
@RestController
@RequestMapping(path = {"/rsocket/messages"})
public class HttpMessageController {
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
	public Mono<MessageBoundary> publishMessage(
			@RequestBody MessageBoundary message) {
		return this.requester
				.route("publish-message-req-resp")
				.data(message)
				.retrieveMono(MessageBoundary.class)
				.log();
	}
	
	@GetMapping(
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<MessageBoundary> getAll() {
		return this.requester
				.route("getAll-req-stream")
				.retrieveFlux(MessageBoundary.class)
				.log();
	}
	
	@GetMapping(
			path = {"/{commaSeparatedIds}"},
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<MessageBoundary> getMessagesByIds(
			@PathVariable("commaSeparatedIds") String commaSeparatedIds) {
		Flux<MessageIdBoundary> ids = Flux.fromArray(commaSeparatedIds
				.split(","))
				.map(idString -> {
					MessageIdBoundary idBoundary = new MessageIdBoundary();
					idBoundary.setMessageId(idString.trim());
					return idBoundary;
				});
		
		return this.requester
				.route("getMessagesByIds-channel")
				.data(ids)
				.retrieveFlux(MessageBoundary.class)
				.log();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAll() {
		return this.requester
				.route("deleteAll-fire-and-forget")
				.send()
				.log();
	}
	
	// Bonus: get by external reference:
	@PostMapping(
			path = {"/byExternalReferences"},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<MessageBoundary> getMessagesByIds(
			@RequestBody List<ExternalReferenceBoundary> externalReferences) {
		Flux<ExternalReferenceBoundary> externalReferenceBoundaries = Flux.fromArray(
				externalReferences.toArray(new ExternalReferenceBoundary[0]));
		
		return this.requester
				.route("getMessagesByExternalReferences-channel")
				.data(externalReferenceBoundaries)
				.retrieveFlux(MessageBoundary.class)
				.log();
	}
}
