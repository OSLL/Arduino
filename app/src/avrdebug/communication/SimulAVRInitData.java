package avrdebug.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SimulAVRInitData implements Serializable{
	private static final long serialVersionUID = -2591376330837482355L;
	private HashMap<String, LinkedHashMap<String, Boolean>> mcuVCDSources;
	
	public SimulAVRInitData() {
	}
	public HashMap<String, LinkedHashMap<String, Boolean>> getMcuVCDSources() {
		return mcuVCDSources;
	}
	public void setMcuVCDSources(
			HashMap<String, LinkedHashMap<String, Boolean>> mcuVCDSources) {
		this.mcuVCDSources = mcuVCDSources;
	}
}
