import java.util.Arrays;

class Solution {
    public int maxFrequency(int[] nums, int k) {
        
        Arrays.sort(nums);

        int left = 0;
        int ans = 1;

        long windowSum = 0;

        for(int right =0; right < nums.length; right++) {
           
            windowSum += nums[right];

            while((long) nums[right] * (right - left + 1) - windowSum > k){
                windowSum -= nums[left];
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}