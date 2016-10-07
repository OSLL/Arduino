package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author ploskov
 */
public class MaximumRunTimePanel extends JPanel {

    private final JFormattedTextField maximumRunTimeField;

    public MaximumRunTimePanel() {
        JLabel label = new JLabel("Максимальное время работы симулятора (нс)");

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        maximumRunTimeField = new JFormattedTextField(numberFormatter);
        maximumRunTimeField.setColumns(10);
        maximumRunTimeField.setHorizontalAlignment(SwingConstants.RIGHT);

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        add(label, BorderLayout.WEST);
        add(maximumRunTimeField, BorderLayout.EAST);
    }

    @Override
    public String toString() {
        if (maximumRunTimeField.getText().isEmpty()) {
            return "";
        }

        return "-m " + maximumRunTimeField.getText();
    }
}
