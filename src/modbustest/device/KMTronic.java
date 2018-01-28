package modbustest.device;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

import modbustest.util.Log;

public class KMTronic extends ModbusRtuDevice {
	
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
				Log.print(Log.GREEN + i + Log.ANSI_RESET + 1 + Log.GREEN + " [" + coil + "] + " + Log.ANSI_RESET);
			}
			Log.print("\n");
		} catch (Exception e) {
			Log.error("  " + e.getMessage());
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
