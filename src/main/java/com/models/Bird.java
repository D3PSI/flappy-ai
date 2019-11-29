package com.models;

import java.util.ArrayList;

import com.main.FlappyWindow;

import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Defines a playable bird object
 * @author D3PSI
 */
public class Bird extends Model2D {

    private ArrayList<Texture> textures = new ArrayList<>();
    private float xOff = -0.66f; 
    private float yOff = 0.0f;
    private float yVel = 0.0f;

    double start = glfwGetTime();
    double now = glfwGetTime();

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
            yOff += yVel * FlappyWindow.deltaTime;
            yVel += -0.5f * FlappyWindow.deltaTime; 
            if (yOff < -0.8f) {
                yOff = -0.8f;
                yVel = 0.0f;
            }
            float u = vertices[i][0] + xOff;
            float v = vertices[i][1] + yOff;
            float x = texCoords[i][0];
            float y = texCoords[i][1];
			GL45.glTexCoord2f(x, y);
            GL45.glVertex2f(u, v);
        }
		GL45.glEnd();
    }

    /**
     * Makes the bird jump
     */
    public void jump() {
        yVel = -1.0f;
    }

}