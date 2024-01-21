package app;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	Mono<UserBoundary> createUser(UserBoundary user);
	Mono<SecureUserBoundary> getUserByEmailAndPassword(String email, String password);
	Flux<SecureUserBoundary> getUsersByCriteria(String criteria, String value);
	Mono<Void> deleteAllUsers();

}
