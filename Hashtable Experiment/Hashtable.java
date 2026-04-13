import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Abstract class representing a hash table with open addressing. 
 * It provides common functionality for different probing strategies, 
 * such as linear probing and double hashing.
 * The class maintains statistics about total inputs, duplicates, unique entries, and probes for new insertions.
 *
 * @author Jacob Smith
 */
public abstract class Hashtable {

    protected HashObject[] table;
    protected int size;

    protected int totalInputs;
    protected int duplicateCount;
    protected int uniqueCount;
    protected int totalProbes;

    /**
     * Constructs a hash table with the given size.
     *
     * @param size the size of the table
     */
    public Hashtable(int size) {
        this.size = size;
        this.table = new HashObject[size];
        this.totalInputs = 0;
        this.duplicateCount = 0;
        this.uniqueCount = 0;
        this.totalProbes = 0;
    }

    /**
     * Returns positive modulus result. This is used to ensure 
     * that hash indices are non-negative.
     * @param dividend the dividend
     * @param divisor the divisor
     * @return positive modulus
     */
    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }

    /**
     * Primary hash function h1(key) = positiveMod(key.hashCode(), size)
     * @param key the key
     * @return primary hash index
     */
    protected int hash(Object key) {
        return positiveMod(key.hashCode(), size);
    }

    /**
     * Inserts the given key.
     * Returns true if it was a new insertion, false if duplicate.
     * Probes are counted only for new insertions. Duplicate insertions 
     * do not affect probe counts or unique counts.
    *
     * @param key the key to insert
     * @return true if new insertion, false if duplicate
     */
    public boolean insert(Object key) {
        totalInputs++;
        int index = hash(key);
        int i = 0;

        while (i < size) { // Loop until we find an empty slot or the key
            int currentIndex = positiveMod(index + probe(i, key), size);

            if (table[currentIndex] == null) { // Empty slot found, insert new object
                HashObject newObject = new HashObject(key);
                newObject.setProbeCount(i + 1);
                table[currentIndex] = newObject;

                uniqueCount++;
                totalProbes += (i + 1);
                return true;
            }

            if (table[currentIndex].getKey().equals(key)) { // Duplicate key found, increment frequency
                table[currentIndex].incrementFrequency();
                duplicateCount++;
                return false;
            }

            i++;
        }

        throw new IllegalStateException("Hash table is full.");
    }

    /**
     * Searches for a key in the table.
     *
     * @param key the key to search for
     * @return true if found, false otherwise
     */
    public boolean search(Object key) {
        int index = hash(key);
        int i = 0;

        while (i < size) {
            int currentIndex = positiveMod(index + probe(i, key), size);

            if (table[currentIndex] == null) {
                return false;
            }

            if (table[currentIndex].getKey().equals(key)) {
                return true;
            }

            i++;
        }

        return false;
    }

    /**
     * Dumps non-empty table entries to a file.
     *
     * @param fileName the file name
     * @throws IOException if writing fails
     */
    public void dumpToFile(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                out.println("table[" + i + "]: " + table[i]);
            }
        }

        out.close();
    }

    /**
     * Returns the HashObject at the given index.
     *
     * @param index the index
     * @return the object at that index
     */
    public HashObject get(int index) {
        return table[index];
    }

    /**
     * Returns the size of the table.
     *
     * @return table size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns total number of inputs processed.
     *
     * @return total inputs
     */
    public int getTotalInputs() {
        return totalInputs;
    }

    /**
     * Returns duplicate count.
     *
     * @return duplicate count
     */
    public int getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Returns unique count.
     *
     * @return unique count
     */
    public int getUniqueCount() {
        return uniqueCount;
    }

    /**
     * Returns total probes for new insertions only.
     *
     * @return total probes
     */
    public int getTotalProbes() {
        return totalProbes;
    }

    /**
     * Returns average probes for new insertions.
     *
     * @return average probes
     */
    public double getAverageProbes() {
        if (uniqueCount == 0) {
            return 0.0;
        }
        return (double) totalProbes / uniqueCount;
    }

    /**
     * Probe function implemented by subclasses.
     *
     * @param i probe number
     * @param key the key
     * @return probe offset
     */
    protected abstract int probe(int i, Object key);
}