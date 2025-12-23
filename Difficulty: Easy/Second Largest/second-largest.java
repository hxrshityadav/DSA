class Solution {

    public int getSecondLargest(int[] arr) {
        if (arr.length < 2) {
            return -1;
        }

        int largest = arr[0];
        int slargest = -1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > largest) {
                slargest = largest;
                largest = arr[i];
            } else if (arr[i] < largest && arr[i] > slargest) {
                slargest = arr[i];
            }
        }

        return slargest;
    }

    public static void main(String[] args) {
        int[] arr = {12, 35, 1, 10, 34, 1};
        Solution sol = new Solution();
        System.out.println(sol.getSecondLargest(arr));
    }
}
