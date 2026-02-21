import java.util.*;

class Solution {
    public static ArrayList<Integer> printDivisors(int n) {
        
        ArrayList<Integer> result = new ArrayList<>();
        
        for (int i = 1; i * i <= n; i++) {
            
            if (n % i == 0) {
                
                result.add(i);
                
                if (i != n / i) {
                    result.add(n / i);
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
}