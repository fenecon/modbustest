package modbustest.device;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

public class ProHybrid extends ModbusRtuDevice {

	public ProHybrid(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "FENECON Pro-Hybrid";
	}

	@Override
	public boolean detectDevice() {
		ModbusSerialMaster master = null;

		try {
			master = getModbusSerialMaster();
			Register[] registers;
			registers = master.readMultipleRegisters(4, 2043, 1);
			int pcsVersion = registers[0].getValue();
			if (pcsVersion < 500) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void printImportantValues() {

	}

	@Override
	public void printErrors() {

	}
}