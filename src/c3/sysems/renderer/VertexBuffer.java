package c3.sysems.renderer;

import java.io.Closeable;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import c3.core.Console;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer implements Closeable, AutoCloseable {

	protected int bufferHandle = 0;
	
	public VertexBuffer() {
		
		// Generate buffer
		bufferHandle = glGenBuffers();
			
		Renderer.hasOpenGLErrorOccurred();
		
	}
	
	public VertexBuffer bind() {
		
		if(bufferHandle != 0) {
			
			glBindBuffer(GL_ARRAY_BUFFER, bufferHandle);
			
			Renderer.hasOpenGLErrorOccurred();
			
		}
		
		return this;
		
	}
	
	public VertexBuffer unbind() {
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return this;
		
	}
	
	public VertexBuffer setData(float[] data) {
			
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
	    setData(buffer);
		
		return this;
	    
	}
	
	public VertexBuffer setData(FloatBuffer buffer) {
		
		if(bufferHandle != 0) {
			
			bind();
			
		    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		    
		} else {
			Console.log(Console.WARNING, "Attempted to set data on a non-allocated VBO");
		}
		
		return this;
	    
	}
	
	@Override
	public void close() {
		
		// Only delete this buffer if its been generated
		if(bufferHandle != 0) {
			
			// Delete buffer
			glDeleteBuffers(bufferHandle);
			
			Renderer.hasOpenGLErrorOccurred();
			
		} else {
			Console.log(Console.WARNING, "Attempted to close a non-allocated VBO");
		}
		
	}
	
}