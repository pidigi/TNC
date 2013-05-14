package asteroids.model.statements;

import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.expressions.E;
import asteroids.model.types.T;

public class Print extends S{
	public Print(int line, int column, E e) {
		super(line, column);
		this.exp = e;
	}
	
	public E getExp() {
		return this.exp;
	}
	
	private final E exp;
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		System.out.println(this.getExp().evaluate(globalTypes,globalExpr));
	}
}
