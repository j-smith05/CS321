/**
 * This class extends the abstract Hashtable class 
 * and implements linear probing for collision resolution.
 * @author Jacob Smith
 */
public class LinearProbing extends Hashtable {

    /**
     * Constructs a linear probing hash table.
     * @param size the table size
     */
    public LinearProbing(int size) {
        super(size);
    }

    /**
     * Linear probing uses offset i.
     * @param i probe number
     * @param key the key
     * @return probe offset
     */
    @Override
    protected int probe(int i, Object key) {
        return i;
    }
}