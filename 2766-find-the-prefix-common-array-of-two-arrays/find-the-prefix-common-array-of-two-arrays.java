class Solution {
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
          int n = A.length;
          int[] ans = new int[n];
          int [] freq = new int[n+1];
          int commonCount = 0;

          for(int i =0; i<n; i++){
            if(++freq[A[i]] == 2) {
                commonCount++;
            }
            if (++freq[B[i]] == 2){
                commonCount++;
            }
            ans[i] = commonCount;
          }
          return ans;
    }
}