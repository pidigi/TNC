package asteroids.model;
import be.kuleuven.cs.som.annotate.*;
import static asteroids.Util.*;

/**
 * A value class of geometric vectors of length 2.
 * 
 * 
 * @version  3.0
 * @author   Frederik Van Eeghem, Pieter Lietaert
 */

public class Vector2D {
	
	/**
	 * Initialize this new vector with given x and y component.
	 *  
	 * @param 	x
	 * 			The x-component for this new two dimensional vector.
	 * @param 	y
	 * 			The y-component for this new two dimensional vector.
	 * @post	The x-component of this new two dimensional vector equals the given x.
	 * 			| (new this).getXComponent() == x
	 * @post	The y-component of this new two dimensional vector equals the given y.
	 * 			| (new this).getYComponent() == y
	 */
	public Vector2D(double x, double y){
		this.xComponent = x;
		this.yComponent = y;
	}
	
	/**
	 * Calculate the vector 2-norm of this two dimensional vector.
	 * 
	 * @return 	The 2-norm of this vector.
	 * 			| let
	 * 			| 	x = getXComponent()
	 * 			|	y = getYComponent()
	 * 			| in
	 * 			| result == Math.sqrt(x*x + y*y);
	 */
	public double getNorm(){
		return Math.sqrt(Math.pow(getXComponent(), 2) 
					+ Math.pow(getYComponent(), 2));
	}
	
	/**
	 * Calculate the elementwise difference of this vector and the
	 * given two dimensional vector.
	 * 
	 * @param 	otherVector
	 * 			The vector to be subtracted from this vector.
	 * @pre		The given vector is effective.
	 * 			| otherVector != null
	 * @return	The elementwise difference of this vector and the given vector.
	 * 			| result.getXComponent() == this.getXComponent() - othership.getXComponent()
	 * 			| result.getYComponent() == this.getYComponent() - othership.getYComponent()
	 */
	public Vector2D subtract(Vector2D otherVector){
		double newX = this.getXComponent() - otherVector.getXComponent();
		double newY = this.getYComponent() - otherVector.getYComponent();
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Calculate the elementwise sum of this vector and the
	 * given two dimensional vector.
	 * 
	 * @param 	otherVector
	 * 			The vector to be added to this vector.
	 * @pre		The given vector is effective.
	 * 			| otherVector != null
	 * @return	The elementwise sum of this vector and the given vector.
	 * 			| result.getXComponent() == this.getXComponent() + othership.getXComponent()
	 * 			| result.getYComponent() == this.getYComponent() + othership.getYComponent()
	 */
	public Vector2D add(Vector2D otherVector){
		double newX = this.getXComponent() + otherVector.getXComponent();
		double newY = this.getYComponent() + otherVector.getYComponent();
		return new Vector2D(newX, newY);
	}
	
		
	/**
	 * Calculate the dot product of this vector and the given vector.
	 * 
	 * @param	otherVector
	 * 			The vector to be multiplied with this vector.
	 * @pre		The given vector is effective.
	 * 			| otherVector != null
	 * @return	The sum of the elementwise product of this vector and the given vector.
	 * 			| result == (this.getXComponent()*otherVector.getXComponent()
	 *			|	+ this.getYComponent()*otherVector.getYComponent())
	 */
	public double getDotProduct(Vector2D otherVector){
		return (this.getXComponent()*otherVector.getXComponent()
					+ this.getYComponent()*otherVector.getYComponent());
	}
	
	
	/**
	 * Calculate the product of this vector and the given number.
	 * 
	 * @param 	factor
	 * 			Number to be multiplied with this vector.
	 * @return	The product of this vector and the given number.
	 * 			| result.getXComponent() == this.getXComponent()*factor
	 * 			| result.getYComponent() == this.getYComponent()*factor
	 */
	public Vector2D multiply(double factor){
		double newX = this.getXComponent()*factor;
		double newY = this.getYComponent()*factor;
		return new Vector2D(newX, newY);
	}
	
	
	/**
	 * Return the vector with norm one and the same direction as this vector.
	 * 
	 * @return	The vector with norm one and the same direction as this vector.
	 * 			| let 
	 * 			|	currentDirection = Math.atan2(this.getYComponent(), this.getXComponent())
	 * 			| in
	 * 			| result.getXComponent() == Math.cos(currentDirection)
	 * 			| result.getYComponent() == Math.sin(currentDirection)
	 */
	public Vector2D getDirection(){
		double currentDirection = Math.atan2(this.getYComponent(),
				this.getXComponent());
		return new Vector2D(Math.cos(currentDirection),Math.sin(currentDirection));
	}

	/**
	 * Check whether this vector and the given two dimensional vector are equal.
	 * 
	 * @param 	otherVector
	 * 			The other two dimensional vector to check equality with.
	 * @return	True if and only if the given vector is effective,
	 * 			the x-component of both vectors are approximately equal
	 * 			and the y-component of both vectors are approximately equal.
	 * 			| result == (otherVector != null
	 *			|  && fuzzyEquals(this.getXComponent(),otherVector.getXComponent())
	 *			|  && fuzzyEquals(this.getYComponent(),otherVector.getYComponent()))
	 */
	public boolean equals(Vector2D otherVector){
		return (otherVector != null
			&& fuzzyEquals(this.getXComponent(),otherVector.getXComponent())
			&& fuzzyEquals(this.getYComponent(),otherVector.getYComponent()));
	}
	
	/**
	 * Check whether one of the components is NaN
	 * 
	 * @return	True if and only if at least one of the components is NaN.
	 * 			| result == (Double.isNaN(this.getXComponent()) 
	 * 			|		|| Double.isNaN(this.getYComponent()))
	 */
	public boolean containsNaN(){
		return (Double.isNaN(this.getXComponent()) || Double.isNaN(this.getYComponent()));
	}
	
	
	/**
	 * Return the hash code for this money amount.
	 */
	@Override
	public int hashCode(){
		return (Double.valueOf(getXComponent()).hashCode()
					+ Double.valueOf(getYComponent()).hashCode());
	}
	
	/**
	 * Return a textual representation of this two dimensional vector.
	 * 
	 * @return	A string consisting of the textual representation
	 * 			of this two dimensional vector, given as the x-component and 
	 * 			the y-component, separated by a comma and enclosed
	 * 			in brackets.
	 * 			| result.equals("(" + Double.valueOf(getXComponent()).toString() + 
	 *			|	"," + Double.valueOf(getYComponent()).toString() + ")")
	 */
	@Override
	public String toString(){
		return "(" + Double.valueOf(getXComponent()).toString() + "," +
		Double.valueOf(getYComponent()).toString() + ")";
	}
	
	/**
	 * Get the x-component of this two dimensional vector.
	 */
	@Basic @Immutable
	public double getXComponent(){
		return xComponent;
	}
	
	/**
	 * The x-component of the two dimensional vector
	 */
	private final double xComponent;
	
	
	/**
	 * Get the y-component of this two dimensional vector.
	 */
	@Basic @Immutable
	public double getYComponent(){
		return yComponent;
	}
	
	/**
	 * The y-component of the two dimensional vector
	 */
	private final double yComponent;
}
