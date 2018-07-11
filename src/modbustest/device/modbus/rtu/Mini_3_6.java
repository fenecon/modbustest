package modbustest.device.modbus.rtu;

import java.util.Optional;

public class Mini_3_6 extends Mini {

	public Mini_3_6(String systemportname, Optional<String> id) {
		super(systemportname, id);
	}

	@Override
	public String getName() {
		return "FEMS Mini 3-6";
	}

	@Override
	public boolean detectDevice() {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean detectDevice() {
//		try {
//			if (super.detectMini() && super.batteryCabinet() != 0 && super.batteryCabinet() == 2) {
//				// detect if it is 3-3
//				return true;
//			}
//			return false;
//		} catch (Exception e) {
//			return false;
//		}
//	}
}