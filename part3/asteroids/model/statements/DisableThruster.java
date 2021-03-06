package asteroids.model.statements;

import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.types.T;

public class DisableThruster extends Action{
	public DisableThruster(int line, int column) {
		super(line, column);
	}
	
	@Override
	public void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr) 
			throws NullPointerException{
		((Ship) globalExpr.get("self")).setThrusterActive(false);
	}
}
