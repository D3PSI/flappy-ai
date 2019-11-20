package com.models;

import org.lwjgl.opengl.*;

/**
 * Defines a 2D-model object
 * @author D3PSI
 */
public class Model2D {

    private Texture tex;

    /**
     * Constructor with texture filename
     * @param filename_
     */
    public Model2D(String filename_) {
        tex = new Texture(filename_);
    }

    /**
     * Draw 
     */
    public void draw() {
        tex.bind();

		GL45.glBegin(GL45.GL_QUADS);
			GL45.glTexCoord2f(0, 0);
			GL45.glVertex2f(-0.5f, 0.5f);

			GL45.glTexCoord2f(1, 0);
			GL45.glVertex2f(0.5f, 0.5f);

			GL45.glTexCoord2f(1, 1);
			GL45.glVertex2f(0.5f, -0.5f);

			GL45.glTexCoord2f(0, 1);
			GL45.glVertex2f(-0.5f, -0.5f);
		GL45.glEnd();
    }

}