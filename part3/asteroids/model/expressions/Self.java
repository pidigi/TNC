package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.types.T;

public class Self extends EntityReference {

	public Self(int line, int column) {
		super(line, column);
	}

	@Override
	public Ship evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		if(!eMap.containsKey("self") || !(eMap.get("self") instanceof SEReference))
			throw new IllegalArgumentException();
		return (Ship) (eMap.get("self")).evaluate(tMap,eMap);
	}
}
