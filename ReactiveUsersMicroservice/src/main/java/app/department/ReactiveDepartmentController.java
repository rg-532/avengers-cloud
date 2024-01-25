package app.department;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class {@code ReactiveDepartmentController} implements all required APIs for departments.
 * 
 * @author Rom Gat
 */
@RestController
@RequestMapping(path = {"/departments"})
public class ReactiveDepartmentController {
	private DepartmentService service;
	
	public ReactiveDepartmentController(DepartmentService service) {
		this.service = service;
	}
	
	
	@PostMapping(
			produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DepartmentBoundary> createDepartment(
			@RequestBody DepartmentBoundary department) {
		return this.service
				.createDepartment(department)
				.log();
	}
	
	@GetMapping(
			path = {"/{deptId}"},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<DepartmentBoundary> getDepartmentById(
			@PathVariable("deptId") String deptId) {
		return this.service
				.getDepartmentById(deptId)
				.log();
	}
	
	@GetMapping(
			produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<DepartmentBoundary> getAllDepartments() {
		return this.service
				.getAllDepartments()
				.log();
	}
	
	@DeleteMapping
	public Mono<Void> deleteAllDepartments() {
		return this.service
				.deleteAllDepartments()
				.log();
	}
}
