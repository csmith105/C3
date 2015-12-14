package c3.core;

/**
 * Base class for all game Entities.
 * 
 * @author Cody
 */
public abstract class Entity {

	/**
	 * Next Entity id to be issued
	 */
	private static long nextId = 1;
	
	/**
	 * This Entity's id
	 */
	public final long id;
	
	// By default Entities will tick
	private boolean isTicking = true;
	
	// Entities start as inactive
	private boolean isActive = false;
	
	// TODO Component list
		// Add component method
		// Remove component method
	
	public Entity() {
		
		// Get an unused Entity id
		id = nextId++;
	}
	
	/**
	 * Activate this Entity and all of it's components, effectively adding it to the engine.
	 */
	protected final void activate() {
		
		// If this Entity is already active, do nothing
		if(isActive) return;
		
		// TODO Add this Entity to the Entity list
			// Can this be done safely during ticks given how for-each loops work?
		
		// TODO Activate each component
		
		// If this Entity is meant to be ticking, start / resume ticking
		if(isTicking) addToTickList();
		
		isActive = true;
	}
	
	/**
	 * Deactivate this Entity and all of it's components, effectively removing it from the engine.
	 */
	protected final void deactivate() {
		
		// If this Entity is already inactive, do nothing
		if(!isActive) return;
		
		// TODO Remove this Entity form the Entity list
			// Can this be done safely during ticks given how for-each loops work?
		
		// TODO Deactivate each component
		
		// If this Entity is currently ticking, stop ticking 
		if(isTicking) removeFromTickList();
		
		isActive = false;
	}
	
	/**
	 * Start ticking this Entity.
	 */
	protected final void startTicking() {
		addToTickList();
		isTicking = true;
	}
	
	/**
	 * Stop ticking this Entity
	 */
	protected final void stopTicking() {
		removeFromTickList();
		isTicking = false;
	}
	
	private final void addToTickList() {
		
		// TODO Add this Entity to the tick list
			// Can this be done safely during ticks given how for-each loops work?
	}
	
	private final void removeFromTickList() {
		
		// TODO Remove this Entity from the tick list
			// Can this be done safely during ticks given how for-each loops work?
	}
	
	/**
	 * Called once every frame if this entity is on the tick list.
	 * 
	 * @param deltaTime Amount of elapsed time in seconds between this tick cycle and the last
	 */
	public void tick(float deltaTime) {
		// Do nothing base tick method
	}
	
}