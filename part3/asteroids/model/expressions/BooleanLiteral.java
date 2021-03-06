package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.BooleanT;
import asteroids.model.types.T;

public class BooleanLiteral extends E {

	private final boolean b;
	
	public BooleanLiteral(int line, int column, boolean b) {
		super(line, column);
		this.b = b;
	}

	public boolean getBoolean() {
		return b;
	}

	@Override
	public Boolean evaluate(Map<String,T> tMap, Map<String,Object> eMap) {
		return getBoolean();
	}

	@Override
	public T getType(Map<String,T> tMap) {
		return new BooleanT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return true;
	}
	
	@Override
	public boolean typeCheck(Map<String,T> tMap)
			throws NullPointerException{
		return this.hasValidType(tMap);
	}

}
