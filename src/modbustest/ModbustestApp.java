package modbustest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;

import modbustest.device.Device;
import modbustest.device.modbus.rtu.CarloGavazziEM300;
import modbustest.device.modbus.rtu.JanitzaUMG96RM;
import modbustest.device.modbus.rtu.Socomec;
import modbustest.device.modbus.rtu.Soltaro;
import modbustest.device.modbus.tcp.KMTronicTcp;
import modbustest.util.Log;

public class ModbustestApp {

	private static Optional<String> argUnitId = Optional.empty();

	public static void main(String[] args) throws Exception {
		SerialPort[] ports = SerialPort.getCommPorts();
		Optional<String> argBridgeName = Optional.empty();

		for (String arg : args) {
			if (arg.startsWith("-b=")) {
				argBridgeName = Optional.of(arg.substring(3));

			} else if (arg.startsWith("-i=")) {
				argUnitId = Optional.of(arg.substring(3));

			} else {
				System.out.println("Available commands:");
				System.out.println("-h           help");
				System.out.println("-b=device    set an IP address or a device, e.g. "
						+ Arrays.stream(ports).map(p -> p.getSystemPortName()).collect(Collectors.joining(",")));
				System.out.println("-i=id        device specific ID, e.g. Unit-ID or IP-address");
				System.out.println("-h           help");
				System.exit(0);
			}
		}

		try {
			if (ports != null) {
				if (argBridgeName.isPresent()) {
					tryBridge(argBridgeName.get());

				} else {
					for (SerialPort port : ports) {
						String portName = port.getSystemPortName();
						if (portName.equals("ttyS0")) {
							continue; // ignore ttyS0
						} else if (portName.equals("ttyO0")) {
							continue; // ignore ttyS0
						} else if (portName.equals("ttymxc0")) {
							continue; // ignore ttyS0
						}
						tryBridge(portName);
					}
				}
				Log.info("  ");
			} else {
				Log.error("No serial port available connected: ");
			}
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	private static void tryBridge(String bridgeName) {
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		// get device name
		if (!isWindows) {
			bridgeName = "/dev/" + bridgeName;
		}

		// create devices
		List<Device> devices = new ArrayList<>();
//		devices.add(new CarloGavazziEM300(deviceName, argUnitId));
//		devices.add(new JanitzaUMG96RM(deviceName, argUnitId));
//		 devices.add(new Socomec(deviceName, argUnitId));
		 devices.add(new KMTronicTcp(bridgeName, argUnitId));
		// devices.add(new KMTronic(deviceName));
		// devices.add(new Pro(deviceName));
		// devices.add(new Mini_3_3(deviceName));
		// devices.add(new Mini_3_6(deviceName));
		// devices.add(new ProHybrid(deviceName));
		 devices.add(new Soltaro(bridgeName, Optional.empty()));
		Log.info(Log.HIGH_INTENSITY + Log.YELLOW + "Trying [" + bridgeName + "]" + Log.ANSI_RESET);
		for (Device device : devices) {
			Log.info(Log.HIGH_INTENSITY + Log.CYAN + "- Trying to find [" + device.getName() + "]" + Log.ANSI_RESET);
			boolean detected = device.detectDevice();
			if (detected) {
				Log.info("Detected " + Log.HIGH_INTENSITY + Log.GREEN + "[" + device.getName() + "]" + Log.ANSI_RESET);
			}
		}
	}

}