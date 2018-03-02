package cse12pa7student;

import java.util.List;

public class HTDefaultMap<K,V> implements DefaultMap<K,V> {

	/* Fields */
	private List<Pair<K,V>>[] buckets;

	/* Constructor */
	public HTDefaultMap (V defaultValue, int capacity, double loadFactor, int expansionFactor, Hasher<K> hasher) {
	}
		
	/* Fill in these methods */
	@Override
	public void set(K key, V value) {
	}

	@Override
	public V get(K key) {
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public V defaultValue() {
		return null;
	}

	@Override
	public List<K> keys() {
		return null;
	}

	@Override
	public List<V> values() {
		return null;
	}
	
	public int totalCollisions() {
		return 0;
	}
	
	public int currentCapacity() {
		return 0;
	}
	
	public double currentLoadFactor() {
		return 0.0;
	}
	
	public int deepestChain() {
		return 0;
	}
	
}
