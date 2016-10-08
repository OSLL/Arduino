package simulavr;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
public class SimulAVRConfigFrame extends JFrame {
  private final int PREFERRED_WIDTH;

  private final JComboBox<String> microcontrollerModel;
  private final JFormattedTextField cpuFrequency;
  private final JToggleButton enableTrace;
  private final JToggleButton enableDebug;
  private final JFormattedTextField maxRunTime;
  private final JToggleButton enableVCDTrace;
  private final JButton openVCDConfigFrame;

  private final VCDConfigFrame vcdConfigFrame;

  public SimulAVRConfigFrame() {
    super("Конфигурация SimulAVR");

    PREFERRED_WIDTH = 640;
    Dimension preferredDimension = new Dimension(PREFERRED_WIDTH / 3, 30);

    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
    numberFormatter.setValueClass(Integer.class);
    numberFormatter.setMinimum(0);

    microcontrollerModel = new JComboBox<>();
    microcontrollerModel.setPreferredSize(preferredDimension);

    cpuFrequency = new JFormattedTextField(numberFormatter);
    cpuFrequency.setHorizontalAlignment(SwingConstants.RIGHT);
    cpuFrequency.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    cpuFrequency.setPreferredSize(preferredDimension);

    enableTrace = new JToggleButton("Нет");
    enableTrace.setPreferredSize(preferredDimension);

    enableDebug = new JToggleButton("Нет");
    enableDebug.setPreferredSize(preferredDimension);

    maxRunTime = new JFormattedTextField(numberFormatter);
    maxRunTime.setHorizontalAlignment(SwingConstants.RIGHT);
    maxRunTime.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    maxRunTime.setPreferredSize(preferredDimension);

    enableVCDTrace = new JToggleButton("Нет");
    enableVCDTrace.setPreferredSize(preferredDimension);

    vcdConfigFrame = new VCDConfigFrame();

    openVCDConfigFrame = new JButton("Выбор источников для VCD " +
      "трассировки");
    openVCDConfigFrame.setPreferredSize(new Dimension(PREFERRED_WIDTH, 30));
    openVCDConfigFrame.addActionListener(e -> vcdConfigFrame.setVisible(true));

    setLayout(new GridLayout(0, 1));
    makeGUI();
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        vcdConfigFrame.setVisible(false);
        super.windowClosing(e);
      }
    });
    setVisible(true);
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
    enableTrace.addItemListener(e -> {
      if (enableTrace.isSelected()) {
        enableTrace.setText("Да");
      } else {
        enableTrace.setText("Нет");
      }
    });
    add(enableTracePanel);

    JPanel enableDebugPanel = new JPanel();
    enableDebugPanel.setLayout(new BorderLayout());
    enableDebugPanel.setPreferredSize(preferredPanelDimension);
    enableDebugPanel.add(new JLabel("Запустить режим отладки"));
    enableDebugPanel.add(enableDebug, BorderLayout.EAST);
    enableDebug.addItemListener(e -> {
      if (enableDebug.isSelected()) {
        enableDebug.setText("Да");
      } else {
        enableDebug.setText("Нет");
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
    enableVCDTrace.addItemListener(e -> {
      if (enableVCDTrace.isSelected()) {
        add(openVCDConfigFrame);
        enableVCDTrace.setText("Да");
      } else {
        remove(openVCDConfigFrame);
        enableVCDTrace.setText("Нет");
      }
      pack();
    });
    add(enableVCDTracePanel);

    pack();
  }

  public String getMicrocontrollerModel() {
    return (String) microcontrollerModel.getSelectedItem();
  }

  public void setMicrocontrollerModel(List<String> microcontrollerList) {
    for (String microcontroller : microcontrollerList) {
      microcontrollerModel.addItem(microcontroller);
    }
  }

  public int getCPUFrequency() {
    return Integer.parseInt(cpuFrequency.getText());
  }

  public void setCPUFrequency(int frequency) {
    cpuFrequency.setText(Integer.toString(frequency));
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

  public int getMaxRunTime() {
    return Integer.parseInt(maxRunTime.getText());
  }

  public void setMaxRunTime(int nanoseconds) {
    maxRunTime.setText(Integer.toString(nanoseconds));
  }

  public boolean getEnableVCDTrace() {
    return enableVCDTrace.isSelected();
  }

  public void setEnableVCDTrace(boolean flag) {
    enableVCDTrace.setSelected(flag);
  }

  public void setVCDConfig(Map<String, Boolean> initConfig) {
    vcdConfigFrame.initComponents(initConfig);
  }
}
