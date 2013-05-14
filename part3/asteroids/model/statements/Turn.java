package asteroids.model.statements;

import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.expressions.E;
import asteroids.model.types.DoubleT;
import asteroids.model.types.T;

public class Turn extends Action{
	public Turn(int line, int column, E angle) {
		super(line, column);
		this.angle = angle;
	}
	
	public E getAngle() {
		return this.angle;
	}
	
	private E angle;
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		if (!(angle.getType(globalTypes) instanceof DoubleT)) {
			throw new IllegalArgumentException();
		}
		String angleEval = this.getAngle().evaluate(globalTypes,globalExpr);
		ship.turn(Double.valueOf(angleEval));
	}
}