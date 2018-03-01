package cse12pa7student;

import java.util.List;

public interface DefaultMap<K, V> {
	/*
	 * Sets key to hold value: future calls to get() should find the given key and
	 * size increases by 1.
	 * 
	 * Throws IllegalArgumentException if key is null (there is no such restriction
	 * for value)
	 * 
	 */
	void set(K key, V value);

	/*
	 * Returns the value associated with key if it has been set
	 *
	 * If the defaultValue is null and the key is not found, throws
	 * NoSuchElementException
	 * 
	 * If key has not been set and defaultValue is non-null, returns a default value
	 * 
	 * Throws IllegalArgumentException if key is null
	 */
	V get(K key);
	
	/*
	 * Returns true if the given key was set by set, false otherwise
	 */
	boolean containsKey(K key);

	/*
	 * Returns the number of key/value pairs in the tree
	 */
	int size();

	/*
	 * Returns the default value for this map
	 */
	V defaultValue();

	/*
	 * Returns a list of all keys in ANY order
	 */
	List<K> keys();

	/*
	 * Returns a list of all values in ANY order
	 */
	List<V> values();

}
