package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

	private double deltaTime;
	private double currentTime = 0.0;
	GravityLaws actualLaw;
	private List<Body> bodies;
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(double deltaTime, GravityLaws law){
		bodies = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
		setDeltaTime(deltaTime); 
		setGravityLaws(law);
	}
	
	public void advance() {
		if(bodies.isEmpty()) throw new IllegalArgumentException("There aren't bodies in the Physics Simulator.");
		actualLaw.apply(bodies);
		for(Body b : bodies) {
			b.move(deltaTime);
		}
		currentTime += deltaTime;
		for(SimulatorObserver o : observers) 
			o.onAdvance(bodies, currentTime);
	}
	
	public void addBody(Body b) {
		if(b == null) throw new IllegalArgumentException("Invalid body");
		if(bodies.contains(b)) throw new IllegalArgumentException("Body already included");
		bodies.add(b);
		for(SimulatorObserver o : observers) 
			o.onBodyAdded(bodies, b);
	}
	
	public void reset() {
		bodies.clear();
		currentTime = 0.0;
		for(SimulatorObserver o : observers) 
			o.onReset(bodies, currentTime, deltaTime, actualLaw.toString());
	}
	
	public void setDeltaTime(double dt) {
		if(dt <= 0) throw new IllegalArgumentException("Invalid Delta-Time."); 
		deltaTime = dt;
		for(SimulatorObserver o : observers) 
			o.onDeltaTimeChanged(dt);
	}
	
	public void setGravityLaws(GravityLaws gravityLaws) {
		if(gravityLaws == null) throw new IllegalArgumentException("Gravity Law doesn't exist.");
		actualLaw = gravityLaws;
		for(SimulatorObserver o : observers) 
			o.onGravityLawChanged(gravityLaws.toString());
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!observers.contains(o)) observers.add(o); 
		o.onRegister(bodies, currentTime, deltaTime, actualLaw.toString());
	}
	
	public void deleteObserver(SimulatorObserver o) {
		if(observers.contains(o)) observers.remove(o);
		o.onRegister(bodies, currentTime, deltaTime, actualLaw.toString());
	}
	
	
	@Override
	public String toString(){
		StringBuilder st = new StringBuilder("");
		for(Body b : bodies) {
			st.append(b);
			st.append(", ");
		}
		if(st.length() > 0) st.delete(st.length() - 2, st.length());
		return "{ \"time\": " + currentTime + ", \"bodies\": [ " + st +" ] }";
	}
}
