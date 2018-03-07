package cse12pa7student;

public class Experiment {
	public static void main(String[] args) {
		System.out.println("InitialCapacity,TotalCollisions");
		int initCapacity = 1;
		for (int i = 0; i < 8; i++) {
				HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(0, initCapacity, 0.75, 4, null);
				for (int k = 0; k < 1000000; k++) {
					for (int j = 0; j < 10; j++) {
						ht.set(k + "", k);
					}
				}
				System.out.println(initCapacity + "," + ht.totalCollisions());
		}
	}
	
	public static void main(String[] args){
		System.out.println("expansionFactor,timeTaken");
		for (int i = 2; i <= 11; i++){
			HTDefaultMap<String, Integer> ht = new HTDefaultMap<String, Integer>(0, 40, 0.66, i, null);
			for (int k = 0; k < 1000000; k++) {
				for (int j = 0; j < 10; j++) {
					ht.set(k + "", k);
				}
			}
			long start = System.nanoTime();
			for (int j = 0; j < 10000; j++){
				ht.get(j+"");
			}
			long end = System.nanoTime();
			System.out.print(i + ",");
			System.out.println((double) (end - start)/1000000); 
		}
	}
	
	public static void main(String[] args){
		customHasher<Integer> hasher = new Experiment.customHasher<Integer>();
		double loadFactor = 0.1;
		System.out.println("loadFactor,totalCollisions");
		for (int i = 0; i < 17; i++){
			loadFactor += 0.05;
			HTDefaultMap<Integer, String> ht = new HTDefaultMap<Integer, String>(0+"", 4, loadFactor, 2, hasher);
			for (int j = 1; j <= 1000000; j++){
				for (int k = 0; k < 10; k++){
					ht.set(j, j + "");
				}
			}
			System.out.println(loadFactor + "," + ht.totalCollisions());
		}
		
	}
	
	static class customHasher<K> implements Hasher<K> {
		@Override
		public int hash(K k) {
			if (k instanceof Integer)
				return (Integer) k;
			else{
				String a = k.toString();
				return a.length();
			}
		}
	}
		
}
