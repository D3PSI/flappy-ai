package com.main;

import org.lwjgl.opengl.*;

import com.models.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Defines a static utility class to handle rendering operations
 * @author D3PSI
 */
public class GLRenderer {
	
    static Bird bird;
	static Tile tile;
	
	/**
	 * Initializes shaders and buffers
	 * @param window_ GLFW's window
	 * @throws Exception Thrown when failed to initialize shaders and buffers
	 */
	public static void init(final long window_) throws Exception {
		GL45.glEnable(GL45.GL_TEXTURE_2D);
		GL45.glEnable(GL45.GL_BLEND);
		GL45.glBlendFunc(GL45.GL_SRC_ALPHA, GL45.GL_ONE_MINUS_SRC_ALPHA);
		bird = new Bird(new String[]{
            "res/textures/bird1.png",
            "res/textures/bird2.png",
            "res/textures/bird3.png"
        });
		bird.scale(0.3f);
		tile = new Tile(0.0f);
		Tile.initTiles();
	}

	/**
	 * Coordinates main rendering operations
	 */
	public static void render() {
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		tile.draw();
		bird.draw(0);
	}

	/**
	 * Keyboard callback function
	 * @param key_ The pressed or released key
	 * @param action_ The action of the key
	 */
    public static void keyboard(int key_, int action_) {
        if(key_ == GLFW_KEY_SPACE && action_ == GLFW_PRESS) {
            bird.jump();
		}

		if(key_ == GLFW_KEY_1 && action_ == GLFW_PRESS) {
			GL45.glPolygonMode(GL45.GL_FRONT_AND_BACK, GL45.GL_FILL);
		}
		if(key_ == GLFW_KEY_2 && action_ == GLFW_PRESS) {
			GL45.glPolygonMode(GL45.GL_FRONT_AND_BACK, GL45.GL_LINE);
		}
		if(key_ == GLFW_KEY_3 && action_ == GLFW_PRESS) {
			GL45.glPolygonMode(GL45.GL_FRONT_AND_BACK, GL45.GL_POINT);
		}
    }

	/**
	 * Handles cleaning of resources on shutdown
	 * @param window_ GLFW's window
	 */
	public static void clean(final long window_) {
		glfwFreeCallbacks(window_);
		glfwDestroyWindow(window_);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
}
