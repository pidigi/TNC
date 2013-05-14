package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.types.T;

public abstract class BinaryE extends E {

	private final E e1;
	
	private final E e2;
	
	public BinaryE(int line, int column, E e1, E e2) {
		super(line, column);
		this.e1 = e1;
		this.e2 = e2;
	}

	public E getE1() {
		return e1;
	}

	public E getE2() {
		return e2;
	}
	
	@Override
	public boolean typeCheck(Map<String,T> tMap){
		return this.hasValidType(tMap) 
				&& getE1().typeCheck(tMap)
				&& getE2().typeCheck(tMap);
	}
}
