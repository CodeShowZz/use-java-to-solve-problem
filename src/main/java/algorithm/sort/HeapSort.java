package algorithm.sort;

import java.util.Arrays;

/**
 *  堆排序
 *
 * @author junlin_huang
 * @create 2021-03-24 下午7:55
 **/

public class HeapSort {

    public static void main(String[] args) {
        int[] nums = {1, 6, 4, 3, 7};
        new HeapSort().heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public void heapSort(int[] nums) {
        int length = nums.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            treeify(nums, i, length);
        }
        for (int i = length - 1; i > 0; i--) {
            CommonUtil.swap(nums, 0, i);
            treeify(nums, 0, i);
        }
    }

    public void buildHeap() {

    }


    public void treeify(int[] nums, int i, int length) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left > length - 1 || right > length - 1) {
            return;
        }
        if (nums[left] > nums[largest]) {
            largest = left;
        }
        if (nums[right] > nums[largest]) {
            largest = right;
        }
        if (i != largest) {
            CommonUtil.swap(nums, i, largest);
            treeify(nums, largest, length);
        }
    }
}