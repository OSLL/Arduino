package simulavr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    configMap = initMap;

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

      checkBox.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
          if (configMap != null) {
            if (checkBox.isSelected()) {
              configMap.put(name, true);
            } else {
              configMap.put(name, false);
            }
          }
        }
      });
    }
  }
}
