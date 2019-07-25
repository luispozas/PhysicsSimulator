package simulator.model;

import java.util.List;
import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws{
 	public static double G = 6.67E-11;
	
	public NewtonUniversalGravitation() {}
	
	@Override
	public void apply(List<Body>bodies) {
		for(int i = 0; i < bodies.size(); i++) {
			Body b = bodies.get(i);
			if(b.getMass() > 0.0) {
				b.setAcceleration(gravityForce(bodies, i).scale(1 / b.getMass()));
			}
			else {
				b.setVelocity(new Vector(bodies.get(0).getPosition().dim()));
				b.setAcceleration(new Vector(bodies.get(0).getPosition().dim()));
			}
		}
	}
	
	
	private Vector gravityForce(List<Body> bodies, int idx) {
		Vector sum = new Vector(bodies.get(0).getPosition().dim());
		double sumPart = 0;
		for(int i = 0; i < bodies.size(); i++) {
			if(i != idx) {
				Vector aux = bodies.get(i).getPosition().minus(bodies.get(idx).getPosition());
				sumPart = G * (bodies.get(i).getMass() * bodies.get(idx).getMass() / (aux.magnitude()* aux.magnitude()));
				sum = sum.plus(aux.direction().scale(sumPart));
			}
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Newton’s universal gravitation";
	}
	
	
}
