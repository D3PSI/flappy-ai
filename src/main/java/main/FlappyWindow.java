package main;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Defines the window handler class
 * @author d3psi
 */
public class FlappyWindow {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	
	private long window;

	/**
	 * Runs the application
	 */
	public void run() {
		try {
			init();
			loop();
		} catch (Exception e_) {
			e_.printStackTrace();
		}
	}

	/**
	 * Initializes the application
	 * @throws Exception Thrown when failed to initialize application
	 */
	private void init() throws Exception {
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(
				WIDTH, 
				HEIGHT, 
				"Flappy AI - Genetic Algorithm", 
				NULL, 
				NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});

		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); 
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2);
		} 

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	/**
	 * Defines the main loop for the application
	 * @throws Exception Thrown when failed to initialize user defined objects
	 */
	private void loop() throws Exception {
		GL.createCapabilities();
		GLRenderer.init(window);

		while (!glfwWindowShouldClose(window)) {
			GLRenderer.render(window);
			glfwPollEvents();
		}
	}
	
	/**
	 * Defines the entry point for the application
	 * @param args Command Line arguments
	 */
	public static void main(String[] args) {
		new FlappyWindow().run();
	}

}