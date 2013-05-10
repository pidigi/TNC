package asteroids.model.expressions;

import asteroids.model.Ship;
import asteroids.model.types.EntityT;
import asteroids.model.types.T;

public abstract class EntityReference extends E {
	
	public EntityReference(int line, int column) {
		super(line, column);
	}

	@Override
	public abstract String evaluate(Ship ship);

	@Override
	public T getType() {
		return new EntityT();
	}

	@Override
	public boolean hasValidType() {
		return true;
	}

}
