package modbustest;

import java.util.ArrayList;
import java.util.List;
import com.fazecast.jSerialComm.SerialPort;
import modbustest.device.Device;
import modbustest.device.Mini;
import modbustest.device.Socomec;
import modbustest.device.KMTronic;
import modbustest.device.Pro;
import modbustest.device.ProHybrid;

public class App {

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

	public static void main(String[] args) throws Exception {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		try {
			SerialPort[] ports = SerialPort.getCommPorts();

			if (ports != null) {
				for (SerialPort port : ports) {
					// get device name
					String deviceName;
					if (isWindows) {
						deviceName = port.getSystemPortName();
					} else if (port.getSystemPortName().equals("ttyS0")) {
						continue; // ignore ttyS0
					} else {
						deviceName = "/dev/" + port.getSystemPortName();
					}

					// create devices
					List<Device> devices = new ArrayList<>();
					devices.add(new Socomec(deviceName));
					devices.add(new KMTronic(deviceName));
					devices.add(new Pro(deviceName));
					devices.add(new Mini(deviceName));
					devices.add(new ProHybrid(deviceName));
					System.out.println(HIGH_INTENSITY + YELLOW + "Trying [" + deviceName + "]" + ANSI_RESET);
					for (Device device : devices) {
						System.out.println(
								HIGH_INTENSITY + CYAN + "- Trying to find [" + device.getName() + "]" + ANSI_RESET);
						if (device.detectDevice()) {
							System.out.println(
									"Detected " + HIGH_INTENSITY + GREEN + "[" + device.getName() + "]" + ANSI_RESET);
							device.printImportantValues();
							System.out.println(" ");
							device.printErrors();
							System.out.println(" " );
						}
					}
				}
				System.out.println("  ");
			} else {
				System.out.println(RED + "FAIL : No USB-RS485 converter connected: " + ANSI_RESET);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}