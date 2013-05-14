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
		return world.getWidth();
	}

	@Override
	public double getWorldHeight(World world) {
		return world.getHeight();
	}

	@Override
	public Set<Ship> getShips(World world) {
		return world.getShips();
	}

	@Override
	public Set<Asteroid> getAsteroids(World world) {
		return world.getAsteroids();
	}

	@Override
	public Set<Bullet> getBullets(World world) {
		return world.getBullets();
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
		world.evolve(dt, collisionListener);
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
		return ship.getPosition().getXComponent();
	}

	@Override
	public double getShipY(Ship ship) {
		return ship.getPosition().getYComponent();
	}

	@Override
	public double getShipXVelocity(Ship ship) {
		return ship.getVelocity().getXComponent();
	}

	@Override
	public double getShipYVelocity(Ship ship) {
		return ship.getVelocity().getYComponent();
	}

	@Override
	public double getShipRadius(Ship ship) {
		return ship.getRadius();
	}

	@Override
	public double getShipDirection(Ship ship) {
		return ship.getAngle();
	}

	@Override
	public double getShipMass(Ship ship) {
		return ship.getMass();
	}

	@Override
	public World getShipWorld(Ship ship) {
		return ship.getWorld();
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) {
		return ship.isThrusterActive();
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) {
		ship.setThrusterActive(active);
	}

	@Override
	public void turn(Ship ship, double angle) {
		ship.turn(angle);
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
		return asteroid.getPosition().getXComponent();
	}

	@Override
	public double getAsteroidY(Asteroid asteroid) {
		return asteroid.getPosition().getYComponent();
	}

	@Override
	public double getAsteroidXVelocity(Asteroid asteroid) {
		return asteroid.getVelocity().getXComponent();
	}

	@Override
	public double getAsteroidYVelocity(Asteroid asteroid) {
		return asteroid.getVelocity().getYComponent();
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) {
		return asteroid.getRadius();
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) {
		return asteroid.getMass();
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) {
		return asteroid.getWorld();
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
		return bullet.getPosition().getXComponent();
	}

	@Override
	public double getBulletY(Bullet bullet) {
		return bullet.getPosition().getYComponent();
	}

	@Override
	public double getBulletXVelocity(Bullet bullet) {
		return bullet.getVelocity().getXComponent();
	}

	@Override
	public double getBulletYVelocity(Bullet bullet) {
		return bullet.getVelocity().getYComponent();
	}

	@Override
	public double getBulletRadius(Bullet bullet) {
		return bullet.getRadius();
	}

	@Override
	public double getBulletMass(Bullet bullet) {
		return bullet.getMass();
	}

	@Override
	public World getBulletWorld(Bullet bullet) {
		return bullet.getWorld();
	}

	@Override
	public Ship getBulletSource(Bullet bullet) {
		return bullet.getShip();
	}

	@Override
	public asteroids.IFacade.ParseOutcome<Program> parseProgram(String text) {
		ProgramFactoryI factory = new ProgramFactoryI();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public asteroids.IFacade.TypeCheckOutcome typeCheckProgram(Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShipProgram(Ship ship, Program program) {
		ship.setProgram(program);
	}
}
