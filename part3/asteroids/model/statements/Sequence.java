package asteroids.model.statements;

import java.util.List;
import java.util.Map;
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
	public S getStatement(int line,int column) {
		if (this.getList().isEmpty() || this.getLine() > line) {
				return null;
		}
		for (S stat:this.getList()) {
			S statement = stat.getStatement(line,column);
			if (statement != null) {
				return statement;
			}
		}
		return null;
	}
	
	public boolean containsAction() {
		if (this.getList().isEmpty()) {
			return false;
		}
		for (S stat:this.getList()) {
			boolean contains = stat.containsAction();
			if (contains) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getEndLine() {
		if (this.getList().isEmpty()) {
			return this.getLine();
		}
		return this.getList().get(this.getList().size()-1).getEndLine();
	}
	
	@Override
	public int getEndColumn() {
		if (this.getList().isEmpty()) {
			return this.getColumn();
		}
		return this.getList().get(this.getList().size()-1).getEndColumn();
	}
	
	@Override
	public void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr) {
	}
	
	public boolean typeCheck(Map<String, T> globalTypes) 
			throws NullPointerException{
		if (this.getList().isEmpty()) {
			return true;
		}
		for (S stat:this.getList()) {
			boolean check = stat.typeCheck(globalTypes);
			if (!check) {
				return false;
			}
		}
		return true;
	}
}