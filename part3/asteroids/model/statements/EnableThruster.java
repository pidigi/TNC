package asteroids.model.statements;

import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.types.T;

public class EnableThruster extends Action{
	public EnableThruster(int line, int column) {
		super(line, column);
	}

	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
		ship.setThrusterActive(true);
	}
}
