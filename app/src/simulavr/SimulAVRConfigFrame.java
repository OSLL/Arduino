package simulavr;

import avrdebug.communication.SimulAVRConfigs;
import avrdebug.communication.SimulAVRInitData;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
public class SimulAVRConfigFrame extends JFrame {
  private static final long serialVersionUID = -6403161257709033802L;

  private final int PREFERRED_WIDTH;

  private final JComboBox<String> microcontrollerModel;
  private final JFormattedTextField cpuFrequency;
  private final JToggleButton enableTrace;
  private final JToggleButton enableDebug;
  private final JFormattedTextField maxRunTime;
  private final JToggleButton enableVCDTrace;
  private final JButton openVCDConfigFrame;
  private final SimulAVRConfigs defaultConfigs;
  
  private VCDConfigFrame vcdConfigFrame;
  private Map<String, ArrayList<String>> vcdConfigs;
  private SimulAVRConfigs configs;

  public SimulAVRConfigFrame(SimulAVRConfigs defaultConfigs) {
    super("Конфигурация SimulAVR");

    this.defaultConfigs = defaultConfigs;
    configs = null;
    
    PREFERRED_WIDTH = 640;
    Dimension preferredDimension = new Dimension(PREFERRED_WIDTH / 3, 30);

    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
    numberFormatter.setValueClass(Integer.class);
    numberFormatter.setMinimum(0);

    microcontrollerModel = new JComboBox<>();
    microcontrollerModel.setPreferredSize(preferredDimension);
    microcontrollerModel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String microcontroller = (String) microcontrollerModel.getSelectedItem();
        vcdConfigFrame.setVisible(false);
        vcdConfigFrame = new VCDConfigFrame();
        vcdConfigFrame.initElements(vcdConfigs.get(microcontroller));
        
        if (configs != null) {
        	if(configs.getSelectedMcu().equals(microcontroller)){
                LinkedHashMap<String, Boolean> vcdSources = configs.getVcdSources();
                for (String config : vcdConfigs.get(microcontroller)) {
                  vcdConfigFrame.setConfig(config, vcdSources.get(config));
                }
        	}
        }
      }
    });

    cpuFrequency = new JFormattedTextField(numberFormatter);
    cpuFrequency.setHorizontalAlignment(SwingConstants.RIGHT);
    cpuFrequency.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    cpuFrequency.setPreferredSize(preferredDimension);
    cpuFrequency.setText(Long.toString(defaultConfigs.getCpuFreq()));

    enableTrace = new JToggleButton("Нет");
    enableTrace.setPreferredSize(preferredDimension);

    enableDebug = new JToggleButton("Нет");
    enableDebug.setPreferredSize(preferredDimension);
    enableDebug.setEnabled(defaultConfigs.isDebugEnable());

    maxRunTime = new JFormattedTextField(numberFormatter);
    maxRunTime.setHorizontalAlignment(SwingConstants.RIGHT);
    maxRunTime.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    maxRunTime.setPreferredSize(preferredDimension);
    maxRunTime.setText(Long.toString(defaultConfigs.getMaxRunTime()));

    enableVCDTrace = new JToggleButton("Нет");
    enableVCDTrace.setPreferredSize(preferredDimension);

    vcdConfigFrame = new VCDConfigFrame();

    openVCDConfigFrame = new JButton("Выбор источников для VCD " +
      "трассировки");
    openVCDConfigFrame.setPreferredSize(new Dimension(PREFERRED_WIDTH, 30));
    openVCDConfigFrame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        vcdConfigFrame.setVisible(true);
      }
    });

    setLayout(new GridLayout(0, 1));
    makeGUI();
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        vcdConfigFrame.setVisible(false);
        super.windowClosed(e);
      }
    });

    //setVisible(true);
  }

  private void makeGUI() {
    Dimension preferredPanelDimension = new Dimension(PREFERRED_WIDTH, 30);

    JPanel microcontrollerModelPanel = new JPanel();
    microcontrollerModelPanel.setLayout(new BorderLayout());
    microcontrollerModelPanel.setPreferredSize(preferredPanelDimension);
    microcontrollerModelPanel.add(new JLabel("Модель микроконтроллера"));
    microcontrollerModelPanel.add(microcontrollerModel, BorderLayout.EAST);
    add(microcontrollerModelPanel);

    JPanel cpuFrequencyPanel = new JPanel();
    cpuFrequencyPanel.setLayout(new BorderLayout());
    cpuFrequencyPanel.setPreferredSize(preferredPanelDimension);
    cpuFrequencyPanel.add(new JLabel("Частота CPU (Гц)"));
    cpuFrequencyPanel.add(cpuFrequency, BorderLayout.EAST);
    add(cpuFrequencyPanel);

    JPanel enableTracePanel = new JPanel();
    enableTracePanel.setLayout(new BorderLayout());
    enableTracePanel.setPreferredSize(preferredPanelDimension);
    enableTracePanel.add(new JLabel("Включить трассировку выполняемых " +
      "процессором инструкций"));
    enableTracePanel.add(enableTrace, BorderLayout.EAST);
    enableTrace.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (enableTrace.isSelected()) {
          enableTrace.setText("Да");
        } else {
          enableTrace.setText("Нет");
        }
      }
    });
    //add(enableTracePanel);

    JPanel enableDebugPanel = new JPanel();
    enableDebugPanel.setLayout(new BorderLayout());
    enableDebugPanel.setPreferredSize(preferredPanelDimension);
    enableDebugPanel.add(new JLabel("Запустить режим отладки"));
    enableDebugPanel.add(enableDebug, BorderLayout.EAST);
    enableDebug.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (enableDebug.isSelected()) {
          enableDebug.setText("Да");
        } else {
          enableDebug.setText("Нет");
        }
      }
    });
    add(enableDebugPanel);

    JPanel maxRunTimePanel = new JPanel();
    maxRunTimePanel.setLayout(new BorderLayout());
    maxRunTimePanel.setPreferredSize(preferredPanelDimension);
    maxRunTimePanel.add(new JLabel("Максимальное время работы симулятора " +
      "(нс)"));
    maxRunTimePanel.add(maxRunTime, BorderLayout.EAST);
    add(maxRunTimePanel);

    JPanel enableVCDTracePanel = new JPanel();
    enableVCDTracePanel.setLayout(new BorderLayout());
    enableVCDTracePanel.setPreferredSize(preferredPanelDimension);
    enableVCDTracePanel.add(new JLabel("Включить VCD трассировку"));
    enableVCDTracePanel.add(enableVCDTrace, BorderLayout.EAST);
    enableVCDTrace.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (enableVCDTrace.isSelected()) {
          SimulAVRConfigFrame.this.add(openVCDConfigFrame);
          enableVCDTrace.setText("Да");
        } else {
          SimulAVRConfigFrame.this.remove(openVCDConfigFrame);
          enableVCDTrace.setText("Нет");
        }
        SimulAVRConfigFrame.this.pack();
      }
    });
    add(enableVCDTracePanel);

    pack();
  }

  public String getMicrocontrollerModel() {
    return (String) microcontrollerModel.getSelectedItem();
  }

  private void setMicrocontrollerModel(String microcontroller) {
    microcontrollerModel.setSelectedItem(microcontroller);
  }

  private void setMicrocontrollerList(List<String> microcontrollerList) {
    for (String microcontroller : microcontrollerList) {
      microcontrollerModel.addItem(microcontroller);
    }
  }

  public long getCPUFrequency() {
    String text = cpuFrequency.getText();
    // здесь какой то хитрый whitespace, который не подходит под регулярку \\s+
    try{
    	long value = Long.parseLong(text.replaceAll(" ", ""));
    	return value;
    }catch (NumberFormatException e){
    	return 16000000L;
    }
  }

  public void setCPUFrequency(long frequency) {
    cpuFrequency.setText(Long.toString(frequency));
  }

  public boolean getEnableTrace() {
    return enableTrace.isSelected();
  }

  public void setEnableTrace(boolean flag) {
    enableTrace.setSelected(flag);
  }

  public boolean getEnableDebug() {
    return enableDebug.isSelected();
  }

  public void setEnableDebug(boolean flag) {
    enableDebug.setSelected(flag);
  }

  public long getMaxRunTime() {
    String text = maxRunTime.getText();
    // здесь какой то хитрый whitespace, который не подходит под регулярку \\s+
    try{
    	long value = Long.parseLong(text.replaceAll(" ", ""));
    	return value;
    }catch (NumberFormatException e){
    	return 0;
    }
  }

  public void setMaxRunTime(long nanoseconds) {
    maxRunTime.setText(Long.toString(nanoseconds));
  }

  public boolean getEnableVCDTrace() {
    return enableVCDTrace.isSelected();
  }

  public void setEnableVCDTrace(boolean flag) {
    enableVCDTrace.setSelected(flag);
  }

  private void initVCDConfig(Map<String, ArrayList<String>> vcdConfigs) {
    this.vcdConfigs = vcdConfigs;
  }

  public Map<String, Boolean> getVCDConfig() {
    return vcdConfigFrame.getElements();
  }

  public void initFrame(SimulAVRInitData initData) {
    Map<String, ArrayList<String>> data = initData.getMcuVCDSources();
    ArrayList<String> microcontrollers = new ArrayList<>(data.keySet());

    initVCDConfig(data);
    setMicrocontrollerList(microcontrollers);
    setMicrocontrollerModel(defaultConfigs.getSelectedMcu());
    addWindowListener(new WindowAdapter() {
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
    //setVisible(true);
  }

  private void saveData() {
    configs = new SimulAVRConfigs();

    configs.setCpuFreq(getCPUFrequency());
    configs.setDebugEnable(getEnableDebug());
    configs.setMaxRunTime(getMaxRunTime());
    configs.setSelectedMcu(getMicrocontrollerModel());
    configs.setTraceEnable(getEnableTrace());
    configs.setVCDTraceEnable(getEnableVCDTrace());
    configs.setVcdSources(new LinkedHashMap<>(getVCDConfig()));
  }

  public SimulAVRConfigs getConfigs() {
    return configs;
  }

  @Override
  public void setVisible(boolean b) {
    if (configs != null) {
      setCPUFrequency(configs.getCpuFreq());
      setEnableDebug(configs.isDebugEnable());
      setMaxRunTime(configs.getMaxRunTime());
      setMicrocontrollerModel(configs.getSelectedMcu());
      setEnableTrace(configs.isTraceEnable());
      setEnableVCDTrace(configs.isVCDTraceEnable());
    } else {
      setMicrocontrollerModel(defaultConfigs.getSelectedMcu());
      setCPUFrequency(defaultConfigs.getCpuFreq());
      setEnableDebug(defaultConfigs.isDebugEnable());
      setMaxRunTime(defaultConfigs.getMaxRunTime());
      setEnableTrace(defaultConfigs.isTraceEnable());
      setEnableVCDTrace(defaultConfigs.isVCDTraceEnable());
    }
    super.setVisible(b);
  }
}
