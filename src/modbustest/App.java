package modbustest;

import java.util.ArrayList;
import java.util.List;
import com.fazecast.jSerialComm.SerialPort;
import modbustest.device.Device;
import modbustest.device.Mini_3_3;
import modbustest.device.Mini_3_6;
import modbustest.device.Socomec;
import modbustest.util.Log;
import modbustest.device.KMTronic;
import modbustest.device.Pro;
import modbustest.device.ProHybrid;

public class App {

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
					} else if (port.getSystemPortName().equals("ttyO0")) {
						continue; // ignore ttyS0
					} else {
						deviceName = "/dev/" + port.getSystemPortName();
					}

					// create devices
					List<Device> devices = new ArrayList<>();
					devices.add(new Socomec(deviceName));
					devices.add(new KMTronic(deviceName));
					devices.add(new Pro(deviceName));
					devices.add(new Mini_3_3(deviceName));
					devices.add(new Mini_3_6(deviceName));
					devices.add(new ProHybrid(deviceName));
					Log.info(Log.HIGH_INTENSITY + Log.YELLOW + "Trying [" + deviceName + "]" + Log.ANSI_RESET);
					for (Device device : devices) {
						Log.info(
								Log.HIGH_INTENSITY + Log.CYAN + "- Trying to find [" + device.getName() + "]" + Log.ANSI_RESET);
						if (device.detectDevice()) {
							Log.info(
									"Detected " + Log.HIGH_INTENSITY + Log.GREEN + "[" + device.getName() + "]" + Log.ANSI_RESET);
							device.printImportantValues();
							Log.info(" ");
							device.printErrors();
							Log.info(" ");
						}
					}
				}
				Log.info("  ");
			} else {
				Log.error("No USB-RS485 converter connected: ");

			}
		} catch (Exception e) {
			Log.exception(e);
		}
	}
}