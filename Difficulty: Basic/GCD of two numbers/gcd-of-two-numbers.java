class Solution {
    public static int gcd(int a, int b) {
        // code here
        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        
        return a;
    }
}
