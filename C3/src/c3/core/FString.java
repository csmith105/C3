package c3.core;

import java.math.BigInteger;
import java.util.Hashtable;

/**
 * FString is a string wrapper that uses a hash value for fast direct comparison
 * 
 * @author Cody
 */
public class FString {

	// Used to ensure no two strings ever hash to the same value
	public static Hashtable<BigInteger, String> hashesInUse = new Hashtable<BigInteger, String>();
	
	private final String string;
	private final BigInteger hash;
	
	public FString(String string) {
		this.string = string;
		hash = fnv1a64(string);
	}
	
	@Override
	public String toString() {
		return string + " (" + hash.toString() + ")";
	}
	
	/**
	 * Gets the actual string value for this FString
	 * 
	 * @return the string value for this FString
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * Gets the hash for this FString as a BigInteger
	 * @return the hash for this FString
	 */
	public BigInteger getHash() {
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof FString && ((FString) obj).getHash().equals(hash));
	}
	
	@Override
    public int hashCode() {
		// This is a down conversion from BigInteger to integer
        return (int) hash.intValue();
    }
	
	private static final BigInteger PRIME64 = new BigInteger("100000001b3", 16);
	private static final BigInteger MOD64 = new BigInteger("2").pow(64);
	
	// TODO move this to a math library
	private static BigInteger fnv1a64(String string) {
		
		// See http://github.com/jakedouglas/fnv-java/
		BigInteger hash = new BigInteger("cbf29ce484222325", 16);

	    for(char c : string.toCharArray()) {
	    	hash = hash.xor(BigInteger.valueOf((int) c & 0xff));
	    	hash = hash.multiply(PRIME64).mod(MOD64);
	    }

	    return hash;
	    
	}

}
