package app;

import java.util.Date;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class {@code ReactiveUserService} implements the {@code UserService} for any user REST controller.
 * 
 * @author Rom Gat
 */
@Service
public class ReactiveUserService implements UserService {

	@Override
	public Mono<UserBoundary> createUser(UserBoundary user) {
		// TODO Auto-generated method stub
		return Mono.just(user);
	}

	@Override
	public Mono<SecureUserBoundary> getUserByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		SecureUserBoundary boundary = new SecureUserBoundary();
		
		boundary.setEmail(email);
		
		NameBoundary name = new NameBoundary();
		name.setFirstName("foo");
		name.setLastName("bar");
		boundary.setName(name);
		
		boundary.setBirthdate(new Date());
		boundary.setRecruitdate(new Date());
		
		return Mono.just(boundary);
	}

	@Override
	public Flux<SecureUserBoundary> getUsersByCriteria(String criteria, String value) {
		// TODO Auto-generated method stub
		return Flux.empty();
	}

	@Override
	public Mono<Void> deleteAllUsers() {
		// TODO Auto-generated method stub
		return Mono.empty();
	}

}
