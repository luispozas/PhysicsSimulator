package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	
	private static final long serialVersionUID = 7975272576602063959L;
	private static final String _nameTime = " Time:  ";
	private static final String _nameBodies = " Bodies:  ";
	private static final String _nameLaws = "Laws:  ";
	
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		_currTime = new JLabel(_nameTime + "0.0");
		_currTime.setPreferredSize(new Dimension(120, 15));
		this.add(_currTime);
		this.add(createSeparator());
		
		_numOfBodies = new JLabel(_nameBodies + "0");
		_numOfBodies.setPreferredSize(new Dimension(90, 15));
		this.add(_numOfBodies);
		this.add(createSeparator());
		
		_currLaws = new JLabel(_nameLaws + "...");
		this.add(_currLaws);
	}
	
	private JSeparator createSeparator() {
		JSeparator s = new JSeparator(SwingConstants.VERTICAL);
		s.setMinimumSize(new Dimension(1, 24));
		s.setPreferredSize(new Dimension(1, 24));
		s.setMaximumSize(new Dimension(1, 24));
		s.setBackground(Color.GRAY);
		return s;
	}
	
	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_currTime.setText(_nameTime + time);
		_numOfBodies.setText(_nameBodies + bodies.size());
		_currLaws.setText(_nameLaws + gLawsDesc);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_currTime.setText(_nameTime + time);
		_numOfBodies.setText(_nameBodies + bodies.size());
		_currLaws.setText(_nameLaws + gLawsDesc);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_numOfBodies.setText(_nameBodies + bodies.size());
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_currTime.setText(_nameTime + time);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		_currLaws.setText(_nameLaws + gLawsDesc);
	}

}
