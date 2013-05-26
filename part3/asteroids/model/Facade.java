package asteroids.model;

import java.io.*;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;
import org.antlr.v4.runtime.*;

import asteroids.CollisionListener;
import asteroids.IFacade;
import asteroids.ModelException;
import asteroids.model.statements.*;
import asteroids.model.types.*;
import asteroids.model.expressions.*;
import asteroids.model.programs.parsing.*;

public class Facade implements IFacade<World, Ship, Asteroid, Bullet, Program> {

	@Override
	public World createWorld(double width, double height) {
		try{
			return new World(width, height);
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getWorldWidth(World world) {
		try{
			return world.getWidth();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getWorldHeight(World world) {
		try {
			return world.getHeight();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public Set<Ship> getShips(World world) {
		try {
			return world.getShips();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public Set<Asteroid> getAsteroids(World world) {
		try {
			return world.getAsteroids();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public Set<Bullet> getBullets(World world) {
		try {
			return world.getBullets();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void addShip(World world, Ship ship) {
		try{
			world.addAsSpatialElement(ship);
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void addAsteroid(World world, Asteroid asteroid) {
		try {
			world.addAsSpatialElement(asteroid);
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void removeShip(World world, Ship ship) {
		try {
			world.removeAsSpatialElement(ship);
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void removeAsteroid(World world, Asteroid asteroid) {
		try {
			world.removeAsSpatialElement(asteroid);
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void evolve(World world, double dt,
			CollisionListener collisionListener) {
		try{
			world.evolve(dt, collisionListener);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity,
			double yVelocity, double radius, double direction, double mass) {
		try{
			return new Ship(new Vector2D(x,y), direction, radius, new Vector2D(xVelocity,yVelocity), mass);
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}
	
	@Override
	public boolean isShip(Object o) {
		try{
			boolean result = ((SpatialElement) o).isShip();
			return result;
		} catch(Exception exc) {
			return false;
		}
	}

	@Override
	public double getShipX(Ship ship) {
		try {
			return ship.getPosition().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipY(Ship ship) {
		try {
			return ship.getPosition().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipXVelocity(Ship ship) {
		try {
			return ship.getVelocity().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipYVelocity(Ship ship) {
		try {
			return ship.getVelocity().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipRadius(Ship ship) {
		try {
			return ship.getRadius();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipDirection(Ship ship) {
		try {
			return ship.getAngle();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getShipMass(Ship ship) {
		try {
			return ship.getMass();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public World getShipWorld(Ship ship) {
		try {
			return ship.getWorld();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) {
		try {
			return ship.isThrusterActive();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) {
		try {
			ship.setThrusterActive(active);
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void turn(Ship ship, double angle) {
		try {
			ship.turn(angle);
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public void fireBullet(Ship ship) {
		try {
			ship.fireBullet();
		}
		catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) {
		try{
			return new Asteroid(new Vector2D(x,y), radius, new Vector2D(xVelocity,yVelocity));
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius, Random random) {
		try{
			return new Asteroid(new Vector2D(x,y), radius, new Vector2D(xVelocity,yVelocity));
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	@Override
	public boolean isAsteroid(Object o) {
		try{
			boolean result = ((SpatialElement) o).isAsteroid();
			return result;
		} catch(Exception exc) {
			return false;
		}
	}

	@Override
	public double getAsteroidX(Asteroid asteroid) {
		try {
			return asteroid.getPosition().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getAsteroidY(Asteroid asteroid) {
		try {
			return asteroid.getPosition().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getAsteroidXVelocity(Asteroid asteroid) {
		try {
			return asteroid.getVelocity().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getAsteroidYVelocity(Asteroid asteroid) {
		try {
			return asteroid.getVelocity().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) {
		try {
			return asteroid.getRadius();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) {
		try {
			return asteroid.getMass();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) {
		try {
			return asteroid.getWorld();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public boolean isBullets(Object o) {
		try{
			boolean result = ((SpatialElement) o).isBullet();
			return result;
		} catch(Exception exc) {
			return false;
		}
	}

	@Override
	public double getBulletX(Bullet bullet) {
		try {
			return bullet.getPosition().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getBulletY(Bullet bullet) {
		try {
			return bullet.getPosition().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getBulletXVelocity(Bullet bullet) {
		try {
			return bullet.getVelocity().getXComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getBulletYVelocity(Bullet bullet) {
		try{
			return bullet.getVelocity().getYComponent();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getBulletRadius(Bullet bullet) {
		try {
			return bullet.getRadius();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public double getBulletMass(Bullet bullet) {
		try {
			return bullet.getMass();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public World getBulletWorld(Bullet bullet) {
		try {
			return bullet.getWorld();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public Ship getBulletSource(Bullet bullet) {
		try {
			return bullet.getShip();
		} catch (Exception exc){
			throw new ModelException(exc);	
		}
	}

	@Override
	public asteroids.IFacade.ParseOutcome<Program> parseProgram(String text) {
		ProgramFactoryImpl factory = new ProgramFactoryImpl();
	    ProgramParser<E, S, T> parser = new ProgramParser<E, S, T>(factory);
	    try {
	        parser.parse(text);
	        List<String> errors = parser.getErrors();
	        if(! errors.isEmpty()) {
	          return ParseOutcome.failure(errors.get(0));
	        } else {
	          return ParseOutcome.success(new Program(parser.getGlobals(), parser.getStatement())); 
	        }
	    } catch(RecognitionException e) {
	      return ParseOutcome.failure(e.getMessage());
	    }
	}

	@Override
	public asteroids.IFacade.ParseOutcome<Program> loadProgramFromStream(
			InputStream stream) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public asteroids.IFacade.ParseOutcome<Program> loadProgramFromUrl(URL url)
			throws IOException {
		
			String result;
			  FileInputStream stream = new FileInputStream(new File(url.getPath()));
			  try {
			    FileChannel fc = stream.getChannel();
			    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			    result = Charset.defaultCharset().decode(bb).toString();
			    /* Instead of using default, pass in a decoder. */
			  }
			  finally {
			    stream.close();
			  }
			  return parseProgram(result);
	}

	@Override
	public boolean isTypeCheckingSupported() {
		return true;
	}

	@Override
	public asteroids.IFacade.TypeCheckOutcome typeCheckProgram(Program program) {
		try {
			if(program.typeCheck())
				return asteroids.IFacade.TypeCheckOutcome.success();
			else
				return asteroids.IFacade.TypeCheckOutcome.failure("Fail");
		} catch (Exception exc){
			throw new ModelException(exc);
		}
	}

	@Override
	public void setShipProgram(Ship ship, Program program) {
		try {
			ship.setProgram(program);
		} catch (Exception exc){
			throw new ModelException(exc);
		}	
	}
}
