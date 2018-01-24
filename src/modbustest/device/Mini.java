package modbustest.device;

import java.util.HashMap;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

public class Mini extends ModbusRtuDevice {
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
	public Mini(String systemportname) {
		super(systemportname);
	}

	@Override
	public String getName() {
		return "FEMS Mini 3-3 or 3-6";
		// TODO separate implementation for Mini 3-3 and Mini 3-6
	}

	@Override
	public boolean detectDevice() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			Register[] registers;
			registers = master.readMultipleRegisters(4, 122, 1);
			int VoltPhaseB = registers[0].getValue();
			registers = master.readMultipleRegisters(4, 123, 1);
			int VoltPhaseC = registers[0].getValue();
			registers = master.readMultipleRegisters(4, 2003, 1);
			int GridVoltage = registers[0].getValue();
			System.out.println("   ");
			if ((VoltPhaseB == 0 && VoltPhaseC == 0) && GridVoltage != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void printImportantValues() {
		ModbusSerialMaster master = null;
		try {
			master = getModbusSerialMaster();
			Register[] registers;
			int vall = 0;
			int[] ValueRegs = { 10143, 121, 122, 123 };
			HashMap<Integer, String[]> b = new HashMap<Integer, String[]>();
			b.put(109, new String[] { WHITE + "Current" + HIGH_INTENSITY + GREEN + "  SOC   " + ANSI_RESET + " :"
					+ HIGH_INTENSITY + GREEN + " % " + ANSI_RESET });

			b.put(121, new String[] { "Voltage of Grid phase A  :      " });
			b.put(122, new String[] { "Voltage of Grid phase B  :      " });
			b.put(123, new String[] { "Voltage of Grid phase C  :      " });

			for (int valuereg : ValueRegs) {
				String[] messages = b.get(valuereg);
				registers = master.readMultipleRegisters(4, valuereg, 1);
				vall = registers[0].getValue();
				System.out.println(messages[0] + HIGH_INTENSITY + GREEN + vall + ANSI_RESET);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void printErrors() {

		try {
			ModbusSerialMaster master = null;
			HashMap<Integer, String[]> a = new HashMap<Integer, String[]>();
			a.put(2011, new String[] { "Control curent overload 100%", "Control curent overload 110%",
					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
					"Too many locking waveforms error", "Invert voltage zero drift error",
					"Grid voltage zero drift error", "Control current zero drift error",
					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
					"Hardware control current protection" });
			a.put(2111, new String[] { "Control curent overload 100%", "Control curent overload 110%",
					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
					"Too many locking waveforms error", "Invert voltage zero drift error",
					"Grid voltage zero drift error", "Control current zero drift error",
					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
					"Hardware control current protection" });
			a.put(2211, new String[] { "Control curent overload 100%", "Control curent overload 110%",
					"Control curent overload 150%", "Control curent overload 200%", "Control curent overload 220%",
					"Control curent overload 300%", "Control instant cuurent overload 102%", "Grid cuurrent overload",
					"Too many locking waveforms error", "Invert voltage zero drift error",
					"Grid voltage zero drift error", "Control current zero drift error",
					"Invert current zero drift error", "Grid current zero drift error", "PDP protection",
					"Hardware control current protection" });
			a.put(2012,
					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
			a.put(2112,
					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
			a.put(2212,
					new String[] { "Hardware invert voltage protection", "Hardware DC voltage protection",
							"Hardware temperature protection", "No capture signal", "DC over voltage", "DC side snap",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
							"Fan fault", "Phase loss fault", "Reservation", "Reservation" });
			a.put(2013,
					new String[] { "Control panel is over temperature", "Power panel is over temperature",
							"DC entrance is over temperature", "Capacitance is over temperature",
							"Radiator is over temperature", "Transformer is over temperature",
							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
							"Group network sync signal error", "Load photovoltaic zero drift error",
							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
			a.put(2113,
					new String[] { "Control panel is over temperature", "Power panel is over temperature",
							"DC entrance is over temperature", "Capacitance is over temperature",
							"Radiator is over temperature", "Transformer is over temperature",
							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
							"Group network sync signal error", "Load photovoltaic zero drift error",
							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
			a.put(2213,
					new String[] { "Control panel is over temperature", "Power panel is over temperature",
							"DC entrance is over temperature", "Capacitance is over temperature",
							"Radiator is over temperature", "Transformer is over temperature",
							"Group network communication  fault", "EEPROM fault", "Reserve", "Reserve",
							"Group network sync signal error", "Load photovoltaic zero drift error",
							"Load grid zero drift error", "Reserve", "Reserve", "Reserve" });
			a.put(3007,
					new String[] { "Discharge severe overcurrent", "Charge severe overcurrent", "General undervoltage",
							"Severe overvoltage", "General overvoltage", "Severe undervoltage", "Inside CAN broken",
							"General undervoltage high-current discharge", "BMU error", "Current sampling invalidation",
							"battery fail", "Reservation", "Reservation", "Temperature sampling broken",
							"Contactor 1 test back is abnormal(turn on abnormity )",
							"Contactor 1 test back is abnormal(turn off abnormity )" });
			a.put(3207,
					new String[] { "Discharge severe overcurrent", "Charge severe overcurrent", "General undervoltage",
							"Severe overvoltage", "General overvoltage", "Severe undervoltage", "Inside CAN broken",
							"General undervoltage high-current discharge", "BMU error", "Current sampling invalidation",
							"battery fail", "Reservation", "Reservation", "Temperature sampling broken",
							"Contactor 1 test back is abnormal(turn on abnormity )",
							"Contactor 1 test back is abnormal(turn off abnormity )" });
			a.put(3208,
					new String[] { "Contactor 2 test back is abnormal(turn on abnormity )",
							"Contactor 2 test back is abnormal(turn off abnormity )", "Severe high temperature fault",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
							"Hall invalidation", "Contactor invalidation", "Reservation", "Outside CAN  broken",
							"Cathode contactor broken", "Reservation", "Reservation" });
			a.put(4808,
					new String[] { "No available battery group", "Stack general leakage", "Stack severe leakage",
							"Stack starting fail", "Stack stopping fail", "Reservation", "Reservation", "Reservation",
							"Reservation", "Battery protection", "Reservation", "Reservation", "Reservation",
							"Reservation", "Reservation", "Reservation" });
			a.put(4809,
					new String[] { "Stack and group 1 CAN communication interrupt",
							"Stack and group 2 CAN communication interrupt", "Reservation", "Reservation",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation",
							"Reservation", "Reservation", "Reservation", "Reservation", "Reservation", "Reservation" });

			int[] ErrorRegisters = { 2011, 2012, 2013, 2111, 2112, 2113, 2211, 2212, 2213, 3007, 3008, 3207, 3208, 4808,
					4809 };
			for (int errorRegister : ErrorRegisters) {
				master = getModbusSerialMaster();
				Register[] registers = master.readMultipleRegisters(4, errorRegister, 1);
				String[] messages = a.get(errorRegister);
				if (registers[0].getValue() == 0) {
					continue;
				} else {
					for (int j = 0; j < 16; j++) {
						if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2011 || errorRegister == 2012 || errorRegister == 2013)) {

							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Inverter 1)--- : " + messages[j] + BACKGROUND_BLACK);

						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2111 || errorRegister == 2112 || errorRegister == 2113)) {
							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Inverter 2)---:  " + messages[j] + BACKGROUND_BLACK);

						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2211 || errorRegister == 2212 || errorRegister == 2213)) {
							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Inverter 3)--- :  " + messages[j] + BACKGROUND_BLACK);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 3007 || errorRegister == 3008)) {
							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Battery Group 1)--- :  " + messages[j] + BACKGROUND_BLACK);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 3207 || errorRegister == 3208)) {
							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Battery Group 2)--- :  " + messages[j] + BACKGROUND_BLACK);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 4808 || errorRegister == 4809)) {
							System.out.println(HIGH_INTENSITY + RED + "ERROR" + ANSI_RESET + BACKGROUND_RED
									+ "---(Battery Stack)--- :  " + messages[j] + BACKGROUND_BLACK);
						}
					}
				}
			}
		} catch (

		Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public boolean getBit(int n, int k) {
		return (((n >> k) & 1) == 1 ? true : false);
	}
}