package modbustest.device;

import com.ghgande.j2mod.modbus.util.SerialParameters;

public abstract class ModbusRtuDevice implements Device {
	
	private final String systemportname;
	
	public ModbusRtuDevice(String systemportname) {
		this.systemportname = systemportname;
	}
	
	protected SerialParameters getParameters() {
		SerialParameters params = new SerialParameters();
		params.setPortName(this.systemportname);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("None");
		params.setEncoding("rtu");
		return params;
	}
	
}
