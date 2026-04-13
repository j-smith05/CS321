/**
 * Double hashing hash table implementation.
 * @author Jacob Smith
 */
public class DoubleHashing extends Hashtable {

    /**
     * Constructs a double hashing table.
     *
     * @param size the table size
     */
    public DoubleHashing(int size) {
        super(size);
    }

    /**
     * Double hashing probe:
     * h2(key) = 1 + positiveMod(key.hashCode(), size - 2)
     *
     * @param i probe number
     * @param key the key
     * @return probe offset
     */
    @Override
    protected int probe(int i, Object key) {
        int h2 = 1 + positiveMod(key.hashCode(), size - 2);
        return i * h2;
    }
}