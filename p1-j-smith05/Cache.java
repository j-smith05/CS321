import java.util.Iterator;
import java.util.LinkedList;

/**
 * A cache implementation that stores key-value pairs.
 * Each value must implement the KeyInterface to provide a key.
 * This cache has a fixed maximum size and uses a Least Recently Used (LRU)
 * eviction policy when the cache is full. 
 * 
 * @author Jacob Smith
 **/

public class Cache<K, V extends KeyInterface<K>> implements CacheInterface<K, V> {
	
	// Maximum size of the cache
	private final int maxSize;
	private final LinkedList<V> cacheLL;
	
	// Statistics tracking
	private long references;
	private long hits;

	// Constructor to initialize the cache with a maximum size
	public Cache(int maxSize) {
		this.maxSize = maxSize;
		this.cacheLL = new LinkedList<>();
		this.references = 0;
		this.hits = 0;
	}


	@Override
	public V get(K key) {
        references++;

        if (key == null) return null;

        // Search for matching key
        Iterator<V> it = cacheLL.iterator();
        while (it.hasNext()) {
            V value = it.next();
            if (key.equals(value.getKey())) {
                // Cache hit: remove from current position, move to front (MRU)
                it.remove();
                cacheLL.addFirst(value);
                hits++;
                return value;
            }
		}
		// Cache miss
		return null;

	}

	@Override
	public V add(V value) {
		if (value == null) return null;

		// Remove any existing entry with the same key (avoid duplicates)
		remove(value.getKey());

		V evicted = null;

		// If full, evict LRU (last)
		if (cacheLL.size() >= maxSize) {
			evicted = cacheLL.removeLast();
		}

		// Add as MRU (first)
		cacheLL.addFirst(value);

		return evicted;
	}

	@Override
	public V remove(K key) {
        if (key == null) return null; //quick null check

        Iterator<V> it = cacheLL.iterator(); // use iterator to allow removal during iteration
        while (it.hasNext()) {
            V value = it.next();
            if (key.equals(value.getKey())) {
                it.remove();
                return value; // Return the removed value
            }
        }
        return null; // Return null if the key was not found

	}

	@Override
	public void clear() {
		cacheLL.clear(); // Clear the linked list
	}

	@Override
	public String toString() {
        double hitPercent = 0.0;

		if (references > 0) {
			hitPercent = (100.0 * hits) / references; // Calculate hit percentage
		}        
		return "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" // Prints out cache statistics
				+ "Cache with " + maxSize + " entries has been created\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+ String.format("Total number of references:%12d\n", references)
				+ String.format("Total number of cache hits:%12d\n", hits)
				+ String.format("Cache hit percent:%17.2f%%\n", hitPercent);
		}
	}

