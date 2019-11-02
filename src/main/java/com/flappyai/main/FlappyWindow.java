package com.flappyai.main;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.flappyai.shaders.FragmentShader;
import com.flappyai.shaders.ShaderPipeline;
import com.flappyai.shaders.VertexShader;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Defines the window handler class
 * @author d3psi
 */
public class FlappyWindow {

	private long window;
	private ShaderPipeline pipe;

	/**
	 * Runs the application
	 */
	public void run() {
		try {
			init();
			loop();
			clean();
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

		window = glfwCreateWindow(1080, 720, "Hello World!", NULL, NULL);
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
				(vidmode.height() - pHeight.get(0)) / 2
			);
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
		
		shaders();
		buffers();
	
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}
	
	/**
	 * Handles cleaning of resources on shutdown
	 */
	private void clean() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
		pipe.clean();
	}

	/**
	 * Initializes shaders
	 * @throws Exception Thrown when failed to initialize shaders
	 */
	private void shaders() throws Exception {
		VertexShader vs = new VertexShader("shaders/main.vert");
		FragmentShader fs = new FragmentShader("shaders/main.frag");
		pipe = new ShaderPipeline();
		pipe.vs(vs);
		pipe.fs(fs);
	}
	
	/**
	 * Initializes buffers
	 * @throws Exception Thrown when failed to initialize buffers
	 */
	private void buffers() throws Exception {
		
	}
	
	/**
	 * Defines the entry point for the application
	 * @param args Command Line arguments
	 */
	public static void main(String[] args) {
		new FlappyWindow().run();
	}

}