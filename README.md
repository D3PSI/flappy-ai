[![Build Status](https://travis-ci.com/D3PSI/flappy-ai.svg?branch=master)](https://travis-ci.com/D3PSI/flappy-ai)

# flappy-ai

## Beginner-Level AI and Game-Programming

This repository defines an entry project for genetic algorithms and AI. It serves as a learning-aid for my dear friend and myself. This project is written in Java and requires a Java Version 8 or higher. Java 13 is recommended to compile it for youself, even though unit tests do not want to work at the moment with Java 13. We are working on that, but since it is not very important we may as well just forget about it.

Flappy-AI makes use of hardware acceleration through LWJGL's OpenGL 4.5 bindings. It uses a genetic algorithm to beat the game and create the perfect Flappy Bird Artificial Intelligence.

## Installation

### Linux

To install and compile this project, execute the following commands:

    sudo apt-get update && sudo apt-get install gradle -y
    git clone https://github.com/D3PSI/flappy-ai.git
    cd flappy-ai/
    gradle build

This will give you the `.jar` in `build/libs/flappy-ai-all.jar`. Run it from the project root directory with:

    java -jar build/libs/flappy-ai-all.jar

### Windows

On Windows you will want to either open the project in your favourite Java IDE and compile it through gradle or have gradle installed and added to your PATH variable and execute the following:

    git clone https://github.com/D3PSI/flappy-ai.git
    cd flappy-ai/
    gradle build

This will also give you the `.jar` in `build/libs/flappy-ai-all.jar`. You can run it from the project root directory via the console:

    java -jar build/libs/flappy-ai-all.jar

Thank you for you interest in this little project!
