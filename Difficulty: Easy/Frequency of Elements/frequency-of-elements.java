class Solution {
    public ArrayList<ArrayList<Integer>> countFreq(int[] arr) {
        
        // Step 1: Create map
        Map<Integer, Integer> map = new HashMap<>();
        
        // Step 2: Count frequencies
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        // Step 3: Sort keys (since expected output is sorted)
        ArrayList<Integer> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        
        // Step 4: Build result
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        
        for (int key : keys) {
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(key);
            pair.add(map.get(key));
            result.add(pair);
        }
        
        return result;
    }
}