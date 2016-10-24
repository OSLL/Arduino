package simulavr;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ploskov Aleksandr
 */
public class VCDConfigFrame extends JFrame {
    private Map<String, Panel> configMap;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    public VCDConfigFrame() {
        super("Источники для VCD трассировки");

        configMap = new HashMap<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));
    }

    public void initElements(List<String> names) {
        for (String name : names) {
            Panel newPanel = new Panel(name, false);
            configMap.put(name, newPanel);
            mainPanel.add(newPanel);
        }
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = scrollPane.getPreferredSize().width;
        int height = screenDimension.height * 4 / 5;
        scrollPane.setPreferredSize(new Dimension(width, height));
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
