package asteroids.model;

import java.util.*;
import asteroids.model.types.*;
import asteroids.model.statements.*;

public class Program {
	public Program(Map<String, T> globalTypes, S executable) {
		this.globalTypes = globalTypes;
		this.executable = executable;
		this.setLine(executable.getLine());
		this.endLine = executable.getEndLine()+1;
		this.setColumn(0);
	}
	
	public Map<String, T> getGlobalTypes() {
		return this.globalTypes;
	}
	
	private final Map<String, T> globalTypes;
	
	public S getExecutable() {
		return this.executable;
	}
	
	private final S executable;
	
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
	
	public int getColumn(){
		return this.column;
	}
	
	public void setColumn(int column){
		this.column = column;
	}
	
	private int column;
	
	public Map<String, Object> getGlobalExpressions() {
		return this.globalExpressions;
	}
	
	public void setGlobalExpressions(Map<String, Object> globalExpr) {
		this.globalExpressions = globalExpr;
	}
	
	private Map<String, Object> globalExpressions = new HashMap<String,Object>();
	
	public void setShip(Ship ship) throws IllegalArgumentException{
		if(ship != null && ship.getProgram() != this)
			throw new IllegalArgumentException();
		if(ship == null && getShip() != null 
				&& getShip().getProgram() == this)
			throw new IllegalArgumentException();
		this.ship = ship;
		globalExpressions.put("self", ship);
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
	
	public void terminate() throws IllegalArgumentException{
		if(getShip() != null){
			this.getShip().setProgram(null);
		}
		this.terminated = true;
	}
	
	private boolean terminated = false;
	
	public boolean getBroken() {
		return this.broken;
	}
	
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
	
	private boolean broken;
	
	public void advanceProgram(double dt) throws IllegalArgumentException, NullPointerException{
		this.waitingTime += dt;
		while(!this.isTerminated() && !getBroken() && this.waitingTime >= 0.2){
				try {
					S currentStatement = null;
					while (!(currentStatement instanceof Action) && this.getLine() < this.getEndLine()){
						currentStatement = this.getExecutable().getStatement(this.getLine(),this.getColumn());
						if (currentStatement == null) { // At end of line.
							this.setLine(this.getLine()+1);
							this.setColumn(0);
						} else {
							currentStatement.execute(this.getGlobalTypes(),this.getGlobalExpressions());
							this.setGlobalExpressions(currentStatement.updateGlobals(this.getGlobalExpressions()));
							this.setLine(currentStatement.updateLine());
							this.setColumn(currentStatement.updateColumn());
						}
					}
				} catch(RuntimeException exc) {
					System.out.println(exc);
					this.setBroken(true);
				}
			this.waitingTime -= 0.2;
		}
	}
	
	public boolean typeCheck() throws NullPointerException {
		return this.getExecutable().typeCheck(this.getGlobalTypes());
	}
}