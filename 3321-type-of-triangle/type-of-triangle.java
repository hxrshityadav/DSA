class Solution {
    public String triangleType(int[] nums) {
        Set<Integer> set = new HashSet<>();

        if (nums[0] + nums[1] > nums[2] && nums[1] + nums[2] > nums[0] && nums[0] + nums[2] > nums[1]) {
            set.add(nums[0]);
            set.add(nums[1]);
            set.add(nums[2]);

            if(set.size()==1) return "equilateral";
            if(set.size()==2) return "isosceles";
            if(set.size()==3) return "scalene";
        }

        return "none";

    }
}