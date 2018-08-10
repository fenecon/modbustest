package modbustest.device.modbus.rtu;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class Soltaro extends ModbusRtuDevice {

	public final static int ADDRESS_CONTACTOR_CONTROL = 0x2010;

	public Soltaro(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public String getName() {
		return "Soltaro";
	}

	@Override
	public boolean detectDevice() {
		boolean detected = false;
		for (int unitId = 1; unitId < 10; unitId++) {
			Optional<Integer> valueOpt = readData(unitId);
			if (valueOpt.isPresent()) {
				Integer value = valueOpt.get();
				Log.info("ID is :   " + Log.GREEN + value);
				switch (value) {
				case 0:
					Log.info("Rack is off");
					break;
				case 1:
					Log.info("Rack is initiating");
					break;
				case 3:
					Log.info("Rack is started");
					break;
				default:
					Log.info("Rack state is unknown");
					break;
				}
				Log.info(Log.ANSI_RESET);
				detected = true;
			}
		}
		return detected;
	}

	private final Optional<Integer> readData(int unitId) {
		Log.info(Log.HIGH_INTENSITY + Log.CYAN + "- - Trying Unit-ID [" + unitId + "]");
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster(); 
			// 	TODO What happens if another device gives a value back at this address? 
			Register[] registers = master.readMultipleRegisters(unitId, ADDRESS_CONTACTOR_CONTROL, 1);
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
