package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ploskov
 */
public class VCDFlagPanel extends JPanel {

    private JCheckBox checkBox;

    public VCDFlagPanel(String name) {
        JLabel label = new JLabel(name);
        checkBox = new JCheckBox();

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        add(label, BorderLayout.WEST);
        add(checkBox, BorderLayout.EAST);
    }
}
