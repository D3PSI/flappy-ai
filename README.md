[![Build Status](https://travis-ci.com/D3PSI/flappy-ai.svg?branch=master)](https://travis-ci.com/D3PSI/flappy-ai) [![Java CI](https://github.com/D3PSI/flappy-ai/workflows/Java%20CI/badge.svg)](https://github.com/D3PSI/flappy-ai/actions?query=workflow%3A"Java+CI")

# flappy-ai

## Beginner-Level AI and Game-Programming

This repository defines an entry project for genetic algorithms and AI. It serves as a learning-aid for my dear friend and myself. This project is written in Java and requires a Java Version 8 or higher. Java 13 is recommended to compile it for youself.

Flappy-AI makes use of hardware acceleration through LWJGL's OpenGL 4.5 bindings. It uses a genetic algorithm to beat the game and create the perfect Flappy Bird Artificial Intelligence.

## Installation

### Linux

To install and compile this project, execute the following commands:

    git clone https://github.com/D3PSI/flappy-ai.git
    ./flappy-ai/INSTALL.sh

This will give you the `.jar` in `build/libs/flappy-ai-all.jar` and will install the entire project to `$HOME/flappy-ai`. It will also install Gradle through SDKMAN!. Restart your terminal or `source` your `.bashrc`:

    source $HOME/.bashrc

And type:

    flappy-ai

This will start the application. Alternatively, you can also execute the `.jar` in the project root directory by executing:

    java -jar build/libs/flappy-ai-all.jar

To uninstall it, remove the automatically added entry `$HOME/flappy-ai` from the PATH-variable by editing `$HOME/.bashrc` and removing the line `export PATH=$PATH:$HOME/flappy-ai`. Then go ahead and delete the project contents on disk:

    rm -r $HOME/flappy-ai

### Windows

On Windows you will want to either open the project in your favourite Java IDE and compile it through Gradle or have Gradle installed and added to your PATH variable and execute the following:

    git clone https://github.com/D3PSI/flappy-ai.git
    cd flappy-ai/
    gradle build

This will also give you the `.jar` in `build/libs/flappy-ai-all.jar`. You can run it from the project root directory via the console:

    java -jar build/libs/flappy-ai-all.jar

Thank you for you interest in this little project!
