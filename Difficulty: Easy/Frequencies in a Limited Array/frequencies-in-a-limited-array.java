import java.util.*;

class Solution {
    public List<Integer> frequencyCount(int[] arr) {

        int n = arr.length;

        int[] freq = new int[n];

        for (int num : arr) {
            freq[num - 1]++;
        }

        List<Integer> ans = new ArrayList<>();

        for (int count : freq) {
            ans.add(count);
        }

        return ans;
    }
}