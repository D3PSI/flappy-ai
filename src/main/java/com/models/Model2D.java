package com.models;

import com.math.Matrix;

import org.lwjgl.opengl.*;

/**
 * Defines a 2D-model object
 * @author D3PSI
 */
public class Model2D {

    private     Texture     tex;
    public      float[][]   vertices = new float[][]{
        {-0.5f, 0.5f},
        {0.5f, 0.5f},
        {0.5f, -0.5f},
        {-0.5f, -0.5f}
    };
    protected   float[][]   texCoords = new float[][]{
        {0, 0},
        {1, 0},
        {1, 1},
        {0, 1}
    };

    /**
     * Constructor with texture filename
     * @param filename_
     */
    public Model2D(String filename_) {
        tex = new Texture(filename_);
    }

    /**
     * Default constructor
     */
    public Model2D() {
    }

    /**
     * Draws the quad with the texture applied to it
     */
    public void draw() {
        tex.bind();

        GL45.glBegin(GL45.GL_QUADS);
        for(int i = 0; i < 4; i++) {
            float u = vertices[i][0];
            float v = vertices[i][1];
            float x = texCoords[i][0];
            float y = texCoords[i][1];
			GL45.glTexCoord2f(x, y);
            GL45.glVertex2f(u, v);
        }
		GL45.glEnd();
    }

    /**
     * Rotates the quad
     * @param rad_ The angle to rotate around (in radians)
     */
    public void rotate(float rad_) {
        float[][] data = new float[][]{
			{1.0f, 0.0f},
			{0.0f, 1.0f}
		};
		Matrix transform = new Matrix(data);
        transform.rotateZ(rad_);
        for(int i = 0; i < 4; i++) {
            float[][] arr = {{vertices[i][0]}, {vertices[i][1]}};
            Matrix vec = new Matrix(arr);
            vec = Matrix.multiply(vec, transform);
            vertices[i][0] = vec.get(0, 0);
            vertices[i][1] = vec.get(1, 0);
        }
    }

    /**
     * Translates the quad
     * @param vec_ The translation vector
     */
    public void translate(float[] vec_) {
        for(int i = 0; i < 4; i++) {
            vertices[i][0] += vec_[0];
            vertices[i][1] += vec_[1];
        }
    }

    /**
     * Scales the quad by a scalar
     * @param scalar_ The factor to scale by
     */
    public void scale(float scalar_) {
        for(int i = 0; i < 4; i++) {
            vertices[i][0] *= scalar_;
            vertices[i][1] *= scalar_;
        }
    }

}