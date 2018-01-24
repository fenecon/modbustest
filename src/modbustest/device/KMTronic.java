package modbustest.device;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

public class KMTronic extends ModbusRtuDevice {

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
	
	public KMTronic(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "FEMS Relais (KMTronic)";
	}

	@Override
	public boolean detectDevice() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			// if we are able to read exactly 8 coils, we believe this is a KMTronic board
			master.readCoils(1, 0, 8);			
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (master != null) {
				master.disconnect();
			}
		}
	}

	@Override
	public void printImportantValues() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			// if we are able to read exactly 8 coils, we believe this is a KMTronic board
			BitVector coils = master.readCoils(1, 0, 8);

			for (int i = 0; i < coils.size(); i++) {
				boolean coil = coils.getBit(i);
				System.out.print(GREEN + i + ANSI_RESET + 1 + GREEN + " [" + coil + "] + ANSI_RESET");
			}
			System.out.print("\n");
		} catch (Exception e) {
			System.out.println("  " + e.getMessage());
		} finally {
			if (master != null) {
				master.disconnect();
			}
		}
	}

	@Override
	public void printErrors() {
		/* nothing to do */
	}

}
