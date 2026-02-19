import java.util.HashMap;

class Solution {
    public int longestSubarray(int[] arr, int k) {
        
        HashMap<Long, Integer> map = new HashMap<>();
        
        long prefixSum = 0;
        int maxLen = 0;

        for (int i = 0; i < arr.length; i++) {
            
            prefixSum += arr[i];

            if (prefixSum == k) {
                maxLen = i + 1;
            }

            if (map.containsKey(prefixSum - k)) {
                int len = i - map.get(prefixSum - k);
                maxLen = Math.max(maxLen, len);
            }

            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            }
        }

        return maxLen;
    }
}
