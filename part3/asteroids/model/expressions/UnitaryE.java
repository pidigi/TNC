package asteroids.model.expressions;

import java.util.Map;

import asteroids.model.types.T;

public abstract class UnitaryE extends E {
	
	private final E e;
		
	public UnitaryE(int line, int column, E e){
		super(line,column);
		this.e = e;
	}

	public E getE() {
		return e;
	}
	
	@Override
	public boolean typeCheck(Map<String,T> tMap)
			throws NullPointerException{
		return this.hasValidType(tMap) 
				&& getE().typeCheck(tMap);
	}
}
