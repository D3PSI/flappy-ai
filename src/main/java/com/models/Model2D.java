package com.models;

import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.math.Matrix4f;

/**
 * Defines a 2D-Model object
 * @author d3psi
 */
public class Model2D {

    private int     VAO;
    private int     VBO;
    private int     EBO;
	private int     vertexCount     = 0;
    private boolean indexed;

    public Matrix4f model;
    
	/**
	 * Constructor with vertex data
	 * @param vertexData_ A linear array of floating point vertex data
	 * @param vertexCount_ The number of vertices
	 */
	public Model2D(float[] vertexData_, int vertexCount_) {
        this.indexed = false;
        this.VAO = GL45.glGenVertexArrays();
        bindVAO();
        createVertexBuffer(vertexData_, vertexCount_);
        unbindVAO();
		this.vertexCount = vertexCount_;
	}
    
    /**
     * Constructor with vertex data
     * @param vertexData_ A linear array of floating point vertex data
     * @param indexData_ A linear array of integer index data
	 * @param vertexCount_ The number of vertices
	 */
	public Model2D(float[] vertexData_, int[] indexData_) {
        this.VAO = GL45.glGenVertexArrays();
        bindVAO();
        createVertexBuffer(vertexData_, indexData_.length);
        createIndexBuffer(indexData_);
        unbindVAO();
        this.vertexCount = indexData_.length;
	}

    /**
     * Creates a vertex buffer from given vertex data
     * @param vertexData_ A linear array of floating point vertex data
     * @param length_ THe number of vertices
     */
    private void createVertexBuffer(float[] vertexData_, int length_) {
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertexData_.length);
		vertexBuffer.put(vertexData_).flip();
        this.VBO = GL45.glGenBuffers();
        bindVBO();
		GL45.glBufferData(GL45.GL_ARRAY_BUFFER, vertexBuffer, GL45.GL_STATIC_DRAW);
		GL45.glEnableVertexAttribArray(0);
        GL45.glVertexAttribPointer(0, 3, GL45.GL_FLOAT, false, 0, 0);
        unbindVBO();
		if (vertexBuffer != null) {
			MemoryUtil.memFree(vertexBuffer);
		}
    }

    /**
     * Creates an index buffer from given index data
     * @param indexData_ A linear array of integer index data
     */
    private void createIndexBuffer(int[] indexData_) {
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indexData_.length);
        indexBuffer.put(indexData_).flip();
        bindVAO();
        EBO = GL45.glGenBuffers();
        bindEBO();
        GL45.glBufferData(GL45.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL45.GL_STATIC_DRAW);
        this.indexed = true;
    }

	/**
	 * Binds the vertex array
	 */
	private void bindVAO() {
		GL45.glBindVertexArray(VAO);
	}
	
	/**
	 * Unbinds the vertex array
	 */
	private void unbindVAO() {
		GL45.glBindVertexArray(0);
    }
    
    /**
     * Binds the vertex buffer
     */
    private void bindVBO() {
        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, VBO);
    }

    /**
     * Unbinds the vertex buffer
     */
    private void unbindVBO() {
        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Binds the index buffer
     */
    private void bindEBO() {
        GL45.glBindBuffer(GL45.GL_ELEMENT_ARRAY_BUFFER, EBO);
    }

    /**
     * Unbinds the index buffer
     */
    private void unbindEBO(){
        GL45.glBindBuffer(GL45.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

	/**
	 * Draws the bound vertex buffer
	 */
	public void draw() {
        bindVAO();
        if (indexed) {
            bindEBO();
            GL45.glDrawElements(
                GL45.GL_TRIANGLES, 
                vertexCount, 
                GL45.GL_UNSIGNED_INT, 
                0);
            unbindEBO();
        }
        else {
            GL45.glDrawArrays(GL45.GL_TRIANGLES, 0, vertexCount);
        }
        unbindVAO();
	}
	
	/**
	 * Handles cleaning of allocated resources
	 */
	public void clean() {
        GL45.glDeleteVertexArrays(VAO);
        GL45.glDeleteBuffers(VBO);
	}
	
}
