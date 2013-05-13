package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.SpatialElement;
import asteroids.model.types.EntityT;
import asteroids.model.types.T;

public abstract class EntityReference extends E {
	
	public EntityReference(int line, int column) {
		super(line, column);
	}

	@Override
	public T getType() {
		return new EntityT();
	}

	@Override
	public boolean hasValidType() {
		return true;
	}
	
	public abstract SpatialElement getElement(Map<String,T> tMap, Map<String,E> eMap);

}
