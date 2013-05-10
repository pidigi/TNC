package asteroids.model.types;

public abstract class T {
	
	public boolean isBoolean(){
		return this instanceof BooleanT;
	}
	
	public boolean isDouble(){
		return this instanceof DoubleT;
	}
	
	public boolean isEntity(){
		return this instanceof EntityT;
	}
	
}
