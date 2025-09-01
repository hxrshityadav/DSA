class Solution {
    public static int getSecondLargest(int[] arr) {
        int largest = arr[0];
        int second = -1;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > largest) {
                second = largest;
                largest = arr[i];
            } else if (arr[i] < largest && arr[i] > second) {
                second = arr[i];
            }
        }
        return second;
    }
    
    public static void main(String[] args) {
        int arr1[] = {12, 35, 1, 10, 34, 1};
        System.out.println(getSecondLargest(arr1));  // 34
        
        int arr2[] = {10, 5, 10};
        System.out.println(getSecondLargest(arr2));  // 5
        
        int arr3[] = {10, 10, 10};
        System.out.println(getSecondLargest(arr3));  // -1
    }
}
