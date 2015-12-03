package c3.engine;

import java.util.Hashtable;

/**
 * Assets represent resources (files) outside the engine. All Assets are immutable.
 * 
 * Each type (SharderAsset, TextureAsset) of asset will subclass this class. Each type
 * will load in it's respective assets from the filesystem into the master list provided
 * here once and only once at runtime, using static calls. This will create effectively
 * a list of singleton assets.
 * 
 * @author Cody
 */
public abstract class Asset {
	
	/**
	 * Master list of all assets known to the game engine.
	 */
	protected static Hashtable<FString, Asset> assetList = new Hashtable<FString, Asset>();
	
	private final UUID id;
	private final FString path;
	private final FString name;
	
	// TODO Create an asset hash table
		// Create auto-binding / unbinding to the list
		// Create ways to search the list for an asset by name
	
	public static void reindexAllAssets() {
		
	}
	
	protected Asset(String path, String name) {
		
		id = new UUID();
		this.path = new FString(path);
		this.name = new FString(name);
		
	}


}
