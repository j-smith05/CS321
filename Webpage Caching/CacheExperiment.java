/**
 * 
 * A program to experiment with caching Webpages. Takes in command line arguments 
 * for cache size, number of Webpages to generate, standard deviation for URL 
 * generation, debug level, and an optional seed for random number generation. 
 * then simulates accessing Webpages and caching them. Finally, it prints out 
 * cache statistics and timing information. 
 * @author Jacob Smith
 */

public class CacheExperiment {
    public static void main(String[] args) {
        if (args.length != 4 && args.length != 5) {
            System.err.println("Usage: java CacheExperiment <cache-size> <number-of-Webpages> "
                    + "<standard-deviation> <debug-level=0-3> [<seed>]");
            System.exit(1);
        }
        // Parsing command line arguments
        int cacheSize = Integer.parseInt(args[0]);
        int numLookups = Integer.parseInt(args[1]);
        double stddev = Double.parseDouble(args[2]);
        int debugLevel = Integer.parseInt(args[3]);

        Long seed = null;

        // Optional seed argument
        if (args.length == 5) {
            seed = Long.parseLong(args[4]);
        }

        long start = System.currentTimeMillis();

        // Initialize Webpage generator
        WebpageGenerator generator;

        // Create generator with or without seed
        if (seed == null) {
            generator = new WebpageGenerator(numLookups, stddev);
        } else {
            generator = new WebpageGenerator(numLookups, stddev, seed);
        }

        // Create cache
        Cache<String, Webpage> cache = new Cache<>(cacheSize);

        // Simulate accessing Webpages
        for (int i = 0; i < numLookups; i++) {
            String url = generator.getURL();

            Webpage page = cache.get(url);
            if (page == null) {
                page = generator.readPage(url);
                cache.add(page);
            }
        }


        // Print debug information based on debug level
        if (debugLevel == 1) {
            System.out.println("--------------------------------------");
            System.out.println("Printing the Webpage Distribution:");
            System.out.println("--------------------------------------");
            generator.getWebpageDatabasePings();
        } else if (debugLevel == 2 || debugLevel == 3) {
            System.out.println("============================================");
            System.out.println("Generated and serialized Webpages");
            System.out.println("============================================");
            generator.printWebpages(debugLevel); // 2 = summary, 3 = full
        }
        
        // Print cache statistics
        System.out.print(cache.toString());

        // Print timing information
        long end = System.currentTimeMillis();
        System.out.println("----------------------------------------------------------------");
        System.out.printf("Time elapsed: %.1f milliseconds%n", (double) (end - start));
        System.out.println("----------------------------------------------------------------");
    }
}
