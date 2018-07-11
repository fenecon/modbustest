package modbustest.device.modbus.rtu;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;

import modbustest.device.modbus.KMTronic;

public class KMTronicRtu extends ModbusRtuDevice implements KMTronic {

	public KMTronicRtu(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public AbstractModbusMaster getModbusMaster() throws Exception {
		return this.getModbusSerialMaster();
	}

}
