package simulavr;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
class VCDConfigFrame extends JFrame {
  private Map<String, Panel> configMap;

  VCDConfigFrame() {
    super("Источники для VCD трассировки");

    setLayout(new GridLayout(0, 1));
    configMap = new HashMap<>();
  }

  void setElement(String name, boolean flag) {
    if (configMap.containsKey(name)) {
      configMap.get(name).setFlag(flag);
    }

    Panel newPanel = new Panel(name, flag);
    configMap.put(name, newPanel);
    add(newPanel);
  }

  boolean getElement(String name) {
    return configMap.get(name).getFlag();
  }

  private class Panel extends JPanel {
    private final JCheckBox checkBox;

    Panel(String name, boolean initFlag) {
      checkBox = new JCheckBox();
      checkBox.setSelected(initFlag);

      setLayout(new BorderLayout());
      add(new JLabel(name));
      add(checkBox, BorderLayout.EAST);
    }

    boolean getFlag() {
      return checkBox.isSelected();
    }

    void setFlag(boolean flag) {
      checkBox.setSelected(flag);
    }
  }
}
