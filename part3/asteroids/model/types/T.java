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
	
	public boolean equals(T type) {
		if (type == null)
			throw new NullPointerException();
		return this.getClass().equals(type.getClass());
	}
}
