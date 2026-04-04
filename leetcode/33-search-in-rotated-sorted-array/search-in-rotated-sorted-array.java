class Solution {
  public int search(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    
    while (low <= high) {
        int mid = (low + high) / 2;
        
        if (nums[mid] == target) return mid;

        // Check if left half is sorted
        if (nums[low] <= nums[mid]) {
            if (nums[low] <= target && target < nums[mid]) {
                high = mid - 1;  // target in left sorted part
            } else {
                low = mid + 1;   // target in right part
            }
        } else {
            // Right half is sorted
            if (nums[mid] < target && target <= nums[high]) {
                low = mid + 1;   // target in right sorted part
            } else {
                high = mid - 1;  // target in left part
            }
        }
    }

    return -1;  // Not found
}

}