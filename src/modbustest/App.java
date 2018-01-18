package modbustest;

import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;
import com.ghgande.j2mod.modbus.util.SerialParameters;

import modbustest.device.Device;
import modbustest.device.Mini;
import modbustest.device.Socomec;
import modbustest.device.KMTronic;
import modbustest.device.Pro;

public class App {

	public static void main(String[] args) throws Exception {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		try {
			List<Device> devices = new ArrayList<>();
			devices.add(new Mini());
			devices.add(new Socomec());
			devices.add(new KMTronic());
			devices.add(new Pro());
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

					System.out.println("Trying [" + deviceName + "]");
					SerialParameters params = getParameters(port.getSystemPortName());
					for (Device device : devices) {
						System.out.println("- Trying to find [" + device.getName() + "]");
						if (device.detectDevice(params)) {
							System.out.println("Found [" + device.getName() + "]");
							device.printImportantValues(params);
							device.printErrors(params);
						}
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static SerialParameters getParameters(String systemportname) {
		SerialParameters params = new SerialParameters();
		params.setPortName(systemportname);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("None");
		params.setEncoding("rtu");
		return params;
	}
}