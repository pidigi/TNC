package asteroids.test;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

import static asteroids.Util.*;
import asteroids.model.*;
import asteroids.model.statements.*;
import asteroids.model.types.T;

public class ShipTest {

	private static Ship standardShip;
	
	/**
	 * Set up an immutable test fixture
	 * 
	 * @post 	The variable standard ship references a new ship at the origin with zero velocity,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception{
		standardShip = new Ship(new Vector2D(0,0),0,10,new Vector2D(0,0),300000, 1E10);
	}
	
	//private static SpatialElement element100, ship100PiD4, elementWorld, elementNoWorld, elementVelocity;
	
	private static Ship ship100, ship100PiD4, shipWorld, standardShipMutable,
		shipNoWorld, shipVelocity, shipWorldBounce;
	private static World newWorld;
	private static Program standardProgram, standardProgram2;
	
	/**
	 * Set up a mutable test fixture
	 * 
	 * @post 	The variable ship100 references a new ship at (100,0) with 
	 * 			a velocity of -10 in the x-direction and zero in the y-direction,
	 * 			an angle of 0, radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post 	The variable ship100PiD4 references a new ship at (100,0) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post 	The variable shipWorld references a new ship at (100,100) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post	The variable newWorld references a new World with dimensions (1000,1000)
	 * @effect	newWorld.addAsSpatialElement(shipWorld)
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		ship100 = new Ship(new Vector2D(100,0),0,10,new Vector2D(-10,0),300000,1E5);
		ship100PiD4 = new Ship(new Vector2D(100,0),Math.PI/4,10,new Vector2D(0,0),300000,1E5);
		shipWorld = new Ship(new Vector2D(100,100),Math.PI/4,10,new Vector2D(0,0),300000,1E5);
		shipWorldBounce = new Ship(new Vector2D(120,100),Math.PI/4,10,new Vector2D(-1,0),300000,1E5);
		standardShipMutable = new Ship(new Vector2D(0,0),0,10,new Vector2D(0,0),300000, 1E10);
		newWorld = new World(1000,1000);
		newWorld.addAsSpatialElement(shipWorld);
		newWorld.addAsSpatialElement(shipWorldBounce);
		shipNoWorld = new Ship(new Vector2D(100,100),0,10,new Vector2D(0,0),300000,1E5);
		shipVelocity = new Ship(new Vector2D(0,0),0,10,new Vector2D(10,10),300000,1E5);
		standardProgram = new Program(new HashMap<String,T>(), (S) new Fire(0,0));
		standardProgram2 = new Program(new HashMap<String,T>(), (S) new Fire(1,1));
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor3_NormalCase() throws Exception{
		Ship newShip = new Ship();
		assertEquals(0, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(0, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(0, newShip.getAngle(),EPSILON);
		assertEquals(10, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	// Since all constructors have the same effect as the most extended constructor,
	// only the latter will be tested for all exceptional cases.

	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.NaN,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,-5,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.POSITIVE_INFINITY,new Vector2D(2000,10000),300000,1E5);
	}
			
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNPosition() throws Exception{
		new Ship(new Vector2D(Double.NaN,100),Math.PI/2,15,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NullPosition() throws Exception{
		new Ship(null,Math.PI/2,15,new Vector2D(2000,10000),300000);
	}
	
	@Test
	public final void constructor_NaNMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),Double.NaN,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooLowMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),-50,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooHighMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),400000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_NaNVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(Double.NaN,10000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_NullVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,null,300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooHighVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(300000,300000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowMass() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(300000,300000),300000,-10);
	}
	
	// Checkers are tested because they are public methods, 
	// although you could say that they already have been tested through 
	// the tests for the constructor.
	
	@Test
	public final void isValidAngle_TrueCase() {
		assertTrue(ship100.isValidAngle(Math.PI));
	}
	
	@Test
	public final void isValidAngle_FalseCaseNaN(){
		assertFalse(ship100.isValidAngle(Double.NaN));
	}
	
	@Test
	public final void isValidAngle_FalseCaseOutOfRange1(){
		assertFalse(ship100.isValidAngle(-Math.PI));
	}
	
	@Test
	public final void isValidAngle_FalseCaseOutOfRange2(){
		assertFalse(ship100.isValidAngle(2*Math.PI));
	}
		
	@Test
	public final void turn_NormalCase(){
		ship100.turn(Math.PI/4);
		assertEquals(Math.PI/4,ship100.getAngle(),EPSILON);
	}
	
	@Test
	public final void thrust_NormalCase(){
		ship100PiD4.thrust(100);
		assertEquals(100*Math.cos(Math.PI/4),ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(100*Math.sin(Math.PI/4),ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	@Test
	public final void thrust_NegativeAcceleration(){
		ship100PiD4.thrust(-100);
		assertEquals(0,ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(0,ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	@Test
	public final void thrust_NaNAcceleration(){
		ship100PiD4.thrust(Double.NaN);
		assertEquals(0,ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(0,ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	public final void setThrusterActive_SingleCase(){
		standardShip.setThrusterActive(true);
		assertTrue(standardShip.isThrusterActive());
	}
	
	@Test
	public final void fireBullet_NormalCase(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		assertTrue(bullets.size() == 1);
		assertEquals(1, shipWorld.getNbBullets(), EPSILON);
		for (Bullet bullet: bullets) {
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getDirection().getXComponent(), 
					Math.cos(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getDirection().getYComponent(), 
					Math.sin(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getNorm(), 
					shipWorld.getRadius(),EPSILON);
			assertEquals(bullet.getRadius(),3,EPSILON);
			assertEquals(bullet.getVelocity().getNorm(),250,EPSILON);
			assertEquals(bullet.getVelocity().getDirection().getXComponent(),
					Math.cos(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getVelocity().getDirection().getYComponent(),
					Math.sin(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getMaxSpeed(),300000,EPSILON);
			assertEquals(bullet.getMass(),4/3*Math.PI*Math.pow(3,3)*Bullet.getMassDensity(),EPSILON);
			assertTrue(bullet.getShip() == shipWorld);
			assertTrue(shipWorld.getWorld().hasAsSpatialElement(bullet));
		}
	}
	
	@Test
	public final void fireBullet_TooManyBullets(){
		for(int i = 0; i < 7; i++){
			shipWorld.fireBullet();
			shipWorld.turn(Math.PI/7*2);
		}
		assertEquals(3, shipWorld.getNbBullets(), EPSILON);
	}
	
	@Test
	public final void fireBullet_NoWorld(){
		shipNoWorld.fireBullet();
		assertEquals(0, shipWorld.getNbBullets(), EPSILON);
	}
	
	// TODO NoProperWorld hard to check...
	
	// Include possible tests for abstract superclass
	
	@Test
	public final void terminate_NormalCase() {
		World originalWorld = shipWorld.getWorld();
		shipWorld.terminate();
		assertTrue(shipWorld.isTerminated());
		assertTrue(shipWorld.getWorld() == null);
		assertFalse(originalWorld.hasAsSpatialElement(shipWorld));
	}
	
	@Test
	public final void terminate_NormalCaseNonEffectiveWorld() {
		ship100.terminate();
		assertTrue(ship100.isTerminated());
		assertTrue(ship100.getWorld() == null);
	}
	
	@Test
	public final void canHaveAsWorld_NormalCase() {
		World newWorld = new World(1000,1000);
		assertTrue(shipWorld.canHaveAsWorld(newWorld));
	}
	
	@Test
	public final void canHaveAsWorld_NullWorld() {
		World world = null;
		assertTrue(shipWorld.canHaveAsWorld(world));
	}
	
	// For test of canHaveAsSpatialElement, see World testsuite.
	
	@Test
	public final void hasProperWorld_NormalCase() {
		assertTrue(shipWorld.hasProperWorld());
	}
	
	@Test
	public final void hasProperWorld_NoWorldCase() {
		shipWorld.getWorld().removeAsSpatialElement(shipWorld);
		assertTrue(shipWorld.hasProperWorld());
	}
	// For test of hasAsSpatialElement, see World testsuite.
	// TODO Rest hard to simulate
	
	@Test
	public final void setWorld_NormalCase() throws Exception {
		World newWorld = new World(2000,2000);
		newWorld.addAsSpatialElement(shipNoWorld);
		assertTrue(shipNoWorld.getWorld() == newWorld);
	}
	// addAsSpatialElement contains setWorld, which cannot be 
	// used seperately without adding the element first
	// So here, setWorld is tested indirectly.
	
	@Test(expected = IllegalArgumentException.class)
	public final void setWorld_NotAdded() throws Exception {
		World newWorld = new World(2000,2000);
		shipNoWorld.setWorld(newWorld);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setWorld_NullWorld() throws Exception {
		shipWorld.setWorld(null);
	}
	// TODO Rest hard to simulate
	
	// Checkers are tested because they are public methods, 
	// although you could say that they already have been tested through 
	// the tests for the constructor.
	
	@Test
	public final void isValidPosition_TrueCase(){
		assertTrue(ship100.isValidPosition(new Vector2D(5,10)));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNull(){
		assertFalse(ship100.isValidPosition(null));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNaN(){
		assertFalse(ship100.isValidPosition(new Vector2D(Double.NaN,10)));
	}	
	
	// TODO setters ook nog speciaal testen? (wss niet)
	
	
	@Test
	public final void move_NormalCase() throws Exception{
		ship100.move(1);
		assertEquals(90,ship100.getPosition().getXComponent(),EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NegativeTime() throws Exception{
		ship100.move(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_InfTime() throws Exception{
		ship100.move(Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NaNTime() throws Exception{
		ship100.move(Double.NaN);
	}
	
	@Test
	public final void getDistanceBetween_NormalCase() throws Exception{
		double newDistance = ship100.getDistanceBetween(standardShip);
		assertEquals(80,newDistance,EPSILON);
	}
	
	@Test
	public final void getDistanceBetween_SameElement() throws Exception{
		double newDistance = standardShip.getDistanceBetween(standardShip);
		assertEquals(0,newDistance,EPSILON);
	}
	
	// Extra test to check if distances are indeed negative.
	@Test
	public final void getDistanceBetween_CompleteOverlap() throws Exception{
		double newDistance = ship100.getDistanceBetween(ship100PiD4);
		assertEquals(-20,newDistance,EPSILON);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getDistanceBetween_NullElement() throws Exception{
		ship100.getDistanceBetween(null);
	}
	
	@Test
	public final void overlap_TrueCase() throws Exception{
		assertTrue(ship100.overlap(ship100PiD4));
	}
	
	@Test
	public final void overlap_FalseCase() throws Exception{
		assertFalse(ship100.overlap(standardShip));
	}
	
	@Test
	public final void overlap_TrueCaseSameElement() throws Exception{
		assertTrue(ship100.overlap(ship100));
	}
	
	@Test(expected = NullPointerException.class)
	public final void overlap_NullCase() throws Exception{
		ship100.overlap(null);
	}
	
	@Test
	public final void getTimeToCollision_NormalCase(){
		double newCollisionTime = ship100.getTimeToCollision(standardShipMutable);
		assertEquals(8,newCollisionTime,EPSILON);
		assertTrue(0 <= newCollisionTime);
		assertFalse(((Double) newCollisionTime).isNaN());
		ship100.move(newCollisionTime/2);
		standardShipMutable.move(newCollisionTime/2);
		assertTrue(fuzzyLessThanOrEqualTo(0.0,ship100.getDistanceBetween(standardShipMutable)));
		ship100.move(newCollisionTime/2);
		standardShipMutable.move(newCollisionTime/2);
		assertEquals(ship100.getDistanceBetween(standardShipMutable),0.0,EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_NoCollisionCase(){
		double newCollisionTime = ship100PiD4.getTimeToCollision(standardShip);
		assertTrue(newCollisionTime == Double.POSITIVE_INFINITY);
	}
	
	public final void getTimeToCollision_SameElement(){
		double newCollisionTime = ship100.getTimeToCollision(ship100);
		assertTrue(newCollisionTime == Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getTimeToCollision_NullCase(){
		ship100PiD4.getTimeToCollision(null);
	}
	
	@Test
	public final void getCollisionPosition_NormalCase(){
		Vector2D newCollisionPosition = ship100.getCollisionPosition(standardShip);
		assertEquals(10,newCollisionPosition.getXComponent(),EPSILON);
		assertEquals(0,newCollisionPosition.getYComponent(),EPSILON);
	}
	
	@Test
	public final void getCollisionPosition_NoCollision(){
		Vector2D newCollisionPosition = ship100PiD4.getCollisionPosition(standardShip);
		assertTrue(newCollisionPosition == null);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getCollisionPosition_NullCase(){
		ship100PiD4.getCollisionPosition(null);
	}
	
	@Test
	public final void getTimeToHorizontalWallCollision_NormalCase(){
		double time = shipVelocity.getTimeToHorizontalWallCollision(20);
		assertEquals(shipVelocity.getRadius(), 
				Math.abs(20 - (shipVelocity.getPosition().getYComponent()
				+ shipVelocity.getVelocity().getYComponent()*time)),EPSILON);
	}
	
	@Test
	public final void getTimeToHorizontalWallCollision_NoCollision(){
		double time = shipVelocity.getTimeToHorizontalWallCollision(10);
		assertEquals(Double.POSITIVE_INFINITY,time,EPSILON);
	}
	
	@Test
	public final void getTimeToHorizontalWallCollision_NaNCase(){
		double time = shipVelocity.getTimeToHorizontalWallCollision(Double.NaN);
		assertEquals(Double.POSITIVE_INFINITY,time,EPSILON);
	}
	
	@Test
	public final void getTimeToVerticalWallCollision_NormalCase(){
		double time = shipVelocity.getTimeToVerticalWallCollision(20);
		assertEquals(shipVelocity.getRadius(), 
				Math.abs(20 - (shipVelocity.getPosition().getXComponent()
				+ shipVelocity.getVelocity().getXComponent()*time)),EPSILON);
	}
	
	@Test
	public final void getTimeToVerticalWallCollision_NoCollision(){
		double time = shipVelocity.getTimeToVerticalWallCollision(10);
		assertEquals(Double.POSITIVE_INFINITY,time,EPSILON);
	}
	
	@Test
	public final void getTimeToVerticalWallCollision_NaNCase(){
		double time = shipVelocity.getTimeToVerticalWallCollision(Double.NaN);
		assertEquals(Double.POSITIVE_INFINITY,time,EPSILON);
	}
	
	@Test
	public final void isValidRadius_TrueCase(){
		assertTrue(ship100.isValidRadius(20));
	}
	
	@Test
	public final void isValidRadius_FalseCaseNaN(){
		assertFalse(ship100.isValidRadius(Double.NaN));
	}
	
	@Test
	public final void isValidRadius_FalseCaseTooLow(){
		assertFalse(ship100.isValidRadius(-0.01));
	}
	
	@Test
	public final void isValidRadius_FalseCaseInf(){
		assertFalse(ship100.isValidRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidMass_trueCase(){
		assertTrue(ship100.isValidMass(10));
	}
	
	@Test
	public final void isValidMass_falseCase(){
		assertFalse(ship100.isValidMass(-1));
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
	
	@Test
	public final void isShip_TrueCase(){
		SpatialElement ship = new Ship(new Vector2D(100,0),10, 1 ,new Vector2D(-10,0),300000,1E5);
		assertTrue(ship.isShip());
	}
	
	@Test
	public final void isShip_FalseCase(){
		SpatialElement asteroid = new Asteroid(new Vector2D(100,0),10,new Vector2D(-10,0),300000,new Random());
		assertFalse(asteroid.isShip());
	}
	
	@Test
	public final void isAsteroid_TrueCase(){
		SpatialElement asteroid = new Asteroid(new Vector2D(100,0),10,new Vector2D(-10,0),300000,new Random());
		assertTrue(asteroid.isAsteroid());
	}
	
	@Test
	public final void isAsteroid_FalseCase(){
		SpatialElement asteroid = new Ship(new Vector2D(100,0),10, 1 ,new Vector2D(-10,0),300000,1E5);
		assertFalse(asteroid.isAsteroid());
	}
	
	@Test
	public final void isBullet_TrueCase(){
		Ship ship = new Ship(new Vector2D(100,0),10, 1 ,new Vector2D(-10,0),300000,1E5);
		SpatialElement bullet = new Bullet(new Vector2D(100,0),10, new Vector2D(-10,0),300000,ship);
		assertTrue(bullet.isBullet());
	}
	
	@Test
	public final void isBullet_FalseCase(){
		SpatialElement asteroid = new Asteroid(new Vector2D(100,0),10,new Vector2D(-10,0),300000,new Random());
		assertFalse(asteroid.isBullet());
	}
	
	@Test
	public final void canHaveAsBullet_TrueCase(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			assertTrue(shipWorld.canHaveAsBullet(bullet));
		}
	}
	
	@Test
	public final void canHaveAsBullet_NullCase(){
		assertFalse(shipWorld.canHaveAsBullet(null));
	}
	
	@Test
	public final void canHaveAsBullet_NotFromThisShip(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			assertFalse(standardShip.canHaveAsBullet(bullet));
		}
	}
	
	@Test
	public final void canHaveAsBullet_TerminatedCase(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			bullet.terminate();
			assertFalse(shipWorld.canHaveAsBullet(bullet));
		}
	}
	
	@Test
	public final void addAsBullet_normalCase(){
		// already tested by testing fireBullet
		shipWorld.fireBullet();
		assertEquals(1,shipWorld.getNbBullets());
	}
	
	@Test
	public final void addAsBullet_alreadyPresent(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			// has actually already been added by fireBullet
			shipWorld.addAsBullet(bullet); 
		}
		assertEquals(1, shipWorld.getNbBullets());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void addAsBullet_NullCase(){
		shipWorld.addAsBullet(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsBullet_NotFromThisShip(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			standardShip.addAsBullet(bullet);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsBullet_TerminatedCase(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			bullet.terminate();
			shipWorld.addAsBullet(bullet);
		}
	}
	
	@Test
	public final void removeAsBullet_normalCase(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			bullet.terminate();
		}
		assertEquals(0, shipWorld.getNbBullets());
	}
	
	@Test
	public final void removeAsBullet_alreadyRemoved(){
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		for(Bullet bullet : bullets){
			bullet.terminate();
			// has actually already been removed by terminate
			shipWorld.removeAsBullet(bullet); 
		}
		assertEquals(0, shipWorld.getNbBullets());
	}
	
	@Test
	public final void removeAsBullet_NotPresent(){
		shipWorld.fireBullet();
		Bullet temp = new Bullet(new Vector2D(20,20), 10, new Vector2D(0,0),
					10,shipNoWorld);
		temp.terminate();
		shipWorld.removeAsBullet(temp);
		assertEquals(1, shipWorld.getNbBullets());
	}
	
	@Test(expected = NullPointerException.class)
	public final void removeAsBullet_NullCase(){
		shipWorld.removeAsBullet(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void removeAsBullet_NonTerminatedCase(){
		Bullet temp = new Bullet(new Vector2D(20,20), 10, new Vector2D(0,0),
				10,shipNoWorld);
		shipWorld.removeAsBullet(temp);
	}
	
	@Test
	public final void setMaxNbBullets_NormalCase(){
		shipWorld.setMaxNbBullets(5);
		assertEquals(5, shipWorld.getMaxNbBullets());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setMaxNbBullets_NegativeCase(){
		shipWorld.setMaxNbBullets(-5);
	}
	
	
//	@Test
//	public final void canResolve_TrueCase(){
//		shipWorld.fireBullet();
//		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
//		for(Bullet bullet : bullets){
//			assertTrue(shipWorld.isValidObjectCollision(bullet)); 
//		}
//	}
//	
//	@Test
//	public final void canResolve_NullCase(){
//		assertFalse(shipWorld.isValidObjectCollision(null)); 
//	}
//	
//	@Test
//	public final void canResolve_NullWorldCase1(){
//		assertFalse(shipWorld.isValidObjectCollision(standardShip)); 
//	}
//	
//	@Test
//	public final void canResolve_NullWorldCase2(){
//		assertFalse(standardShip.isValidObjectCollision(shipWorld)); 
//	}
//	
//	@Test
//	public final void canResolve_NotSameWorld(){
//		World testWorld = new World(1000,1000);
//		testWorld.addAsSpatialElement(shipNoWorld);
//		assertFalse(shipNoWorld.isValidObjectCollision(shipWorld)); 
//	}
//	
//	
//	
//	@Test
//	public final void resolve_BounceCase(){
//		Vector2D shipWorldPos = shipWorld.getPosition();
//		Vector2D shipWorldBouncePos = shipWorldBounce.getPosition();
//		shipWorld.resolve(shipWorldBounce);
//		assertEquals(-1, shipWorld.getVelocity().getXComponent(),EPSILON);
//		assertEquals(0, shipWorld.getVelocity().getYComponent(),EPSILON);
//		assertEquals(0, shipWorldBounce.getVelocity().getXComponent(),EPSILON);
//		assertEquals(0, shipWorldBounce.getVelocity().getYComponent(),EPSILON);
//		assertEquals(shipWorldPos.getXComponent(), shipWorld.getPosition().getXComponent(),EPSILON);
//		assertEquals(shipWorldPos.getYComponent(), shipWorld.getPosition().getYComponent(),EPSILON);
//		assertEquals(shipWorldBouncePos.getXComponent(), shipWorldBounce.getPosition().getXComponent(),EPSILON);
//		assertEquals(shipWorldBouncePos.getYComponent(), shipWorldBounce.getPosition().getYComponent(),EPSILON);
//	}
//	// TODO uitgebreider testen? (!= hoeken en massas???)
//	// TODO andere cases testen...
//	
//	@Test(expected = IllegalArgumentException.class)
//	public final void resolve_NullCase(){
//		standardShip.resolve(null);
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public final void resolve_NullWorldCase1(){
//		shipWorld.resolve(standardShip); 
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public final void resolve_NullWorldCase2(){
//		standardShip.resolve(shipWorld); 
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public final void resolve_NotSameWorld(){
//		World testWorld = new World(1000,1000);
//		testWorld.addAsSpatialElement(shipNoWorld);
//		shipNoWorld.resolve(shipWorld); 
//	}
	
	
	@Test
	public final void canHaveAsProgram_NormalTrueCase(){
		assertTrue(ship100.canHaveAsProgram(standardProgram));
	}
	
	@Test
	public final void canHaveAsProgram_TerminatedCase(){
		standardProgram.terminate();
		assertFalse(ship100.canHaveAsProgram(standardProgram));
	}
	
	@Test
	public final void canHaveAsProgram_NullCase(){
		assertTrue(ship100.canHaveAsProgram(null));
	}
	// TODO Hard to check the typechecking
	
	@Test
	public final void setProgram_NormalCase(){
		ship100.setProgram(standardProgram);
		assertTrue(standardProgram == ship100.getProgram());
		assertTrue(standardProgram.getShip() == ship100);
	}
	
	@Test
	public final void setProgram_NullCase(){
		ship100.setProgram(null);
		assertTrue(null == ship100.getProgram());
	}
	
	@Test
	public final void setProgram_ChangeProgramCase(){
		ship100.setProgram(standardProgram);
		assertTrue(standardProgram == ship100.getProgram());
		assertTrue(standardProgram.getShip() == ship100);
		ship100.setProgram(standardProgram2);
		assertTrue(standardProgram2 == ship100.getProgram());
		assertTrue(standardProgram2.getShip() == ship100);
		assertTrue(standardProgram.getShip() == null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void setProgram_TerminatedCase(){
		ship100.terminate();
		ship100.setProgram(null);
	}
	
	@Test
	public final void setProgram_CollidedCase(){
		ship100.setProgram(standardProgram);
		ship100.collide();
		assertTrue(ship100.getProgram() == null);
		assertTrue(ship100.isTerminated());
		assertTrue(standardProgram.isTerminated());
	}
	
	// TODO Alles te maken met collisions
	
	
	@Test
	public final void setVelocity_NormalCase(){
		ship100.setVelocity(new Vector2D(10,10));
		assertEquals(10, ship100.getVelocity().getXComponent(), EPSILON);
		assertEquals(10, ship100.getVelocity().getYComponent(), EPSILON);
	}
	
	@Test
	public final void setVelocity_NullCase(){
		ship100.setVelocity(null);
		assertEquals(0, ship100.getVelocity().getXComponent(), EPSILON);
		assertEquals(0, ship100.getVelocity().getYComponent(), EPSILON);
	}
	
	@Test
	public final void setVelocity_NaNCase(){
		ship100.setVelocity(new Vector2D(Double.NaN,10));
		assertEquals(0, ship100.getVelocity().getXComponent(), EPSILON);
		assertEquals(0, ship100.getVelocity().getYComponent(), EPSILON);
	}
	
	@Test
	public final void setVelocity_TooLargeCase(){
		ship100.setVelocity(new Vector2D(300000,300000));
		assertEquals(300000/Math.sqrt(2), ship100.getVelocity().getXComponent(), EPSILON);
		assertEquals(300000/Math.sqrt(2), ship100.getVelocity().getYComponent(), EPSILON);
	}
	
	// TODO alles rond isValidObjectCollision (en eronder is SpatialElement)
}
