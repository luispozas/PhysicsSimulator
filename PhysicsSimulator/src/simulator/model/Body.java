package simulator.model;
import simulator.misc.Vector;

public class Body {

	protected String id;
	protected Vector v;
	protected Vector a;
	protected Vector p;
	protected double m;
	
	public Body(String id, Vector v, Vector a, Vector p, double m) {
		this.id = id;
		this.v = v;
		this.a = a;
		this.p = p;
		this.m = m;
	}
	
	public String getId() {
		return id;
	}
	
	public Vector getVelocity() {
		return new Vector(v);
	}
	
	public Vector getAcceleration() {
		return new Vector(a);
	}
	
	public Vector getPosition() {
		return new Vector(p);
	}
	
	public double getMass() {
		return m;
	}
	
	void setVelocity(Vector v) {
		this.v = new Vector(v);
	}
	
	void setAcceleration(Vector a) {
		this.a = new Vector(a);
	}
	
	void setPosition(Vector p) {
		this.p = new Vector(p);
	}
	
	void move(double t) {
		p = p.plus(v.scale(t).plus(a.scale(t*t / 2)));
		v = v.plus(a.scale(t));	
	}

	@Override
	public String toString() {
		return "{  \"id\": \"" + id + "\", \"mass\": " + m + ", \"pos\": " + p + ", \"vel\": " + v + ", \"acc\": " + a +" }";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		
		return true;
	}
	
	
}
