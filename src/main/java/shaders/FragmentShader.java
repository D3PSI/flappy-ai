package shaders;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL45.*;

/**
 * Defines a FragmentShader object
 * @author d3psi
 */
public class FragmentShader extends Shader {

	/**
	 * Constructor with filepath
	 * @param filepath_ The path to the file on disk
	 * @throws Exception Thrown when failed to create shader
	 */
	public FragmentShader(String filepath_) throws Exception {
		super(filepath_);
		shaderID = GL45.glCreateShader(GL_FRAGMENT_SHADER);
		if(shaderID == 0)
			throw new Exception("Failed to create shader at " + path);
		compile();
	}
	
}