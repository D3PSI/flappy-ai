package shaders;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL45.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Defines a base Shader class for abstraction
 * @author d3psi
 */
public class Shader {

	protected int shaderID;
	String path;
	
	/**
	 * Constructor with filepath
	 * @param filepath_ The path to the file on disk
	 */
	public Shader(String filepath_) {	
		this.path = filepath_;
	}
	
	/**
	 * Reads a shader source file
	 * @return Returns a String containing the encoded shader source
	 * @throws IOException Thrown when failed to read the file
	 */
	private String readSource() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		
		return content;
	}
	
	/**
	 * Returns the shaders id
	 * @return Returns and int representing the shaders id
	 */
	public int id() {
		return shaderID;
	}
	
	/**
	 * Compiles a shader from a given source
	 * @throws Exception Thrown when failed to compile the shader source
	 */
	protected void compile() throws Exception {
		GL45.glShaderSource(shaderID, readSource());
		GL45.glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderID, 1024));
        }
	}
	
	/**
	 * Handles cleaning of resources
	 */
	public void clean() {
		GL45.glDeleteShader(shaderID);
	}
	
}
