package app.message;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import app.message.objects.ExternalReferenceEntity;
import app.message.objects.MessageEntity;
import reactor.core.publisher.Flux;

public interface ReactiveMessageCrud extends ReactiveMongoRepository<MessageEntity, String> {
	
	// Bonus: get by external reference:
	public Flux<MessageEntity> findAllByExternalReferencesContains(
			@Param("externalReference") ExternalReferenceEntity externalReference);
	
	@Query("{ externalReferences: { $elemMatch: {service: ?0 } } }")
	public Flux<MessageEntity> findAllByExternalReferencesContainsWithService(String service);
	
	@Query("{ externalReferences: { $elemMatch: {externalServiceId: ?0 } } }")
	public Flux<MessageEntity> findAllByExternalReferencesContainsWithExternalServiceId(String externalServiceId);

}
