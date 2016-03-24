package c3.assets;

import java.io.File;

import c3.core.Console;
import c3.core.FString;
import c3.core.Utility;
import c3.exceptions.AssetLoadException;

public class ModelAsset extends Asset {

	public static void indexAllAssets() {
		
		Console.log("Indexing all Model assets...");
		
		File[] files = Utility.getFilesWithExtension("./models/", ".pmodel");
		
		if(files == null) return;
		
		for(File file : files) {
			
			FString name = new FString(file.getName());
			
			if(Asset.exists(name)) continue;
			
			new VertexShaderAsset(name, new FString(file.getPath()));
			
		}
				
	}
	
	public ModelAsset(FString name, FString path) {
		
		super(name, path);
		
		Console.log("Found new model asset: " + name);
		
	}

	@Override
	public void load() throws AssetLoadException {
		
		// TODO protocol buffer load here
		
		// Load the shader from file
		/*try {
			assetData = Utility.getFileAsString(path);
		} catch (IOException e) {
			throw new AssetLoadException(this, "Failed to load asset from file: " + path);
		}*/
		
	}

	@Override
	public void unload() {
		
		// TODO specify data format, then unload
		
	}
	
}
