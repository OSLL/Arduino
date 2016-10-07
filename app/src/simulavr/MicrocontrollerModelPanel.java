package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ploskov
 */
public class MicrocontrollerModelPanel extends JPanel {

    private final JComboBox<String> microcontrollerComboBox;

    public MicrocontrollerModelPanel(String[] microcontrollerList) {
        JLabel label = new JLabel("Модель микроконтроллера");
        microcontrollerComboBox = new JComboBox<>(microcontrollerList);

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        add(label, BorderLayout.WEST);
        add(microcontrollerComboBox, BorderLayout.EAST);
    }

    @Override
    public String toString() {
        return "--device  "
                + (String) microcontrollerComboBox.getSelectedItem();
    }
}
