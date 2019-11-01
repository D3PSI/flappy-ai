package shaders;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL45.*;

/**
 * Defines a ShaderPipeline object containing all the needed pipeline stages
 * @author d3psi
 */
public class ShaderPipeline {
	
	private int pipeID;
	private VertexShader vs;
	private FragmentShader fs;

	/**
	 * Default constructor
	 */
	public ShaderPipeline() {
	}
	
	/**
	 * Links the shaders to a shader program
	 * @throws Exception Thrown when failed to link shader code
	 */
	public void link() throws Exception {
		GL45.glLinkProgram(pipeID);
		if(GL45.glGetProgrami(pipeID, GL_LINK_STATUS) == 0)
			throw new Exception("Error linking shader code: " + GL45.glGetProgramInfoLog(pipeID, 1024));
		if(vs.id() != 0)
			GL45.glDetachShader(pipeID, vs.id());
		if(fs.id() != 0)
			GL45.glDetachShader(pipeID, fs.id());
		GL45.glValidateProgram(pipeID);
		if(GL45.glGetProgrami(pipeID, GL_VALIDATE_STATUS) == 0)
			System.err.println("Warning validating shader code: " + GL45.glGetProgramInfoLog(pipeID, 1024));
	}
	
	/**
	 * Binds the shader pipeline
	 */
	public void bind() {
		GL45.glUseProgram(pipeID);
	}
	
	/**
	 * Unbinds the shader pipeline
	 */
	public void unbind() {
		GL45.glUseProgram(0);
	}
	
	/**
	 * Cleans allocated resources
	 */
	public void clean() {
		unbind();
		if(pipeID != 0)
			try {
				vs.clean();
				fs.clean();
			} catch (Exception e_) {
				e_.printStackTrace();
			}
			GL45.glDeleteProgram(pipeID);
	}
	
	/**
	 * Add the vertex shader
	 */
	public void vs(VertexShader vs_) {
		GL45.glAttachShader(pipeID, vs_.id());
	}
	
	/**
	 * Add the fragment shader
	 */
	public void fs(FragmentShader fs_) {
		GL45.glAttachShader(pipeID, fs_.id());
	}
	
	/**
	 * Returns the pipeline's id
	 * @return Returns an int representing the pipeline's id
	 */
	public int id() {
		return pipeID;
	}
	
}
