package modbustest.device;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;


public class Socomec implements Device {

	@Override
	public boolean detectDevice(SerialParameters params) throws Exception {
		System.out.println("In socomec ");
		ModbusSerialMaster master = new ModbusSerialMaster(params);
		master.connect();
		Register[] registers = master.readMultipleRegisters(50005, 1);
		if (registers[0].getValue() == 0 ) {

			return false;

		} else {

			return true;
		}
	}

	@Override
	public void printImportantValues(SerialParameters params) throws Exception {
		ModbusSerialMaster master = new  ModbusSerialMaster(params);
		Register[] registers = master.readMultipleRegisters(50005, 1);
		System.out.println("SOCOMEC ID is :   " + registers[0].getValue());		
	}

	@Override
	public void printErrors(SerialParameters parems) {
		System.out.println("  ErrSoco ");
	}

}
