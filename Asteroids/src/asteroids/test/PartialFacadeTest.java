//package asteroids.test;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import asteroids.IFacade;
//import asteroids.IShip;
//import asteroids.ModelException;
//import asteroids.Util;
//import asteroids.model.Facade;
//
//public class PartialFacadeTest {
//
//  IFacade facade;
//  
//  @Before
//  public void setUp() {
//    facade = new Facade();
//  }
//  
//  @Test
//  public void testCreateShip() {
//    IShip ship = facade.createShip(100, 200, 10, -10, 20, -Math.PI);
//    assertNotNull(ship);
//    assertEquals(100, facade.getX(ship), Util.EPSILON);
//    assertEquals(20, facade.getRadius(ship), Util.EPSILON);
//  }
//
//  @Test(expected=ModelException.class)
//  public void testCreateShipXIsNan() {
//    facade.createShip(Double.NaN, 200, 10, -10, 20, -Math.PI);
//  }
//  
//  @Test(expected=ModelException.class)
//  public void testCreateShipRadiusNegative() {
//    facade.createShip(100, 200, 10, -10, -20, -Math.PI);
//  }
//  
//  @Test
//  public void testMove() {
//    IShip ship = facade.createShip(100, 100, 30, -15, 20, 0);
//    facade.move(ship, 1);
//    assertEquals(130, facade.getX(ship), Util.EPSILON);
//    assertEquals(85, facade.getY(ship), Util.EPSILON);
//  }
//}
