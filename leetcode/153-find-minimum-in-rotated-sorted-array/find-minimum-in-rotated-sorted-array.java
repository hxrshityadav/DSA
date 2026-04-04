class Solution {
  public int findMin(int[] nums) {
    int low = 0, high = nums.length - 1;

    while (low < high) {
        int mid = (low + high) / 2;

        if (nums[mid] > nums[high]) {
            // Min must be in right part
            low = mid + 1;
        } else {
            // Min is in left part including mid
            high = mid;
        }
    }

    return nums[low];  // or nums[high], both same
  }
}