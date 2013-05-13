package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.*;

public abstract class Property extends UnitaryE {

	public Property(int line, int column, E e) {
		super(line, column, e);
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return getE().getType(tMap).isEntity();
		// TODO exacte type checken via String compare? (wss niet)
	}
	
	protected SpatialElement getElement(Map<String, T> tMap, Map<String, E> eMap){
		if(!hasValidType(tMap))
			throw new IllegalArgumentException();
		return ((EntityReference) getE()).getElement(tMap, eMap);
	}

}
