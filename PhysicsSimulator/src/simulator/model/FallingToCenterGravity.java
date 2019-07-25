package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {
	public static double g = 9.81;
	
	public FallingToCenterGravity() {}
	
	@Override
	public void apply(List<Body>bodies) {
		for(Body b : bodies) {
			b.setAcceleration(b.getPosition().direction().scale(-g));
		}
	}
	
	@Override
	public String toString() {
		return "Falling to center gravity";
	}

}
