package app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

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
	private ReactiveUserCrud crud;
	
	public ReactiveUserService(ReactiveUserCrud crud) {
		this.crud = crud;
	}
	
	
	

	public boolean isUserValid(UserBoundary user) {
		String emailPattern = "^(\\w|\\.)+@(\\S+)$";
		boolean isValidEmail = Pattern.compile(emailPattern).matcher(user.getEmail()).matches();
		
		if (user.getEmail() == null || !isValidEmail)
			return false;
		if (user.getPassword() == null || user.getPassword().length() < 3)
			return false;
		if (user.getName().getFirstName() == null || user.getName().getFirstName().length() == 0)
			return false;
		if (user.getName().getLastName() == null || user.getName().getLastName().length() == 0)
			return false;
		if (user.getBirthdate() == null)
			return false;
		if (user.getRecruitdate() == null)
			return false;
		
		return true;
		
	}
	
	@Override
	public Mono<UserBoundary> createUser(UserBoundary user) {
		if (!isUserValid(user))
			return Mono.empty();
		
		return this.crud
				.findById(user.getEmail())
				
				//Map to a pair which would indicate if this was empty or not.
				.map(u -> Arrays.asList(user.toEntity(), u))
				.defaultIfEmpty(Arrays.asList(user.toEntity(), null))
				
				//Flat map to an empty mono / mono of the boundary to save based on whether the crud returned empty or not.
				.flatMap(pair -> {
					UserEntity newUser = pair.get(0);
					UserEntity existingUser = pair.get(1);
					
					if (existingUser == null)
						return Mono.just(newUser);
					else
						return Mono.empty();
				})
				
				//Then save (if empty does nothing and returns nothing).
				.flatMap(this.crud::save)
				.map(UserBoundary::new);
	}

	@Override
	public Mono<SecureUserBoundary> getUserByEmailAndPassword(String email, String password) {
		return this.crud
				.findByEmailAndPassword(email, password)
				.map(SecureUserBoundary::new);
	}

	@Override
	public Flux<SecureUserBoundary> getUsersByCriteria(String criteria, String value) {
		if (criteria == null)
			return this.crud
					.findAll()
					.map(SecureUserBoundary::new);
		
		switch (criteria) {
		case "byDomain":
			return this.crud
					.findAllByEmailLike("*@" + value)
					.map(SecureUserBoundary::new);
		case "byLastname":
			return this.crud
					.findAllByLastName(value)
					.map(SecureUserBoundary::new);
		case "byMinimumAge":
			try {
				return this.crud
						.findAllByBirthdateLessThan(
								Date.from(
										LocalDate.now()
										.minusYears(Integer.valueOf(value))
										.plusDays(1)
										.atStartOfDay(ZoneId.systemDefault())
										.toInstant()))
						.map(SecureUserBoundary::new);
			} catch (NumberFormatException nfe) {
				return Flux.empty();
			}
		default:
			return Flux.empty();
		}
	}

	@Override
	public Mono<Void> deleteAllUsers() {
		return this.crud.deleteAll();
	}

}
