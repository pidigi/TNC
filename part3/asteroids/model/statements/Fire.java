package asteroids.model.statements;

import java.util.Map;

import asteroids.model.*;
import asteroids.model.expressions.E;
import asteroids.model.types.T;

public class Fire extends Action{
	public Fire(int line,int column) {
		super(line,column);
	}
	
	@Override
	public S getStatement(int line) {
		if (this.getLine() == line) {
			return this;
		}
		return null;
	}
	
	@Override
	public void execute (Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		ship.fireBullet();
	}
}
