package asteroids.model.statements;

import java.util.Map;
import asteroids.model.types.T;

public abstract class S {
	public S(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	private final int column;
	
	public int getLine() {
		return this.line;
	}
	
	public int getEndLine() {
		return this.line;
	}
	
	private final int line;
	
	public boolean isAction() {
		return false;
	}
	
	public boolean containsAction() {
		return isAction();
	}
	
	public S getStatement(int line) {
		if (line == this.getLine()) {
			return this;
		}
		return null;
	}
	
	public Map<String, Object> updateGlobals(Map<String, Object> currentGlobals) {
		return currentGlobals;
	}
	
	public int updateLine() {
		return this.getLine() + 1;
	}
	
	public abstract void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr);
	
	public boolean typeCheck(Map<String, T> globalTypes) {
		return true;
	}
}