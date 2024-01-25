package app.user;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import app.department.DepartmentEntity;
import app.department.ReactiveDepartmentCrud;
import app.user.department_specifier.DepartmentSpecifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class {@code ReactiveUserService} implements the {@code UserService} for any user REST controller.
 * 
 * @author Rom Gat
 */
@Service
public class ReactiveUserService implements UserService {
	private ReactiveUserCrud userCrud;
	private ReactiveDepartmentCrud departmentCrud;
	
	public ReactiveUserService(ReactiveUserCrud userCrud, ReactiveDepartmentCrud departmentCrud) {
		this.userCrud = userCrud;
		this.departmentCrud = departmentCrud;
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
		
		return this.userCrud
				.findById(user.getEmail())
				
				//Map to a pair which would indicate if this was empty or not.
				.map(u -> true)
				.defaultIfEmpty(false)
				
				//Flat map to an empty mono / mono of the boundary to save based on whether the crud returned empty or not.
				.flatMap(wasFound -> {
					if (wasFound)
						return Mono.empty();
					else
						return Mono.just(user);
				})
				
				//Then save (if empty does nothing and returns nothing).
				.map(UserBoundary::toEntity)
				.flatMap(this.userCrud::save)
				.map(UserBoundary::new);
	}

	@Override
	public Mono<SecureUserBoundary> getUserByEmailAndPassword(String email, String password) {
		return this.userCrud
				.findByEmailAndPassword(email, password)
				.map(SecureUserBoundary::new);
	}

	@Override
	public Flux<SecureUserBoundary> getUsersByCriteria(String criteria, String value) {
		if (criteria == null)
			return this.userCrud
					.findAll()
					.map(SecureUserBoundary::new);
		
		switch (criteria) {
		case "byDomain":
			return this.userCrud
					.findAllByEmailLike("*@" + value)
					.map(SecureUserBoundary::new);
			
		case "byLastname":
			return this.userCrud
					.findAllByLastName(value)
					.map(SecureUserBoundary::new);
			
		case "byMinimumAge":
			try {
				return this.userCrud
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
			
		case "byDepartment":
			return this.departmentCrud
					.findById(value)
					.flatMapMany(d -> this.userCrud
							.findAllByDepartmentsContains(d))
					.map(SecureUserBoundary::new);
			
		default:
			return Flux.empty();
		}
	}

	@Override
	public Mono<Void> deleteAllUsers() {
		return this.userCrud.deleteAll();
	}


	@Override
	public Mono<UserBoundary> assignUserToDepartment(String email, DepartmentSpecifier specifier) {
		return this.departmentCrud
				.findById(specifier.getContainer().getDeptId())
				.defaultIfEmpty(new DepartmentEntity())
				.flatMap(d -> {
					if (d.getDeptId() == null)
						return Mono.empty();
					else
						return this.userCrud
								.findById(email)
								.flatMap(u -> {
									u.addDepartment(d);
									return this.userCrud.save(u);
								});
				})
				
				//Then save (if empty does nothing and returns nothing).
				.map(UserBoundary::new);
	}

}
