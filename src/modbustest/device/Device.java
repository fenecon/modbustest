package modbustest.device;

public interface Device {
	
	public String getName();
	
	public boolean detectDevice();
	
	public void printImportantValues();
	
	public void printErrors();
		
}
