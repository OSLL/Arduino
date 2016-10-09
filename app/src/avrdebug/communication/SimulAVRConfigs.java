package avrdebug.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SimulAVRConfigs implements Serializable {
	private static final long serialVersionUID = -235225560474202938L;
	private ArrayList<String> mcuList;
	private long cpuFreq;
	private boolean isTraceEnable;
	private boolean isDebugEnable;
	private long maxRunTime;
	private boolean isVCDTraceEnable;
	private LinkedHashMap<String, Boolean> vcdSources;
	
	public SimulAVRConfigs() {
	}
	
	public ArrayList<String> getMcuList() {
		return mcuList;
	}
	public void setMcuList(ArrayList<String> mcuList) {
		this.mcuList = mcuList;
	}
	public long getCpuFreq() {
		return cpuFreq;
	}
	public void setCpuFreq(long cpuFreq) {
		this.cpuFreq = cpuFreq;
	}
	public boolean isTraceEnable() {
		return isTraceEnable;
	}
	public void setTraceEnable(boolean isTraceEnable) {
		this.isTraceEnable = isTraceEnable;
	}
	public boolean isDebugEnable() {
		return isDebugEnable;
	}
	public void setDebugEnable(boolean isDebugEnable) {
		this.isDebugEnable = isDebugEnable;
	}
	public long getMaxRunTime() {
		return maxRunTime;
	}
	public void setMaxRunTime(long maxRunTime) {
		this.maxRunTime = maxRunTime;
	}
	public boolean isVCDTraceEnable() {
		return isVCDTraceEnable;
	}
	public void setVCDTraceEnable(boolean isVCDTraceEnable) {
		this.isVCDTraceEnable = isVCDTraceEnable;
	}
	public LinkedHashMap<String, Boolean> getVcdSources() {
		return vcdSources;
	}
	public void setVcdSources(LinkedHashMap<String, Boolean> vcdSources) {
		this.vcdSources = vcdSources;
	}
}
