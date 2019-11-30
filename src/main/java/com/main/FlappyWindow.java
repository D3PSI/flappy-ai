package com.main;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.neat.*;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

/**
 * Defines the window handler class
 * @author D3PSI
 */
public class FlappyWindow {

	private static final int 		WIDTH 		= 1280;
	private static final int 		HEIGHT 		= 720;
	private static final String 	TITLE 		= "Flappy AI - Genetic Algorithm";
	public static int 				width 		= 0;
    public static int 				height 		= 0;
	public static float             deltaTime   = 0.0f;
	public static float 			timer 		= 0.0f;
	
	private long window;

	/**
	 * Runs the application
	 */
	public void run() {
		try {
			init();
			loop();
		} catch (Exception e_) {
			e_.printStackTrace();
		}
	}

	/**
	 * Initializes the application
	 * @throws Exception Thrown when failed to initialize application
	 */
	private void init() throws Exception {
		GLFWErrorCallback.createPrint(System.err).set();

		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		window = glfwCreateWindow(
				WIDTH, 
				HEIGHT, 
				TITLE, 
				NULL, 
				NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            GLRenderer.keyboard(key, action);
			
		});

		glfwSetFramebufferSizeCallback(window, (window_, width_, height_) -> {
            if (width_ > 0 && height_ > 0 && (width != width_ || height != height_)) {
                width = width_;
				height = height_;
				GL45.glViewport(0, 0, width_, height_);
            }
        });

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); 
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2);
		} 

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	/**
	 * Defines the main loop for the application
	 * @throws Exception Thrown when failed to initialize user defined objects
	 */
	private void loop() throws Exception {
		GL.createCapabilities();
		GLRenderer.init(window);
        double start = glfwGetTime();

		while (!glfwWindowShouldClose(window)) {
            double last = glfwGetTime();
			GLRenderer.render();
			glfwPollEvents();
			glfwSwapBuffers(window);
            double now = glfwGetTime();
			deltaTime = (float)(last - now);
			timer = (float)(last - start);
        }
	}
	
	/**
	 * Defines the entry point for the application
	 * @param args Command Line arguments
	 */
	public static void main(String[] args) {
		//new FlappyWindow().run();
        // 1.) Define the genotype (factory) suitable
        //     for the problem.
        Factory<Genotype<BitGene>> gtf =
            Genotype.of(BitChromosome.of(10, 0.5));
 
        // 3.) Create the execution environment.
        Engine<BitGene, Integer> engine = Engine
            .builder(NEAT::eval, gtf)
            .build();
 
        // 4.) Start the execution (evolution) and
        //     collect the result.
        Genotype<BitGene> result = engine.stream()
            .limit(100)
            .collect(EvolutionResult.toBestGenotype());
 
        System.out.println("Hello World:\n" + result);
	}

}