package asteroids.test;

import static org.junit.Assert.*;



import org.junit.*;

import static asteroids.Util.*;
import asteroids.model.*;

public class SpatialElementTest {
	
	private static SpatialElement standardElement;
	
	/**
	 * Set up an immutable test fixture
	 * 
	 * @post 	The variable standardElement references a new spatial element
	 * 			at the origin with zero velocity, radius of 10 and maximum velocity of 300000 
	 * 			and mass of 200.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception{
		standardElement = new SpatialElement(new Vector2D(0,0),10,new Vector2D(0,0),300000,1E10);
	}
	
	private SpatialElement element100, element1002, elementWorld, elementNoWorld;
	
	/**
	 * Set up a mutable test fixture
	 * 
	 * @post 	The variable element100 references a new ship at (100,0) with 
	 * 			a velocity of -10 in the x-direction and zero in the y-direction,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 * @post 	The variable element1002 references a new ship at (100,0) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10 and maximum velocity of 300000.
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		element100 = new SpatialElement(new Vector2D(100,0),10,new Vector2D(-10,0),300000,1E5);
		element1002 = new SpatialElement(new Vector2D(100,0),10,new Vector2D(0,0),300000,1E5);
		elementWorld = new SpatialElement();
		World newWorld = new World(1000,1000);
		newWorld.addAsSpatialElement(elementWorld);
		elementWorld.setWorld(newWorld);
		elementNoWorld = new SpatialElement();
//		World newWorld2 = new World(1000,1000);
//		newWorld.addAsSpatialElement(elementWorld);
//		elementWorld2.setWorld(newWorld);
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(2000,10000),300000,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(2000, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newElement.getMass(),EPSILON);
		assertFalse(newElement.isTerminated());
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(2000,10000),1E10);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(2000, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
		assertEquals(1E10, newElement.getMass(),EPSILON);
		assertFalse(newElement.isTerminated());
	}
	
	@Test
	public final void constructor3_NormalCase() throws Exception{
		SpatialElement newElement = new SpatialElement();
		assertEquals(0, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(0, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(1, newElement.getRadius(),EPSILON);
		assertEquals(0, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
		assertEquals(1E15, newElement.getMass(),EPSILON);
		assertFalse(newElement.isTerminated());
	}
	
	// Since all constructors have the same effect as the most extended constructor,
	// only the latter will be tested for all exceptional cases.
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNPosition() throws Exception{
		new SpatialElement(new Vector2D(Double.NaN,100),15,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NullPosition() throws Exception{
		new SpatialElement(null,15,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNRadius() throws Exception{
		new SpatialElement(new Vector2D(50,100),Double.NaN,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowRadius() throws Exception{
		new SpatialElement(new Vector2D(50,100),-5,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighRadius() throws Exception{
		new SpatialElement(new Vector2D(50,100),Double.POSITIVE_INFINITY,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test
	public final void constructor_NaNMaxSpeed() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(2000,10000),Double.NaN,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(2000, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooLowMaxSpeed() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(2000,10000),-50,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(2000, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooHighMaxSpeed() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(2000,10000),400000,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(2000, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_NaNVelocity() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(Double.NaN,10000),300000,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(0, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_NullVelocity() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,null,300000,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(0, newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooHighVelocity() throws Exception{
		SpatialElement newElement = new SpatialElement(new Vector2D(50,100),15,new Vector2D(300000,300000),300000,1E5);
		assertEquals(50, newElement.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newElement.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newElement.getRadius(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newElement.getVelocity().getXComponent(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newElement.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newElement.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void terminate_NormalCase() {
		World originalWorld = elementWorld.getWorld();
		elementWorld.terminate();
		assertTrue(elementWorld.isTerminated());
		assertTrue(elementWorld.getWorld() == null);
		assertFalse(originalWorld.hasAsSpatialElement(elementWorld));
	}
	
	// In hoeverre moe ge dit testen, want canHaveAsSpatialElement is van world???
	@Test
	public final void canHaveAsWorld_NormalCase() {
		World newWorld = new World(1000,1000);
		assertTrue(elementWorld.canHaveAsWorld(newWorld));
	}
	
	@Test
	public final void canHaveAsWorld_NullWorld() {
		World world = null;
		assertTrue(elementWorld.canHaveAsWorld(world));
	}
	
	@Test
	public final void hasProperWorld_NormalCase() {
		assertTrue(elementWorld.hasProperWorld());
	}
	
	@Test
	public final void hasProperWorld_FalseCase() {
		elementWorld.getWorld().removeAsSpatialElement(elementWorld);
		assertTrue(elementWorld.hasProperWorld());
	}
	
	// Hoe valse case testen van hasProperWorld, moeilijk om daar aan te graken?
	
	@Test
	public final void setWorld_NormalCase() throws Exception {
		World newWorld = new World(2000,2000);
		newWorld.addAsSpatialElement(elementNoWorld);
		elementNoWorld.setWorld(newWorld);
		assertTrue(elementNoWorld.getWorld() == newWorld);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setWorld_NotAdded() throws Exception {
		World newWorld = new World(2000,2000);
		elementNoWorld.setWorld(newWorld);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setWorld_NullWorld() throws Exception {
		elementWorld.setWorld(null);
	}
	
	// Checkers are tested because they are public methods, 
	// although you could say that they already have been tested through 
	// the tests for the constructor.
	
	@Test
	public final void isValidPosition_TrueCase(){
		assertTrue(element100.isValidPosition(new Vector2D(5,10)));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNull(){
		assertFalse(element100.isValidPosition(null));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNaN(){
		assertFalse(element100.isValidPosition(new Vector2D(Double.NaN,10)));
	}	
	
	@Test
	public final void move_NormalCase() throws Exception{
		element100.move(1);
		assertEquals(90,element100.getPosition().getXComponent(),EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NegativeTime() throws Exception{
		element100.move(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_InfTime() throws Exception{
		element100.move(Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NaNTime() throws Exception{
		element100.move(Double.NaN);
	}
	
	@Test
	public final void getDistanceBetween_NormalCase() throws Exception{
		double newDistance = element100.getDistanceBetween(standardElement);
		assertEquals(80,newDistance,EPSILON);
	}
	
	@Test
	public final void getDistanceBetween_SameElement() throws Exception{
		double newDistance = standardElement.getDistanceBetween(standardElement);
		assertEquals(0,newDistance,EPSILON);
	}
	
	@Test
	public final void getDistanceBetween_CompleteOverlap() throws Exception{
		double newDistance = element100.getDistanceBetween(element1002);
		assertEquals(-20,newDistance,EPSILON);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getDistanceBetween_NullElement() throws Exception{
		element100.getDistanceBetween(null);
	}
	
	@Test
	public final void overlap_TrueCase() throws Exception{
		element100.overlap(element1002);
	}
	
	@Test
	public final void overlap_FalseCase() throws Exception{
		element100.overlap(standardElement);
	}
	
	@Test
	public final void overlap_TrueCaseSameElement() throws Exception{
		element100.overlap(element100);
	}
	
	@Test(expected = NullPointerException.class)
	public final void overlap_NullCase() throws Exception{
		element100.overlap(null);
	}
	
	@Test
	public final void getTimeToCollision_NormalCase() throws Exception{
		double newCollisionTime = element100.getTimeToCollision(standardElement);
		assertEquals(8,newCollisionTime,EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_NoCollisionCase() throws Exception{
		double newCollisionTime = element1002.getTimeToCollision(standardElement);
		assertTrue(newCollisionTime == Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getTimeToCollision_NullCase()throws Exception{
		element1002.getTimeToCollision(null);
	}
	
	@Test
	public final void getCollisionPosition_NormalCase(){
		Vector2D newCollisionPosition = element100.getCollisionPosition(standardElement);
		assertEquals(10,newCollisionPosition.getXComponent(),EPSILON);
		assertEquals(0,newCollisionPosition.getYComponent(),EPSILON);
	}
	
	@Test
	public final void getCollisionPosition_NoCollision(){
		Vector2D newCollisionPosition = element1002.getCollisionPosition(standardElement);
		assertTrue(newCollisionPosition == null);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getCollisionPosition_NullCase(){
		element1002.getCollisionPosition(null);
	}
	
	@Test
	public final void isValidRadius_TrueCase(){
		assertTrue(element100.isValidRadius(20));
	}
	
	@Test
	public final void isValidRadius_FalseCaseNaN(){
		assertFalse(element100.isValidRadius(Double.NaN));
	}
	
	@Test
	public final void isValidRadius_FalseCaseTooLow(){
		assertFalse(element100.isValidRadius(-0.01));
	}
	
	@Test
	public final void isValidRadius_FalseCaseInf(){
		assertFalse(element100.isValidRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidTime_TrueCase(){
		assertTrue(Ship.isValidTime(8.0));
	}
	
	@Test
	public final void isValidTime_FalseCaseNaN(){
		assertFalse(Ship.isValidTime(Double.NaN));
	}
	
	@Test
	public final void isValidTime_FalseCaseNegative(){
		assertFalse(Ship.isValidTime(-8.0));
	}
}

