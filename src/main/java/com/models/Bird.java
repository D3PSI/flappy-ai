package com.models;

import java.util.ArrayList;

import com.main.FlappyWindow;

import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;

import com.evo.NEAT.*;

/**
 * Defines a playable bird object
 * @author D3PSI
 */
public class Bird extends Model2D{
	
	/*
	 * neat vars
	 */
	public float vision[] = new float[4]; // input;
	public float decision[] = new float[1]; //output;
	public int timeSurvived = 0;
	public int fitness = 0;
	public int lifespan = 0;
	public int bestScore = 0;
	public boolean dead = false;
	public int score = 0;
	public int gen = 0;
	public int genomeInputs = 4;
	public int genomeOutputs = 1;
	public Genome brain = new Genome();
	
	public float distToNextPipe = 0;
	public float distToHighPipe = 0;
	public float distToLowPipe = 0;

    private static ArrayList<Texture> textures = new ArrayList<>();
    public float xOff = -0.66f; 
    public float yOff = 0.0f;
    private float yVel = 0.0f;
    

    double start = glfwGetTime();
    double now = glfwGetTime();

    /**
     * Constructor with filenames
     */
    public Bird() {
        scale(0.125f);
    }

    /**
     * Initializes texture loading to static field for performance increase with multiple birds
     * @param filenames_
     */
    public static void textures(String[] filenames_) {
        for(String filename : filenames_) {
            textures.add(new Texture(filename));
        }
    }

    /**
     * Draws the bird with the selected texture
     */
    public void draw() {
        lifespan++;
        textures.get(0).bind();

        GL45.glBegin(GL45.GL_QUADS);
        for(int i = 0; i < 4; i++) {
            yOff += yVel * FlappyWindow.deltaTime;
            yVel += -0.5f * FlappyWindow.deltaTime; 
            if(yOff < -0.8f) {
                yOff = -0.8f;
                yVel = 0.0f;
            } else if(yOff > 0.8f) {
                yOff = 0.8f;
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
        yVel = -0.5f;
    }
    
    public void look() {
    	vision[0] = yVel;
    	vision[1] = distToNextPipe;
    	vision[2] = distToLowPipe;
    	vision[1] = distToHighPipe;
    	
    }
    
    public void think() {
    	decision = brain.evaluateNetwork(vision);
    	
    	if(decision[0] > 0.6) {
    		jump();
    	}
    	
    }
    
    public Bird Crossover(Bird _bird) {
    	Bird child = new Bird();
    	child.brain = brain.crossOver(_bird.brain, child.brain);
    	return child;
    }
    
    public Bird clone() {
    	Bird clone = new Bird();
    	clone.fitness = fitness;
    	clone.bestScore = score;
    	clone.gen = gen;
    	clone.brain = brain;
    	clone.brain.setFitness(fitness);
    	clone.brain.generateNetwork();
    	
    	return clone;
    }
    
    public void calcFitness() {
    	fitness = 1 + score * score + lifespan/20;
    	brain.setFitness(fitness);
    }
    
    public void update() {
    	
    }

}