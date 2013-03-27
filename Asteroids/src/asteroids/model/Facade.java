package asteroids.model;
import asteroids.*;


/**
 * This class implements the interface IFacade to use the 
 * class Ship to control ships in the GUI of Asteroids.
 * 
 * @version	1.1
 * @author 	Frederik Van Eeghem, Pieter Lietaert
 */

public class Facade implements asteroids.IFacade{
	public IShip createShip(){
		try{
			Ship newShip = new Ship(new Vector2D(0,0), 0, 10, new Vector2D(0,0));
			IShip newShipCast = (IShip)newShip;
			return newShipCast;
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}
	
	public IShip createShip(double x, double y, double xVelocity, double yVelocity, double radius, double angle){
		try{
			return new Ship(new Vector2D(x,y), angle, radius, new Vector2D(xVelocity,yVelocity));
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}
	
	public double getX(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getPosition().getXComponent();
	}
	
	/**
	 * Return the y-coordinate of <code>ship</code>.
	 */
	public double getY(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getPosition().getYComponent();
	}

	/**
	 * Return the velocity of <code>ship</code> along the X-axis.
	 */
	public double getXVelocity(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getVelocity().getXComponent();
	}

	/**
	 * Return the velocity of <code>ship</code> along the Y-axis.
	 */
	public double getYVelocity(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getVelocity().getYComponent();
	}
	
	/**
	 * Return the radius of <code>ship</code>.
	 */
	public double getRadius(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getRadius();
	}

	/**
	 * Return the direction of <code>ship</code> (in radians).
	 */
	public double getDirection(IShip ship){
		Ship realShip = (Ship)ship;
		return realShip.getAngle();
	}

	/**
	 * Update <code>ship</code>'s position, assuming it moves <code>dt</code>
	 * seconds at its current velocity.
	 */
	public void move(IShip ship, double dt){
		try{
			Ship realShip = (Ship)ship;
			realShip.move(dt);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}
	

	/**
	 * Update <code>ship</code>'s velocity based on its current velocity, its
	 * direction and the given <code>amount</code>.
	 */
	public void thrust(IShip ship, double amount){
		Ship realShip = (Ship)ship;
		realShip.thrust(amount);
	}

	/**
	 * Update the direction of <code>ship</code> by adding <code>angle</code>
	 * (in radians) to its current direction. <code>angle</code> may be
	 * negative.
	 */
	public void turn(IShip ship, double angle){
		Ship realShip = (Ship)ship;
		realShip.turn(angle);
	}

	/**
	 * Return the distance between <code>ship1</code> and <code>ship2</code>.
	 * 
	 * The absolute value of the result of this method is the minimum distance
	 * either ship should move such that both ships are adjacent. Note that the
	 * result must be negative if the ships overlap. The distance between a ship
	 * and itself is 0.
	 */
	public double getDistanceBetween(IShip ship1, IShip ship2){
		try{
			Ship realShip1 = (Ship)ship1;
			Ship realShip2 = (Ship)ship2;
			return realShip1.getDistanceBetween(realShip2);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	/**
	 * Check whether <code>ship1</code> and <code>ship2</code> overlap. A ship
	 * always overlaps with itself.
	 */
	public boolean overlap(IShip ship1, IShip ship2){
		try{
			Ship realShip1 = (Ship)ship1;
			Ship realShip2 = (Ship)ship2;
			return realShip1.overlap(realShip2);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the number of seconds until the first collision between
	 * <code>ship1</code> and <code>ship2</code>, or Double.POSITIVE_INFINITY if
	 * they never collide. A ship never collides with itself.
	 */
	public double getTimeToCollision(IShip ship1, IShip ship2){
		try{
			Ship realShip1 = (Ship)ship1;
			Ship realShip2 = (Ship)ship2;
			return realShip1.getTimeToCollision(realShip2);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the first position where <code>ship1</code> and <code>ship2</code>
	 * collide, or <code>null</code> if they never collide. A ship never
	 * collides with itself.
	 * 
	 * The result of this method is either null or an array of length 2, where
	 * the element at index 0 represents the x-coordinate and the element at
	 * index 1 represents the y-coordinate.
	 */
	public double[] getCollisionPosition(IShip ship1, IShip ship2){
		try{
			Ship realShip1 = (Ship)ship1;
			Ship realShip2 = (Ship)ship2;
			Vector2D collPos = realShip1.getCollisionPosition(realShip2);
			if (collPos == null){
				return null;
			}
			else{
				double[] collCoor = {collPos.getXComponent(),collPos.getYComponent()};
				return collCoor;
			}
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}
}
