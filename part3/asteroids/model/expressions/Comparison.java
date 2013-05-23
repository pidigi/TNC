package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.types.*;

public abstract class Comparison extends BinaryE {

	public Comparison(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public boolean hasValidType(Map<String,T> tMap) 
			throws NullPointerException{
		return (getE1().getType(tMap).isDouble() 
				&& getE2().getType(tMap).isDouble());
	}
		
	@Override
	public BooleanT getType(Map<String,T> tMap){
		return new BooleanT();
	}
}
