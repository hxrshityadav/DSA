import java.util.HashSet;

class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        HashSet<Integer> prefixes = new HashSet<>();
        
        for (int val : arr1) {
            while (val > 0) {
                prefixes.add(val);
                val /= 10; 
            }
        }
        
        int maxLength = 0;
        
        for (int val : arr2) {
            while (val > 0) {
                if (prefixes.contains(val)) {                  
                    maxLength = Math.max(maxLength, String.valueOf(val).length());
                    break; 
                }
                val /= 10;
            }
        }
        return maxLength;
    }
}
