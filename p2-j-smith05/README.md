
# Project 2: Priority Queue – Stardew Valley Simulation

* Author: Jacob Smith
* Class: CS321 Section 001
* Semester: Spring 2026

## Overview

This project implements a max-heap priority queue to simulate a discrete event task scheduling system inspired by the game Stardew Valley. Tasks are generated and prioritized based on priority level and creation time, while preventing starvation through priority aging. The simulation models energy consumption, failure events, and multi-day task scheduling, which are important aspects of Stardew Valley.

## Reflection

This project, like each of my projects helped me learn more priority queues and heap data structures because I had to implement the max-heap logic from scratch rather than relying on and using  Java’s built in Max-heap. Designing the comparison logic for tasks was especially important, since it wasn't just a basic comparision but rather tasks had to be ordered first by priority and then by hour created. Once I corrected that ordering and properly implemented heap resizing, the simulation behavior aligned with the expected output.

The most challenging part was debugging errors that caused incorrect scheduling and failing test cases. In particular, I forgot to resize the heap, in the insert method, which caused enqueue failures and caused all the test cases to produce incorrect simulation results. The issue itself wasn't overally difficult to fix, I was just struggling to track down the issue. Additionally I also struggled a lot with JUnit Testing, as I've never delt with that type of testing before, and was new to me so I wasn't sure where to begin.  Overall, I enjoyed this project and seeing how different date structures can come together to and work with each other to create a simulation like the video game Stardew Valley

## Compiling and Using

To compile the program:

    javac MyLifeInStardew.java

To run the program:

    java MyLifeInStardew <max-priority> <time-to-increment-priority> <simulation-days> <task-generation-probability> [seed]

Where:

    Max-priority: is the highest possible task priority (must be ≥ 1)

    Time-to-increment-priority: is how long a task waits before its priority increases

    Simulation-days: is the number of days to simulate

    Task-generation-probability: is a value between 0 and 1

    Seed: is optional and allows reproducible results

You can also run the six automated tests with:

    ./run-tests.sh

## Results

The simulation successfully passed all provided test cases using provided test script. This shows that the final implementation of Project 2 correctly handles max heap operations, priority aging, task generation, and energy depletion scenarios. My outputs matched expected results across multiple seed values.

## Sources Used

For this Project I used a couple outside sources to help me understand JUnit Testing as I've never worked with it before.

I started with this article from [Bright](https://brightsec.com/blog/junit-testing-the-basics-and-a-quick-tutorial/) which helped me to understand the basic idea behind JUnit Testing and how to implement it.

I watched this video from [Coding with John](https://youtu.be/vZm0lHciFsQ?si=aHIYrD-ifyZd_1UU) which gave me a better visual understanding of how to write JUnit Testing.
