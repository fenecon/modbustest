package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusIOException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

public class KMTronic extends ModbusRtuDevice {

	public KMTronic(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "FEMS Relais (KMTronic)";
	}

	private String[] coilsStatus;

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
		ModbusSerialMaster master = new ModbusSerialMaster(getParameters());
		for (int r = 0; r < 8; r++) {
			coilsStatus = null;
			try {
				BitVector bits = master.readCoils(1, r, 1);
				if (bits.getBit(0) == true) {
					coilsStatus[r] = "true";
					continue;
				} else if (bits.getBit(0) == false) {
					coilsStatus[r] = "false";
					continue;
				}
			} catch (ModbusIOException ex) {
				continue;
			} catch (ModbusSlaveException ec) {
				continue;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("KMTronic ------- Coil Status (Respectively 1 - to - 8) : " + coilsStatus[r]);
		}

	}

	@Override
	public void printErrors() {
		System.out.println(" ErrKM  ");

	}

}
