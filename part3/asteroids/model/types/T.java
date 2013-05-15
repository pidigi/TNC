package asteroids.model.types;

public abstract class T {	
	public boolean isBoolean(){
		return false;
	}
	
	public boolean isDouble(){
		return false;
	}
	
	public boolean isEntity(){
		return false;
	}
	
	public boolean equals(T type) {
		if (type == null)
			throw new NullPointerException();
//		if (this.isBoolean())
//			return type.isBoolean();
//		else if (this.isDouble())
//			return type.isDouble();
//		else
//			return type.isBoolean();
		return this.getClass().equals(type.getClass());
	}
}
