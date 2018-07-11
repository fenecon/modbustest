package modbustest.device.modbus.tcp;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

import modbustest.device.Device;

public abstract class ModbusTcpDevice implements Device {

	private final static int PORT = 502;

	protected final Optional<Integer> unitId;

	private final String ip;

	public ModbusTcpDevice(String ip, Optional<String> id) {
		this.ip = ip;
		if (id.isPresent()) {
			this.unitId = Optional.of(Integer.parseInt(id.get()));
		} else {
			this.unitId = Optional.empty();
		}
	}

	protected ModbusTCPMaster getModbusTCPMaster() throws Exception {
		ModbusTCPMaster master = new ModbusTCPMaster(this.ip, ModbusTcpDevice.PORT);
		master.connect();
		return master;
	}
}
