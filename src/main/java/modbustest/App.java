package modbustest;

import java.util.HashMap;

import com.fazecast.jSerialComm.SerialPort;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.ModbusIOException;
import com.ghgande.j2mod.modbus.ModbusSlaveException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.BitVector;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class App {

	public static void main(String[] args) throws Exception {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		try {
			SerialPort[] ports = SerialPort.getCommPorts();
			if (ports != null) {
				for (SerialPort port : ports) {
					System.out.println(port.getSystemPortName());
					System.out.println("        ");
					if (isWindows) {
						System.out.println("Trying---  : " + port.getSystemPortName());
						detectDevice(port.getSystemPortName());
					} else if (port.getSystemPortName().equals("ttyS0")) {
						continue;
					} else {
						System.out.println("Trying---  : " + port.getSystemPortName());
						detectDevice("/dev/" + port.getSystemPortName());

					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void detectDevice(String device) throws Exception {
		ModbusSerialMaster master = new ModbusSerialMaster(getParameters(device));
		master.connect();
		Socomec(master);
		// KMTronic(master);
		Register[] registers = master.readMultipleRegisters(4, 121, 1);
		int VoltPhaseA = registers[0].getValue();
		registers = master.readMultipleRegisters(4, 122, 1);
		int VoltPhaseB = registers[0].getValue();
		registers = master.readMultipleRegisters(4, 123, 1);
		int VoltPhaseC = registers[0].getValue();
		System.out.println("VoltPhaseA :  " + VoltPhaseA + "   " + " VoltPhaseB :  " + VoltPhaseB + "   "
				+ " VoltPhaseC " + VoltPhaseC);
		if (VoltPhaseA == 0 && VoltPhaseB == 0 || VoltPhaseB == 0 && VoltPhaseC == 0
				|| VoltPhaseA == 0 && VoltPhaseC == 0) {

			detectedMini(master);

		} else {

			detectedPro(master);
		}
	}

	private static void Socomec(ModbusSerialMaster master) throws ModbusException {
		Register[] IDvalue = master.readMultipleRegisters(50005, 1);
		System.out.println("SOCOMEC Detected IDValue is :  " + IDvalue[0].getValue());
	}

	private static void KMTronic(ModbusSerialMaster master) {
		for (int r = 0; r < 8; r++) {
			for (int u = 1; u < 17; u++) {
				try {
					BitVector bits = master.readCoils(u, r, 1);
					if (bits.getBit(0) == true || bits.getBit(0) == false) {

						System.out.println(" ----------KMTronic ----UnitID is :   " + u + "     ----------"
								+ "For register :   " + r);

					} else {
						continue;
					}
				} catch (ModbusIOException ex) {
					continue;
				} catch (ModbusSlaveException ec) {
					continue;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private static void detectedMini(ModbusSerialMaster master) throws Exception {
		System.out.println(" --------------Detected FENECON MINI ------------  ");
		Register[] registers = master.readMultipleRegisters(4, 10143, 1);
		for (Register register : registers) {
			System.out.println(" --------------FENECON MINI--- SOC is :    " + register + "----------");
		}
		ErrorMessages(master);
	}

	private static void detectedPro(ModbusSerialMaster master) throws Exception {
		System.out.println(" --------------Detected FENECON by BYD PRO------------  ");
		Register[] registers = master.readMultipleRegisters(4, 109, 1);
		for (Register register : registers) {
			System.out.println(" --------------FENECON PRO--- SOC is :    " + register + "----------");
		}
		ErrorMessages(master);
	}

	private static SerialParameters getParameters(String device) {
		SerialParameters params = new SerialParameters();
		params.setPortName(device);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("None");
		params.setEncoding("rtu");
		return params;
	}

	private static void ErrorMessages(ModbusSerialMaster master) throws Exception {
		try {
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
				Register[] registers = master.readMultipleRegisters(4, errorRegister, 1);
				String[] messages = a.get(errorRegister);
				if (registers[0].getValue() == 0
						&& (errorRegister == 2011 || errorRegister == 2012 || errorRegister == 2013)) {
					System.out.println("----(Inverter 1) There is no error for register---- :        " + errorRegister);
				} else if (registers[0].getValue() == 0 && (errorRegister == 3007 || errorRegister == 3008)) {

					System.out.println("----(Battery Group 1) There is no error for register---- :   " + errorRegister);

				} else if (registers[0].getValue() == 0 && (errorRegister == 4808 || errorRegister == 4809)) {

					System.out.println("----(Battery Stack) There is no error for register---- :     " + errorRegister);
				} else if (registers[0].getValue() == 0 && (errorRegister == 3207 || errorRegister == 3208)) {

					System.out.println("----(Battery Group 2) There is no error for register---- :   " + errorRegister);
				} else if (registers[0].getValue() == 0
						&& (errorRegister == 2111 || errorRegister == 2112 || errorRegister == 2113)) {

					System.out.println("----(Inverter 2) There is no error for register---- :        " + errorRegister);
				} else if (registers[0].getValue() == 0
						&& (errorRegister == 2211 || errorRegister == 2212 || errorRegister == 2213)) {

					System.out.println("----(Inverter 3) There is no error for register---- :        " + errorRegister);
				} else {
					for (int j = 0; j < 16; j++) {
						if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2011 || errorRegister == 2012 || errorRegister == 2013)) {

							System.out.println("(Inverter 1) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);

						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2111 || errorRegister == 2112 || errorRegister == 2113)) {
							System.out.println("(Inverter 2) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);

						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 2211 || errorRegister == 2212 || errorRegister == 2213)) {
							System.out.println("ERROR-----------(Inverter 3) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 3007 || errorRegister == 3008)) {
							System.out.println("ERROR-----------(Battery Group 1) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 3207 || errorRegister == 3208)) {
							System.out.println("ERROR-----------(Battery Group 2) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);
						} else if (getBit(registers[0].getValue(), j) == true
								&& (errorRegister == 4808 || errorRegister == 4809)) {
							System.out.println("ERROR-----------(Battery Stack) For Register " + errorRegister
									+ "--- Error Message is---- :  " + messages[j]);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	static boolean getBit(int n, int k) {
		return (((n >> k) & 1) == 1 ? true : false);
	}
}