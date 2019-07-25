package simulator.model;

import simulator.misc.Vector;

public class MassLosingBody extends Body {
	private final double lossFactor; //Entre 0 y 1 que representa el factor de perdida de masa.
	private final double lossFrequency; //Numero positivo que indica el intervalo de tiempo despu�s del cual el objeto pierde masa.
	private double contadorTime;
	
	public MassLosingBody(double lossFactor, double lossFrequency, String id, Vector v, Vector a, Vector p, double m) {
		super(id, v, a, p, m);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		contadorTime = 0.0;
	}
	
	@Override
	void move(double t) {
		super.move(t);
		contadorTime += t;
		if(contadorTime >= lossFrequency) {
			m = m*(1-lossFactor);
			contadorTime = 0.0;
		}
	}
	
	@Override
	public String toString() {
		return "{  \"id\": \"" + id + "\", \"mass\": " + m + ", \"pos\": " + p + ", \"vel\": " + v + ", \"acc\": " + a + ", \"freq\": " + lossFrequency + ", \"factor\": " + lossFactor + " }";
	}
	
}
