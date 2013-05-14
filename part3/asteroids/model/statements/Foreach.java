package asteroids.model.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.SpatialElement;
import asteroids.model.expressions.E;
import asteroids.model.expressions.SEReference;
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
	public S getStatement(int line) {
		if (line == body.getEndLine()) {
			return this;
		}
		if (line == this.getLine()) {
			return this;
		}
		return body.getStatement(line);
	}
	
	@Override
	public Map<String, E> updateGlobals(Map<String, E> currentGlobals) {
		if (this.getEnded()) {
			currentGlobals.remove(this.getName());
			return currentGlobals;
		}
		SpatialElement element = list.get(this.getLooped());
		SEReference ref = new SEReference(this.getLine(),this.getColumn(),element);
		currentGlobals.put(this.getName(),ref);
		return currentGlobals;
	}
	
	@Override
	public int updateLine() {
		if (this.getEnded()) {
			return body.getEndLine() + 1;
		} else {
			return this.getLine() + 1;
		}
	}
	
	@Override
	public void execute(Ship ship, Map<String, T> globalTypes,  Map<String, E> globalExpr) {
		if (looped == -1) {
			this.setEnded(false);
			looped += 1;
			switch (type) {
				case SHIP:
					for (Ship shipje:ship.getWorld().getShips()) {
						if(shipje != ship) 
							list.add(shipje);
					}
					break;
				case ASTEROID:
					for (Asteroid asteroid:ship.getWorld().getAsteroids()) {
						list.add(asteroid);
					}
					break;
				case BULLET:
					for (Bullet bullet:ship.getWorld().getBullets()) {
						list.add(bullet);
					}
					break;
				case ANY:
					for (Asteroid asteroid:ship.getWorld().getAsteroids()) {
						list.add(asteroid);
					}
					for (Bullet bullet:ship.getWorld().getBullets()) {
						list.add(bullet);
					}
					for (Ship shipje:ship.getWorld().getShips()) {
						list.add(shipje);
					}
					break;
			}
			if (list.isEmpty()) {
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
}
