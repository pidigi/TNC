package asteroids.model.statements;

import java.util.Map;

import asteroids.model.types.T;

public class Skip extends Action{
	public Skip(int line, int column) {
		super(line, column);
	}

	@Override
	public void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
	}
}