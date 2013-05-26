package asteroids.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses( { ShipTest.class, WorldTest.class,
	Vector2DTest.class})
public class AllTests {
}