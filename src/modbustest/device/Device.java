package modbustest.device;

public interface Device {

	public String getName();

	public boolean detectDevice();

	public boolean detectDevice(String id);

}
