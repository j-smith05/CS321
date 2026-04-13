/**
 * HashObject class represents an object stored in a hash table.
 * It contains the key, frequency of occurrences, and probe count.
 * The equals and hashCode methods are overridden to ensure that 
 * HashObjects are compared based on their keys.
 *
 * @author Jacob Smith
 */
public class HashObject {

    private final Object key;
    private int frequency;
    private int probeCount;

    /**
     * Constructs a new HashObject with the specified key.
     * @param key the key for the object
     */
    public HashObject(Object key) {
        this.key = key;
        this.frequency = 1;
        this.probeCount = 0;
    }

    /**
     * Returns the key for this hash object.
     * @return the key
     */
    public Object getKey() {
        return key;
    }

    /**
     * Returns the frequency of this key.
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Returns the probe count for this object.
     * @return the probe count
     */
    public int getProbeCount() {
        return probeCount;
    }

    /**
     * Increments the frequency. 
     */
    public void incrementFrequency() {
        frequency++;
    }

    /**
     * Sets the probe count.
     * @param probeCount the probe count
     */
    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }

    /**
     * Compares this object with another HashObject.
     * Two HashObjects are considered equal if their keys are equal.
     * @param obj the other object
     * @return true if keys are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashObject)) {
            return false;
        }

        HashObject other = (HashObject) obj;
        return key.equals(other.key);
    }

    /**
     * Returns hash code based on the key. 
     * @return the key's hash code
     */
    @Override
    public int hashCode() {
        return key.hashCode();
    }

    /**
     * Returns string representation in required dump format.
     * @return key frequency probeCount
     */
    @Override
    public String toString() {
        return key + " " + frequency + " " + probeCount;
    }
}