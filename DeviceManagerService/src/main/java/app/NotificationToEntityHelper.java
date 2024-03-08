package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import app.objects.DeviceBoundary;
import app.objects.DeviceEntity;
import app.objects.DeviceNotificationBoundary;
import app.objects.ExternalReferenceBoundary;
import reactor.util.function.Tuple2;

/**Helper class with methods which aid the service with anything related to extracting
 * DeviceEntities from DeviceNotificationBoundaries, Managing DeviceNotificationBoundaries
 * and validations.
 */
public class NotificationToEntityHelper {
	private String appName;
	
	
	public NotificationToEntityHelper(String appName) {
		this.appName = appName;
	}
	
	
	/**Initialization method for a new DeviceNotificationBoundary:
	 * 
	 * @param boundary	{@code DeviceNotificationBoundary} to initialize.
	 * @return			Initialized {@code DeviceNotificationBoundary} (Fluent API).
	 */
	public DeviceNotificationBoundary initNotification(DeviceNotificationBoundary notification) {
		notification.setMessageId(UUID.randomUUID().toString());
		notification.setPublishedTimestamp(new Date());
		
		if (notification.getExternalReferences() == null)
			notification.setExternalReferences(new ArrayList<>());
		
		return notification;
	}
	
	
	/**Converter method from {@code DeviceNotificationBoundary} to {@code DeviceEntity}.
	 * 
	 * @param notification	{@code DeviceNotificationBoundary} to convert.
	 * @return				Converted {@code DeviceEntity}.
	 */
	public DeviceEntity extractEntity(DeviceNotificationBoundary notification) {
		DeviceEntity entity = notification.fetchDevice().toEntity();
		entity.setAlias(notification.fetchDeviceAlias());
		
		return entity;
	}
	
	
	/**Insertion method of {@code DeviceEntity} into an existing {@code DeviceNotificationBoundary}
	 * 
	 * @param entity		{@code DeviceEntity} to insert.
	 * @param notification	{@code DeviceNotificationBoundary} to insert into.
	 * @return				{@code DeviceNotificationBoundary} with the updated entity (Fluent API).
	 */
	public DeviceNotificationBoundary insertEntityIntoNotification(
			Tuple2<DeviceEntity, DeviceNotificationBoundary> tuple) {
		tuple.getT2().fetchDevice(new DeviceBoundary(tuple.getT1()));
		return tuple.getT2();
	}
	
	
	/**Converter method from DeviceEntity to new DeviceNotificationBoundary on queries:
	 * 
	 * @param entity	{@code DeviceEntity} to be transformed to a {@code DeviceBoundary} and inserted
	 * 					into the generated {@code DeviceNotificationBoundary}.
	 * @param summary	Summary to include in the generated {@code DeviceNotificationBoundary}.
	 * @return			Generated {@code DeviceNotificationBoundary}.
	 */
	public DeviceNotificationBoundary entityToNewNotification(DeviceEntity entity, String summary) {
		DeviceNotificationBoundary boundary = new DeviceNotificationBoundary();
		
		this.initNotification(boundary);
		boundary.setMessageType("deviceNotification");
		boundary.setSummary(summary);
		
		if (entity == null) {
			boundary.setMessageDetails(
					Collections.singletonMap("none", null));
		}
		else {
			boundary.setMessageDetails(
					Collections.singletonMap(entity.getAlias(), new DeviceBoundary(entity)));
		}
		
		ExternalReferenceBoundary externalReference = new ExternalReferenceBoundary();
		externalReference.setService(this.appName);
		
		if (entity == null) {
			externalReference.setExternalServiceId("none");
		}
		else {
			externalReference.setExternalServiceId(boundary.fetchDevice().getId());
		}
		
		ArrayList<ExternalReferenceBoundary> externalReferenceList = new ArrayList<>();
		externalReferenceList.add(externalReference);
		
		boundary.setExternalReferences(externalReferenceList);
		
		return boundary;
	}
	
}
