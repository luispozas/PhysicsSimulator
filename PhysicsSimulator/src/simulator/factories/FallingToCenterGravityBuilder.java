package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{

	public FallingToCenterGravityBuilder() {
		super("ftcg", "Falling to center gravity law");
	}

	@Override
	public FallingToCenterGravity createObject(JSONObject data) throws JSONException {
		return new FallingToCenterGravity();
	}

}
