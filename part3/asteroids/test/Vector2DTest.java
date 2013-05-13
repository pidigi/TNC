package asteroids.test;

import static org.junit.Assert.*;
import org.junit.*;
import static asteroids.Util.*;
import asteroids.model.*;


public class Vector2DTest {

	/**
	 * Variable referencing a two dimensional vector with
	 * components 50 and 100 for the x- and y- component, respectively.
	 */
	private static Vector2D Vector2DX50Y100;

	/**
	 * Variable referencing a two dimensional vector with 
	 * components 10 and 3 for the x- and y- component, respectively.
	 */
	private static Vector2D Vector2DX10Y3;

	/**
	 * Set up an immutable test fixture.
	 * 
	 * @post The variable Vector2DX50Y100 references a new two dimensional
	 *       vector with x-component 50 and y-component 100.
	 * @post The variable Vector2DX10Y3 references a new two dimensional vector
	 *       with x-component 10 and y-component 3.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() {
		Vector2DX50Y100 = new Vector2D(50, 100);
		Vector2DX10Y3 = new Vector2D(10, 3);
	}

	@Test
	public final void constructor_NormalCase() {
		Vector2D newVector2D = new Vector2D(1, 2);
		assertEquals(1, newVector2D.getXComponent(),EPSILON);
		assertEquals(2, newVector2D.getYComponent(),EPSILON);
	}

	@Test
	public final void getNorm_SingleCase() {
		assertEquals(Math.sqrt(109), Vector2DX10Y3.getNorm(),EPSILON);
	}

	@Test
	public final void subtract_SingleCase() {
		Vector2D subtractResult = Vector2DX50Y100.subtract(Vector2DX10Y3);
		assertEquals(40, subtractResult.getXComponent(),EPSILON);
		assertEquals(97, subtractResult.getYComponent(),EPSILON);
	}

//	@Test
	public final void add_SingleCase() {
		Vector2D addResult = Vector2DX50Y100.add(Vector2DX10Y3);
		assertEquals(60, addResult.getXComponent(),EPSILON);
		assertEquals(103, addResult.getYComponent(),EPSILON);
	}

	@Test
	public final void getDotProduct_SingleCase() {
		assertEquals(800,
				Vector2DX50Y100.getDotProduct(Vector2DX10Y3),EPSILON);
	}

	@Test
	public final void multiply_SingleCase() {
		Vector2D multiplyResult = Vector2DX50Y100.multiply(2);
		assertEquals(100, multiplyResult.getXComponent(),EPSILON);
		assertEquals(200, multiplyResult.getYComponent(),EPSILON);
	}

	@Test
	public final void getDirection_SingleCase() {
		Vector2D directionResult = Vector2DX50Y100.getDirection();
		assertEquals(
				Vector2DX50Y100.multiply(1 / Vector2DX50Y100.getNorm())
						.getXComponent(), directionResult.getXComponent(),EPSILON);
		assertEquals(
				Vector2DX50Y100.multiply(1 / Vector2DX50Y100.getNorm())
						.getYComponent(), directionResult.getYComponent(),EPSILON);
	}

	@Test
	public final void toString_SingleCase() {
		String stringResult = Vector2DX50Y100.toString();
		String expectedString = "(50.0,100.0)";
		assertEquals(expectedString, stringResult);
	}

	@Test
	public final void equals_TrueCase() {
		Vector2D Vector2DX50Y100_2 = new Vector2D(50, 100);
		boolean trueResult = Vector2DX50Y100.equals(Vector2DX50Y100_2);
		assertTrue(trueResult);
	}
	
	@Test
	public final void equals_TrueCasePerturbed() {
		Vector2D Vector2DX50Y100_2 = new Vector2D(50 + 1e-15, 100 + 1e-13);
		boolean trueResult = Vector2DX50Y100.equals(Vector2DX50Y100_2);
		assertTrue(trueResult);
	}

	@Test
	public final void equals_TrueCaseSame() {
		boolean trueResult = Vector2DX50Y100.equals(Vector2DX50Y100);
		assertTrue(trueResult);
	}

	@Test
	public final void equals_FalseCase() {
		boolean falseResult = Vector2DX50Y100.equals(Vector2DX10Y3);
		assertFalse(falseResult);
	}

	 @Test
	 public final void hashCode_SingleCase(){
		 int obtainedHash = Vector2DX50Y100.hashCode();
		 assertEquals(-2136866816,obtainedHash);	
	 }
}
