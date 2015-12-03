package c3.engine;

/**
 * This object creates and maintains an immutable unique identifier.
 * 
 * @author Cody
 */
public class UUID {

	// Next ID to be issued
	static long nextId = 0;
	
	// This UUID's id
	private long id;
	
	public UUID() {
		// Super simple UUID generator, will almost certainly be replaced later
		// when we implement any kind of client/server model
		id = nextId++;
	}
	
	/**
	 * Returns the raw id
	 * 
	 * @return The raw id in the form of a long
	 */
	public long getRawID() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof UUID && ((UUID) obj).getRawID() == id);
	}
	
	@Override
    public int hashCode() {
        return (int) id;
    }

}