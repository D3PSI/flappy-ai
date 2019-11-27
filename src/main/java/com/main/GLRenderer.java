package com.main;

import org.lwjgl.opengl.*;

import com.models.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Defines a static utility class to handle rendering operations
 * @author d3psi
 */
public class GLRenderer {
	
	static Bird bird;
	
	/**
	 * Initializes shaders and buffers
	 * @param window_ GLFW's window
	 * @throws Exception Thrown when failed to initialize shaders and buffers
	 */
	public static void init(final long window_) throws Exception {
		GL45.glEnable(GL45.GL_TEXTURE_2D);
		bird = new Bird(new String[]{
            "res/textures/bird1.png",
            "res/textures/bird2.png",
            "res/textures/bird3.png"
        });
	}

	/**
	 * Coordinates main rendering operations
	 * 
	 * @param window_ GLFW's window
	 */
	public static void render(final long window_) {
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		bird.draw(0);
	}

	/**
	 * Handles cleaning of resources on shutdown
	 * 
	 * @param window_ GLFW's window
	 */
	public static void clean(final long window_) {
		glfwFreeCallbacks(window_);
		glfwDestroyWindow(window_);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
}
