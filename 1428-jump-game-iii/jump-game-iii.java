class Solution {
    public boolean canReach(int[] arr, int start) {
        // Base case: check out of bounds or if the index was already visited
        if (start < 0 || start >= arr.length || arr[start] < 0) {
            return false;
        }
        
        // Target found: if the current index holds a 0, we've successfully reached the goal
        if (arr[start] == 0) {
            return true;
        }
        
        // Mark the current node as visited by making it negative
        arr[start] = -arr[start];
        
        // Recursively try to jump to the left or right neighbor
        return canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);
    }
}
