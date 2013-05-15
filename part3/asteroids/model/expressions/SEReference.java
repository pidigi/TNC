package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.SpatialElement;
import asteroids.model.types.T;

public class SEReference extends EntityReference {

	private final SpatialElement element;	
	
	public SEReference(int line, int column, SpatialElement element) {
		super(line, column);
		this.element = element;
	}

	@Override
	public SpatialElement evaluate(Map<String, T> tMap, Map<String,Object> eMap) {
		return this.getElement();
	}

	public SpatialElement getElement() {
		return this.element;
	}
}
