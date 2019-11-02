package buffers;

import java.util.ArrayList;

import org.lwjgl.opengl.GL45;

/**
 * Defines a 2D-Model object
 * @author d3psi
 */
public class Model2D {

	private VertexBuffer vertexData;
	private int vertexCount = 0;
	private ArrayList<Integer> VAOs = new ArrayList<Integer>();;
	
	/**
	 * Constructor with vertex data
	 * @param vertexData_
	 */
	public Model2D(float[] vertexData_, int vertexCount_) {
		this.vertexData = new VertexBuffer(vertexData_);
		this.vertexCount = vertexCount_;
	}
	
	/**
	 * Activates a vertex array for the models vertex data
	 * @param index_ The index of the vertex array to activate
	 * @param size_ The length of the data in said vertex array
	 * @param stride_ The stride between the datapoints
	 * @param offset_ The starting offset
	 */
	public void addVertexArray(int index_, int size_, int stride_, int offset_) {
		int vao = GL45.glGenVertexArrays();
		VAOs.add(vao);
		GL45.glBindVertexArray(vao);
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
	 * Binds a VAO
	 * @param index_ The VAO's index
	 */
	public void bindVAO(int index_) {
		GL45.glBindVertexArray(index_);
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
		GL45.glDrawArrays(GL45.GL_TRIANGLES, 0, vertexCount);
	}
	
	/**
	 * Handles cleaning of allocated resources
	 */
	public void clean() {
		vertexData.clean();
		for(int vao : VAOs) {
			GL45.glDeleteVertexArrays(vao);
		}
	}
	
}
