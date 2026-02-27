# Project 1: Webpage Caching

**Author:** Jacob Smith

**Class:** CS321 Section 001

**Semester:** Spring 2026  

## Overview

This project implements a generic cache using Java’s LinkedList data structure to simulate how a web server caches webpages in memory. The cache stores recently accessed webpages so future requests can be served faster, reducing the cost of repeatedly reading pages from disk. A test program generates webpage requests using files given by the instructor and measures cache performance based on hit ratio and execution time. 

The core program functionality is implemented by myself in Cache.java under the instructor's guidance, which has a few core methods: get, add, remove, clear, and toString. The cache interface is defined in CacheInterface.java, which specifies the required cache operations. The program CacheExperiment.java serves as the main driver and uses WebpageGenerator.java, which uses more files, to generate webpage requests.

## Reflection

This project reinforced my understanding of how abstract data types like caches are implemented using standard data structures like a linkedlist from java's standard library. Implementing the cache with a Most Recently Used (MRU) access policy and a Least Recently Used (LRU) eviction policy required careful management of the linked list ordering. Once the logic for moving elements within the list was correct and understandable, the overall behavior of the cache became much easier to understand and debug.

The most challenging part of the project was ensuring the cache behaved as specificed in the assignments. Small mistakes being fixed before they lead to bigger ones. Another challenge was compiling the project locally due to JUnit, which was fixed by compiling without the provided test file. Despite these difficulties, it was satisfying to see the cache performance improve significantly as the cache size was increased. 

This project taught me a lot I learned more about using bash commands as I havent touched them in awhile, I learned about the idead of Most Recently Used, and Least Recently Used. Overall I enjoyed this first project, as I was able to struggle and grow through new topics!

## Compiling and Using

To compile the project (excluding the provided JUnit test file), run the following command from the project directory:

    javac $(ls *.java | grep -v CacheTest.java)

To run the cache experiment, use the following command format:

    java CacheExperiment <cache-size> <number-of-lookups> <standard-deviation <debug-level> [<seed>]

Example Usages: 

    java CacheExperiment 150 10000 30.0 0 123

    java CacheExperiment 700 50000 45.0 3 321



The debug level controls the amount of webpage information outputted to the terminal, as followed:
- 0: prints cache statistics only  
- 1: prints the webpage access distribution  
- 2: prints summarized webpage contents  
- 3: prints full webpage contents  

## Results

The experimental results show that increasing the cache size significantly improves performance. As a cache grows larger, the cache hit ratio increases and and the execution time decreases because fewer webpages need to be generated, which simulates fewer disk reads. These results taught me the effectiveness of the MRU/LRU caching strategy as I worked through the project. In the end
This project and results show me the relationship between a cache size, hit ratio, and execution time of the program. 

## Sources Used

No external sources were used beyond the course lecture notes, provided starter code, and official Java documentation.


