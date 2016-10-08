package simulavr;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
class VCDConfigFrame extends JFrame {
  private Map<String, Boolean> configMap;

  VCDConfigFrame() {
    super("Источники для VCD трассировки");

    setLayout(new GridLayout(0, 1));
  }

  void initComponents(Map<String, Boolean> initMap) {
    for (String elem : initMap.keySet()) {
      add(new Panel(elem, initMap.get(elem)));
    }
  }

  private class Panel extends JPanel {
    private final JCheckBox checkBox;

    Panel(String name, boolean initFlag) {
      checkBox = new JCheckBox();

      setLayout(new BorderLayout());
      add(new JLabel(name));
      add(checkBox, BorderLayout.EAST);

      checkBox.addItemListener(e -> {
        if (configMap != null) {
          if (checkBox.isSelected()) {
            configMap.put(name, true);
          } else {
            configMap.put(name, false);
          }
        }
      });
    }
  }
}
