package app.department;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentService {

	Mono<DepartmentBoundary> createDepartment(DepartmentBoundary department);
	Mono<DepartmentBoundary> getDepartmentById(String deptId);
	Flux<DepartmentBoundary> getAllDepartments();
	Mono<Void> deleteAllDepartments();

}
