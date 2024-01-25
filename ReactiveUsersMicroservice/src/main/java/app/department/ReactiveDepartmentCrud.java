package app.department;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveDepartmentCrud  extends ReactiveMongoRepository<DepartmentEntity, String> {

}
