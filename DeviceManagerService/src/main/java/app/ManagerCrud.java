package app;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ManagerCrud  extends ReactiveMongoRepository<DeviceEntity, String> {

}
