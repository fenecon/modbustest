package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

public class Socomec extends ModbusRtuDevice {

	public static final String HIGH_INTENSITY = "\u001B[1m";

	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	public static final String BACKGROUND_BLACK = "\u001B[40m";
	public static final String BACKGROUND_RED = "\u001B[41m";
	public static final String BACKGROUND_WHITE = "\u001B[47m";
	public static final String ANSI_RESET = "\u001B[0m";
	
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
			if (registers[0].getValue() == 0) {
				return false;
			} else {
				return true;
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
			registers = master.readMultipleRegisters(50005, 1);
			System.out.println("SOCOMEC ID is :   "  + GREEN + registers[0].getValue() + ANSI_RESET);
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	}

	@Override
	public void printErrors() {		
		// Nothing to do
	}

}
