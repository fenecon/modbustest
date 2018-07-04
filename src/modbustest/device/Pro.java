package modbustest.device;

import java.util.HashMap;
import java.util.Map.Entry;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import modbustest.util.Log;

public class Pro extends ModbusRtuDevice {

	public Pro(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "FENECON Pro 9-12";
	}
//
//	@Override
//	public boolean detectDevice() {
//		ModbusSerialMaster master = null;
//		try {
//			master = getModbusSerialMaster();
//
//			Register[] registers = master.readMultipleRegisters(4, 3000, 1);
//			int chargingCurrentLimit = registers[0].getValue();
//			registers = master.readMultipleRegisters(4, 3001, 1);
//			int dischargingCurrentLimit = registers[0].getValue();
//			registers = master.readMultipleRegisters(4, 2043, 1);
//			int pcsVersion = registers[0].getValue();
//			if ((chargingCurrentLimit > 1400 || dischargingCurrentLimit > 1400) && pcsVersion > 500) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	@Override
//	public void printImportantValues() {
//		ModbusSerialMaster master = null;
//		try {
//			master = getModbusSerialMaster();
//
//			HashMap<Integer, String> b = new HashMap<Integer, String>();
//			b.put(109, Log.WHITE + "Current" + Log.HIGH_INTENSITY + Log.GREEN + "  SOC   " + Log.ANSI_RESET + " :"
//					+ Log.HIGH_INTENSITY + Log.GREEN + " % " + Log.ANSI_RESET);
//			b.put(3000, "Charging Power Limit     :      ");
//			b.put(3001, "Discharging Power Limit  :      ");
//			b.put(121, "Voltage of Grid phase A  :      ");
//			b.put(122, "Voltage of Grid phase B  :      ");
//			b.put(123, "Voltage of Grid phase C  :      ");
//
//			for (Entry<Integer, String> entry : b.entrySet()) {
//				Register[] registers = master.readMultipleRegisters(4, entry.getKey(), 1);
//				int vall = registers[0].getValue();
//				Log.info(entry.getValue() + Log.HIGH_INTENSITY + Log.GREEN + vall + Log.ANSI_RESET);
//			}
//		} catch (Exception e) {
//			Log.exception(e);
//		}
//	}
//
//	@Override
//	public void printErrors() {
//		try {
//			ModbusSerialMaster master = null;
//			HashMap<Integer, String[]> a = new HashMap<Integer, String[]>();
//
//			// ----------ERROR------------MESSAGES---------------
//			a.put(2011, new String[] { "Control curent overload 100%", "Control curent overload 110%",
//					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
//					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
//					"Too many locking waveforms error", "Invert voltage zero drift error",
//					"Grid voltage zero drift error", "Control current zero drift error",
//					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
//					"Hardware control current protection" });
//			a.put(2111, new String[] { "Control curent overload 100%", "Control curent overload 110%",
//					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
//					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
//					"Too many locking waveforms error", "Invert voltage zero drift error",
//					"Grid voltage zero drift error", "Control current zero drift error",
//					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
//					"Hardware control current protection" });
//			a.put(2211, new String[] { "Control curent overload 100%", "Control curent overload 110%",
//					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
//					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
//					"Too many locking waveforms error", "Invert voltage zero drift error",
//					"Grid voltage zero drift error", "Control current zero drift error",
//					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
//					"Hardware control current protection" });
//			a.put(2012,
//					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
//							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
//							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
//			a.put(2112,
//					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
//							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
//							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
//			a.put(2212,
//					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
//							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
//							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
//			a.put(2013,
//					new String[] { "Control panel is over temperature", "Power panel is over temperature",
//							"DC entrance is over temperature", "Capacitance is over temperature",
//							"Radiator is over temperature", "Transformer is over temperature",
//							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
//							"Group network sync signal error", "Load photovoltaic zero drift error",
//							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
//			a.put(2113,
//					new String[] { "Control panel is over temperature", "Power panel is over temperature",
//							"DC entrance is over temperature", "Capacitance is over temperature",
//							"Radiator is over temperature", "Transformer is over temperature",
//							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
//							"Group network sync signal error", "Load photovoltaic zero drift error",
//							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
//			a.put(2213,
//					new String[] { "Control panel is over temperature", "Power panel is over temperature",
//							"DC entrance is over temperature", "Capacitance is over temperature",
//							"Radiator is over temperature", "Transformer is over temperature",
//							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
//							"Group network sync signal error", "Load photovoltaic zero drift error",
//							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
//			a.put(3007,
//					new String[] { "Discharge severe overcurrent", "Charge severe overcurrent", "General undervoltage",
//							"Severe overvoltage", "General overvoltage", "Severe undervoltage", "Inside CAN broken",
//							"General undervoltage high-current discharge", "BMU error", "Current sampling invalidation",
//							"battery fail", "Reservation", "Reservation", "Temperature sampling broken",
//							"Contactor 1 test back is abnormal(turn on abnormity )",
//							"Contactor 1 test back is abnormal(turn off abnormity )" });
//			a.put(3207,
//					new String[] { "Discharge severe overcurrent", "Charge severe overcurrent", "General undervoltage",
//							"Severe overvoltage", "General overvoltage", "Severe undervoltage", "Inside CAN broken",
//							"General undervoltage high-current discharge", "BMU error", "Current sampling invalidation",
//							"battery fail", "Reservation", "Reservation", "Temperature sampling broken",
//							"Contactor 1 test back is abnormal(turn on abnormity )",
//							"Contactor 1 test back is abnormal(turn off abnormity )" });
//			a.put(3208,
//					new String[] { "Contactor 2 test back is abnormal(turn on abnormity )",
//							"Contactor 2 test back is abnormal(turn off abnormity )", "Severe high temperature fault",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
//							"Hall invalidation", "Contactor invalidation", "Reservation", "Outside CAN  broken",
//							"Cathode contactor broken", "Reservation", "Reservation" });
//			a.put(4808,
//					new String[] { "No available battery group", "Stack general leakage", "Stack severe leakage",
//							"Stack starting fail", "Stack stopping fail", "Reservation", "Reservation", "Reservation",
//							"Reservation", "Battery protection", "Reservation", "Reservation", "Reservation",
//							"Reservation", "Reservation", "Reservation" });
//			a.put(4809,
//					new String[] { "Stack and group 1 CAN communication interrupt",
//							"Stack and group 2 CAN communication interrupt", "Reservation", "Reservation",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
//							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation" });
//
//			// --------------ALARM-------MESSAGES----------------
//			a.put(2041,
//					new String[] { "Grid low voltage", "Grid over voltage", "Grid low frequence", "Grid over frequence",
//							"The grid break abruptly", "Grid condition NOT allowed to be connectted", "Low DC voltage",
//							"High input impedance", "The setting of network jumper is wrong",
//							"Communication fault to inverter", "System time failed", "Reserve", "Reserve", "Reserve",
//							"Reserve", "Reserve" });
//			a.put(3005, new String[] { "General charge over-current alarm", "General discharge over-current alarm",
//					"Charge current limit alarm  ", "Discharge current limit alarm", "General high voltage alarm",
//					"General low voltage alarm", "Abnormal voltage change alarm", "General high temperature alarm",
//					"General low temperature alarm", "Abnormal temperature change alarm", "Severe high voltage alarm",
//					"Severe  low voltage alarm", "Severe  low temperature alarm", "Severve charge over-current alarm",
//					"Severve discharge over-current alarm", "Abnormal cell capacity alarm" });
//			a.put(4810, new String[] { "General  over-current alarm at cell stack charge",
//					"General  over-current alarm at cell stack discharge", "Current limit alarm at cell stack charge",
//					"Current limit alarm at cell stack discharge", "General cell stack high voltage alarm",
//					"General cell stack low voltage alarm", "Abnormal cell stack voltage change alarm",
//					"General cell stack high temperature alarm", "General cell stack low temperature alarm",
//					"Abnormal cell stack temperature change alarm", "Severe cell stack high voltage alarm",
//					"Severe cell stack low voltage alarm", "Severe cell stack low temperature alarm",
//					"Severve over-current alarm at cell stack charge",
//					"Severve over-current alarm at cell stack discharge", "Abnormal cell stack capacity alarm" });
//			a.put(4811,
//					new String[] { "The parameter of EEPROM in cell stack lose effectiveness",
//							"Isolating switch in confluence ark break.",
//							"The communication between cell stack and temperature of collector break",
//							"The temperature of collector fail", "Hall sensor don't work accurately",
//							"The communication of PCS break", "Advanced charging or main contactor close abnormally",
//							"Abnormal sampled voltage", "Abnormal advanced contactor or abnormal RS485 gallery of PCS",
//							"Abnormal main contactor", "General cell stack leakage", "Severe cell stack leakage",
//							"Smoke alarm ", "The communication wire to ammeter break",
//							"The communication wire to dred break", "Reservation" });
//
//			int[] ErrorRegisters = { 2011, 2012, 2013, 2111, 2112, 2113, 2211, 2212, 2213, 3007, 3008, 3207, 3208, 4808,
//					4809, 2041, 3005, 4810, 4811 };
//			for (int errorRegister : ErrorRegisters) {
//				master = getModbusSerialMaster();
//				Register[] registers = master.readMultipleRegisters(4, errorRegister, 1);
//				String[] messages = a.get(errorRegister);
//				if (registers[0].getValue() == 0) {
//					continue;
//				} else {
//					for (int j = 0; j < 16; j++) {
//						if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 2011 || errorRegister == 2012 || errorRegister == 2013)) {
//
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Inverter 1)--- : " + messages[j] + Log.BACKGROUND_BLACK);
//
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 2111 || errorRegister == 2112 || errorRegister == 2113)) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Inverter 2)---:  " + messages[j] + Log.BACKGROUND_BLACK);
//
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 2211 || errorRegister == 2212 || errorRegister == 2213)) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Inverter 3)--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 3007 || errorRegister == 3008)) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Battery Group 1)--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 3207 || errorRegister == 3208)) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Battery Group 2)--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 4808 || errorRegister == 4809)) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ERROR : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Battery Stack)--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true && errorRegister == 2041) {
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ALARM : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Inverter )--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true && errorRegister == 3005) {
//
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ALARM : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Battery Group )--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						} else if (getBit(registers[0].getValue(), j) == true
//								&& (errorRegister == 4810 || errorRegister == 4811)) {
//
//							Log.info(Log.HIGH_INTENSITY + Log.RED + "ALARM : " + Log.ANSI_RESET + Log.BACKGROUND_RED
//									+ "---(Battery Stack )--- :  " + messages[j] + Log.BACKGROUND_BLACK);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			Log.exception(e);
//		}
//	}
//
//	public boolean getBit(int n, int k) {
//		return (((n >> k) & 1) == 1 ? true : false);
//	}

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

}
