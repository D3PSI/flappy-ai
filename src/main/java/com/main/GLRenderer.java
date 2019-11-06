package com.main;

import org.lwjgl.opengl.*;

import com.buffers.Model2D;
import com.shaders.FragmentShader;
import com.shaders.ShaderPipeline;
import com.shaders.VertexShader;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Defines a static utility class to handle rendering operations
 * @author d3psi
 *
 */
public class GLRenderer {

	private static ShaderPipeline pipe;
	
	static float vertices[] = {
		    -0.5f, -0.5f, 0.0f,
		     0.5f, -0.5f, 0.0f,
		     0.0f,  0.5f, 0.0f
		};  
	static Model2D quad;
	
	/**
	 * Initializes shaders and buffers
	 * @param window_ GLFW's window
	 * @throws Exception Thrown when failed to initialize shaders and buffers
	 */
	public static void init(long window_) throws Exception {
		VertexShader vs = new VertexShader("shaders/main.vert");
		FragmentShader fs = new FragmentShader("shaders/main.frag");
		pipe = new ShaderPipeline();
		pipe.vs(vs);
		pipe.fs(fs);

		quad = new Model2D(vertices, 3);
		quad.addVertexArray(0, 3, 0, 0);
		quad.bindVAO(0);
		quad.set();
		quad.unbindVBO();
		quad.unbindVAO();
		
		GL45.glClearColor(0.3f, 0.1f, 0.2f, 0.0f);
	}
	
	/**
	 * Coordinates main rendering operations
	 * @param window_ GLFW's window
	 */
	public static void render(long window_) {
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		
		pipe.bind();
		quad.bindVAO(0);
		quad.draw();
		
		glfwSwapBuffers(window_);
	}

	/**
	 * Handles cleaning of resources on shutdown
	 * @param window_ GLFW's window
	 */
	public static void clean(long window_) {

		quad.clean();
		pipe.clean();
		
		glfwFreeCallbacks(window_);
		glfwDestroyWindow(window_);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
}
