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
	public String evaluate(Map<String, T> tMap, Map<String, E> eMap) {
		return String.valueOf(getElement(tMap, eMap));
	}

	public SpatialElement getElement(Map<String, T> tMap, Map<String, E> eMap) {
		return this.element;
	}

}
