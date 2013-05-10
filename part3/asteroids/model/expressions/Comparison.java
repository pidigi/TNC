package asteroids.model.expressions;

import asteroids.model.types.*;

public abstract class Comparison extends BinaryE {

	public Comparison(int line, int column, E e1, E e2) {
		super(line, column, e1, e2);
	}

	@Override
	public boolean hasValidType() {
		return (getE1().getType().isDouble() 
				&& getE2().getType().isDouble());
	}
		
	@Override
	public BooleanT getType(){
		return new BooleanT();
	}
	
}
