package com.main;
import com.models.*;
import org.lwjgl.opengl.*;

import com.models.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import com.evo.NEAT.*;


public class Population {
	public ArrayList<Bird> birds = new ArrayList<Bird>();
	public Bird bestBird;
	public int bestScore = 0;
	public static int globalBestScroe = 0;
	public int gen = 1;
	public ArrayList<InnovationCounter> innovationHistory = new ArrayList<InnovationCounter>();
	public ArrayList<Bird> genbirds = new ArrayList<Bird>();
	public ArrayList<Species> species = new ArrayList<Species>();
	
	public Boolean newStage = false;
	
	public int genSinceNewWorld = 0;
	
	public int superSpeed = 1;
	
	public Population(int size) {
		for (int i = 0; i < size; i++) {
			birds.add(new Bird());
			birds.get(birds.size() -1).brain.Mutate();
			birds.get(birds.size() -1).brain.generateNetwork();
		}
	}
	
	public Bird getCurrentBest() {
		for(int i = 0; i < birds.size(); i++) {
			if(!birds.get(i).dead) {
				return birds.get(i);
			}
		}
		return birds.get(0);
	}
	
	public void updateAlive() {
		boolean firstShown = false;
		for(int i = 0; i < birds.size(); i++) {
			if(!birds.get(i).dead) {
				for(int j = 0; j < superSpeed; j++) {
					birds.get(i).look();
					birds.get(i).think();
					birds.get(i).update();
				}
				if(birds.get(i).score > globalBestScroe) {
					globalBestScroe =  birds.get(i).score; 
				}
			}
		}
	}
	
	public boolean done() {
		for(int i = 0; i < birds.size(); i++) {
			if(!birds.get(i).dead) {
				return false;
			}
		}
		return true;
	}
	
	public void setBestPlayer() {
		Bird tempBest = birds.get(0);
		tempBest.gen = gen;
		
		if(tempBest.score >= bestScore) {
			bestScore = tempBest.score;
			bestBird = tempBest;
		}
	}
	
	public void naturalSelection() {
		Bird previousBest = birds.get(0);
		speciate();
		calculateFitness();
		sortSpecies();
		
		cullSpecies();
		setBestPlayer();
		killStaleSpecies();
		killBadSpecies();
		
		//int avgSum = getAverigeSum();
		ArrayList<Bird> children = new ArrayList<Bird>();
		
		for(int i =0; i < species.size(); i++) {
			
		}
	}
	
	public void speciate() {
		
	}
	
	public void calculateFitness() {
		for(int i = 0; i < birds.size(); i++) {
			birds.get(i).calcFitness();
		}
	}
	
	public void sortSpecies() {
		
	}
	
	public void killStaleSpecies() {
		
	}
	
	public void killBadSpecies() {
		
	}
	
	public float getAvgFitnessSum() {
		float AvgSum = 0;
		for(int i = 0; i<species.size();i++) {
			//AvgSum += species.get(i).averageFitness;
		}
		
		return AvgSum;
	}
	
	public void cullSpecies() {
		for(int i = 0; i<species.size();i++) {
			//species.get(i).cull();
			//species.get(i).fitnessSharing();
			//species.get(i).setAverage();
		}
	}
}
