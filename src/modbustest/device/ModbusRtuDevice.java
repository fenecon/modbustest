package modbustest.device;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public abstract class ModbusRtuDevice implements Device {

	private final static int TIMEOUT = 500;
	private final static int RETRIES = 1; // default would be 3

	protected final Optional<String> id;

	private final String systemportname;

	public ModbusRtuDevice(String systemportname, Optional<String> id) {
		this.systemportname = systemportname;
		this.id = id;
	}

	protected ModbusSerialMaster getModbusSerialMaster() throws Exception {
		SerialParameters params = new SerialParameters();
		params.setPortName(this.systemportname);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("None");
		params.setEncoding("rtu");
		ModbusSerialMaster master = new ModbusSerialMaster(params);
		master.setTimeout(TIMEOUT);
		master.setRetries(RETRIES);
		master.connect();
		return master;
	}
}
