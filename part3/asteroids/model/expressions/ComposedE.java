package asteroids.model.expressions;

public abstract class ComposedE extends E {

	public ComposedE(int line, int column, E... e) {
		super(line, column);
		// TODO niet zinvol om hier lijst van argumenten toe te laten?
	}

}
