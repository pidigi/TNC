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

	public abstract Object evaluate(Map<String,T> tMap, Map<String,Object> eMap);
	
	public abstract T getType(Map<String,T> tMap);

	public abstract boolean hasValidType(Map<String,T> tMap);
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public abstract boolean typeCheck(Map<String,T> tMap);
}
