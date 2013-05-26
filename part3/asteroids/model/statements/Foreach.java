package asteroids.model.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.SpatialElement;
import asteroids.model.programs.parsing.ProgramFactory.ForeachType;
import asteroids.model.types.T;

public class Foreach extends S{
	public Foreach(int line, int column, ForeachType type, String variableName, S body) {
		super(line, column);
		this.type = type;
		this.name = variableName;
		this.body = body;
	}
	
	public ForeachType getType() {
		return this.type;
	}
	
	private final ForeachType type;
	
	public String getName() {
		return this.name;
	}
	
	private final String name;
	
	public S getBody() {
		return this.body;
	}
	
	private final S body;
	
	public int getLooped() {
		return this.looped;
	}
	
	public void setLooped(int looped) {
		this.looped = looped;
	}
	
	private int looped = -1;
	
	public boolean getEnded() {
		return this.ended;
	}
	
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	private boolean ended = false;
	
	@Override
	public S getStatement(int line,int column) {
		if (line == this.getLine() && column <= this.getColumn()) {
			return this;
		}
		S bodyStat = this.getBody().getStatement(line,column);
		if (bodyStat != null) {
			return bodyStat;
		}
		if (line == this.getBody().getEndLine() && column <= this.getBody().getEndColumn()) {
			return this;
		}
		return null;
	}
	
	@Override
	public Map<String, Object> updateGlobals(Map<String, Object> currentGlobals) {
		if (this.getEnded()) {
			currentGlobals.remove(this.getName());
			return currentGlobals;
		}
		currentGlobals.put(this.getName(),this.getList().get(this.getLooped()));
		return currentGlobals;
	}
	
	@Override
	public int updateLine() {
		if (this.getEnded()) {
			return this.getBody().getEndLine();
		} else {
			return this.getLine();
		}
	}
	
	@Override
	public int updateColumn() {
		if (this.getEnded()) {
			return this.getBody().getEndColumn() + 1;
		} else {
			return this.getColumn() + 1;
		}
	}
	
	@Override
	public void execute(Map<String, T> globalTypes,  Map<String, Object> globalExpr) 
			throws IllegalArgumentException, NullPointerException{
		if (getBody().containsAction())
			throw new IllegalArgumentException("Body of for loop contains action statement.");
		
		Ship ship = ((Ship) globalExpr.get("self"));
		if (this.getLooped() == -1) {
			this.setEnded(false);
			this.setLooped(this.getLooped()+1);
			switch (this.getType()) {
				case SHIP:
					for (Ship shipje:ship.getWorld().getShips()) {
						this.getList().add(shipje);
					}
					break;
				case ASTEROID:
					for (Asteroid asteroid:ship.getWorld().getAsteroids()) {
						this.getList().add(asteroid);
					}
					break;
				case BULLET:
					for (Bullet bullet:ship.getWorld().getBullets()) {
						this.getList().add(bullet);
					}
					break;
				case ANY:
					for (Asteroid asteroid:ship.getWorld().getAsteroids()) {
						this.getList().add(asteroid);
					}
					for (Bullet bullet:ship.getWorld().getBullets()) {
						this.getList().add(bullet);
					}
					for (Ship shipje:ship.getWorld().getShips()) {
						this.getList().add(shipje);
					}
					break;
			}
			if (this.getList().isEmpty()) {
				this.setLooped(-1);
				this.setEnded(true);
			}
		} else if (this.getLooped() + 1 >= this.getList().size()) {
			this.setLooped(-1);
			this.setEnded(true);
			this.getList().clear();
		} else {
			this.setLooped(this.getLooped()+1);
		}
	}
	
	public List<SpatialElement> getList() {
		return this.list;
	}
	
	private List<SpatialElement> list = new ArrayList<SpatialElement>();
	
	public boolean typeCheck(Map<String, T> globalTypes) 
			throws NullPointerException{
		boolean bodyTypeCheck = this.getBody().typeCheck(globalTypes);
		boolean bodyActionCheck = this.getBody().containsAction();
		if (!globalTypes.containsKey(this.getName()))
			return false;
		boolean variableCheck = globalTypes.get(this.getName()).isEntity();
		return bodyTypeCheck && !bodyActionCheck && variableCheck;
	}
}
