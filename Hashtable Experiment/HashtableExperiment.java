import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
* Driver program for hashing experiments.
 * It runs experiments for linear probing and double 
 * hashing with different input sources and load factors.
 * Usage:
 * java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]
 *
 * @author Jacob Smith
 */
public class HashtableExperiment {

    private static final int MIN_CAPACITY = 95500;
    private static final int MAX_CAPACITY = 96000;
    private static final String WORD_FILE = "word-list.txt";
    private static final Random RAND = new Random();

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            return;
        }

        int dataSource;
        double loadFactor;
        int debugLevel = 0;
        
        try { // Parse command-line arguments
            dataSource = Integer.parseInt(args[0]);
            loadFactor = Double.parseDouble(args[1]);

            if (args.length == 3) {
                debugLevel = Integer.parseInt(args[2]);
            }
        } catch (NumberFormatException e) {
            printUsage();
            return;
        }

        if (dataSource < 1 || dataSource > 3) {
            printUsage();
            return;
        }

        if (debugLevel < 0 || debugLevel > 2) {
            printUsage();
            return;
        }

        if (loadFactor < 0 || loadFactor > 1) {
            printUsage();
            return;
        }

        int tableCapacity = TwinPrimeGenerator.generateTwinPrime(MIN_CAPACITY, MAX_CAPACITY);
        if (tableCapacity == -1) {
            System.out.println("HashtableExperiment: Could not find a twin prime table capacity.");
            return;
        }

        int targetUnique = (int) Math.ceil(loadFactor * tableCapacity);

        System.out.println("HashtableExperiment: Found a twin prime for table capacity: " + tableCapacity);
        System.out.println("HashtableExperiment: Input: " + inputName(dataSource)
                + "   Loadfactor: " + String.format("%.2f", loadFactor));
        System.out.println();

        LinearProbing linearTable = new LinearProbing(tableCapacity);
        DoubleHashing doubleTable = new DoubleHashing(tableCapacity);

        try {
            runExperiment(dataSource, targetUnique, debugLevel, linearTable, doubleTable);
        } catch (IOException e) {
            System.out.println("Error: Could not read input source.");
            return;
        }

        printTableResults("Linear Probing", linearTable, targetUnique, debugLevel);
        printTableResults("Double Hashing", doubleTable, targetUnique, debugLevel);
    }

    /**
     * Runs the experiment until target unique insertions are reached.
     *
     * @param dataSource the input source
     * @param targetUnique target number of unique insertions
     * @param debugLevel debug level
     * @param linearTable linear probing table
     * @param doubleTable double hashing table
     * @throws IOException if file reading fails
     */
    private static void runExperiment(int dataSource, int targetUnique, int debugLevel,
                                      LinearProbing linearTable, DoubleHashing doubleTable)
            throws IOException {

        switch (dataSource) {
            case 1:
                runRandomExperiment(targetUnique, debugLevel, linearTable, doubleTable);
                break;
            case 2:
                runDateExperiment(targetUnique, debugLevel, linearTable, doubleTable);
                break;
            case 3:
                runWordExperiment(targetUnique, debugLevel, linearTable, doubleTable);
                break;
            default:
                break;
        }
    }

    /**
     * Data source 1: random Integer values. 
     */
    private static void runRandomExperiment(int targetUnique, int debugLevel,
                                            LinearProbing linearTable, DoubleHashing doubleTable) {
        while (linearTable.getUniqueCount() < targetUnique) {
            Integer key = Integer.valueOf(RAND.nextInt());
            boolean insertedLinear = linearTable.insert(key);
            doubleTable.insert(key);

            if (debugLevel == 2) {
                printDebug(key, insertedLinear);
            }
        }
    }

    /**
     * Data source 2: Date objects separated by 1 second.
     */
    private static void runDateExperiment(int targetUnique, int debugLevel,
                                          LinearProbing linearTable, DoubleHashing doubleTable) {
        long current = new Date().getTime();

        while (linearTable.getUniqueCount() < targetUnique) {
            Date key = new Date(current);
            boolean insertedLinear = linearTable.insert(key);
            doubleTable.insert(key);

            if (debugLevel == 2) {
                printDebug(key, insertedLinear);
            }

            current += 1000;
        }
    }

    /**
     * Data source 3: words from word-list.txt
     */
    private static void runWordExperiment(int targetUnique, int debugLevel,
                                          LinearProbing linearTable, DoubleHashing doubleTable)
            throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(WORD_FILE));
        String line;

        while (linearTable.getUniqueCount() < targetUnique && (line = reader.readLine()) != null) {
            String key = line.trim();
            boolean insertedLinear = linearTable.insert(key);
            doubleTable.insert(key);

            if (debugLevel == 2) {
                printDebug(key, insertedLinear);
            }
        }

        reader.close();
    }

    /**
     * Prints per-element debugging info.
     *
     * @param key the key
     * @param inserted true if inserted, false if duplicate
     */
    private static void printDebug(Object key, boolean inserted) {
        if (inserted) {
            System.out.println("Inserted: " + key);
        } else {
            System.out.println("Duplicate: " + key);
        }
    }

    /**
     * Prints summary and optionally dump file.
     *
     * @param methodName method name
     * @param table the table
     * @param targetUnique target unique insertions
     * @param debugLevel debug level
     */
    private static void printTableResults(String methodName, Hashtable table,
                                          int targetUnique, int debugLevel) {
        System.out.println("\tUsing " + methodName);
        System.out.println("HashtableExperiment: size of hash table is " + targetUnique);
        System.out.println("\tInserted " + table.getTotalInputs()
                + " elements, of which " + table.getDuplicateCount() + " were duplicates");
        System.out.printf("\tAvg. no. of probes = %.2f%n", table.getAverageProbes());

        if (debugLevel == 1) {
            try {
                if (methodName.equals("Linear Probing")) {
                    table.dumpToFile("linear-dump.txt");
                } else {
                    table.dumpToFile("double-dump.txt");
                }
                System.out.println("HashtableExperiment: Saved dump of hash table");
            } catch (IOException e) {
                System.out.println("Error: Could not save dump file.");
            }
        }

        System.out.println();
    }

    /**
     * Returns input source name.
     *
     * @param dataSource source number
     * @return source name
     */
    private static String inputName(int dataSource) {
        switch (dataSource) {
            case 1:
                return "Random Numbers";
            case 2:
                return "Dates";
            case 3:
                return "Word-List";
            default:
                return "Unknown";
        }
    }

    /**
     * Prints usage message.
     */
    private static void printUsage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println(" <dataSource>: 1 ==> random numbers");
        System.out.println("               2 ==> date value as a long");
        System.out.println("               3 ==> word list");
        System.out.println(" <loadFactor>: The ratio of objects to table size,");
        System.out.println("               denoted by alpha = n/m");
        System.out.println(" <debugLevel>: 0 ==> print summary of experiment");
        System.out.println("               1 ==> save the two hash tables to a file at the end");
        System.out.println("               2 ==> print debugging output for each insert");
    }
}