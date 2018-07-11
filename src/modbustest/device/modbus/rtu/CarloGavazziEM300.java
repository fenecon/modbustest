package modbustest.device.modbus.rtu;

import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class CarloGavazziEM300 extends ModbusRtuDevice {

	private final int OFFSET = 300000 + 1;

	public CarloGavazziEM300(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public String getName() {
		return "Carlo Gavazzi EM300-Series";
	}

	@Override
	public boolean detectDevice() {
		boolean detected = false;
		if (this.unitId.isPresent()) {
			this.detect(this.unitId.get());

		} else {
			for (int unitId = 1; unitId < 6; unitId++) {
				if(this.detect(unitId)) {
					detected = true;
				}
			}
		}
		return detected;
	}

	private boolean detect(int unitId) {
		Log.info(Log.HIGH_INTENSITY + Log.CYAN + "- - Trying Unit-ID [" + unitId + "]");
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			Register[] registers = master.readMultipleRegisters(unitId, 300052 - OFFSET, 1);
			Log.info("Frequency is:   " + Log.GREEN + registers[0].getValue() + Log.ANSI_RESET);
			registers = master.readMultipleRegisters(unitId, 300041 - OFFSET, 1);
			Log.info("ActivePower is: " + Log.GREEN + registers[0].getValue() + Log.ANSI_RESET);
			return true;
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		} finally {
			if (master != null) {
				master.disconnect();
			}
		}
	}
}
