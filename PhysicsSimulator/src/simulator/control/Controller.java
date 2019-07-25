package simulator.control;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	private PhysicsSimulator _sim;
	private Factory<Body> _bodiesFactory;
	private Factory<GravityLaws> _lawsFactory;
	private File _file;
	
	public Controller(PhysicsSimulator sim, Factory<Body> bodiesFactory, Factory<GravityLaws> lawsFactory) {
		_sim = sim;
		_bodiesFactory = bodiesFactory;
		_lawsFactory = lawsFactory;
	}
	
	public void setFile(File f) { _file = f; }
	
	public File getFile() { return _file; }
	
	public void loadBodies(InputStream in) throws IllegalArgumentException{  //in contiene una estructura de la forma { "bodies": [bb1,...,bbn] }
		try {
			JSONObject jsonInput = new JSONObject(new JSONTokener(in));
			JSONArray bodies = jsonInput.getJSONArray("bodies");
			for(int i = 0; i < bodies.length(); ++i) {
				_sim.addBody(_bodiesFactory.createInstance(bodies.getJSONObject(i)));
			}
		}
		catch(JSONException ex) {
			throw new IllegalArgumentException("Invalid JSONObject bodies", ex);
		}
	}
	
	public void run(int n, OutputStream o) {
		PrintStream out = new PrintStream(((o == null) ? System.out : o));
		out.println("{");
		out.println("\"states\": [");
		out.print(_sim);
		if(n != 0) out.println(", ");
		for(int i = 0; i < n; ++i) {
			_sim.advance();
			out.print(_sim);
			if(i != n - 1) out.println(", ");
		}
		out.format("%n]%n}%n");
		out.flush();
	}
	
	public void run(int n) {
		for(int i = 0; i < n; ++i) {
			_sim.advance();
		}
	}
	
	public void reset() {
		_sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		_sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		_sim.addObserver(o);
	}
	
	public void deleteObserver(SimulatorObserver o) {
		_sim.deleteObserver(o);
	}
	
	public Factory<GravityLaws> getGravityLawsFactory(){
		return _lawsFactory;
	}
	
	public void setGravityLaws(JSONObject info) {
		_sim.setGravityLaws(_lawsFactory.createInstance(info));
	}
	
}
