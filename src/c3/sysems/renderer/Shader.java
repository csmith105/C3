package c3.sysems.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {
	
	protected int shaderHandle = 0;
	
	/**
	 * Compiles the supplied program into supplied type of shader, storing the resulting handle in this class. Reloads the shader if one already exists.
	 * @param program the program to be compiled and loaded
	 * @param type the type of shader to be complied and loaded
	 * @throws RuntimeException
	 */
	protected void compile(String program, int type) throws RuntimeException {
		
		// If a shader is currently loaded...
		if(shaderHandle > 0) {
			
			// do nothing
			return;
			
		}
		
		try {
			
			// Attempt to create an empty vertex shader object
			shaderHandle = GL20.glCreateShader(type);
		     
			// Return false and die if the shader couldn't be created
		    if(shaderHandle == 0) {
		    		throw new RuntimeException("Error getting shader handle");
		    }
		    
		    // Attempt to assign the source string to the vertex shader object
		    GL20.glShaderSource(shaderHandle, program);
		    
		    // Attempt to compile the shader
		    GL20.glCompileShader(shaderHandle);
		    
		    	// Did the shader compile successfully?
		    final int shaderStatus = GL20.glGetShaderi(shaderHandle, GL20.GL_COMPILE_STATUS);
		    
		    if(shaderStatus == GL11.GL_FALSE) {
		    		throw new RuntimeException("Error compiling program: " + GL20.glGetProgramInfoLog(shaderHandle, 100));
		    }
		    		
		    // Yes it did, return :)
		    return;
		    
		} catch(Exception e) {
			
		    unload();
		    
		    throw e;
		    
		}
		
	}
	
	/**
	 * Mark this shader for deletion from the graphics card.
	 */
	public void unload() {
		
		// If the handle is valid...
		if(shaderHandle > 0) {
			
			// Mark the shader for deletion
			GL20.glDeleteShader(shaderHandle);
			
		}
		
	}
	
	public int getHandle() {
		
		return this.shaderHandle;
		
	}

}