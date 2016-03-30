package c3.sysems.renderer;

import java.io.Closeable;

import c3.core.Console;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray implements Closeable, AutoCloseable {

	protected int arrayHandle = 0;
	
	private int index;
	private int size;
	private int stride;
	private int offset;
	
	public VertexArray() {
		
		// Generate buffer
		arrayHandle = glGenVertexArrays();
			
		Renderer.hasOpenGLErrorOccurred();
		
	}
	
	public VertexArray bind() {
		
		if(arrayHandle != 0) {
			
			glBindVertexArray(arrayHandle);
			
			Renderer.hasOpenGLErrorOccurred();
			
		}
		
		return this;
		
	}
	
	public VertexArray unbind() {
		
		glBindVertexArray(0);
		
		return this;
		
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
		
	public VertexArray setup(int index, int size, int stride, int offset) {
		
		if(arrayHandle != 0) {
			
			bind();
			
			glVertexAttribPointer(index, size, GL_FLOAT, false, stride, offset);
		    
		} else {
			Console.log(Console.WARNING, "Attempted to setup non-allocated VAO");
		}
		
		return this;
	    
	}
	
	public VertexArray enable(int index) {
		
		if(arrayHandle != 0) {
			
			bind();
			
			glEnableVertexAttribArray(index);
			
		} else {
			Console.log(Console.WARNING, "Attempted to enable non-allocated VAO");
		}
		
		return this;
	    
	}
	
	@Override
	public void close() {
		
		// Only delete this buffer if its been generated
		if(arrayHandle != 0) {
			
			// Delete buffer
			glDeleteBuffers(arrayHandle);
			
			Renderer.hasOpenGLErrorOccurred();
			
		} else {
			Console.log(Console.WARNING, "Attempted to set data on a non-allocated VAO");
		}
		
	}
	
}