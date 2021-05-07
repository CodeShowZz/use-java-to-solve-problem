package algorithm.leetcode;

import java.util.*;

/**
 * @author junlin_huang
 * @create 2021-04-04 上午9:42
 **/


class Solution {

    public static void main(String[] args) {
        List<List<Integer>> res = new Solution().combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println(res);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList();
        combinationSum(candidates, target, new ArrayList(), ans, 0);
        return ans;
    }

    public void combinationSum(int[] candidates, int target, List<Integer> tmp, List<List<Integer>> res, int index) {
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        combinationSum(candidates, target, tmp, res, index + 1);
        if (target - candidates[index] >= 0) {
            tmp.add(candidates[index]);
            combinationSum(candidates, target - candidates[index], tmp, res, index);
            tmp.remove(tmp.size() - 1);
        }
    }

}