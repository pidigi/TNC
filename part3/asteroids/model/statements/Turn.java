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
		double angleEval = (Double) this.getAngle().evaluate(globalTypes,globalExpr);
		double resultingAngle = angleEval + ship.getAngle();
		double resultingAngleIn =  resultingAngle % (2*Math.PI);
		double turningAngle = resultingAngleIn - ship.getAngle();
		ship.turn(turningAngle);
	}
	
	@Override
	public boolean typeCheck(Map<String, T> globalTypes) {
		return angle.getType(globalTypes).isDouble() && angle.typeCheck(globalTypes);
	}
}