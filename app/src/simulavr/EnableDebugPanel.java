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
public class EnableDebugPanel extends JPanel {

    private final JToggleButton yesNoButton;

    public EnableDebugPanel() {
        JLabel label = new JLabel("Запустить режим отладки");

        yesNoButton = new JToggleButton("Нет", false);

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
            return "--gdbserver";
        }

        return "";
    }
}
