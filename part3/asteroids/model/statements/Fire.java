package asteroids.model.statements;

import java.util.Map;

import asteroids.model.*;
import asteroids.model.types.T;

public class Fire extends Action{
	public Fire(int line,int column) {
		super(line,column);
	}
	
	@Override
	public void execute (Ship ship, Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
		ship.fireBullet();
	}
}
