package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class Self extends EntityReference {

	public Self(int line, int column) {
		super(line, column);
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return "self";
	}

	public SpatialElement getElement(Map<String, T> tMap, Map<String, E> eMap){
		if(!eMap.containsKey("self") || !(eMap.get("self") instanceof SEReference))
			throw new IllegalArgumentException();
		return ((SEReference) eMap.get("self")).getElement(tMap, eMap);		
	}
	
}
