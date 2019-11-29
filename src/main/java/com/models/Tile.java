package com.models;

import com.main.FlappyWindow;
import com.main.GLRenderer;

/**
 * Defines a tile object for the background
 * @author D3PSI
 */
public class Tile {

    static Model2D  ground       = new Model2D("res/textures/base.png");
    static Model2D  ceiling      = new Model2D("res/textures/base.png");
    static Model2D  background   = new Model2D("res/textures/bg.png");
    static Model2D  pipebot      = new Model2D("res/textures/pipe.png");
    static Model2D  pipetop      = new Model2D("res/textures/pipe.png");

    public float    height       = 0.0f;
    public float    translation  = 0.0f;

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
            {-0.66f, height - 0.2f},
            {-0.5f, height - 0.2f},
            {-0.5f, -0.87f},
            {-0.66f, -0.87f}
        };
        pipetop.vertices = new float[][]{
            {-0.66f, height + 0.2f},
            {-0.5f, height + 0.2f},
            {-0.5f, 0.87f},
            {-0.66f, 0.87f}
        };
        background.translate(new float[]{translation, 0.0f});
        pipebot.translate(new float[]{translation, 0.0f});
        pipetop.translate(new float[]{translation, 0.0f});
        ground.translate(new float[]{translation, 0.0f});
        ceiling.translate(new float[]{translation, 0.0f});
        translation += FlappyWindow.deltaTime * 0.5f;
        background.draw();
        pipebot.draw();
        pipetop.draw();
        ground.draw();
        ceiling.draw(); 
    }

    /**
     * Checks for collision with pipes
     * @return Returns true on collision
     */
    public boolean collision() {
        if(pipebot.vertices[0][0] <= -0.5f && pipebot.vertices[0][0] > -0.66f) {
            if(GLRenderer.bird.vertices[0][1] + GLRenderer.bird.yOff < pipebot.vertices[0][1] || GLRenderer.bird.vertices[1][1] + GLRenderer.bird.yOff > pipetop.vertices[1][1]) {
                System.out.print("Hit!");
                return true;
            }
        }
        return false;
    }

}