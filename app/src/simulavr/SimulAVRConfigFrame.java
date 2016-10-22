package simulavr;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
class SimulAVRConfigFrame extends JFrame {
  private final int PREFERRED_WIDTH;

  private final JComboBox<String> microcontrollerModel;
  private final JFormattedTextField cpuFrequency;
  private final JToggleButton enableTrace;
  private final JToggleButton enableDebug;
  private final JFormattedTextField maxRunTime;
  private final JToggleButton enableVCDTrace;
  private final JButton openVCDConfigFrame;

  private VCDConfigFrame vcdConfigFrame;
  private Map<String, ArrayList<String>> vcdConfigs;

  SimulAVRConfigFrame() {
    super("Конфигурация SimulAVR");

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
      }
    });

    cpuFrequency = new JFormattedTextField(numberFormatter);
    cpuFrequency.setHorizontalAlignment(SwingConstants.RIGHT);
    cpuFrequency.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    cpuFrequency.setPreferredSize(preferredDimension);
    cpuFrequency.setText(Integer.toString(0));

    enableTrace = new JToggleButton("Нет");
    enableTrace.setPreferredSize(preferredDimension);

    enableDebug = new JToggleButton("Нет");
    enableDebug.setPreferredSize(preferredDimension);

    maxRunTime = new JFormattedTextField(numberFormatter);
    maxRunTime.setHorizontalAlignment(SwingConstants.RIGHT);
    maxRunTime.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    maxRunTime.setPreferredSize(preferredDimension);
    maxRunTime.setText(Integer.toString(0));

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
    add(enableTracePanel);

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

  String getMicrocontrollerModel() {
    return (String) microcontrollerModel.getSelectedItem();
  }

  void setMicrocontrollerModel(List<String> microcontrollerList) {
    for (String microcontroller : microcontrollerList) {
      microcontrollerModel.addItem(microcontroller);
    }
  }

  long getCPUFrequency() {
    return Long.parseLong(cpuFrequency.getText());
  }

  public void setCPUFrequency(int frequency) {
    cpuFrequency.setText(Integer.toString(frequency));
  }

  boolean getEnableTrace() {
    return enableTrace.isSelected();
  }

  public void setEnableTrace(boolean flag) {
    enableTrace.setSelected(flag);
  }

  boolean getEnableDebug() {
    return enableDebug.isSelected();
  }

  public void setEnableDebug(boolean flag) {
    enableDebug.setSelected(flag);
  }

  long getMaxRunTime() {
    return Long.parseLong(maxRunTime.getText());
  }

  public void setMaxRunTime(int nanoseconds) {
    maxRunTime.setText(Integer.toString(nanoseconds));
  }

  boolean getEnableVCDTrace() {
    return enableVCDTrace.isSelected();
  }

  public void setEnableVCDTrace(boolean flag) {
    enableVCDTrace.setSelected(flag);
  }

  void initVCDConfig(Map<String, ArrayList<String>> vcdConfigs) {
    this.vcdConfigs = vcdConfigs;
  }

  Map<String, Boolean> getVCDConfig() {
    return vcdConfigFrame.getElements();
  }
}
