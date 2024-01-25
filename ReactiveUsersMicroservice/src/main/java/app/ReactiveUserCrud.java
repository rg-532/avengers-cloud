package app;

import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveUserCrud extends ReactiveMongoRepository<UserEntity, String> {
	
	public Mono<UserEntity> findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password);
	
	public Flux<UserEntity> findAllByEmailLike(
			@Param("pattern") String pattern);

	public Flux<UserEntity> findAllByLastName(
			@Param("lastName") String lastname);
	
	public Flux<UserEntity> findAllByBirthdateLessThan(
			@Param("date") Date date);
	
	
}
