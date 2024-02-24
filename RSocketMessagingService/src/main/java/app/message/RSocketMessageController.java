package app.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import app.message.objects.ExternalReferenceBoundary;
import app.message.objects.MessageBoundary;
import app.message.objects.MessageIdBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**Accessible on property spring.rsocket.server.port.
 */
@Controller
public class RSocketMessageController {
	private MessageService service;
	private Log logger = LogFactory.getLog(RSocketMessageController.class);
	
	public RSocketMessageController(MessageService service) {
		this.service = service;
	}
	
	
	@MessageMapping("publish-message-req-resp")
	public Mono<MessageBoundary> publishMessage(
			@Payload MessageBoundary message) {
		this.logger.debug("invoking: publish-message-req-resp.");
		return this.service.publishMessage(message);
	}
	
	@MessageMapping("getAll-req-stream")
	public Flux<MessageBoundary> getAll() {
		this.logger.debug("invoking: getAll-req-stream.");
		return this.service.getAll();
	}
	
	@MessageMapping("getMessagesByIds-channel")
	public Flux<MessageBoundary> getMessagesByIds(Flux<MessageIdBoundary> ids) {
		this.logger.debug("invoking: getMessagesByIds-channel.");
		return ids.flatMap(id->this.service.getMessageById(id));
	}
	
	@MessageMapping("deleteAll-fire-and-forget")
	public Mono<Void> deleteAll() {
		this.logger.debug("invoking: deleteAll-fire-and-forget.");
		return this.service.deleteAll();
	}
	
	// Bonus: get by external reference:
	@MessageMapping("getMessagesByExternalReferences-channel")
	public Flux<MessageBoundary> getMessagesByExternalReferences(
			Flux<ExternalReferenceBoundary> externalReferences) {
		this.logger.debug("invoking: getMessagesByExternalReferences-channel.");
		return externalReferences.flatMap(er->this.service.getMessagesByExternalReference(er));
	}
}
