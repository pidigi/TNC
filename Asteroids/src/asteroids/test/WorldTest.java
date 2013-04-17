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
	
	/**
	 * Set up an mutable test fixture
	 * 
	 * @post 	The variable standardWorld references a world size 1000 by 1000, 
	 * 			containing 2 ships, 2 asteroids and 2 bullets also stored in sets standardShips,
	 * 			standardBullets and standardAsteroids.
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		standardWorld = new World(1000,1000);
		Ship standardShip1 = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		Ship standardShip2 = new Ship(new Vector2D(200,200), 0, 50, new Vector2D(0,0), 300000, 50);
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
	}
	
	@Test
	public final void constructor_NormalCase() throws Exception{
		World newWorld = new World(2000,3000);
		assertEquals(2000, newWorld.getWidth(),EPSILON);
		assertEquals(3000, newWorld.getHeight(),EPSILON);
		assertFalse(newWorld.isTerminated());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNWidt() throws Exception{
		new World(Double.NaN,3000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNheigth() throws Exception{
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
		//TODO: Checken of leeg is? Nog eens checken aan de hand van de formele specificaties van de methode.
		assertTrue(standardWorld.isTerminated());
		//TODO: Implementatie van het vernietigen van de asteroids
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
	
	@Test
	public final void getShips_StandardCase() {
		Set<Ship> ships = standardWorld.getShips();
		for(Ship ship: ships) {
			assertTrue(standardShips.contains(ship));
		}
		for(Ship ship: standardShips) {
			assertTrue(ships.contains(ship));
		}
	}
	
	@Test
	public final void getAsteroids_StandardCase() {
		Set<Asteroid> asteroids = standardWorld.getAsteroids();
		for(Asteroid asteriod: asteroids) {
			assertTrue(standardAsteroids.contains(asteriod));
		}
		for(Asteroid asteriod: standardAsteroids) {
			assertTrue(asteroids.contains(asteriod));
		}
	}
	
	@Test
	public final void getBullets_StandardCase() {
		Set<Bullet> bullets = standardWorld.getBullets();
		for(Bullet bullet: bullets) {
			assertTrue(standardBullets.contains(bullet));
		}
		for(Bullet bullet: standardBullets) {
			assertTrue(bullets.contains(bullet));
		}
	}
	
	@Test
	public final void hasAsSpatialElement_TrueCases() {
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
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		Asteroid newAsteroid = new Asteroid(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, new Random());
		Bullet newBullet = new Bullet(new Vector2D(100,100), 50, new Vector2D(0,0), 300000, newShip);
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
	public final void canHaveAsSpatialElement_FalseCaseTerminatedElement() {
		Ship newShip = new Ship(new Vector2D(100,100), 0, 50, new Vector2D(0,0), 300000, 50);
		newShip.terminate();
		assertFalse(standardWorld.canHaveAsSpatialElement(newShip));
	}
	
	@Test
	public final void addAsSpatialElement_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(800,800), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
		assertTrue(standardWorld.hasAsSpatialElement(newShip));
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
	public final void addAsSpatialElement_AlreadyAdded() throws Exception{
		Ship newShip = new Ship(new Vector2D(800,800), 0, 50, new Vector2D(0,0), 300000, 50);
		standardWorld.addAsSpatialElement(newShip);
		standardWorld.addAsSpatialElement(newShip);
	}
	
	@Test
	public final void removeAsSpatialElement_NormalCase() throws Exception{
		for(Ship ship: standardShips) {
			standardWorld.removeAsSpatialElement(ship);
		}
		for(Asteroid asteroid: standardAsteroids) {
			standardWorld.removeAsSpatialElement(asteroid);
		}
		for(Bullet bullet: standardBullets) {
			standardWorld.removeAsSpatialElement(bullet);
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
	public final void hasProperSpatialElement_TrueCase() {
		assertTrue(standardWorld.hasProperSpatialElements());
	}
	
	// TODO How to test for false case?
//	@Test
//	public final void hasProperSpatialElement_FalseCase() {
//		assertFalse(standardWorld.hasProperSpatialElements());
//	}
}
