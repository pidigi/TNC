package asteroids.model.expressions;

public abstract class UnitaryE extends E {
	
	private final E e;
		
	public UnitaryE(int line, int column, E e){
		super(line,column);
		this.e = e;
	}

	public E getE() {
		return e;
	}
}
