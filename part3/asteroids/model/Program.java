package asteroids.model;

import java.util.*;
import asteroids.model.types.*;
import asteroids.model.expressions.E;
import asteroids.model.expressions.SEReference;
import asteroids.model.statements.*;

public class Program {
	// TODO: Waar tijd bijhouden? Moet tijd bijgehouden worden?
	public Program(Map<String, T> globalTypes, S executable) {
		this.globalTypes = globalTypes;
		this.executable = executable;
		this.setLine(executable.getLine());
		this.endLine = executable.getEndLine()+1;
	}
	
	public Map<String, T> getGlobalTypes() {
		return this.globalTypes;
	}
	
	private final Map<String, T> globalTypes;
	
	public S getExecutable() {
		return this.executable;
	}
	
	private S executable;
	
	public int getLine() {
		return this.line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	private int line;
	
	public int getEndLine() {
		return this.endLine;
	}
	
	private final int endLine;
	
	public Map<String, E> getGlobalExpressions() {
		return this.globalExpressions;
	}
	
	public void setGlobalExpressions(Map<String, E> globalExpr) {
		this.globalExpressions = globalExpr;
	}
	
	private Map<String, E> globalExpressions = new HashMap<String,E>();
	
	public void setShip(Ship givenShip) {
		ship = givenShip;
		globalExpressions.put("self",new SEReference(0,0,givenShip));
	}
	
	public Ship getShip() {
		return ship;
	}
	
	private Ship ship;
	
	public void setWaitingTime(double time) {
		this.waitingTime = time;
	}
	
	private double waitingTime = 0;
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		this.terminated = true;
		this.getShip().setProgram(null);
		this.setShip(null);
	}
	
	private boolean terminated = false;
	
	public void advanceProgram(double dt) {
		if (this.getLine() >= this.getEndLine()) {
			// TODO: goed?
			this.terminate();
		} else if (this.waitingTime>0.05) {
			// TODO: hoe beter?
			S currentStatement = this.getExecutable();
			while (!(currentStatement instanceof Action) && this.getLine() < this.getEndLine()) {
				currentStatement = this.getExecutable().getStatement(this.line);
				if (currentStatement == null) {
					this.setLine(this.getLine()+1);
				} else {
					// TODO: geen ship meer meegeven, maar via self handelen?
					currentStatement.execute(this.getShip(),this.getGlobalTypes(),this.getGlobalExpressions());
					this.setGlobalExpressions(currentStatement.updateGlobals(this.getGlobalExpressions()));
					this.setLine(currentStatement.updateLine());
				}
			}
			this.waitingTime = 0;
		} else {
			waitingTime += dt;
		}
	}
}