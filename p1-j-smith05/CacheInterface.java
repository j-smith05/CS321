/**
 * A cache interface that defines basic cache operations for storing, adding,
 * removing, and retrieving key–value pairs with a fixed maximum capacity. The cache
 * follows a Most Recently Used (MRU) access policy and a Least Recently Used
 * (LRU) replacement policy. 
 * @author CS321 Instructors & Jacob Smith
 */

public interface CacheInterface<K, V extends KeyInterface<K>> {

    /**
    * Looks up an item in the cache using the given key.
    * If the item is found in the cache (a cache hit), it is returned and moved to
    * the front of the cache to mark it as the most recently used entry. If the item
    * is not found (a cache miss), this method returns null.
    * @param key the key to look up in the cache
    * @return the value associated with the key, or null if not found
    */
    public V get(K key);

    /**
    * Adds a value to the cache as the most recently used entry.
    * If the cache is already full, the least recently used (LRU) item at the
    * end of the cache is removed before the new value is added. If an item is
    * removed due to eviction, it is returned; otherwise null is returned.
    * @param value the value to be added to the cache
    * @return the value that was removed due to eviction, or null if no eviction occurred
    */
    public V add(V value);

    /**
    * Removes the value associated with the given key from the cache.
    * If the key exists in the cache, the corresponding value is removed and
    * returned. If the key is not found, this method returns null.
    * @param key the key whose associated value is to be removed from the cache
    * @return the value that was removed, or null if the key was not found
    */
    public V remove(K key);

    /**
     * Removes all entries from the cache, leaving it empty.
     */
    public void clear();

    /**
     * {@inheritDoc} 
     */
    public String toString();
}
