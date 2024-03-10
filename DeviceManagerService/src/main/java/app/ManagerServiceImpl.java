package app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import app.objects.DeviceEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManagerServiceImpl implements ManagerService {
	ManagerCrud crud;
	
	public ManagerServiceImpl(ManagerCrud crud) {
		this.crud = crud;
	}
	
	@Override
	public Mono<DeviceEntity> registerDevice(DeviceEntity device) {
		if (device.getType() == null) {
			return Mono.error(new IllegalArgumentException("ManagerServiceImpl :: Type must not be null."));
		}
		
		if (device.getLocation() == null) {
			return Mono.error(new IllegalArgumentException("ManagerServiceImpl :: Location must not be null."));
		}
		
		if (device.getId() == null) {
			device.setId(UUID.randomUUID().toString());
		}
		
		device.setRegistrationTimestamp(new Date());
		device.setLastUpdateTimestamp(new Date());
		
		if (device.getStatus() == null) {
			device.setStatus(new HashMap<>());
		}
		
		if (device.getAdditionalAttributes() == null) {
			device.setAdditionalAttributes(new HashMap<>());
		}
		
		return this.crud
				.save(device)
				.log();
	}

	@Override
	public Mono<Void> updateDevice(String id, DeviceEntity update) {
		return this.crud
				.findById(id)
				.flatMap(original -> {
					original.setLastUpdateTimestamp(new Date());
					
					if (update.getType() != null)
						original.setType(update.getType());
					if (update.getLocation() != null)
						original.setLocation(update.getLocation());
					if (update.getStatus() != null)
						original.setStatus(update.getStatus());
					else
						original.setStatus(new HashMap<>());
					if (update.getAdditionalAttributes() != null)
						original.setAdditionalAttributes(update.getAdditionalAttributes());
					else
						original.setAdditionalAttributes(new HashMap<>());
					
					original.setManufacturerPowerInWatts(update.getManufacturerPowerInWatts());
					original.setSubType(update.getSubType());
					
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
					original.setLastUpdateTimestamp(new Date());
					
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
	public Flux<DeviceEntity> getAllDevices() {
		return this.crud
				.findAll()
				.log();
	}

	@Override
	public Mono<DeviceEntity> getDeviceById(String id) {
		return this.crud
				.findById(id)
				.log();
	}

	@Override
	public Flux<DeviceEntity> getDevicesByExample(DeviceEntity example) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<DeviceEntity> toMatch = Example.of(example, matcher);
		
		return this.crud
				.findAll(toMatch)
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
