package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

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
		ModbusSerialMaster master = new ModbusSerialMaster(getParameters());
		try {
			master.connect();
			Register[] registers = master.readMultipleRegisters(50005, 1);
			if (registers[0].getValue() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void printImportantValues() {
		ModbusSerialMaster master = new ModbusSerialMaster(getParameters());
		Register[] registers;
		try {
			registers = master.readMultipleRegisters(50005, 1);
			System.out.println("SOCOMEC ID is :   " + registers[0].getValue());
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void printErrors() {
		System.out.println("  ErrSoco ");
	}

}
