public class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] means s[0...i-1] matches p[0...j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base case: empty string matches empty pattern
        dp[0][0] = true;
        
        // Deals with patterns like a*, a*b*, .* etc. matching empty string
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char currentPatternChar = p.charAt(j - 1);
                
                if (currentPatternChar == '*') {
                    // Case 1: Match 0 occurrences of the character before '*'
                    dp[i][j] = dp[i][j - 2];
                    
                    // Case 2: Match 1 or more occurrences
                    char precedingPatternChar = p.charAt(j - 2);
                    if (precedingPatternChar == '.' || precedingPatternChar == s.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (currentPatternChar == '.' || currentPatternChar == s.charAt(i - 1)) {
                    // Match single character directly
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        
        return dp[m][n];
    }
}
