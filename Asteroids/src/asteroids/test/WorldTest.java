package asteroids.test;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import static asteroids.Util.*;
import asteroids.model.*;

public class WorldTest {
	private static World worldToAdd;
	/**
	 * Set up an immutable test fixture
	 * 
	 * @post 	The variable worldToAdd references a world size 1000 by 1000, where spatial elements can be added to.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception{
		worldToAdd = new World(1000,1000);
	}
	
	private static World standardWorld;
	private static Set<Ship> standardShips;
	private static Set<Asteroid> standardAsteroids;
	private static Set<Bullet> standardBullets;
	private static Ship newShip, newShip2;
	private static Asteroid newAsteroid;
	private static World worldToEvolve;
	
	/**
	 * Set up an mutable test fixture
	 * 
	 * @post	The variable standardShip1 references a new ship at (100,100) with 
	 * 			a velocity of zero, an angle of zero, a radius of 50, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post	The variable standardShip2 references a new ship at (200,200) with 
	 * 			a velocity of zero, an angle of zero, a radius of 50, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post 	The variable standardAsteroid1 references a new asteroid at (300,300) with zero velocity,
	 * 			radius of 50, and maximum velocity of 300000 and a new Random object.
	 * @post 	The variable standardAsteroid2 references a new asteroid at (300,300) with zero velocity,
	 * 			radius of 50, and maximum velocity of 300000 and a new Random object.
	 * @post	The variable standardBullet1 references a new Bullet at (500,500) with radius 20,
	 * 			velocity zero, maximum velocity 300000 and standardShip1 as its source.
	 * @post	The variable standardBullet2 references a new Bullet at (600,600) with radius 20,
	 * 			velocity zero, maximum velocity 300000 and standardShip2 as its source.
	 * @post	The variable standardShips references a Set containing standardShip1 and standardShip2.
	 * @post	The variable standardAsteroids references a Set containing standardAsteroid1 
	 * 			and standardAsteroid2.
	 * @post	The variable standardBullets references a Set containing standardBullet1
	 * 			and standardBullet2.
	 * @post 	The variable standardWorld references a world size 1000 by 1000, 
	 * 			referencing standardShip1, standardShip2, standardAsteroid1,
	 * 			standardAsteroid2, standardBullet1, and standardBullet2.
	 * @post	The variable newWorld references a new World with dimensions (1000,1000)
	 * 			referencing the element newShip.
	 * @post	The variable newShip references a new ship at (100,100) with 
	 * 			a velocity (100,0), an angle of zero, a radius of 50, a maximum velocity of 300000
	 * 			and a mass of 1.1E18.
	 * @post	The variable newShip2 references a new ship at (900,100) with 
	 * 			a velocity (-100,0), an angle of zero, a radius of 50, a maximum velocity of 300000
	 * 			and a mass of 1.1E18.
	 * @post	The variable newAsteroid references a new asteroid at (500,100) with zero velocity,
	 * 			radius of 50, and maximum velocity of 300000 and a new Random object.
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		standardWorld = new World(1000,1000);
		Ship standardShip1 = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 1e5);
		Ship standardShip2 = new Ship(new Vector2D(200,200), 0, 50, new Vector2D(0,0), 300000, 1e5);
		standardShips = new HashSet<Ship>();
		standardShips.add(standardShip1);
		standardShips.add(standardShip2);
		Asteroid standardAsteroid1 = new Asteroid(new Vector2D(300,300), 50, new Vector2D(0,0), 300000, new Random());
		Asteroid standardAsteroid2 = new Asteroid(new Vector2D(400,400), 50, new Vector2D(0,0), 300000, new Random());
		standardAsteroids = new HashSet<Asteroid>();
		standardAsteroids.add(standardAsteroid1);
		standardAsteroids.add(standardAsteroid2);
		Bullet standardBullet1 = new Bullet(new Vector2D(500,500),20,new Vector2D(0,0),300000,standardShip1);
		Bullet standardBullet2 = new Bullet(new Vector2D(600,600),20,new Vector2D(0,0),300000,standardShip2);
		standardBullets = new HashSet<Bullet>();
		standardBullets.add(standardBullet1);
		standardBullets.add(standardBullet2);
		standardWorld.addAsSpatialElement(standardShip1);
		standardWorld.addAsSpatialElement(standardShip2);
		standardWorld.addAsSpatialElement(standardAsteroid1);
		standardWorld.addAsSpatialElement(standardAsteroid2);
		standardWorld.addAsSpatialElement(standardBullet1);
		standardWorld.addAsSpatialElement(standardBullet2);
		
		worldToEvolve = new World(1000,1000);
		newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(100,0), 300000, 1.1E18);
		newShip2 = new Ship(new Vector2D(900,100), 0, 50, new Vector2D(-100,0), 300000, 1.1E18);
		worldToEvolve.addAsSpatialElement(newShip);
		newAsteroid = new Asteroid(new Vector2D(500,100), 50, new Vector2D(0,0), 300000, new Random());
	}
	
	@Test
	public final void constructor_NormalCase() throws Exception{
		World newWorld = new World(2000,3000);
		assertEquals(2000, newWorld.getWidth(),EPSILON);
		assertEquals(3000, newWorld.getHeight(),EPSILON);
		assertFalse(newWorld.isTerminated());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNWidth() throws Exception{
		new World(Double.NaN,3000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNheight() throws Exception{
		new World(2000,Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NegativeWidth() throws Exception{
		new World(-10,1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NegativeHeight() throws Exception{
		new World(1000,-10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighWidth() throws Exception{
		new World(Double.POSITIVE_INFINITY,1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighHeight() throws Exception{
		new World(1000,Double.POSITIVE_INFINITY);
	}
	
	@Test
	public final void terminate_NormalCase() {
		Set<Asteroid> asteroids = standardWorld.getAsteroids();
		Set<Ship> ships = standardWorld.getShips();
		Set<Bullet> bullets = standardWorld.getBullets();
		standardWorld.terminate();
		assertTrue(standardWorld.isTerminated());
		assertTrue(standardWorld.getAsteroids().isEmpty());
		assertTrue(standardWorld.getShips().isEmpty());
		assertTrue(standardWorld.getBullets().isEmpty());
		for (Asteroid asteroid: asteroids) {
			assertTrue(asteroid.isTerminated());
			assertFalse(standardWorld.hasAsSpatialElement(asteroid));
		}
		for (Ship ship: ships) {
			assertTrue(ship.isTerminated());
			assertFalse(standardWorld.hasAsSpatialElement(ship));
		}
		for (Bullet bullet: bullets) {
			assertTrue(bullet.isTerminated());
			assertFalse(standardWorld.hasAsSpatialElement(bullet));
		}
	}
	
	@Test
	public final void isValidWidth_TrueCase() {
		assertTrue(standardWorld.isValidWidth(10000));
	}
	
	@Test
	public final void isValidWidth_FalseCaseTooLow() {
		assertFalse(standardWorld.isValidWidth(-1000));
	}
	
	@Test
	public final void isValidWidth_FalseCaseTooHigh() {
		assertFalse(standardWorld.isValidWidth(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidWidth_FalseNaNCase() {
		assertFalse(standardWorld.isValidWidth(Double.NaN));
	}
	
	@Test
	public final void isValidHeigth_TrueCase() {
		assertTrue(standardWorld.isValidHeight(10000));
	}
	
	@Test
	public final void isValidHeigth_FalseCaseTooLow() {
		assertFalse(standardWorld.isValidHeight(-1000));
	}
	
	@Test
	public final void isValidHeigth_FalseCaseTooHigh() {
		assertFalse(standardWorld.isValidHeight(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidHeigth_FalseNaNCase() {
		assertFalse(standardWorld.isValidHeight(Double.NaN));
	}
	
	//Second part of condition cannot be tested since world.elements
	// is not publicly available
	@Test
	public final void getShips_StandardCase() {
		Set<Ship> ships = standardWorld.getShips();
		for(Ship ship: ships) {
			assertTrue(standardWorld.hasAsSpatialElement(ship));
		}
	}
	
	@Test
	public final void getAsteroids_StandardCase() {
		Set<Asteroid> asteroids = standardWorld.getAsteroids();
		for(Asteroid asteriod: asteroids) {
			assertTrue(standardWorld.hasAsSpatialElement(asteriod));
		}
	}
	
	@Test
	public final void getBullets_StandardCase() {
		Set<Bullet> bullets = standardWorld.getBullets();
		for(Bullet bullet: bullets) {
			assertTrue(standardWorld.hasAsSpatialElement(bullet));
		}
	}
	
	@Test
	public final void getIllegalOverlap_NoOverlap(){
		assertTrue(standardWorld.getIllegalOverlap(newShip2) == null);
	}
	
	@Test
	public final void getIllegalOverlap_Overlap(){
		// cannot be tested since no overlapping object can
		// be added to a world, see the tests of canHaveAsSpatialElement
	}
	
	@Test
	public final void hasAsSpatialElement_TrueCases() {
		// just test all at once
		for(Ship ship: standardShips) {
			assertTrue(standardWorld.hasAsSpatialElement(ship));
		}
		for(Asteroid asteroid: standardAsteroids) {
			assertTrue(standardWorld.hasAsSpatialElement(asteroid));
		}
		for(Bullet bullet: standardBullets) {
			assertTrue(standardWorld.hasAsSpatialElement(bullet));
		}
	}
	
	@Test
	public final void hasAsSpatialElement_FalseCase() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		Asteroid newAsteroid = new Asteroid(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, new Random());
		Bullet newBullet = new Bullet(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, newShip);
		assertFalse(standardWorld.hasAsSpatialElement(newShip));
		assertFalse(standardWorld.hasAsSpatialElement(newAsteroid));
		assertFalse(standardWorld.hasAsSpatialElement(newBullet));
	}
	
	@Test
	public final void canHaveAsSpatialElement_TrueCase() {
		Ship newShip = new Ship(new Vector2D(700,700), 0, 50, new Vector2D(0,0), 300000, 50);
		Asteroid newAsteroid = new Asteroid(new Vector2D(800,800), 50, new Vector2D(0,0), 300000, new Random());
		Bullet newBullet = new Bullet(new Vector2D(900,900), 50, new Vector2D(0,0), 300000, newShip);
		standardWorld.canHaveAsSpatialElement(newShip);
		assertTrue(standardWorld.canHaveAsSpatialElement(newShip));
		assertTrue(standardWorld.canHaveAsSpatialElement(newAsteroid));
		assertTrue(standardWorld.canHaveAsSpatialElement(newBullet));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseNull() {
		assertFalse(standardWorld.canHaveAsSpatialElement(null));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseTerminatedWorld() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.terminate();
		assertFalse(standardWorld.canHaveAsSpatialElement(newShip));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseTerminatedBullet() {
		Bullet bullet = new Bullet(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, new Ship());
		bullet.terminate();
		assertFalse(standardWorld.canHaveAsSpatialElement(bullet));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseTerminatedElement() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		newShip.terminate();
		assertFalse(standardWorld.canHaveAsSpatialElement(newShip));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseIllegalOverlap() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.canHaveAsSpatialElement(newShip));
	}
	
	@Test
	public final void canHaveAsSpatialElement_FalseCaseOutOfBounds() {
		Ship newShip = new Ship(new Vector2D(100,1000), 0, 50, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.canHaveAsSpatialElement(newShip));
	}
	
	@Test
	public final void resolveInitialBullet_NotABullet(){
		Ship newShip = new Ship(new Vector2D(100,1000), 0, 50, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.resolveInitialBullet(newShip));
	}
	
	@Test(expected = NullPointerException.class)
	public final void resolveInitialBullet_NullCase(){
		standardWorld.resolveInitialBullet(null);
	}
	
	@Test
	public final void resolveInitialBullet_BulletOutOfBounds(){
		Bullet bullet = new Bullet(new Vector2D(100,1100), 50, new Vector2D(0,0), 300000, new Ship());
		assertTrue(standardWorld.resolveInitialBullet(bullet));
		assertTrue(bullet.isTerminated());
	}
	
	@Test
	public final void resolveInitialBullet_BulletCollides(){
		Bullet bullet = new Bullet(new Vector2D(800,800), 50, new Vector2D(0,0), 300000, new Ship());
		Asteroid newOverlapAsteroid = new Asteroid(new Vector2D(800,800), 50, new Vector2D(0,0), 300000, new Random());
		standardWorld.addAsSpatialElement(newOverlapAsteroid);
		assertTrue(standardWorld.resolveInitialBullet(bullet));
		assertTrue(bullet.isTerminated());
		assertTrue(newOverlapAsteroid.isTerminated());
	}
	
	@Test
	public final void addAsSpatialElement_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(800,800), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
		assertTrue(standardWorld.hasAsSpatialElement(newShip));
		assertTrue(newShip.getWorld() == standardWorld);
		// For distinctions based on resolveInitialBullet,
		// see the tests of that method
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_Null() throws Exception{
		standardWorld.addAsSpatialElement(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_TerminatedWorld() throws Exception{
		standardWorld.terminate();
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_CaseTerminatedBullet() {
		Bullet bullet = new Bullet(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, new Ship());
		bullet.terminate();
		standardWorld.addAsSpatialElement(bullet);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_CaseTerminatedElement() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		newShip.terminate();
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_CaseIllegalOverlap() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_CaseOutOfBounds() {
		Ship newShip = new Ship(new Vector2D(100,1000), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void addAsSpatialElement_AlreadyAdded() throws Exception{
		Ship newShip = new Ship(new Vector2D(800,800), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test
	public final void removeAsSpatialElement_NormalCase() throws Exception{
		for(Ship ship: standardShips) {
			standardWorld.removeAsSpatialElement(ship);
			assertFalse(standardWorld.hasAsSpatialElement(ship));
		}
		for(Asteroid asteroid: standardAsteroids) {
			standardWorld.removeAsSpatialElement(asteroid);
			assertFalse(standardWorld.hasAsSpatialElement(asteroid));
		}
		for(Bullet bullet: standardBullets) {
			standardWorld.removeAsSpatialElement(bullet);
			assertFalse(standardWorld.hasAsSpatialElement(bullet));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void removeAsSpatialElement_NotAssigned() throws Exception{
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.removeAsSpatialElement(newShip);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void removeAsSpatialElement_AssignedToOtherWorld() throws Exception{
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		worldToAdd.addAsSpatialElement(newShip);
		standardWorld.removeAsSpatialElement(newShip);
	}
	
	@Test
	public final void hasProperSpatialElements_TrueCase() {
		assertTrue(standardWorld.hasProperSpatialElements());
	}
	
	@Test
	public final void hasProperSpatialElements_FalseCase() {
		// Since hasProperSpatialElements is a class invariant,
		// no false case can be generated to test...
	}
	
	@Test
	public final void withinBounds_TrueCase() {
		Ship ShipCloseToBoundary = new Ship(new Vector2D(900,900), 0, 99, new Vector2D(0,0), 300000, 50);
		assertTrue(standardWorld.withinBounds(ShipCloseToBoundary));
	}
	
	@Test
	public final void withinBounds_NullCase() {
		assertFalse(standardWorld.withinBounds(null));
	}
	
	@Test
	public final void withinBounds_OutsideRight() {
		Ship ShipOutOfBoundary = new Ship(new Vector2D(1500,500), 0, 99, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.withinBounds(ShipOutOfBoundary));
	}
	
	@Test
	public final void withinBounds_OutsideLeft() {
		Ship ShipOutOfBoundary = new Ship(new Vector2D(-500,500), 0, 99, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.withinBounds(ShipOutOfBoundary));
	}
	
	@Test
	public final void withinBounds_OutsideTop() {
		Ship ShipOutOfBoundary = new Ship(new Vector2D(500,1500), 0, 99, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.withinBounds(ShipOutOfBoundary));
	}
	
	@Test
	public final void withinBounds_OutsideBottom() {
		Ship ShipOutOfBoundary = new Ship(new Vector2D(500,-500), 0, 99, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.withinBounds(ShipOutOfBoundary));
	}
	
	//extra test near boundary
	@Test
	public final void withinBounds_OnBounds() {
		Ship ShipOnBoundary = new Ship(new Vector2D(1000,1000), 0, 99, new Vector2D(0,0), 300000, 50);
		assertFalse(standardWorld.withinBounds(ShipOnBoundary));
	}
	
	@Test
	public final void isValidObjectCollision_TrueCase() {
		for (Ship ship: standardShips) {
			for (Asteroid asteroid: standardAsteroids) {
				assertTrue(standardWorld.isValidObjectCollision(ship,asteroid));
			}
			for (Bullet bullet: standardBullets) {
				if (bullet.getShip() != ship) {
					assertTrue(standardWorld.isValidObjectCollision(ship,bullet));
				}
			}
		}
	}
	
	@Test
	public final void isValidObjectCollision_SameElement(){
		assertFalse(standardWorld.isValidObjectCollision(newShip, newShip));
	}
	
	@Test
	public final void isValidObjectCollision_BulletFromSameShip() {
		for (Ship ship: standardShips) {
			for (Bullet bullet: standardBullets) {
				if (bullet.getShip() == ship) {
					assertFalse(standardWorld.isValidObjectCollision(ship,bullet));
				}
			}
		}
	}
	
	@Test
	public final void isValidObjectCollision_NotSameWorld() {
		World newWorld = new World(1000,1000);
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		newWorld.addAsSpatialElement(newShip);
		for (Asteroid asteroid: standardAsteroids) {
			assertFalse(standardWorld.isValidObjectCollision(asteroid,newShip));
		}
	}
	
	@Test
	public final void evolve_CaseMove(){
		worldToEvolve.evolve(1.0, null);
		assertEquals(200,newShip.getPosition().getXComponent(),EPSILON);
	}
	
	@Test
	public final void evolve_CaseMoveThrust(){
		newShip.setThrusterActive(true);
		worldToEvolve.evolve(1.0, null);
		assertEquals(200,newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(101,newShip.getVelocity().getXComponent(),EPSILON);
	}
	
	@Test
	public final void evolve_CaseWallCollision(){
		worldToEvolve.evolve(10.0, null);
		assertEquals(800,newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(-100,newShip.getVelocity().getXComponent(),EPSILON);
	}
	
	@Test
	public final void evolve_CaseObjectCollisionBounce(){
		worldToEvolve.addAsSpatialElement(newShip2);
		worldToEvolve.evolve(7.0, null);
		assertEquals(100,newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(-100,newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(900,newShip2.getPosition().getXComponent(),EPSILON);
		assertEquals(100,newShip2.getVelocity().getXComponent(),EPSILON);
	}
	
	@Test
	public final void evolve_CaseObjectCollisionBulletHitShip(){
		worldToEvolve.addAsSpatialElement(newShip2);
		newShip.fireBullet();
		worldToEvolve.evolve(7.0, null);
		assertEquals(800,newShip.getPosition().getXComponent(),EPSILON);
		assertTrue(newShip2.isTerminated());
		assertTrue(worldToEvolve.getBullets().isEmpty());
	}
	
	@Test
	public final void evolve_CaseObjectCollisionShipHitAsteroid(){
		worldToEvolve.addAsSpatialElement(newAsteroid);
		worldToEvolve.evolve(7.0, null);
		assertTrue(newShip.isTerminated());
		assertTrue(worldToEvolve.getAsteroids().contains(newAsteroid));
	}
	
	@Test
	public final void evolve_CaseObjectCollisionBulletHitAsteroid(){
		newShip.fireBullet();
		worldToEvolve.addAsSpatialElement(newAsteroid);
		worldToEvolve.evolve(2.0, null);
		assertTrue(worldToEvolve.getBullets().isEmpty());
		assertTrue(newAsteroid.isTerminated());
		assertFalse(worldToEvolve.getAsteroids().contains(newAsteroid));
		assertEquals(2,worldToEvolve.getAsteroids().size());
	}
	
	@Test
	public final void evolve_CaseBulletFirstBounce(){
		newShip.fireBullet();
		worldToEvolve.evolve(4.0, null);
		for(Bullet bullet: worldToEvolve.getBullets()){
		assertEquals(844,bullet.getPosition().getXComponent(),EPSILON);
		assertFalse(bullet.isTerminated());
		assertTrue(bullet.getHasBounced());
		}
	}
	
	@Test
	public final void evolve_CaseBulletSecondBounce(){
		newShip.fireBullet();
		worldToEvolve.evolve(10.0, null);
		assertTrue(worldToEvolve.getBullets().isEmpty());
	}
	
	@Test
	public final void evolve_CaseBulletOverSource(){
		newShip.fireBullet();
		worldToEvolve.evolve(7.0, null);
		assertFalse(worldToEvolve.getBullets().isEmpty());
		assertEquals(800,newShip.getPosition().getXComponent(),EPSILON);
	}
	
}
