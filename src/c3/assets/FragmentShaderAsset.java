package c3.assets;

import java.io.File;
import java.io.IOException;

import c3.core.Console;
import c3.core.FString;
import c3.core.Utility;
import c3.exceptions.AssetLoadException;

public class FragmentShaderAsset extends Asset {
	
	public static void indexAllAssets() {
		
		Console.log("Indexing all Vertex Shader assets...");
		
		File[] files = Utility.getFilesWithExtension("./shaders/vertex/", ".vert");
		
		if(files == null) return;
		
		for(File file : files) {
			
			FString name = new FString(file.getName());
			
			if(Asset.exists(name)) continue;
			
			new VertexShaderAsset(name, new FString(file.getPath()));
			
		}
				
	}
	
	private String assetData;

	public FragmentShaderAsset(FString name, FString path) {
		
		super(name, path);
		
		Console.log("Found new Fragment asset: " + name);
		
	}

	@Override
	public void load() throws AssetLoadException {
		
		// Load the shader from file
		try {
			assetData = Utility.getFileAsString(path);
		} catch (IOException e) {
			throw new AssetLoadException(this, "Failed to load asset from file: " + path);
		}
		
	}

	@Override
	public void unload() {
		
		// Dump the shader string
		assetData = null;
		
	}
	
	public String getData() {
		return assetData;
	}

}