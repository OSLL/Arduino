package simulavr;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by Ploskov Aleksandr
 */
public class VCDConfigFrame extends JFrame {
  private Map<String, Panel> configMap;

  public VCDConfigFrame() {
    super("Источники для VCD трассировки");

    setLayout(new GridLayout(0, 1));
    configMap = new HashMap<>();
  }

  public void initElements(List<String> names) {
    for (String name : names) {
      Panel newPanel = new Panel(name, false);
      configMap.put(name, newPanel);
      add(newPanel);
    }

    pack();
  }

  public Map<String, Boolean> getElements() {
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
