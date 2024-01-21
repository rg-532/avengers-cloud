package app;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class {@code ReactiveUserController} implements all required APIs for users.
 * 
 * @author Rom Gat
 */
@RestController
@Validated
@RequestMapping(path = {"/users"})
public class ReactiveUserController {
	private UserService service;

	public ReactiveUserController(UserService service) {
		this.service = service;
	}
	
	
	@PostMapping(
			produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<UserBoundary> createUser(
			@Valid @RequestBody UserBoundary user) {
		return this.service
				.createUser(user)
				.log();
	}
	
	@GetMapping(
			path = {"/{email}"},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<SecureUserBoundary> getUserByEmailAndPassword(
			@PathVariable("email") String email,
			@RequestParam(name="password", required=true) String password) {
		return this.service
				.getUserByEmailAndPassword(email, password)
				.log();
	}
	
	@GetMapping(
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<SecureUserBoundary> getUsersByCriteria(
			@RequestParam(name="criteria", required=false) String criteria,
			@RequestParam(name="value", required=false) String value) {
		return this.service
				.getUsersByCriteria(criteria, value)
				.log();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAllUsers() {
		return this.service
				.deleteAllUsers()
				.log();
	}
}
