package buffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * Defines a helper class for working with buffers in LWJGL
 * @author d3psi
 */
public class Buffer {

	/**
	 * Defines a helper function to fill a FloatBuffer object
	 * @param data_ The data to fill the buffer with
	 * @return Returns the newly created FloatBuffer object
	 */
	public static FloatBuffer fillFloatBuffer(float[] data_) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(data_.length);
		buf.put(data_);
		buf.flip();
		return buf;
	}
	
}
