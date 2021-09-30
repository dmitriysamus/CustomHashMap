/**
 * Custom hash map.
 */
public interface CustomHashMap<K, V> {

    int size();

    boolean isEmpty();

    /**
     * Get item by key.
     *
     * @param key - key
     */
    V get(K key);

    /**
     * Add value by key.
     *
     * @return previous value or null
     */
    V put(K key, V value);

    /**
     * Remove item by key
     *
     * @param key - key
     * @return deleted item or null if item with key doesn't exists
     */
    V remove(K key);

    /**
     * Checks if item exists by key.
     *
     * @param key - key
     * @return true or false
     */
    boolean containsKey(K key);

    /**
     * Checks if item exists by value.
     *
     * @param value - item
     * @return true or false
     */
    boolean containsValue(V value);

    /**
     * Get all keys.
     */
    Object[] keys();

    /**
     * Get all values.
     */
    Object[] values();

    /**
     * Get content in format '[ (key1, value1) ... (keyN, valueN) ] or [ ] if empty.
     */
    String toString();
}
