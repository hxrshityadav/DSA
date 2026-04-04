class Solution {
    public int findMin(int[] nums) {
    int low = 0, high = nums.length - 1;

    while (low < high) {
        int mid = (low + high) / 2;

        if (nums[mid] > nums[high]) {
            low = mid + 1;  // min in right
        } else if (nums[mid] < nums[high]) {
            high = mid;     // min in left or mid
        } else {
            high--;         // can't tell → safely shrink
        }
    }

    return nums[low];
}
}