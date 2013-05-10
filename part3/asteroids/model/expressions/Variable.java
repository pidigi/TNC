package asteroids.model.expressions;

import asteroids.model.Ship;
import asteroids.model.types.T;

public class Variable extends E {

	private final String name;
	
	private T type;
	
	public Variable(int line, int column, String name) {
		super(line, column);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String evaluate(Ship ship) {
		return getName();
	}

	@Override
	public T getType() {
		return this.type;
	}
	
	public void setType(T type){
		this.type = type;
	}

	@Override
	public boolean hasValidType() {
		return true;
	}

}
