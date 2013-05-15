package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Equality extends Comparison {

	public Equality(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}
	
	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return (getE1().getType(tMap).isDouble() 
				&& getE2().getType(tMap).isDouble() ||
				getE1().getType(tMap).isEntity()
				&& getE2().getType(tMap).isEntity());
	}
	
	@Override
	public Boolean evaluate(Map<String,T> tMap, Map<String,Object> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		// TODO: deze methode checken
		// TODO overal hasValidType checken in evaluate!!!
		// TODO ook boolean ondersteunen?
		if(getE1().getType(tMap).isEntity())
			return getE1().evaluate(tMap, eMap) == getE2().evaluate(tMap, eMap);
		return getE1().evaluate(tMap, eMap).equals(getE2().evaluate(tMap, eMap));
	}
}
