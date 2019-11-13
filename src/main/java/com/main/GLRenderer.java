package com.main;

import org.lwjgl.opengl.*;

import com.models.Model2D;
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
	
	static float[]  vertices    = new float[]{
            0.5f,  0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
           -0.5f, -0.5f, 0.0f, 
           -0.5f,  0.5f, 0.0f
            }; 
    static int[]    indices     = new int[]{
            0, 1, 3,
            1, 2, 3
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
		pipe.link();

        quad = new Model2D(vertices, indices);

		GL45.glClearColor(0.3f, 0.1f, 0.2f, 0.0f);
	}
	
	/**
	 * Coordinates main rendering operations
	 * @param window_ GLFW's window
	 */
	public static void render(long window_) {
		
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		
        pipe.bind();
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
