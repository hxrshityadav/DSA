class Solution {
    public boolean isZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] a = new int[n];

        for(int[] query:queries){
            int s= query[0];
            int e= query[1];

            a[s]+= 1;
            if (e+1<n) {
                a[e+1]-=1;
            }
        }

        for(int i=1; i<n; i++) {
            a[i] = a[i] + a[i-1];
        }

        for (int i = 0; i<n; i++) {
            if(a[i]< nums[i])

            return false;
        }

        return true;



    }
}