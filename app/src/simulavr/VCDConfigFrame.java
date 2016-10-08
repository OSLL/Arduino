package simulavr;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

  void initElements(Map<String, Boolean> initMap) {
    for (String name : initMap.keySet()) {
      Panel newPanel = new Panel(name, initMap.get(name));
      configMap.put(name, newPanel);
      add(newPanel);
    }

    pack();
  }

  Map<String, Boolean> getElements() {
    Map<String, Boolean> newMap = new HashMap<>(configMap.size());

    for (String name : configMap.keySet()) {
      newMap.put(name, configMap.get(name).getFlag());
    }

    return newMap;
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
