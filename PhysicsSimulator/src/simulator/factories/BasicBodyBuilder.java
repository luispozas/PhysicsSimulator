package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	public BasicBodyBuilder() {
		super("basic", "Basic physical body");
	}

	@Override
	public Body createObject(JSONObject data) throws JSONException {
		Vector vel = new Vector(jsArrayToDoubleArray(data.getJSONArray("vel")));
		Vector pos = new Vector(jsArrayToDoubleArray(data.getJSONArray("pos")));
		Vector acc = new Vector(vel.dim());
		double mass = data.getDouble("mass");
		String id = data.getString("id");
		return new Body(id, vel, acc, pos, mass);
	}
	
	@Override
	public JSONObject createInfo() {
		JSONObject ob = new JSONObject();
		ob.put("id", "Identifier of body");
		ob.put("pos", "Position in the sim");
		ob.put("vel", "Instant speed");
		ob.put("mass", "Mass of body");
		return ob;
	}

}
