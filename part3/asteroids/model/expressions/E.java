package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.*;


public abstract class E {
	
	private final int line;
	private final int column;
	
	public E(int line, int column){
		this.line = line;
		this.column = column;
	}

	public abstract String evaluate(Map<String,T> tMap, Map<String,E> eMap);
	
	public abstract T getType();

	public abstract boolean hasValidType();
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
}
