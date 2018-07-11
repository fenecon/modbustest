package modbustest.device;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.BitVector;

import modbustest.util.Log;

public class KMTronic extends ModbusRtuDevice {

	public KMTronic(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public String getName() {
		return "FEMS Relais (KMTronic)";
	}

	@Override
	public boolean detectDevice() {
		boolean detected = false;
		int unitId = 1;
		Optional<String> valueOpt = readData(unitId);
		if (valueOpt.isPresent()) {
			Log.info("State is :   " + Log.GREEN + valueOpt.get() + Log.ANSI_RESET);
			detected = true;
		}
		return detected;
	}
	private final Optional<String> readData(int unitId) {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
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
