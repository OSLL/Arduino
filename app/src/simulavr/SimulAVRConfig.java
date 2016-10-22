package simulavr;

import avrdebug.communication.SimulAVRConfigs;
import avrdebug.communication.SimulAVRInitData;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
public class SimulAVRConfig {
  private SimulAVRConfigFrame frame;
  private SimulAVRConfigs configs;

  public SimulAVRConfig() {
    frame = new SimulAVRConfigFrame();
    configs = new SimulAVRConfigs();
  }

  public SimulAVRConfig(SimulAVRInitData initData) {
    frame = new SimulAVRConfigFrame();
    configs = new SimulAVRConfigs();

    initFrame(initData);
  }

  public void initFrame(SimulAVRInitData initData) {
    Map<String, ArrayList<String>> data = initData.getMcuVCDSources();
    ArrayList<String> microcontrollers = new ArrayList<>(data.keySet());

    frame.initVCDConfig(data);
    frame.setMicrocontrollerModel(microcontrollers);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int dialogResult = JOptionPane.showConfirmDialog(null,
          "Would you like to save configs?",
          "Warning",
          JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
          saveData();
        }
        super.windowClosing(e);
      }
    });

    frame.setVisible(true);
  }

  private void saveData() {
    configs.setCpuFreq(frame.getCPUFrequency());
    configs.setDebugEnable(frame.getEnableDebug());
    configs.setMaxRunTime(frame.getMaxRunTime());
    configs.setSelectedMcu(frame.getMicrocontrollerModel());
    configs.setTraceEnable(frame.getEnableTrace());
    configs.setVCDTraceEnable(frame.getEnableVCDTrace());
    configs.setVcdSources(new LinkedHashMap<>(frame.getVCDConfig()));
  }

  public SimulAVRConfigs getConfigs() {
    return configs;
  }
}
