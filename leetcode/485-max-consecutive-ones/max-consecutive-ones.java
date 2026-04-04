class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int currentCount = 0;
        int maxCount = 0;

        for(int i = 0; i< nums.length; i++) {
            int num = nums[i];
            if(num == 1){
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);   
            } else {
                currentCount = 0;
            }
        }
        return maxCount;
    }
}