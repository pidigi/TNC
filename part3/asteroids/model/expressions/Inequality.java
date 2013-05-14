package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Inequality extends Comparison {

	public Inequality(int line, int column, E e1, E e2) {
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
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		if(getE1().getType(tMap).isEntity())
			return String.valueOf(getE1().getElement(tMap, eMap) != getE2().getElement(tMap, eMap));
		return String.valueOf(getE1().evaluate(tMap, eMap) != getE2().evaluate(tMap, eMap));
	}

}
