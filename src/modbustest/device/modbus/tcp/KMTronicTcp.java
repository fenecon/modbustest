package modbustest.device.modbus.tcp;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;

import modbustest.device.modbus.KMTronic;

public class KMTronicTcp extends ModbusTcpDevice implements KMTronic {

	public KMTronicTcp(String ip, Optional<String> id) {
		super(ip, id);
	}

	@Override
	public AbstractModbusMaster getModbusMaster() throws Exception {
		return this.getModbusTCPMaster();
	}

}
