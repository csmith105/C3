package c3.sysems.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Program {

	protected int programHandle = 0;
	
	private VertexShader vertexShader;
	private FragmentShader fragmentShader;
	
	public Program(VertexShader vertexShader, FragmentShader fragmentShader) {
		this.vertexShader = vertexShader;
		this.fragmentShader = fragmentShader;
	}
	
	public void compile() {
		
		// If a shader is currently loaded...
		if(programHandle > 0) {
			
			// do nothing
			return;
			
		}
		
		vertexShader.compile();
		fragmentShader.compile();
		
		try {
			
			// Attempt to create an empty program object
			programHandle = GL20.glCreateProgram();
		     
			// Return false and die if the shader couldn't be created
		    if(programHandle == 0)
		    		throw new RuntimeException("Error getting program handle");
		    
		    // Add vertex shader
		    GL20.glAttachShader(programHandle, vertexShader.getHandle());
		    
		    // Add fragment shader
		    GL20.glAttachShader(programHandle, fragmentShader.getHandle());
		    
		    // Attempt to compile the program
		    GL20.glLinkProgram(programHandle);
		    
		    	// Did the shader compile successfully?
		    final int programStatus = GL20.glGetProgrami(programHandle, GL20.GL_LINK_STATUS);
		    
		    if(programStatus == GL11.GL_FALSE)
		    		throw new RuntimeException("Error compiling program: " + GL20.glGetProgramInfoLog(programHandle, 100));
		    
		    // Yes it did, return :)
		    return;
		    
		} catch(Exception e) {
			
		    unload();
		    
		    throw e;
		    
		}
		
	}
	
	public void unload() {
		
		// If the handle is valid...
		if(programHandle > 0) {
			
			// Mark the shader for deletion
			GL20.glDeleteProgram(programHandle);
			
		}
		
	}
	
}
