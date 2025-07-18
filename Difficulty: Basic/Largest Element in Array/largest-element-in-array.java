class Solution {
    public static int largest(int[] arr) {
        int max = arr[0];
        
        for  (int i = 0; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
            return max;
        
    }
    
    public static void main (String[] args) {
        int arr[] = {20, 324, 55, 90, 9707};
        System.out.println(largest(arr));
    }
}
