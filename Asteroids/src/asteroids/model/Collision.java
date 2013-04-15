package asteroids.model;

public class Collision implements Comparable<Collision>{

	public Collision(SpatialElement element1, SpatialElement element2){
		this.element1 = element1;
		this.element2 = element2;
		collisionTime = element1.getTimeToCollision(element2);
	}
	
		
	public boolean contains(SpatialElement otherElement){
		return (getElement1() == otherElement || getElement2() == otherElement);
	}
	
	public SpatialElement getElement1(){
		return element1;
	}
	
	private final SpatialElement element1;
	
	public SpatialElement getElement2(){
		return element2;
	}
	
	private final SpatialElement element2;
	
	public double getCollisionTime(){
		return collisionTime;
	}
	
	private double collisionTime;

	@Override
	public int compareTo(Collision otherCollision) {
		if(this.getCollisionTime() > otherCollision.getCollisionTime())
			return 1;
		else if(this.getCollisionTime() == otherCollision.getCollisionTime())
			return 0;
		else
			return -1;
	}
	
}
