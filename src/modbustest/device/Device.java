package modbustest.device;

import com.ghgande.j2mod.modbus.util.SerialParameters;

public interface Device {
	
	public boolean detectDevice(SerialParameters params) throws Exception;
	
	public void printImportantValues(SerialParameters params) throws Exception;
	
	public void printErrors(SerialParameters parems);
		
}
