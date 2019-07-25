package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T>{

	private final List<Builder<T>> _builders;
	private final List<JSONObject> _factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = new ArrayList<Builder<T>>(builders);
		_factoryElements = getJSONList();
	}
	
	@Override
	public T createInstance(JSONObject info) {
		for(Builder<T> b : _builders) {
			T obj = b.createInstance(info);
			if (obj != null) return obj;
		}
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		return new ArrayList<JSONObject>(_factoryElements);
	}
	
	private List<JSONObject> getJSONList() {
		List<JSONObject> _factElem = new ArrayList<JSONObject>();
		for(Builder<T> b : _builders) {
			_factElem.add(b.getBuilderInfo());
		}
		return _factElem;
	}

}
