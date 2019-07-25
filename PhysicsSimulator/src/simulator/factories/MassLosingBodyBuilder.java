package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body>{

	public MassLosingBodyBuilder() {
		super("mlb", "Mass losing body");
	}

	@Override
	public MassLosingBody createObject(JSONObject data) throws JSONException {
		Vector vel = new Vector(jsArrayToDoubleArray(data.getJSONArray("vel")));
		Vector pos = new Vector(jsArrayToDoubleArray(data.getJSONArray("pos")));
		Vector acc = new Vector(vel.dim());
		double mass = data.getDouble("mass");
		String id = data.getString("id");
		double lossFactor = data.getDouble("factor");
		double lossFrequency = data.getDouble("freq");
		return new MassLosingBody(lossFactor, lossFrequency, id, vel, acc, pos, mass);
	}
	
	@Override
	public JSONObject createInfo() {
		JSONObject ob = new JSONObject();
		ob.put("id", "Identifier of body");
		ob.put("pos", "Position in the sim");
		ob.put("vel", "Instant speed");
		ob.put("mass", "Mass of body");
		ob.put("factor", "Mass losing factor");
		ob.put("freq", "Mass losing frecuency");
		return ob;
	}

}
