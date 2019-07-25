package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private static final long serialVersionUID = 7659594686556692193L;
	private final String columnNames[] = {"Id", "Mass", "Position", "Velocity", "Acceleration"};
	private List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<Body>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return getBodyValue(_bodies.get(arg0), arg1);
	}
	
	private Object getBodyValue(Body b, int val) {
		Object obj = null;
		switch(val) {
			case 0: obj = b.getId(); break;
			case 1: obj = b.getMass(); break;
			case 2: obj = b.getPosition().toString(); break;
			case 3: obj = b.getVelocity().toString(); break;
			case 4: obj = b.getAcceleration().toString(); break;
		}
		return obj;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		updateTable(bodies);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		updateTable(bodies);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		updateTable(bodies);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		updateTable(bodies);
	}
	
	private void updateTable(List<Body> bodies) {
		_bodies = bodies;
		this.fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {}

}
