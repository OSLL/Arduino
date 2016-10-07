package simulavr;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author ploskov
 */
public class SimulAVR {

    private final SimulAVRFrame mainFrame;
    private JPanel mainPanel;
    private List<String> argsList;

    public SimulAVR() {
        mainFrame = new SimulAVRFrame();

        mainFrame.setSize(800, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                argsList = Arrays.stream(mainPanel.getComponents())
                        .map(Component::toString)
                        .filter(arg -> arg != null && !arg.isEmpty())
                        .collect(Collectors.toList());
                System.exit(0);
            }
        });
    }

    public void makeGUI(String[] microcontrollerList, 
            String traceFilename,
            List<String> vcdArgs) {
        mainPanel.removeAll();

        MicrocontrollerModelPanel modelPanel
                = new MicrocontrollerModelPanel(microcontrollerList);
        mainPanel.add(modelPanel);

        CPUFrequencePanel frequencePanel
                = new CPUFrequencePanel(4000000);
        mainPanel.add(frequencePanel);
        
        EnableTracePanel tracePanel
                = new EnableTracePanel(traceFilename);
        mainPanel.add(tracePanel);
        
        EnableDebugPanel debugPanel
                = new EnableDebugPanel();
        mainPanel.add(debugPanel);
        
        MaximumRunTimePanel runTimePanel
                = new MaximumRunTimePanel();
        mainPanel.add(runTimePanel);
        
        EnableVCDTracePanel vcdTracePanel
                = new EnableVCDTracePanel(vcdArgs);
        mainPanel.add(vcdTracePanel);
    }

    public void run() {
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public List<String> getCurrentArgumentsList() {
        if (mainFrame.isShowing()) {
            return Arrays.stream(mainPanel.getComponents())
                    .map(Component::toString)
                    .filter(arg -> arg != null && !arg.isEmpty())
                    .collect(Collectors.toList());
        }

        return argsList;
    }

    // FIXME: remove this test method
    public static void main(String[] args) {
        SimulAVR app = new SimulAVR();

        String[] microcontrollerList = {"at90can128",
            "at90can32",
            "at90can64",
            "at90s4433",
            "at90s8515",
            "atmega128",
            "atmega1284a",
            "atmega16",
            "atmega164a",
            "atmega168",
            "atmega32",
            "atmega324a",
            "atmega328",
            "atmega48",
            "atmega644a",
            "atmega8",
            "atmega88",
            "attiny2313"};
        
        // тут я не знаю, как выглядит vcdList
        List<String> vcdList = Arrays.asList(microcontrollerList);

        app.makeGUI(microcontrollerList, "output.trace", vcdList);

        app.run();

        while (true) {
            System.out.println(app.getCurrentArgumentsList());
        }
    }
}
