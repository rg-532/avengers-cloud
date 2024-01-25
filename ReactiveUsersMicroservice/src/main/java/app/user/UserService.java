package app.user;

import app.user.department_specifier.DepartmentSpecifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	Mono<UserBoundary> createUser(UserBoundary user);
	Mono<SecureUserBoundary> getUserByEmailAndPassword(String email, String password);
	Flux<SecureUserBoundary> getUsersByCriteria(String criteria, String value);
	Mono<Void> deleteAllUsers();
	
	// For department sections:
	Mono<UserBoundary> assignUserToDepartment(String email, DepartmentSpecifier container);

}
