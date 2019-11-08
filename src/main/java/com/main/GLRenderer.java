package com.main;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import com.buffers.Model2D;
import com.shaders.FragmentShader;
import com.shaders.ShaderPipeline;
import com.shaders.VertexShader;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;

/**
 * Defines a static utility class to handle rendering operations
 * @author d3psi
 *
 */
public class GLRenderer {

	private static ShaderPipeline pipe;
	
	static float[] vertices = new float[]{
			0.0f, 0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f
			}; 
	static Model2D quad;
	static int VAO, VBO;
	
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

		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
		verticesBuffer.put(vertices).flip();
		VAO = GL45.glGenVertexArrays();
		GL45.glBindVertexArray(VAO);
		VBO = GL45.glGenBuffers();
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, VBO);
		GL45.glBufferData(GL45.GL_ARRAY_BUFFER, verticesBuffer, GL45.GL_STATIC_DRAW);
		GL45.glVertexAttribPointer(0, 3, GL45.GL_FLOAT, false, 0, 0);
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
		GL45.glBindVertexArray(0);
		if (verticesBuffer != null) {
			MemoryUtil.memFree(verticesBuffer);
		}
		
		GL45.glClearColor(0.3f, 0.1f, 0.2f, 0.0f);
	}
	
	/**
	 * Coordinates main rendering operations
	 * @param window_ GLFW's window
	 */
	public static void render(long window_) {
		
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		
		pipe.bind();
		GL45.glBindVertexArray(VAO);
		GL45.glEnableVertexAttribArray(0);
		GL45.glDrawArrays(GL45.GL_TRIANGLES, 0, 3);
		GL45.glDisableVertexAttribArray(0);
		GL45.glBindVertexArray(0);
		
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
