package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Builder<T> {
	protected final String _typetag; 
	protected final String _desctag;
	
	public Builder(String typetag, String desctag) {
		_typetag = typetag;
		_desctag = desctag;
	}
	
	public abstract T createObject(JSONObject data) throws JSONException;
	
	public JSONObject createInfo() {
		return new JSONObject();
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T b = null;
		if(info != null && _typetag.equals(info.getString("type"))) {
			try {
				b = createObject(info.getJSONObject("data"));
			}
			catch(JSONException ex) {
				throw new IllegalArgumentException("Invalid JSONObject data", ex);
			}
		}
		return b;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject ob = new JSONObject();
		ob.put("type", _typetag);
		ob.put("data", createInfo());
		ob.put("desc", _desctag);
		return ob;
	}
	
	protected double[] jsArrayToDoubleArray(JSONArray ja) {
		double[] dvel = new double[ja.length()];
		for (int i = 0; i < ja.length(); i++) {
			dvel[i] = ja.getDouble(i);
		}
		return dvel;
	}
	
}
