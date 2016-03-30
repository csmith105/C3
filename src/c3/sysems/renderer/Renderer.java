package c3.sysems.renderer;

import static org.lwjgl.opengl.GL11.*;

import c3.core.Console;
import c3.core.Window;

public class Renderer {
	
	final private Window window;
	
	public Renderer(Window window) {
		this.window = window;
	}
	
	public void draw() {
		
	}

	public static boolean hasOpenGLErrorOccurred() {
		
		int errorFlag = glGetError();
			
		if(errorFlag != GL_NO_ERROR) {
			
			Console.log(Console.WARNING, "OpenGL Error: " + errorFlag);
			
			return true;
			
		}
		
		return false;
			
	}
	
}
