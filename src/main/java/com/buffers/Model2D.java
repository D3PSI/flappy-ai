package com.buffers;

import org.lwjgl.opengl.GL45;

/**
 * Defines a 2D-Model object
 * @author d3psi
 */
public class Model2D {

	private VertexBuffer vertexData;
	private int VAO;
	private int vertexCount = 0;
	
	/**
	 * Constructor with vertex data
	 * @param vertexData_ A linear array of floating point vertex data
	 * @param vertexCount_ The number of vertices
	 */
	public Model2D(float[] vertexData_, int vertexCount_) {
		this.VAO = createVertexArray();
		this.vertexData = new VertexBuffer(vertexData_);
		this.vertexCount = vertexCount_;
	}
	
	/**
	 * Creates a new vertex array object
	 */
	private int createVertexArray() {
		int vao = GL45.glGenVertexArrays();
		return vao; 
	}
	
	/**
	 * Activates a vertex array for the models vertex data
	 * @param index_ The index of the vertex array to activate
	 * @param size_ The length of the data in said vertex array
	 * @param stride_ The stride between the datapoints
	 * @param offset_ The starting offset
	 */
	public void addToVertexArray(int index_, int size_, int stride_, int offset_) {
		GL45.glBindVertexArray(VAO);
		GL45.glVertexAttribPointer(
				index_, 
				size_, 
				GL45.GL_FLOAT, 
				false, 
				stride_, 
				offset_);
	    GL45.glEnableVertexAttribArray(index_);
	}
	
	/**
	 * Binds the VAO
	 */
	public void bindVAO() {
		GL45.glBindVertexArray(VAO);
	}
	
	/**
	 * Unbinds the vertex array currently bound
	 */
	public void unbindVAO() {
		GL45.glBindVertexArray(0);
	}

	/**
	 * Unbinds the vertex buffer object
	 */
	public void unbindVBO() {
		vertexData.unbind();
	}
	
	/**
	 * Sets the vertex buffer options
	 */
	public void set() {
		vertexData.setBufferData();
	}
	
	/**
	 * Draws the bound vertex buffer
	 */
	public void draw() {
		bindVAO();
		GL45.glDrawArrays(GL45.GL_TRIANGLES, 0, vertexCount);
	}
	
	/**
	 * Handles cleaning of allocated resources
	 */
	public void clean() {
		vertexData.clean();
		GL45.glDeleteVertexArrays(VAO);
	}
	
}
