package app.message;

import app.message.objects.ExternalReferenceBoundary;
import app.message.objects.MessageBoundary;
import app.message.objects.MessageIdBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
	
	public Mono<MessageBoundary> publishMessage(MessageBoundary message);
	public Flux<MessageBoundary> getAll();
	public Mono<MessageBoundary> getMessageById(MessageIdBoundary id);
	public Mono<Void> deleteAll();
	
	// Bonus: get by external reference:
	public Flux<MessageBoundary> getMessagesByExternalReference(ExternalReferenceBoundary externalReference);
	
}
