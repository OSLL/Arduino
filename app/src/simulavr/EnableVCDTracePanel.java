package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author ploskov
 */
public class EnableVCDTracePanel extends JPanel {

    private final JToggleButton yesNoButton;
    private final JFrame vcdConfigFrame;

    public EnableVCDTracePanel(List<String> args) {
        JLabel label = new JLabel("Включить VCD трассировку");
        yesNoButton = new JToggleButton("Нет", false);

        vcdConfigFrame = new JFrame("Источники VCD трассировки");
        JPanel vcdConfigPanel = new JPanel();
        vcdConfigPanel.setLayout(new BoxLayout(vcdConfigPanel,
                BoxLayout.Y_AXIS));
        for (String arg : args) {
            vcdConfigPanel.add(new VCDFlagPanel(arg));
        }
        JScrollPane vcdScrollPane = new JScrollPane(vcdConfigPanel);
        vcdConfigFrame.add(vcdScrollPane, BorderLayout.CENTER);
        vcdConfigFrame.pack();

        yesNoButton.addItemListener((ItemEvent e) -> {
            if (yesNoButton.isSelected()) {
                yesNoButton.setText("Да");
                vcdConfigFrame.setVisible(true);
            } else {
                yesNoButton.setText("Нет");
                vcdConfigFrame.setVisible(false);
            }
        });

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        add(label, BorderLayout.WEST);
        add(yesNoButton, BorderLayout.EAST);
    }

    public boolean isSelected() {
        return yesNoButton.isSelected();
    }

    @Override
    public String toString() {
        if (yesNoButton.isSelected()) {
            return "-c";
        }

        return "";
    }
}
