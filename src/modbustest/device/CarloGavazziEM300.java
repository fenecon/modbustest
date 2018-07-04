package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class CarloGavazziEM300 extends ModbusRtuDevice {

	private final int OFFSET = 300000 + 1;
	
	public CarloGavazziEM300(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "Carlo Gavazzi EM300-Series";
	}

	@Override
	public boolean detectDevice() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			Register[] registers = master.readMultipleRegisters(5, 300052 - OFFSET, 1);
			int ID5 = registers[0].getValue();
			if (ID5 != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			master.disconnect();
		}
	}

	@Override
	public void printImportantValues() {
		ModbusSerialMaster master = null;
		Register[] registers;
		try {
			master = getModbusSerialMaster();
			registers = master.readMultipleRegisters(5, 300052 - OFFSET, 1);
			Log.info("Frequency is :   "  + Log.GREEN + registers[0].getValue() + Log.ANSI_RESET);
		} catch (ModbusException e) {
			Log.exception(e);
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			master.disconnect();
		}
	}

	@Override
	public void printErrors() {		
		// Nothing to do
	}

}
