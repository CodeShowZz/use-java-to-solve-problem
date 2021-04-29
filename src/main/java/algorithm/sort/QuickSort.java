package algorithm.sort;

import java.util.Arrays;

/**
 * 记录 2分59秒
 *
 * @author junlin_huang
 * @create 2021-03-24 下午9:58
 **/

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {5, 6, 4, 3, 7};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    public static void sort(int[] arr, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int p = partition(arr, lo, hi);
        sort(arr, lo, p - 1);
        sort(arr, p + 1, hi);
    }

    public static int partition(int[] arr, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = arr[lo];
        while (true) {
            while (v > arr[++i]) {

            }
            while (v < arr[--j]) {

            }
            if (i >= j) {
                break;
            }
            CommonUtil.swap(arr, i, j);
        }
        CommonUtil.swap(arr, lo, j);
        return j;
    }


}
