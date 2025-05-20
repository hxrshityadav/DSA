class Solution {
    public String largestNumber(int[] nums) {
        // Step 1: Convert all integers to strings
        String[] strNums = Arrays.stream(nums)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        // Step 2: Sort with a custom comparator
        Arrays.sort(strNums, (a, b) -> (b + a).compareTo(a + b));

        // Step 3: If the largest number is "0", the whole number is zero
        if (strNums[0].equals("0")) {
            return "0";
        }

        // Step 4: Join all strings to form the result
        return String.join("", strNums);
    }
}