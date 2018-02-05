package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class Socomec extends ModbusRtuDevice {

	public Socomec(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "Socomec";
	}

	@Override
	public boolean detectDevice() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			// try different unit ids (common ones are 5 or 6)
			Register[] registers = master.readMultipleRegisters(5, 50005, 1);
			int ID5 = registers[0].getValue();
			registers = master.readMultipleRegisters(6, 50005, 1);
			int ID6 = registers[0].getValue();
			if (ID5 != 0 || ID6!=0 ) {
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
		ModbusSerialMaster master = null;
		Register[] registers;
		try {
			master = getModbusSerialMaster();
			registers = master.readMultipleRegisters(5, 50005, 1);
			Log.info("SOCOMEC ID is :   "  + Log.GREEN + registers[0].getValue() + Log.ANSI_RESET);
		} catch (ModbusException e) {
			Log.exception(e);
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	@Override
	public void printErrors() {		
		// Nothing to do
	}

}
