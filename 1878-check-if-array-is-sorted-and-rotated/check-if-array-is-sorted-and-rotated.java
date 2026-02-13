class Solution {
    public boolean check(int[] nums) {
        int len = nums.length;
        int dev = 0;

        for (int i = 1; i < len; i++) {
            if (nums[i] < nums[i - 1]) {
                dev++;
            }
        }

       
        if (nums[0] < nums[len - 1]) {
            dev++;
        }

        return dev <= 1;
    }
}
