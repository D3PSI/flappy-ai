package buffers;

/**
 * Defines a handler for a vertex buffer
 * @author d3psi
 */
public class VertexBuffer {

	public int VBO = 0;
	private float[] vertices;
	
	/**
	 * Constructor with vertex data
	 */
	public VertexBuffer(float[] vertices_) {
		this.vertices = vertices_;
	}
	
}
