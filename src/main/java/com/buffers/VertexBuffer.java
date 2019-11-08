package com.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryUtil;

/**
 * Defines a handler class for a vertex buffer
 * @author d3psi
 */
public class VertexBuffer {

	private int VBO;
	private FloatBuffer buffer;
	
	/**
	 * Constructor with vertex data
	 */
	public VertexBuffer(float[] vertices_) {
		this.buffer = MemoryUtil.memAllocFloat(vertices_.length).put(vertices_).flip();
		VBO = GL45.glGenBuffers();
	}
	
	/**
	 * Sets buffer data options
	 */
	public void setBufferData() {
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, VBO);
		GL45.glBufferData(GL45.GL_ARRAY_BUFFER, buffer, GL45.GL_STATIC_DRAW);
	}
	
	/**
	 * Unbinds the vertex buffer
	 */
	public void unbind() {
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Cleans the vertex buffer
	 */
	public void clean() {
		GL45.glDeleteBuffers(VBO);
	}
	
}