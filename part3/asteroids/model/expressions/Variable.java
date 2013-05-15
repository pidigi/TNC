package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.T;

public class Variable extends E {

	private final String name;
	
	public Variable(int line, int column, String name) {
		super(line, column);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Object evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return eMap.get(getName()).evaluate(tMap, eMap);
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return tMap.get(getName());
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return tMap.containsKey(this.getName());
	}
	
	@Override
	public boolean typeCheck(Map<String,T> tMap){
		return hasValidType(tMap);
	}
}
