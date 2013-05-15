package asteroids.model.statements;

import java.util.Map;

import asteroids.model.*;
import asteroids.model.types.T;

public class Fire extends Action{
	public Fire(int line,int column) {
		super(line,column);
	}
	
	@Override
	public void execute (Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
		((Ship) globalExpr.get("self")).fireBullet();
	}
}
