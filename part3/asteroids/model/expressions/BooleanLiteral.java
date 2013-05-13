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
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return String.valueOf(getBoolean());
	}

	@Override
	public T getType() {
		return new BooleanT();
	}

	@Override
	public boolean hasValidType() {
		return true;
	}

}
