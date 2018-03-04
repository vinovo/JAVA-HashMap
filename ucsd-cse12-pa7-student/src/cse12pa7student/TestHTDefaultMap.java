package cse12pa7student;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.*;

public class TestHTDefaultMap {

	class customHasher<K> implements Hasher<K> {
		@Override
		public int hash(K k) {
			String a = k.toString();
			return a.length();
		}

	}

	Hasher<String> hasher = new customHasher<String>();

	// ** Put your JUnit tests here **//
	@Test
	public void testSetAndGet() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(10000, 1, 0.75, 2, hasher);
		ht.set("red", 281);
		assertEquals((Integer) ht.get("red"), (Integer) 281);
		ht.set("", 0);
		assertEquals((Integer) ht.get(""), (Integer) 0);
		ht.set("blue", 322);
		assertEquals((Integer) ht.get("blue"), (Integer) 322);
		ht.set("teal", 99);
		assertEquals((Integer) ht.get("teal"), (Integer) 99);
		ht.set("red", 81);
		assertEquals((Integer) ht.get("red"), (Integer) 81);
	}

	@Test
	public void getNull() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(0, 5, 1, 2, hasher);
		ht.set("A", null);
		assertNull(ht.get("A"));
	}

	@Test(expected = NoSuchElementException.class)
	public void nullDefaultValue() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 5, 0.1, 5, null);
		ht.get("A");
	}

	@Test
	public void testContainsKey() {
		HTDefaultMap<String, Integer> empty = new HTDefaultMap<String, Integer>(null, 0, 0, 0, null);
		assertFalse(empty.containsKey("a"));
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 0.7, 3, null);
		assertFalse(ht.containsKey(""));
		ht.set("abc", 3);
		assertFalse(ht.containsKey("abcdefgh"));
		for (int i = 65; i < 91; i++) {
			ht.set((char) i + "", i);
		}
		assertTrue(ht.containsKey("Z"));
		ht.set("Z", 65538);
		assertTrue(ht.containsKey("Z"));
		assertTrue(ht.containsKey("H"));
		assertFalse(ht.containsKey("!@#$%^&(*)"));
	}

	@Test
	public void testSize() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 0.7, 3, null);
		assertEquals(0, ht.size());
		for (int i = 65; i < 91; i++) {
			ht.set((char) i + "", i);
		}
		assertEquals(26, ht.size());
		ht.set("a", 1);
		ht.set("Z", 26); // should not change the size
		assertEquals(27, ht.size());
	}

	@Test
	public void testDefaultValue() {
		HTDefaultMap<String, Integer> ht1 = new HTDefaultMap<String, Integer>(null, 4, 0.7, 3, null);
		assertNull(ht1.defaultValue());
		HTDefaultMap<String, Integer> ht2 = new HTDefaultMap<String, Integer>(0, 4, 0.7, 3, null);
		assertEquals((Integer) 0, (Integer) ht2.defaultValue());
		HTDefaultMap<String, Integer> ht3 = new HTDefaultMap<String, Integer>(1000000, 4, 0.7, 3, null);
		assertEquals((Integer) 1000000, (Integer) ht3.defaultValue());
	}

	@Test
	public void testKeys() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 0.7, 3, null);
		for (int i = 65; i < 91; i++) {
			ht.set((char) i + "", i);
		}
		List<String> lst = ht.keys();
		assertEquals(ht.size(), lst.size());
		for (int i = 65; i < 91; i++) {
			assertTrue(lst.contains((char) i + ""));
		}
		assertFalse(lst.contains("anythingFancy"));
	}

	@Test
	public void testValues() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 0.7, 3, null);
		for (int i = 65; i < 91; i++) {
			ht.set((char) i + "", i);
		}
		List<Integer> lst = ht.values();
		assertEquals(ht.size(), lst.size());
		for (int i = 65; i < 91; i++) {
			assertTrue(lst.contains((i)));
		}
		assertFalse(lst.contains(100000));
	}

	@Test
	public void testTotalCollisions() {
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 1, 2, hasher);
		ht.set("aa", 2);
		ht.set("abcdef", 6);
		assertEquals(1, ht.totalCollisions());
		ht.set("ad", 2);
		assertEquals(2, ht.totalCollisions());
		ht.set("bc", 2);
		//expecting rehash here
		assertEquals(5, ht.totalCollisions());
		ht.set("ae", 2);
		// capacity is now 8, "abcdef" no longer collides with Strings of length
		// 2
		assertEquals(6, ht.totalCollisions());
		ht.set("a", 100);
		assertEquals(6, ht.totalCollisions());
		ht.set("a", 1);
		assertEquals(7, ht.totalCollisions());
		ht.set("c", 1);
		ht.set("d", 1);
		//expecting rehash here. Before rehash, the total collisions should be 9
		//during rehash, 5 collisions occur
		assertEquals(14, ht.totalCollisions());
	}
	
	@Test
	public void currentCapacity() {
		HTDefaultMap<String, Integer> ht1 = new HTDefaultMap<String, Integer>(null, 1, 0.7, 3, null);
		assertEquals(1, ht1.currentCapacity());
		ht1.set("a", 1);
		assertEquals(3, ht1.currentCapacity());
		ht1.set("b", 2);
		assertEquals(3, ht1.currentCapacity());
		ht1.set("b", 222);
		// loadFactor is 2/3, less than 0.7
		assertEquals(3, ht1.currentCapacity());
		ht1.set("c", 3);
		assertEquals(9, ht1.currentCapacity());
		ht1.set("d", 4);
		ht1.set("e", 5);
		ht1.set("f", 6);
		// current LoadFactor is 6/9, less than 0.7
		assertEquals(9, ht1.currentCapacity());
		ht1.set("g", 7);
		assertEquals(27, ht1.currentCapacity());
		HTDefaultMap<String, Integer> ht2 = new HTDefaultMap<String, Integer>(null, 2, 1, 2, null);
		assertEquals(2, ht2.currentCapacity());
		ht2.set("a", 1);
		ht2.set("b", 2);
		assertEquals(4, ht2.currentCapacity());
		ht2.set("c", 3);
		ht2.set("d", 4);
		assertEquals(8, ht2.currentCapacity());
		for (int i = 65; i < 91; i++) {
			ht2.set((char) i + "", i);
		}
		// At this line there should be 30 keys in the map. The capacity should
		// have been updated to 16*2
		assertEquals(32, ht2.currentCapacity());
		ht2.set("This PA", 31);
		ht2.set("is hard", 32);
		assertEquals(64, ht2.currentCapacity());
	}
	
	@Test
	public void testCurrentLoadFactor(){
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 1, 2, hasher);
		assertEquals(0.0, ht.currentLoadFactor(), 0);
		ht.set("a", 1);
		ht.set("ab", 2);
		assertEquals(0.5, ht.currentLoadFactor(), 0);
		ht.set("abc", 3);
		assertEquals(0.75, ht.currentLoadFactor(), 0);
		ht.set("abcd", 4);
		assertEquals(0.5, ht.currentLoadFactor(), 0);
	}
	
	@Test
	public void testDeepestChain(){
		HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(null, 4, 1, 2, hasher);
		ht.set("somekey", 7);
		ht.set("abc", 3);
		ht.set("abcd", 4);
		assertEquals(2, ht.deepestChain());
		for (int i = 65; i < 91; i++) {
			ht.set((char) i + "", i);
		}
		assertEquals(26, ht.deepestChain());
	}
}
