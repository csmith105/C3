package c3.core;

import java.util.Hashtable;

import c3.exceptions.AssetLoadException;

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
	
	public static Asset getByName(FString name) {
		return assetList.get(name);
	}
	
	public static void reindexAllAssets() {
		// TODO implement this
	}
	
	protected final String path;
	protected final FString name;
	
	protected Asset(String name, String path) {
		
		this.path = path;
		this.name = new FString(name);
		
		// Add this asset to the assetList
		assetList.put(this.name, this);
		
	}
	
	/**
	 * Gets the path of this asset
	 * 
	 * @return the path of this asset
	 */
	protected String getPath() {
		return this.path;
	}
	
	/**
	 * Called either internally when an asset is requested but not loaded or prematurely by the system during level load.
	 */
	public abstract void load() throws AssetLoadException;
	
	public abstract void unload();
	
}