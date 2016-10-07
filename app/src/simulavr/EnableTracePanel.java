package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author ploskov
 */
public class EnableTracePanel extends JPanel {

    private final String filename;
    private final JToggleButton yesNoButton;

    public EnableTracePanel(String filename) {
        JLabel label = new JLabel("Включить трассировку выполняемых процессором"
                + " инструкций");

        this.filename = filename;
        yesNoButton = new JToggleButton("Да", true);
        
        yesNoButton.addItemListener((ItemEvent e) -> {
            if (yesNoButton.isSelected()) {
                yesNoButton.setText("Да");
            } else {
                yesNoButton.setText("Нет");
            }
        });
        
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        add(label, BorderLayout.WEST);
        add(yesNoButton, BorderLayout.EAST);
    }

    @Override
    public String toString() {
        if (yesNoButton.isSelected()) {
            return "--trace " + filename;
        }
        
        return "";
    }
}
