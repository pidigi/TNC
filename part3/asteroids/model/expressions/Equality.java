package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Equality extends Comparison {

	public Equality(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}
	
	@Override
	public boolean hasValidType(Map<String,T> tMap) 
			throws NullPointerException{
		return (getE1().getType(tMap).isDouble() 
				&& getE2().getType(tMap).isDouble()) ||
				(getE1().getType(tMap).isEntity()
				&& getE2().getType(tMap).isEntity()) ||
				(getE1().getType(tMap).isBoolean()
				&& getE2().getType(tMap).isBoolean());
	}
	
	@Override
	public Boolean evaluate(Map<String,T> tMap, Map<String,Object> eMap)
			throws IllegalArgumentException, NullPointerException{
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		if(getE1().getType(tMap).isEntity())
			return getE1().evaluate(tMap, eMap) == getE2().evaluate(tMap, eMap);
		return getE1().evaluate(tMap, eMap).equals(getE2().evaluate(tMap, eMap));
	}
}
