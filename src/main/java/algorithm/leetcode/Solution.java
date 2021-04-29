package algorithm.leetcode;

import java.util.*;

/**
 * @author junlin_huang
 * @create 2021-04-04 上午9:42
 **/


class Solution {

    public static void main(String[] args) {
        int[] nums = { 3, -2, -4, 6, -7, 9, 2, 0, -1, -8, 7};
        int maxSubSum = new Solution().maxSubSum(nums);
        System.out.println(maxSubSum);
    }

    public int maxSubSum(int[] nums) {
        int pre = nums[0];
        int maxSubSum = pre;
        for (int i = 1; i < nums.length; i++) {
            if (pre + nums[i] > nums[i]) {
                pre = pre + nums[i];
            } else {
                pre = nums[i];
            }
            maxSubSum = Math.max(maxSubSum, pre);
        }
        return maxSubSum;
    }

}