public class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Check for odd-length palindromes (e.g., "aba" centered at 'b')
            int len1 = expandAroundCenter(s, i, i);
            // Check for even-length palindromes (e.g., "abba" centered between 'b' and 'b')
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > end - start) {
                // Update the boundaries of the longest palindrome found so far
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        // Expand outwards as long as the characters match and indices are within bounds
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // Return the length of the palindrome found
        return right - left - 1;
    }
}
