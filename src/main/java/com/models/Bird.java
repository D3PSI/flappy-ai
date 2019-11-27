package com.models;

import java.util.ArrayList;

import org.lwjgl.opengl.*;

/**
 * Defines a playable bird object
 * @author D3PSI
 */
public class Bird extends Model2D {

    private ArrayList<Texture> textures = new ArrayList<>();

    /**
     * Constructor with filenames
     * @param filenames_ Filenames to textures
     */
    public Bird(String[] filenames_) {
        for(String filename : filenames_) {
            textures.add(new Texture(filename));
        }
    }

    /**
     * Draws the bird with the selected texture
     * @param textureIndex_ The index of the texture to bind
     */
    public void draw(int textureIndex_) {
        textures.get(textureIndex_).bind();

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

}