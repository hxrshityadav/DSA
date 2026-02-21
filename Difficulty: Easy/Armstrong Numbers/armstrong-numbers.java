// User function Template for Java
class Solution {
    static boolean armstrongNumber(int n) {
        // code here
        int sum = 0;
        int original = n;
        while (n>0){
            int digit = n%10;
            sum += digit*digit*digit;
            n /=10;
        }
        return sum == original;
    }
}