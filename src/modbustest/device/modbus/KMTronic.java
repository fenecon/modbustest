package modbustest.device.modbus;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.BitVector;

import modbustest.device.Device;
import modbustest.util.Log;

public interface KMTronic extends Device {

	public default String getName() {
		return "FEMS Relais (KMTronic)";
	}

	public AbstractModbusMaster getModbusMaster() throws Exception;

	public default boolean detectDevice() {
		boolean detected = false;
		int unitId = 1;
		Optional<String> valueOpt = readData(unitId);
		if (valueOpt.isPresent()) {
			Log.info("State is :   " + Log.GREEN + valueOpt.get() + Log.ANSI_RESET);
			detected = true;
		}
		return detected;
	}

	public default Optional<String> readData(int unitId) {
		AbstractModbusMaster master = null;
		try {
			master = this.getModbusMaster();
			BitVector coils = master.readCoils(1, 0, 8);
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < coils.size(); i++) {
				boolean coil = coils.getBit(i);
				b.append(coil ? "x" : "-");
			}
			return Optional.of(b.toString());
		} catch (Exception e) {
			Log.error(e.getMessage());
			return Optional.empty();
		} finally {
			if (master != null) {
				master.disconnect();
			}
		}
	}

}
