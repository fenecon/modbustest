package modbustest.device.modbus.rtu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class JanitzaUMG96RM extends ModbusRtuDevice {

	public JanitzaUMG96RM(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public String getName() {
		return "Janitza UMG96";
	}

	@Override
	public boolean detectDevice() {
		boolean detected = false;
		if (this.unitId.isPresent()) {
			this.detect(this.unitId.get());

		} else {
			for (int unitId = 1; unitId < 6; unitId++) {
				if (this.detect(unitId)) {
					detected = true;
				}
			}
		}
		return detected;
	}

	private boolean detect(int unitId) {
		Log.info(Log.HIGH_INTENSITY + Log.CYAN + "- - Trying Unit-ID [" + unitId + "]");
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			InputRegister[] registers = master.readInputRegisters(unitId, 3526, 1);
			Log.info("Frequency is:       " + Log.GREEN + registers[0].getValue() + Log.ANSI_RESET);
			
			registers = master.readInputRegisters(unitId, 808, 6);
			Log.info("VoltageL1 is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
			Log.info("VoltageL2 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
			Log.info("VoltageL3 is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);

			registers = master.readInputRegisters(unitId, 860, 6);
			Log.info("CurrentL1 is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
			Log.info("CurrentL2 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
			Log.info("CurrentL3 is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);

			registers = master.readInputRegisters(unitId, 884, 6);
			Log.info("ApparentPowerL1 is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
			Log.info("ApparentPowerL2 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
			Log.info("ApparentPowerL3 is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);

			registers = master.readInputRegisters(unitId, 900, 6);
			Log.info("ReactivePowerL1 is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
			Log.info("ReactivePowerL2 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
			Log.info("ReactivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);
			
			
			registers = master.readInputRegisters(unitId, 820, 16);
			Log.info("CosPhi        is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
			Log.info("ActivePowerL2 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[6], registers[7]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[8], registers[9]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[10], registers[11]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[12], registers[13]) + Log.ANSI_RESET);
			Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[14], registers[15]) + Log.ANSI_RESET);
			//			Log.info("ActivePower   is:   " + Log.GREEN + registersToFloat(registers[6], registers[7]) + Log.ANSI_RESET);
			
//			registers = master.readInputRegisters(unitId, 3920, 8);
//			Log.info("ActivePowerL1 is:   " + Log.GREEN + registerToShort(registers[0]) + Log.ANSI_RESET);
//			Log.info("ActivePowerL2 is:   " + Log.GREEN + registerToShort(registers[1]) + Log.ANSI_RESET);
//			Log.info("ActivePowerL3 is:   " + Log.GREEN + registerToShort(registers[2]) + Log.ANSI_RESET);
//			Log.info("ActivePower   is:   " + Log.GREEN + registerToShort(registers[3]) + Log.ANSI_RESET);
//			Log.info("ReactivePowerL1 is: " + Log.GREEN + registerToShort(registers[4]) + Log.ANSI_RESET);
//			Log.info("ReactivePowerL2 is: " + Log.GREEN + registerToShort(registers[5]) + Log.ANSI_RESET);
//			Log.info("ReactivePowerL3 is: " + Log.GREEN + registerToShort(registers[6]) + Log.ANSI_RESET);
//			Log.info("ReactivePower   is: " + Log.GREEN + registerToShort(registers[7]) + Log.ANSI_RESET);
			return true;
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		} finally {
			if (master != null) {
				master.disconnect();
			}
		}
	}
	
	private short registerToShort(InputRegister register) {
		ByteBuffer buff = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
		buff.put(register.toBytes());
		buff.rewind();
		return buff.getShort(0);
	}

//	registers = master.readInputRegisters(unitId, 825, 16);
//	Log.info("ActivePowerL1 is:   " + Log.GREEN + registersToFloat(registers[0], registers[1]) + Log.ANSI_RESET);
//	Log.info("ActivePowerL3 is:   " + Log.GREEN + registersToFloat(registers[2], registers[3]) + Log.ANSI_RESET);
//	Log.info("ActivePower   is:   " + Log.GREEN + registersToFloat(registers[4], registers[5]) + Log.ANSI_RESET);
//	Log.info("ReactivePowerL1 is: " + Log.GREEN + registersToFloat(registers[6], registers[7]) + Log.ANSI_RESET);
//	Log.info("ReactivePowerL2 is: " + Log.GREEN + registersToFloat(registers[8], registers[9]) + Log.ANSI_RESET);
//	Log.info("ReactivePowerL3 is: " + Log.GREEN + registersToFloat(registers[10], registers[11]) + Log.ANSI_RESET);
//	Log.info("ReactivePower   is: " + Log.GREEN + registersToFloat(registers[12], registers[13]) + Log.ANSI_RESET);
	
	private float registersToFloat(InputRegister register1, InputRegister register2) {
		ByteBuffer buff = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		buff.put(register1.toBytes());
		buff.put(register2.toBytes());
		buff.rewind();
		return buff.getFloat(0);
	}
}
