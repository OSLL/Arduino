package simulavr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
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
public class CPUFrequencePanel extends JPanel {

    private final JFormattedTextField cpuFrequenceField;

    public CPUFrequencePanel(int defaultFrequence) {
        JLabel label = new JLabel("Частота CPU (Гц)");

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        cpuFrequenceField = new JFormattedTextField(numberFormatter);
        cpuFrequenceField.setText(Integer.toString(defaultFrequence));
        cpuFrequenceField.setColumns(10);
        cpuFrequenceField.setHorizontalAlignment(SwingConstants.RIGHT);
        
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        add(label, BorderLayout.WEST);
        add(cpuFrequenceField, BorderLayout.EAST);
    }
    
    @Override
    public String toString() {
        if (cpuFrequenceField.getText().isEmpty()) {
            return "";
        }
        
        return "--cpufrequency " + cpuFrequenceField.getText();
    }
}
