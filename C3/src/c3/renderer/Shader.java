package c3.renderer;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public abstract class Shader {
	
	protected int programHandle = 0;
	
	public Shader() {
		
	}
	
	/**
	 * Compiles the supplied program into supplied type of shader, storing the resulting handle in this class. Reloads the shader if one already exists.
	 * @param program the program to be compiled and loaded
	 * @param type the type of shader to be complied and loaded
	 * @throws RuntimeException
	 */
	protected void compile(String program, int type) throws RuntimeException {
		
		// If a shader is currently loaded...
		if(programHandle > 0) {
			
			// Unload it
			unload();
			
		}
		
		try {
			
			// Attempt to create an empty vertex shader object
			programHandle = ARBShaderObjects.glCreateShaderObjectARB(type);
		     
			// Return false and die if the shader couldn't be created
		    if(programHandle == 0)
		    		throw new RuntimeException("Error getting shader handle");
		    
		    // Attempt to assign the source string to the vertex shader object
		    ARBShaderObjects.glShaderSourceARB(programHandle, program);
		    
		    // Attempt to compile the shader
		    ARBShaderObjects.glCompileShaderARB(programHandle);
		    
		    // Did the shader compile successfully?
		    if(ARBShaderObjects.glGetObjectParameteriARB(programHandle, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
		    		throw new RuntimeException("Error compiling shader: " + ARBShaderObjects.glGetInfoLogARB(programHandle, ARBShaderObjects.glGetObjectParameteriARB(programHandle, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
		    
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
		if(programHandle > 0) {
			
			// Mark the shader for deletion
			ARBShaderObjects.glDeleteObjectARB(programHandle);
			
		}
		
	}

}