package com.main;

import org.lwjgl.opengl.*;

import com.models.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import com.evo.NEAT.*;

/**
 * Defines a static utility class to handle rendering operations
 * @author D3PSI
 */
public class GLRenderer {
	
	private static Tile[] tiles = new Tile[5];
	public static ArrayList<Bird> bird = new ArrayList<Bird>();
	public static int entities = 30;
	public static int score = 0;
	public static float distance = 0.0f;
	
	/**
	 * Initializes shaders and buffers
	 * @param window_ GLFW's window
	 * @throws Exception Thrown when failed to initialize shaders and buffers
	 */
	public static void init(final long window_) throws Exception {
		GL45.glEnable(GL45.GL_TEXTURE_2D);
		GL45.glEnable(GL45.GL_BLEND);
		GL45.glBlendFunc(GL45.GL_SRC_ALPHA, GL45.GL_ONE_MINUS_SRC_ALPHA);
		Bird.textures(new String[]{
            "res/textures/bird1.png",
            "res/textures/bird2.png",
            "res/textures/bird3.png"
        });
		for(int i = 0; i < entities; i++) {
			bird.add(new Bird());
		}
		initTiles();
	}

	/**
	 * Initializes the tiles
	 */
	public static void initTiles() {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile((float)(Math.random() * (0.2 - -0.2)) + -0.2f);
			tiles[i].translation = -1.0f + tiles.length * 0.66f + i * -0.66f;
		}
		tiles[tiles.length - 3].noPipe();
		tiles[tiles.length - 2].noPipe();
		tiles[tiles.length - 1].noPipe();
	}

	/**
	 * Coordinates main rendering operations
	 */
	public static void render() {
		GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
		for(Tile tile : tiles) {
			for(int i = 0; i < bird.size(); i++) {
				if(tile.inTile(bird.get(i))) {
					
					bird.get(i).distToNextPipe = tile.getDistance(bird.get(i));
					bird.get(i).distToHighPipe = tile.height + tile.SPREAD;
					bird.get(i).distToLowPipe = tile.height - tile.SPREAD;
					System.out.println(distance);
					System.out.println(bird.get(i).distToHighPipe);
					System.out.println(bird.get(i).distToLowPipe);
					
				}
			}
			if(tile.translation <= -1.0f) {
				tile.height = (float)(Math.random() * (0.2 - -0.2)) + -0.2f;
				tile.translation += tiles.length * 0.66f;
				if(tile.hasPipe()) {
					for(int i = 0; i < bird.size(); i++) {
						bird.get(i).score++;
					}
					score += 1;
					System.out.println(score);
				}
				tile.pipe();
			}
			tile.draw();
			for(int i = 0; i < bird.size(); i++) {
				if(tile.collision(bird.get(i))) {
					try {
						bird.remove(i);
					} catch (Exception e_) {
						e_.printStackTrace();
					}
				}
			}
			if(bird.size() == 0) {
				bird.clear();
				System.out.println("all dead");
				restart();
				return;
			}
		}
		for(int i = 0; i < bird.size(); i++) {
			bird.get(i).draw();
		}
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * Restarts the game
	 */
	public static void restart() {
		System.out.println("restart Game...");
		double start = glfwGetTime();
		while (glfwGetTime() - start < 1.0) {
			Tile.stop = true;
			FlappyWindow.deltaTime = 0.0f;
		}
		score = 0;
		GLRenderer.initTiles();
		for(int i = 0; i < entities; i++) {
			bird.add(new Bird());
			System.out.println("a bird has been revived...");
		}
		Tile.stop = false;
	}

	/**
	 * Keyboard callback function
	 * @param key_ The pressed or released key
	 * @param action_ The action of the key
	 */
    public static void keyboard(int key_, int action_) {
        if(key_ == GLFW_KEY_SPACE && action_ == GLFW_PRESS) {
        	for(int i = 0; i < bird.size(); i++) {
        		bird.get(i).jump();
        	}
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
