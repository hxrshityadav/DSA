class Solution {
    public static int largest(int[] arr) {
        // code here
        
        int maxSoFar = arr[0];
        
        for( int i =1; i<arr.length; i++){
            if (arr[i]> maxSoFar){
                maxSoFar = arr[i];
            }
        }
        return maxSoFar;
    }
}
