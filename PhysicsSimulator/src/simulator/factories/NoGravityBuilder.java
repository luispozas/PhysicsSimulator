package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{

	public NoGravityBuilder() {
		super("ng", "No gravity law");
	}

	@Override
	public NoGravity createObject(JSONObject data) throws JSONException {
		return new NoGravity();
	}

}
