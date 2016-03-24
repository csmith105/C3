package c3.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public abstract class Utility {

	public static String getFileAsString(FString path) throws IOException {
		
		StringBuilder vertexCode = new StringBuilder();
		String line = null ;
		
		BufferedReader reader = new BufferedReader(new FileReader(path.getString()));
		    
		try {
			
			while((line = reader.readLine()) != null) {
				vertexCode.append(line);
				vertexCode.append('\n');
			}
			
		} catch (IOException e) {
			
			reader.close();
			
			// Rethrow the exception
			throw e; 
			
		}
 
		reader.close();
		
		return vertexCode.toString();
		
	}

	public static File[] getFilesWithExtension(String path, String extension) {
		
		File dir = new File(path);

	    	return dir.listFiles(new FilenameFilter() {
	    		
	    		public boolean accept(File dir, String filename) {
	    			
	    			return filename.endsWith(extension);
	    			
	    		}
	    		
	    	});

    }
	
}





