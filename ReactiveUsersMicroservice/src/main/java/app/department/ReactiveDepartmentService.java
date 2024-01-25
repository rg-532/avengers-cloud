package app.department;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import app.user.ReactiveUserCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class {@code ReactiveDepartmentService} implements the {@code DepartmentService} for any department REST controller.
 * 
 * @author Rom Gat
 */
@Service
public class ReactiveDepartmentService implements DepartmentService{
	private ReactiveDepartmentCrud departmentCrud;
	private ReactiveUserCrud userCrud;
	
	public ReactiveDepartmentService(ReactiveDepartmentCrud departmentCrud, ReactiveUserCrud userCrud) {
		this.departmentCrud = departmentCrud;
		this.userCrud = userCrud;
	}
	
	
	public boolean isDepartmentValid(DepartmentBoundary department) {
		if (department.getDeptId() == null || department.getDeptId().length() == 0)
			return false;
		if (department.getDepartmentName() == null || department.getDepartmentName().length() == 0)
			return false;
		if (department.getCreationDate() == null)
			return false;
		
		return true;
	}

	@Override
	public Mono<DepartmentBoundary> createDepartment(DepartmentBoundary department) {
		if (!isDepartmentValid(department))
			return Mono.empty();
		
		return this.departmentCrud
				.findById(department.getDeptId())
				
				//Map to a pair which would indicate if this was empty or not.
				.map(d -> true)
				.defaultIfEmpty(false)
				
				//Flat map to an empty mono / mono of the boundary to save based on whether the crud returned empty or not.
				.flatMap(wasFound -> {
					if (wasFound)
						return Mono.empty();
					else
						return Mono.just(department);
				})
				
				//Then save (if empty does nothing and returns nothing).
				.map(DepartmentBoundary::toEntity)
				.flatMap(this.departmentCrud::save)
				.map(DepartmentBoundary::new);
	}

	@Override
	public Mono<DepartmentBoundary> getDepartmentById(String deptId) {
		return this.departmentCrud
				.findById(deptId)
				.map(DepartmentBoundary::new);
	}

	@Override
	public Flux<DepartmentBoundary> getAllDepartments() {
		return this.departmentCrud
				.findAll()
				.map(DepartmentBoundary::new);
	}

	@Override
	public Mono<Void> deleteAllDepartments() {
		return this.userCrud.saveAll(
				this.userCrud
				.findAll()
				.map(u -> {
					u.setDepartments(new HashSet<>());
					return u;
				}))
				.collectList()
				.flatMap(l -> this.departmentCrud
						.deleteAll());
	}
}
