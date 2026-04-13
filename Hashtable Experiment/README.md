# Project 3: Experiment with Hashing

* Author: Jacob Smith
* Class: CS321 001
* Semester: Spring 2026

## Overview

This program implements a hash table using open addressing and compares the performance of linear probing and double hashing. The goal of the experiment is to analyze how different load factors and data sources affect the average number of probes required for insertion. By running experiments with random numbers, dates, and a word list, the program demonstrates how different probing techniques/uses impact efficiency.

## Reflection

This project helped me better understand how hashing actually behaves in practice, especially how different collision resolution strategies perform under different conditions. One thing that worked well was setting up the experiment structure and using the provided scripts to generate results. Once everything was compiling correctly, running the experiments and collecting data was pretty smooth. I also found it interesting to see how drastically the performance changed as the load factor increased.

One challenge I ran into was making was setting up AWS in order to run it in the cloud. It took some time and a lot of help from the TA but I got it set up and working! Another part, that I always struggle with, was making sure the output formatting matched exactly what the test scripts expected. Overall, I enjoyed seeing the real differences between linear probing and double hashing, especially with the word list where linear probing performed much worse.

## Compiling and Using

To compile the program, navigate to the project directory and run:

    javac *.java

To run the program, use the following command:

    java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]

Where:

    <dataSource>:
        1 = Random numbers
        2 = Dates
        3 = Word list
    <loadFactor>: 
        A value between 0.5 and 0.99
    <debugLevel> (optional):
        0 = Print summary results
        1 = Print summary and save hash table dumps to files
        2 = Detailed debugging output
Examples:

    java HashtableExperiment 1 0.75 0
    java HashtableExperiment 3 0.99 1
    java HashtableExperiment 2 0.5 2

## Results 

### Data Source 1: Random Numbers

| Load Factor |Avg Num. of Linear Probing |Avg Num. of Double Hashing |
|-----------------|----------------|----------------|
| 0.50            | 1.49           | 1.38           |
| 0.60            | 1.75           | 1.53           |
| 0.70            | 2.17           | 1.71           |
| 0.80            | 2.94           | 2.01           |
| 0.90            | 5.85           | 2.57           |
| 0.95            | 11.72          | 3.14           |
| 0.99            | 36.77          | 4.62           |

For random numbers, both methods perform similarly at lower load factors, but as the table becomes more full, linear probing requires more probes than double hashing. This shows that double hashing handles collisions more efficiently at higher load factors.

### Data Source 2: Dates

| Load Factor |Avg Num. of Linear Probing |Avg Num. of Double Hashing |
|-----------------|----------------|----------------|
| 0.50            | 1.13           | 1.26           |
| 0.60            | 1.22           | 1.50           |
| 0.70            | 1.29           | 1.77           |
| 0.80            | 1.40           | 2.20           |
| 0.90            | 1.68           | 3.05           |
| 0.95            | 2.10           | 3.66           |
| 0.99            | 5.37           | 5.43           |

For date inputs, linear probing performs better than double hashing across most load factors. Likely due to the  distribution of hash values for dates results in fewer clustering issues, allowing linear probing to remain efficient even as the table fills, as linear probing usely falls behind due to clustering.

### Data Source 3: World-List

| Load Factor | Avg Num. of Linear Probing |Avg Num. of Double Hashing |
|-----------------|----------------|----------------|
| 0.50            | 1.60           | 1.39           |
| 0.60            | 2.15           | 1.53           |
| 0.70            | 3.60           | 1.72           |
| 0.80            | 6.71           | 2.02           |
| 0.90            | 19.81          | 2.57           |
| 0.95            | 110.59         | 3.19           |
| 0.99            | 471.67         | 4.70           |

For the word list data, linear probing performs worse as the load factor increases. I believe this is due to clustering and duplicates. As the table becomes more full, probe sequences become extremely long. Compared to double hashing which distributes keys more evenly and maintains low probe counts even at high load factors.

## Sources used (this is VERY important!)

Apart from the sources in class, and files given, I used a [Geeks For Geeks article](https://www.geeksforgeeks.org/linux-unix/scp-command-in-linux-with-examples/) to assist in some command line things i was struggling with before going to the TA Lab. As I got most of my help directly from the TA Lab.

---