
package processing.app;

import gtkwave.GtkWave;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import static processing.app.I18n.tr;

public class DebugToolbar extends JPanel {
	Editor editor;
	/** Titles for each button when the shift key is pressed. */ 
	static final String titleShift[] = {
		tr("Start debug"), tr("Continue"), tr("Stop"), tr("Step Into"), tr("Step Over"),
		tr("Step Out"), tr("Set/Unset breakpoint"), tr("Variable list"), tr("Registration"), tr("Open GTKWave"), tr("Open SimulAVR config"), tr("Start simulator"), tr("Stop simulator") 
	};	
	static final int DEBUG     = 0;
	static final int CONTINUE  = 1;
	static final int STOP      = 2;
	static final int STEP_IN   = 3;
	static final int STEP_OVER = 4;
	static final int STEP_OUT  = 5;
	static final int SET_UNSET_BRPT  = 6;
	static final int VAR_LIST  = 7;
	static final int REGISTR  = 8;
	static final int GTKWAVE  = 9;
	static final int SIMULAVR  = 10;
	static final int STARTSIMUL  = 11;
	static final int STOPSIMUL  = 12;
	private JButton debugButton;
	private JButton continueButton;
	private JButton stopButton;
	private JButton stepInButton;
	private JButton stepOverButton;
	private JButton stepOutButton;
	private JButton breakpointButton;
	private JButton varListButton;
	private JButton registrationButton;
//	private JButton GtkWaveButton;
//	private JButton simulAvrConfigButton;
	private JButton startSimulButton;
	private JButton stopSimulButton;
//	private JCheckBox useSimulatorChekBox;
//	GtkWave gtk;
		
	public DebugToolbar(Editor _editor) {
		editor = _editor;
		this.setPreferredSize(new Dimension(800, 40));
		debugButton = new JButton(new ImageIcon(Theme.getThemeImage("bug", this, 15, 15)));
		debugButton.setToolTipText(titleShift[DEBUG]);
		debugButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String newKey = (String) JOptionPane.showInputDialog(null, tr("Please, enter your key to debug:"), tr("Access key"), JOptionPane.INFORMATION_MESSAGE, null, null, editor.debugKey);
				if(newKey == null)
					return;
				editor.setDebugKey(newKey);
				//TODO need to write universal handle in Editor to coordinate simulator/mcu selection there
				if(editor.isUseSimulator()){
					if(editor.simulavrFrame.getConfigs()== null){
						System.err.println("Configure simulator first");
						return;
					}
					editor.handleRun(false, editor.presentDebugSimulatorHandler, editor.runDebugSimulatorHandler);
				}else{
					editor.handleRun(false, editor.presentDebugHandler, editor.runDebugHandler);	
				}
				
			}
		});
		continueButton = new JButton(new ImageIcon(Theme.getThemeImage("resume", this, 15, 15)));
		continueButton.setContentAreaFilled(false);
		continueButton.setPreferredSize(new Dimension(25, 25));
		continueButton.setToolTipText(titleShift[CONTINUE]);
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.debugProcess.resume();
			}
		});
		stopButton = new JButton(new ImageIcon(Theme.getThemeImage("stop", this, 15, 15)));
		stopButton.setContentAreaFilled(false);
		stopButton.setPreferredSize(new Dimension(25, 25));
		stopButton.setToolTipText(titleShift[STOP]);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.StopDebugSession();
			//TODO а завершается ли симулятор при завершении отладочной сессии??	
			}
		});
		stepInButton = new JButton(new ImageIcon(Theme.getThemeImage("step_into", this, 15, 15)));
		stepInButton.setContentAreaFilled(false);
		stepInButton.setPreferredSize(new Dimension(25, 25));
		stepInButton.setToolTipText(titleShift[STEP_IN]);
		stepInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.debugProcess.startStepInto();
			}
		});
		stepOverButton = new JButton(new ImageIcon(Theme.getThemeImage("step_over", this, 15, 15)));
		stepOverButton.setContentAreaFilled(false);
		stepOverButton.setPreferredSize(new Dimension(25, 25));
		stepOverButton.setToolTipText(titleShift[STEP_OVER]);
		stepOverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.debugProcess.startStepOver();
			}
		});
		stepOutButton = new JButton(new ImageIcon(Theme.getThemeImage("step_out", this, 15, 15)));
		stepOutButton.setContentAreaFilled(false);
		stepOutButton.setPreferredSize(new Dimension(25, 25));
		stepOutButton.setToolTipText(titleShift[STEP_OUT]);
		stepOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.debugProcess.startStepOut();
			}
		});
		breakpointButton = new JButton(new ImageIcon(Theme.getThemeImage("set_unset_bp", this, 32, 15)));
		breakpointButton.setContentAreaFilled(false);
		breakpointButton.setPreferredSize(new Dimension(35, 25));
		breakpointButton.setToolTipText(titleShift[SET_UNSET_BRPT]);
		breakpointButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.handleSetUnsetBreakpoint();
			}
		});
		varListButton = new JButton(new ImageIcon(Theme.getThemeImage("var_list", this, 15, 15)));
		varListButton.setContentAreaFilled(false);
		varListButton.setPreferredSize(new Dimension(25, 25));
		varListButton.setToolTipText(titleShift[VAR_LIST]);
		varListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.varFrame != null)
					editor.varFrame.setVisible(true);
			}
		});
		registrationButton = new JButton(tr("Registration"));
		registrationButton.setToolTipText(titleShift[REGISTR]);
		registrationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.registrationFrame != null)
					editor.registrationFrame.setVisible(true);
			}
		});
/*		
 * 		useSimulatorChekBox = new JCheckBox(tr("Use SimulAVR"), false);
		useSimulatorChekBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox chb = (JCheckBox) arg0.getSource();
				if(chb.isSelected()){
					System.out.println("Trying load simulator configs");
					if(editor.loadSimulatorInitConfig()){
						System.out.println("Configs successfuly loaded");
						simulatorSelectedEvent();
						return;
					}
					System.err.println("Unable to load simulator configs");
					chb.setSelected(false);
				}else{
					editor.communicator.stopSimulator(editor.debugKey);
					simulatorDeselectedEvent();
				}
			}
		});
		GtkWaveButton = new JButton(tr("GTKWave"));
		GtkWaveButton.setToolTipText(titleShift[GTKWAVE]);
		GtkWaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gtk = new GtkWave(editor.sketchController.getSketch().getFolder().getAbsolutePath() + "/" + "simulAVR-vcd-output");
				gtk.start();
			}
		});

		simulAvrConfigButton = new JButton(tr("SimulAVR config"));
		simulAvrConfigButton.setToolTipText(titleShift[SIMULAVR]);
		simulAvrConfigButton.setEnabled(false);
		simulAvrConfigButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.simulavrFrame.setVisible(true);
			}
		});
*/		
		startSimulButton = new JButton(tr("Start sim"));
		startSimulButton.setToolTipText(titleShift[STARTSIMUL]);
		startSimulButton.setEnabled(false);
		startSimulButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String newKey = (String) JOptionPane.showInputDialog(null, tr("Please, enter your key to debug:"), tr("Access key"), JOptionPane.INFORMATION_MESSAGE, null, null, editor.debugKey);
				if(newKey == null){
					System.err.println("You must enter the key");
					return;
				}
				editor.setDebugKey(newKey);
				if(editor.isUseSimulator()){
					if(editor.simulavrFrame.getConfigs()== null){
						System.err.println("Configure simulator first");
						return;
					}
					editor.handleRun(false, editor.presentDebugSimulatorHandler, editor.runDebugSimulatorHandler);
					simulatorStartedEvent();
				}
			}
		});
		
		stopSimulButton = new JButton(tr("Stop sim"));
		stopSimulButton.setToolTipText(titleShift[STOPSIMUL]);
		stopSimulButton.setEnabled(false);
		stopSimulButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(editor.debugProcess != null)
					editor.StopDebugSession();
				editor.communicator.stopSimulator(editor.debugKey);
				simulatorStopedEvent();
			}
		});
		
		setLayout(new FlowLayout());
		//add(debugButton);
		add(continueButton);
		//add(stopButton);
		add(stepInButton);
		add(stepOverButton);
		add(stepOutButton);
		add(breakpointButton);
		add(varListButton);
		//add(Box.createHorizontalGlue());
		//add(registrationButton);
		add(Box.createHorizontalGlue());
		add(startSimulButton);
		add(stopSimulButton);
		add(Box.createHorizontalGlue());
		//add(useSimulatorChekBox);
		add(Box.createHorizontalGlue());
		//add(GtkWaveButton);
		//add(simulAvrConfigButton);
		debugButtonsEnable(false);
	}
	
	public void debugButtonsEnable(boolean flag){
		continueButton.setEnabled(flag);
		stopButton.setEnabled(flag);
		stepInButton.setEnabled(flag);
		stepOverButton.setEnabled(flag);
		stepOutButton.setEnabled(flag);
		//breakpointButton.setEnabled(flag);
		varListButton.setEnabled(flag);
	}
	
	public void simulatorStartedEvent(){
		stopSimulButton.setEnabled(true);
		debugButtonsEnable(true);
		//show debug buttons 
	}
	
	public void simulatorStopedEvent(){
		stopSimulButton.setEnabled(false);
		debugButtonsEnable(false);
		//hide debug buttons
	}
	
	public void simulatorSelectedEvent(){
		//simulAvrConfigButton.setEnabled(true);
		startSimulButton.setEnabled(true);
		stopSimulButton.setEnabled(false);
	}
	
	public void simulatorDeselectedEvent(){
		//simulAvrConfigButton.setEnabled(false);
		startSimulButton.setEnabled(false);
		stopSimulButton.setEnabled(false);
	}
	
	public void targetIsRunning(){
		continueButton.setEnabled(false);
		
		stepInButton.setEnabled(false);
		stepOverButton.setEnabled(false);
		stepOutButton.setEnabled(false);
		breakpointButton.setEnabled(false);
		
		stopSimulButton.setEnabled(true);
	}
	
	public void targetIsStopped(){
		continueButton.setEnabled(true);
		stepInButton.setEnabled(true);
		stepOverButton.setEnabled(true);
		stepOutButton.setEnabled(true);
		breakpointButton.setEnabled(true);
	}
	
	
}
