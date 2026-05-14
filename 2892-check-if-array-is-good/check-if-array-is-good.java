import java.util.Arrays;

class Solution {
    public boolean isGood(int[] nums) {
        int n = nums.length - 1;
        int[] freq =new int[201];
        
        int maxVal = 0;
        for (int num : nums) {
            if (num > 200) return false; 
            freq[num]++;
            maxVal = Math.max(maxVal, num);
        }
        
        if (maxVal != n) return false;
        
        for (int i = 1; i < n; i++) {
            if (freq[i] != 1) return false;
        }
        
        return freq[n] == 2;
    }
}
