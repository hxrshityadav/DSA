class Solution {
    public static int largest(int[] arr) {
        // code here
        
        int maxSoFar = 1;
        
        for( int i =0; i<arr.length; i++){
            if (arr[i]> maxSoFar){
                maxSoFar = arr[i];
            }
        }
        return maxSoFar;
    }
}
