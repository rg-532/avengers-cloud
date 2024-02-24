package app.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import app.message.objects.ExternalReferenceBoundary;
import app.message.objects.MessageBoundary;
import app.message.objects.MessageIdBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMessageService implements MessageService {
	private ReactiveMessageCrud crud;
	
	public ReactiveMessageService(ReactiveMessageCrud crud) {
		this.crud = crud;
	}

	@Override
	public Mono<MessageBoundary> publishMessage(MessageBoundary message) {
		// Override fields for proper creation:
		message.setMessageId(null);
		message.setPublishedTimestamp(new Date());
		
		if (message.getExternalReferences() == null) {
			message.setExternalReferences(new ArrayList<>());
		}
		
		if (message.getMessageDetails() == null) {
			message.setMessageDetails(new HashMap<>());
		}
		
		// Save new message:
		return Mono.just(message.toEntity())
				.flatMap(this.crud::save)
				.map(MessageBoundary::new)
				.log();
	}

	@Override
	public Flux<MessageBoundary> getAll() {
		return this.crud
				.findAll()
				.map(MessageBoundary::new)
				.log();
	}

	@Override
	public Mono<MessageBoundary> getMessageById(MessageIdBoundary id) {
		return this.crud
				.findById(id.getMessageId())
				.map(MessageBoundary::new)
				.log();
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.crud
				.deleteAll()
				.log();
	}
	
	// Bonus: get by external reference:
	@Override
	public Flux<MessageBoundary> getMessagesByExternalReference(ExternalReferenceBoundary externalReference) {
		if (externalReference.getService() == null && externalReference.getExternalServiceId() == null) {
			return this.getAll();
		}
		else if (externalReference.getService() == null) {
			return this.crud
					.findAllByExternalReferencesContainsWithExternalServiceId(
							externalReference.getExternalServiceId())
					.map(MessageBoundary::new)
					.log();
		}
		else if (externalReference.getExternalServiceId() == null) {
			return this.crud
					.findAllByExternalReferencesContainsWithService(
							externalReference.getService())
					.map(MessageBoundary::new)
					.log();
		}
		else {
			return this.crud
					.findAllByExternalReferencesContains(externalReference.toEntity())
					.map(MessageBoundary::new)
					.log();
		}
	}

}
