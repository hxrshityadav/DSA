import java.util.Arrays;

class Solution {
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;
        // dp[i] stores the maximum jumps to reach index i. 
        // Initialize with -1 to indicate unreachable.
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        
        // Base case: 0 jumps to reach the start
        dp[0] = 0;
        
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < j; i++) {
                // If index i is reachable AND the jump condition is met
                if (dp[i] != -1 && Math.abs(nums[j] - nums[i]) <= target) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
        }
        
        return dp[n-1];
    }
}
