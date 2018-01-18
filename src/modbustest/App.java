package modbustest;

import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

import modbustest.device.Device;
import modbustest.device.Mini;
import modbustest.device.Socomec;
import modbustest.device.KMTronic;
import modbustest.device.Pro;

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
					} else {
						deviceName = "/dev/" + port.getSystemPortName();
					}
					
					// create devices
					List<Device> devices = new ArrayList<>();
					devices.add(new Mini(deviceName));
					devices.add(new Socomec(deviceName));
					devices.add(new KMTronic(deviceName));
					devices.add(new Pro(deviceName));

					System.out.println("Trying [" + deviceName + "]");
					for (Device device : devices) {
						System.out.println("- Trying to find [" + device.getName() + "]");
						if (device.detectDevice()) {
							System.out.println("Found [" + device.getName() + "]");
							device.printImportantValues();
							device.printErrors();
						}
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}