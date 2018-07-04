package modbustest.device;

import java.util.HashMap;
import java.util.Map.Entry;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean detectDevice(String id) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean detectDevice() {
//		ModbusSerialMaster master = null;
//
//		try {
//			master = getModbusSerialMaster();
//			Register[] registers;
//			registers = master.readMultipleRegisters(4, 2043, 1);
//			int pcsVersion = registers[0].getValue();
//			if (pcsVersion < 500) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			return false;
//		}
//	}

//	@Override
//	public void printImportantValues() {
//		ModbusSerialMaster master = null;
//		try {
//			master = getModbusSerialMaster();
//
//			HashMap<Integer, String> b = new HashMap<Integer, String>();
//			b.put(109, Log.WHITE + "Current" + Log.HIGH_INTENSITY + Log.GREEN + "  SOC   " + Log.ANSI_RESET + " :"
//					+ Log.HIGH_INTENSITY + Log.GREEN + " % " + Log.ANSI_RESET);
//
//			for (Entry<Integer, String> entry : b.entrySet()) {
//				Register[] registers = master.readMultipleRegisters(4, entry.getKey(), 1);
//				int vall = registers[0].getValue();
//				Log.info(entry.getValue() + Log.HIGH_INTENSITY + Log.GREEN + vall + Log.ANSI_RESET);
//			}
//		} catch (Exception e) {
//			Log.exception(e);
//		}
//
//	}
//
//	@Override
//	public void printErrors() {
//
//	}
}