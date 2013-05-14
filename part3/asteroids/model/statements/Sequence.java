package asteroids.model.statements;

import java.util.List;
import java.util.Map;
import asteroids.model.Ship;
import asteroids.model.expressions.E;
import asteroids.model.types.T;

public class Sequence extends S{
	public Sequence(int line,int column, List<S> list) {
		super(line,column);
		this.list = list;
	}
	
	public List<S> getList() {
		return this.list;
	}
	
	private final List<S> list;
	
	@Override	
	public S getStatement(int line) {
		if (this.getList().isEmpty()) {
				return null;
		}
		for (S stat:this.getList()) {
			S statement = stat.getStatement(line);
			if (statement != null) {
				return statement;
			}
		}
		return null;
	}
	
	@Override
	public int getEndLine() {
		if (this.getList().isEmpty()) {
			return this.getLine();
		}
		return this.getList().get(this.getList().size()-1).getEndLine();
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
	}
	
	@Override
	public int updateLine() {
		return this.getLine();
	}
}