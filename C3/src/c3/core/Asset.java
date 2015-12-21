package c3.core;

import java.util.Hashtable;

import c3.assets.VertexShaderAsset;
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
	
	public static Asset get(FString name) {
		return assetList.get(name);
	}
	
	public static boolean exists(FString name) {
		return assetList.containsKey(name);
	}
	
	public static void indexAllAssets() {
		
		Console.log("Indexing all assets...");
		
		VertexShaderAsset.indexAllAssets();
	}
	
	protected final FString name, path;
	
	protected Asset(FString name, FString path) {
		
		this.path = path;
		this.name = name;
		
		// Add this asset to the assetList
		assetList.put(this.name, this);
		
	}
	
	/**
	 * Gets the path of this asset
	 * 
	 * @return the path of this asset
	 */
	protected FString getPath() {
		return this.path;
	}
	
	/**
	 * Called either internally when an asset is requested but not loaded or prematurely by the system during level load.
	 */
	public abstract void load() throws AssetLoadException;
	
	public abstract void unload();
	
}