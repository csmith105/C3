package c3.core;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
	
	// The window handle
    private long windowHandle;
    
    // We need to strongly reference callback instances.
    private GLFWKeyCallback keyCallback;
    
    private int width;
    private int height;
    
    private String title;
    
    private boolean resizeable;
    
    private GLFWErrorCallback errorCallback;
    
	public Window() {
		width = 800;
		height = 600;
		title = "New Window";
		resizeable = true;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setKeyCallback(GLFWKeyCallback keyCallback) {
		this.keyCallback = keyCallback;
	}
	
	public void setResizable(boolean resizable) {
		this.resizeable = resizable;
	}
	
	public long getHandle() {
		return this.windowHandle;
	}
	
	public void init() {
		
		// Setup an error callback
        glfwSetErrorCallback(errorCallback = new GLFWErrorCallback() {

			@Override
			public void invoke(int error, long description) {
				
				Console.log(Console.ERROR, "GLFW Error: " + error + ", " + description);
				
			}
        	
        });
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if(glfwInit() != GLFW_TRUE) {
        		throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Configure the window
        
        // (optional) The current window hints are already the default
        glfwDefaultWindowHints(); 
        
        // The window will stay hidden after creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
        
        // The window will be resizable
        glfwWindowHint(GLFW_RESIZABLE, (resizeable) ? GLFW_TRUE : GLFW_FALSE); 
 
        // Create the window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        
        if(windowHandle == NULL) {
        		throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowHandle, keyCallback);
 
        // Get the resolution of the primary monitor
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Center our window
        glfwSetWindowPos(windowHandle, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(windowHandle);
        
        // Window resize callback
        glfwSetFramebufferSizeCallback(windowHandle, new GLFWFramebufferSizeCallback() {
    		
	    		@Override
	    	    public void invoke(long window, int width, int height) {
	    			GL11.glViewport(0, 0, width, height);
	    		}
	    		
	    	});
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
		
	}
	
	public void destroy() {
		
		// Release window and window callbacks
        glfwDestroyWindow(windowHandle);
        
        errorCallback.release();
        
	}
	
}
