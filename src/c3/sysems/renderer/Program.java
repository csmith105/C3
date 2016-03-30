package c3.sysems.renderer;

import java.io.Closeable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import c3.core.Console;

public class Program implements Closeable, AutoCloseable {

	protected int programHandle = 0;
	
	private VertexShader vertexShader;
	private FragmentShader fragmentShader;
	
	private boolean compiled = false;
	private boolean needsRecompiled = false;
	
	public Program() {
		
		// Attempt to create an empty program object
		programHandle = glCreateProgram();
		
		Renderer.hasOpenGLErrorOccurred();
		
	}
	
	public Program setShader(VertexShader vertexShader) {
		
		if(this.vertexShader != vertexShader) {
			
			this.vertexShader = vertexShader;
			
			needsRecompiled = true;
			
		}
		
		return this;
		
	}
	
	public Program setShader(FragmentShader fragmentShader) {
		
		if(this.fragmentShader != fragmentShader) {
			
			this.fragmentShader = fragmentShader;
			
			needsRecompiled = true;
			
		}
		
		return this;
		
	}
	
	public boolean isCompiled() {
		
		return compiled;
		
	}
	
	public boolean needsRecompiled() {
		
		return needsRecompiled;
		
	}
	
	public Program compile() {
		
		// If the handle is null
		if(programHandle == 0) {
			
			// Attempt to create an empty program object
			programHandle = glCreateProgram();
			
			Renderer.hasOpenGLErrorOccurred();
			
		}
		
		// Compile shaders
		vertexShader.compile();
		fragmentShader.compile();
	    
	    // Add vertex / fragment shader
	    glAttachShader(programHandle, vertexShader.getHandle());
	    glAttachShader(programHandle, fragmentShader.getHandle());
	    
	    // Attempt to compile the program
	    glLinkProgram(programHandle);
	    
	    	// Did the shader compile successfully?
	    if(glGetProgrami(programHandle, GL_LINK_STATUS) == GL_TRUE) {
	    		
	    		compiled = true;
	    		needsRecompiled = false;
	    		
	    } else {
	    	
	    		Console.log(Console.WARNING, "Program failed to compile");
	    	
	    }
		
	    return this;
		
	}
	
	@Override
	public void close() {
		
		// If the handle is valid
		if(programHandle != 0) {
			
			// Release the program handle
			glDeleteProgram(programHandle);
			
			// Set the program handle to null
			programHandle = 0;
			
		}
		
	}
	
}