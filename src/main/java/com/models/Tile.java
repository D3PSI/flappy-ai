package com.models;

import com.main.FlappyWindow;
import com.main.GLRenderer;

/**
 * Defines a tile object for the background
 * @author D3PSI
 */
public class Tile {

    static Model2D              ground       = new Model2D("res/textures/base.png");
    static Model2D              ceiling      = new Model2D("res/textures/base.png");
    static Model2D              background   = new Model2D("res/textures/bg.png");
    static Model2D              pipebot      = new Model2D("res/textures/pipe.png");
    static Model2D              pipetop      = new Model2D("res/textures/pipe.png");
    public static boolean       stop         = false;

    public float                height       = 0.0f;
    public float                translation  = 0.0f;

    private boolean             noPipe       = false;

    public static final float   SPREAD       = 0.3f;

    /**
     * Constructor with arguments
     * @param height_ The height of the gap
     */
    public Tile(float height_) {
        this.height = height_;
    }

    /**
     * Draws the tile
     */
    public void draw() {
        background.vertices = new float[][]{
            {-1.0f, 1.0f},
            {-0.34f, 1.0f},
            {-0.34f, -1.0f},
            {-1.0f, -1.0f}
        };
        ground.vertices = new float[][]{
            {-1.0f, -0.87f},
            {-0.34f, -0.87f},
            {-0.34f, -1.0f},
            {-1.0f, -1.0f}
        };
        ceiling.vertices = new float[][]{
            {-0.34f, 0.87f},
            {-1.0f, 0.87f},
            {-1.0f, 1.0f},
            {-0.34f, 1.0f}
        };
        pipebot.vertices = new float[][]{
            {-0.66f, height - SPREAD},
            {-0.5f, height - SPREAD},
            {-0.5f, -0.87f},
            {-0.66f, -0.87f}
        };
        pipetop.vertices = new float[][]{
            {-0.66f, height + SPREAD},
            {-0.5f, height + SPREAD},
            {-0.5f, 0.87f},
            {-0.66f, 0.87f}
        };
        if(!stop) {
            background.translate(new float[]{translation, 0.0f});
            pipebot.translate(new float[]{translation, 0.0f});
            pipetop.translate(new float[]{translation, 0.0f});
            ground.translate(new float[]{translation, 0.0f});
            ceiling.translate(new float[]{translation, 0.0f});
            translation += FlappyWindow.deltaTime * 0.5f;
        }
        background.draw();
        if(!noPipe) {
            pipebot.draw();
            pipetop.draw();
        }
        ground.draw();
        ceiling.draw(); 
    }

    /**
     * Disables the pipe for the tile
     */
    public void noPipe() {
        noPipe = true;
    }

    /**
     * Enables the pipe for the tile
     */
    public void pipe() {
        noPipe = false;
    }

    /**
     * Checks whether the tile has a pipe
     * @return Returns a boolean representing the pipe state of the tile
     */
    public boolean hasPipe() {
        return !noPipe;
    }

    /**
     * Checks for collision with pipes
     * @param bird_ to check collision
     * @return Returns true on collision
     */
    public boolean collision(Bird bird_) {
        if(!noPipe) {
            if(pipebot.vertices[1][0] > bird_.vertices[0][0] + bird_.xOff
                && pipebot.vertices[0][0] < bird_.vertices[1][0] + bird_.xOff) {
                if(bird_.vertices[2][1] + bird_.yOff < pipebot.vertices[0][1] 
                    || bird_.vertices[1][1] + bird_.yOff > pipetop.vertices[1][1]) {
                	bird_.dead = true;
                    return true;
                }
            }
        }
        System.out.println(inTile(bird_));
        return false;
    }

    /**
     * Checks whether the bird is currently in the tile
     * @param bird_ to check in tile
     * @return Returns true if yes
     */
    public boolean inTile(Bird bird_) {
        if(ground.vertices[1][0] > bird_.vertices[0][0] + bird_.xOff
        && ground.vertices[0][0] < bird_.vertices[1][0] + bird_.xOff) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the distance to the pipe
     * @param bird_ to get distance
     * @return The distance to the pipe
     */
    public float getDistance(Bird bird_) {
        return pipebot.vertices[1][0] - (bird_.vertices[1][0] + bird_.xOff);
    }

}