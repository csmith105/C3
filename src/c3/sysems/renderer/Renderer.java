package c3.sysems.renderer;

import static org.lwjgl.opengl.GL11.*;

import c3.core.Console;

public class Renderer {

	public static void checkOpenGLError() {
		
		int errorFlag = glGetError();
			
		if(errorFlag != GL_NO_ERROR) {
			Console.log(Console.ERROR, "OpenGL Error: " + errorFlag);
		}
			
	}
	
}
