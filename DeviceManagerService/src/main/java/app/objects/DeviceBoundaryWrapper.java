package app.objects;

public class DeviceBoundaryWrapper {
	private DeviceBoundary device;

	
	public DeviceBoundaryWrapper() {
	}
	
	public DeviceBoundaryWrapper(DeviceBoundary device) {
		this.setDevice(device);
	}

	
	public DeviceBoundary getDevice() {
		return device;
	}

	public void setDevice(DeviceBoundary device) {
		this.device = device;
	}


	@Override
	public String toString() {
		return "DeviceBoundaryWrapper [device=" + device + "]";
	}

}
