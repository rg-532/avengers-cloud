package app;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import app.objects.DeviceEntity;

public interface ManagerCrud  extends ReactiveMongoRepository<DeviceEntity, String> {

}
