package asteroids.model.expressions;

import java.util.Map;
import asteroids.model.types.*;

public class DoubleLiteral extends E {
	
	private final double d;

	public DoubleLiteral(int line, int column, double d) {
		super(line, column);
		this.d = d;
	}

	public double getDouble() {
		return d;
	}

	@Override
	public String evaluate(Map<String,T> tMap, Map<String,E> eMap) {
		return String.valueOf(getDouble());
	}

	@Override
	public DoubleT getType(Map<String,T> tMap) {
		return new DoubleT();
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) {
		return true;
	}
	
	@Override
	public boolean typeCheck(Map<String,T> tMap){
		return this.hasValidType(tMap);
	}

}
