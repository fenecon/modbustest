package modbustest.device;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class Janitza extends ModbusRtuDevice {

	public Janitza(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "Janitza UMG96";
	}

	@Override
	public boolean detectDevice() {
		boolean detected = false;
		for (int unitId = 1; unitId < 6; unitId++) {
			Optional<Integer> valueOpt = readData(unitId);
			if (valueOpt.isPresent()) {
				Log.info("Frequency is :   " + Log.GREEN + valueOpt.get() + Log.ANSI_RESET);
				detected = true;
			}
		}
		return detected;
	}

	@Override
	public boolean detectDevice(String id) {
		boolean detected = false;
		int unitId = Integer.parseInt(id);
		Optional<Integer> valueOpt = readData(unitId);
		if (valueOpt.isPresent()) {
			Log.info("Frequency is :   " + Log.GREEN + valueOpt.get() + Log.ANSI_RESET);
			detected = true;
		}
		return detected;
	}

	private final Optional<Integer> readData(int unitId) {
		Log.info(Log.HIGH_INTENSITY + Log.CYAN + "- - Trying Unit-ID [" + unitId + "]");
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			Register[] registers = master.readMultipleRegisters(unitId, 800, 1);
			int value = registers[0].getValue();
			return Optional.ofNullable(value);
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
