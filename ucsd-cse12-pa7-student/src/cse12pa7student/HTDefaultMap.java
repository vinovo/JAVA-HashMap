package cse12pa7student;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HTDefaultMap<K, V> implements DefaultMap<K, V> {

	/* Fields */
	private List<Pair<K, V>>[] buckets;
	private V defaultValue;
	private double loadThreshold;
	private int capacity, size, expansionFactor, totalCollisions;
	private Hasher<K> hasher;

	/* Constructor */
	public HTDefaultMap(V defaultValue, int startCapacity, double loadThreshold, int expansionFactor,
			Hasher<K> hasher) {
		if (startCapacity <= 0)
			startCapacity = 1;
		this.defaultValue = defaultValue;
		this.capacity = startCapacity;
		this.loadThreshold = loadThreshold;
		this.expansionFactor = expansionFactor;
		this.hasher = hasher;
		this.size = 0;
		buckets = new List[startCapacity];
		this.totalCollisions = 0;
	}

	/* Fill in these methods */
	@Override
	public void set(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		else {
			Pair<K, V> newPair = new Pair<K, V>(key, value);
			int hashCode = hashToBuckets(key);
			if (buckets[hashCode] == null) {
				buckets[hashCode] = new ArrayList<Pair<K, V>>();
				buckets[hashCode].add(newPair);
			} else {
				boolean found = false;
				for (int i = 0; i < buckets[hashCode].size(); i++) {
					// if the key already exists.
					if (buckets[hashCode].get(i).key.equals(key)) {
						buckets[hashCode].set(i, newPair);
						found = true;
						this.size--;
					}
				}
				if (!found) {
					buckets[hashCode].add(newPair);
					this.totalCollisions++;
				}
			}
			this.size++;
			if (currentLoadFactor() >= loadThreshold)
				rehash();
		}
	}

	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		else {
			int hashCode = hashToBuckets(key);
			if (buckets[hashCode] != null) {
				for (Pair<K, V> p : buckets[hashCode]) {
					if (p.key.equals(key)){
						return p.value;
					}
				}
			}
			if (this.defaultValue == null)
				throw new NoSuchElementException("key not found!");
			else 
				return this.defaultValue;
		}
	}

	@Override
	public boolean containsKey(K key) {
		if (key == null)
			return false;
		if (size == 0)
			return false;
		int hashCode = hashToBuckets(key);
		if (buckets[hashCode] == null)
			return false;
		for (Pair<K,V> p : buckets[hashCode]){
			if (p.key.equals(key))
				return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public V defaultValue() {
		return this.defaultValue;
	}

	@Override
	public List<K> keys() {
		List<K> keyList = new ArrayList<K>();
		for (List<Pair<K,V>> lst : buckets){
			if (lst != null){
				for (Pair<K,V> p : lst){
					keyList.add(p.key);
				}
			}
		}
		return keyList;
	}

	@Override
	public List<V> values() {
		List<V> valueList = new ArrayList<V>();
		for (List<Pair<K,V>> lst : buckets){
			if (lst != null){
				for (Pair<K,V> p : lst){
					valueList.add(p.value);
				}
			}
		}
		return valueList;
	}

	public int totalCollisions() {
		return this.totalCollisions;
	}

	public int currentCapacity() {
		return this.capacity;
	}

	public double currentLoadFactor() {
		return (double) this.size / this.capacity;
	}

	public int deepestChain() {
		int maxDepth = 0;
		for (List<Pair<K,V>> lst : buckets){
			if (lst != null && lst.size() > maxDepth){
				maxDepth = lst.size();
			}
		}
		return maxDepth;
	}

	// Helper method that hash key to bucket.
	private int hashToBuckets(K key) {
		int hashCode;
		if (hasher == null)
			hashCode = Math.abs(key.hashCode());
		else
			hashCode = Math.abs(hasher.hash(key));
		return hashCode % capacity;
	}

	// Helper method to rehash
	private void rehash() {
		this.capacity = this.capacity * this.expansionFactor;
		List<Pair<K, V>>[] newBuckets = new List[this.capacity];
		for (List<Pair<K, V>> b : this.buckets) {
			if (b != null) {
				for (Pair<K, V> p : b) {
					int hashCode = hashToBuckets(p.key);
					if (newBuckets[hashCode] == null)
						newBuckets[hashCode] = new ArrayList<Pair<K, V>>();
					else
						this.totalCollisions++;
					newBuckets[hashCode].add(p);
				}
			}
		}
		buckets = newBuckets;
	}

}
