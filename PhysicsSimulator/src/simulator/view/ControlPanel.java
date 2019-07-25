package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;


import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private static final long serialVersionUID = -5911412048135786713L;
	@SuppressWarnings("unused")
	private JButton open, physics, run, stop, exit, reset; 
	private JSpinner steps;
	private JLabel stepLabel, dtLabel;
	private JTextField dtime;
	private JToolBar toolBar;
	private Controller _ctrl;
	private MainWindow _mainWindow;
	
	
	public ControlPanel(Controller ctrl, MainWindow mw) {
		_ctrl = ctrl;
		_mainWindow = mw;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		toolBar = new JToolBar();
		
		open = addOpenButton("resources/icons/open.png", toolBar);
		toolBar.add(createSeparator());
		physics = addPhysicsButton("resources/icons/physics.png", toolBar);
		toolBar.add(createSeparator());
		run = addRunButton("resources/icons/run.png", toolBar);
		stop = addStopButton("resources/icons/stop.png", toolBar);
		reset = addResetButton("resources/icons/reset.png", toolBar);
		
		stepLabel = new JLabel(" Steps: ");
		stepLabel.setMinimumSize(new Dimension(50, 40));
		stepLabel.setPreferredSize(new Dimension(50, 40));
		stepLabel.setMaximumSize(new Dimension(50, 40));
		toolBar.add(stepLabel);
		
		steps = new JSpinner(new SpinnerNumberModel(10000, 1, 100000000, 100));
		steps.setMinimumSize(new Dimension(70, 30));
		steps.setPreferredSize(new Dimension(70, 30));
		steps.setMaximumSize(new Dimension(70, 30));
		toolBar.add(steps);
		
		dtLabel = new JLabel(" Delta-Time: ");
		dtLabel.setMaximumSize(new Dimension(80, 30));
		dtLabel.setPreferredSize(new Dimension(80, 30));
		dtLabel.setMinimumSize(new Dimension(80, 30));
		toolBar.add(dtLabel);
		
		dtime = new JTextField("1000.0");
		dtime.setMaximumSize(new Dimension(70, 30));
		dtime.setPreferredSize(new Dimension(70, 30));
		dtime.setMinimumSize(new Dimension(70, 30));
		dtime.setEditable(true);
		toolBar.add(dtime);
	
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(createSeparator());
		exit = addExitButton("resources/icons/exit.png", toolBar);
		this.add(toolBar);
	}
	
	private JSeparator createSeparator() {
		JSeparator s = new JSeparator(SwingConstants.VERTICAL);
		s.setMinimumSize(new Dimension(5,43));
		s.setPreferredSize(new Dimension(5,43));
		s.setMaximumSize(new Dimension(5,43));
		s.setBackground(Color.GRAY);
		return s;
	}
	
	private JButton addOpenButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Load a file");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.openFile();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	private JButton addPhysicsButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Choose Gravity Law");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.chooseGravityLaw();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	private JButton addRunButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Run simulation");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.runAction();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	private JButton addStopButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Stop simulation");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.stopAction();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	private JButton addResetButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Reset simulation");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.resetAction();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	private JButton addExitButton(String iconPath, JToolBar toolBar) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(iconPath));
		button.setToolTipText("Exit application");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.exitAction();
			}
		});
		toolBar.add(button);
		return button;
	}
	
	public Double getDTimeValue() {
		return Double.parseDouble(dtime.getText());
	}
	
	public int getStepsValue() {
		return (Integer) steps.getValue();
	}
	
	public void putEnableButtons(boolean enable) {
		open.setEnabled(enable);
		physics.setEnabled(enable);
		run.setEnabled(enable);
		exit.setEnabled(enable);
		reset.setEnabled(enable);
	}
	
	
	// SimulatorObserver methods
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		dtime.setText(Double.toString(dt));
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		dtime.setText(Double.toString(dt));
	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		dtime.setText(Double.toString(dt));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {}

	@Override
	public void onAdvance(List<Body> bodies, double time) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {}

}