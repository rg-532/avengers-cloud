package app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManagerServiceImpl implements ManagerService {
	ManagerCrud crud;
	
	public ManagerServiceImpl(ManagerCrud crud) {
		this.crud = crud;
	}
	
	@Override
	public Mono<DeviceBoundary> registerDevice(DeviceBoundary device) {
		if (device.getType() == null) {
			return Mono.error(new IllegalArgumentException("Type must not be null."));
		}
		
		if (device.getAlias() == null) {
			return Mono.error(new IllegalArgumentException("Alias must not be null."));
		}
		
		device.setId(null);
		device.setRegistrationTimestamp(new Date());
		device.setLastUpdateTimestamp(new Date());
		
		if (device.getStatus() == null) {
			device.setStatus(new HashMap<>());
		}
		
		return this.crud
				.save(device.toEntity())
				.map(DeviceBoundary::new)
				.log();
	}

	@Override
	public Mono<Void> updateDevice(String id, DeviceBoundary update) {
		return this.crud
				.findById(id)
				.flatMap(original -> {
					DeviceEntity updateEntity = update.toEntity();
					original.setLastUpdateTimestamp(new Date());
					
					if (updateEntity.getType() != null)
						original.setType(updateEntity.getType());
					if (updateEntity.getAlias() != null)
						original.setAlias(updateEntity.getAlias());
					if (updateEntity.getStatus() != null)
						original.setStatus(updateEntity.getStatus());
					else
						original.setStatus(new HashMap<>());
					
					return this.crud
							.save(original);
				})
				.log()
				.then();
	}

	@Override
	public Mono<Void> updateDeviceStatus(String id, Map<String, Object> status) {
		return this.crud
				.findById(id)
				.flatMap(original -> {
					if (status != null)
						original.setStatus(status);
					else
						original.setStatus(new HashMap<>());
					
					return this.crud
							.save(original);
				})
				.log()
				.then();
	}

	@Override
	public Flux<DeviceBoundary> getAllDevices() {
		return this.crud
				.findAll()
				.map(DeviceBoundary::new)
				.log();
	}

	@Override
	public Mono<DeviceBoundary> getDeviceById(String id) {
		return this.crud
				.findById(id)
				.map(DeviceBoundary::new)
				.log();
	}

	@Override
	public Flux<DeviceBoundary> getDevicesByExample(DeviceBoundary example) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<DeviceEntity> toMatch = Example.of(example.toEntity(), matcher);
		return this.crud
				.findAll(toMatch)
				.map(DeviceBoundary::new)
				.log();
	}

	@Override
	public Mono<Void> deleteAllDevices() {
		return this.crud
				.deleteAll()
				.log();
	}

	@Override
	public Mono<Void> deleteDeviceById(String id) {
		return this.crud
				.deleteById(id)
				.log();
	}

}
