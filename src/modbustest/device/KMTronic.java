package modbustest.device;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusIOException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.BitVector;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class KMTronic implements Device {

	@Override
	public String getName() {
		return "FEMS Relais (KMTronic)";
	}

	private String[] coilsStatus;

	@Override
	public boolean detectDevice(SerialParameters params) {
		ModbusSerialMaster master = new ModbusSerialMaster(params);
		master.setTimeout(500);
		try {
			master.connect();			
			master.readCoils(1, 0, 8);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void printImportantValues(SerialParameters params) throws Exception {
		ModbusSerialMaster master = new ModbusSerialMaster(params);
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
	public void printErrors(SerialParameters parems) {
		System.out.println(" ErrKM  ");

	}

}
