package c3.core;

import org.lwjgl.glfw.*;

import c3.assets.Asset;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
 
public class C3 {
	
	/**
	 * Debug flag
	 */
	private static boolean debug = false;
	
	public static boolean isDebug() {
		return debug;
	}
	
	private static void processArguments(String[] arguments) {
		
		for(int i = 0; i < arguments.length; ++i) {
			
			switch(arguments[i]) {
			
				case "debug":
					debug = true;
					break;
			
			}
			
		}
		
	}
 
    public void run() {
    	
    		final Window window = new Window();
    		
    		GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
					
				if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
						
					// we will detect this in our rendering loop
    					glfwSetWindowShouldClose(window, GLFW_TRUE); 
    						
    				}  
    					
    			}
                	
        	};
        		
        	window.setKeyCallback(keyCallback);

        	window.init();
        	
        	loop(window);
        	
        	window.destroy();
        	
        	keyCallback.release();
        	
        	// Terminate GLFW and release the GLFWErrorCallback
        	glfwTerminate();
        
    }
 
    private void loop(Window window) {
    	
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
 
        // Render loop
        while(glfwWindowShouldClose(window.getHandle()) == GLFW_FALSE) {
        	
        		// clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
 
            // swap the color buffers
            glfwSwapBuffers(window.getHandle()); 
 
            // Poll for window / key events.
            glfwPollEvents();
            
        }
        
    }
 
    public static void main(String[] arguments) {
    		
    		// Process command line arguments before running the engine
    		C3.processArguments(arguments);
    		
    		Asset.indexAllAssets();
    		
    		// Run the engine
        new C3().run();
        
    }
 
}